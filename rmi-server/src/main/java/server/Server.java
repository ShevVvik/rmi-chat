package server;

public class Server  {

    public static void main(String args[]) {
        System.out.println("Test2");
        ChatServerImpl obj = new ChatServerImpl();
        obj.initializeServer();

    }
}
