package ch.unibas.dmi.dbis.cs108.BudaClientServerStuff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BudaClientThread implements Runnable {
    int number;
    Socket socket;
    String name;


    public BudaClientThread(int number, Socket socket) {
        this.number = number;
        this.socket = socket;
        name = "";
    }

    public void run() {
        System.out.println("Connection " + number + " established.");
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] command;
            String comString;
            while (true) {
                command = new byte[5];
                in.read(command);           //BudaClientThread waits to receive a line from the inputstream.
                comString = new String(command);
                if (comString.equalsIgnoreCase("Quitx")) { //todo: do as switch.
                    BudaServer.quit = true;
                    System.out.println("I just set quit to true!");
                    break;
                } else if (comString.equalsIgnoreCase("NAME:")) {
                    setName(in);
                } else if (comString.equalsIgnoreCase("NAMES")) {
                    printnames();
                } else {
                    System.out.println("Client number " + number + " sent this message: \"" + comString + "\" and I'm not sure what it means.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printnames() {
        for (BudaClientThread t: BudaServer.Clients) {
            System.out.println("user named "+ t.name + " is connected (#" + t.number + ")");
        }
    }

    public void setName(InputStream in) throws IOException {
        String nameString = "";
        int i;
        while (true) {
            i = in.read();
            if (i == 46) break;                 //the name ends with a "."
            nameString = nameString + (char) i;
        }
        this.name = nameString;
        System.out.println("Client number " + number + " changed their name to: " + nameString);
    }

}
