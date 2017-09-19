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
public class CheckServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            resp.sendRedirect("/orders");
        }
        if("save".equals(action)){
            ChecksService.saveCheck(((User)(req.getSession().getAttribute("user"))).getId(),
                    Integer.parseInt(req.getParameter("orderId")),
                    Integer.parseInt(req.getParameter("price")), req.getParameter("comment"));
            resp.sendRedirect("/orders");
        }
    }
}
