/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miguel.res_dao.dao;

import com.miguel.res_dao.model.User;
import com.miguel.res_dao.utils.ConnectMongo;
import com.mongodb.client.MongoCollection;
import java.util.Arrays;
import org.bson.Document;
import org.bson.types.ObjectId;

public class userDAOImplement implements userDAO {

    @Override
    public User getUser(String username, String password) {
        MongoCollection<Document> Users = ConnectMongo.getDb().getCollection("users");
        Document doc = Users.aggregate(Arrays.asList(new Document("$match",
                new Document("username", username)
                        .append("password", password)))).first();
        User user = null;
        if (doc != null) {
            user = new User(doc.getObjectId("_id").toString(), doc.getString("username"), doc.getString("password"));
        }
        return user;
    }

}
