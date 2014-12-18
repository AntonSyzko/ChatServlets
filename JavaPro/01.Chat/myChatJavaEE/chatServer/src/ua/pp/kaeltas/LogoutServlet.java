package ua.pp.kaeltas;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kaeltas on 18.12.14.
 */
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("login", null);
        Cookie cookie = new Cookie("authcode", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        response.sendRedirect("/");
    }
}
