
/*
 * Connection Interface - IConnection
 * Written by Martin Hulkkonen
 */

package Connection;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Established a connection to a server
 * @author Martin Hulkkonen
 */
public interface IConnection {
	
	/**
	 * Connects to a server with the given parameters
	 * <br>
	 * <b>Note:</b> The server connects only if the connection is not ethablished
	 * @return On success it returns <b>true</b> on error it returns <b>false</b>
	 * 
	 */
	public boolean Connect();
		
	/**
	 * Close a connection from a server
	 * <br>
	 * <b>Note:</b> The server disconnects only if the connection is ethablished
	 * @return On success it returns <b>true</b> on error it returns <b>false</b>
	 */
	public boolean Disconnect();
	
	/**
	 * Get the InputStream from the Connection 
	 * @return Returns the InputStream as a BufferedReader
	 * <br>
	 * <b>Note:</b> It can return a NullPointer - Check it with the function isConnected() for a valid value
	 */
	public BufferedReader getInputStream();
		
	/**
	 * Get the OutputStream from the Connection 
	 * @return Returns the OutputStream as a PrintWriter
	 * <br>
	 * <b>Note:</b> It can return a NullPointer - Check it with the function isConnected() for a valid value
	 */
	public PrintWriter getOutputStream();
	
	/**
	 * Returns the state of the Connection 
	 * @return Returns <b>true</b> if there exists a connection and <b>false</b> if the is no connection
	 */
	public boolean isConnected();
}
