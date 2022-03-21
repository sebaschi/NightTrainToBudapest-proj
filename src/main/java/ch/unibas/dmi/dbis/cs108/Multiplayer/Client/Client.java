package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import java.awt.image.BufferStrategy;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private int name;
    private final Socket socket;

    public Client(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
