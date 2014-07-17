package jsv;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class SingleChat 
{
	private Vector<Online> Online = new Vector<Online>();	
	private int indexInServer ;
	
	public SingleChat()
	{		
	}
	
	public SingleChat(Vector<Online> Online)
	{
		this.Online = Online;
	}
	
	
	private void setOneToOneChatFlag(String LogInId)
	{
		for(int i=0;i<Online.size();i++)
		{
			if(LogInId.equals(Online.elementAt(i).ID)) 
			{
				Online.elementAt(i).OneToOneChatFlag = true; //設定此帳號進入一對一聊天的狀態
				indexInServer = i;  //此帳號的位置
				break;
			}
		}
	}
	
	private void chat(Vector<Online> Online,int index,String LogInId,Socket clientInServer) throws IOException 
	{	
		//index是聊天對象在Online資料庫的位置
		boolean MessageCount = true;
		DataOutputStream out = new DataOutputStream(clientInServer.getOutputStream());
		do
		{
			if(!Online.elementAt(index).OneToOneChatFlag && MessageCount) //若對方不在一對一模式回傳訊息
			{
				out.writeBytes("no\n");
				MessageCount= false;
			}
			else if(Online.elementAt(index).OneToOneChatFlag)    //若對方進入則回傳對方已進入模式
				out.writeBytes("yes\n");
			
		}while(!Online.elementAt(index).OneToOneChatFlag);
		
		Thread ChRead = new Thread(new ChatRead(Online,index,LogInId,clientInServer,true));		
		ChRead.start();  //開始讀取訊息傳給對方
		while(ChRead.isAlive()); //直到不再傳遞訊息給對方才跳出
	}
	
	
	public void acceptClient(Socket client,String LogInId) throws IOException 
	{ 
		//client 是要伺服器幫忙傳訊息的客戶端
		boolean onlineFlag = false;
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		String contact = in.readLine();	 //接受要傳訊息的連絡人帳號
		
		for(int index=0;index<Online.size();index++)
		{
			if(contact.equals(Online.elementAt(index).ID)) 
			{
				onlineFlag = true;
				out.writeBoolean(onlineFlag);//此帳號在線上			
				setOneToOneChatFlag(LogInId); //設定此客戶已進入一對一聊天模式
				chat(Online,index,LogInId,client);
				break;
			}	
		}
		if(!onlineFlag) out.writeBoolean(onlineFlag);//此帳號不在線上
		
		Online.elementAt(indexInServer).OneToOneChatFlag = false; //結束後將此帳號的一對一聊天模式關閉
		
	}
	
	
}






