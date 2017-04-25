package cn.becomegood.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Response {

	Socket socket;
	Request request;
	String send;

	
	public void setSend(String send) {
		this.send = send;
	}
	public Response(Request request) {
		this.request = request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	
	
	public void SendStaticResource() {
		
		try {
			/**
			 * 建立通向发送方的socket
			 */
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("(MM/dd HH:mm:ss)");
			String nowDate = dateFormat.format(now);
			socket = new Socket(InetAddress.getByName(request.getReceiveIP()),12344);
			OutputStream output = socket.getOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(output,"UTF-8");
			outputStreamWriter.write(send+nowDate+":"+"\n"+request.getContent());
			outputStreamWriter.flush();
			output.close();
			outputStreamWriter.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
