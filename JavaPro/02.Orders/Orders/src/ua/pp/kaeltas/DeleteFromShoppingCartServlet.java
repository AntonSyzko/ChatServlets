package ua.pp.kaeltas;

import ua.pp.kaeltas.dbwrapping.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by kaeltas on 23.12.14.
 */
public class DeleteFromShoppingCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            try {
                Map<Product, Integer> oldShoppingCartMap = null;
                Map<Product, Integer> newShoppingCartMap = null;
                if (session.getAttribute("shoppingCartMap") != null) {
                    oldShoppingCartMap = (Map<Product, Integer>) session.getAttribute("shoppingCartMap");
                    newShoppingCartMap = new LinkedHashMap<Product, Integer>(oldShoppingCartMap);
                    int index = Integer.parseInt(request.getParameter("productindex"));
                    if (index > 0) {
                        Product prodToDelete = (Product)(new ArrayList(newShoppingCartMap.keySet())).get(index - 1);
                        if (newShoppingCartMap.get(prodToDelete) > 1) {
                            newShoppingCartMap.put(prodToDelete, oldShoppingCartMap.get(prodToDelete)-1);
                        } else {
                            newShoppingCartMap.remove(prodToDelete);
                        }
                    }
                }

                session.setAttribute("shoppingCartMap", Collections.unmodifiableMap(newShoppingCartMap));
                response.sendRedirect("/userpanel");
            } catch (NumberFormatException nfe) {
                response.sendError(333, "Wrong parameter..");
            }
        } else {
            response.sendRedirect("/");
        }
    }
}
