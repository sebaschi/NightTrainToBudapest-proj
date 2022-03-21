package ch.unibas.dmi.dbis.cs108.Multiplayer.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private static final int gamePort = 42069;
    private static int clientIndex = 0;
    private HashMap<String, Integer> nameToIndex = new HashMap<>();
    private HashMap<Integer, ClientHandler> indexToHandler = new HashMap<>();

    public static void main(String[] args) {

        try {
            ServerSocket gameServer = new ServerSocket(gamePort);
            System.out.println("Waiting for a connection on Port 42069");
            while(true) {
                Socket client = gameServer.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
