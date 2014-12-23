package ua.pp.kaeltas;

import ua.pp.kaeltas.dbwrapping.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaeltas on 22.12.14.
 */
public class UserPanelServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn = MysqlConnectionFactory.createConnection();
            try {
                List<Product> productList = getProductList(conn);
                request.setAttribute("productList", productList);

                request.getRequestDispatcher("userpanel.jsp").forward(request, response);
            } finally {
                conn.close();
            }
        } catch (Exception e) {
            response.sendError(333, "Sorry.. error..");
        }
    }

    private List<Product> getProductList(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        List<Product> productList = new ArrayList<Product>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT id, name, price FROM Product");
            while (resultSet.next()) {
                Product product = new Product();
                product.id = resultSet.getInt(1);
                product.name = resultSet.getString(2);
                product.price = resultSet.getString(3);
                productList.add(product);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return productList;
    }
}
