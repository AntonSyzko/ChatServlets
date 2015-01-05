package ua.pp.kaeltas;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kaeltas on 05.01.15.
 */
public class AlterDbServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = MysqlConnectionFactory.createConnection();
        try {
            Statement st = conn.createStatement();
            try {
                st.executeUpdate("ALTER TABLE ordersdb.OrderData ADD product_count INT NOT NULL;");
            } finally {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.getWriter().println("Success");

    }
}
