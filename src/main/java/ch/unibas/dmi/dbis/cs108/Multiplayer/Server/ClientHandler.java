package ch.unibas.dmi.dbis.cs108.Multiplayer.Server;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private String clientUserName;
    private BufferedWriter out;
    private BufferedReader in;
    private Socket socket;
    Scanner sc;
    public static HashSet<ClientHandler> clientHandlers = new HashSet<>();

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));
            this.clientUserName = in.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUserName + " has joined the Server");
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
        }
    }

    @Override
    public void run() {
        String msg;

        while(socket.isConnected()) {
            try {

                msg = in.readLine();
                if( msg.equals("QUIT")){
                    broadcastMessage("Client: " + clientUserName + " has left the Server");
                }
                broadcastMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, in, out);
                break;
            }
        }
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void broadcastMessage(String msg) {
        for (ClientHandler client : clientHandlers) {
            try {
                if(!client.clientUserName.equals((clientUserName))) {
                    client.out.write(msg);
                    client.out.newLine();
                    client.out.flush();
                } else {
                    client.out.write("Message: **" + msg + "** sent!");
                    client.out.newLine();
                    client.out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, in ,out);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUserName + " has left the server");
    }

    public void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
        removeClientHandler();
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
