package cn.becomegood.web_util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class PublicIP {
	private static URL ip_url = null;

	
	/**
	 * 可以得到当前主机的公网IP
	 * @return
	 */
	public static InetAddress getPublicIP() {
		try {
			ip_url = new URL("http://1212.ip138.com/ic.asp");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		InetAddress publicIP = null;
		StringBuilder builder = new StringBuilder(); 
		try {
			URLConnection urlConnection = ip_url.openConnection();
			InputStream input = urlConnection.getInputStream();
			InputStreamReader reader = new InputStreamReader(input,"GB2312");
			BufferedReader bufferedReader= new BufferedReader(reader);
			String str = null;
			while((str = bufferedReader.readLine()) != null){
				builder.append(str+"\r\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int index1 = builder.indexOf("[")+1;
			int index2 = builder.indexOf("]");
			String ip_String = builder.substring(index1,index2);
			publicIP = InetAddress.getByName(ip_String);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return publicIP;
	}
}
