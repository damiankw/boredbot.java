class Setting {
  private String key, value;
  
  public Setting(String key, String value) {
    this.key = key;
    this.value = value;
  }
  
  public String key() {
    return this.key;
  }
  
  public String value() {
    return this.value;
  }
  
  public String toString() {
    return this.key + "=" + this.value;
  }
}