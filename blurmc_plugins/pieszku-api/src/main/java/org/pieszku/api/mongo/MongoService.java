package org.pieszku.api.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoService {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    public MongoService(){
        String[] collections = new String[]{"users", "guilds", "kits", "bans", "mutes", "backups"};
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb://root:pswd%21%40rdd@51.83.150.215:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false"));
        this.mongoDatabase = mongoClient.getDatabase("blurmc");
        for (String collection : collections) {
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}
