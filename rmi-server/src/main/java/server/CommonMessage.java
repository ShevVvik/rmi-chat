package server;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonMessage<T> extends Message implements Serializable {

    protected Date timeMessage;
    protected String nameAuthor;
    protected T content;

    public CommonMessage(Date timeMessage, String nameAuthor, T content) {
        this(timeMessage, nameAuthor, content, null);
    }

    public CommonMessage(Date timeMessage, String nameAuthor, T content, List<ChatUser> recipients) {
        this.timeMessage = timeMessage;
        this.nameAuthor = nameAuthor;
        this.content = content;
        this.listRecipient = recipients;
    }

    @Override
    public String toString() {
        return timeMessage +
                " - " + nameAuthor +
                ": " + content;
    }

    @Override
    List<ChatUser> getListRecipient() {
        return listRecipient;
    }

    @Override
    String print() {
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        String result = date.format(this.timeMessage) + " - " + nameAuthor + ": " + this.content;
        return result;
    }
}
