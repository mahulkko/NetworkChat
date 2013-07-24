
/* Connection Class - Connection
 * Written by Martin Hulkkonen
 * 
 * Connection 
 * ==========
 * Makes a connection to a Server given with the parameters 
 * address for the address and port for the port of the server
 * 
 * Note:
 * The functions getInputStream() and getOutputStream() can return 
 * a null pointer. 
 * With the function isConnected() you can proof if the Streams have
 * valid values.
 * 
 */

package Connection.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import Connection.IConnection;

/**
 * Ethablished a connection to a server
 * @author Martin Hulkkonen
 *
 */

public class Connection implements IConnection {
	
	/**
	 * TimeOut for the Connection
	 */
	final int TIMEOUT = 100; 
	
	/**
	 * Variable to proof if the Server is already connected
	 */
	private boolean isConnected;
	
	/**
	 * Address the server is connected
	 */
	private String adress;
	
	/**
	 * Port where the server is connected
	 */
	private int port;
	
	/**
	 * Socket for the connection
	 */
	private Socket connection = null;
	
	/**
	 * SocketAdress for the server
	 */
	private SocketAddress addr = null;
	
	/**
	 * OutputStream for the connection
	 */
	protected PrintWriter out;
	
	/**
	 * InputStream for the connection
	 */
	protected BufferedReader in;
	
	/**
	 * Set the parameter for the connection
	 * @param adress - Adress for the connection
	 * @param port - Port for the connection
	 */
	public Connection(String adress, int port) {
		this.isConnected = false;
		this.adress = adress;
		this.port = port;
	}
		
	@Override
	public boolean Connect() {
		// Connect only if its not connected
		if(!this.isConnected) {
			try {
				// Create new socket and connect
				this.connection = new Socket();
				this.addr = new InetSocketAddress(this.adress,this.port);
				this.connection.connect(this.addr,TIMEOUT);
				
				// Get the Input and Output Streams
				this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
				this.out = new PrintWriter(this.connection.getOutputStream(), true);
				
				this.isConnected = true;
				return true;
			// Error 
			} catch (IOException e) {
				return false;
			}
		}
		return false;		
	}
	
	@Override
	public boolean Disconnect() {
		// Disconnect only if its connected
		if(this.isConnected) {
			try {
				// Close the connection
				this.connection.close();
				this.isConnected = false;
				return true;
			// Error 
			} catch (IOException e) {
				return false;
			}
		  }
		return false;
	}
	
	@Override
	public BufferedReader getInputStream() {
		return this.in;
	}

	@Override
	public PrintWriter getOutputStream() {
		return this.out;
	}
	
	@Override
	public boolean isConnected() {
	  return this.isConnected;
	}
}
