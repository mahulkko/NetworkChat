package NetworkChat;

import org.apache.log4j.BasicConfigurator;

import Connection.IConnection;
import Connection.impl.Connection;

public class NetworkChat {
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		System.out.println("NetworkChat");
		IConnection con = new Connection("localhost",12345);
		System.out.println(con.Connect());	
	    
		while(con.isConnected()){  
			String msg = con.getMessageBlocked();
			if(msg != "null")
			   System.out.println("Message: " + msg);
		}
		System.out.println("Verbindung verloren");
	}
}
