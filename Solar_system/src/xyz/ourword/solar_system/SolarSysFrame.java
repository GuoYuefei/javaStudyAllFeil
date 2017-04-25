package xyz.ourword.solar_system;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;

import xyz.ourword.util.Constant;
import xyz.ourword.util.MyFrame;
import xyz.ourword.util.MyFrameUtil;

@SuppressWarnings("serial")
public class SolarSysFrame extends MyFrame{
	
	
	@Override
	public void launchFrame() {
		super.launchFrame();
		setIconImage(MyFrameUtil.getImage("/images/tubiao.ico"));
		setResizable(false);
	}




	//背景
	Image bg = MyFrameUtil.getImage("/images/bg.jpg");
	//恒星
	Star sun = new Star("/images/sun.jpg",(Constant.WINDOWS_WIDTH-Constant.SUN_WIDTH)/2,(Constant.WINDOWS_HEIGHT-Constant.SUN_HRIGHT)/2,Constant.SUN_WIDTH,Constant.SUN_HRIGHT);
	//行星
	planet mercury = new planet("/images/Mercury.jpg",8,8,100,35,692.44*Constant.NEPTUNE_SPEED,sun);
	planet venus = new planet("/images/Venus.jpg",13,13,135,55,267.97*Constant.NEPTUNE_SPEED,sun);
	planet earth = new planet("/images/earth.jpg",14,14,165,80,164.8*Constant.NEPTUNE_SPEED,sun);
	planet mars = new planet("/images/Mars.jpg",10,10,190,105,87.66*Constant.NEPTUNE_SPEED,sun);
	planet jupiter = new planet("/images/Jupiter.jpg",22,22,240,135,13.9*Constant.NEPTUNE_SPEED,sun);
	planet saturn = new planet("/images/Saturn.jpg",20,20,280,160,5.59*Constant.NEPTUNE_SPEED,sun);
	planet uranus = new planet("/images/Uranus.jpg",17,17,320,210,1.96*Constant.NEPTUNE_SPEED,sun);
	planet neptune = new planet("/images/Neptune.jpg",16,16,375,260,Constant.NEPTUNE_SPEED,sun);
	//卫星
	Moon moon = new Moon("/images/Moon.png",7,7,14,13,365*164.8*Constant.NEPTUNE_SPEED,earth);
	@Override
	public void paint(Graphics g) {
		g.drawImage(bg,0,0,null);
		
		sun.plaint(g);
		
		mercury.plaint(g);
		venus.plaint(g);
		earth.plaint(g);
		mars.plaint(g);
		jupiter.plaint(g);
		saturn.plaint(g);
		uranus.plaint(g);
		neptune.plaint(g);
		
		moon.plaint(g);
	}
	
	/**
	 * 无参构造，完全继承自MyFrame类
	 * @throws HeadlessException
	 */
	public SolarSysFrame() throws HeadlessException {
		super();
	}
	
	/**
	 * 有参构造，完全继承自Frame类
	 * @param title 标题
	 * @throws HeadlessException
	 */
	public SolarSysFrame(String title) throws HeadlessException {
		super(title);
	}

	
	
	
	public static void main(String[] args) {
		SolarSysFrame solarsystem = new SolarSysFrame("太阳系行星模拟运行图");
		solarsystem.launchFrame();
	}

}
