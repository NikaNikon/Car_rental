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

@WebServlet("/carForm")
public class CarFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        if (req.getAttribute("action") != null) {
            if (req.getAttribute("action").equals("edit")) {
                Car car = CarsService.getById((String) req.getAttribute("carId"));
                req.setAttribute("car", car);
                req.getSession().setAttribute("car", car);
            }
        }

        req.setAttribute("classes", CarClassesService.getAll());
        req.setAttribute("statuses", CarsService.getStatuses());
        req.getRequestDispatcher("WEB-INF/pages/CarDataForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("save".equals(req.getParameter("action"))) {
            boolean isAdded = CarsService.addNewCar(req.getParameter("licensePlate"),
                    req.getParameter("model"), req.getParameter("carClass"),
                    req.getParameter("price"), req.getParameter("fullName"),
                    req.getParameter("description"), req.getParameter("driverPrice"));
            if (isAdded) {
                resp.sendRedirect("/admin?action=cars");
            } else {
                resp.sendRedirect("ErrPage.jsp");
            }
        } else if ("update".equals(req.getParameter("action"))) {
            Car car = (Car) req.getSession().getAttribute("car");
            boolean isUpdated = CarsService.update(car.getId(), req.getParameter("licencePlate"),
                    req.getParameter("model"), req.getParameter("carClass"),
                    req.getParameter("price"), req.getParameter("fullName"),
                    req.getParameter("description"), Car.Status.valueOf(req.getParameter("status")),
                    req.getParameter("driverPrice"));
            if (isUpdated) {
                req.getSession().removeAttribute("car");
                resp.sendRedirect("/admin?action=cars");
            } else {
                req.getSession().removeAttribute("car");
                resp.sendRedirect("ErrPage.jsp");
            }
        } else if ("back".equals(req.getParameter("action"))) {
            resp.sendRedirect("/admin?action=cars");
        }

    }
}
