package NetworkChat;

import java.util.concurrent.LinkedBlockingDeque;

import Connection.IConnection;
import Connection.impl.Connection;

public class NetworkChat {

	public static void main(String[] args) {
		
		boolean check = true;
		System.out.println("NetworkChat");
		IConnection con = new Connection("localhost",13000);
		LinkedBlockingDeque<String> send = new LinkedBlockingDeque<String>();
		LinkedBlockingDeque<String> send2 = new LinkedBlockingDeque<String>();
		con.Connect();
		con.startReciveMsg(send);
		con.startReciveMsg(send2);
		con.sendMsg("Hallo");
		con.sendMsg("wie");
		con.sendMsg("gehts?");
		
		while(check) {
		 System.out.println(con.isConnected());
		 try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
