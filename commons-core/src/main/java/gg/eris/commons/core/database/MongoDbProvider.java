package gg.eris.commons.core.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.Collections;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MongoDbProvider {

  public static MongoDatabase newClient(String host, int port, String database, String username,
      String password) {
    MongoCredential credential = MongoCredential
        .createCredential(username, database, password.toCharArray());
    return getDatabase(newClient(MongoClientSettings.builder()
        .credential(credential)
        .applyToClusterSettings(builder ->
            builder.hosts(Collections.singletonList(new ServerAddress(host, port))))
        .build()
    ), database);
  }

  public static MongoDatabase getDatabase(MongoClient client, String database) {
    return client.getDatabase(database);
  }

  public static MongoClient newClient(MongoClientSettings settings) {
    return MongoClients.create(settings);
  }

}
