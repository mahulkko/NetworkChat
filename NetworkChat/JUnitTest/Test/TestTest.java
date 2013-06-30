package Test;

import java.io.IOException;
import junit.framework.TestCase;

public class TestTest extends TestCase {
	
	Test test;
	Test test2;
	
	public void setUp() throws IOException {
		this.test = new Test();
	}
	
	public void testDoc() {
		
		assertEquals(0 , test.getNumber());
		assertEquals(true , test.setNumber(10));	
		assertEquals(10 , test.getNumber());
		this.test2 = new Test(10);
		assertEquals(10 , test2.getNumber());
	}
}
