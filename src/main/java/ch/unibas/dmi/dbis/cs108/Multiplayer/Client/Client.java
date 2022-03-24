package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    public String userName;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));

            //TODO add the system based generated username here.
            this.userName = userName;
            this.out.write(getUsername());
            this.out.newLine();
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
        }
    }

    public void sendMessage() {
        try {
            Scanner sc = new Scanner(System.in);
            while (socket.isConnected()) {
                String msg = sc.nextLine();
                String encodedMsg = encodeMessage(msg);
                out.write(encodedMsg);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
        }
    }

    /**
     * Uses <code>NTtBProtocolParser</code> to turn Client
     * input into the NTtB Protocol format.
     * Must be called before a client input is sent to the server.
     * @param msg the msg to be encoded.
     * @return Message encoded adhering to the NTtB Protocoll.
     */
    private String encodeMessage(String msg) {
        NTtBProtocolParser pp = new NTtBProtocolParser(this);
        return pp.parseMsg(msg);
    }

    /**
     * Listens for incoming messages
     */
    public void chatListener() {
        /*TODO: what type of decoding has to be done
          think better about structure for incoming messages
        */
        //TODO how shall input be logged?
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
        //TODO Correctly closing a clients connection
        //TODO the server should be notified in a way so he can handle it cleanly
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

    public String getUsername() {
        return userName;
    }
}
