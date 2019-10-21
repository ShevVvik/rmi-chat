package server;

import java.util.List;

public class TechnicalMessage extends Message {

    public TechnicalMessage(String content, List<ChatUser> recipients) {
        this.content = content;
        this.listRecipient = recipients;
    }

    @Override
    List<ChatUser> getListRecipient() {
        return listRecipient;
    }

    @Override
    String print() {
        return content.toString();
    }
}
