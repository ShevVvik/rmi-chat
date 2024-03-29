package server;

import server.Messages.CommonMessage;
import server.Messages.Message;
import server.Messages.PrivateMessage;
import server.Messages.TechnicalMessage;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*

 */

public class ChatServerImpl implements IChatServer {

    private IChatServer stub;
    private Registry registry;
    private Sender sender;
    private List<ChatUser> listRecipient;

    public ChatServerImpl() {
        this.sender = new Sender(this);
    }

    void initializeServer() {
        try {
            stub = (IChatServer) UnicastRemoteObject.exportObject(this, 0);
            registry = LocateRegistry.createRegistry(1099);
            registry.bind("Chat", stub);
            this.sender.start();
            listRecipient = new CopyOnWriteArrayList<ChatUser>();
            System.out.println("Server ready");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String connect(String name) {
        try {
            ChatUser newUser = new ChatUser(name);
            if (true){
                sendTechnicalMessage(name + " connected to the server!");
                listRecipient.add(newUser);
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
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
        }
        if (user != null) {
            listRecipient.remove(user);
            sendTechnicalMessage(name + " has left the server!");
        }
    }

    @Override
    public <T> void send(T mes, String username) {
        Date currentDate = new Date();
        Message<T> message = new CommonMessage<T>(currentDate, username,  mes, listRecipient);
        sender.addMessage(message);
    }

    @Override
    public <T> void sendPrivate(String nameRecipient, T mes, String username) {
        Date currentDate = new Date();
        Message<T> message = null;
        for(ChatUser user: listRecipient) {
            if(user.getUsername().equals(nameRecipient)) {
                message = new PrivateMessage<T>(currentDate, username,  mes, user);
                break;
            }
        }
        sender.addMessage(message);
    }

    private void sendTechnicalMessage(String textMessage) {
        Message message = new TechnicalMessage(textMessage, listRecipient);
        sender.addMessage(message);
    }

    @Override
    public List<String> getUserList() {
        List<String> list = new ArrayList<>();
        for(ChatUser user: listRecipient) {
            list.add(user.getUsername());
        }
        return list;
    }
}
