package jsv;

import java.io.*;
import java.util.Vector;

public class ChatWrite implements Runnable
{
	private Vector<Online> Online = new Vector<Online>();	
	private String LogInId = new String();
	private boolean SingleChatFlag = false;
	private int index;
	
	public ChatWrite()
	{		
	}
	
	public ChatWrite(Vector<Online> Online,int index,String LogInId,boolean flag)
	{
		this.Online = Online;
		this.index = index; //在單人聊天是對象在資料庫的位置，在聊天室是此客戶的位置
		this.LogInId = LogInId;
		this.SingleChatFlag = flag;
	}
	
	public void run() 
	{
		if(SingleChatFlag)
		{
			try 
			{	
				DataOutputStream out = new DataOutputStream(Online.elementAt(index).SC.getOutputStream()); //傳給接收訊息的客戶端
				out.writeBytes(LogInId+" say:"+Online.elementAt(index).shareMessage+"\n");

			}	 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{

			try 
			{	
				for(int i=0;i<Online.size();i++)
				{
					if(Online.elementAt(i).ChatRoomFlag && i!=index)
					{
						DataOutputStream out = new DataOutputStream(Online.elementAt(i).SC.getOutputStream()); //傳訊息給聊天是所有人
						out.writeBytes(LogInId+" say:"+Online.elementAt(index).shareMessage+"\n");
					}
				}

			}	 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}	
}
