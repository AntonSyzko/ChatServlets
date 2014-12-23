package ua.pp.kaeltas.dbwrapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaeltas on 23.12.14.
 */
public class Order {
    public int id;
    public int user_id;
    public String login;
    public String datetime;
    public List<Product> orderData;

    public Order() {
        orderData = new ArrayList<Product>();
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getLogin() {
        return login;
    }

    public List<Product> getOrderData() {
        return orderData;
    }

    public String getDatetime() {
        return datetime;
    }
}
