package cn.becomegood.fly.chatroom.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JOptionPane;


public final class ConstantGUI {
	public static final int LOGIN_WIDTH = 200;
	public static final int LOGIN_HEIGHT = 110;
	public static final int CHATWINDOW_WIDTH = 650;
	public static final int CHATWINDOW_HEIGHT = 555;
	public static final int REGISTER_WIDTH = 200;
	public static final int REGISTER_HEIGHT = 300;
	//登录
	public static final int IN_WIDTH = 180;
	public static final int IN_HEIGHT = 140;
	//聊天选择面板
	public static final int OPTUI_WIDTH = 240;
	public static final int OPTUI_HEIGHT = 550;
	public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();     //得到屏幕大小
	
	
	public static Socket getSocket() {
		try {
			return new Socket(/*InetAddress.getLocalHost()*/InetAddress.getByName("120.27.100.225"),7788);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null,"无法连接服务器","Wrong",JOptionPane.WARNING_MESSAGE);
			return null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"无法连接服务器","Wrong",JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
	/**
	 * 随机字符串生成函数
	 * @param length	随机字符串长度
	 * @return
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
	
	//工具类构造器私有化
	private ConstantGUI(){

	}
}
