package cn.becomegood.web_util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Response {

	private static final int BUFFER_SIZE =1024;
	Request request;
	OutputStream output;
	public Response(OutputStream output) {
		this.output = output;
	}
	public Response(Request request) {
		this.request = request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public void SendStaticResource() {
		
		byte[] bytes = new byte[BUFFER_SIZE];
		Path path = Paths.get(System.getProperty("user.dir"),"htdocs",request.getUri());
		if (!Files.exists(Paths.get(System.getProperty("user.dir"),"htdocs"))) {
			try {
				Files.createDirectory(Paths.get(System.getProperty("user.dir"),"htdocs"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Files.exists(path)) {
			try{InputStream inputStream = Files.newInputStream(path);
				int ch = inputStream.read(bytes,0,BUFFER_SIZE);
				while(ch != -1){
					output.write(bytes,0,ch);
					ch = inputStream.read(bytes,0,BUFFER_SIZE);
				}
			}catch (IOException e) {
				
			}
		}else {
			String errString = "HTTP/1.1 404 File Not Found\r\n"
					+"Content-type: TEXT/ttml\r\t"
					+"<h1>File Not Found</h1>";
			try {
				output.write(errString.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
