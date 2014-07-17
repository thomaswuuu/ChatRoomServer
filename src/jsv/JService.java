package jsv;

import java.net.ServerSocket;

public class JService 
{
	public static void main(String argv[]) 
	{
		Database Data = new Database();
	
		try
		{
			ServerSocket server = new ServerSocket(1000);
			server.setReuseAddress(true);
			for(int num=0;num<=10;num++)
			{
					Thread conclient = new Thread(new ConnectClient(server,Data,num));				
					conclient.start();
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	
	
	}

	
}
