package jsv;

import java.net.Socket;
import java.util.Vector;

public class ChatRoom
{
	private Vector<Online> Online = new Vector<Online>();	
	private int indexInServer ;
	
	public ChatRoom()
	{		
	}
	
	public ChatRoom(Vector<Online> Online)
	{
		this.Online = Online;
		
	}
	
	private void setChatRoomFlag(String LogInId)
	{
		for(int i=0;i<Online.size();i++)
		{
			if(LogInId.equals(Online.elementAt(i).ID)) 
			{
				Online.elementAt(i).ChatRoomFlag = true; //�]�w���b���i�J�@��@��Ѫ����A
				indexInServer = i;  //���b������m
				break;
			}
		}
	}
	
	public void chatRoom(Socket client,String LID)
	{
		setChatRoomFlag(LID);
		Thread ChR = new Thread(new ChatRead(Online, indexInServer, LID, client,false));
		ChR.start();
		while(ChR.isAlive()); //���줣�A�ǻ��T�����Ҧ��b��ѫǪ��H�~���X
		Online.elementAt(indexInServer).ChatRoomFlag = false;
	}
}
