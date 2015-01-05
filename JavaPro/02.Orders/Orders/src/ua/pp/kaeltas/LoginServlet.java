package ua.pp.kaeltas;

import com.google.appengine.api.search.Results;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;

/**
 * Created by kaeltas on 22.12.14.
 */
public class LoginServlet extends HttpServlet {

    Boolean isAdmin = false;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            Connection conn = MysqlConnectionFactory.createConnection();
            try {
                if (login != null && password != null && !login.isEmpty() && !password.isEmpty()) {

                    if (isValidateUserCredentials(conn, login, password)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("login", login);
                        session.setAttribute("isAdmin", isAdmin);
                        session.setMaxInactiveInterval(30 * 60); //30min
                        //Cookie cookie = new Cookie("authcode", nextAuthCode(conn));
                        //cookie.setMaxAge(30 * 60);
                        //response.addCookie(cookie);
                        response.sendRedirect("/userpanel");
                    } else {
                        request.setAttribute("errorAuth", "Wrong password!");
                        request.setAttribute("lastLogin", login);
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                } else {
                    if (login != null && !login.isEmpty()) {
                        request.setAttribute("lastLogin", login);
                    }
                    request.setAttribute("errorAuth", "Login/password can't be empty!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } finally {
                conn.close();
            }
        } catch (Exception e) {
            response.sendError(333, "Sorry, something goes wrong..");
            e.printStackTrace();
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.sendRedirect("/login");
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            response.sendRedirect("/userpanel");
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private boolean isValidateUserCredentials(Connection conn, String login, String password) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT isAdmin FROM User WHERE login=? AND password=?");
        Boolean result = false;
        try {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isAdmin = resultSet.getBoolean(1);
                result = true;
            } else {
                result = false;
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return result;
    }

    /*private String nextAuthCode(Connection conn) throws SQLException {
        String cookieAuthCode = null;
        boolean isContinue = false;
        do {
            SecureRandom secureRandom = new SecureRandom();
            byte[] hash = new byte[32];
            secureRandom.nextBytes(hash);

            cookieAuthCode = new String(hash);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM User WHERE authcode = " + cookieAuthCode);
            isContinue = resultSet.next();
        } while (isContinue);

        return cookieAuthCode;
    }*/
}
