import java.util.*;

public class Channel {
  private String name, topic, mode, information;
  private List NickList = new ArrayList();

  /* Default Constructor: */
  public Channel(String name) {
    this.name = name;
    this.topic = "";
    this.mode = "";
    this.information = "";
  }

  /* To set the Topic after creation: */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /* To set the Mode after creation: */
  public void setMode(String mode) {
    this.mode = mode;
  }

  /* To set the Information after creation: */
  public void setInformation(String information) {
    this.information = information;
  }

  /* To add a Nickname to the List: */
  public void addNickname(String nickname, String mode) {
    /* Remove any old Nicknames of the same name from the channel: */
    for (int i = this.NickList.size(); i > 0; i--) {
      ChanUser tmpChanUser = (ChanUser) this.NickList.get(i - 1);
      if (tmpChanUser.nick().equalsIgnoreCase(nickname)) {
        this.NickList.remove(i - 1);
      }
    }

    /* Add in the new Nickname to the Channel: */
    this.NickList.add(new ChanUser(nickname, mode));
  }

  /* To remove a Nickname from the List: */
  public void delNickname(String nickname) {
    /* Remove any old Nicknames of the same name from the channel: */
    for (int i = this.NickList.size(); i > 0; i--) {
      ChanUser tmpChanUser = (ChanUser) this.NickList.get(i - 1);
      if (tmpChanUser.nick().equalsIgnoreCase(nickname)) {
        this.NickList.remove(i - 1);
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

  /* To return the Channel Mode: */
  public String mode() {
    return this.mode;
  }

  /* To find a Nickname in the List: */
  public ChanUser findNickname(String nickname) {
    for (int i = 0; i < this.NickList.size(); i++) {
      ChanUser tmpChanUser = (ChanUser) this.NickList.get(i);
      if (tmpChanUser.nick().equalsIgnoreCase(nickname)) {
        return tmpChanUser;
      }
    }

    return null;
  }

  /* To list all Nicknames in the List as an Array: */
  public ChanUser[] list() {
    ChanUser user[] = new ChanUser[this.NickList.size()];
    for (int i = 0; i < this.NickList.size(); i++) {
      user[i] = (ChanUser) this.NickList.get(i);
    }

    return user;
  }

  /* To list all Nicknames in the List as a String: */
  public String toString() {
    String tmpReturn = "";
    for (int i = 0; i < this.NickList.size(); i++) {
      ChanUser tmpChanUser = (ChanUser) this.NickList.get(i);
      tmpReturn += " " + tmpChanUser.nick();
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }




////////////////////////////////////////////////////////////////////////////////
////////////////////// testing /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
  public static void main(String args[]) {
    Channel channel = new Channel("#psyFIN");
    channel.addNickname("damian", "");
    channel.addNickname("dbm", "o");
    channel.addNickname("PsyfiN", "ov");

    System.out.println(channel.toString());
  }
}