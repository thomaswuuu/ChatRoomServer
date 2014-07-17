package jsv;

import java.net.*;
import java.io.*;

public class ConnectClient implements Runnable
{
	private Database Data = new Database();
	private ServerSocket server;
	private String LID = new String(); //登入時的帳號
	private boolean flag = true;
	private boolean status;
	private int mode; //模式選擇
	private int num; //客戶端數
	
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
				System.out.println("與客戶端 "+num+" 連線中...");
				Socket client = server.accept();
				System.out.println("與客戶端  "+num+" 連線成功!"+" From IP= " + client.getInetAddress());
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
						
							case 0:  //結束
								continue L1;
							case 1:  //新增								
								Adder Ad = new Adder(Data.Contact);
								status = Ad.AddAccount(in,out) ; //是否繼續新增帳號的狀態								
								if(!status) continue L2;								
								break;
							case 2:  //登入
								Log Li = new Log(Data);			
								LogInData LIData = Li.logInByClient(in, out,client);
								status = LIData.LogStatus; //登入的狀態
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
							case 0:  //登出
								Log Lo = new Log(Data.Online);
								Lo.logOutByClient(LID);  
								flag = true;								
								continue L2;
							case 1:  //觀看線上人數
								Checker ch = new Checker(Data.Online);
								ch.showOnlineToClient(out);								
								break;
							case 2:  //一對一聊天
								SingleChat Sc = new SingleChat(Data.Online);//將登入ID的一對一聊天旗標設為TRUE
								Sc.acceptClient(client,LID); //一對一聊天的狀態
								break;
							case 3:  //多人聊天
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


