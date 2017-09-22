package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.model.Order;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/order")
public class OrderFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if(req.getSession().getAttribute("user") != null){
            req.getSession().setAttribute("user",
                    UsersServise.refresh(((User)(req.getSession().getAttribute("user")))));
        }

        if (req.getParameter("action").startsWith("order")) {

            if(!"OK".equals(OrdersService.checkAbilityToMakeOrders(
                    ((User)(req.getSession().getAttribute("user")))))){
                throw new IllegalAccessError();
            }

            String id = req.getParameter("action").split("_")[1];
            if (!CarsService.changeStatus(CarsService.getById(id), Car.Status.IN_RENT)) {
                resp.sendRedirect("ErrPage.jsp");
            } else {
                if (((User) (req.getSession().getAttribute("user"))).getPassportData() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String birthday = sdf.format(((User) (req.getSession().getAttribute("user"))).
                            getPassportData().getDateOfBirth());
                    req.setAttribute("birthday", birthday);
                }
                req.setAttribute("carId", Integer.parseInt(id));
                req.setAttribute("maxDateOfBirth", OrdersService.getMaxDateOfBirth());
                req.setAttribute("minStartDate", OrdersService.getDaysAfterToday(1));
                req.setAttribute("maxStartDate", OrdersService.getDaysAfterToday(3));
                req.getRequestDispatcher("WEB-INF/pages/OrderForm.jsp").forward(req, resp);
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
            CarsService.changeStatus(CarsService.getById(req.getParameter("carId")),
                    Car.Status.IN_RENT);
            Order order = OrdersService.makeOrder(req.getParameter("name"), req.getParameter("middleName"),
                    req.getParameter("lastName"), OrdersService.getDate(req.getParameter("dateOfBirth")),
                    req.getParameter("phone"), ((User) req.getSession().getAttribute("user")).getId(),
                    Integer.parseInt(req.getParameter("carId")),
                    OrdersService.getDate(req.getParameter("startDate")),
                    OrdersService.getDate(req.getParameter("endDate")), driver);

            if (order == null) {
                CarsService.changeStatus(CarsService.getById(req.getParameter("carId")),
                        Car.Status.AVAILABLE);
                resp.sendRedirect("ErrPage.jsp");
            } else {
                TwilioSender.send(req.getParameter("phone"), order.getId(), order.getTotalPrice());

                req.getSession().setAttribute("cars", null);
                req.getSession().setAttribute("user",
                        UsersServise.getUser(((User) (req.getSession().getAttribute("user"))).getId()));
                resp.sendRedirect("OrderSuccess.jsp");
            }
        }
    }
}
