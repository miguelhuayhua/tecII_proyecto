/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.miguel.res_dao.dao;

import com.miguel.res_dao.model.User;
import org.bson.Document;

/**
 *
 * @author miguel
 */
public interface userDAO {

    public User getUser(String username, String password);
}
