package xyz.ourword.solar_system;

import java.awt.Graphics;
import java.awt.Image;

import xyz.ourword.util.MyFrameUtil;



public class Star {
	protected double x,y,width,height;
	protected Image star_image;
	public void plaint(Graphics g){
		g.drawImage(star_image,(int)x,(int)y,(int)width,(int)height,null);
		
	}
	

	/**
	 * 默认构造
	 */
	public Star() {
	}
	/**
	 * 参数为图片对象，图片出现坐标x,y的构造函数
	 * 图片大小缺省
	 * @param star_image
	 * @param x
	 * @param y
	 */
	public Star(Image star_image,double x,double y){
		this.star_image = star_image;
		this.x  = x;
		this.y = y;
		width = star_image.getWidth(null);
		height = star_image.getHeight(null);
	}
	/**
	 * 参数为图片对象，图片出现坐标，图片大小
	 * @param star_image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Star(Image star_image,double x,double y,double width,double height){
		this(star_image,x,y);
		this.width = width;
		this.height = height;
	}
	/**
	 * 参数为图片路径，图片出现坐标x,y的构造函数
	 * 图片大小缺省
	 * @param path 图片路径
	 * @param x 图片出现位置x坐标
	 * @param y 图片出现位置y坐标
	 */
	public Star(String path,double x,double y){
		this(MyFrameUtil.getImage(path),x,y);
	}
	/**
	 * 参数为图片路径，图片出现坐标，图片大小
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Star(String path,double x,double y,double width,double height){
		this(MyFrameUtil.getImage(path),x,y,width,height);
	}
	
	
	
	//变量的get，set函数
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public Image getStar_image() {
		return star_image;
	}

}
