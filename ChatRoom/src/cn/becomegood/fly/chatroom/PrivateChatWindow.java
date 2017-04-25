package cn.becomegood.fly.chatroom;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.becomegood.fly.chatroom.util.DataUtil;

public class PrivateChatWindow extends ChatWindow {
	private String hostID;			//该私聊窗口对于用户的ID
	private String toID;			//该私聊窗口私聊通道的id


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	@Override
	public void init() {
		super.init();
		DataUtil dataUtil = new DataUtil();
		dataUtil.dataExe(DataUtil.QUERY,"select user_name from users where user_id=? limit 0,1",toID);
		ResultSet resultSet = dataUtil.getResultSet();
		try {
			resultSet.next();
			this.setTitle("您与"+resultSet.getString(1)+"的聊天窗口");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}






	@Override
	protected void getAndSend() {
		//开启发送消息的线程
		aSendM = new PrivateSendM(socket,this,hostName,hostID,toID);
		Thread aSendThread = new Thread(aSendM);
		aSendThread.start();
		//开启接收消息的线程
		aGetM = new PrivateGetM(socket,this);
		Thread aGetThread = new Thread(aGetM);
		aGetThread.start();
		
	}






	public PrivateChatWindow(String userName,String hostID,String toID) throws HeadlessException {
		super(userName);
		this.hostID = hostID;
		this.toID = toID;
	}


	public String getHostID() {
		return hostID;
	}

	public String getToID() {
		return toID;
	}
	

}
