package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatClient extends Remote {

    String getMessageFromServer(String commonMessage) throws RemoteException;

}
