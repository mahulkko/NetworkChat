package Connection;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * IConnection Interface - Connection
 * <br>
 * Written by Martin Hulkkonen
 * <br><br>
 * <b><u>Connection</u></b>
 * <br>
 * Open a connection to a Server given with the parameters 
 * <br>
 * address for the address and port for the port of the server
 * <br>
 * Established a connection to a server
 * <br>
 * @author Martin Hulkkonen
 *
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
	 * Insert a new LinkedBlockingQueue for receiving messages
	 * @param queue - Queue were the messages from the server are saved
	 * @return When the queue is successfully insert it will returns <b>true</b> on error it returns <b>false</b>
	 */
	public boolean startReceivingMessages(LinkedBlockingQueue<String> queue);
	
	/**
	 * Get the queue to stop receiving messages to it
	 * @param queue - Queue where should stop getting messages
	 * @return When the queue is successfully deleted it will returns <b>true</b> on error it returns <b>false</b>
	 */
	public boolean stopReceivingMessages(LinkedBlockingQueue<String> queue);
		
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
