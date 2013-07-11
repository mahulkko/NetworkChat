package Connection.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	private Object lock = new Object();								// Look Variable to syncronize between threads
	private PrintWriter out;										// Outputstream for the Connection
	private BufferedReader in;										// Inputstream for the Connection
	private LinkedList<LinkedBlockingDeque<String>> send = null;	// LinkedList for the BroadcastQueue
	private LinkedBlockingDeque<String> msg = null;					// BlockingDeque for the SendMsg Function
	private Thread ThreadSend;										// Thread for the Send
	private Thread ThreadRecive;									// Thread for the Recieve
	private Recive rcv;												// Recieve Class
	private Send snd;												// Send Class
	
	
	// Inits at startup the parameters with the given parameter
	public Connection(String adress, int port) {
		this.init(adress, port);
	}
	
	// Default Constructor without parameters, must call init after that
	public Connection() { }
	
	// Set the parameter to open a connection
	public boolean init(String adress, int port) {
		synchronized(this.lock) {
			if(!this.isConnected) {
				this.connection = new Socket();
				this.adress = adress;
				this.port = port;
				this.send = new LinkedList<LinkedBlockingDeque<String>>();
				this.addr = new InetSocketAddress(this.adress,this.port);
				return true;
			}
		}
		return false;
	}
	
	// opens a connection to the server
	public boolean Connect() {
		synchronized(this.lock) {
			if(this.isConnected){
				return false;
			}
			if(this.addr != null) {
				try {
					this.connection.connect(this.addr,TIMEOUT);
					this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
					this.out = new PrintWriter(this.connection.getOutputStream(), true);
					this.msg = new LinkedBlockingDeque<String>();
					
					this.snd = new Send(this.out,this.msg);
					this.rcv = new Recive(this.lock,this.in,this.send);
					
					this.ThreadSend = new Thread(this.snd);
					this.ThreadSend.start();
					
					this.ThreadRecive = new Thread(this.rcv);
					this.ThreadRecive.start();
					
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
		synchronized(this.lock) {
			if(this.isConnected) {
				try {
					this.connection.close();
					this.snd.stop();
					this.rcv.stop();
					this.isConnected = false;
					return true;
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
		synchronized(this.lock) {
			return this.send.add(queue);
		}
	}
	
	// stop recive messages from the server
	public boolean stopReciveMsg(LinkedBlockingDeque<String> queue) {
		if(this.send == null){
			return false;
		}
		synchronized(this.lock) {
			return this.send.remove(queue);
		}
	}
	
	public boolean isConnected() {
		return this.isConnected;
	}
}
