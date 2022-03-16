import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnector implements Runnable{
    public void run() {
        try {
            System.out.println(
                    "Warte auf Verbindungen auf Port 8090...");
            ServerSocket servSock = new ServerSocket(8090);
            while (true) {
                Socket socket = servSock.accept();
                System.out.println("got a connection: socket " + BudaServer.connections + socket.toString());
                BudaClientThread newClientThread = new BudaClientThread(++BudaServer.connections, socket);
                BudaServer.Clients.add(newClientThread);
                Thread bCT = new Thread(newClientThread);
                bCT.start();
            }
        } catch (IOException e) {
            System.out.println("server got an error");
            System.err.println(e);
            System.exit(1);
        }


    }
}
