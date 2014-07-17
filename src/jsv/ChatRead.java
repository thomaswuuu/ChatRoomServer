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
		this.index = index; //�b��H��ѬO��H�b��Ʈw����m�A�b��ѫǬO���Ȥ᪺��m
		this.LogInId = LogInId;
		this.clientInServer = clientInServer;
		this.SingleChatFlag =flag;
	}
	
	//�[�ݲ�ѫǦ��֦b�u�W
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
		if(SingleChatFlag) //�@��@���
		{
			do{
				try 
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(clientInServer.getInputStream()));
					DataOutputStream out = new DataOutputStream(clientInServer.getOutputStream());					
					Online.elementAt(index).shareMessage += in.readLine(); //�q�Ȥ�ݭn����ѹ�H���T��
					out.writeBytes("I say:"+Online.elementAt(index).shareMessage+"\n"); //�^�ǵ��ۤv�n����ѹ�H�����e
					Thread ChWrite = new Thread(new ChatWrite(Online,index,LogInId,true));
					ChWrite.start();  //��Ū���T���s��@�ɸ�ơA�A�ǵ����
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}	
			}while(!Online.elementAt(index).shareMessage.equals("exit"));
		}
		else  //��ѫ�
		{
			do
			{
				try
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(clientInServer.getInputStream()));
					DataOutputStream out = new DataOutputStream(clientInServer.getOutputStream());
					Online.elementAt(index).shareMessage = in.readLine(); //�q�Ȥ�ݭn����ѹ�H���T��
					if(Online.elementAt(index).shareMessage.equals("1"))
						out.writeBytes("[--Online Contact in Chatroom--]\n"+viewChatRoomOnline()+"\n");
					out.writeBytes("I say:"+Online.elementAt(index).shareMessage+"\n"); //�^�ǵ��ۤv�n����ѫǩҦ��H�����e
					Thread ChWrite = new Thread(new ChatWrite(Online,index,LogInId,false));
					ChWrite.start();  //��Ū���T���s��@�ɸ�ơA�A�ǵ����
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}while(!Online.elementAt(index).shareMessage.equals("0"));;
		}
	}
	
}

