package net.nictitate.boredBOT;

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
  
  public static String lrange(String text, int first) {
    return Util.lrange(text, first, -1);
  }
  
  public static String lrange(String text, int first, int last) {
    String txt[] = text.split(" ");

    if (first >= txt.length) {
      return "";
    }
    
    String output = "";
    int words = 0;
    for (int i = 0; i < txt.length; i++) {
      if ((words >= first) && ((last > 0) || (last == -1))) {
        output += " " + txt[i];
        if ((!txt[i].equals("")) && (last != -1)) {
          last--;
        }
      } else if (txt[i].equals("")) { 
        continue;
      }
      
      words++;
    }
    
    return output.trim();
  }
  
  public static String trimLeft(String text, char chr) {
    if (text.equals("")) {
      return text;
    }
    
    int i = 0;
    while (text.charAt(i) == chr) {
      i++;
    }
    
    return text.substring(i);
  }
  
  public static String trimRight(String text, char chr) {
    if (text.equals("")) {
      return text;
    }
    
    int i = text.length() - 1;
    while (text.charAt(i) == chr) {
      i--;
    }
    
    return text.substring(0, i + 1);
  }
  
  public static String getNickFromAddress(String address) {
    return Util.trimLeft(address, ':').split("!")[0];
  }

  public static String getUserFromAddress(String address) {
    return address.split("!")[1].split("@")[0];
  }

  public static String getHostFromAddress(String address) {
    return address.split("@")[1];
  }

}












