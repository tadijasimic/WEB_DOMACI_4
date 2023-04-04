package com.project.web_domaci_4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


@WebServlet(name = "make_order", value = "/make_order")
public class MakeOrderServlet extends HttpServlet {
    private static final String MY_PAGE = "make_order.html";


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (Util.INSTANCE.getOrders().containsKey(request.getSession().getId())) {
            response.sendRedirect("/your_order");
            return;
        }
        out.println(Util.INSTANCE.getPage(MY_PAGE, "nebitno", "nebitno"));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        if (request.getParameter("select1") == null || request.getParameter("select2") == null ||
                request.getParameter("select3") == null || request.getParameter("select4") == null ||
                request.getParameter("select5") == null) {
            response.getOutputStream().println("Morate izabrati jelo za svaki dan!");
            response.setStatus(401);
            return;
        }
        ///making an order
        HashMap<String, String> myOrder = new HashMap<>();

        myOrder.put("mondayMeal", request.getParameter("select1"));
        myOrder.put("tuesdayMeal", request.getParameter("select2"));
        myOrder.put("wednesdayMeal", request.getParameter("select3"));
        myOrder.put("thursdayMeal", request.getParameter("select4"));
        myOrder.put("fridayMeal", request.getParameter("select5"));

        try {
            String client = request.getSession().getId();

            synchronized (this) {
                if (!Util.INSTANCE.getOrders().containsKey(client)) {
                    //DODAJEM KORISNIKA I NJEGOV NEDELJNI MENI
                    Util.INSTANCE.getOrders().put(client, myOrder);
                } else throw new RuntimeException("Ne bi trebalo da moze da dodje do ovoga...");
            }
            request.getSession().setAttribute("jmbg", request.getParameter("jmbg"));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        System.out.println(myOrder);
        response.sendRedirect("/your_order");

    }

    public void destroy() {
    }
}