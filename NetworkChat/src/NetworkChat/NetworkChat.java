package NetworkChat;

import Connection.IConnection;
import Connection.impl.Connection;

public class NetworkChat {

	public static void main(String[] args) {
		
		System.out.println("NetworkChat");
		IConnection con = new Connection("localhost",13000);
		System.out.println(con.Connect());	
	}
}
