package ch.unibas.dmi.dbis.cs108.Multiplayer.helpers;

/**
 * PingPong offers services to for listening and sending
 * Pings to a communication partner.
 * Runs on a Thread as not to disturb other communication channels.(Is this necessary?)
 */
public class PingPong implements PingPongInterface {
    @Override
    public void pingListener(String ping) {

    }

    @Override
    public String pongSender() {
        return null;
    }

    @Override
    public void run() {

    }
    //TODO: Impl
}
