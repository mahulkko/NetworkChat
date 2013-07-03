package Connection;

public interface IConnection {
	
	// Set the parameter to open a connection
	public boolean init(String adress, int port);
	
	// opens a connection to the server
	public boolean Connect();
		
	// Closed a connection to the server
	public boolean Disconnect();
		
	// sends a message to the server
	public boolean sendMsg(String message);
		
	// recive a message from the server
	public String reciveMsg();
}
