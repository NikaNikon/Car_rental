package com.litovchenko.carsapp.controller;

import com.litovchenko.carsapp.dao.CarClassDAO;
import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Main")
public class Main extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        main(new String[] {});
    }

    public void main(String[] args) {
        DAOFactory factory = new MySqlDAOFactory();
        CarClassDAO carClassDAO = factory.getCarClassDAO();
        System.out.println(carClassDAO.getAll());
    }
}
