package jsv;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Adder 
{
	private Vector<Account> Contact = new Vector<Account>();
	
	public Adder()
	{	
	}
	
	public Adder(Vector<Account> Contact)
	{
		this.Contact = Contact;
	}
	
	private boolean adder(Account acnt) //新增帳號
	{		
		Contact.add(acnt);
		return true;
	}

	private boolean isUsed(String ID) //判斷帳號是否已使用
	{
		boolean result = false;
		
		for(int i=0;i<Contact.size();i++)
		{
			if(ID.equals(Contact.elementAt(i).ID))
			{
				result = true;
			}
		}
		
		return result;
	}
	
	public boolean AddAccount(BufferedReader in,DataOutputStream out) throws IOException
	{
		boolean addStatus = true;  //判斷是否繼續新增帳號
		boolean Message;  //回傳帳號是否已使用
		Account acnt = new Account();
		
		L1:	while(true)
		{
			acnt.ID = in.readLine();									
			if(acnt.ID.equals("000000")) //如果接收到到6個0，則停止新增帳號回到選擇模式
			{
				addStatus = false;
				break L1; 
			}
			
			acnt.PWD = in.readLine();
			if(isUsed(acnt.ID))  //如果帳號已被使用，回傳已使用並繼續新增
			{
				Message = false;									
				out.writeBoolean(Message);
				continue L1;
			}
			else   //帳號未使用，直接新增回傳允許使用此帳號
			{
				Message = adder(acnt);
				out.writeBoolean(Message);										
				break;
			}
		}
		return addStatus;
	}
}



