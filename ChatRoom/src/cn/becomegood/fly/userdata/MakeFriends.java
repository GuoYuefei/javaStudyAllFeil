package cn.becomegood.fly.userdata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.becomegood.fly.chatroom.util.DataUtil;

public class MakeFriends extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostID;
	private final JLabel searchLabel = new JLabel("查询id：");
	private final JLabel resultLabel = new JLabel("结果：");
	private final JTextField searchField = new JTextField();
	private final JTextArea resultArea = new JTextArea();
	private final JScrollPane resultPane = new JScrollPane(resultArea);
	private final JButton searchButton = new JButton("查询");
	private final JButton addButton = new JButton("添加好友");
	
	public static void main(String[] args) {
		MakeFriends mkFriends = new MakeFriends("112233");
	}
	
	private void init() {
		this.setTitle("寻找好友");
		this.setLayout(null);
		this.setVisible(true);
		//点xx后彻底释放资源
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				dispose();
			}
		});
		
		resultPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		resultArea.setEditable(false);
		resultArea.setLineWrap(true);
		
		this.add(searchLabel);
		this.add(resultLabel);
		this.add(searchField);
		this.add(resultPane);
		this.add(addButton);
		this.add(searchButton);
		
		this.setBounds(300,300,200,300);
		searchLabel.setBounds(5,5,60,30);
		searchField.setBounds(65,5,135,30);
		resultLabel.setBounds(5,110,60,30);
		resultPane.setBounds(65,40,135,180);
		addButton.setBounds(5,225,90,50);
		searchButton.setBounds(105,225,90,50);
		
		buttonlistener();
	}
		
	private void buttonlistener() {
		//加好友
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean flag = false;					//是否成功运行sql语句的标志
				boolean isExist = false;		//是否存在好友的标志
				DataUtil dataUtil = new DataUtil();
				
				//这一部验证是否存在此人和好友关系
				flag = dataUtil.dataExe(DataUtil.QUERY,"select FriendID from friendship " +
						"where HostID = ?",hostID);
				if (flag) {
					ResultSet resultSet = dataUtil.getResultSet();
					try {
						//遍历所有结果，如果存在好友关系了将提示好友已存在
						while (resultSet.next()) {
							if (searchField.getText().equals(resultSet.getString(1))) {
								isExist = true;
							}
						}
					} catch (SQLException e1) {
//						e1.printStackTrace();
						isExist = false;
					}
				}
				//如果存在好友关系就提示并且用return退出函数
				if (isExist) {
					JOptionPane.showMessageDialog(null,searchField.getText()+"已存在你的好友列表中",
							"提示",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//进行好友数据的插入
				flag = dataUtil.dataExe(DataUtil.UPDATE,
						"insert into friendship(HostID,FriendID)" +
						" values(?,?)",hostID,searchField.getText());
				if(flag){
					JOptionPane.showMessageDialog(null,"加"+searchField.getText()+"为好友成功！"
							,"成功",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null,"查无此人","错误",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		//添加查询内容
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String isOnline = null;
				DataUtil dataUtil = new DataUtil();
				dataUtil.dataExe(DataUtil.QUERY,"select user_id,user_name,user_age,isOnline" +
						" from users where user_id = ?",searchField.getText());
				ResultSet resultSet = dataUtil.getResultSet();
				try {
					resultSet.next();
					//该if语句将数据库中isOnline的tinyint类型通过分析转换成用户可读的string
					if (resultSet.getInt(4)==0) {
						isOnline = "离线";
					} else {
						isOnline = "在线";
					}
					resultArea.setText("id: "+resultSet.getString(1)+"\r\n" +
							"网名： "+resultSet.getString(2)+"\r\n" +
							"年龄： "+resultSet.getString(3)+"\r\n" +
							"上限与否： "+isOnline+"\r\n");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"查无此人","错误",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	
	public MakeFriends(String hostID) {
		this.hostID = hostID;
		init();
	}
}
