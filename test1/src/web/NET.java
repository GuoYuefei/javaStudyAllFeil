package web;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NET {
	
	public static void main(String[] args) {
		try {
			String netip = null;
			String localip = null;
			Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (networkInterface.hasMoreElements() && !finded) {
				NetworkInterface ni = networkInterface.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
				ip = address.nextElement();
				System.out.println(ip);
				if (!ip.isSiteLocalAddress()
				&& !ip.isLoopbackAddress()
				&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
				netip = ip.getHostAddress();
				finded = true;
				break;
				} else if (ip.isSiteLocalAddress()
				&& !ip.isLoopbackAddress()
				&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
				localip = ip.getHostAddress();
				}
				}
			}
			System.out.println(netip);
			System.out.println(localip);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
