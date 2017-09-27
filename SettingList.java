package net.nictitate.boredBOT;

import java.util.*;

class SettingList extends ArrayList<Setting> {
  // retrieve the entire Setting based on a search by key
  public Setting find(String key) {
    for (int i = 0; i < super.size(); i++) {
      Setting setting = (Setting) super.get(i);
      if (setting.key().equalsIgnoreCase(key)) {
        return setting;
      }
    }

    return null;
  }
  
  // retrieve just the value of the Setting based on a search by key
  public String get(String key) {
    Setting setting = this.find(key);
    
    return setting.value();
  }

  // add in a new setting
  public void add(String key, String value) {
    // delete the old key/value
    this.del(key);
    
    // add the new key/value
    super.add(new Setting(key, value));
  }
  
  public void del(String key) {
    // search for the Setting by key
    Setting setting = this.find(key);
    
    // remove from the ArrayList
    super.remove(setting);
  }
}