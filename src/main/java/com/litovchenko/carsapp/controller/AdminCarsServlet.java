package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.service.CarsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/allCars")
public class AdminCarsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("action") != null) {
            if (req.getParameter("action").startsWith("edit")) {
                req.setAttribute("action", "edit");
                req.setAttribute("carId", req.getParameter("action").split("_")[1]);
                req.getRequestDispatcher("/carForm").forward(req, resp);
            } else if ("newCar".equals(req.getParameter("action"))) {
                req.getRequestDispatcher("/carForm").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("action") != null) {
            if (req.getParameter("action").startsWith("delete")) {
                if (!CarsService.deleteById(req.getParameter("action"))) {
                    resp.sendRedirect("ErrPage.jsp");
                } else {
                    req.getSession().setAttribute("action", "cars");
                    resp.sendRedirect("/admin?action=cars");
                }
            }
        }
    }
}
