package Server;

import java.io.IOException;

/**
 * Class that will init the servers
 */
public class ServerMain {
    public static void main(String[] args) throws IOException 
	{
		try {
			ServerHTTP serverHTTP = new ServerHTTP();
			
		}   catch(Exception e){
			System.out.println("Message error : "+e.getMessage());
		}
	}
}
