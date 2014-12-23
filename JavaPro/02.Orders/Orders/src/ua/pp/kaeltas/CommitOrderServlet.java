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
import java.util.List;

/**
 * Created by kaeltas on 23.12.14.
 */
public class CommitOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            List<Product> shoppingCartList = null;
            if (session.getAttribute("shoppingCartList") != null) {
                shoppingCartList = (List<Product>) session.getAttribute("shoppingCartList");

                try {
                    Connection conn = MysqlConnectionFactory.createConnection();

                    Statement statement = conn.createStatement();
                    try {
                        ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) FROM `Order`");
                        if (resultSet.next()) {
                            if (resultSet.getInt(1) < 1000) {
                                PreparedStatement preparedStatement = null;
                                try {

                                    conn.setAutoCommit(false);
                                    try {
                                        preparedStatement = conn.prepareStatement("INSERT INTO `Order` (user_id) VALUES ((SELECT id FROM User WHERE login = ?))", PreparedStatement.RETURN_GENERATED_KEYS);
                                        preparedStatement.setString(1, (String) session.getAttribute("login"));
                                        int res = preparedStatement.executeUpdate();
                                        ResultSet rs = preparedStatement.getGeneratedKeys();
                                        if (rs.next()) {
                                            int lastid = rs.getInt(1);
                                            preparedStatement = conn.prepareStatement("INSERT INTO OrderData (order_id, product_id) VALUES (?, ?)");
                                            for (Product product : shoppingCartList) {
                                                preparedStatement.setInt(1, lastid);
                                                preparedStatement.setInt(2, product.id);
                                                preparedStatement.executeUpdate();
                                            }
                                        }

                                        conn.commit();
                                        session.setAttribute("shoppingCartList", null);
                                        response.sendRedirect("/successorder.jsp");
                                        conn.setAutoCommit(true);
                                    } catch (Exception e) {
                                        conn.rollback();
                                        e.printStackTrace();
                                        response.sendRedirect("/");
                                    }
                                } finally {
                                    if (preparedStatement != null) {
                                        preparedStatement.close();
                                    }
                                    conn.close();
                                }
                            } else {
                                response.sendError(333, "Sorry, can't save your order. Too much orders in database.");
                            }
                        }
                    } finally {
                        if (statement != null) {
                            statement.close();
                        }
                    }
                } catch (Exception e) {
                    response.sendError(333, "Sorry, something goes wrong..");
                }

            }

        } else {
            response.sendRedirect("/");
        }
    }
}
