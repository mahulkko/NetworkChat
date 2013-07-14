package Connection;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;

import junit.framework.TestCase;

public class ConnectionTest extends TestCase {
		
	private IConnection con;
	 
	
	private LinkedBlockingDeque<String> queue;
	
	public void setUp() throws IOException {
		this.queue = new LinkedBlockingDeque<String>();
	}
	
	public void testDoc() {
		//assertEquals(true,this.con.startReciveMsg(this.queue));	
	}
}
