package ch.unibas.dmi.dbis.cs108.Multiplayer.helpers;

public interface PingPongInterface extends Runnable{
    void pingListener(String ping);
    String pongSender();
}
