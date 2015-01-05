package ua.pp.kaeltas.dbwrapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaeltas on 23.12.14.
 */
public class Order {
    public int id;
    public int user_id;
    public String login;
    public String datetime;
    public Map<Product, Integer> orderData;

    public Order() {
        orderData = new LinkedHashMap<Product, Integer>();
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

    public Map<Product, Integer> getOrderData() {
        return orderData;
    }

    public String getDatetime() {
        return datetime;
    }
}
