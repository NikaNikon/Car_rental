package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.service.ChecksService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/check")
public class CheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) (req.getSession().getAttribute("user"));
        String action = req.getParameter("action");
        if ("MANAGER".equals(user.getRole())) {
            if (action == null || "unpayed".equals(action)) {
                req.setAttribute("checks", ChecksService.getUnpayed(ChecksService.getAll()));
            } else if ("all".equals(action)) {
                req.setAttribute("checks", ChecksService.getAll());
            }
        } else if ("CUSTOMER".equals(user.getRole())) {
            if (action == null || "unpayed".equals(action)) {
                req.setAttribute("checks", ChecksService.getUnpayed(
                        ChecksService.getByUserId(user.getId())));
            } else if ("all".equals(action)) {
                req.setAttribute("checks", ChecksService.getByUserId(user.getId()));
            }
        }
        req.getRequestDispatcher("WEB-INF/pages/RepairmentChecks.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect("/MainPageServlet");
        }
        if ("save".equals(action)) {
            ChecksService.saveCheck(Integer.parseInt(req.getParameter("orderId")),
                    Integer.parseInt(req.getParameter("price")), req.getParameter("comment"));
            resp.sendRedirect("/orders");
        }
        if (action.startsWith("pay")) {
            ChecksService.pay(Integer.parseInt(action.split("_")[1]));
            resp.sendRedirect("/check");
        }
    }
}
