package ua.pp.kaeltas;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
	
	private static final MessageList msgList = new MessageList();

	private final List<Message> list = new ArrayList<Message>();

	private MessageList() {
		Message m = new Message();
		m.text = "Hi, user1!";
		m.to = "user1";
		m.from = "user2";
		list.add(m);

		m = new Message();
		m.text = "Hi all!";
		m.to = "";
		m.from = "user1";
		list.add(m);

		m = new Message();
		m.text = "Hi, user2!";
		m.to = "user2";
		m.from = "user1";
		list.add(m);
	}
	
	public static MessageList getInstance() {
		return msgList;
	}
	
	public synchronized void add(Message m) {
		list.add(m);
	}
	
	public synchronized JsonMessageList get(int fromN, String login, String password) {
		List<Message> res = new ArrayList<Message>();

		for (int i = fromN; i < list.size(); i++) {
			Message m = list.get(i);
			if (!m.isPrivate()) {
				res.add(m);
			} else {
				if (login.equals(m.from) ||
						(login.equals(m.to) && UsersSingleton.getInstance().isValidateUserCredentials(login, password)) ) {
					res.add(m);
				}
			}
		}

		return new JsonMessageList(res, list.size());
	}

	public synchronized JsonMessageList get(int fromN, String login) {
		List<Message> res = new ArrayList<Message>();

		for (int i = fromN; i < list.size(); i++) {
			Message m = list.get(i);
			if (!m.isPrivate()) {
				res.add(m);
			} else {
				if ( login.equals(m.from) || login.equals(m.to) ) {
					res.add(m);
				}
			}
		}

		return new JsonMessageList(res, list.size());
	}

	public synchronized List<Message> get() {
		List<Message> res = new ArrayList<Message>();

		res.addAll(list);

		return res;
	}
}
