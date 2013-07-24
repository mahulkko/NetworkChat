
/*
 * Connection Interface - IConnection
 * Written by Martin Hulkkonen
 */

package Connection;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface IConnection {
	
	// opens a connection to the server
	public boolean Connect();
		
	// Closed a connection to the server
	public boolean Disconnect();
	
	// Returns the input stream
	public BufferedReader getInputStream();
		
	// Returns the input stream
	public PrintWriter getOutputStream();
	
	// Returns the status of the connection
	public boolean isConnected();
}
