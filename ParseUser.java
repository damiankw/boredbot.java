import java.util.*;
import java.lang.String;

public class ParseUser {
  // Difine the Variables needed;
  private String from, nick, ident, host, cmd, chan, text;

  // Default constructor when data is passed
  public ParseUser(String read_data) {
    // Create an Array to work from and processing Variables
    List arg = new ArrayList();
    String arrRead_data[] = read_data.substring(1, read_data.length()).split(" ");
    String tmpLast = "";
    boolean isLast = false;

    // Go through all of the words in the line
    for (int i = 0; i < arrRead_data.length; i++) {
      // If the word starts with ':' set it up to be the last argument
      if (arrRead_data[i].startsWith(":")) {
        isLast = true;
        tmpLast += " " + arrRead_data[i].substring(1, arrRead_data[i].length());
      // If the word is in the last set, add it to the Last variable
      } else if (isLast) {
        tmpLast += " " + arrRead_data[i];
      // Add the argument to the set
      } else {
        arg.add(arrRead_data[i]);
      }
    }
    arg.add(tmpLast);

    this.from = (String) arg.get(0);
    this.cmd = (String) arg.get(1);
    this.chan = (String) arg.get(2);
    this.text = (String) arg.get(arg.size() - 1);
    if (this.text.length() > 0) {
      this.text = this.text.substring(1, this.text.length());
    }
    if (arg.get(0).toString().indexOf("!") != -1) {
      this.nick = (String) this.from.substring(0, this.from.indexOf("!"));
      this.ident = (String) this.from.substring(this.from.indexOf("!") + 1, this.from.indexOf("@"));
      this.host = (String) this.from.substring(this.from.indexOf("@") + 1, this.from.length());
    }
  }

  public String nick() {
    return this.nick;
  }

  public String ident() {
    return this.ident;
  }

  public String host() {
    return this.host;
  }

  public String cmd() {
    return this.cmd;
  }

  public String text() {
    return this.text;
  }

  public String text(int arg) {
    String tmpText[] = this.text.split(" ");
    return tmpText[arg];
  }

  public String text(int first, int last) {
    String tmpText[] = this.text.split(" ");
    String tmpReturn = "";
    for (int i = first; i <= last; i++) {
      tmpReturn += " " + tmpText[i];
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }

  public int textSize() {
    String tmpText[] = this.text.split(" ");
    return tmpText.length;
  }
}