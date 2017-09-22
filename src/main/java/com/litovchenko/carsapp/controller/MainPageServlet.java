package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.service.*;
import org.apache.log4j.Logger;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainPageServlet", urlPatterns = {"/MainPageServlet", "/MainPageServlet/*"})
public class MainPageServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(MainPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if(req.getSession().getAttribute("user") != null){
            req.getSession().setAttribute("user",
                    UsersServise.refresh(((User)(req.getSession().getAttribute("user")))));
        }

        List<Car> list;
        if (req.getSession().getAttribute("cars") != null) {
            list = (List<Car>) req.getSession().getAttribute("cars");
        } else {
            list = CarsService.getAvailable();
        }

        if (null != req.getParameter("clear")) {
            req.getSession().setAttribute("cars", null);
            resp.sendRedirect("/MainPageServlet");
            return;
        }

        if (null != req.getParameter("sort") && !"default".equals(req.getParameter("sort"))) {
            CarsService.sort(list, req.getParameter("sort"));
        }

        if (null != req.getParameter("carClass")) {
            list = CarsService.getAvailable();
            list = CarsService.pickByClass(list, req.getParameter("carClass"));
        }
        if (null != req.getParameter("model")) {
            list = CarsService.getAvailable();
            list = CarsService.pickByModel(list, req.getParameter("model"));
        }
        if (null != req.getParameter("minPrice") && req.getParameter("minPrice").length() > 0) {
            try {
                list = CarsService.pickByPrice(list,
                        Integer.parseInt(req.getParameter("minPrice")), "min");
            } catch (NumberFormatException e) {
                LOGGER.error("An exception occurred while processing invalid input of price filter " +
                        "on main page: " + e);
                throw new UserInputException(e);
            }
        }
        if (null != req.getParameter("maxPrice") && req.getParameter("maxPrice").length() > 0) {
            try {
                list = CarsService.pickByPrice(list,
                        Integer.parseInt(req.getParameter("maxPrice")), "max");
            } catch (NumberFormatException e) {
                LOGGER.error("An exception occurred while processing invalid input of price filter " +
                        "on main page: " + e);
                throw new UserInputException(e);
            }
        }
        req.getSession().setAttribute("cars", list);
        User user = ((User) (req.getSession().getAttribute("user")));
        if (user != null) {
            if("MANAGER".equals(user.getRole()) || "ADMIN".equals(user.getRole())){
                req.getSession().setAttribute("msg", "you are " + user.getRole().toLowerCase());
            } else {
                req.getSession().setAttribute("msg",
                        OrdersService.checkAbilityToMakeOrders(user));
            }
        }

        req.setAttribute("cars", list);
        req.setAttribute("classes", CarClassesService.getClasses());
        req.setAttribute("models", CarsService.getModels());
        req.getRequestDispatcher("WEB-INF/pages/Main Page.jsp").forward(req, resp);
    }
}
