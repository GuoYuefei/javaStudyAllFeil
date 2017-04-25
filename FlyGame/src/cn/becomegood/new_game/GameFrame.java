package cn.becomegood.new_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class GameFrame extends Frame {
	Image image= MyGameUtil.getImage("/images/faceyellow.png");
	/**
	 * 加载游戏窗口
	 */
	public void launchFrame(){
		setSize(350,300);
		setLocation(800,240);
		setVisible(true);
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
	}
	@Override
	public void paint(Graphics g) {
		g.drawLine(200,200,250,250);
		g.drawRect(50,100,50,50);
		g.drawOval(50,100,50,50);					//根据外切矩形画的！
		Color color = g.getColor();
		g.setColor(Color.blue);
		g.fillRect(100,150,60,60);
		Font font = g.getFont();
		g.setFont(new Font("大号的",1,80));
		g.drawString("大家好！",5,80);
		g.setColor(color);
		g.setFont(font);
		g.drawImage(image,60,110,null);
		
	}
	
	
	
	
	public static void main(String[] args) {
		GameFrame gf = new GameFrame();
		gf.launchFrame();
//		gf.removeAll();移除所有组件
//		gf.validate(); 每次移除或者添加组件时调用此方法，以保证容器里的组件能正确的表达出来
		
	}
}
