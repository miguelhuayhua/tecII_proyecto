package com.miguel.res_dao.model;

import java.util.List;
import org.bson.Document;

public class Restaurant {

    private Document address;
    private String borough;
    private String cuisine;
    private List<Document> grades;
    private String name;
    private String restaurant_id;

    public Restaurant() {
    }

    public Restaurant(Document address, String borough, String cuisine, List<Document> grades, String name, String restaurant_id) {
        this.address = address;
        this.borough = borough;
        this.cuisine = cuisine;
        this.grades = grades;
        this.name = name;
        this.restaurant_id = restaurant_id;
    }

    public Document getAddress() {
        return address;
    }

    public void setAddress(Document address) {
        this.address = address;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public List<Document> getGrades() {
        return grades;
    }

    public void setGrades(List<Document> grades) {
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
