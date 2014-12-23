package ua.pp.kaeltas;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kaeltas on 22.12.14.
 */
public class AdminPanelServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        if ((Boolean)httpSession.getAttribute("isAdmin")) {
            request.getRequestDispatcher("adminpanel.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }
}
