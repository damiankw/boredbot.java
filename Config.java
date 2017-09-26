package net.nictitate.boredBOT;

public class Config {
  private String nick, user, name, server;
  private int port;
  
  public Config(String nick, String user, String name, String server, int port) {
    this.nick = nick;
    this.user = user;
    this.name = name;
    this.server = server;
    this.port = port;
  }
  
  public String nick() {
    return this.nick;
  }
  
  public String user() {
    return this.user;
  }
  
  public String name() {
    return this.name;
  }
  
  public String server() {
    return this.server;
  }
  
  public int port() {
    return this.port;
  }
}