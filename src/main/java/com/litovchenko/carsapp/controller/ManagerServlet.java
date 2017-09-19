package com.litovchenko.carsapp.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managerServlet")
public class ManagerServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            resp.sendRedirect("/MainPageServlet");
        }
        switch(action){
            case "orders": {
                resp.sendRedirect("/orders");
                break;
            }
            case "checks": {

                break;
            }
        }
    }
}
