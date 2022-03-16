This is a demo of some (basic) client / server functionality.
Run BudaClient.java for the Client and BudaServer.java for the Server. Everything connects locally via port 8090

The client reads from the console input and sends that to the server. The Server generally reads messages in five characters (so a text-based protocol could be based on commands of five characters).

The server can connect to an arbitrary number of clients.

Type "Name:Jonas B." to change the client's name to Jonas B (the "." is where the name stops).

Type "Names" for the Server to display everyone who is connected along with their name (if they have set a name).

Typing "Quitx" should quit everything but I havent quite managed to implement that yet so just repeatedly use ctrl+c to quit everything.

-Jonas