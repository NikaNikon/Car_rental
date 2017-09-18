package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.service.CarsService;
import com.litovchenko.carsapp.service.OrdersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getParameter("action").startsWith("order")) {
            String id = req.getParameter("action").split("_")[1];
            if (!CarsService.changeStatus(CarsService.getById(id), Car.Status.IN_RENT)) {
                resp.sendRedirect("ErrPage.jsp");
            } else {
                if(((User)(req.getSession().getAttribute("user"))).getPassportData() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String birthday = sdf.format(((User)(req.getSession().getAttribute("user"))).
                            getPassportData().getDateOfBirth());
                    req.setAttribute("birthday", birthday);
                }
                req.setAttribute("carId", Integer.parseInt(id));
                req.setAttribute("maxDateOfBirth", OrdersService.getMaxDateOfBirth());
                req.setAttribute("minStartDate", OrdersService.getDaysAfterToday(1));
                req.setAttribute("maxStartDate", OrdersService.getDaysAfterToday(3));
                req.getRequestDispatcher("OrderForm.jsp").forward(req, resp);
            }
        }

        switch (req.getParameter("action")) {
            case "home": {
                CarsService.changeStatus(CarsService.getById(req.getParameter("carId")),
                        Car.Status.AVAILABLE);
                resp.sendRedirect("/MainPageServlet");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("makeOrder".equals(req.getParameter("action"))) {
            boolean driver = "on".equals(req.getParameter("driver"));
            System.out.println(req.getParameter("name"));
            System.out.println(req.getParameter("middleName"));
            System.out.println(req.getParameter("lastNme"));
            System.out.println(req.getParameter("dateOfBirth"));
            System.out.println(req.getParameter("phone"));
            System.out.println(req.getParameter("startDate"));
            System.out.println(req.getParameter("endDate"));
            System.out.println(driver);
            CarsService.changeStatus(CarsService.getById(req.getParameter("carId")),
                    Car.Status.AVAILABLE);
            if (!OrdersService.makeOrder(req.getParameter("name"), req.getParameter("middleName"),
                    req.getParameter("lastName"), OrdersService.getDate(req.getParameter("dateOfBirth")),
                    req.getParameter("phone"), ((User) req.getSession().getAttribute("user")).getId(),
                    Integer.parseInt(req.getParameter("carId")),
                    OrdersService.getDate(req.getParameter("startDate")),
                    OrdersService.getDate(req.getParameter("endDate")), driver)) {
                CarsService.changeStatus(CarsService.getById(req.getParameter("carId")),
                        Car.Status.AVAILABLE);
                resp.sendRedirect("ErrPage.jsp");
            } else {
                resp.sendRedirect("OrderSuccess.jsp");
            }
        }
    }
}
