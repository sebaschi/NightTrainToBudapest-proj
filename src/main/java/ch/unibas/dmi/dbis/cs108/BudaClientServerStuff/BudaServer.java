package ch.unibas.dmi.dbis.cs108.BudaClientServerStuff;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class BudaServer {
    public static boolean quit = false; //todo: meaningfully implement this
    public static HashSet<BudaClientThread> Clients = new HashSet<BudaClientThread>();
    static int connections = 0;

    public static void main(String[] args) {
        ServerConnector ServC = new ServerConnector();
        Thread ServCThread = new Thread(ServC);
        ServCThread.start();            //the ServCThread listens for new connections so the server can do other things
        while (!quit) {
            //Main server stuff goes here
        }
        //ServCThread.stop(); //todo: find some alternative for this.
        System.out.println("stopping the main BudaServer thread.");
        System.out.println("Quitting after the next connection is made.");
    }
}