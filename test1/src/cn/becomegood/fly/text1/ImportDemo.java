package cn.becomegood.fly.text1;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
public final class ImportDemo extends Applet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Button redButton;
	public void init(){
		redButton = new Button("试着点一下哦");
		redButton.setBackground(Color.blue);
		redButton.setForeground(Color.green);
		add(redButton);
		}
	public final void paint(Graphics g){
		g.drawString("我在玩awt！",90,45);
	}
	
}
