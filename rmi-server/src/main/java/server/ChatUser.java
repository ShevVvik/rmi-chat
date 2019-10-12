package server;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChatUser {

    private String username;
    private String userConnectionName;
    private IChatClient userConnection;

    public ChatUser(String name) throws RemoteException, MalformedURLException, NotBoundException {
        this.username = name;
        this.userConnectionName = "ConnectedUser_" + name;
        this.userConnection = ( IChatClient ) Naming.lookup(this.userConnectionName);
    }

    public String getUsername() {
        return username;
    }

    public IChatClient getUserConnection() {
        return userConnection;
    }

    public void setUserConnection(IChatClient userConnection) {
        this.userConnection = userConnection;
    }

    public String getUserConnectionName() {
        return userConnectionName;
    }
}
