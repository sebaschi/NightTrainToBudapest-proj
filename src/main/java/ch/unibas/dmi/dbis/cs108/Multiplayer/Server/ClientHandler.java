package ch.unibas.dmi.dbis.cs108.Multiplayer.Server;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class ClientHandler implements Runnable  {
    private String clientUserName;
    private BufferedWriter out;
    private BufferedReader in;
    private Socket socket;
    Scanner sc;
    public static HashSet<ClientHandler> connectedClients = new HashSet<>();
    public static HashSet<ClientHandler> inGameClients = new HashSet<>();
    public static HashSet<ClientHandler> ghostClients = new HashSet<>();
    private ClientMsgDecoder clientMsgDecoder = new ClientMsgDecoder();

    /**
     * Implements the connecting logik in client-server
     * architecture.
     * @param socket
     */
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));
            this.clientUserName = in.readLine();
            connectedClients.add(this);
            broadcastMessage("SERVER: " + clientUserName + " has joined the Server");
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
        }
    }

    @Override
    /**
     * point of contact for client and server.
     */
    public void run() {
        String msg;
        String response;
        while(socket.isConnected()) {
            try {

                msg = in.readLine();
                response = clientMsgDecoder.decodeMsg(msg); //The response of the server to the clients message
                out.write(response);
                out.newLine();
                out.flush();
                //TODO if merely an acknowledgement is sent back to the client, how does the client recieve game updates?
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
        for (ClientHandler client : connectedClients) {
            try {
                if(!client.clientUserName.equals((clientUserName))) {
                    client.out.write(msg);
                } else {
                    client.out.write("Message: **" + msg + "** sent!");
                }
                client.out.newLine();
                client.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, in ,out);
            }
        }
    }

    public void removeClientHandler() {
        connectedClients.remove(this);
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

    public void decodeMsg(String msg){

    }
}
