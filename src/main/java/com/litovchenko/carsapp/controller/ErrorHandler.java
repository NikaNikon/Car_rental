package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.service.ApplicationException;
import com.litovchenko.carsapp.service.UserInputException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/errorHandler")
public class ErrorHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Throwable t = (Throwable)req.getAttribute("javax.servlet.error.exception");
        if(t == null){
            resp.sendRedirect("ErrPage.jsp");
        }
        if(t.getClass().equals(UserInputException.class)){
            resp.sendRedirect("InputErrPage.jsp");
        } else if (t.getClass().equals(ApplicationException.class)){
            resp.sendRedirect("AppErrPage.jsp");
        } else {
            resp.sendRedirect("ErrPage.jsp");
        }

    }
}
