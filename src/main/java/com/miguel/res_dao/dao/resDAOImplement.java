package com.miguel.res_dao.dao;

import com.miguel.res_dao.model.Restaurant;
import java.util.List;
import com.miguel.res_dao.utils.ConnectMongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

public class resDAOImplement implements restaurantDAO {

    @Override
    public void insertRestaurant(Restaurant r) {
        MongoCollection<Document> Listings = ConnectMongo.getDb().getCollection("restaurants");
        String id = Listings.aggregate(Arrays.asList(new Document("$project",
                new Document("restaurant_id", 1L)
                        .append("_id", 0L)),
                new Document("$sort",
                        new Document("restaurant_id", -1L)),
                new Document("$limit", 1L))).first().getString("restaurant_id");
        Document newDocument = new Document();
        newDocument.append("name", r.getName());
        newDocument.append("restaurant_id", "" + (Integer.parseInt(id) + 1));
        newDocument.append("borough", r.getBorough());
        newDocument.append("cuisine", r.getCuisine());
        newDocument.append("address", r.getAddress());
        Listings.insertOne(newDocument);
        ConnectMongo.CloseMongoConnection();
    }

    @Override
    public void updateRestaurant(Restaurant r, String id) {
        MongoCollection<Document> Listings = ConnectMongo.getDb().getCollection("restaurants");
        Document query = new Document();
        query.append("restaurant_id", id);
        Bson updates = Updates.combine(Updates.set("name", r.getName()),
                Updates.set("cuisine", r.getCuisine()),
                Updates.set("borough", r.getBorough()),
                Updates.set("address", r.getAddress()));
        System.out.println(updates);
        Listings.updateOne(Filters.eq("restaurant_id", id), updates);
        ConnectMongo.CloseMongoConnection();
    }

    @Override
    public void deleteRestaurant(String id) {

        MongoCollection<Document> Restaurant = ConnectMongo.getDb().getCollection("restaurants");
        Document filter = new Document();
        filter.append("restaurant_id", id);
        Restaurant.findOneAndDelete(filter);
        ConnectMongo.CloseMongoConnection();
    }

    @Override
    public List<Restaurant> getAll() {
        List<Restaurant> list = new ArrayList<Restaurant>();
        MongoCollection<Document> Listings = ConnectMongo.getDb().getCollection("restaurants");
        MongoCursor<Document> it = Listings.aggregate(Arrays.asList(
                new Document("$sort", new Document("restaurant_id", -1L)), new Document("$project",
                        new Document("restaurant_id", 1L)
                                .append("name", 1L)
                                .append("borough", 1L)
                                .append("cuisine", 1L)
                                .append("_id", 0L)),
                new Document("$match",
                        new Document("name",
                                new Document("$ne", ""))),
                new Document("$limit", 20L))).cursor();
        while (it.hasNext()) {
//we iterate the document after mongodb collection request
            Document restaurant = it.next();
            // Document address = restaurant.get("address", Document.class);

//and obtain the values and set them in specified variables
            String borough = restaurant.getString("borough");
            String cuisine = restaurant.getString("cuisine");
            //List<Document> grades = restaurant.getList("grades", Document.class);
            String name = restaurant.getString("name");
            String restaurant_id = restaurant.getString("restaurant_id");
//restaurant object created with the given variables
            Restaurant res = new Restaurant(null, borough, cuisine, null, name, restaurant_id);
//put object within our list 
            list.add(res);
        }
        ConnectMongo.CloseMongoConnection();
        return list;
    }

    @Override
    public Restaurant getByIdOrName(String id, String name) {
        MongoCollection<Document> Listings = ConnectMongo.getDb().getCollection("restaurants");

        Restaurant restaurant = new Restaurant();
        if (id != null) {
            Document doc = Listings.aggregate(Arrays.asList(new Document("$match",
                    new Document("restaurant_id",
                            new Document("$regex", id)
                    )),
                    new Document("$limit", 1L))).first();
            restaurant.setAddress(doc.get("address", Document.class));
            restaurant.setBorough(doc.getString("borough"));
            restaurant.setCuisine(doc.getString("cuisine"));
            restaurant.setName(doc.getString("name"));
            restaurant.setRestaurant_id(doc.getString("restaurant_id"));
        } else {
            Document doc = Listings.aggregate(Arrays.asList(new Document("$match",
                    new Document("name",
                            new Document("$regex", name)
                                    .append("$options", "i"))),
                    new Document("$limit", 1L))).first();
            restaurant.setAddress(doc.get("address", Document.class));
            restaurant.setBorough(doc.getString("borough"));
            restaurant.setCuisine(doc.getString("cuisine"));
            restaurant.setName(doc.getString("name"));
            restaurant.setRestaurant_id(doc.getString("restaurant_id"));
        }
        ConnectMongo.CloseMongoConnection();
        return restaurant;
    }

    @Override
    public int getRestaurantCount() {
        MongoCollection<Document> Listings = ConnectMongo.getDb().getCollection("restaurants");
        Document result = Listings.aggregate(Arrays.asList(new Document("$count", "count"))).first();
        ConnectMongo.CloseMongoConnection();
        return result.getInteger("count");
    }
}
