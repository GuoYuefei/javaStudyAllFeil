package cn.becomegood.fly.chatroom;

import java.io.IOException;
import java.net.Socket;


public class PrivateGetM extends AllGetM {
	private String MyToID;
	
	private String 	InforFromID;
	
	

	

	@Override
	protected synchronized void waitMessage() {
		while(isRun){
			try {
				message = new StringBuilder(reader.readLine());
				InforFromID = message.substring(0,message.indexOf(" "));		//私聊等待的消息是携带信息来源的id信息的
				message =new StringBuilder(message.substring(message.indexOf(" ")+1));
				System.out.println(InforFromID+"\n"+message);
				//当信息来源的id与本窗口对话的id相同时才进行显示
				if(message != null && InforFromID.equals(MyToID)){
					window.getDisplay().append(message.toString()+"\r\n");
					message = null;
				}
			} catch (IOException e) {
				isRun = false;
			}
		}
	
	}
	
	
	

	public PrivateGetM(Socket client, PrivateChatWindow chatWindow) {
		super(client, chatWindow);
		this.MyToID = chatWindow.getToID();
	}

}
