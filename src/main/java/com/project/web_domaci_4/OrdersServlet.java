package com.project.web_domaci_4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "orders", value = "/orders")
public class OrdersServlet extends HttpServlet {

    private static final String MY_PAGE = "orders.html";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("JA SAM");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String lozinka = (String) request.getParameter("password");
        String page ;
        if((page = Util.INSTANCE.getPage(MY_PAGE,lozinka,"nebitno")) == null)
        {   response.setStatus(401);
            return;
        }
        out.println(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Util.INSTANCE.deleteOrders();
        response.sendRedirect("/orders?password="+ Util.INSTANCE.readResource("password.txt"));
    }
}
