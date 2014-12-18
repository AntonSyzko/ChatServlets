package ua.pp.kaeltas;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private MessageList msgList = MessageList.getInstance();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException 
	{
		HttpSession session = request.getSession();
		if (session.getAttribute("login") != null) { //check, if we authorized
			Message m = null;

			try {
				m = Message.readFromReaderGson(request.getReader());
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}

//			if (!UsersSingleton.getInstance().isUserExist(m.from)) {
//				//UsersSingleton.getInstance().addUser(m.from, m.password);
//				//todo: code gets broken here =))
//			}
			if (m.text != null && !m.text.isEmpty()) {
				m.from = (String)session.getAttribute("login");
				m.date = new Date();

				msgList.add(m);
			}
			response.setStatus(200);
		} else {
			response.sendRedirect("/");
		}
	}
}
