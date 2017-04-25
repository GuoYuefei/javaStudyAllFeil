package cn.becomegood.fly.attact.Simple;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
/**
 * 一个错误的类，前车之鉴
 * 错误：没有运用独立线程，可以参照NewSimpleA.java
 * @author fly
 *
 */
public class SimpleA {
	private Integer i = 1;
	JProgressBar jBar;
	public Window window;
	
	public SimpleA(Window window){
		this.window = window;
	}
//	public static void main(String[] args) {
//		JProgressBar jBar;
//		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//		JFrame jF = new JFrame("进度显示");
////		jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	jF.setLayout(new GridLayout(1,1));
//	jF.setBounds((int)dimension.getWidth()/2-jF.getWidth()/2,0,
//			jF.getWidth(),jF.getHeight());
//	jF.setSize(350,60);
//	jF.setVisible(true);
//	jBar = new JProgressBar(0,100);
//	jBar.setStringPainted(true);
//	int i=1;
//	int times = 1000;
//	jF.add(jBar); 
//	while(i<=times){
//		jBar.setValue((int)((i++*1.0/times)*100));
//		System.out.println(jBar.getValue());
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	}


	/**
	 * 
	 * @param IP 发送的 ip地址
	 * @param prot 端口
	 * @param message 信息
	 * @param times 多少次
	 */
	public void quickSend(final String IP,final int prot,final String message,final int times) {
		jBar=null;
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		JDialog jD = new JDialog(window,"进度显示");
//			jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jD.setLayout(new GridLayout(1,1));
		jD.setBounds((int)dimension.getWidth()/2-jD.getWidth()/2,0,
				jD.getWidth(),jD.getHeight());
		jD.setSize(350,60);
		jD.setVisible(true);
		jBar = new JProgressBar(0,100);
		jBar.setStringPainted(true);
		jD.add(jBar);
		
		
		while(times>=this.i){
			InetAddress local = null;
			try {
				local = InetAddress.getByName(IP);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{ 
				Socket client = new Socket(local,prot);
				OutputStream OS = client.getOutputStream();
				PrintWriter PW = new PrintWriter(new OutputStreamWriter(OS,"UTF-8"));
					
				PW.write(message);
				PW.flush();
				OS.close();
				PW.close();
				client.close();
				synchronized (i) {
					jBar.setValue((int)((i*1.0/times)*100));
					System.out.println(jBar.getValue());	
					i++;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		i=1;
	}
}
