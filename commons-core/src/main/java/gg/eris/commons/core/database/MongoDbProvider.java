package gg.eris.commons.core.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MongoDbProvider {

  public static MongoDatabase newClient(MongoCredentials credentials) {
    ConnectionString connectionString = new ConnectionString(
        "mongodb+srv://"
            + credentials.getUsername() + ":" + credentials.getPassword()
            + "@" + credentials.getHostname()
    );

    return getDatabase(newClient(MongoClientSettings.builder()
        .applyToSocketSettings(builder -> {
          builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
          builder.readTimeout(3000, TimeUnit.MILLISECONDS);
        })
        .applyToClusterSettings(builder -> {
          builder.serverSelectionTimeout(3000, TimeUnit.MILLISECONDS);
        })
        .applyConnectionString(connectionString)
        .retryWrites(true)
        .writeConcern(WriteConcern.MAJORITY)

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
