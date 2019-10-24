package server;

import java.util.List;
import java.util.Scanner;

public class ChatController {

    private Connector connector;
    private String username;
    private boolean connection;

    public void start() {
        connector = new Connector();
        Scanner input = new Scanner(System.in);
        System.out.println("Simple RMI chat application" +
                "\nUse /help to get info");

        while (true) {
            StringBuilder command = new StringBuilder(input.nextLine());
            switch(parse(command)) {
                case HELP: {
                    System.out.println("\nCommand list for chat:\n" +
                            "/connect - use to connect to server\n" +
                            "/disconnect - use to disconnect\n" +
                            "/pm <USERNAME> - use to send private message\n" +
                            "/auto - use to auto-connect to last server\n" +
                            "/exit - exit from application");
                } break;
                case CONNECT: {
                    connectionCommand();
                } break;
                case DISCONNECT: {
                    disconnectionCommand();
                } break;
                case COMMON_MESSAGE: {
                    commonMessageCommand(command);
                } break;
                case PRIVATE_MESSAGE: {
                    privateMessageCommand(command);
                } break;
                case USER_LIST: {
                    userListCommand();
                } break;
                case AUTO_LOGIN: {
                    autoLoginCommand();
                } break;
                case EXIT: {
                    disconnectionCommand();
                    System.exit(0);
                } default: {

                }
            }
        }

    }

    private void autoLoginCommand() {
        connector.connect(AutiLoginUttil.loadConnectionData());
    }

    private void userListCommand() {
        List<String> userList = connector.getUserList();
        System.out.println("User list: ");
        for(String user: userList) {
            System.out.println(user);
        }
    }

    private void connectionCommand() {
        System.out.println("\nConnection to server:");
        if(connector.connect()) {
            System.out.println("Welcome, " + username + "!");
            connection = true;
        } else {
            System.out.println("Sorry, we have a problem with connection to server.");
        }
    }

    private void disconnectionCommand() {
        if (!connection) {
            System.out.println("\nConnection: false");
        } else {
            System.out.println("\nDisconnecting from the server...");
            connector.disconnect();
            System.out.println("Disconnect successful!");
            connection = false;
        }
    }

    private void commonMessageCommand(StringBuilder message) {
        if (!connection) {
            System.out.println("Connection: false\n" +
                    "use /connect to connect to the server");
        } else if (!connector.send(message.toString())) {
            System.out.println("Sorry, we have problems sending a message.");
        };
    }

    private void privateMessageCommand(StringBuilder message) {
        if (!connection) {
            System.out.println("Connection: false");
        } else {
            String usernameRecipient = parsePrivateMessage(message);

            if (!connector.sendPrivateMessage(message.toString(), usernameRecipient)) {
                System.out.println("Sorry, we have problems sending a message.");
            };
        }
    }

    private String parsePrivateMessage(StringBuilder message) {
        int indexFirstWhiteSpace = message.indexOf(" ");
        String username = message.substring(0, indexFirstWhiteSpace);
        message.replace(0, indexFirstWhiteSpace + 1, "");
        return username;
    }

    private ControlCommand parse(StringBuilder row) {
        if(row.indexOf("/help") == 0)
        {
            row.replace(0, 4, "");
            return ControlCommand.HELP;
        }
        if(row.indexOf("/pm") == 0)
        {
            row.replace(0, 4, "");
            return ControlCommand.PRIVATE_MESSAGE;
        }
        if(row.indexOf("/connect") == 0)
        {
            row.replace(0, 7, "");
            return ControlCommand.CONNECT;
        }
        if(row.indexOf("/disconnect") == 0)
        {
            row.replace(0, 10, "");
            return  ControlCommand.DISCONNECT;
        }
        if(row.indexOf("/users") == 0)
        {
            return  ControlCommand.USER_LIST;
        }
        if(row.indexOf("/auto") == 0)
        {
            return  ControlCommand.USER_LIST;
        }
        if(row.indexOf("/exit") == 0)
        {
            return  ControlCommand.EXIT;
        }
        return ControlCommand.COMMON_MESSAGE;
    }


}
