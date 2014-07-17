package jsv;

import java.io.*;
import java.net.*;
import java.util.Vector;
	

public class ChatRead implements Runnable
{	
	private Socket clientInServer  = new Socket();
	private Vector<Online> Online = new Vector<Online>();	
	private String LogInId = new String();
	private boolean SingleChatFlag = false;
	private int index;
	
	
	public ChatRead()
	{		
	}
	
	public ChatRead(Vector<Online> Online,int index,String LogInId,Socket clientInServer,boolean flag)
	{
		this.Online = Online;
		this.index = index; //在單人聊天是對象在資料庫的位置，在聊天室是此客戶的位置
		this.LogInId = LogInId;
		this.clientInServer = clientInServer;
		this.SingleChatFlag =flag;
	}
	
	//觀看聊天室有誰在線上
	private String viewChatRoomOnline()
	{
		String OnlinePeople = new String();
		
		for(int i=0;i<Online.size();i++)
		{
			if(Online.elementAt(i).ChatRoomFlag)
				OnlinePeople += Online.elementAt(i).ID+"\t";
		}
		
		return OnlinePeople;
	}
	

	public void run() 
	{	
		if(SingleChatFlag) //一對一聊天
		{
			do{
				try 
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(clientInServer.getInputStream()));
					DataOutputStream out = new DataOutputStream(clientInServer.getOutputStream());					
					Online.elementAt(index).shareMessage += in.readLine(); //從客戶端要給聊天對象的訊息
					out.writeBytes("I say:"+Online.elementAt(index).shareMessage+"\n"); //回傳給自己要給聊天對象的內容
					Thread ChWrite = new Thread(new ChatWrite(Online,index,LogInId,true));
					ChWrite.start();  //先讀取訊息存到共享資料，再傳給對方
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}	
			}while(!Online.elementAt(index).shareMessage.equals("exit"));
		}
		else  //聊天室
		{
			do
			{
				try
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(clientInServer.getInputStream()));
					DataOutputStream out = new DataOutputStream(clientInServer.getOutputStream());
					Online.elementAt(index).shareMessage = in.readLine(); //從客戶端要給聊天對象的訊息
					if(Online.elementAt(index).shareMessage.equals("1"))
						out.writeBytes("[--Online Contact in Chatroom--]\n"+viewChatRoomOnline()+"\n");
					out.writeBytes("I say:"+Online.elementAt(index).shareMessage+"\n"); //回傳給自己要給聊天室所有人的內容
					Thread ChWrite = new Thread(new ChatWrite(Online,index,LogInId,false));
					ChWrite.start();  //先讀取訊息存到共享資料，再傳給對方
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}while(!Online.elementAt(index).shareMessage.equals("0"));;
		}
	}
	
}

