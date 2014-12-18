package ua.pp.kaeltas;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by kaeltas on 16.12.14.
 */
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UsersSingleton usersSingleton = UsersSingleton.getInstance();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null && password != null && !login.isEmpty() && !password.isEmpty()) {
            if (usersSingleton.isUserExist(login)) {
                if (usersSingleton.isValidateUserCredentials(login, password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("login", login);
                    session.setMaxInactiveInterval(30 * 60); //30min
                    Cookie cookie = new Cookie("authcode", usersSingleton.nextAuthCode());
                    cookie.setMaxAge(30 * 60);
                    response.addCookie(cookie);
                    response.sendRedirect("/chat");
                } else {
                    request.setAttribute("errorAuth", "Wrong password!");
                    request.setAttribute("lastLogin", login);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else { //create user
                String authCode = usersSingleton.nextAuthCode();
                usersSingleton.addUser(login, password, authCode);

                HttpSession session = request.getSession();
                session.setAttribute("login", login);
                session.setMaxInactiveInterval(30 * 60); //30min
                Cookie cookie = new Cookie("authcode", authCode);
                cookie.setMaxAge(30 * 60);
                response.addCookie(cookie);
                response.sendRedirect("/chat");
            }
        } else {
            if (login != null && !login.isEmpty()) {
                request.setAttribute("lastLogin", login);
            }
            request.setAttribute("errorAuth", "Login/password can't be empty!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.sendRedirect("/login");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
