package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader in;
    private  BufferedWriter out;
    public String userName;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));
            this.userName = userName;
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
        }
    }

    public void sendMessage() {
        try {
            out.write(userName);
            out.newLine();
            out.flush();

            Scanner sc = new Scanner(System.in);
            while (socket.isConnected()) {
                String msg = sc.nextLine();
                out.write(userName + ": " + msg);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
        }
    }

    public void chatListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String chatMsg;

                while(socket.isConnected()) {
                    try {
                        chatMsg = in.readLine();
                        System.out.println(chatMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEverything(socket, in, out);
                    }

                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
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

    public static  void  main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a nickname: ");
        String username = sc.next();
        Socket socket;
        try {
            socket = new Socket("localhost", 42069);
            Client client = new Client(socket, username);
            client.chatListener();
            client.sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
