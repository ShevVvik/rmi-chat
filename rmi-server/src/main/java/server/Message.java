package server;

import java.io.Serializable;
import java.util.Date;

public class Message<T> implements Serializable {

    protected Date timeMessage;
    protected String nameAuthor;
    protected T content;

    public Message(Date timeMessage, String nameAuthor, T content) {
        this.timeMessage = timeMessage;
        this.nameAuthor = nameAuthor;
        this.content = content;
    }

    public Date getTimeMessage() {
        return timeMessage;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return timeMessage +
                " - " + nameAuthor +
                ": " + content;
    }
}
