package NetworkChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Connection.IConnection;
import Connection.impl.Connection;

public class NetworkChat {

	public static void main(String[] args) {
		
		System.out.println("NetworkChat");
		IConnection con = new Connection("localhost",12345);
		System.out.println(con.Connect());	
		
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    
		while(true){  
			try {
				String msg = br.readLine();
				con.sendMessage(msg);
				System.out.println("Message: " + msg + " send");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
