package cn.becomegood.fly.userdata;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cn.becomegood.fly.chatroom.ChatWindow;
import cn.becomegood.fly.chatroom.PrivateChatWindow;
import cn.becomegood.fly.chatroom.util.ConstantGUI;
import cn.becomegood.fly.chatroom.util.DataUtil;

public class OptUI extends JFrame {
	private String hostID;
	private String hostName;
	private List<String> friends = new ArrayList<String>();	//统一管理好友id
	ImageIcon mkFri = new ImageIcon("src/images/mkFriends.jpg");
	private final JButton mkFriends = new JButton(mkFri);		//加友按钮
	private final JButton allChat = new JButton("群聊");			//群聊按钮
//	private final JButton Refresh = new JButton("刷新");
	
	public static void main(String[] args) {
		OptUI optUI = new OptUI("gyf");
		optUI.setVisible(true);
	}
	
	private void init() {
		int x = 5;
		int y = 80;
		int i=1;
		DP(hostID);
		this.setTitle("好友主页");
		this.setLayout(null);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DataUtil dataUtil = new DataUtil();
				dataUtil.dataExe(DataUtil.UPDATE,"update users " +
				"set isOnline = 0 where user_id = ?",hostID);			//将在线与否的标志在数据库中置为0，表示离线
				System.exit(0);
			}
		});
		
		//产生组件和添加
		this.add(mkFriends);
//		this.add(Refresh);
		JLabel hostNameLabel = new JLabel(hostName);
		hostNameLabel.setFont(new Font("宋体",1,30));
		this.add(hostNameLabel);
		this.add(allChat);
		//将朋友按钮有序的排列，3列多行
		for (final String friend : friends) {
			JButton one = new JButton(friend);
			this.add(one);	//可以在匿名类后面.addActionListener()
			one.setBounds(x,y,100,30);
			x+=65;
			if (i++%3 == 0) {
				x = 5;
				y+=35;
			}
			one.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					PrivateChatWindow privateChatWindow = new PrivateChatWindow(hostName,hostID,friend);
					privateChatWindow.init();
					
				}
			});
			
		}

	
		
		
		//组件的位置和大小设定
		this.setBounds((int)ConstantGUI.SCREENSIZE.getWidth()/3,0,
				ConstantGUI.OPTUI_WIDTH,ConstantGUI.OPTUI_HEIGHT);
		mkFriends.setBounds(10,20,20,20);
		hostNameLabel.setBounds(85,10,100,30);
		allChat.setBounds(5,50,230,20);
//		Refresh.setBounds(180,20,50,20);
		
		
		
		//以下为普通按钮的事件监听
		//加友按钮
		mkFriends.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MakeFriends mkFriends = new MakeFriends(hostID);
			}
		});
		//群聊按钮
		allChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatWindow chatWindow = new ChatWindow(hostName);
				chatWindow.init();
			}
		});

		
		
	}
		
	
	
	
	//DP = data processing 译为数据处理
	/**
	 * 所有得到的数据将储存到类的实例变量中
	 * @param hostID
	 */
	private void DP(String hostID) {
		DataUtil dataUtil = new DataUtil();
		//将数据库中标记在线与否部分标记成在线，1表示在线
		dataUtil.dataExe(DataUtil.UPDATE,"update users " +
				"set isOnline = 1 where user_id = ?",hostID);
		//调出名字
		dataUtil.dataExe(DataUtil.QUERY,"select user_name from users" +
				" where user_id = ?",hostID);
		try {
			ResultSet resultSet = dataUtil.getResultSet();
			resultSet.next();
			hostName = resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataUtil.dataExe(DataUtil.QUERY,"select FriendID from friendship" +
				" where HostID = ?",hostID);
		try {
			ResultSet resultSet = dataUtil.getResultSet();
			while (resultSet.next()) {
				friends.add(resultSet.getString(1));		//放入集合，方便管理
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public OptUI(String hostID) {
		this.hostID = hostID;
		init();
	}
}
