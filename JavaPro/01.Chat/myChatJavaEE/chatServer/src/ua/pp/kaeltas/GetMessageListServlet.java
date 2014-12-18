package ua.pp.kaeltas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessageListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private MessageList msgList = MessageList.getInstance();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		HttpSession session = request.getSession();

		if (session.getAttribute("login") != null) {
			String login = (String)session.getAttribute("login");

			String fromNStr = request.getParameter("fromn");
			try {
				int fromN = Integer.parseInt(fromNStr);

				JsonMessageList jsonMsgList = msgList.get(fromN, login);

				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");

				Gson gson = new GsonBuilder().create();
				gson.toJson(jsonMsgList, response.getWriter());
			} catch (Exception e) {
				response.sendError(333, "Something goes wrong..");
			}
		} else {
			response.sendRedirect("/");
		}
	}
}
