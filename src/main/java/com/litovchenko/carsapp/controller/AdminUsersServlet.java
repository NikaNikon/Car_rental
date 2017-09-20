package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.service.LoginService;
import com.litovchenko.carsapp.service.UsersServise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminUsers")
public class AdminUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            if ("newManager".equals(action)) {
                req.setAttribute("manager", true);
                req.getRequestDispatcher("WEB-INF/pages/Registration.jsp").forward(req, resp);
            }
            if ("users".equals(action)) {
                req.setAttribute("users", UsersServise.getCustomers());
                req.getRequestDispatcher("WEB-INF/pages/AdminUsers.jsp").forward(req, resp);
            }
        }
        req.setAttribute("managers", UsersServise.getManagers());
        req.getRequestDispatcher("WEB-INF/pages/AdminManagers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            if (action.startsWith("block") || action.startsWith("userBlock") ||
                    action.startsWith("unblock") || action.startsWith("userUnblock")
                    || action.startsWith("delete")) {
                int id = Integer.parseInt(action.split("_")[1]);
                if (action.startsWith("block") || action.startsWith("userBlock")) {
                    if (UsersServise.changeBlockedStatus(true, id)) {
                        if (action.startsWith("userBlock")) {
                            resp.sendRedirect("/adminUsers?action=users");
                        } else {
                            resp.sendRedirect("/adminUsers");
                        }
                    }
                } else if (action.startsWith("unblock") || action.startsWith("userUnblock")) {
                    if ((UsersServise.changeBlockedStatus(false, id))) {
                        if (action.startsWith("userUnblock")) {
                            resp.sendRedirect("/adminUsers?action=users");
                        } else {
                            resp.sendRedirect("/adminUsers");
                        }
                    }
                } else if (action.startsWith("delete")) {
                    if (UsersServise.deleteUser(id)) {
                        resp.sendRedirect("/adminUsers");
                    }
                } else {
                    resp.sendRedirect("ErrPage.jsp");
                }
            } else if ("register".equals(action)) {
                String validation = LoginService.validateData(req.getParameter("login"), req.getParameter("email"),
                        req.getParameter("password"), req.getParameter("confirmPassword"));
                switch (validation) {
                    case "OK": {
                        if (UsersServise.addManager(req.getParameter("login"), req.getParameter("email"),
                                req.getParameter("password"))) {
                            resp.sendRedirect("/adminUsers");
                        } else {
                            resp.sendRedirect("ErrPage.jsp");
                        }
                        break;
                    }
                    default: {
                        req.setAttribute("manager", true);
                        req.setAttribute("msg", validation);
                        req.getRequestDispatcher("WEB-INF/pages/Registration.jsp").forward(req, resp);

                        break;
                    }
                }
            }

        }
    }
}
