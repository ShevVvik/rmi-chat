package server;

import java.rmi.RemoteException;

public class Client{



    public static void main(String[] args) {
        ChatController controller = new ChatController();
        controller.start();
    }


}
