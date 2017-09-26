import net.nictitate.boredBOT.boredBOT;

public class StartBOT {
  public static void main(String[] args){
    boredBOT bot = new boredBOT("boredBOT", "bored", "I am the first boredBOT-j", "stable.nictitate.net", 6667);
    
    bot.connect();
  }
}