package net.nictitate.boredBOT;

import java.util.*;

public class ProcessUser {
  private boredBOT client;
  
  public ProcessUser(boredBOT client) {
    this.client = client;
  }
  
  public void process(String data) {
    System.out.println("<- " + data);
    
    if (Util.lindex(data, 1).equals("PRIVMSG")) {
      // :damian!damian@local.damian.id.au PRIVMSG #nictitate :text
      if (Util.trimLeft(Util.lindex(data, 2), ':').substring(0, 1).equals("#")) {
        if (Util.lindex(data, 3).substring(1, 2).equals("")) {
          if (Util.trimLeft(Util.trimLeft(Util.lindex(data, 3), ':'), '').equalsIgnoreCase("ACTION")) {
            usr_action_chan(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lindex(data, 2), Util.trimRight(Util.lrange(data, 4), ''));
          } else {
            usr_ctcp_chan(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lindex(data, 2), Util.trimRight(Util.trimLeft(Util.lrange(data, 3).substring(1), ''), ''));
          }
        } else {
          usr_privmsg_chan(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lindex(data, 2), Util.lrange(data, 3).substring(1));
        }
      } else {
        if (Util.lindex(data, 3).substring(1, 2).equals("")) {
          if (Util.trimLeft(Util.trimLeft(Util.lindex(data, 3), ':'), '').equalsIgnoreCase("ACTION")) {
            usr_action_user(Util.getNickFromAddress(Util.lindex(data, 0)), Util.trimRight(Util.lrange(data, 4), ''));
          } else {
            usr_ctcp_user(Util.getNickFromAddress(Util.lindex(data, 0)), Util.trimRight(Util.trimLeft(Util.lrange(data, 3).substring(1), ''), ''));
          }
        } else {
          usr_privmsg_user(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lrange(data, 3).substring(1));
        }
      }
        
    } else if (Util.lindex(data, 1).equals("NOTICE")) {
      // :damian!damian@local.damian.id.au NOTICE boredBOT :oi
      
    } else if (Util.lindex(data, 1).equals("JOIN")) {
      // :damian!damian@local.damian.id.au JOIN :#nictitate
      usr_join(Util.getNickFromAddress(Util.lindex(data, 0)), Util.getUserFromAddress(Util.lindex(data, 0)), Util.getHostFromAddress(Util.lindex(data, 0)), Util.trimLeft(Util.lindex(data, 2), ':'));
      
    } else if (Util.lindex(data, 1).equals("PART")) {
      // :damian!damian@local.damian.id.au PART #nictitate
      usr_part(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lindex(data, 2), (Util.lrange(data, 3).length() > 1) ? Util.lrange(data, 3).substring(1) : "");
      
    } else if (Util.lindex(data, 1).equals("INVITE")) {
      // :damian!damian@local.damian.id.au INVITE boredBOT :#meerkat
      
    } else if (Util.lindex(data, 1).equals("KICK")) {
      // :damian!damian@local.damian.id.au KICK #meerkat boredBOT :...
      usr_kick(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lindex(data, 2), Util.lindex(data, 3), Util.lrange(data, 4).substring(1));
      
    } else if (Util.lindex(data, 1).equals("MODE")) {
      // :damian!damian@local.damian.id.au MODE #nerdhacks +ntkl hi 10
      
    } else if (Util.lindex(data, 1).equals("NICK")) {
      // :bleh!damian@local.damian.id.au NICK damian
      usr_nick(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lindex(data, 2));
      
    } else if (Util.lindex(data, 1).equals("QUIT")) {
      // :damian!damian@local.damian.id.au QUIT :Quit: ..
      usr_quit(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lrange(data, 2).substring(1));
      
    } else if (Util.lindex(data, 1).equals("TOPIC")) {
      // :damian!damian@local.damian.id.au TOPIC #meerkat :oi oi oi
      usr_topic(Util.getNickFromAddress(Util.lindex(data, 0)), Util.lindex(data, 2), Util.lrange(data, 3).substring(1));
      
    }
  }
  
  private void usr_privmsg_chan(String nick, String chan, String text) {
    System.out.println("<" + nick + ":" + chan + "> " + text);
    
    if (Util.lindex(text, 0).equalsIgnoreCase(".chaninfo")) {
      // .chaninfo [<channel>]
      if (Util.lindex(text, 1).equals("")) {
        client.msg(chan, "*** Listing all channel information");
        for (Channel ch : client.getChannels()) {
          client.msg(chan, "name:[" + ch.name() + "] users:[" + ch.countUser() + "] joined:[" + ch.joined() + "] created:[" + ch.created() + "] topicby:[" + ch.topicBy() + "] topicwhen:[" + ch.topicWhen() + "] mode:[" + ch.mode() + "] topic:[" + ch.topic() + "]");
        }
      } else {
        client.msg(chan, ".. listing info for " + chan);
      }
    } else if (Util.lindex(text, 0).equalsIgnoreCase(".userinfo")) {
      for (User us : client.getUsers()) {
        //client.msg(chan, "nick:[" + us.nick() + "] user:[" + us.user() + "] host:[" + us.host() + "] channels:[" + client.getUserChan(us.nick()).length + "]");
        System.out.println("nick:[" + us.nick() + "] user:[" + us.user() + "] host:[" + us.host() + "] channels:[" + client.getUserChan(us.nick()).length + "]");
      }
      
    } else if (Util.lindex(text, 0).equalsIgnoreCase(".status")) {
      client.msg(chan, "uptime:[" + ((new Date().getTime() / 1000) - Long.parseLong(client.getEnv("start_time"))) + "secs] connected:[" + ((new Date().getTime() / 1000) - Long.parseLong(client.getEnv("connect_time"))) + "secs] nick:[" + client.getEnv("botnick") + "] ident:[" + client.getEnv("botuser") + "] host:[" + client.getEnv("bothost") + "] server:[" + client.getEnv("server") + "]");
      client.msg(chan, "channels:[" + client.getChannels().length + "] list:[" + Arrays.toString(client.getChannels()) + "]");
      client.msg(chan, "users:[" + client.getUsers().length + "] list:[" + Arrays.toString(client.getUsers()) + "]");
      //client.msg(info.target(), "settings:[" + client.settings.size() + "]");
    
      
    } else if (Util.lindex(text, 0).equalsIgnoreCase(".join")) {
      client.send("JOIN " + Util.lindex(text, 1));
    } 
  }
  
  private void usr_privmsg_user(String nick, String text) {
    System.out.println("<" + nick + "> " + text);
  }
  
  private void usr_action_chan(String nick, String chan, String text) {
    System.out.println("* " + nick + ":" + chan + " " + text);
  }
  
  private void usr_action_user(String nick, String text) {
    System.out.println("* " + nick + " " + text);
  }
  
  private void usr_ctcp_chan(String nick, String chan, String text) {
    System.out.println("[" + nick + ":" + chan + " " + Util.lindex(text, 0).toUpperCase() + " " + Util.lrange(text, 1) + "]");
    if (Util.lindex(text, 0).equalsIgnoreCase("VERSION")) {
      client.ctcpreply(nick, "VERSION boredBOT Java IRC Client v0.01");
    }
  }
  
  private void usr_ctcp_user(String nick, String text) {
    System.out.println("[" + nick + " " + Util.lindex(text, 0).toUpperCase() + " " + Util.lrange(text, 1) + "]");
    if (Util.lindex(text, 0).equalsIgnoreCase("VERSION")) {
      client.ctcpreply(nick, "VERSION boredBOT Java IRC Client v0.01");
    }
  }
  
  private void usr_notice_chan(String nick, String chan, String text) {
    System.out.println("-" + nick + ":" + chan + "- " + text);
    
  }
  
  private void usr_notice_user(String nick, String text) {
    System.out.println("-" + nick + "- " + text);
    
  }
  
  private void usr_join(String nick, String user, String host, String chan) {
    System.out.println("*** Join: " + nick + " (" + user + "@" + host + ") to " + chan);
    if (nick.equals(client.getEnv("botnick"))) {
      client.addChannel(chan);
      client.send("MODE " + chan);
      client.send("WHO " + chan);
    } else if (client.getUser(nick) == null) {
      client.addUser(nick, user, host);
    }
    
    client.getChannel(chan).addUser(nick);
  }
  
  private void usr_part(String nick, String chan, String text) {
    System.out.println("*** Part: " + nick + " (" + client.getUser(nick).address() + ") from " + chan + " [" + text + "]");
    
    if (client.getUserChan(nick).length == 1) {
      // remove user from database if it's the last channel, this will remove from all channels
      client.delUser(nick);
      
    } else {
      // remove user from channel
      client.getChannel(chan).delUser(nick);
    }
    
    // MAYBE keep a record of them?? (maybe do this later with the seen function only)
  }
  
  private void usr_invite(String nick, String chan) {
    System.out.println("");
  }
  
  private void usr_kick(String nick, String chan, String knick, String text) {
    System.out.println("*** Kick: " + knick + " was kicked from " + chan + " by " + nick + " (" + text + ")");
    
    if (client.getUserChan(knick).length == 0) {
      // remove user from database if it's the last channel, this will remove from all channels
      client.delUser(knick);
      
    } else {
      // remove user from channel
      client.getChannel(chan).delUser(knick);
    }
  }
  
  private void usr_mode_chan(String nick, String chan, String mode) {
    System.out.println("*** Mode: (on " + chan + " by " + nick + ") " + mode);
  }
  
  private void usr_mode_user(String nick, String mode) {
    System.out.println("USER MODE ....");
  }
  
  private void usr_nick(String nick, String newnick) {
    System.out.println("*** Nick: " + nick + " is now known as " + newnick);
    
    // change nick on user
    client.getUser(nick).setNick(newnick);
    
    // update the channel listings
    for (Channel ch : client.getChannels()) {
      client.getChannel(ch.name()).findUser(nick).setNick(newnick);
    }
  }
  
  private void usr_quit(String nick, String text) {
    System.out.println("*** Quit: " + nick + " (" + client.getUser(nick).address() + ") " + text);

    client.delUser(nick);
  }
  
  private void usr_topic(String nick, String chan, String text) {
    System.out.println("*** Topic: (on " + chan + " by " + nick + ") " + text);
    
    client.getChannel(chan).setTopic(text);
    client.getChannel(chan).setTopicBy(client.getUser(nick).fulladdress());
    client.getChannel(chan).setTopicWhen(new Date().getTime() / 1000);
  }
    
/*    
    ParseUser info = new ParseUser(data);
    System.out.println("[" + info.nick() + "!" + info.ident() + "@" + info.host() + "] " + "!" + info.cmd() + ":" + info.target() + "! " + info.text());

    if (info.text(0).equals(".die")) {
      client.send("QUIT :" + info.text(1, info.textSize() - 1));
    
    } else if (info.text(0).equals(".status")) {
      Date date = new Date();
      System.out.println(client.getEnv("start_time"));
      client.msg(info.target(), "uptime:[" + ((date.getTime() / 1000) - Long.parseLong(client.getEnv("start_time"))) + "secs] connected:[" + ((date.getTime() / 1000) - Long.parseLong(client.getEnv("connect_time"))) + "secs] nick:[" + client.getEnv("botnick") + "] ident:[" + client.getEnv("botuser") + "] host:[" + client.getEnv("bothost") + "] server:[" + client.getEnv("server") + "]");
      //client.msg(info.target(), "settings:[" + client.settings.size() + "]");
      client.msg(info.target(), "channels:[" + client.channels.total() + "] list:[" + client.channels.toString() + "]");
      client.msg(info.target(), "users:[" + client.users.total() + "] list:[" + client.users.toString() + "]");
    
    } else if (info.text(0).equals(".chaninfo")) {
      Channel chans[] = client.channels.list();
      for (int i = 0; i < client.channels.total(); i++) {
        client.msg(info.target(), "name:[" + chans[i].name() + "] created:[" + chans[i].created() + "] topicby:[" + chans[i].topicBy() + "] topicwhen:[" + chans[i].topicWhen() + "] mode:[" + chans[i].mode() + "] topic:[" + chans[i].topic() + "]");
      }

    } else if (info.text(0).equals(".join")) {
      client.send("JOIN " + info.text(1));
    
    } else if ((info.cmd().equals("JOIN")) && (info.nick().equalsIgnoreCase(client.getEnv("botnick")))) {
      client.channels.add(info.target());
      client.send("MODE " + info.target());
      client.send("WHO " + info.target());

    }
  } */
}