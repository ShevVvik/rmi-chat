package server.Messages;

import server.ChatUser;

import java.util.List;

public class TechnicalMessage extends Message {

    public TechnicalMessage(String content, List<ChatUser> recipients) {
        this.content = content;
        this.listRecipient = recipients;
    }

    @Override
    public List<ChatUser> getListRecipient() {
        return listRecipient;
    }

    @Override
    public String print() {
        return content.toString();
    }
}
