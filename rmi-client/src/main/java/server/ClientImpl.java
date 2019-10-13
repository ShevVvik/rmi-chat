package server;

import java.io.Serializable;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;


public class ClientImpl extends UnicastRemoteObject implements IChatClient, Serializable {

    protected ClientImpl() throws RemoteException {
        super();
    }

    @Override
    public String getMessageFromServer(Message message) throws RemoteException {
        System.out.println(message.toString());
        return message.toString();
    }
}
