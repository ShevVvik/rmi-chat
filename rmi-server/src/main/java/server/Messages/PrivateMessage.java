package server.Messages;

import server.ChatUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PrivateMessage<T> extends CommonMessage {

    private ChatUser recipient;

    public PrivateMessage(Date timeMessage, String nameAuthor, T content, ChatUser recipient) {
        super(timeMessage, nameAuthor, content);
        this.recipient = recipient;
    }

    @Override
    public List<ChatUser> getListRecipient() {
        List<ChatUser> listRecipient = new CopyOnWriteArrayList<>();
        listRecipient.add(recipient);
        return listRecipient;
    }

    @Override
    public String toString() {
        return timeMessage +
                " - " + nameAuthor +
                "(to " + listRecipient +
                "): " + content;
    }

    @Override
    public String print() {
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        String result = date.format(this.timeMessage) + " - " + nameAuthor +
                " (to " + recipient.getUsername() + "): " + this.content;
        return result;
    }
}
