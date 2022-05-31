package com.miguel.res_dao.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import com.miguel.res_dao.dao.userDAOImplement;
import com.miguel.res_dao.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie auth = new Cookie("auth", "");
        auth.setPath("/");
        auth.setMaxAge(0);
        response.addCookie(auth);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            User user = new userDAOImplement().getUser(username, password);

            if (user == null) {
                response.sendRedirect("/res_management");
            } else {
                Cookie auth = new Cookie("auth", user.getId());
                Cookie cookieName = new Cookie("username", user.getUsername());
                auth.setPath("/");
                auth.setMaxAge(3600);
                cookieName.setMaxAge(3600);
                cookieName.setPath("/");
                response.addCookie(auth);
                response.addCookie(cookieName);
                response.sendRedirect("welcome");
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
