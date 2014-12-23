package ua.pp.kaeltas;

import ua.pp.kaeltas.dbwrapping.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaeltas on 22.12.14.
 */
public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("isAdmin") != null && (Boolean)session.getAttribute("isAdmin")) {
            String productname = request.getParameter("productname");
            String productprice = request.getParameter("productprice").replace(',', '.');

            if (productname != null && !productname.isEmpty()
                    && productprice != null && !productprice.isEmpty()) {
                try {
                    Connection conn = MysqlConnectionFactory.createConnection();
                    try {
                        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Product (name, price) VALUES (?, ?)");
                        preparedStatement.setString(1, productname);

                        try {
                            BigDecimal bigDecimal = new BigDecimal(productprice);
                            preparedStatement.setBigDecimal(2, bigDecimal);

                            preparedStatement.execute();

                            response.sendRedirect("addproduct");
                        } catch (NumberFormatException nfe) {
                            request.setAttribute("errorprice", "Некорректная цена {вводите только цифры и символ . для указания копеек}");
                            request.setAttribute("productname", productname);
                            request.setAttribute("productprice", productprice);

                            List<Product> productList = getProductList(conn);
                            request.setAttribute("productList", productList);

                            request.getRequestDispatcher("addproduct.jsp").forward(request, response);
                        }
                    } finally {
                        conn.close();
                    }
                } catch (Exception e) {
                    response.sendError(333, "Sorry, something goes wrong..");
                    e.printStackTrace();
                }
            } else {
                response.sendRedirect("addproduct");
            }
        } else {
            response.sendRedirect("/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("isAdmin") != null && (Boolean)session.getAttribute("isAdmin")) {
            try {
                Connection conn = MysqlConnectionFactory.createConnection();
                try {
                    List<Product> productList = getProductList(conn);
                    request.setAttribute("productList", productList);

                    request.getRequestDispatcher("addproduct.jsp").forward(request, response);
                } finally {
                    conn.close();
                }
            } catch (Exception e) {
                response.sendError(333, "Sorry, something goes wrong..");
            }
        } else {
            response.sendRedirect("/");
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
