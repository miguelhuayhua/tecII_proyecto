package com.miguel.res_dao.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;

public class ConnectMongo {

    private static MongoClient mongoClient;

    public static MongoDatabase getDb() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://invited:invited@sandbox.dcuvf.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings setting = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        mongoClient = MongoClients.create(setting);
        MongoDatabase database = mongoClient.getDatabase("sample_restaurants");
        return database;
    }

    public static void CloseMongoConnection() {
        mongoClient.close();
    }

}
