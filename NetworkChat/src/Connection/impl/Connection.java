
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
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import Connection.IConnection;

/**
 * Established a connection to a server
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
	private PrintWriter out;
	
	/**
	 * InputStream for the connection
	 */
	private BufferedReader in;
	
	/**
	 * Queue to receive messages
	 */
	private LinkedBlockingQueue<String> queue;
	
	/**
	 * Thread to receive messages
	 */
	private Thread receiveMsg;
	
	/**
	 * Logger for log4j Connection
	 */
	static Logger log = Logger.getLogger("Connection");
	
	/**
	 * Logger for log4j ReceiveMsg
	 */
	static Logger logMsg = Logger.getLogger("Connection.ReceiveMsg");
	
	/**
	 * Set the parameter for the connection
	 * @param adress - Address for the connection
	 * @param port - Port for the connection
	 */
	public Connection(String adress, int port) {
		log.info("Initializise connection");
		this.isConnected = false;
		this.adress = adress;
		this.port = port;
	}
	
	//TODO: Add Thread for the receive message function
		
	@Override
	public boolean Connect() {
		// Connect only if its not connected
		log.info("Try to connect to server");
		if(!this.isConnected) {
			try {
				// Create new socket and connect
				log.info("Create new Socket with Adress:" + this.adress + " and Port: " + this.port);
				this.connection = new Socket();
				this.addr = new InetSocketAddress(this.adress,this.port);
				this.connection.connect(this.addr,TIMEOUT);
				
				// Get the Input and Output Streams
				log.info("Initialize Input and Output Stream for the communication");
				this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
				this.out = new PrintWriter(this.connection.getOutputStream(), true);
				this.queue = new LinkedBlockingQueue<String>();
				
				// Start Receive Thread
				log.info("Create and start a Receive Thread");
				this.receiveMsg = new Thread(new ReceiveMsg());
				this.receiveMsg.start();
				
				log.info("Client successful connected");
				this.isConnected = true;
				return true;
			} catch (IOException e) {
				log.info("Could not connect to the server");
				return false;
			}
		}
		log.info("Already connectet to the server");
		return false;		
	}
	
	@Override
	public boolean Disconnect() {
		// Disconnect only if its connected
		log.info("Try to disconnect from the server");
		if(this.isConnected) {
			try {
				// Close the connection
				log.info("Close the Connection");
				this.connection.close();
				this.isConnected = false;
				
				// Set the Output and InputStream to zero
				this.in = null;
				this.out = null;
				log.info("Client successful disconected");
				return true;
			} catch (IOException e) {
				log.info("Could not close the connection to the server");
				return false;
			}
		  }
		log.info("Client alredy disconnected from the server");
		return false;
	}
	
	@Override
	public String getMessage() {
		String msg = this.queue.poll();
		log.info("Get message from server: " + msg);
		return msg;
	}
	
	@Override
	public String getMessageBlocked(){
		try {
			String msg;
			msg = this.queue.take();
			log.info("Get message from Server: " + msg);
			return msg;
		} catch (InterruptedException e) {
			log.error("Could not receive a message from the server");
		}
		return null;
	}

	@Override
	public void sendMessage(String msg) {
		log.info("Send message to the server: " + msg);
		this.out.println(msg);
	}	
	@Override
	public boolean isConnected() {
	  return this.isConnected;
	}
	
	
	// Inner Class for the receive
	public class ReceiveMsg implements Runnable {

		@Override
		public void run() {
			logMsg.info("Start new ReceiveMsg Thread");
			while(isConnected) {
				try {
					String msg = in.readLine();
					logMsg.info("New message from server: " + msg);
					//TODO: If the queue is full all the next messages are lost
					queue.offer(msg);
				} catch (IOException e) {
					logMsg.info("Connection to server lost");
					// Disconnect
					Disconnect();
					logMsg.info("Disconnect from the server and clean up");
					queue.offer("null");
					break;
				}
			}
		}	
	}
}
