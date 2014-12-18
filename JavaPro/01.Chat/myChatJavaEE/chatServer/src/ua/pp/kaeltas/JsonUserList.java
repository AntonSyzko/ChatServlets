package ua.pp.kaeltas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kaeltas on 11.12.14.
 */
public class JsonUserList {
    public Set<String> users = new HashSet<>();

    public JsonUserList(Set<String> users) {
        this.users = users;
    }

    public JsonUserList() {
    }
}
