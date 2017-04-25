package cn.becomegood.fly.attact.Simple;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AttactSimpleMain extends JFrame{
	public AttactSimpleMain(String title) {
		super(title);
		init();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static JButton attactButton = new JButton("Attact!");
	public static AttactSimpleMain oneJFrame;
	/**
	 * 
	 */
	public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JFrame.setDefaultLookAndFeelDecorated(true);
					oneJFrame = new AttactSimpleMain("Attact Server");
					oneJFrame.setVisible(true);
				}
			});
	}
	
	
	/*
	 * 构建窗体
	 */
	void init() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final JTextField iPField = new JTextField();
		final JTextField protField = new JTextField();
		final JTextField messageField = new JTextField();
		final JTextField timesField = new JTextField();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(5,2));
		this.add(new JLabel("IP:"));
		this.add(iPField);
		this.add(new JLabel("Prot:"));
		this.add(protField);
		this.add(new JLabel("Message:"));
		this.add(messageField);
		this.add(new JLabel("Times:"));
		this.add(timesField);
		attactButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
						String IP;
						int prot;
						String message;
						int times;
						//读取用户输入的四个值
						IP = iPField.getText();
						prot = Integer.parseInt(protField.getText());
						message = messageField.getText();
						times = Integer.parseInt(timesField.getText());
						NewSimpleA newSimpleA = new NewSimpleA(IP,prot,message,times);
						Thread barThread = new Thread(newSimpleA);         //NewSimpleA继承了Runable的接口，传参给Thread
						barThread.start();
			}
		});
		this.add(attactButton);
		this.pack();
		this.setBounds((int)dimension.getWidth()/2-this.getWidth(),(int)dimension.getHeight()/2-this.getHeight(),
				this.getWidth(),this.getHeight());
		
		
	}

}
