package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NTtBFormatMsg;
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
    public static HashSet<ClientHandler> lobby = new HashSet<>();
    public static HashSet<ClientHandler> ghostClients = new HashSet<>();
    private ClientMsgDecoder clientMsgDecoder = new ClientMsgDecoder();

    /**
     * Implements the login logik in client-server
     * architecture.
     * @param socket the socket on which to make the connection.
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

    //Getters:
    public BufferedWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public static HashSet<ClientHandler> getConnectedClients() {
        return connectedClients;
    }

    public static HashSet<ClientHandler> getLobby() {
        return lobby;
    }

    public static HashSet<ClientHandler> getGhostClients() {
        return ghostClients;
    }

    public ClientMsgDecoder getClientMsgDecoder() {
        return clientMsgDecoder;
    }

    //Setters


    @Override
    /**
     * The main logik of the client handler.
     * Since every client is put on a string this is where
     * most interactions between client and server are held..
     */
    public void run() {
        String msg;
        NTtBFormatMsg response;
        while(socket.isConnected()) {
            try {

                msg = in.readLine();
                response = clientMsgDecoder.decodeMsg(msg); //The response of the server to the clients message
                out.write(response.getMessage());
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
