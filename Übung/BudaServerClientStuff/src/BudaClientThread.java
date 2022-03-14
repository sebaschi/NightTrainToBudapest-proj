

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
                System.out.println("Waiting to receive a line");
                in.read(command);
                System.out.println("Got a line!");
                comString = new String(command);
                System.out.println("Client number " + number + " sent this message: " + comString);
                if (comString.equalsIgnoreCase("Quitx")) {
                    BudaServer.quit = true;
                    System.out.println("I just set quit to true!");
                    break;
                }

                //todo: do as switch.

                if (comString.equalsIgnoreCase("NAME:")) { //todo: implement these as methods?
                    setName(in);
                }

                if (comString.equalsIgnoreCase("NAMES")) {
                    printnames();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printnames() {
        for (BudaClientThread t: BudaServer.Clients) {
            System.out.println(t.name + " connected (#" + t.number + ")");
        }
    }

    public void setName(InputStream in) throws IOException {
        //byte[] namebyte = new byte[0];
        String nameString = "";
        int i;
        while (true) {
            i = in.read();
            if (i == 46) break;
            nameString = nameString + (char) i;
        }
        this.name = nameString;
    }

}
