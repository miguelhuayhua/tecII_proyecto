/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miguel.res_dao.dao;

import com.miguel.res_dao.utils.ConnectMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.Arrays;
import java.util.List;
import org.bson.BsonNull;
import org.bson.Document;

/**
 *
 * @author miguel
 */
public class queryRestaurantDAOImplement implements queryRestaurantDAo {

    @Override
    public List<String> getCousineGroup() {
        MongoCollection<Document> Restaurants = ConnectMongo.getDb().getCollection("restaurants");
        List<String> cuisines = Restaurants.aggregate(Arrays.asList(new Document("$project",
                new Document("cuisine", 1L)
                        .append("_id", 0L)),
                new Document("$group",
                        new Document("_id",
                                new BsonNull())
                                .append("name",
                                        new Document("$addToSet", "$cuisine"))))).first().getList("name", String.class);
        ConnectMongo.CloseMongoConnection();
        return cuisines;
    }

    @Override
    public MongoCursor<Document> getTopRestaurantTypes() {
        MongoCollection<Document> Restaurants = ConnectMongo.getDb().getCollection("restaurants");
        MongoCursor<Document> mongoCursor = Restaurants.aggregate(Arrays.asList(new Document("$project",
                new Document("_id", 0L)
                        .append("cuisine", 1L)),
                new Document("$group",
                        new Document("_id", "$cuisine")
                                .append("count",
                                        new Document("$count",
                                                new Document()))),
                new Document("$sort",
                        new Document("count", -1L)),
                new Document("$limit", 20L))).iterator();
        ConnectMongo.CloseMongoConnection();

        return mongoCursor;
    }

    @Override
    public MongoCursor<Document> getTopBestCities() {
        MongoCollection<Document> Restaurants = ConnectMongo.getDb().getCollection("restaurants");
        MongoCursor<Document> mongoCursor = Restaurants.aggregate(Arrays.asList(new Document("$project",
                new Document("_id", 0L)
                        .append("borough", 1L)),
                new Document("$group",
                        new Document("_id", "$borough")
                                .append("count",
                                        new Document("$count",
                                                new Document()))),
                new Document("$sort",
                        new Document("count", -1L)),
                new Document("$limit", 3L))).iterator();
        ConnectMongo.CloseMongoConnection();

        return mongoCursor;

    }

}
