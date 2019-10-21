package server;

import java.util.List;

public abstract class Message<T> {
    T content;
    List<ChatUser> listRecipient;
    abstract List<ChatUser> getListRecipient();
    abstract String print();
}