package server;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AutiLoginUttil {

    public static void saveConnectionData(ConnectionData connection) {
        Gson gson = new Gson();
        try(FileWriter writer = new FileWriter("autologin.txt")) {
            gson.toJson(connection, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionData loadConnectionData() {
        Gson gson = new Gson();
        ConnectionData connectionData = null;
        try(FileReader reader = new FileReader("autologin.txt")) {
            connectionData = gson.fromJson(reader, ConnectionData.class);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return connectionData;
    }
}
