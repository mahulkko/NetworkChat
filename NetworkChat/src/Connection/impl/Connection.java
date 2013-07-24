
/* Connection Class - Connection
 * Written by Martin Hulkkonen
 * 
 * Connection 
 * ==========
 * Makes a connection to a Server given with the parameters 
 * adress for the adress and port for the port of the server
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

public class Connection implements IConnection {
	
	// Timeout for the connect
	final int TIMEOUT = 100; 
	
	// Variable for the Connection
	private boolean isConnected;
	private String adress;
	private int port;
	
	// Socketvariable
	private Socket connection = null;
	private SocketAddress addr = null;
	
	// Input Output Stream
	protected PrintWriter out;
	protected BufferedReader in;
	
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
