package NetworkChat;

import org.apache.log4j.BasicConfigurator;

import Connection.IConnection;
import Connection.impl.Connection;

public class NetworkChat {
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		IConnection con = new Connection("localhost",12345);
		con.Connect();	
	    
		while(con.isConnected()){  
			
		}
	}
}
