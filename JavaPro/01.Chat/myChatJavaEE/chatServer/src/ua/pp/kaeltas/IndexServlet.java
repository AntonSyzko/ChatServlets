package ua.pp.kaeltas;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by kaeltas on 16.12.14.
 */
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //todo: add session
        boolean isAuthorizedBySession = false;
        boolean isAuthorizedByCookie = false;
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) { //authorize by session
            isAuthorizedBySession = true;
        } else if (request.getCookies() != null) { //authorizse by cookie
            for (Cookie cookie : request.getCookies()) {
                if ("authcode".equals(cookie.getName())) {
                    String login =null;
                    if ((login=UsersSingleton.getInstance().getLoginByCookie(cookie.getValue())) != null) {
                        isAuthorizedByCookie = true;
                        session.setAttribute("login", login);
                        session.setMaxInactiveInterval(30*60); //30 min
                        break;
                    }
                }
            }
        }

        if (isAuthorizedBySession || isAuthorizedByCookie) {
            response.sendRedirect("/chat");
        } else {
            response.sendRedirect("/login");
        }

    }
}
