package NetworkChat;

import java.util.concurrent.LinkedBlockingDeque;

import Connection.IConnection;

public class Test2 implements Runnable{
	
	private IConnection con;
	private LinkedBlockingDeque<String> msg;
	
	public Test2(IConnection con){
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
				if(buf.equals("exit 2")){
					this.con.stopReciveMsg(msg);
					System.out.println("Aufhören Nachrichten zu bekommen! (2)");
				}
				if(!buf.equals("null")){
					System.out.println("Test 2: " + buf);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
