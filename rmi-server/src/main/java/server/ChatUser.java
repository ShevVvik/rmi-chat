package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

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
        return new String(username);
    }

    public IChatClient getUserConnection() {
        IChatClient clone = null;
        try {
            clone = ( IChatClient ) Naming.lookup(this.userConnectionName);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public void setUserConnection(IChatClient userConnection) {
        this.userConnection = userConnection;
    }

    public String getUserConnectionName() {
        return userConnectionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatUser chatUser = (ChatUser) o;
        return username.equals(chatUser.username) &&
                userConnectionName.equals(chatUser.userConnectionName) &&
                userConnection.equals(chatUser.userConnection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userConnectionName, userConnection);
    }
}
