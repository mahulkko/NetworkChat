package NetworkChat;

import java.util.concurrent.LinkedBlockingDeque;

import Connection.IConnection;

public class Test1 implements Runnable{
	
	private IConnection con;
	private LinkedBlockingDeque<String> msg;
	
	public Test1(IConnection con){
		this.con = con;
		this.msg = new LinkedBlockingDeque<String>();
		this.con.startReciveMsg(msg);
	}

	@Override
	public void run() {
		String buf;
		while(this.con.isConnected()){
			try {
				buf = this.msg.takeFirst();
				if(buf.equals("exit 1")){
					this.con.stopReciveMsg(msg);
					System.out.println("Aufhören Nachrichten zu bekommen! (1)");
				}
				if(!buf.equals("null")){
					System.out.println("Test 1: " + buf);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
