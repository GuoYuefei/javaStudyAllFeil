package cn.becomegood.fly.chatroom;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import javax.swing.JFrame;

import cn.becomegood.fly.chatroom.util.ConstantGUI;

public class AllSendM implements Runnable {
	String hostName;
	PrintWriter writer;
	StringBuilder message;
	private boolean isRun = true;
	// private Scanner console;
	private ChatWindow window;
	protected String isPublic;

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		//把必要信息发送给服务器处理
		initSend();
		// 不断接收输入信息，和发送信息
		while (isRun) {
			while (window.getMessage() == null && isRun) { // 只要isRun置于false就停止该线程
				try {
					Thread.currentThread().sleep(40); // 等待40ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			message = window.getMessage();
			// System.out.println(message);
			if (!message.toString().trim().equals("") && message != null) {
				sendMessage(message.toString());
				window.setMessage(null);
			}
			message = null;
		}
	}
	
	/*
	 * 初始化发送信息
	 * 使用保护权限，只能被子类使用或重写
	 */
	protected void initSend() {
		// 将自己的网名发送给服务器
		sendMessage(hostName);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 群聊时id无效，发送任意随机字符串
		sendMessage(ConstantGUI.getRandomString(20));
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// toID代表发送给所有人，因为这是群聊
		sendMessage("all");
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 是否是公共聊天
		isPublic = "true";
		sendMessage(isPublic);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 发送信息
	public void sendMessage(String message) {
		writer.print("F" + message + "\r\n"); // 第一位F作为标记位，用于给服务器判断是否已断开连接
		writer.flush();
	}
	


	/*
	 * 构造函数
	 */
	public AllSendM(Socket client, ChatWindow chatWindow) {
		super();
		try {
			this.writer = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream(), "utf-8"), true);
			window = chatWindow;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AllSendM(Socket client, ChatWindow chatWindow,String hostName) {
		super();
		try {
			this.writer = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream(), "utf-8"), true);
			window = chatWindow;
			this.hostName = hostName;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	// 关闭线程方法
	public void stop() {
		isRun = false;
	}

	public String gethostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setMessage(StringBuilder message) {
		this.message = message;
	}

	public void setWindow(ChatWindow window) {
		this.window = window;
	}

}