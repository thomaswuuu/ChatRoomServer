package jsv;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Checker
{
	private Vector<Online> Online = new Vector<Online>();	
	
	public Checker()
	{		
	}
	
	public Checker(Vector<Online> Online)
	{
		this.Online = Online;
	}
	
	public void showOnlineToClient(DataOutputStream out) throws IOException
	{
		String OnlineContact = new String();
		
		for(int i=0;i<Online.size();i++)
		{
			OnlineContact += Online.elementAt(i).ID+"\t";
			if((i>4)&&(i%4==0))   //每4個換行一次
				OnlineContact+="\n";	
		}
		
		OnlineContact+=";";  //讓使用者接收到分號就停止接收
		out.write(OnlineContact.getBytes());
		
		
	}
	
	
}
