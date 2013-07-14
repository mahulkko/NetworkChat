package Connection;

import Connection.impl.ConnectionManagement;

import junit.framework.TestCase;

public class ConnectionManagementTest extends TestCase {
		
	private ConnectionManagement con;
	 	
	public void setUp() {
		this.con = new ConnectionManagement("localhost", 13000);
	}
	
	public void testDoc() {
		assertEquals(false,this.con.isConnected());	
		assertEquals(false,this.con.disconnect());	
		assertEquals(true,this.con.connect());	
		assertEquals(true,this.con.isConnected());	
		assertEquals(false,this.con.connect());	
		assertEquals(true,this.con.disconnect());
	}
}
