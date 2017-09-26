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

  /* Create a new socket and control it: */
  public boredBOT() {
    try {
      Date date = new Date();
      this.start = date.getTime();
      
      // Connect the socket:
      //sck_server = new Socket("irc.freenode.net", 6667);
      sck_server = new Socket("stable.nictitate.net", 6667);

      // Set up the socket Streams:
      sck_out = new PrintWriter(sck_server.getOutputStream(), true);
      sck_in = new BufferedReader(new InputStreamReader(sck_server.getInputStream()));

      // Send data to server for authentication:
      this.bot.setNick("boredbot");
      System.out.println("-> SET BOTNICK: " + this.bot.nick());
      send_data("NICK boredbot");
      send_data("USER boredbot {} {} :I am boredBOT-j!");

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
    ParseServ args = new ParseServ(read_data);
    if (args.type() == 1) {
      System.out.println("!" + args.server() + "! #" + args.numeric() + ": " + args.arg(0, args.size()));

      if (args.numeric().equals("001")) {
        send_data("WHO " + this.bot.nick());
        send_data("JOIN #nictitate");
      } else if (args.numeric().equals("004")) {
        this.server = args.arg(0);
      
      } else if (args.numeric().equals("005")) {
        for (int i = 0; i < args.size(); i++) {
          String setting[];
          if (args.arg(i).indexOf("=") == -1) {
            setting = (args.arg(i)+"=1").split("=");
          } else {
            setting = args.arg(i).split("=");
          }
          
          this.settings.add(new Setting(setting[0], setting[1]));
        }
        
      } else if (args.numeric().equals("324")) {
        Channel chan = this.chans.find(args.arg(0));
        
        chan.setMode(args.arg(1));
        
        chans.update(chan);

      } else if (args.numeric().equals("329")) {
        Channel chan = this.chans.find(args.arg(0));
        
        chan.setCreated(Long.parseLong(args.arg(1)));
        
        chans.update(chan);

      } else if (args.numeric().equals("332")) {
        Channel chan = this.chans.find(args.arg(0));
        
        chan.setTopic(args.arg(1, args.size()));
        
        chans.update(chan);

      } else if (args.numeric().equals("333")) {
        Channel chan = this.chans.find(args.arg(0));
        
        chan.setTopicBy(args.arg(1));
        chan.setTopicWhen(Long.parseLong(args.arg(2)));
        
        chans.update(chan);

      } else if (args.numeric().equals("352")) {
        // store user info: #nictitate boredbot local.damian.id.au stable.nictitate.net boredbot H 0 I am boredBOT-j!
        if (this.users.find(args.arg(4)) == null) {
          this.users.add(args.arg(4), args.arg(1), args.arg(2));
        } else {
          User user = this.users.find(args.arg(4));
          user.setNick(args.arg(4));
          user.setUser(args.arg(1));
          user.setHost(args.arg(2));
          this.users.update(user);
        }
        
        if (bot.nick().equals(args.arg(1))) {
          bot.setUser(args.arg(4));
          bot.setHost(args.arg(2));
        }
      
      } else if (args.numeric().equals("353")) {
          // store users on channel: = #nictitate boredbot @damian
/*          String users[] = args.arg(2).split(" ");
          for (int i = 0; i < users.length; i++) {
            if (this.users.find(users[i]) == null) {
              User user = new User(users[i]);
              this.users.add(user);
            }
          }
*/          
      
      } else if (args.numeric().equals("433")) {
        this.bot.setNick("boredBOT-j");
        send_data("NICK boredBOT-j");

      }
    } else if (args.type() == 2) {
      System.out.println("<- " + read_data);
      
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
      msg(info.target(), "users:[" + this.chans.total() + "] list:[" + this.chans.toString() + "]");
    
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










