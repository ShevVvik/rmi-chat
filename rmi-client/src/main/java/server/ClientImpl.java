package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientImpl extends UnicastRemoteObject implements IChatClient {

    private String username = "Dima";

    protected ClientImpl() throws RemoteException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        username = sc.nextLine();
    }

    public void initializeConnection() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099);
            Scanner sc =new Scanner(System.in);

            Naming.rebind("ConnectedUser_" + username, this);
            IChatServer stub = (IChatServer) registry.lookup("Chat");
            System.out.println(stub.connect(username));
            while (true) {
                sc.hasNext();
                stub.send(sc.nextLine(), username);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String getMessageFromServer(Message message) throws RemoteException {
        System.out.println(message.toString());
        return message.toString();
    }
}
