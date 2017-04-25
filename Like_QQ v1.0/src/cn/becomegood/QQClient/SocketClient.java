package cn.becomegood.QQClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class SocketClient extends Thread{
	
	private ServerSocket server;
	static String hostIP = "120.27.100.225";
	private boolean shutdown = true;
	
	@Override
	public void run() {
		receive();
	}
	/**
	 * 接收服务器的消息
	 */
	@SuppressWarnings("finally")
	private void receive() {
		/**
		 * 创建一个本地的服务器，方便接收信息
		 */
		try {
			server = new ServerSocket(12344,10,InetAddress.getByName("192.168.1.4"));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		 * 循环保持监听状态
		 */
		while(shutdown){
			try {
				Socket socket1 =  server.accept();
				InputStream inputStream = socket1.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				String str = reader.readLine();
				while(str != null){
					System.out.println(str);
					str = reader.readLine();
				}
				System.out.println("\nMe:");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				continue;
			}
		}
	}
	/**
	 * 客户端发送消息
	 * @param client
	 */
	public void await() {
		
		try {
			Scanner scanner =new Scanner(System.in);
			while(true){
				System.out.print("请您输入联系人的IP地址：");
				String receiveIP = scanner.nextLine();
				if (receiveIP.equals("exit")) {
					scanner.close();
					break;
				}
				System.out.println("Me:");
				while(true){
					Socket socket = new Socket(InetAddress.getLocalHost(),12345);
					String content = scanner.nextLine();
					System.out.println();
					if (content.equals("bye")) {
						break;
					}
					OutputStream outputStream = socket.getOutputStream();
					OutputStreamWriter writer = new OutputStreamWriter(outputStream,"UTF-8");
					writer.write(content+' '+receiveIP);
					writer.flush();
					writer.close();
					socket.close();
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
