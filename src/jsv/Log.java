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
	
	public Log(Database data) //�u���b�n�J�Ҧ��~�ϥ�
	{
		this.data = data;
	}
	
	public Log(Vector<Online> Online) //�u���b�n�X�Ҧ��~�ϥ�
	{
		this.Online = Online;
	}
	
	private boolean isIdExist(String ID)
	{
		boolean flag = false;
		
		for(int i=0;i<data.Contact.size();i++)   //�P�_�b���O�_�s�b
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
			if(acnt.ID.equals(data.Contact.elementAt(i).ID)   //�P�_�b���K�X�O�_�@�P
				&& acnt.PWD.equals(data.Contact.elementAt(i).PWD) )
				flag = true;			
		}
		
		return flag;
	}
	
	//�n�J�D�禡	
	public LogInData logInByClient(BufferedReader in,DataOutputStream out,Socket clientInServer) throws IOException
	{
		Online contact = new Online();
		Account acnt = new Account();
		LogInData LIData = new LogInData();
		LIData.LogStatus = false; //�P�_�O�_�n�J���\
		boolean Message;  //�P�_�O�_�b�K�s�b
	
		acnt.ID = in.readLine();
		acnt.PWD = in.readLine();
		
		if(isIdExist(acnt.ID))
		{
			Message = true;
			out.writeBoolean(Message);  //�b���s�b�A�ǰe�s�b�T�����Ȥ��		
			if(isPasswordExist(acnt))  //�K�X���T
			{
				contact.ID = acnt.ID;
				contact.SC = clientInServer;				
				data.Online.add(contact);  //��b�K���s�b�B���T�A��i�n�J��Ʈw
				LIData.ID = acnt.ID; 	   //�T�{�b���n�J��^�Ǥw�n�J�b��
				out.writeBoolean(Message);	//�ǰe���\�n�J�T���Ȥ��		
				LIData.LogStatus = true;  //�T�{�n�J
			}
			else   //��ܱb�����T���K�X���~
			{
				Message = false;    
				out.writeBoolean(Message);  
			}
		}
		else     //��ܱb�K�����s�b
		{
			Message = false;
			out.writeBoolean(Message);  
			out.writeBoolean(Message);			
		}
		
		return LIData;		
	}
	
	//�n�X�D�禡
	public void logOutByClient(String ID)  //�q�n�J��Ʈw�������n�J�b��
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

