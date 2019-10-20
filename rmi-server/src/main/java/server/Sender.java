package server;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender extends Thread {

    private BlockingQueue<Message> messageList;
    private List<ChatUser> listRecipient;

    public Sender(Message message, List<ChatUser> listRecipient) {
        this();
        this.messageList.add(message);
        this.listRecipient = new CopyOnWriteArrayList<ChatUser>(listRecipient);
    }

    public Sender() {
        messageList = new LinkedBlockingQueue<>();
        listRecipient = new CopyOnWriteArrayList<ChatUser>();
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public void addRecipient(ChatUser recipient) {
        this.listRecipient.add(recipient);
    }


    @Override
    public void run() {
        while (true) {
            try {
                Message mes = messageList.take();
                int numberOfTask = listRecipient.size();
                int numberOfThread = 4;
                if (numberOfTask >= 4) {
                    int stepInList = numberOfTask / numberOfThread;
                    for (int i = 0; i < 4; i++) {
                        HelperWithSend thread = new HelperWithSend(listRecipient.subList(numberOfTask - stepInList, numberOfTask), mes);
                        thread.start();
                        stepInList = numberOfTask / numberOfThread;
                        numberOfThread--;
                        numberOfTask-=stepInList;
                    }
                } else {
                    HelperWithSend thread = new HelperWithSend(listRecipient, mes);
                    thread.start();
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void removeRecipient(ChatUser user) {
    }

    class HelperWithSend extends Thread {

        private List<ChatUser> sublistRecipient;
        private Message message;

        HelperWithSend(List<ChatUser> list, Message message) {
            this.sublistRecipient = list;
            this.message = message;
        }

        @Override
        public void run() {
            Iterator<ChatUser> iterator = sublistRecipient.iterator();
            while (iterator.hasNext()) {
                try {
                    iterator.next().getUserConnection().getMessageFromServer(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
