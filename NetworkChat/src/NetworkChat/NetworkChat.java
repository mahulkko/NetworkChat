package NetworkChat;

import Connection.IConnection;
import Connection.impl.Connection;

public class NetworkChat {

	public static void main(String[] args) {
		
		System.out.println("NetworkChat");
		IConnection con = new Connection("localhost",13000);
		System.out.println(con.Connect());	
		
		Thread a,b;
		Test1 t1 = new Test1(con);
		Test2 t2 = new Test2(con);
		a = new Thread(t1);
		a.start();
		b = new Thread(t2);
		b.start();	
	}
}
