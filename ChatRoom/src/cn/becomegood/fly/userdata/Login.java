package cn.becomegood.fly.userdata;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.becomegood.fly.chatroom.util.ConstantGUI;
import cn.becomegood.fly.chatroom.util.DataUtil;

public class Login extends JDialog{
	private String id;
	JLabel jLabel1 = new JLabel("登录帐号：");
	JLabel jLabel2 = new JLabel("密码：");
	JTextField idField = new JTextField();
	JPasswordField pwField = new JPasswordField();
	JButton registerJButton = new JButton("Register");
	JButton loginJButton = new JButton("Login");
	Register register;
	public static void main(String[] args) {
		Login login = new Login();
	}
	public Login() {
		super();
		init();
	}
	
	
	private void init() {
		this.setTitle("登录");
		this.setLayout(new GridLayout(3,2));
		
		this.add(jLabel1);
		this.add(idField);
		this.add(jLabel2);
		this.add(pwField);
		this.add(registerJButton);
		this.add(loginJButton);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		//点xx退出窗口，并返回所有资源
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				dispose();
			}
			
		});
		this.setBounds((int)ConstantGUI.SCREENSIZE.getWidth()/3,(int)ConstantGUI.SCREENSIZE.getHeight()/3,
				ConstantGUI.IN_WIDTH,ConstantGUI.IN_HEIGHT);
		this.ButtonListener();
		
	}
	//将按钮监听事件写入这里
	private void ButtonListener() {
		registerJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				register = new Register();
				register.setVisible(true);
			}
		});
		
		loginJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				String id = idField.getText();
//				String pw = pwField.getPassword().toString(); 			用toString打印出来发现pw储存的是地址信息
//				String pw = String.valueOf(pwField.getPassword());		用string.valueof可以正确反映其值
				DataUtil dataUtil = new DataUtil();
				dataUtil.dataExe(DataUtil.QUERY,"select user_pw from users where user_id=? limit 0,1",
						idField.getText());
				try {
					ResultSet pwResultSet = dataUtil.getResultSet();
					pwResultSet.next();
					//判断密码正确与否
					if (pwResultSet.getString(1).equals(String.valueOf(pwField.getPassword()))) {
						id = idField.getText();
						OptUI optUI = new OptUI(id);
						optUI.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null,"密码不正确！","提示",JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"帐号不存在","提示",JOptionPane.WARNING_MESSAGE);
//					e1.printStackTrace();
				}
				
			}
		});
		
		
	}
	//向接下来的界面提供用户id，动态呈现内容
	public String getId() {
		return id;
	}
	
	
}
