package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.service.CarsService;
import com.litovchenko.carsapp.service.UsersServise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action;
        if(req.getParameter("action") == null){
            action = (String)req.getSession().getAttribute("action");
        } else {
            action = req.getParameter("action");
        }
        switch (action){
            case "cars": {
                req.setAttribute("cars", CarsService.getList());
                req.getRequestDispatcher("AdminCars.jsp").forward(req, resp);
                break;
            }
            case "users": {
                req.setAttribute("users", UsersServise.getCustomers());
                req.getRequestDispatcher("AdminUsers.jsp").forward(req, resp);
                break;
            }
            case "managers": {
                req.setAttribute("managers", UsersServise.getManagers());
                req.getRequestDispatcher("AdminManagers.jsp").forward(req, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
