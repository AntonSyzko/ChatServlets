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
public class DeleteOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null && session.getAttribute("isAdmin") != null && (Boolean)session.getAttribute("isAdmin")) {
            try {
                Connection conn = MysqlConnectionFactory.createConnection();

                int orderid = Integer.parseInt(request.getParameter("orderid"));

                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `OrderData` WHERE order_id=?");
                PreparedStatement preparedStatement2 = conn.prepareStatement("DELETE FROM `Order` WHERE id=?");
                try {
                    preparedStatement.setInt(1, orderid);
                    preparedStatement2.setInt(1, orderid);

                    conn.setAutoCommit(false);
                    try{
                        preparedStatement.executeUpdate();
                        preparedStatement2.executeUpdate();
                        conn.commit();
                        conn.setAutoCommit(true);
                        response.sendRedirect("/showorders");
                    } catch (Exception e) {
                        conn.rollback();
                        throw e;
                    }
                } finally {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (preparedStatement2 != null) {
                        preparedStatement2.close();
                    }
                }
            } catch (Exception e) {
                response.sendError(333, "Sorry, something goes wrong..");
            }
        } else {
            response.sendRedirect("/");
        }
    }
}
