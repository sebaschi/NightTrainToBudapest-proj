package ch.unibas.dmi.dbis.cs108.jonasStuff;

public enum ServerProtocol {
  CRTGM,      //Create a new game
  CHATA,      //chat to all
  CHATW,      //whisper chat
  CHATG,      //ghost chat
  LEAVG,      //leave a game
  JOING,      //join a game
  VOTEG,      //ghost voting who to infect
  VOTEH,      //humans voting who is the ghost
  QUITS,      //quit server/ leave server
  LISTP,      //list players/clients in session with the Server
  CPING,      //Ping from client to server.
}
