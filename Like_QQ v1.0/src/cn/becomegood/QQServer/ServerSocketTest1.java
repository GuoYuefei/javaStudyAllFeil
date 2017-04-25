package cn.becomegood.QQServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import cn.becomegood.tools.Response;


public class ServerSocketTest1 extends Thread {
	public ServerSocketTest1(ServerSocket serverSocket) {
		server = serverSocket;
	}
	
	private static final String SHUTDOWN_COMMAND = "/shutdown";
	private static ServerSocket server;
	private boolean shutdown = false;
//
//	@Override
//	public void run() {
//		await();
//	}

	@SuppressWarnings("finally")
	/**
	 * 服务器等待客户端的信息，并处理
	 * @author fly
	 */
	public void await() {
		
		while (!shutdown) {
			Socket socket =null;
			InputStream input = null;
//			OutputStream ouput = null;
			try{
				socket = server.accept();
				System.out.println("与一个客户端建立连接");
				System.out.println("该客户端ip地址为："+socket.getInetAddress());
				System.out.println("该客户端端口："+socket.getPort());
				System.out.println();
				input = socket.getInputStream();
//				ouput = socket.getOutputStream();
				cn.becomegood.tools.Request request = new cn.becomegood.tools.Request(input);
				request.parse();
				Response response = new Response(request);
				response.setSend(socket.getInetAddress().toString());    //加入发送方的ip信息
				response.SendStaticResource();
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
				input.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				continue;
			}
		}
		System.out.println("服务器关闭！");
	}
}
