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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaeltas on 23.12.14.
 */
public class AddToShoppingCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            Map<Product, Integer> shoppingCartMap = null;
            if (session.getAttribute("shoppingCartMap") != null) {
                shoppingCartMap = (Map<Product, Integer>)session.getAttribute("shoppingCartMap");
            } else {
                shoppingCartMap = new LinkedHashMap<Product, Integer>();
            }

            if (shoppingCartMap.size() < 10) {
                try {
                    Connection conn = MysqlConnectionFactory.createConnection();
                    PreparedStatement preparedStatement = null;
                    try {
                        int productid = Integer.parseInt(request.getParameter("productid"));
                        preparedStatement = conn.prepareStatement("SELECT name, price FROM Product WHERE id = ?");
                        preparedStatement.setInt(1, productid);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            Product product = new Product();
                            product.id = productid;
                            product.name = resultSet.getString(1);
                            product.price = resultSet.getString(2);
                            if (shoppingCartMap.containsKey(product)) {
                                shoppingCartMap.put(product, shoppingCartMap.get(product)+1);
                            } else {
                                shoppingCartMap.put(product, 1);
                            }
                        }

                        session.setAttribute("shoppingCartMap", shoppingCartMap);
                        //request.getRequestDispatcher("userpanel.jsp").forward(request, response);
                        response.sendRedirect("/userpanel");
                    } finally {
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        conn.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(333, "Sorry, something goes wrong..");
                }
            } else {
                response.sendError(333, "Too much products in shopping cart. Maximum is 10 products.");
            }
        } else {
            response.sendRedirect("/");
        }
    }

}
