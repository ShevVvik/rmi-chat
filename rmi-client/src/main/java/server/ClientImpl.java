package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientImpl extends UnicastRemoteObject implements IChatClient {

    protected ClientImpl() throws RemoteException {
    }

    public void initializeConnection() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099);
            Scanner sc =new Scanner(System.in);

            Naming.rebind("ConnectedUser_2", this);
            IChatServer stub = (IChatServer) registry.lookup("Chat");


            stub.addClient("Dima");
            stub.sendAll("Hi, all!");
            while (true) {
                sc.hasNext();
                stub.sendAll(sc.nextLine());
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String getMessageFromServer(String message) throws RemoteException {
        System.out.println(message);
        return message;
    }
}
