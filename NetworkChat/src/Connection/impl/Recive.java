package Connection.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import utility.Bool;

public class Recive implements Runnable{
	
	private Object lock;
	private BufferedReader in;
	private LinkedList<LinkedBlockingDeque<String>> send;
	private boolean run = true;
	private Bool isConnected;
	
	public Recive(Object lock, BufferedReader in, LinkedList<LinkedBlockingDeque<String>> send, Bool isConnected) {
		this.lock = lock;
		this.in = in;
		this.send = send;
		this.isConnected = isConnected;
	}
	
	public void stop() {
		this.run = false;
	}

	@Override
	public void run() {
		String buf;
		while(this.run){
			try {
				buf = this.in.readLine();
				synchronized(this.lock) {
					for(int i = 0; i<this.send.size(); i++) {
						try {
							this.send.get(i).put(buf);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				this.isConnected.setBool(false);
				this.run = false;
			}
		}
	}
}
