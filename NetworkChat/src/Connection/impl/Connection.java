package Connection.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import Connection.IConnection;

public class Connection implements IConnection {
	
	final int TIMEOUT = 100; 
	
	private Socket connection = null;								// Socket for the connection
	private SocketAddress addr = null;								// SocketAddress for the connection
	private int port = -1;											// Port where the Server is running
	private String adress = null;									// Name of the Server DNS or Ip
	private boolean isConnected = false;							// indicates if the server is connectet or not 
	private Object lock;											// Look Variable to syncronize between threads
	private OutputStream out;										// Outputstream for the Connection
	private BufferedReader in;										// Inputstream for the Connection
	private LinkedList<LinkedBlockingDeque<String>> send = null;	// LinkedList for the BroadcastQueue
	private LinkedBlockingDeque<String> msg = null;					// BlockingDeque for the SendMsg Function
	
	
	// Inits at startup the parameters with the given parameter
	public Connection(String adress, int port) {
		this.init(adress, port);
	}
	
	// Default Constructor without parameters, must call init after that
	public Connection() { }
	
	// Set the parameter to open a connection
	public boolean init(String adress, int port) {
		synchronized(lock) {
			if(this.port >= 0 && this.adress != null && !this.isConnected) {
				this.connection = new Socket();
				this.addr = new InetSocketAddress(this.adress,this.port);
				this.adress = adress;
				this.port = port;
				this.send = new LinkedList<LinkedBlockingDeque<String>>();
				return true;
			}
		}
		return false;
	}
	
	// opens a connection to the server
	public boolean Connect() {
		synchronized(lock) {
			if(this.connection != null && this.addr != null && !this.isConnected) {
				try {
					this.connection.connect(this.addr,TIMEOUT);
					this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
					this.out = this.connection.getOutputStream();
					this.msg = new LinkedBlockingDeque<String>();
					this.isConnected = true;
					return true;
				} catch (IOException e) {
					return false;
				}
			}
		}
		return false;
	}
	
	// Closed a connection to the server
	public boolean Disconnect() {
		synchronized(lock) {
			if(this.isConnected) {
				try {
					this.connection.close();
					this.isConnected = false;
				} catch (IOException e) {
					return false;
				}
			}
		}
		return false;
	}
	
	// sends a message to the server
	public boolean sendMsg(String message) {
		if(this.isConnected) {
			return this.msg.add(message);
		}
		return false;
	}
	
	// recive a message from the server
	public boolean startReciveMsg(LinkedBlockingDeque<String> queue) {
		if(this.send == null){
			return false;
		}
		synchronized(lock) {
			return this.send.add(queue);
		}
	}
	
	// stop recive messages from the server
	public boolean stopReciveMsg(LinkedBlockingDeque<String> queue) {
		if(this.send == null){
			return false;
		}
		synchronized(lock) {
			return this.send.remove(queue);
		}
	}
	
	public boolean isConnected() {
		return this.isConnected;
	}
}
