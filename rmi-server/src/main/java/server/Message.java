package server;

public abstract class Message<T> {
    T content;
    abstract String print();
}