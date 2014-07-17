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
	
	private boolean adder(Account acnt) //�s�W�b��
	{		
		Contact.add(acnt);
		return true;
	}

	private boolean isUsed(String ID) //�P�_�b���O�_�w�ϥ�
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
		boolean addStatus = true;  //�P�_�O�_�~��s�W�b��
		boolean Message;  //�^�Ǳb���O�_�w�ϥ�
		Account acnt = new Account();
		
		L1:	while(true)
		{
			acnt.ID = in.readLine();									
			if(acnt.ID.equals("000000")) //�p�G�������6��0�A�h����s�W�b���^���ܼҦ�
			{
				addStatus = false;
				break L1; 
			}
			
			acnt.PWD = in.readLine();
			if(isUsed(acnt.ID))  //�p�G�b���w�Q�ϥΡA�^�Ǥw�ϥΨ��~��s�W
			{
				Message = false;									
				out.writeBoolean(Message);
				continue L1;
			}
			else   //�b�����ϥΡA�����s�W�^�Ǥ��\�ϥΦ��b��
			{
				Message = adder(acnt);
				out.writeBoolean(Message);										
				break;
			}
		}
		return addStatus;
	}
}



