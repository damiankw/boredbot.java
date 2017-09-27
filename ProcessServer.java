package net.nictitate.boredBOT;

public class ProcessServer {
  private boredBOT client;
  
  public ProcessServer(boredBOT client) {
    this.client = client;
  }
  
  public void process(String data) {
    System.out.println("<- " + data);
    
    if (data.length() == 0) {
      // just some santising, in case nothing comes through
      return;
    } else if (data.substring(0, 1).equals(":")) {
      data = data.substring(1, data.length());
      
      if (Util.lindex(data, 1).equals("001")) {
        svr_001();
        
      } else if (Util.lindex(data, 1).equals("004")) {
        svr_004(Util.lindex(data, 3));
        
      } else if (Util.lindex(data, 1).equals("005")) {
        for (String word : Util.lrange(data, 3, -1).split(" ")) {
          String setting[];
          if (word.indexOf("=") == -1) {
            setting = (word + "=1").split("=");
          } else {
            setting = word.split("=");
          }
          
          //client.settings.add(new Setting(setting[0], setting[1]));
          // put this on the backburner, it's a list of the settings from the server
        }
      } else if (Util.lindex(data, 1).equals("324")) {
        svr_324(Util.lindex(data, 3), Util.lindex(data, 4));

      } else if (Util.lindex(data, 1).equals("329")) {
        svr_329(Util.lindex(data, 3), Long.parseLong(Util.lindex(data, 4)));

      } else if (Util.lindex(data, 1).equals("332")) {
        svr_332(Util.lindex(data, 3), Util.lrange(data, 4, -1).substring(1));

      } else if (Util.lindex(data, 1).equals("333")) {
        svr_333(Util.lindex(data, 3), Util.lindex(data, 4), Long.parseLong(Util.lindex(data, 5)));

      } else if (Util.lindex(data, 1).equals("352")) {
        // :stable.nictitate.net 352 boredBOT #nictitate damian local.damian.id.au stable.nictitate.net damian H :0 damian
        svr_352(Util.lindex(data, 3), Util.lindex(data, 7), Util.lindex(data, 4), Util.lindex(data, 5));
      
      } else if (Util.lindex(data, 1).equals("433")) {
        svr_443();
        
      }
    }
  }
  
  // initial confirmed connection to the server
  public void svr_001() {
    client.send("WHO " + client.getEnv("botnick"));
    client.send("JOIN #nictitate");        
  }
  
  // the name of the server you're connected to
  public void svr_004(String server) {
    client.setEnv("server", server);
  }
 
  public void svr_324(String chan, String mode) {
    client.getChannel(chan).setMode(mode);
  }

  public void svr_329(String chan, long created) {
    client.getChannel(chan).setCreated(created);
  }
  
  public void svr_332(String chan, String topic) {
    client.getChannel(chan).setTopic(topic);
  }
  
  public void svr_333(String chan, String nick, long date) {
    client.getChannel(chan).setTopicBy(nick);
    client.getChannel(chan).setTopicWhen(date);

  }
  
  public void svr_352(String chan, String nick, String user, String host) {
    if (client.getUser(nick) == null) {
      client.addUser(nick, user, host);
    } else {
      client.getUser(nick).setNick(nick);
      client.getUser(nick).setUser(user);
      client.getUser(nick).setHost(host);
    }

    if (client.getChannel(chan) != null) {
      client.getChannel(chan).addUser(nick);
    }

    if (client.getEnv("botnick").equals(nick)) {
      client.setEnv("botuser", user);
      client.setEnv("bothost", host);
    }

        // also add user to channel
  }

  // if the nickname you're changing to is already taken
  // *** NOTE: this needs to be updated for nick changes later on
  public void svr_443() {
    int rand = 10 + (int)(Math.random() * 99); 
    client.setEnv("botnick", client.getConf("nick") + rand);
    client.send("NICK " + client.getConf("nick") + rand);
  }
}