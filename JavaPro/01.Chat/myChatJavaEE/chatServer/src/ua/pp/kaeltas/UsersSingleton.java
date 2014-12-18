package ua.pp.kaeltas;

import java.security.SecureRandom;
import java.util.*;

/**
 * Created by kaeltas on 11.12.14.
 */
public class UsersSingleton {
    private static UsersSingleton instance;

    Map<String, String> usersDb;
    Map<String, String> usersCookiesDb;

    Set<String> onlineUsers = new TreeSet<>();

    private UsersSingleton() {
        usersDb = new HashMap<>();
        usersCookiesDb = new HashMap<>();
        usersDb.put("user1", "user1");
        usersDb.put("user2", "user2");
        usersDb.put("user3", "user3");
    }

    public static UsersSingleton getInstance() {
        if (instance == null) {
            instance = new UsersSingleton();
        }
        return instance;
    }

    public synchronized boolean isValidateUserCredentials(String login, String password) {
        if (usersDb.containsKey(login) && password.equals(usersDb.get(login)) ) {
            return true;
        }
        return false;
    }

    public synchronized String getLoginByCookie(String cookie) {
        return usersCookiesDb.get(cookie);
    }

    //todo: probably extract next several methods to new class User
    public synchronized boolean isUserExist(String login) {
        return usersDb.containsKey(login);
    }

    public synchronized void setUserStatusOnline(String login) {
        onlineUsers.add(login);
    }

    public synchronized void setUserStatusOffline(String login) {
        onlineUsers.remove(login);
    }

    public synchronized boolean isUserStatusOnline(String login) {
        return onlineUsers.contains(login);
    }

    public synchronized Set<String> getOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers);
    }

    public synchronized void addUser(String login, String password, String cookie) {
        usersDb.put(login, password);
        usersCookiesDb.put(login, cookie);
    }

    public synchronized Set<String> getUsers() {
        return Collections.unmodifiableSet(new TreeSet(usersDb.keySet()));
    }

    //create unique hashcode for user - to use in cookie
    public synchronized String nextAuthCode() {
        String cookieAuthCode = null;
        do {
            SecureRandom secureRandom = new SecureRandom();
            byte[] hash = new byte[32];
            secureRandom.nextBytes(hash);

            cookieAuthCode = new String(hash);
        } while (getLoginByCookie(cookieAuthCode) != null);

        return cookieAuthCode;
    }
}

