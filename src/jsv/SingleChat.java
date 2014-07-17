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
				Online.elementAt(i).OneToOneChatFlag = true; //�]�w���b���i�J�@��@��Ѫ����A
				indexInServer = i;  //���b������m
				break;
			}
		}
	}
	
	private void chat(Vector<Online> Online,int index,String LogInId,Socket clientInServer) throws IOException 
	{	
		//index�O��ѹ�H�bOnline��Ʈw����m
		boolean MessageCount = true;
		DataOutputStream out = new DataOutputStream(clientInServer.getOutputStream());
		do
		{
			if(!Online.elementAt(index).OneToOneChatFlag && MessageCount) //�Y��褣�b�@��@�Ҧ��^�ǰT��
			{
				out.writeBytes("no\n");
				MessageCount= false;
			}
			else if(Online.elementAt(index).OneToOneChatFlag)    //�Y���i�J�h�^�ǹ��w�i�J�Ҧ�
				out.writeBytes("yes\n");
			
		}while(!Online.elementAt(index).OneToOneChatFlag);
		
		Thread ChRead = new Thread(new ChatRead(Online,index,LogInId,clientInServer,true));		
		ChRead.start();  //�}�lŪ���T���ǵ����
		while(ChRead.isAlive()); //���줣�A�ǻ��T�������~���X
	}
	
	
	public void acceptClient(Socket client,String LogInId) throws IOException 
	{ 
		//client �O�n���A�������ǰT�����Ȥ��
		boolean onlineFlag = false;
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		String contact = in.readLine();	 //�����n�ǰT�����s���H�b��
		
		for(int index=0;index<Online.size();index++)
		{
			if(contact.equals(Online.elementAt(index).ID)) 
			{
				onlineFlag = true;
				out.writeBoolean(onlineFlag);//���b���b�u�W			
				setOneToOneChatFlag(LogInId); //�]�w���Ȥ�w�i�J�@��@��ѼҦ�
				chat(Online,index,LogInId,client);
				break;
			}	
		}
		if(!onlineFlag) out.writeBoolean(onlineFlag);//���b�����b�u�W
		
		Online.elementAt(indexInServer).OneToOneChatFlag = false; //������N���b�����@��@��ѼҦ�����
		
	}
	
	
}






