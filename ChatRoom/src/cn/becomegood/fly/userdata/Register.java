package cn.becomegood.fly.userdata;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.becomegood.fly.chatroom.util.ConstantGUI;
import cn.becomegood.fly.chatroom.util.DataUtil;

public class Register extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel label1 = new JLabel("登录帐号：");
	private final JLabel label2 = new JLabel("密码：");
	private final JLabel label3 = new JLabel("再次确认：");
	private final JLabel label4 = new JLabel("网名：");
	private final JLabel label5 = new JLabel("真实姓名");
	private final JLabel label6 = new JLabel("你的手机号：");
	private final JLabel label7 = new JLabel("你的年龄：");
	private final JTextField idField = new JTextField();
	private final JPasswordField passwdField = new JPasswordField();
	private final JPasswordField againField = new JPasswordField();
	private final JTextField chatNameField = new JTextField();
	private final JTextField trueNameField = new JTextField();
	private final JTextField PhoneField = new JTextField();
	private final JTextField ageField = new JTextField();
	private final JButton OUT_Button = new JButton("OUT");
	private final JButton OK_Button = new JButton("Register");
	
	public static void main(String[] args) {
		Register register = new Register();
		register.setVisible(true);
		
	}
	
	
	public Register() {
		super();
		init();
	}


	//构造函数，显示对话框
	public Register(Frame owner, boolean modal) {
		super(owner, modal);
		init();
	}
	private void init(){
		this.setTitle("欢迎注册！");
		this.setLayout(new GridLayout(8,2));
		
		this.add(label1);
		this.add(idField);
		this.add(label2);
		this.add(passwdField);
		this.add(label3);
		this.add(againField);
		this.add(label4);
		this.add(chatNameField);
		this.add(label5);
		this.add(trueNameField);
		this.add(label6);
		this.add(PhoneField);
		this.add(label7);
		this.add(ageField);
		this.add(OUT_Button);
		this.add(OK_Button);
		this.setBounds((int)ConstantGUI.SCREENSIZE.getWidth()/4,(int)ConstantGUI.SCREENSIZE.getHeight()/4,
				ConstantGUI.REGISTER_WIDTH,ConstantGUI.REGISTER_HEIGHT);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				dispose();
			}
			
		});
		
		//事件监听
		OK_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isSuccess;
				String id = idField.getText();
				String pw = new String(passwdField.getPassword());
				String again = new String(againField.getPassword());
				String name = chatNameField.getText();
				String trueName = trueNameField.getText();
				String age = ageField.getText();
				String phone = PhoneField.getText();
				if (pw.length()<6) {
					JOptionPane.showMessageDialog(null,"密码长度过短，请重新输入","输入有误",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (!pw.equals(again)) {
					JOptionPane.showMessageDialog(null,"两次密码不相同","输入有误",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String sql = "insert into users(user_id,user_pw,user_name,user_true,user_age,user_phone)" +
						"values(?,?,?,?,?,?)";
				DataUtil dataUtil = new DataUtil();
				//mysql会自动分析age成tinyint
				isSuccess = dataUtil.dataExe(DataUtil.UPDATE,sql,id,pw,name,trueName,age,phone);		
				if (isSuccess) {
					JOptionPane.showMessageDialog(null,"Register Successful!","注册成功",
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();			//系统回收所有UI组件
				} else {
					JOptionPane.showMessageDialog(null,"注册不成功，请检查信息的正确与否！或者更换注册帐号信息","A Wrong",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		OUT_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
	}
	
	
}
