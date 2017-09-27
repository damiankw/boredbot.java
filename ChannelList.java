package net.nictitate.boredBOT;

import java.util.*;

public class ChannelList {
  /* The array to hold the list of Channels: */
  private ArrayList<Channel> channels = new ArrayList<Channel>();

  /* Default Constructor: */
  public ChannelList() { }

  /* To add a new Channel: */
  public void add(String chan) {
    if (this.get(chan) == null) {
      this.channels.add(new Channel(chan));
    }
  }

  public void add(Channel chan) {
    if (this.get(chan.name()) == null) {
      this.channels.add(chan);
    }
  }

  /* To remove an old Channel: */
  public void del(String chan) {
    for (int i = this.channels.size(); i > 0; i--) {
      Channel tmpChan = (Channel) this.channels.get(i - 1);
      if (tmpChan.name().equalsIgnoreCase(chan)) {
        this.channels.remove(i - 1);
      }
    }
  }
  
  /* To find a Channel: */
  public Channel get(String chan) {
    for (int i = 0; i < this.channels.size(); i++) {
      Channel tmpChan = (Channel) this.channels.get(i);
      if (tmpChan.name().equalsIgnoreCase(chan)) {
        return tmpChan;
      }
    }

    return null;
  }

  public void update(Channel chan) {
    this.del(chan.name());
    
    this.add(chan);
  }
  

  /* To list all Channels as an Array: */
  public Channel[] list() {
    Channel chan[] = new Channel[this.channels.size()];
    for (int i = 0; i < this.channels.size(); i++) {
      chan[i] = (Channel) this.channels.get(i);
    }

    return chan;
  }

  public int total() {
    return this.channels.size();
  }
  
  /* To list all Users as a String: */
  public String toString() {
    String tmpReturn = "";
    for (int i = 0; i < this.channels.size(); i++) {
      Channel tmpChan = (Channel) this.channels.get(i);
      tmpReturn += " " + tmpChan.name();
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }
}