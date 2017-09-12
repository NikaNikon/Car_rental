package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.service.CarClassesService;
import com.litovchenko.carsapp.service.CarsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainPageServlet", urlPatterns = {"/MainPageServlet", "/MainPageServlet/*"})
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Car> list;
        if (req.getSession().getAttribute("cars") != null) {
            list = (List<Car>) req.getSession().getAttribute("cars");
        } else {
            list = CarsService.getList();
        }

        if (null != req.getParameter("clear")) {
            req.getSession().setAttribute("cars", null);
            doPost(req, resp);
            return;
        }

        if (null != req.getParameter("sort") && !"default".equals(req.getParameter("sort"))) {
            CarsService.sort(list, req.getParameter("sort"));
        }

        if (null != req.getParameter("carClass")) {
            list = CarsService.pickByClass(list, req.getParameter("carClass"));
        }
        if (null != req.getParameter("model")) {
            list = CarsService.pickByModel(list, req.getParameter("model"));
        }
        if (null != req.getParameter("minPrice") && req.getParameter("minPrice").length() > 0) {
            try {
                list = CarsService.pickByPrice(list,
                        Integer.parseInt(req.getParameter("minPrice")), "min");
            } catch (NumberFormatException e) {
                System.out.println("Cannot do filter: min " + req.getParameter("minPrice"));
            }
        }
        if (null != req.getParameter("maxPrice") && req.getParameter("maxPrice").length() > 0) {
            try {
                list = CarsService.pickByPrice(list,
                        Integer.parseInt(req.getParameter("maxPrice")), "max");
            } catch (NumberFormatException e) {
                System.out.println("Cannot do filter: max " + req.getParameter("maxPrice"));
            }
        }
        req.getSession().setAttribute("cars", list);

        req.setAttribute("cars", list);
        req.setAttribute("classes", CarClassesService.getClasses());
        req.getRequestDispatcher("Main Page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (null != req.getParameter("clear")) {
            resp.sendRedirect("/MainPageServlet");
        }
    }
}
