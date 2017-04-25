package cn.becomegood.Pattern.Singleton;

import java.util.ArrayList;
import java.util.Random;

/**
 * 单例模式的应用，一般来说单例模式是没有接口和抽象类的，所以难以扩展
 * 这个皇帝的类限制了只能产生两个皇帝
 * @author fly
 *
 */
public class Emperor {
	private static int maxNumOfEmperor = 2;
	private static ArrayList<String> nameList = new ArrayList<String>(maxNumOfEmperor);
	private static ArrayList<Emperor> emperors = new ArrayList<Emperor>(maxNumOfEmperor);
	private static int countNumOfEmperor = 0;
//	private static final Emperor EMPEROR = new Emperor();
	private Emperor(){
		//私有构造函数，目的是为了世俗约束，不能产生皇帝的量上限
	}
	/*
	 * 传入皇帝的名字产生一个皇帝
	 */
	private Emperor(String name){
		nameList.add(name);
	}
	static{
		for (int i = 0; i < maxNumOfEmperor; i++) {
			emperors.add(new Emperor("皇"+(i+1)+"帝"));
		}
	}
	public static Emperor getInstance(){
		//随机产生一个精神领袖
		Random random = new Random();
		countNumOfEmperor = random.nextInt(maxNumOfEmperor);
		return emperors.get(countNumOfEmperor);
	}
	
	public void say(){
		System.out.println("我就是"+nameList.get(countNumOfEmperor)+".....");
	}
}
