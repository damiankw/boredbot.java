package net.nictitate.boredBOT;

import java.util.*;

public class Channel {
  private String name, topic, topicBy, mode;
  private long created, topicWhen;
  private ArrayList<ChanUser> users = new ArrayList<ChanUser>();

  /* Default Constructor: */
  public Channel(String name) {
    this.name = name;
    this.topic = "";
    this.mode = "+";
    this.created = 0;
    this.topicBy = "";
    this.topicWhen = 0;
  }

  /* To set the Topic after creation: */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setTopicBy(String nick) {
    this.topicBy = nick;
  }

  public void setTopicWhen(long date) {
    this.topicWhen = date;
  }

  /* To set the Mode after creation: */
  public void setMode(String mode) {
    this.mode = mode;
  }
  
  public void setCreated(long created) {
    this.created = created;
  }

  // add a user to the list
  public void addUser(String nick, String mode) {
    // remove the nickname if it already exists (though we want to keep join time?)
    this.delUser(nick);
    
    /* Add in the new Nickname to the Channel: */
    this.users.add(new ChanUser(nick, mode));
  }

  /* To remove a Nickname from the List: */
  public void delUser(String nick) {
    /* Remove any old Nicknames of the same name from the channel: */
    for (int i = this.users.size(); i > 0; i--) {
      ChanUser tmpChanUser = (ChanUser) this.users.get(i - 1);
      if (tmpChanUser.nick().equalsIgnoreCase(nick)) {
        this.users.remove(i - 1);
      }
    }
  }

  /* To return a Channel Name: */
  public String name() {
    return this.name;
  }

  /* To return the Channel Topic: */
  public String topic() {
    return this.topic;
  }

  public String topicBy() {
    return this.topicBy;
  }

  public long topicWhen() {
    return this.topicWhen;
  }

  /* To return the Channel Mode: */
  public String mode() {
    return this.mode;
  }

  public long created() {
    return this.created;
  }

  /* To find a Nickname in the List: */
  public ChanUser findUser(String nick) {
    for (int i = 0; i < this.users.size(); i++) {
      ChanUser tmpChanUser = (ChanUser) this.users.get(i);
      if (tmpChanUser.nick().equalsIgnoreCase(nick)) {
        return tmpChanUser;
      }
    }

    return null;
  }

  /* To list all Nicknames in the List as an Array: */
  public ChanUser[] listUser() {
    ChanUser user[] = new ChanUser[this.users.size()];
    for (int i = 0; i < this.users.size(); i++) {
      user[i] = (ChanUser) this.users.get(i);
    }

    return user;
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