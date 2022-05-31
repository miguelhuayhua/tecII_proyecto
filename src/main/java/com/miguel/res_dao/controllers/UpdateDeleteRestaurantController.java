package com.miguel.res_dao.controllers;

import com.miguel.res_dao.dao.queryRestaurantDAOImplement;
import com.miguel.res_dao.dao.resDAOImplement;
import com.miguel.res_dao.model.Restaurant;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;

@WebServlet(name = "UpdateDeleteRestaurantController", urlPatterns = {"/query"})
public class UpdateDeleteRestaurantController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("show", false);
        request.getRequestDispatcher("query.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getParameter("_method"));

        Restaurant restaurant = null;
        request.setAttribute("show", true);
        if (request.getParameter("_method").equals("GET")) {

            String name = request.getParameter("name");
            String id = request.getParameter("restaurantId");

            if (name != null) {
                restaurant = new resDAOImplement().getByIdOrName(null, name);
            } else {
                restaurant = new resDAOImplement().getByIdOrName(id, null);
            }
            System.out.println(new queryRestaurantDAOImplement().getCousineGroup());
            System.out.println("Miguel, arriba");
            request.setAttribute("cuisines", new queryRestaurantDAOImplement().getCousineGroup());
            request.setAttribute("restaurant", restaurant);
            request.getRequestDispatcher("query.jsp").forward(request, response);

        } else if (request.getParameter("_method").equals("PUT")) {
            String name = request.getParameter("name");
            String borough = request.getParameter("borough");
            String cuisine = request.getParameter("cuisine");
            String id = request.getParameter("id");
            String building = request.getParameter("building");
            String street = request.getParameter("street");
            restaurant = new Restaurant();
            Document address = new Document();
            address.append("building", building);
            address.append("street", street);
            restaurant.setAddress(address);
            restaurant.setBorough(borough);
            restaurant.setName(name);
            restaurant.setRestaurant_id(id);
            restaurant.setCuisine(cuisine);
            new resDAOImplement().updateRestaurant(restaurant, id);
            response.sendRedirect("welcome");

        } else if (request.getParameter("_method").equals("DELETE")) {
            System.out.println("ES DELETE");
            new resDAOImplement().deleteRestaurant(request.getParameter("restaurantId"));
            response.sendRedirect("welcome");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
