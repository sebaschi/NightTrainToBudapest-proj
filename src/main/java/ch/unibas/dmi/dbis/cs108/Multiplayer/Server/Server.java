package ch.unibas.dmi.dbis.cs108.Multiplayer.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    int port = 42069;

    public static void main(String[] args) {

        try {
            ServerSocket gameServer = new ServerSocket(42069);
            System.out.println("Waiting for a connection on Port 42069");
            while(true) {
                Socket connector = gameServer.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
