package cn.becomegood.web_util;

import java.io.IOException;
import java.io.InputStream;

public class Request {

	private InputStream input;
	private String uri;
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
			uri = ParseUri(request.toString());

	}
	
	private String ParseUri(String requestString) {
		int index1 = requestString.indexOf(' ');
		int index2;
		if (index1 != -1){
			index2 = requestString.indexOf(' ', index1+1);
			if (index2>index1) {
				return requestString.substring(index1+1,index2);
			}
		}
		return null;
	}
	
	
	public String getUri() {
		return uri;
	}

	public void setBuffer_size(int buffer_size) {
		this.buffer_size = buffer_size;
	}
	public int getBuffer_size() {
		return buffer_size;
	}
	
	public Request(InputStream input) {
		this.input = input;
	}
}
