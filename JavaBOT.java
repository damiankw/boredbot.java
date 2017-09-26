import java.io.*;
import java.net.*;
import java.util.*;

public class JavaBOT {
  /* Set up the DataStreams: */
  private Socket sck_server = null;
  private PrintWriter sck_out = null;
  private BufferedReader sck_in = null;

  /* Set up local variables: */
  private String botnick = "";

  /* Write data to the socket: */
  public void send_data(String send_data) {
    sck_out.println(send_data);
    System.out.println("<- " + send_data);
  }

  /* Initialization: */
  public static void main(String[] args){
    new JavaBOT();
  }

  /* Create a new socket and control it: */
  public JavaBOT() {
    try {
      // Connect the socket:
      sck_server = new Socket("irc.mncs.info", 6667);

      // Set up the socket Streams:
      sck_out = new PrintWriter(sck_server.getOutputStream(), true);
      sck_in = new BufferedReader(new InputStreamReader(sck_server.getInputStream()));

      // Send data to server for authentication:
      send_data("NICK damibot");
      send_data("USER damibot {} {} :I am Java!");
      send_data("JOIN #Testikles`");

      // Set up the tmpRead to read from the server:
      String read_data;

      // While the socket exists, read it:

      while ((read_data = sck_in.readLine()) != null) {
        if (read_data.substring(1, read_data.indexOf(" ")).equals(this.botnick)) {
          //svr_self(read_data);
        } else if ((read_data.substring(0, read_data.indexOf(" ")).indexOf("!") > 0) && (read_data.substring(0, read_data.indexOf(" ")).indexOf("@") > 0)) {
          svr_user(read_data);
        }
        else {
          svr_server(read_data);
        }
      }
    }
    catch (IOException catchError) {
      System.out.println("& There was an error: " + catchError.toString());
    }
  }

  /* When the server sends a command: */
  public void svr_server(String read_data) {
    ParseServ args = new ParseServ(read_data);


    System.out.println("!" + args.server() + "! #" + args.numeric() + ": " + args.arg(0, args.argSize()));

//    if (args.numeric().equals("PING")) {
//      send_data("PONG :" + args.server());
//    }
     ////! need to make this properly .. need to add in an lrange type method to ParseServ()


//    System.out.println("SERVER: " + read_data);
  }

  /* When a client sends a command:*/
  public void svr_user(String read_data) {
    ParseUser args = new ParseUser(read_data);
    System.out.println("[" + args.nick() + "!" + args.ident() + "@" + args.host() + "] " + args.text());

    if (args.text(0).equals("!die")) {
      send_data("QUIT :" + args.text(1, args.textSize() - 1));
    }
  }
}