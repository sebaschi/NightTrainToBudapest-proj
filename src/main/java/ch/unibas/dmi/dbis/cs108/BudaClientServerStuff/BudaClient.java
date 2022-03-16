package ch.unibas.dmi.dbis.cs108.BudaClientServerStuff;

import java.io.*;
import java.net.Socket;

public class BudaClient {
    public static void main(String[] args) {
        Socket sock = null;
        try {
            sock = new Socket("localhost", 8090);
            OutputStream out= sock.getOutputStream();
            BufferedReader conin = new BufferedReader(new InputStreamReader(System.in));
            String line = ""; //this String is the line that will be sent to the server
            while (true) {
                line = conin.readLine();
                out.write(line.getBytes());
                if (line.equalsIgnoreCase("Quitx")) {
                    break;
                }
                //line.startsWith() //todo: automatically handle name lengths
                //TODO: Implement inputStream in.
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
