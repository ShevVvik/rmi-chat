package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatServer extends Remote {

    String connect(String name) throws RemoteException;
    void disconnect(String name) throws RemoteException;
    <T> void send(T mes, String username) throws RemoteException;
    <T> void sendPrivate(String nameRecipient, T mes, String username) throws RemoteException;
}
