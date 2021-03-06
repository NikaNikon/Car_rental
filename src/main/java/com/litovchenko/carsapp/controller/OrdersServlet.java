package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.service.OrdersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) (req.getSession().getAttribute("user"));
        if ("CUSTOMER".equals(user.getRole())) {
            String action = req.getParameter("action");
            if (action == null || "new".equals(action) || "back".equals(action)) {
                req.setAttribute("orders", OrdersService.getNewOrders(
                        OrdersService.getOrders(user.getId())));
            } else if ("all".equals(action)) {
                req.setAttribute("orders", OrdersService.getOrders(user.getId()));
            } else if (action.startsWith("getCheck_")) {

                resp.setContentType("application/pdf");
                resp.setHeader("target", "_blank");

                OutputStream out = resp.getOutputStream();
                byte[] bytes = OrdersService.getCheck(Integer.parseInt(action.split("_")[1]));
                out.write(bytes, 0, bytes.length);
                out.close();

            }
            req.getRequestDispatcher("WEB-INF/pages/Orders.jsp").forward(req, resp);
        } else if ("MANAGER".equals(user.getRole())) {
            String action = req.getParameter("action");
            if (action == null || "new".equals(action) || "back".equals(action)) {
                req.setAttribute("orders", OrdersService.getNewOrders(OrdersService.getOrders()));
            } else if ("active".equals(action)) {
                req.setAttribute("orders",
                        OrdersService.getActiveOrders(OrdersService.getOrders()));
            } else if ("closed".equals(action)) {
                req.setAttribute("orders",
                        OrdersService.getClosedOrders(OrdersService.getOrders()));
            }
            req.getRequestDispatcher("WEB-INF/pages/Orders.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect("/orders");
        }
        int id = Integer.parseInt(action.split("_")[1]);
        if (action.startsWith("confirm")) {
            OrdersService.confirm(id);
            resp.sendRedirect("/orders");
        } else if (action.startsWith("reject")) {
            if (req.getParameter("comment") == null) {
                req.setAttribute("action", action);
                req.getRequestDispatcher("WEB-INF/pages/OrderCommentForm.jsp").forward(req, resp);
            } else {
                OrdersService.reject(id, req.getParameter("comment"));
                resp.sendRedirect("/orders");
            }
        } else if (action.startsWith("close")) {
            OrdersService.closeOrder(id);
            resp.sendRedirect("/orders");
        } else if (action.startsWith("check")) {
            if (!OrdersService.checkAbility(id)) {
                req.setAttribute("msg", "msg");
                req.setAttribute("orders",
                        OrdersService.getClosedOrders(OrdersService.getOrders()));
                req.getRequestDispatcher("WEB-INF/pages/Orders.jsp").forward(req, resp);
            }
            req.setAttribute("orderId", id);
            req.getRequestDispatcher("WEB-INF/pages/CheckForm.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/orders");
        }
    }
}
