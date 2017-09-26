public class Util {
  public static String lindex(String text, int word) {
    String txt[] = text.split(" ");

    if (word >= txt.length) {
      return "";
    }
    
    // count the actual words (not spaces)
    int words = 0;
    for (int i = 0; i < txt.length; i++) {
      if (txt[i].equals("")) {
        continue;
      } else if (words == word) {
        return txt[i];
      }
      
      words++;
    }
    
    return "n/a";
  }
  
  public static String lrange(String text, int first, int last) {
    String txt[] = text.split(" ");

    if (first >= txt.length) {
      return "";
    }
    
    String output = "";
    int words = 0;
    for (int i = 0; i < txt.length; i++) {
      if ((words >= first) && (last > 0)) {
        output += " " + txt[i];
        if (!txt[i].equals("")) {
          last--;
        }
      } else if (txt[i].equals("")) { 
        continue;
      }
      
      words++;
    }
    
    return output.trim();
  }
}