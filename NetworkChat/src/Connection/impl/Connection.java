package Connection.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import Connection.IConnection;

public class Connection implements IConnection {
	
	final int TIMEOUT = 100; 
	
	private Socket connection = null;		// Socket for the connection
	private SocketAddress addr = null;  	// SocketAddress for the connection
	private int port = -1;					// Port where the Server is running
	private String adress = null;      		// Name of the Server DNS or Ip
	private boolean isConnected = false;	// indicates if the server is connectet or not 
	
	// Inits at startup the parameters with the given parameter
	public Connection(String adress, int port) {
		this.init(adress, port);
	}
	
	// Opens a connection with standard values
	public Connection() { }
	
	// Set the parameter to open a connection
	public boolean init(String adress, int port) {
		if(this.port >= 0 && this.adress != null) {
			this.connection = new Socket();
			this.addr = new InetSocketAddress(this.adress,this.port);
			this.adress = adress;
			this.port = port;
			return true;
		}
		return false;
	}
	
	// opens a connection to the server
	public boolean Connect() {
		if(this.connection != null && this.addr != null) {
			try {
				this.connection.connect(this.addr,TIMEOUT);
				this.isConnected = true;
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}
	
	// Closed a connection to the server
	public boolean Disconnect() {
		if(this.isConnected) {
			try {
				this.connection.close();
				this.isConnected = false;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}
	
	// sends a message to the server
	public boolean sendMsg(String message) {
		// to implement
		return false;
	}
	
	// recive a message from the server
	public String reciveMsg() {
		// user must set a list to the connection to get the message
		return "";
	}
}
