package xyz.ourword.util;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * 窗口 自己写的封装类
 * @version 1.0
 * @author fly
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends Frame{
	/**
	 * 加载窗口，设置窗口的大小。。。。
	 * 对于常量都放在Constant类中，方便管理。以后可以加配置文件
	 * @param WINDOWS_WIDHT 窗口宽大小
	 * @param WINDOWS_HEIGHT 窗口高大小
	 */
	public void launchFrame() {
		setSize(Constant.WINDOWS_WIDTH,Constant.WINDOWS_HEIGHT);
		setLocation(350,50);
		setVisible(true);							//设置可见，默认false;
		new PaintThread().start();
//		实现“关闭窗口”按钮功能
		addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		}
	public void paint(Graphics g){
	}
	
	/**
	 * 重画界面  线程
	 * @author fly
	 *
	 */
	class PaintThread extends Thread{
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 无参构造，完全继承自Frame类
	 * @throws HeadlessException
	 */
	public MyFrame() throws HeadlessException {
		super();
	}
	/**
	 * 有参构造，完全继承自Frame类
	 * @param title 标题
	 * @throws HeadlessException
	 */
	public MyFrame(String title) throws HeadlessException {
		super(title);
	}
	
}

