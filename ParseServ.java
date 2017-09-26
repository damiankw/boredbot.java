//:apollo.ny.us.austnet.org 377 damibot :
//:apollo.ny.us.austnet.org NOTICE damibot :Highest connection count: 707 (706 clients)
//:apollo.ny.us.austnet.org 353 damibot = #Testikles` :damibot BlueWish[a] damian
//:apollo.ny.us.austnet.org 366 damibot #Testikles` :End of /NAMES list.
//NOTICE damibot :*** Your host is apollo.ny.us.austnet.org, running version austhex.servd7.5.pre13.dbuf.ghash.sec3rc1

import java.util.*;
import java.lang.String;

public class ParseServ {
  private String server, numeric, destination;
  private int type;
  private String args[];

  public ParseServ(String read_data) {
    List<String> arg = new ArrayList<String>();
    // stupid AustNet bug, for some reason it sends blank lines
    if (read_data.length() == 0) {
      read_data = "  ";
    }
    String arrRead_data[] = read_data.substring(1, read_data.length()).split(" ");
    String tmpLast = "";
    boolean isLast = false;

    for (int i = 0; i < arrRead_data.length; i++) {
      if (arrRead_data[i].startsWith(":")) {
        isLast = true;
        tmpLast += arrRead_data[i].substring(1, arrRead_data[i].length());
      } else if (isLast) {
        tmpLast += " " + arrRead_data[i];
      } else {
        arg.add(arrRead_data[i]);
      }
    }

    arg.add(tmpLast);

    /* Check to see if the autoVariables will bugger up*/
    if (arg.size() > 3) {
      this.type = 1;
      this.server = (String) arg.get(0);
      this.numeric = (String) arg.get(1);
      this.destination = (String) arg.get(2);
      this.args = new String[arg.size() - 3];
      for (int i = 0; i < arg.size() - 3; i++) {
        this.args[i] = (String) arg.get(i + 3);
      }
    } else {
    // if they will .. arg all
      this.type = 2;
      this.args = new String[arg.size()];
      for (int i = 0; i < arg.size(); i++) {
        this.args[i] = (String) arg.get(i);
      }
      
      this.args[0] = read_data.substring(0, 1) + args[0];
    }
  }

  public String server() {
    return this.server;
  }

  public String numeric() {
    return this.numeric;
  }

  public int type() {
    return this.type;
  }

  public String dest() {
    return this.destination;
  }

  public String arg(int argument) {
    return this.args[argument];
  }

  public int size() {
    return this.args.length;
  }
  
  public String text() {
    return this.arg(0, this.args.length);
  }

  public String arg(int first, int last) {
    String tmpReturn = "";
    for (int i = first; i < last; i++) {
      tmpReturn += " " + this.args[i];
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }
}