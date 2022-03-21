package ch.unibas.dmi.dbis.cs108.Multiplayer.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int gamePort = 42069;
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {


            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Port 42069 open on ");
                ClientHandler nextClient = new ClientHandler(socket);

                Thread th = new Thread(nextClient);
                th.start();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(gamePort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server server = new Server(serverSocket);
        server.startServer();
    }

    public static void broadcast(String msg){
        //TODO
    }
}
