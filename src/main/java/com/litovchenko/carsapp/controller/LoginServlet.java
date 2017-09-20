package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.service.LoginService;
import com.litovchenko.carsapp.service.OrdersService;
import com.litovchenko.carsapp.service.UsersServise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        switch (req.getParameter("action")) {
            case "login": {
                req.getRequestDispatcher("WEB-INF/pages/Login.jsp").forward(req, resp);
                break;
            }
            case "register": {
                req.getRequestDispatcher("WEB-INF/pages/Registration.jsp").forward(req, resp);
                break;
            }
            case "home": {
                resp.sendRedirect("MainPageServlet");
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user;
        switch (req.getParameter("action")) {
            case "login": {
                user = UsersServise.logIn(req.getParameter("login"),
                        LoginService.hashPassword(req.getParameter("password")));
                if (user != null) {
                    req.getSession().setAttribute("user", user);
                    req.getSession().setAttribute("msg",
                            OrdersService.checkAbilityToMakeOrders(user));
                    resp.sendRedirect("/MainPageServlet");
                } else {

                    System.out.println("user == null");

                    req.setAttribute("errMsg", "Invalid login or password.");
                    req.getRequestDispatcher("WEB-INF/pages/Login.jsp").forward(req, resp);
                }
                break;
            }
            case "register": {
                String validation = LoginService.validateData(req.getParameter("login"), req.getParameter("email"),
                        req.getParameter("password"), req.getParameter("confirmPassword"));
                switch (validation) {
                    case "OK": {
                        user = UsersServise.addUser(req.getParameter("login"), req.getParameter("email"),
                                LoginService.hashPassword(req.getParameter("password")));
                        if (user != null) {
                            req.getSession().setAttribute("user", user);
                            req.getSession().setAttribute("msg",
                                    OrdersService.checkAbilityToMakeOrders(user));
                            resp.sendRedirect("RegistrationSuccess.jsp");
                        } else {
                            resp.sendRedirect("ErrPage.jsp");
                        }
                        break;
                    }
                    default: {
                        req.setAttribute("msg", validation);
                        req.getRequestDispatcher("WEB-INF/pages/Registration.jsp").forward(req, resp);

                        break;
                    }
                }
                break;
            }
            case "logout": {
                req.getSession().setAttribute("user", null);
                resp.sendRedirect("/MainPageServlet");
                break;
            }
        }
    }
}
