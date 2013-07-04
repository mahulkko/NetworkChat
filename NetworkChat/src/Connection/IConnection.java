package Connection;

import java.util.concurrent.LinkedBlockingDeque;

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
	public boolean startReciveMsg(LinkedBlockingDeque<String> queue);
	
	// stop recive messages from the server
	public boolean stopReciveMsg(LinkedBlockingDeque<String> queue);
	
	// Returns the status of the connection
	public boolean isConnected();
}
