package cn.becomegood.QQServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import cn.becomegood.tools.PublicIP;

public class ServerMain {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 12345;
		InetAddress local;
		try {
//			local = InetAddress.getLocalHost();
			local = PublicIP.getPublicIP();
			serverSocket = new ServerSocket(port,100,local);
			System.out.println(serverSocket.getInetAddress());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerSocketTest1 server = new ServerSocketTest1(serverSocket);
		server.await();
		
	}
}
