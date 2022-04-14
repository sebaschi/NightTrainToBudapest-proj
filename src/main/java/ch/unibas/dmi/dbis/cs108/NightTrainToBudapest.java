package ch.unibas.dmi.dbis.cs108;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.Server;
import java.net.InetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Help.IParameterRenderer;

public class NightTrainToBudapest {

  public static void main(String[] args){
    try{
      String clientOrServer = args[0];
      if (clientOrServer.equalsIgnoreCase("client")) {
        String addrString = args[1].substring(0,args[1].indexOf(":"));
        InetAddress addr = InetAddress.getByName(addrString);
        int port = Integer.parseInt(args[1].substring(args[1].indexOf(":") + 1));
        String username = null;
        if (args.length > 2) { //if the client provided a username
          //StringBuilder usernamebuilder = new StringBuilder(); todo: support username with spaces.
          username = args[2];
        }
        Client.main(addr, port, username);
      } else if (clientOrServer.equalsIgnoreCase("server")) {
        int port = Integer.parseInt(args[1]);
        Server.main(port);
      } else {
        System.out.println("invalid arguments!");
      }

    } catch (Exception e) {
      System.out.println("Please give more arguments.");
      System.out.println("Syntax:");
      System.out.println("client <hostadress>:<port> [<username>] | server <port>");
      e.printStackTrace();
    }
  }
}
