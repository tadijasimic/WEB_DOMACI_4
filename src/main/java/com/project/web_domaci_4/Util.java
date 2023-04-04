package com.project.web_domaci_4;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.StringTokenizer;

public enum Util {

    INSTANCE;
    private static final String MAKE_ORDER = "make_order.html", YOUR_ORDER = "your_order.html", ORDERS = "orders.html";
    private static final String PASSWORD = "password.txt";
    private volatile HashMap<String, HashMap<String,String>> orders = new HashMap<>();


    public HashMap<String, HashMap<String,String>> getOrders() {
        return orders;
    }


    public String getPage(String page, String password, String clientID) {

        if(page.equals(MAKE_ORDER)) {
            return formatMakeOrder();
        }

        else if (page.equals(YOUR_ORDER)) {
            return formatYourOrder(clientID);

        }

        if(page.equals(ORDERS) && password.equals(readResource(PASSWORD).trim())) {
            return formatOrders();
        }
        return null;
    }
    public void deleteOrders(){
        orders = new HashMap<>();
    }

    private String formatMakeOrder() {

    String src = readResource(MAKE_ORDER);
    HashMap<String,String> menu = getMenu();
        for(String key : menu.keySet()) {
            src = src.replace(key,menu.get(key));
            System.out.println("REPLACEOVAO JE ");


        }
    return src;
    }

    private String formatYourOrder(String clientID) {

       String src = readResource(YOUR_ORDER);

       try {
           if (!orders.containsKey(clientID))
               throw new RuntimeException(">,< ????");
       }catch (RuntimeException e)  {
           e.printStackTrace();
           return "error";
       }

       HashMap<String ,String> myOrder = orders.get(clientID);

       for(String key : myOrder.keySet()) {
           src = src.replace(key,myOrder.get(key));
       }
       return src;
    }

    private String formatOrders() {
        HashMap<String, HashMap<String,Integer> > allOrders = new HashMap<>();

        for(String client : orders.keySet()) {

            for(String dan : orders.get(client).keySet()) {

                if(!allOrders.containsKey(dan)){

                    allOrders.put(dan,new HashMap<>());
                }
                if(!allOrders.get(dan).containsKey(orders.get(client).get(dan)))
                    allOrders.get(dan).put(orders.get(client).get(dan),1);
                else
                    allOrders.get(dan).put(orders.get(client).get(dan), allOrders.get(dan).get(orders.get(client).get(dan)) + 1);
            }
        }

        String src = readResource(ORDERS);

        for(String dan : allOrders.keySet()) {
            for(String jelo : allOrders.get(dan).keySet()) {
                String part = "<div class=\"day\">"+ dan +":</div>\n" +
                        "    <div class=\"meal\">\n" +
                        "        <span>"+ jelo +"</span>\n" +
                        "        <span>("+ allOrders.get(dan).get(jelo) +")</span>\n" +
                        "    </div>\n" +
                        "    <hr>\nHERE";

                src = src.replace("HERE",part);
            }
        }

        return src.replace("HERE","");
    }


    private HashMap<String,String> getMenu() {
        HashMap<String,String> menu = new HashMap<>();
        String src = readResource("menu.txt");
        StringTokenizer token = new StringTokenizer(src,"[\n \r\t]*)");
        String str1,str2;
        while(token.countTokens() >= 2) {
            str1 = token.nextToken();
            str2 = token.nextToken();
            menu.put(str1,str2);
            System.out.println(str1 + " jelo:   " + str2);
        }

        return menu;
    }

    public String readResource(String resource) {

        try {
            String filePath = Objects.requireNonNull(Util.class.getClassLoader().getResource(resource)).getFile();

            if (filePath == null) throw new NullPointerException();


            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line).append(System.lineSeparator());
                }

                return response.toString();

            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "EMPTY";
    }
}
