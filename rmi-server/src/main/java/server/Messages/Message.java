package server.Messages;

import server.ChatUser;

import java.util.List;

public abstract class Message<T> {
    T content;
    List<ChatUser> listRecipient;
    public abstract List<ChatUser> getListRecipient();
    public abstract String print();
}