import java.io.*;
import java.net.Socket;

public class ClientListener implements Runnable{
    private final Socket sock;

    public ClientListener(Socket sock) {
        this.sock = sock;
    }

    public void run(){
        byte[] command = new byte[5];
        String comString;

        try {
            InputStream in = sock.getInputStream();
            in.read(command);
            System.out.println("Got a line!");
            comString = new String(command);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
