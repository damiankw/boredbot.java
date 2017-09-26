public class User {
  /* Define the working Variables: */
  private String nick, user, host;

  // create a new user
  public User() {
    this.nick = "";
    this.user = "";
    this.host = "";
  }
  
  public User(String nick) {
    this.nick = nick;
    this.user = "";
    this.host = "";
  }
  
  public User(String nick, String user, String host) {
    this.nick = nick;
    this.user = user;
    this.host = host;
  }

  // update nickname
  public void setNick(String nick) {
    this.nick = nick;
  }

  // update the username
  public void setUser(String user) {
    this.user = user;
  }

  // update the hostname
  public void setHost(String host) {
    this.host = host;
  }

  // get the nickname
  public String nick() {
    return this.nick;
  }

  // get the username
  public String user() {
    return this.user;
  }

  // get the hostname
  public String host() {
    return this.host;
  }

  // get the full address
  public String address() {
    return this.nick + "!" + this.user + "@" + this.host;
  }

  // output a friendly string
  public String toString() {
    return this.nick + ", " + this.user + ", " + this.host;
  }
}