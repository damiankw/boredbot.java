public class User {
  /* Define the working Variables: */
  private String nickname, username, hostname, information;

  /* Default Container */
  public User(String nickname, String username, String hostname) {
    new User(nickname, username, hostname, "");
  }

  /* Default Container */
  public User(String nickname, String username, String hostname, String information) {
    this.nickname = nickname;
    this.username = username;
    this.hostname = hostname;
    this.information = information;
  }

  /* To set the NickName after creation: */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /* To set the UserName after creation: */
  public void setUsername(String username) {
    this.username = username;
  }

  /* To set the HostName after creation: */
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  /* To set the Information after creation: */
  public void setInformation(String information) {
    this.information = information;
  }

  /* To return the NickName: */
  public String nick() {
    return this.nickname;
  }

  /* To return the UserName: */
  public String user() {
    return this.username;
  }

  /* To return the HostName: */
  public String host() {
    return this.hostname;
  }

  /* To return the Full Address: */
  public String address() {
    return this.nickname + "!" + this.username + "@" + this.hostname;
  }

  /* To return the Information: */
  public String info() {
    return this.information;
  }

  /* Print a short information string: */
  public String toString() {
    return this.nickname + ", " + this.username + ", " + this.hostname + ", " + this.information;
  }
}