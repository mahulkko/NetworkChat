package Connection.impl;

import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingDeque;

public class Send implements Runnable{
	
	private PrintWriter out;
	private LinkedBlockingDeque<String> msg;
	private boolean run = true;
	
	
	Send(PrintWriter out, LinkedBlockingDeque<String> msg) {
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
					this.out.println(this.msg.takeFirst());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    } 
	}
}
