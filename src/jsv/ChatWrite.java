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
		this.index = index; //�b��H��ѬO��H�b��Ʈw����m�A�b��ѫǬO���Ȥ᪺��m
		this.LogInId = LogInId;
		this.SingleChatFlag = flag;
	}
	
	public void run() 
	{
		if(SingleChatFlag)
		{
			try 
			{	
				DataOutputStream out = new DataOutputStream(Online.elementAt(index).SC.getOutputStream()); //�ǵ������T�����Ȥ��
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
						DataOutputStream out = new DataOutputStream(Online.elementAt(i).SC.getOutputStream()); //�ǰT������ѬO�Ҧ��H
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
