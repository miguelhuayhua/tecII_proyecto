/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.miguel.res_dao.controllers;

import com.miguel.res_dao.dao.queryRestaurantDAOImplement;
import com.miguel.res_dao.dao.resDAOImplement;
import com.miguel.res_dao.model.Restaurant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;

@WebServlet(name = "AddRestaurantController", urlPatterns = {"/addrestaurant"})
public class AddRestaurantController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("cuisines", new queryRestaurantDAOImplement().getCousineGroup());
        request.getRequestDispatcher(
                "add.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Restaurant res = new Restaurant();
        String cuisine = request.getParameter("cuisine");
        String name = request.getParameter("name");
        String borough = request.getParameter("borough");
        String building = request.getParameter("building");
        String street = request.getParameter("street");
        res.setName(name);
        res.setBorough(borough);
        res.setCuisine(cuisine);
        Document address = new Document("street", street);
        address.append("building", building);
        res.setAddress(address);
        new resDAOImplement().insertRestaurant(res);
        response.sendRedirect("welcome");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
