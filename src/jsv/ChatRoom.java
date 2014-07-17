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
				Online.elementAt(i).ChatRoomFlag = true; //設定此帳號進入一對一聊天的狀態
				indexInServer = i;  //此帳號的位置
				break;
			}
		}
	}
	
	public void chatRoom(Socket client,String LID)
	{
		setChatRoomFlag(LID);
		Thread ChR = new Thread(new ChatRead(Online, indexInServer, LID, client,false));
		ChR.start();
		while(ChR.isAlive()); //直到不再傳遞訊息給所有在聊天室的人才跳出
		Online.elementAt(indexInServer).ChatRoomFlag = false;
	}
}
