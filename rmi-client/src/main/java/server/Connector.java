package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Connector {


    private Registry registry;
    private String username;
    private IChatServer stub;

    public Connector(String username) {
        this.username = username;
    }

    public boolean connect() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Host: ");
            String hostname = input.nextLine();
            System.out.print("Port: ");
            int port = input.nextInt();
            registry = LocateRegistry.getRegistry(hostname, port);
            ClientImpl client = new ClientImpl();
            registry.rebind("ConnectedUser_" + username, client);
            stub = (IChatServer) registry.lookup("Chat");
            stub.connect(username);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean disconnect() {
        try {
            stub.disconnect(username);
            registry.unbind("ConnectedUser_" + username);
            stub = null;
            registry = null;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean send(String message) {
        try {
            stub.send(message, username);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean sendPrivateMessage(String message, String usernameRecipient) {
        try {
            stub.sendPrivate(usernameRecipient, message, username);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



}
