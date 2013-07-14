package Connection.impl;

import java.util.concurrent.LinkedBlockingDeque;

import Connection.IConnection;

public class Connection implements IConnection {
	
	private ConnectionManagement cm;
	private Thread ThreadSend;
	private Thread ThreadRecive;
	private Recive rcv;
	private Send snd;
	
	public Connection(String adress, int port) {
		this.cm = new ConnectionManagement(adress,port);
	}
		
	public boolean Connect() {
		if(!this.cm.isConnected()){
			if(this.cm.connect()){
				this.snd = new Send(this.cm);
				this.rcv = new Recive(this.cm);
				this.ThreadSend = new Thread(this.snd);
				this.ThreadSend.start();
				this.ThreadRecive = new Thread(this.rcv);
				this.ThreadRecive.start();
				return true;
			}
		}
		return false;		
	}
	
	public boolean Disconnect() {
		return this.cm.disconnect();
	}
	
	public boolean sendMsg(String message) {
		return this.cm.sendMsg(message);
	}
	
	public boolean startReciveMsg(LinkedBlockingDeque<String> queue) {
		return this.startReciveMsg(queue);
	}
	
	public boolean stopReciveMsg(LinkedBlockingDeque<String> queue) {
	 return this.stopReciveMsg(queue);
	}
	
	public boolean isConnected() {
	  return this.isConnected();
	}
}
