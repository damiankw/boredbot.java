package net.nictitate.boredBOT;

import java.util.*;

public class Channel {
  private String name, topic, topicBy, mode;
  private long joined, created, topicWhen;
  private ArrayList<ChanUser> users = new ArrayList<ChanUser>();

  // set up default values on creation
  public Channel(String name) {
    Date date = new Date();
    
    this.name = name;
    this.topic = "";
    this.mode = "+";
    this.created = 0;
    this.topicBy = "";
    this.topicWhen = 0;
    this.joined = date.getTime() / 1000;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setTopicBy(String nick) {
    this.topicBy = nick;
  }

  public void setTopicWhen(long date) {
    this.topicWhen = date;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }
  
  public void setCreated(long created) {
    this.created = created;
  }

  
  // return the channel name
  public String name() {
    return this.name;
  }

  // return the current topic
  public String topic() {
    return this.topic;
  }

  // return who last changed the topic
  public String topicBy() {
    return this.topicBy;
  }

  // return when the topic was last updated
  public long topicWhen() {
    return this.topicWhen;
  }

  // return the current mode
  public String mode() {
    return this.mode;
  }

  // return the channel creation date
  public long created() {
    return this.created;
  }
  
  // return when the bot joined the channel
  public long joined() {
    return this.joined;
  }

  
  
  
  

  // add a user to the channel
  public void addUser(String nick) {
    this.addUser(nick, "");
  }
  
  public void addUser(String nick, String mode) {
    if (this.findUser(nick) != null) {
      // update the info if already found
      this.findUser(nick).setNick(nick);
      this.findUser(nick).setMode(mode);
    } else {
      // create new user otherwise
      this.users.add(new ChanUser(nick, mode));
    }
  }

  // delete a user from the channel
  public void delUser(String nick) {
    this.users.remove(this.findUser(nick));
  }

  /* To find a Nickname in the List: */
  public ChanUser findUser(String nick) {
    for (int i = 0; i < this.users.size(); i++) {
      if (this.users.get(i).nick().equalsIgnoreCase(nick)) {
        return this.users.get(i);
      }
    }

    return null;
  }

  /* To list all Nicknames in the List as an Array: */
  public ChanUser[] listUser() {
    ChanUser users[] = new ChanUser[this.users.size()];
    for (int i = 0; i < this.users.size(); i++) {
      users[i] = (ChanUser) this.users.get(i);
    }

    return users;
  }
  
  public int countUser() {
    return this.users.size();
  }

  /* To list all Nicknames in the List as a String: */
  public String toString() {
    String tmpReturn = "";
    for (int i = 0; i < this.users.size(); i++) {
      ChanUser tmpChanUser = (ChanUser) this.users.get(i);
      tmpReturn += " " + tmpChanUser.nick();
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }
}