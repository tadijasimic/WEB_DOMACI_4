package com.project.web_domaci_4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "yourOrderServlet", value = "/your_order")
public class YourOrderServlet extends HttpServlet {
    private static final String MY_PAGE = "your_order.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(Util.INSTANCE.getPage(MY_PAGE,"nebitno", request.getSession().getId()));
    }
}
