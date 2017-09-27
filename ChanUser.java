package net.nictitate.boredBOT;

import java.util.*;

/* Private Class for a Channel User: */
public class ChanUser {
  /* Define the Variables needed: */
  private String nick, mode;
  private long joined;

  /* The Default Constructor: */
  public ChanUser(String nick, String mode) {
    Date date = new Date();
    this.nick = nick;
    this.mode = mode;
    
    this.joined = date.getTime() / 1000;
  }

  /* To return the Nickname: */
  public String nick() {
    return this.nick;
  }

  /* To return the Mode: */
  public String mode() {
    return this.mode;
  }
  
  public long joined() {
    return this.joined;
  }
  
  public void setNick(String nick) {
    this.nick = nick;
  }
  
  public void setMode(String mode) {
    this.mode = mode;
  }
}