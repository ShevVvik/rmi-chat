package server;

import java.util.Scanner;

public class ChatController {

    private Connector connector;
    private String username;
    private boolean connection;

    public void start() {
        Scanner input = new Scanner(System.in);
        System.out.println("Simple RMI chat application");
        System.out.print("Enter username: ");
        username = input.nextLine();

        while (true) {
            StringBuilder command = new StringBuilder(input.nextLine());
            switch(parse(command)) {
                case HELP: {
                    System.out.println("Command list for chat:\n" +
                            "/connect - use to connect to server\n" +
                            "/disconnect - use to disconnect\n" +
                            "/pm <USERNAME> - use to send private message\n" +
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
            }
        }

    }

    private void connectionCommand() {
        System.out.println("Connection to server:");
        connector = new Connector(username);
        if(connector.connect()) {
            System.out.println("Welcome, " + username + "!");
            connection = true;
        } else {
            System.out.println("Sorry, we have a problem with connection to server.");
        }
    }

    private void disconnectionCommand() {
        if (!connection) {
            System.out.println("Connection: false");
        }
        System.out.println("Disconnecting from the server...");
        connector.disconnect();
        System.out.println("Disconnect successful!");
        connection = false;
    }

    private void commonMessageCommand(StringBuilder message) {
        if (!connection) {
            System.out.println("Connection: false");
        }
        if (!connector.send(message.toString())) {
            System.out.println("Sorry, we have problems sending a message.");
        };
    }

    private void privateMessageCommand(StringBuilder message) {
        if (!connection) {
            System.out.println("Connection: false");
        }

        String usernameRecipient = parsePrivateMessage(message);

        if (!connector.sendPrivateMessage(message.toString(), usernameRecipient)) {
            System.out.println("Sorry, we have problems sending a message.");
        };
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
        return ControlCommand.COMMON_MESSAGE;
    }


}
