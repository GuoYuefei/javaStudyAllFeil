package xyz.ourword.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 游戏开发中经常要用到的工具函数放置类
 * @author fly
 *
 */
public class MyFrameUtil {
	/**
	 * 工具来构造函数私有化！！！
	 */
	private MyFrameUtil(){
	};
	/**
	 * 加载图片函数
	 * @param path 图片路径
	 * @return Image
	 * @author fly 
	 */
	public static Image getImage(String path){
		URL url = MyFrameUtil.class.getResource(path);
		BufferedImage image = null;

			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return image;
	}

}
