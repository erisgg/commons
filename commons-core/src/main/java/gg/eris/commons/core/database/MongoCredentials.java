package gg.eris.commons.core.database;

import lombok.Value;

@Value
public class MongoCredentials {

  String username;
  String password;
  String database;
  String hostname;
  int port;

}
