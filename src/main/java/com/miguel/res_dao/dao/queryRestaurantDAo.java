package com.miguel.res_dao.dao;

import com.mongodb.client.MongoCursor;
import java.util.List;
import org.bson.Document;

public interface queryRestaurantDAo {

    public List<String> getCousineGroup();

    public MongoCursor<Document> getTopRestaurantTypes();

    public MongoCursor<Document> getTopBestCities();
}
