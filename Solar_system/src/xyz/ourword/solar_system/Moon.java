package xyz.ourword.solar_system;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Moon类完全继承planet
 * 只是重写了plaint(g)减少了一个轨道路径
 * 这样能更加美观
 * @author fly
 *
 */
public class Moon extends planet{

	@Override
	public void plaint(Graphics g) {
		double cen_x = (centre.x+centre.width/2);
		double cen_y = (centre.y+centre.height/2);
		
		g.drawImage(star_image,(int)(x-width/2),(int)(y-this.height/2),(int)width,(int)height,null);
		//运动轨迹方程 
		x =cen_x+long_axis*Math.cos(degree);
		y =cen_y+short_axis*Math.sin(degree);
		degree += speed;
	}

	/**
	 * 默认构造方式 调用父类的默认构造函数
	 */
	public Moon() {
		super();
	}
	/**
	 * 
	 * @param img 图片对象
	 * @param width 图片宽
	 * @param height 图片高
	 * @param long_axis 轨道长轴
	 * @param short_axis 轨道短轴
	 * @param speed 运行速度
	 * @param centre 围绕中心 类型Star类
	 */
	public Moon(Image img, double width, double height, double long_axis,
			double short_axis, double speed, Star centre) {
		super(img, width, height, long_axis, short_axis, speed, centre);
	}
	/**
	 * 
	 * @param img 图片对象
	 * 图片大小默认
	 * @param long_axis 轨道长轴
	 * @param short_axis 轨道短轴
	 * @param speed 运行速度
	 * @param centre 围绕中心 类型Star类
	 */
	public Moon(Image img, double long_axis, double short_axis, double speed,
			Star centre) {
		super(img, long_axis, short_axis, speed, centre);
	}
	/**
	 * 
	 * @param path 图片路径
	 * @param width 图片宽
	 * @param height 图片高
	 * @param long_axis 轨道长轴
	 * @param short_axis 轨道短轴
	 * @param speed 运行速度
	 * @param centre 围绕中心 类型Star类
	 */
	public Moon(String path, double width, double height, double long_axis,
			double short_axis, double speed, Star centre) {
		super(path, width, height, long_axis, short_axis, speed, centre);
	}
	/**
	 * 
	 * @param path 图片路径
	 * 图片大小默认
	 * @param long_axis 轨道长轴 
	 * @param short_axis 轨道短轴
	 * @param speed 运行速度
	 * @param centre 围绕中心 类型Star类
	 */
	public Moon(String path, double long_axis, double short_axis, double speed,
			Star centre) {
		super(path, long_axis, short_axis, speed, centre);
	}

}
