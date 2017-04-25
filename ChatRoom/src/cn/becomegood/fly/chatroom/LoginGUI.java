package cn.becomegood.fly.chatroom;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cn.becomegood.fly.chatroom.util.ConstantGUI;


public class LoginGUI extends JFrame {
	/**
	 * 
	 */
	private String userName;
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		LoginGUI login = new LoginGUI();
		login.init();
	}
	
	
	
	public void init(){
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("登录");
		JLabel nameL = new JLabel("Your Name:");
		final JTextField nameT = new JTextField();
		JButton LoginB = new JButton("Enter");
		nameL.setBounds(5,5,70,40);
		nameT.setBounds(80,10,110,32);
		LoginB.setBounds(100,45,85,30);
		this.add(nameL);
		this.add(nameT);
		this.add(LoginB);
		setVisible(true);
//		this.pack();
		this.setBounds((int)(ConstantGUI.SCREENSIZE.width-ConstantGUI.LOGIN_WIDTH)/2,
				(int)(ConstantGUI.SCREENSIZE.height-ConstantGUI.LOGIN_HEIGHT)/2,
				ConstantGUI.LOGIN_WIDTH,ConstantGUI.LOGIN_HEIGHT);
		//为LoginB按钮添加监听事件
		LoginB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userName = nameT.getText();
				setVisible(false);
			}
		});
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event instanceof KeyEvent && ((KeyEvent) event).getKeyCode() == 10) {
					userName = nameT.getText();
					setVisible(false);
				}
			}
		},12);
	}
	/**
	 * 对外给出得到userName的方法
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	

}
