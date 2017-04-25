package cn.becomegood.web_test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketTest1 {
	public static void main(String[] args) {
		try {
			InetAddress local = InetAddress.getByName("127.0.1.1");
			Socket client = new Socket(local,1234);
			OutputStream OS = client.getOutputStream();
			PrintWriter PW = new PrintWriter(new OutputStreamWriter(OS,"UTF-8"));
			PW.write(" index.html ");
			PW.flush();
			InputStream input = client.getInputStream();
			BufferedReader buReader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			String str = buReader.readLine();
			while(str != null){
				System.out.println(str);
				str = buReader.readLine();
			}
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
