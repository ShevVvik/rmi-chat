package server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ChatServerImpl implements IChatServer {

    private List<ChatUser> clientList;
    private IChatServer stub;
    private Registry registry;

    public ChatServerImpl() {
        this.clientList = new LinkedList<>();
    }

    void initializeServer() {
        try {
            stub = (IChatServer) UnicastRemoteObject.exportObject(this, 0);
            registry = LocateRegistry.createRegistry(1099);
            registry.bind("Chat", stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String connect(String name) {
        try {
            ChatUser newUser = new ChatUser(name);
            this.clientList.add(newUser);
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            return "Error with connection to server!";
        }
        return "Success";
    }

    @Override
    public void disconnect(String name) throws RemoteException {

    }

    @Override
    public <T> void send(T mes, String username) throws RemoteException {
        if (clientList == null) return;
        System.out.println(mes);
        Date currentDate = new Date();
        Message<T> message = new Message<T>(currentDate, username,  mes);

        for(ChatUser user: clientList) {
            user.getUserConnection().getMessageFromServer(message);
        }
    }

    @Override
    public <T> void sendPrivate(String nameRecipient, T mes, String username) throws RemoteException {
        if (clientList == null) return;

        Date currentDate = new Date();
        Message<T> message = new Message<T>(currentDate, username,  mes);

        for(ChatUser user: clientList) {
            if (user.getUsername().equals(nameRecipient)) {
                user.getUserConnection().getMessageFromServer(message);
            }
        }
    }

}
