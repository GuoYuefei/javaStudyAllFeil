package cn.becomegood.fly.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * 多线程
 * 用于等待服务器端发送数据
 * 并接收数据
 * @author fly
 *
 */
public class AllGetM implements Runnable{
	//该输入流由socket传入
	protected BufferedReader reader;
	protected StringBuilder message = null;
	protected boolean isRun = true;
	protected ChatWindow window;
	
	@Override
	public void run() {
		waitMessage();
		
	}
	/**
	 * 客户端等待数据的接收
	 */
	protected synchronized void waitMessage() {
		while(isRun){
			try {
				message = new StringBuilder(reader.readLine());
				if(message != null){
					window.getDisplay().append(message.toString()+"\r\n");
					message = null;
				}
			} catch (IOException e) {
				isRun = false;
			}
		}
	}

	
	//对外提供得到message的方法
	public StringBuilder getMessage() {
		return message;
	}
	//对外提供修改方法
	public void setWindow(ChatWindow window) {
		this.window = window;
	}
	
	/**
	 * 传入一个socket，通过socket得到一个输入流，从而接收来自服务器的信息
	 * 构造函数
	 */
	public AllGetM(Socket client,ChatWindow chatWindow) {
		super();
		try {
			this.reader =new BufferedReader(new InputStreamReader(client.getInputStream(),"utf-8"));
			window = chatWindow;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//提高关闭线程的方法
	public void stop() {
		isRun = false;
	}

}
