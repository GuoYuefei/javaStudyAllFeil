package cn.becomegood.fly.chatroom;

import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.becomegood.fly.chatroom.util.ConstantGUI;

public class ChatWindow extends JFrame {

	private final SimpleDateFormat SDF = new SimpleDateFormat("hh:mm:ss");
	private final JTextArea displayText = new JTextArea();
	private final JScrollPane displayPane = new JScrollPane(displayText); // 文本操作用JtextArea，界面操作用它
	private final JTextArea messageBox = new JTextArea();
	private final JScrollPane messageBoxPane = new JScrollPane(messageBox); // 同上
	private JButton sendB = new JButton("发送");
	private boolean toSend = true; // 用于取消回车释放时的事件
	protected final Socket socket = ConstantGUI.getSocket();
	private StringBuilder message = null;
	public String hostName = null;
	protected AllSendM aSendM;
	protected AllGetM aGetM;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ChatWindow window = new ChatWindow("gu");
		window.init();
	}
	
	/*
	 * 用于启动发送和接收信息的线程
	 * 子类可以将其重写，使用不同的线程
	 * 重写的时候可以用allSendM等的子类
	 */
	protected void getAndSend() {
		// 发送消息部分
		aSendM = new AllSendM(socket, this);
		aSendM.setHostName(hostName);
		Thread sendMThread = new Thread(aSendM);
		sendMThread.start();
		// 接收消息部分
		aGetM = new AllGetM(socket, this);
		Thread getMThread = new Thread(aGetM);
		getMThread.start();
	}

	public void init() {
		//加入发送信息功能
		getAndSend();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setLayout(null);
		this.setTitle(hostName + "'s Chatroom");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// 点xx退出窗口，并返回所有资源
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				aGetM.stop();
				dispose();
				aSendM.stop();
			}

		});

		// 组件属性设置，包括两个文本框的可编辑属性和按钮监听事件
		displayText.setEditable(false);
		displayText.setLineWrap(true); // 自动换行
		displayText.setFont(new Font("新宋体", Font.PLAIN, 15));
		displayPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// 设置垂直滑块出现
		messageBox.setEditable(true);
		messageBox.setLineWrap(true);
		messageBox.setFont(new Font("新宋体", Font.PLAIN, 12));
		messageBoxPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 同上
		sendB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				message = new StringBuilder(messageBox.getText());
				sendMessage();
			}
		});
		// 通过Toolkit的工具类实现键盘的全局监听
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				if (event instanceof KeyEvent
						&& ((KeyEvent) event).getKeyCode() == 10) {
					if (toSend) {
						message = new StringBuilder(messageBox.getText());
						sendMessage();
					}
					messageBox.setText(null); // 清除回车之后的换行
					toSend = !toSend; // 清除回车释放执行事件
				}
			}
		}, 12);
		// //窗口关闭监听
		// this.addWindowListener(new WindowAdapter() {
		//
		// @Override
		// public void windowClosing(WindowEvent e) {
		// message = new StringBuilder(ConstantGUI.EXIT);
		// try {
		// Thread.sleep(250); //足够的时间等待其他线程读取
		// } catch (InterruptedException e1) {
		// e1.printStackTrace();
		// }
		// super.windowClosing(e);
		// }
		//
		// });
		// 在窗口中增加东西
		this.add(displayPane);
		this.add(messageBoxPane);
		this.add(sendB);
		this.setVisible(true);
		// 设置位置属性
		this.setBounds(
				(int) (ConstantGUI.SCREENSIZE.width - ConstantGUI.CHATWINDOW_WIDTH) / 2,
				(int) (ConstantGUI.SCREENSIZE.height - ConstantGUI.CHATWINDOW_HEIGHT) / 2,
				ConstantGUI.CHATWINDOW_WIDTH, ConstantGUI.CHATWINDOW_HEIGHT);
		displayPane.setBounds(5, 5, 640, 365);
		messageBoxPane.setBounds(5, 380, 640, 105);
		sendB.setBounds(535, 490, 85, 30);

	}

	/*
	 * 将messageBox框中文本发送出去 其实质是将文本转移到display框中
	 */
	private void sendMessage() {
		if (!message.toString().trim().equals("") && message != null) {
			displayText.append(hostName + "(" + SDF.format(new Date()) + "): "
					+ message + "\r\n");
			messageBox.setText(null);
			try {
				Thread.sleep(200); // 等待其他线程读取数据后清0
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		message = null;
	}

	public ChatWindow(String hostName) throws HeadlessException {
		super();
		this.hostName = hostName;
	}

	public StringBuilder getMessage() {
		return message;
	}

	public void setMessage(StringBuilder message) {
		this.message = message;
	}

	public JTextArea getDisplay() {
		return displayText;
	}

}
