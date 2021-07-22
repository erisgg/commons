package gg.eris.commons.core.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MongoDbProvider {

  public static MongoDatabase newClient(MongoCredentials credentials) {
    MongoCredential credential = MongoCredential.createCredential(
        credentials.getUsername(),
        credentials.getDatabase(),
        credentials.getPassword().toCharArray()
    );

    return getDatabase(newClient(MongoClientSettings.builder()
        .credential(credential)
        .applyToSocketSettings(builder -> {
          builder.connectTimeout(1000, TimeUnit.MILLISECONDS);
          builder.readTimeout(10, TimeUnit.MILLISECONDS);
        })
        .applyToClusterSettings(builder -> {
          builder.hosts(Collections.singletonList(
              new ServerAddress(credentials.getHostname(), credentials.getPort())));
          builder.serverSelectionTimeout(1000, TimeUnit.MILLISECONDS);
        })

        .build()
    ), credentials.getDatabase());
  }

  public static MongoDatabase getDatabase(MongoClient client, String database) {
    return client.getDatabase(database);
  }

  public static MongoClient newClient(MongoClientSettings settings) {
    return MongoClients.create(settings);
  }

}
