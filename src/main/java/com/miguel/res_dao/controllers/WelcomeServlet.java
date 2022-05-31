package com.miguel.res_dao.controllers;

import com.miguel.res_dao.dao.queryRestaurantDAOImplement;
import com.miguel.res_dao.dao.resDAOImplement;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "WelcomeServlet", urlPatterns = {"/welcome"})

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cookie> Cookies = Arrays.asList(request.getCookies());
        for (Cookie c : Cookies) {
            if (c.getName().equals("auth") && !c.getValue().equals("")) {
                request.setAttribute("res_list", new resDAOImplement().getAll());
                request.setAttribute("countRes", new resDAOImplement().getRestaurantCount());
                request.setAttribute("topRestaurantTypes", new queryRestaurantDAOImplement().getTopRestaurantTypes());
                request.setAttribute("topRestaurants", new queryRestaurantDAOImplement().getTopBestCities());
                request.getRequestDispatcher("welcome.jsp").forward(request, response);
                break;
            }
        }
        response.sendRedirect("/res_management");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
