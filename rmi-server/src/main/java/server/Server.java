package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server  {

    public static void main(String args[]) {

        try {
            ChatServerImpl obj = new ChatServerImpl();
            IChatServer stub = (IChatServer) UnicastRemoteObject.exportObject(obj, 0);


            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Chat", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
