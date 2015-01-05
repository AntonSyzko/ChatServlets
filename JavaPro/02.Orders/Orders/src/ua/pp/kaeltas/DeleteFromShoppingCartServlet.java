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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kaeltas on 23.12.14.
 */
public class DeleteFromShoppingCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            try {
                Map<Product, Integer> shoppingCartMap = null;
                if (session.getAttribute("shoppingCartMap") != null) {
                    shoppingCartMap = (Map<Product, Integer>) session.getAttribute("shoppingCartMap");
                    int index = Integer.parseInt(request.getParameter("productindex"));
                    if (index > 0) {
                        Product prodToDelete = (Product)(new ArrayList(shoppingCartMap.keySet())).get(index - 1);
                        if (shoppingCartMap.get(prodToDelete) > 1) {
                            shoppingCartMap.put(prodToDelete, shoppingCartMap.get(prodToDelete)-1);
                        } else {
                            shoppingCartMap.remove(prodToDelete);
                        }
                    }
                }

                session.setAttribute("shoppingCartMap", shoppingCartMap);
                response.sendRedirect("/userpanel");
            } catch (NumberFormatException nfe) {
                response.sendError(333, "Wrong parameter..");
            }
        } else {
            response.sendRedirect("/");
        }
    }
}
