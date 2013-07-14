package Connection.impl;

import java.io.IOException;

public class Recive implements Runnable{
	
	private ConnectionManagement cm;
	private String buf;
	
	public Recive(ConnectionManagement cm) {
		this.cm = cm;
	}
	
	@Override
	public void run() {
		while(this.cm.isConnected()){
			try {
				this.buf = this.cm.in.readLine();
				synchronized(this.cm.lock) {
					for(int i = 0; i<this.cm.send.size(); i++) {
						try {
							this.cm.send.get(i).put(buf);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				this.cm.disconnect();
			}
		}
   }
}
