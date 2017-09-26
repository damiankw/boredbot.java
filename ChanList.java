import java.util.*;

public class ChanList {
  /* The array to hold the list of Channels: */
  private List ChanList = new ArrayList();

  /* Default Constructor: */
  public ChanList() { }

  /* To add a new Channel: */
  public void add(String channel) {
    if (this.find(channel) == null) {
      this.ChanList.add(new Channel(channel));
    }
  }

  /* To remove an old Channel: */
  public void del(String channel) {
    for (int i = this.ChanList.size(); i > 0; i--) {
      Channel tmpChannel = (Channel) this.ChanList.get(i - 1);
      if (tmpChannel.name().equalsIgnoreCase(channel)) {
        this.ChanList.remove(i - 1);
      }
    }
  }

  /* To find a Channel: */
  public Channel find(String channel) {
    for (int i = 0; i < this.ChanList.size(); i++) {
      Channel tmpChannel = (Channel) this.ChanList.get(i);
      if (tmpChannel.name().equalsIgnoreCase(channel)) {
        return tmpChannel;
      }
    }

    return null;
  }

  /* To list all Channels as an Array: */
  public Channel[] list() {
    Channel channel[] = new Channel[this.ChanList.size()];
    for (int i = 0; i < this.ChanList.size(); i++) {
      channel[i] = (Channel) this.ChanList.get(i);
    }

    return channel;
  }

  /* To list all Users as a String: */
  public String toString() {
    String tmpReturn = "";
    for (int i = 0; i < this.ChanList.size(); i++) {
      Channel tmpChannel = (Channel) this.ChanList.get(i);
      tmpReturn += " " + tmpChannel.name();
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }



////////////////////////////////////////////////////////////////////////////////
////////////////////// testing /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
  public static void main(String args[]) {
    ChanList c = new ChanList();
    c.add("#scripters");
    c.add("#PsyfiN");
    c.add("#help");

    c.del("#psyfin");

    System.out.println(c.toString());
  }
}