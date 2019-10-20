package server;

public class TechnicalMessage extends Message {

    @Override
    String print() {
        return content.toString();
    }
}
