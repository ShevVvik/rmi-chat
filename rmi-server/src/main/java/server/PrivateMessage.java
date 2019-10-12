package server;

import java.util.Date;

public class PrivateMessage<T> extends Message {
    private String nameRecipient;

    public PrivateMessage(Date timeMessage, String nameAuthor, Object content, String nameRecipient) {
        super(timeMessage, nameAuthor, content);
        this.nameRecipient = nameRecipient;
    }

    public String getNameRecipient() {
        return nameRecipient;
    }

    public void setNameRecipient(String nameRecipient) {
        this.nameRecipient = nameRecipient;
    }

    @Override
    public String toString() {
        return timeMessage +
                " - " + nameAuthor +
                "(to " + nameRecipient +
                "): " + content;
    }
}
