package net.nictitate.boredBOT;

import java.io.*;
import java.net.*;
import java.util.*;

public class boredBOT {
  // core variables for the socket
  private Socket sckServer = null;
  private PrintWriter sckOut = null;
  private BufferedReader sckIn = null;

  // local variables for settings
  private SettingList config = new SettingList();   // configuration passed from the user
  private SettingList env = new SettingList();      // environment variables for common information
  private UserList users = new UserList();          // list of users in the system
  private ChannelList channels = new ChannelList(); // list of channels in the system
  

  // create a new instance
  public boredBOT(String nick, String user, String name, String server, int port) {
    // store the configuration locally
    setConf("nick", nick);
    setConf("user", user);
    setConf("name", name);
    setConf("server", server);
    setConf("port", String.format("%d", port));

    // store when the bot starts
    setEnv("start_time", String.format("%d", new Date().getTime() / 1000));
}

  // connect to the server and do the processing
  public void connect() {
    try {
      // connect to the server and configure the streams
      sckServer = new Socket(getConf("server"), Integer.parseInt(getConf("port")));
      sckOut = new PrintWriter(sckServer.getOutputStream(), true);
      sckIn = new BufferedReader(new InputStreamReader(sckServer.getInputStream()));

      // send initial data to the server
      setEnv("connect_time", String.format("%d", new Date().getTime() / 1000));
      setEnv("botnick", getConf("nick"));
      send("NICK " + getConf("nick"));
      send("USER " + getConf("user") + " {} {} :" + getConf("name"));

      // Set up the tmpRead to read from the server:
      String data;

      // While the socket exists, data it:

      while ((data = sckIn.readLine()) != null) {
        if (Util.lindex(data, 0).equals("PING")) {
          send("PONG " + Util.lindex(data, 1));
          
        } else if ((Util.lindex(data, 0).indexOf("!") > 0) && (Util.lindex(data, 0).indexOf("@") > 0)) {
          // if commands are coming from a user
          new ProcessUser(this).process(data);
        }
        else {
          // if commands are coming from the server
          new ProcessServer(this).process(data);
        }
      }
    }
    catch (IOException error) {
      System.out.println("& There was an error: " + error.toString());
      System.out.println(" -> " + error.getMessage());
    }
  }
  
  // send data to the server
  public void send(String data) {
    sckOut.println(data);
    System.out.println("-> " + data);
  }

  // send a message
  public void msg(String target, String text) {
    send("PRIVMSG " + target + " :" + text);
  }
  
  // send a notice
  public void notice(String target, String text) {
    send("NOTICE " + target + " :" + text);
  }
  
  // send a ctcp reply
  public void ctcpreply(String target, String text) {
    send("NOTICE " + target + " :" + text + "");
  }
  
  // send a ctcp request
  public void ctcp(String target, String text) {
    send("PRIVMSG " + target + " :" + text + "");
  }
  
  // get configuration settings
  public String getConf(String key) {
    return this.config.get(key);
  }
  
  // set configuration settings
  public void setConf(String key, String value) {
    this.config.add(key, value);
  }

  // get environment settings
  public String getEnv(String key) {
    return this.env.get(key);
  }
  
  // set environment settings
  public void setEnv(String key, String value) {
    this.env.add(key, value);
  }
  
  // get channel information
  public Channel getChannel(String chan) {
    return this.channels.get(chan);
  }
  
  // get all channels
  public Channel[] getChannels() {
    return this.channels.list();
  }
  
  // add a channel
  public void addChannel(String chan) {
    this.channels.add(chan);
  }
  
  // get user information
  public User getUser(String nick) {
    return this.users.get(nick);
  }
  
  // get all users
  public User[] getUsers() {
    return this.users.list();
  }
  
  // add a user
  public void addUser(String nick, String user, String host) {
    this.users.add(nick, user, host);
  }
  
  // delete a user
  public void delUser(String nick) {
    // delete the general information about the nickname
    this.users.del(nick);
    
    // delete reference to all channels they are in
    for (Channel ch : this.getUserChan(nick)) {
      this.getChannel(ch.name()).delUser(nick);
    }
  }
  
  // get all channels a user is on
  public Channel[] getUserChan(String nick) {
    ArrayList<Channel> channels = new ArrayList<Channel>();
    
    for (Channel ch : this.channels.list()) {
      if (ch.findUser(nick) != null) {
        channels.add(ch);
      }
    }
    
    return channels.toArray(new Channel[channels.size()]);
  }
}










