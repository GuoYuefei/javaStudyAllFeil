package cn.becomegood.web_test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * URLConnection类的学习试验
 * 低级的网络爬虫
 * @author fly
 * @version 1.0
 */
public class URLConnectionTest1 {
	private Charset charset = Charset.forName("UTF-8");
	private String fileString = "web_log.html";
	//得到当前工作文件夹
	private Path path = Paths.get(System.getProperty("user.dir"),"log",fileString);
	
	public static void main(String[] args) {
		URLConnectionTest1 urlConnectionTest1 = new URLConnectionTest1();
		urlConnectionTest1.urlConnection1("http://localhost:8080/testEE/greeting.jsp");
	}
	/**
	 * 通过输入网址就可以得到其响应头 从命令行输出
	 * 得到网页代码放入当前工作目录下log文件夹的名为fileString的文件中
	 * @param urlString
	 */
	public void urlConnection1(String urlString) {
		if(!Files.exists(Paths.get(System.getProperty("user.dir"),"log"))){
			try {
				Files.createDirectory(Paths.get(System.getProperty("user.dir"),"log"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{OutputStream outputStream = Files.newOutputStream(path,
				StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
				PrintStream printStream = new PrintStream(outputStream);
				PrintStream old = System.out;
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();
			Map<String,List<String>> headers = urlConnection.getHeaderFields();
			Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
			for (Map.Entry<String, List<String>> entry : entrySet) {
				String headerName = entry.getKey();
				System.out.println("Header Name:"+headerName);
				List<String> headerValues = entry.getValue();
				for (String values : headerValues) {
					System.out.println("Header Values:"+values);
				}
				System.out.println("\n");
			}
			System.setOut(printStream);
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream,charset));
			String line = buffer.readLine();
			while (line != null) {
				System.out.println(line);
				line = buffer.readLine();
			}
			System.setOut(old);
			System.out.println("over load！");
			inputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public URLConnectionTest1(Charset charset, String fileString) {
		super();
		this.charset = charset;
		this.fileString = fileString;
	}
	public URLConnectionTest1(Charset charset) {
		super();
		this.charset = charset;
	}
	public URLConnectionTest1(String fileString) {
		super();
		this.fileString = fileString;
	}
	public URLConnectionTest1(){
		
	}
	

	
}
