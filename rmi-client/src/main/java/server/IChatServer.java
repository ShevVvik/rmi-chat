package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatServer extends Remote {

    String addClient(String name) throws RemoteException;
    void sendAll(String message) throws RemoteException;
    String send(String message) throws RemoteException;

}
