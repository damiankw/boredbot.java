/// need to make shit to UPDATE, you idiot.


import java.util.*;

public class UserList {
  /* The array to hold the list of Users: */
  private List UserList = new ArrayList();

  /* Default Constructor: */
  public UserList() { }

  /* To add a new User (without Information): */
  public void add(String nickname, String username, String hostname) {
    this.add(nickname, username, hostname, "");
  }

  /* To add a new User (with Information): */
  public void add(String nickname, String username, String hostname, String information) {
    if (this.find(nickname) == null) {
      this.UserList.add(new User(nickname, username, hostname, information));
    }
  }

  /* To remove an old User: */
  public void del(String nickname) {
    for (int i = this.UserList.size(); i > 0; i--) {
      User tmpUser = (User) this.UserList.get(i - 1);
      if (tmpUser.nick().equalsIgnoreCase(nickname)) {
        this.UserList.remove(i - 1);
      }
    }
  }

  /* To find a User: */
  public User find(String nickname) {
    for (int i = 0; i < this.UserList.size(); i++) {
      User tmpUser = (User) this.UserList.get(i);
      if (tmpUser.nick().equalsIgnoreCase(nickname)) {
        return tmpUser;
      }
    }

    return null;
  }

  /* To list all Users as an Array: */
  public User[] list() {
    User user[] = new User[this.UserList.size()];
    for (int i = 0; i < this.UserList.size(); i++) {
      user[i] = (User) this.UserList.get(i);
    }

    return user;
  }

  /* To list all Users as a String: */
  public String toString() {
    String tmpReturn = "";
    for (int i = 0; i < this.UserList.size(); i++) {
      User tmpUser = (User) this.UserList.get(i);
      tmpReturn += " " + tmpUser.nick();
    }

    return tmpReturn.substring(1, tmpReturn.length());
  }



////////////////////////////////////////////////////////////////////////////////
////////////////////// testing /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
  public static void main(String args[]) {
    UserList u = new UserList();
    u.add("damian", "west", "local.damian.id.au");
    u.add("dbm", "dbm", "pc1.deadbodyman.net");
    u.add("PsyfiN", "PsyFiN", "home.psyfin.net");

    u.del("dbm");

    System.out.println(u.find("damian").address());
  }
}