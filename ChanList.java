package net.nictitate.boredBOT;

import java.util.*;

public class ChanList {
  /* The array to hold the list of Channels: */
  private ArrayList<Channel> chans = new ArrayList<Channel>();

  /* Default Constructor: */
  public ChanList() { }

  /* To add a new Channel: */
  public void add(String chan) {
    if (this.find(chan) == null) {
      this.chans.add(new Channel(chan));
    }
  }

  public void add(Channel chan) {
    if (this.find(chan.name()) == null) {
      this.chans.add(chan);
    }
  }

  /* To remove an old Channel: */
  public void del(String chan) {
    for (int i = this.chans.size(); i > 0; i--) {
      Channel tmpChan = (Channel) this.chans.get(i - 1);
      if (tmpChan.name().equalsIgnoreCase(chan)) {
        this.chans.remove(i - 1);
      }
    }
  }

  /* To find a Channel: */
  public Channel find(String chan) {
    for (int i = 0; i < this.chans.size(); i++) {
      Channel tmpChan = (Channel) this.chans.get(i);
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
    Channel chan[] = new Channel[this.chans.size()];
    for (int i = 0; i < this.chans.size(); i++) {
      chan[i] = (Channel) this.chans.get(i);
    }

    return chan;
  }

  public int total() {
    return this.chans.size();
  }
  
  /* To list all Users as a String: */
  public String toString() {
    String tmpReturn = "";
    for (int i = 0; i < this.chans.size(); i++) {
      Channel tmpChan = (Channel) this.chans.get(i);
      tmpReturn += " " + tmpChan.name();
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }
}