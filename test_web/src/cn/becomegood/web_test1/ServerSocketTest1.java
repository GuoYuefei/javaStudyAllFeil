package cn.becomegood.web_test1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;


import cn.becomegood.web_util.PublicIP;
import cn.becomegood.web_util.Response;


public class ServerSocketTest1 {
	
	private static final String SHUTDOWN_COMMAND = "/shutdown";
	private static ServerSocket server;
	private boolean shutdown = false;

	public static void main(String[] args) {
		ServerSocketTest1 server = new ServerSocketTest1();
		server.await();
	}
	@SuppressWarnings("finally")
	/**
	 * 服务器等待客户端的信息，并处理
	 * @author fly
	 */
	public void await() {
		int port = 1234;
		InetAddress local;
		/**
		 * 与Response配合，保证htdocs文件夹存在，Response会继续使用这个if语句
		 */
		if (!Files.exists(Paths.get(System.getProperty("user.dir"),"htdocs"))) {
			try {
				Files.createDirectory(Paths.get(System.getProperty("user.dir"),"htdocs"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			local = InetAddress.getLocalHost();
//			local = InetAddress.getLocalHost();
			server = new ServerSocket(port,1,local);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(server.getInetAddress());
		while (!shutdown) {
			Socket socket =null;
			InputStream input = null;
			OutputStream ouput = null;
			try{
				socket = server.accept();
				System.out.println("与一个客户端建立连接");
				System.out.println("该客户端ip地址为："+socket.getInetAddress());
				System.out.println("该客户端端口："+socket.getPort());
				System.out.println();
				input = socket.getInputStream();
				ouput = socket.getOutputStream();
				cn.becomegood.web_util.Request request = new cn.becomegood.web_util.Request(input);
				request.parse();
				Response response = new Response(ouput);
				response.setRequest(request);
				response.SendStaticResource();
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
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
