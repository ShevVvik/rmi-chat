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

    private IChatServer stub;
    private Registry registry;
    private Sender sender;

    public ChatServerImpl() {
        this.sender = new Sender();
    }

    void initializeServer() {
        try {
            stub = (IChatServer) UnicastRemoteObject.exportObject(this, 0);
            registry = LocateRegistry.createRegistry(1099);
            registry.bind("Chat", stub);
            this.sender.start();
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
            if (true){
                this.sender.addRecipient(newUser);
            } else {
                return "This username is already taken";
            }

        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            return "Error with connection to server!";
        }
        return "Success";
    }

    @Override
    public void disconnect(String name) {
        ChatUser user = null;
        try {
            user = new ChatUser(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        if (user != null) {
            sender.removeRecipient(user);
        }
    }

    @Override
    public <T> void send(T mes, String username) throws RemoteException {
        Date currentDate = new Date();
        Message<T> message = new Message<T>(currentDate, username,  mes);
        sender.addMessage(message);
    }

    @Override
    public <T> void sendPrivate(String nameRecipient, T mes, String username) throws RemoteException {
        Date currentDate = new Date();
        Message<T> message = new Message<T>(currentDate, username,  mes);
        /*for(ChatUser user: se) {
            if (user.getUsername().equals(nameRecipient)) {
                user.getUserConnection().getMessageFromServer(message);
            }
        }*/
    }
/*
    private ChatUser checkUserInUserList(ChatUser user) {
        ChatUser result = null;
        for(ChatUser u: clientList) {
            if (u.equals(user)) {
                result = u;
                break;
            }
        }
        return user;
    }

    private ChatUser checkUsernameInUserList(String username) {
        ChatUser result = null;
        for(ChatUser u: clientList) {
            if (u.getUsername().equals(username)) {
                result = u;
                break;
            }
        }
        return result;
    }
*/
}
