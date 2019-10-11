package server;


public class ChatUser{

    private String username;
    private int id;
    private String userConnectionName;
    private IChatClient userConnection;

    public ChatUser(String name) {
        this.username = name;

        this.id = ChatServerImpl.getNextId();
        this.userConnectionName = "ConnectedUser_" + 2;
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
