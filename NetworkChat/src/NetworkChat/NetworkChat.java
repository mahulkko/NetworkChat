package NetworkChat;

import java.util.concurrent.LinkedBlockingDeque;

import Connection.IConnection;
import Connection.impl.Connection;

public class NetworkChat {

	public static void main(String[] args) {
				
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
		
		while(true) {
			try {
				System.out.println(send.takeFirst());
				System.out.println(send2.takeFirst());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
