package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private int name;
    private final Socket socket;

    public ClientThread(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(socket.isConnected()){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
