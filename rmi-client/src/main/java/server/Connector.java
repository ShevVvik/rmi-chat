package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Connector {

    private ConnectionData connectionData;

    private Registry registry;
    private IChatServer stub;

    public boolean connect() {
        connectionData = new ConnectionData();
        Scanner input = new Scanner(System.in);
        System.out.print("Host: ");
        connectionData.setHostname(input.nextLine());
        System.out.print("Port: ");
        connectionData.setPort(input.nextInt());
        System.out.print("Enter username: ");
        connectionData.setUsername(input.nextLine().split(" ")[0]);
        return connect(connectionData);
    }

    public boolean connect(ConnectionData connectionData) {
        try {
            registry = LocateRegistry.getRegistry(connectionData.getHostname(), connectionData.getPort());
            ClientImpl client = new ClientImpl();
            registry.rebind("ConnectedUser_" + connectionData.getUsername(), client);
            stub = (IChatServer) registry.lookup("Chat");
            stub.connect(connectionData.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        AutiLoginUttil.saveConnectionData(connectionData);
        return true;
    }

    public boolean disconnect() {
        try {
            stub.disconnect(connectionData.getUsername());
            registry.unbind("ConnectedUser_" + connectionData.getUsername());
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
            stub.send(message, connectionData.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean sendPrivateMessage(String message, String usernameRecipient) {
        try {
            stub.sendPrivate(usernameRecipient, message, connectionData.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<String> getUserList() {
        List<String> userList = null;
        try {
            userList = stub.getUserList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return userList;
    }

}
