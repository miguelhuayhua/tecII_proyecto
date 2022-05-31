package com.miguel.res_dao.dao;

import com.miguel.res_dao.model.Restaurant;
import java.util.List;

public interface restaurantDAO {

    public void insertRestaurant(Restaurant r);

    public void updateRestaurant(Restaurant r, String id);

    public void deleteRestaurant(String id);

    public List<Restaurant> getAll();

    public Restaurant getByIdOrName(String id, String name);

    public int getRestaurantCount();
}
