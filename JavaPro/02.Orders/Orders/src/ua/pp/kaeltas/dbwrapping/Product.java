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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
