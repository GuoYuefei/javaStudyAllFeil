package xyz.ourword.solar_system;

import java.awt.Graphics;
import java.awt.Image;

import xyz.ourword.util.MyFrameUtil;

/**
 * planet需要围绕中心转动
 * @author fly
 * @version 1.0
 */
public class planet extends Star {
	//行星对于star还具有的属性：轨道的长轴，短轴，速度，围绕中心
	protected double long_axis;   //长轴
	protected double short_axis;  //短轴
	protected double speed;       //轨道速度
	protected double degree;      //角度变量 初始0就可以
	Star centre;
	
	@Override
	public void plaint(Graphics g) {
		double cen_x = (centre.x+centre.width/2);
		double cen_y = (centre.y+centre.height/2);
		
		g.drawImage(star_image,(int)(x-width/2),(int)(y-this.height/2),(int)width,(int)height,null);
		g.drawOval((int)(cen_x-long_axis),(int)(cen_y-short_axis),(int)(2*long_axis),(int)(2*short_axis));
		//运动轨迹方程 
		x =cen_x+long_axis*Math.cos(degree);
		y =cen_y+short_axis*Math.sin(degree);
		degree += speed;
	}

	/**
	 * 
	 * @param img 图片对象
	 * 图片大小默认
	 * @param long_axis 轨道长轴
	 * @param short_axis 轨道短轴
	 * @param speed 运行速度
	 * @param centre 围绕中心，star对象
	 */
	public planet(Image img,double long_axis, double short_axis, double speed, Star centre){
		super(img,centre.x+centre.width/2+long_axis,centre.y+centre.height/2);
		this.long_axis = long_axis;
		this.short_axis = short_axis;
		this.speed = speed;
		this.centre = centre;
	}
	/**
	 * @param path 图片的路径
	 * 图片大小默认
	 * @param long_axis 轨道长轴
	 * @param short_axis 轨道短轴
	 * @param speed 运行速度
	 * @param centre 围绕中心，star对象
	 */
	public planet(String path,double long_axis, double short_axis, double speed, Star centre){
		this(MyFrameUtil.getImage(path),long_axis,short_axis,speed,centre);
	}
	/**
	 * 创建planet对象的构造函数
	 * @param img  图片对象
	 * @param width 图片宽
	 * @param height 图片高
	 * @param long_axis  轨道长轴
	 * @param short_axis  轨道短轴
	 * @param speed 轨道的速度
	 * @param centre 轨道中心
	 */
	public planet(Image img,double width,double height,double long_axis, double short_axis, double speed, Star centre) {
		
		this(img,long_axis,short_axis,speed,centre);
		this.width = width;
		this.height = height;
	}
	/**
	 * 创建planet对象的构造函数
	 * @param path 图片路径
	 * @param width 图片宽
	 * @param height 图片高
	 * @param long_axis  轨道长轴
	 * @param short_axis 轨道短轴
	 * @param speed  运行速度
	 * @param centre   围绕中心
	 */
	public planet(String path,double width,double height,double long_axis, double short_axis, double speed, Star centre) {
		this(MyFrameUtil.getImage(path),width,height,long_axis,short_axis,speed,centre);
	}

	/**
	 * 默认调用父类默认构造方法
	 */
	public planet() {
		super();
	}

}
