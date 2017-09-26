import java.util.*;

public class UserList {
  /* The array to hold the list of Users: */
  private ArrayList<User> UserList = new ArrayList<User>();

  /* Default Constructor: */
  public UserList() { }

  // add a user by object
  public void add(User user) {
    this.UserList.add(user);
  }

  // add a user by string
  public void add(String nickname, String username, String hostname) {
    this.UserList.add(new User(nickname, username, hostname));
  }
  
  // delete a user by nickname
  public void del(String nickname) {
    // loop through all users
    for (int i = this.UserList.size(); i > 0; i--) {
      // check if the user matches
      User user = (User) this.UserList.get(i - 1);
      if (user.nick().equalsIgnoreCase(nickname)) {
        this.UserList.remove(i - 1);
      }
    }
  }

  // update information in a user
  public void update(User user) {
    // delete the user
    this.del(user.nick());
    
    // add the user back
    this.add(user);
  }
  
  // search for a user in the list by nickname
  public User find(String nickname) {
    // loop through all users
    for (int i = 0; i < this.UserList.size(); i++) {
      // check if the user exists
      User user = (User) this.UserList.get(i);
      if (user.nick().equalsIgnoreCase(nickname)) {
        return user;
      }
    }

    return null;
  }

  // output an array of users
  public User[] list() {
    User user[] = new User[this.UserList.size()];
    for (int i = 0; i < this.UserList.size(); i++) {
      user[i] = (User) this.UserList.get(i);
    }

    return user;
  }

  public int total() {
    return this.UserList.size();
  }
  
  // output all users as a string
  public String toString() {
    String users = "";
    for (int i = 0; i < this.UserList.size(); i++) {
      User user = (User) this.UserList.get(i);
      users += " " + user.nick();
    }

    return users.substring(1, users.length());
  }
}