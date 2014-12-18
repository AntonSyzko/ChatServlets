package ua.pp.kaeltas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by kaeltas on 11.12.14.
 */
public class GetUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //JsonUserList jsonUserList = new JsonUserList(UsersSingleton.getInstance().getOnlineUsers());
        JsonUserList jsonUserList = new JsonUserList(UsersSingleton.getInstance().getUsers());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Gson gson = new GsonBuilder().create();
        gson.toJson(jsonUserList, response.getWriter());

    }
}
