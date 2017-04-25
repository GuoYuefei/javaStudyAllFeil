package cn.becomegood.Pattern.Singleton;

/*
 * 臣子类是一个场景类
 */
public class Minister {

	public static void main(String[] args) {
		for (int day = 0; day < 9; day++) {
			System.out.print("第"+(1+day)+"天:\t");
			Emperor emperor = Emperor.getInstance();	//单例模式，即使在循环中，得到的实例都是一样的
			emperor.say();
		}
	}
}
