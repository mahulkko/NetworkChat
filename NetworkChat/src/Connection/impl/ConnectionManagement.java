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

public class ConnectionManagement {
	
	final int TIMEOUT = 100; 
	
	private boolean isConnected;
	private String adress;
	private int port;
	private Socket connection = null;
	private SocketAddress addr = null;
	protected PrintWriter out;
	protected BufferedReader in;
	protected LinkedList<LinkedBlockingDeque<String>> send;
	protected LinkedBlockingDeque<String> msg;
	protected Object lock;
	
	public ConnectionManagement(String adress, int port) {
		this.isConnected = false;
		this.adress = adress;
		this.port = port;
		this.connection = new Socket();
		this.send = new LinkedList<LinkedBlockingDeque<String>>();
		this.addr = new InetSocketAddress(this.adress,this.port);
		this.lock = new Object();
	}
		
	public boolean connect() {
		synchronized(this.lock) {
			if(this.isConnected){
				return false;
			}
			try {
				this.connection.connect(this.addr,TIMEOUT);
				this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
				this.out = new PrintWriter(this.connection.getOutputStream(), true);
				this.msg = new LinkedBlockingDeque<String>();
				this.isConnected = true;
				return true;
			} catch (IOException e) {
				return false;
			}
		}
	}
	
	public boolean disconnect() {
		synchronized(this.lock) {
			if(this.isConnected) {
				try {
					this.isConnected = false;
					this.connection.close();
					return true;
				} catch (IOException e) {
					return false;
				}
			  }
			}
		return false;
	}
	
	public boolean sendMsg(String message) {
		if(this.isConnected) {
			return this.msg.add(message);
		}
		return false;
	}
	
	public boolean startReciveMsg(LinkedBlockingDeque<String> queue) {
		synchronized(this.lock) {
			return this.send.add(queue);
		}
	}
	
	public boolean stopReciveMsg(LinkedBlockingDeque<String> queue) {
		synchronized(this.lock) {
			return this.send.remove(queue);
		}
	}
	
	public boolean isConnected() {
		return this.isConnected;
	}
}
