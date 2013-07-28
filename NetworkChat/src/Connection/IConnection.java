
/*
 * Connection Interface - IConnection
 * Written by Martin Hulkkonen
 */

package Connection;

/**
 * Established a connection to a server
 * @author Martin Hulkkonen
 */
public interface IConnection {
	
	/**
	 * Connects to a server with the given parameters
	 * <br>
	 * <b>Note:</b> The server connects only if the connection is not established
	 * @return On success it returns <b>true</b> on error it returns <b>false</b>
	 * 
	 */
	public boolean Connect();
		
	/**
	 * Close a connection from a server
	 * <br>
	 * <b>Note:</b> The server disconnects only if the connection is established
	 * @return On success it returns <b>true</b> on error it returns <b>false</b>
	 */
	public boolean Disconnect();
	
	/**
	 * Read a message from the Server without blocking
	 * @return Returns the String of the message if there was a message by no message it returns null
	 */
	public String getMessage();
	
	/**
	 * Read a message from the Server with blocking
	 * The function is waiting until a message is coming.
	 * @return Returns the String of the message on error it returns <b>null</b>
	 */
	public String getMessageBlocked();
		
	/**
	 * Send a message to the server
	 * @param msg - Message send to the server
	 */
	public void sendMessage(String msg);
	
	/**
	 * Returns the state of the Connection 
	 * @return Returns <b>true</b> if there exists a connection and <b>false</b> if the is no connection
	 */
	public boolean isConnected();
}
