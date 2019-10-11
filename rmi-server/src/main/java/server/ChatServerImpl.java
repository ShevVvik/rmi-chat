package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class ChatServerImpl implements IChatServer {

    private List<ChatUser> clientList;
    private static int nextId;

    public ChatServerImpl() {
        this.clientList = new LinkedList<>();
    }

    public static int getNextId() {
        return nextId++;
    }

    @Override
    public String addClient(String name) throws RemoteException {
        ChatUser newUser = new ChatUser(name);
        this.clientList.add(createChatUser(newUser));
        return newUser.getUserConnectionName();
    }

    @Override
    public void sendAll(String message) throws RemoteException {
        for (ChatUser client: this.clientList) {
            client.getUserConnection().getMessageFromServer(message);
        }
    }

    @Override
    public String send(String message) throws RemoteException {
        return message;
    }

    private ChatUser createChatUser(ChatUser user) {
        try {
            IChatClient connection = ( IChatClient ) Naming.lookup(user.getUserConnectionName());
            user.setUserConnection(connection);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
