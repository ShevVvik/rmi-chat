package server;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonMessage<T> extends Message implements Serializable {

    protected Date timeMessage;
    protected String nameAuthor;
    protected T content;

    public CommonMessage(Date timeMessage, String nameAuthor, T content) {
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

    @Override
    String print() {
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        String result = date.format(this.timeMessage) + " - " + nameAuthor + ": " + this.content;
        return result;
    }
}
