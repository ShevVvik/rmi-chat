package server;

import java.rmi.RemoteException;

public class Client{



    public static void main(String[] args) {
        ClientImpl client = null;
        try {
            client = new ClientImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        client.initializeConnection();
    }


}
