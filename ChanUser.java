/* Private Class for a Channel User: */
public class ChanUser {
  /* Define the Variables needed: */
  private String nickname, mode;

  /* The Default Constructor: */
  public ChanUser(String nickname, String mode) {
    this.nickname = nickname;
    this.mode = mode;
  }

  /* To return the Nickname: */
  public String nick() {
    return this.nickname;
  }

  /* To return the Mode: */
  public String mode() {
    return this.mode;
  }
}