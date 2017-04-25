package cn.becomegood.fly.chatroom;

import java.net.Socket;

public class PrivateSendM extends AllSendM {
	//私聊存在固定的本地id和指定私聊目标的id
	private String hostID;
	private String toID;

	@Override
	protected void initSend() {
		// 将自己的网名发送给服务器
		sendMessage(hostName,true);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 群聊时无ID区分，名字即为id，可是容易造成名字重复
		sendMessage(hostID,true);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// toID代表发送给所有人，因为这是群聊
		sendMessage(toID,true);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 是否是公共聊天,false表示私发
		isPublic = "false";
		sendMessage(isPublic,true);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String message) {
		writer.print("F" + "@ " + message + "\r\n"); // 第一位F作为标记位，用于给服务器判断是否已断开连接
		writer.flush();
	}
	public void sendMessage(String message,boolean notAt) {
		writer.print("F" + message + "\r\n");
		writer.flush();
	}
	/**
	 * 完整参数的构造函数
	 * @param client
	 * @param chatWindow
	 * @param hostName
	 * @param hostID
	 * @param toID
	 */
	public PrivateSendM(Socket client, ChatWindow chatWindow,String hostName,String hostID,String toID) {
		super(client, chatWindow,hostName);
		this.hostID = hostID;
		this.toID = toID;
	}

}
