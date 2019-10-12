package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatClient extends Remote {

    String getMessageFromServer(Message message) throws RemoteException;

}
