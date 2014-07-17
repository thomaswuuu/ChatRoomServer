package jsv;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class Log 
{
	private Database data = new Database();
	private Vector<Online> Online = new Vector<Online>();
	
	public Log()
	{		
	}
	
	public Log(Database data) //只有在登入模式才使用
	{
		this.data = data;
	}
	
	public Log(Vector<Online> Online) //只有在登出模式才使用
	{
		this.Online = Online;
	}
	
	private boolean isIdExist(String ID)
	{
		boolean flag = false;
		
		for(int i=0;i<data.Contact.size();i++)   //判斷帳號是否存在
		{
			if(ID.equals(data.Contact.elementAt(i).ID)) flag = true;
		}
		
		return flag;
	}
	
	private boolean isPasswordExist(Account acnt)
	{
		boolean flag = false;
		
		for(int i=0;i<data.Contact.size();i++)
		{
			if(acnt.ID.equals(data.Contact.elementAt(i).ID)   //判斷帳號密碼是否一致
				&& acnt.PWD.equals(data.Contact.elementAt(i).PWD) )
				flag = true;			
		}
		
		return flag;
	}
	
	//登入主函式	
	public LogInData logInByClient(BufferedReader in,DataOutputStream out,Socket clientInServer) throws IOException
	{
		Online contact = new Online();
		Account acnt = new Account();
		LogInData LIData = new LogInData();
		LIData.LogStatus = false; //判斷是否登入成功
		boolean Message;  //判斷是否帳密存在
	
		acnt.ID = in.readLine();
		acnt.PWD = in.readLine();
		
		if(isIdExist(acnt.ID))
		{
			Message = true;
			out.writeBoolean(Message);  //帳號存在，傳送存在訊息給客戶端		
			if(isPasswordExist(acnt))  //密碼正確
			{
				contact.ID = acnt.ID;
				contact.SC = clientInServer;				
				data.Online.add(contact);  //當帳密都存在且正確，放進登入資料庫
				LIData.ID = acnt.ID; 	   //確認帳號登入後回傳已登入帳號
				out.writeBoolean(Message);	//傳送成功登入訊息客戶端		
				LIData.LogStatus = true;  //確認登入
			}
			else   //表示帳號正確但密碼錯誤
			{
				Message = false;    
				out.writeBoolean(Message);  
			}
		}
		else     //表示帳密都不存在
		{
			Message = false;
			out.writeBoolean(Message);  
			out.writeBoolean(Message);			
		}
		
		return LIData;		
	}
	
	//登出主函式
	public void logOutByClient(String ID)  //從登入資料庫中移除登入帳號
	{
		int index;
		
		for(index=0;index<Online.size();index++)
		{
			if(ID.equals(Online.elementAt(index).ID))
			{
				Online.remove(index);
				break;
			}
		}

	}
}

