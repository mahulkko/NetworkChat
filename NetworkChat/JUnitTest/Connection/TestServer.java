package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer implements Runnable
{
  private final ServerSocket server;
  private boolean run = true;
  
  public TestServer( int port ) throws IOException
  {
    server = new ServerSocket( port );
  }
  
  public void handleConnection( Socket client ) throws IOException
  {
	BufferedReader in = new BufferedReader(new InputStreamReader( client.getInputStream()) );
    String read = in.readLine();
    System.out.println(read);
  }
  
  public void exitServer() {
	  this.run = false;
  }
  
  @Override
  public void run() {
	  while ( this.run )
	    {
	      Socket client = null;
	      try
	      {
	        client = server.accept();
	        System.out.println("Client akzeptiert!");
	        handleConnection ( client );
	      }
	      catch ( IOException e ) {
	        e.printStackTrace();
	      }
	      finally {
	        if ( client != null )
	          try { client.close(); } catch ( IOException e ) { e.printStackTrace(); }
	      }
	   }
	  System.out.println("Server Beendet");
  }

}

