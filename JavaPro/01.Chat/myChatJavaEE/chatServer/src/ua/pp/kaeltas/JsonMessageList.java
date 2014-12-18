package ua.pp.kaeltas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaeltas on 11.12.14.
 */
public class JsonMessageList {
    public List<Message> msgList  = new ArrayList<>();
    public int lastProcessedMessageNum;

    public JsonMessageList(List<Message> msgList, int lastProcessedMessageNum) {
        this.msgList = msgList;
        this.lastProcessedMessageNum = lastProcessedMessageNum;
    }

    public JsonMessageList() {
    }
}
