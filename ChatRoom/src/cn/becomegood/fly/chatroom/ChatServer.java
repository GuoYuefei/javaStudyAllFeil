package cn.becomegood.fly.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.becomegood.fly.chatroom.util.DataUtil;
import cn.becomegood.fly.chatroom.util.PublicIP;


/**
 * 
 * @author fly
 * @version V2.0
 */
public class ChatServer {
	private ServerSocket server = null;
	//将所有建立连接的客户放入容器统一管理
	private List<Connect> all = new ArrayList<Connect>(); 
	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
		chatServer.init();
		chatServer.connectClient();
	}

	//负责监听客户端请求
	@SuppressWarnings("finally")
	public void connectClient() {
		while (true) {
			try {
				Socket client = server.accept();
				Connect connectClient = new Connect(client);
				all.add(connectClient);
				Thread conneThread = new Thread(connectClient);
				conneThread.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				continue;
			}
		}
	}
	/**
	 * 用于接收客户端发送的网名
	 * @param client
	 * @return name
	 */
//	@SuppressWarnings("finally")
//	private String  getName(Socket client) {
//		String name = null;
//		try {
//			BufferedReader reader = new BufferedReader(
//					new InputStreamReader(client.getInputStream(), "utf-8"),
//					180);
//			name = reader.readLine();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			return name;
//		}
//	}
	/**
	 * 初始化
	 * 创建服务器监听
	 */
	public void init() {
		try {
			int port = 7788;
			int backlog = 100;
			server = new ServerSocket(port,backlog,/*InetAddress.getLocalHost()*/PublicIP.getPublicIP());
			System.out.println(server.getInetAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ServerSocket getServer() {
		return server;
	}

/****************************************************************************************************/	
	/**
	 * 内部类
	 * 服务一个客户端的一条道路
	 * 多线程继承Runnale接口
	 * @author fly
	 *
	 */
	class Connect implements Runnable {
		Socket socket;
		String userName = null;
		String hostID = null;			//将来会增加这两个量的获取
		String toID = null;
		String isPublic = null;
		BufferedReader reader;
		PrintWriter writer;
		private boolean isRun = true;
		@Override
		public void run() {
			await();
		
		}
		
		@SuppressWarnings({ "finally" })
		private void await(){
			SimpleDateFormat SDF = new SimpleDateFormat("hh:mm:ss");
			while (isRun) {
				boolean isSend = false;
				//当客户端关闭时就关闭循环
				try {
					if(/*lineString.toString().equals(ConstantGUI.EXIT)*/socket.getInputStream().read() == -1){
						Thread.sleep(250);
						all.remove(this);
						reader.close();
						writer.close();
						socket.close();
						isRun = false;
						break;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					all.remove(this);
					writer.close();
					isRun = false;
					break;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					all.remove(this);
					writer.close();
					isRun = false;
					break;
				}
				
				
				try {
					reader = new BufferedReader(
							new InputStreamReader(socket.getInputStream(), "utf-8"),
							720);
					writer = new PrintWriter(new OutputStreamWriter(
							socket.getOutputStream(),"utf-8"));
					StringBuilder lineString = new StringBuilder(reader.readLine());
					// 当name为空时用户发送的第一个字符就为其名字  后面if以此类推
					if (userName == null) {
						this.userName = lineString.toString();
						System.out.println("name "+lineString);
						continue;
					}
					if (hostID == null) {
						this.hostID = lineString.toString();
						System.out.println("hostID "+hostID);
						continue;
					}
					if (toID == null) {
						this.toID = lineString.toString();
						System.out.println("toID "+toID);
						continue;
					}
					if (isPublic == null) {
						this.isPublic = lineString.toString();
						System.out.println("isPublic "+isPublic);
						continue;
					}
					System.out.println(lineString);
					
					//转发信息部分
					//如果有@，就找出@的人并发送
					if (lineString.toString().startsWith("@")) {
						//,toID为@与空格之间
						if (isPublic.equals("true")) {
							toID = lineString.substring(1,lineString.indexOf(" "));
							//遍历发送信息到用户指定的一个客户端
							for (Connect one : all) {
								//如果指定的名字存在就发送
								if(toID.equals(one.userName)){
									String mess = lineString.substring(lineString.indexOf(" ")+1);
									one.sendMessage("(私)"+this.userName+"("+SDF.format(new Date())+"): "+mess);
									isSend = true;			//如果发送了，就把标志置为true;
								}
							}
							if (!isSend) {					//如果isSend还是false，那么就提示发送人
								this.sendMessage("系统：未找到您所指定的人，请核对名字！！！");
							}
						}else if (isPublic.equals("false")) {		//如果是私聊
							//遍历发送信息到用户指定的一个客户端
							for (Connect one : all) {
								//如果指定的ID存在就发送
								if(toID.equals(one.hostID)){
									String mess = lineString.substring(lineString.indexOf(" ")+1);
									one.sendMessage(this.hostID+" "+this.userName+"("+
											SDF.format(new Date())+"): "+mess);
									isSend = true;			//如果发送了，就把标志置为true;
								}
							}
							if (!isSend) {
								this.sendMessage("系统：发送失败！请重发！");
							}
						}
					}else {
						//如果没有@，遍历发送信息到每一个客户端
						for (Connect one : all) {
							if(one != this){
								one.sendMessage(this.userName+"("+SDF.format(new Date())+"): "+lineString);
							}
						}
					}

					} catch (IOException e) {
					e.printStackTrace();
				} finally {
					continue;
				}
			}
			
		}
		//向这个线程连接的客户端发送信息
		public void sendMessage(String message){
			if (message.trim() != "") {
				writer.print(message+"\r\n");
				writer.flush();
			}
		}

		/**
		 * 通过传入一个socket，使得本类对对应的客户端进行操作
		 * @param socket
		 */
		public Connect(Socket socket) {
			this.socket = socket;
		}
		/**
		 * 可以传入客户端的名字
		 * @param socket
		 * @param name
		 */
		public Connect(Socket socket, String name) {
			this(socket);
			this.userName = name;
		}
		
	}
}
