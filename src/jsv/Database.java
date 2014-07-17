package jsv;

import java.net.Socket;
import java.util.Vector;

public class Database
{
	public Vector<Account> Contact = new Vector<Account>();
	public Vector<Online> Online = new Vector<Online>();	
}

class Account
{
	public String ID = new String();
	public String PWD = new String();	
}

class Online
{
	public boolean OneToOneChatFlag = false;
	public boolean ChatRoomFlag = false;
	public Socket SC = new Socket();
	public String ID = new String();
	public String shareMessage = new String();
}

class LogInData
{
	public String ID = new String();
	public boolean LogStatus ;
}