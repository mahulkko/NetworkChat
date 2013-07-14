package Connection.impl;

public class Send implements Runnable{
	
	private ConnectionManagement cm;
	
	Send(ConnectionManagement cm) {
		this.cm = cm;
	}
	
	@Override
	public void run() {
		System.out.println("Send Thread gestartet!");
		while(this.cm.isConnected()){
			try {
				this.cm.out.println(this.cm.msg.takeFirst());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
		System.out.println("Send Thread beendet!");
	}
}
