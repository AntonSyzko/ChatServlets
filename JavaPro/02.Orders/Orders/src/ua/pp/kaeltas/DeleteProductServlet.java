package ua.pp.kaeltas;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by kaeltas on 23.12.14.
 */
public class DeleteProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null && session.getAttribute("isAdmin") != null && (Boolean)session.getAttribute("isAdmin")) {
            try {
                Connection conn = MysqlConnectionFactory.createConnection();

                int productid = Integer.parseInt(request.getParameter("productid"));

                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `Product` WHERE id=?");
                try {
                    preparedStatement.setInt(1, productid);

                    preparedStatement.executeUpdate();
                    response.sendRedirect("/addproduct");
                } finally {
                    if (preparedStatement != null) {
                        preparedStatement.close();
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
