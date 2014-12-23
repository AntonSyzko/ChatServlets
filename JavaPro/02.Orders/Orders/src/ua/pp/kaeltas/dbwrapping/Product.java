package ua.pp.kaeltas.dbwrapping;

import java.io.Serializable;

/**
 * Created by kaeltas on 22.12.14.
 */
public class Product implements Serializable{
    public int id;
    public String name;
    public String price;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
