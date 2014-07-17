package jsv;

import java.net.*;
import java.io.*;

public class ConnectClient implements Runnable
{
	private Database Data = new Database();
	private ServerSocket server;
	private String LID = new String(); //�n�J�ɪ��b��
	private boolean flag = true;
	private boolean status;
	private int mode; //�Ҧ����
	private int num; //�Ȥ�ݼ�
	
	public ConnectClient()
	{
		
	}
	
	public ConnectClient(ServerSocket server,Database Data,int num) 
	{
		this.server = server;
		this.Data = Data;
		this.num = num;
	}
	
	public void run()
	{
		try
		{
		L1:	while(true)
			{
				System.out.println("�P�Ȥ�� "+num+" �s�u��...");
				Socket client = server.accept();
				System.out.println("�P�Ȥ��  "+num+" �s�u���\!"+" From IP= " + client.getInetAddress());
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				
			
			L2:	while(true)
				{
					mode=in.read();
					System.out.println("IP:" +client.getInetAddress());
					System.out.println("Mode= "+mode);
			
					if(flag)
					{
						switch(mode)
						{
						
							case 0:  //����
								continue L1;
							case 1:  //�s�W								
								Adder Ad = new Adder(Data.Contact);
								status = Ad.AddAccount(in,out) ; //�O�_�~��s�W�b�������A								
								if(!status) continue L2;								
								break;
							case 2:  //�n�J
								Log Li = new Log(Data);			
								LogInData LIData = Li.logInByClient(in, out,client);
								status = LIData.LogStatus; //�n�J�����A
								if(!status) continue L2;
								LID = LIData.ID;
								System.out.println("LogInID= "+LID);
								flag = false;								
								break;
							default:
								break;
						}
					}
					else
					{
						switch(mode)
						{
							case 0:  //�n�X
								Log Lo = new Log(Data.Online);
								Lo.logOutByClient(LID);  
								flag = true;								
								continue L2;
							case 1:  //�[�ݽu�W�H��
								Checker ch = new Checker(Data.Online);
								ch.showOnlineToClient(out);								
								break;
							case 2:  //�@��@���
								SingleChat Sc = new SingleChat(Data.Online);//�N�n�JID���@��@��ѺX�г]��TRUE
								Sc.acceptClient(client,LID); //�@��@��Ѫ����A
								break;
							case 3:  //�h�H���
								ChatRoom ChRoom = new ChatRoom(Data.Online);
								ChRoom.chatRoom(client,LID);
								break;
							default:
								break;
						}
				
					}			
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}


