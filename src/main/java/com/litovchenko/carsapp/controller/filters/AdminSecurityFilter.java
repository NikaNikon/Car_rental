package com.litovchenko.carsapp.controller.filters;

import com.litovchenko.carsapp.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminSecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        String role = ((User)(session.getAttribute("user"))).getRole();
        if(!"ADMIN".equals(role)){
            req.getRequestDispatcher("/MainPageServlet").forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
