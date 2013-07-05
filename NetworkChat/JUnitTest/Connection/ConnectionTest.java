package Connection;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;

import Connection.impl.Connection;
import junit.framework.TestCase;

public class ConnectionTest extends TestCase {
		
	private IConnection con;
	private IConnection con2;
	Thread server;
	TestServer tserver;
	 
	
	private LinkedBlockingDeque<String> queue;
	
	public void setUp() throws IOException {
		this.tserver = new TestServer(12345);
		this.server =   new Thread( this.tserver );
		this.server.start();
		this.con = new Connection();
		this.con2 = new Connection("localhost",1234);
		this.queue = new LinkedBlockingDeque<String>();
	}
	
	public void testDoc() {
		
		assertEquals(false,this.con.startReciveMsg(this.queue));
		assertEquals(false,this.con.sendMsg("nachricht"));
		assertEquals(false,this.con.stopReciveMsg(this.queue));
		assertEquals(false, con.Connect());
		assertEquals(false,this.con.sendMsg("nachricht"));
		assertEquals(false,this.con.startReciveMsg(this.queue));
		assertEquals(false,this.con.stopReciveMsg(this.queue));
		assertEquals(false, con.Disconnect());
		assertEquals(true, con.init("localhost", 12345));
		assertEquals(true,this.con.startReciveMsg(this.queue));
		assertEquals(true,this.con.stopReciveMsg(this.queue));
		assertEquals(true, con.Connect());
		assertEquals(true,this.con.sendMsg("nachricht"));
		assertEquals(true,this.con.startReciveMsg(this.queue));
		assertEquals(true,this.con.stopReciveMsg(this.queue));
		assertEquals(false, con.Connect());
		assertEquals(true, con.isConnected());
		assertEquals(false, con.init("localhost", 12345));
		assertEquals(true, con.Disconnect());
		assertEquals(true,this.con.startReciveMsg(this.queue));
		assertEquals(true,this.con.stopReciveMsg(this.queue));
		assertEquals(false, con.isConnected());
		assertEquals(false, con2.Connect());
		assertEquals(true, con.init("localhost", 12345));
		assertEquals(true, con.Connect());
		assertEquals(true, con.isConnected());
		assertEquals(true, con.Disconnect());
		this.tserver.exitServer();
	}
}
