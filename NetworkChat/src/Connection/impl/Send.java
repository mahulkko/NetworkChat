package Connection.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;

public class Send implements Runnable{
	
	private BufferedWriter out;
	private LinkedBlockingDeque<String> msg;
	private boolean run = true;
	
	
	Send(BufferedWriter out, LinkedBlockingDeque<String> msg) {
		this.out = out;
		this.msg = msg;
	}
	
	public void stop() {
		this.run = false;
	}

	@Override
	public void run() {
		while(this.run){
			try {
				this.out.write(this.msg.removeFirst());
			} catch (IOException e) {
				// Mal sehn
			}
		}
	}
}
