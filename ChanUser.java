/* Private Class for a Channel User: */
public class ChanUser {
  /* Define the Variables needed: */
  private String nick, mode;

  /* The Default Constructor: */
  public ChanUser(String nick, String mode) {
    this.nick = nick;
    this.mode = mode;
  }

  /* To return the Nickname: */
  public String nick() {
    return this.nick;
  }

  /* To return the Mode: */
  public String mode() {
    return this.mode;
  }
}