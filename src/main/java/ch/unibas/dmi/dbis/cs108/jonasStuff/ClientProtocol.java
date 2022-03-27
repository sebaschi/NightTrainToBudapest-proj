package ch.unibas.dmi.dbis.cs108.jonasStuff;

public enum ClientProtocol {
  MSGRS,  //"Message received": Parameters: a string detailing to the client that and what the server received as command.
  SEROR,  //Server had an error. (used for debugging)
  SPING,  //Ping from server to client;
  NOCMD   //No command found.
}
