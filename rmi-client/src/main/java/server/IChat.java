package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote {

    void addClient(IChat client) throws RemoteException;
    void sendAll(String message) throws RemoteException;
    String send(String message) throws RemoteException;
}
