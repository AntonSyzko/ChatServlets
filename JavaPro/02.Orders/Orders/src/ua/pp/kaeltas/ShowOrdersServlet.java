package ua.pp.kaeltas;

import ua.pp.kaeltas.dbwrapping.Order;
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
import java.util.List;

/**
 * Created by kaeltas on 23.12.14.
 */
public class ShowOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null  && session.getAttribute("isAdmin") != null && (Boolean)session.getAttribute("isAdmin")) {
            try {
                Connection conn = MysqlConnectionFactory.createConnection();
                Statement statement = null;
                PreparedStatement preparedStatement = null;
                try {
                    statement = conn.createStatement();
                    String query1 = "SELECT u.id, u.login, o.id, o.datetime" +
                            " FROM `User` u, `Order` o" +
                            " WHERE o.user_id = u.id";

                    String query2 = "SELECT p.id, p.name, p.price, od.product_count" +
                            " FROM `OrderData` od, Product p" +
                            " WHERE od.product_id = p.id" +
                            " AND od.order_id = ?";
                    preparedStatement = conn.prepareStatement(query2);

                    List<Order> orderList = new ArrayList<Order>();
                    ResultSet resultSet = statement.executeQuery(query1);
                    while(resultSet.next()) {
                        Order order = new Order();
                        order.user_id = resultSet.getInt(1);
                        order.login = resultSet.getString(2);
                        order.id = resultSet.getInt(3);
                        order.datetime = resultSet.getDate(4) + " " + resultSet.getTime(4);

                        preparedStatement.setInt(1, order.id);
                        ResultSet resultSet1 = preparedStatement.executeQuery();
                        while (resultSet1.next()) {
                            Product product = new Product();
                            product.id = resultSet1.getInt(1);
                            product.name = resultSet1.getString(2);
                            product.price = resultSet1.getString(3);

                            order.orderData.put(product, resultSet1.getInt(4));
                        }

                        orderList.add(order);
                    }

                    request.setAttribute("orderList", orderList);
                    request.getRequestDispatcher("showorders.jsp").forward(request, response);
                } finally {
                    if (statement != null) {
                        statement.close();
                    }
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
            response.sendRedirect("/");
        }
    }
}
