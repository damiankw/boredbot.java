package net.nictitate.boredBOT;

import java.io.*;
import java.net.*;
import java.util.*;

public class boredBOT {
  /* Set up the DataStreams: */
  private Socket sck_server = null;
  private PrintWriter sck_out = null;
  private BufferedReader sck_in = null;

  /* Set up local variables: */
  private Config config;
  private long start;
  private long connect;
  private User bot = new User();
  private UserList users = new UserList();
  private ChanList chans = new ChanList();
  private String server = "";
  private ArrayList<Setting> settings = new ArrayList<Setting>();
  

  /* Write data to the socket: */
  public void send_data(String send_data) {
    sck_out.println(send_data);
    System.out.println("-> " + send_data);
  }

  // create a new instance
  public boredBOT(String nick, String user, String name, String server, int port) {
    this.config = new Config(nick, user, name, server, port);
  }

  public void connect() {
    try {
      Date date = new Date();
      this.start = date.getTime();
      
      // Connect the socket:
      //sck_server = new Socket("irc.freenode.net", 6667);
      sck_server = new Socket(this.config.server(), this.config.port());

      // Set up the socket Streams:
      sck_out = new PrintWriter(sck_server.getOutputStream(), true);
      sck_in = new BufferedReader(new InputStreamReader(sck_server.getInputStream()));

      // Send data to server for authentication:
      this.bot.setNick(this.config.nick());
      System.out.println("-> SET BOTNICK: " + this.bot.nick());
      send_data("NICK " + this.config.nick());
      send_data("USER " + this.config.user() + " {} {} :" + this.config.name());

      // Set up the tmpRead to read from the server:
      String read_data;

      // While the socket exists, read it:

      while ((read_data = sck_in.readLine()) != null) {
        if (Util.lindex(read_data, 0).equals("PING")) {
          send_data("PONG " + Util.lindex(read_data, 1));
          
        } else if ((Util.lindex(read_data, 0).indexOf("!") > 0) && (Util.lindex(read_data, 0).indexOf("@") > 0)) {
          // if commands are coming from a user
          svr_user(read_data);
        }
        else {
          // if commands are coming from the server
          svr_server(read_data);
        }
      }
    }
    catch (IOException error) {
      System.out.println("& There was an error: " + error.toString());
      System.out.println(" -> " + error.getMessage());
    }
  }
  
  /* When the server sends a command: */
  public void svr_server(String read_data) {
    System.out.println("<- " + read_data);
    
    if (read_data.length() == 0) {
      return;
    } else if (read_data.substring(0, 1).equals(":")) {
      read_data = read_data.substring(1, read_data.length());
      if (Util.lindex(read_data, 1).equals("001")) {
        send_data("WHO " + this.bot.nick());
        send_data("JOIN #nictitate");
        
      } else if (Util.lindex(read_data, 1).equals("004")) {
        this.server = Util.lindex(read_data, 3);
        
      } else if (Util.lindex(read_data, 1).equals("005")) {
        for (String word : Util.lrange(read_data, 3, -1).split(" ")) {
          String setting[];
          if (word.indexOf("=") == -1) {
            setting = (word + "=1").split("=");
          } else {
            setting = word.split("=");
          }
          
          this.settings.add(new Setting(setting[0], setting[1]));
        }
      } else if (Util.lindex(read_data, 1).equals("324")) {
        Channel chan = this.chans.find(Util.lindex(read_data, 3));
        chan.setMode(Util.lindex(read_data, 4));
        chans.update(chan);

      } else if (Util.lindex(read_data, 1).equals("329")) {
        Channel chan = this.chans.find(Util.lindex(read_data, 3));
        chan.setCreated(Long.parseLong(Util.lindex(read_data, 4)));
        chans.update(chan);

      } else if (Util.lindex(read_data, 1).equals("332")) {
        Channel chan = this.chans.find(Util.lindex(read_data, 3));
        chan.setTopic(Util.lrange(read_data, 4, -1).substring(1, Util.lrange(read_data, 4, -1).length()));
        chans.update(chan);

      } else if (Util.lindex(read_data, 1).equals("333")) {
        Channel chan = this.chans.find(Util.lindex(read_data, 3));
        chan.setTopicBy(Util.lindex(read_data, 4));
        chan.setTopicWhen(Long.parseLong(Util.lindex(read_data, 5)));
        chans.update(chan);

      } else if (Util.lindex(read_data, 1).equals("352")) {
        if (this.users.find(Util.lindex(read_data, 7)) == null) {
          this.users.add(Util.lindex(read_data, 7), Util.lindex(read_data, 4), Util.lindex(read_data, 5));
        } else {
          User user = this.users.find(Util.lindex(read_data, 7));
          user.setNick(Util.lindex(read_data, 7));
          user.setUser(Util.lindex(read_data, 4));
          user.setHost(Util.lindex(read_data, 5));
          this.users.update(user);
        }
        
        if (bot.nick().equals(Util.lindex(read_data, 7))) {
          bot.setUser(Util.lindex(read_data, 4));
          bot.setHost(Util.lindex(read_data, 5));
        }
      
      } else if (Util.lindex(read_data, 1).equals("433")) {
        int rand = 10 + (int)(Math.random() * 99); 
        this.bot.setNick("boredBOT-" + rand);
        send_data("NICK boredBOT-" + rand);

      }
    }
  }

  /* When a client sends a command:*/
  public void svr_user(String read_data) {
    ParseUser info = new ParseUser(read_data);
    System.out.println("[" + info.nick() + "!" + info.ident() + "@" + info.host() + "] " + "!" + info.cmd() + ":" + info.target() + "! " + info.text());

    if (info.text(0).equals(".die")) {
      send_data("QUIT :" + info.text(1, info.textSize() - 1));
    
    } else if (info.text(0).equals(".status")) {
      Date date = new Date();
      
      msg(info.target(), "uptime:[" + ((date.getTime() - this.start) / 1000) + "secs] nick:[" + bot.nick() + "] ident:[" + bot.user() + "] host:[" + bot.host() + "] server:[" + this.server + "]");
      msg(info.target(), "settings:[" + this.settings.size() + "]");
      msg(info.target(), "channels:[" + this.chans.total() + "] list:[" + this.chans.toString() + "]");
      msg(info.target(), "users:[" + this.users.total() + "] list:[" + this.users.toString() + "]");
    
    } else if (info.text(0).equals(".chaninfo")) {
      Channel chans[] = this.chans.list();
      for (int i = 0; i < this.chans.total(); i++) {
        msg(info.target(), "name:[" + chans[i].name() + "] created:[" + chans[i].created() + "] topicby:[" + chans[i].topicBy() + "] topicwhen:[" + chans[i].topicWhen() + "] mode:[" + chans[i].mode() + "] topic:[" + chans[i].topic() + "]");
      }

    } else if (info.text(0).equals(".join")) {
      send_data("JOIN " + info.text(1));
    
    } else if ((info.cmd().equals("JOIN")) && (info.nick().equalsIgnoreCase(bot.nick()))) {
      this.chans.add(new Channel(info.target()));
      send_data("MODE " + info.target());
      send_data("WHO " + info.target());

    }
  }

  public void msg(String target, String text) {
    send_data("PRIVMSG " + target + " :" + text);
  }
  
  public String get(String key) {
    // loop through all users
    for (int i = 0; i < this.settings.size(); i++) {
      // check if the user exists
      Setting setting = this.settings.get(i);
      if (setting.key().equalsIgnoreCase(key)) {
        return setting.value();
      }
    }

    return null;
  }
}










