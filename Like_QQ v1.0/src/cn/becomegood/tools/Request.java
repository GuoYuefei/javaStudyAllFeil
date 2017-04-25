package cn.becomegood.tools;

import java.io.IOException;
import java.io.InputStream;


public class Request {

	private InputStream input;
	private String content;
	private String receiveIP;
	private int buffer_size = 2048;
	
	public void parse() {
		StringBuilder request = new StringBuilder(2048);
		byte[] buffer = new byte[buffer_size];
		/**
		 * 通过while循环可以将大于2048字节的数据放入request中
		 */
		try {
			int i = buffer_size;
			while (i == buffer_size){
				i=input.read(buffer);
				if(i==-1) break;
				for (int j=0;j<i;j++){
					request.append((char)buffer[j]);
				}
			}
			System.out.println(i);
		}catch(IOException e){
			e.printStackTrace();
		}

			System.out.println(request.toString());
			ParseAll(request.toString());

	}
	/**
	 * 根据自定义报文 内容+‘ ’+ip的规则整理数据
	 * @param requestString
	 */
	private void ParseAll(String requestString) {
		int index2 = requestString.lastIndexOf(' ');
		content = requestString.substring(0,index2);
		receiveIP = requestString.substring(index2+1,requestString.length());
	}
	
	
	public String getContent() {
		return content;
	}
	public String getReceiveIP() {
		return receiveIP;
	}
	public String getUri() {
		return content;
	}

	public void setBuffer_size(int buffer_size) {
		this.buffer_size = buffer_size;
	}
	public int getBuffer_size() {
		return buffer_size;
	}
	/**
	 * 构造函数，传入一个发送的报文的流
	 * @param input
	 */
	public Request(InputStream input) {
		this.input = input;
	}
}
