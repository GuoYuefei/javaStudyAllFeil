package cn.becomegood.new_game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 游戏开发中经常要用到的工具类
 * @author fly
 *
 */
public class MyGameUtil {
	/**
	 * 加载图片函数
	 * @param path 图片路径
	 * @return Image
	 * @author fly 
	 */
	public static Image getImage(String path){
		URL url = MyGameUtil.class.getResource(path);
		BufferedImage image = null;

			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return image;
	}
	
	
}
