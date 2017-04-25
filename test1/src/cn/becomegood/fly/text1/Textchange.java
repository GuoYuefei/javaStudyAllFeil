package cn.becomegood.fly.text1;




public class Textchange {
	public static void main(String[] args){
		char c1 = 'a';
		int a = (c1 + 2);
		char c2 = (char)a;
		System.out.println(a);
		System.out.println(c2);
		InterFa iFa = new InterFa();
		iFa.catSay("How do you do?");
		iFa.dogSay("你叫什么名字啊？");
		Cat cat=new Cat();
		greet(cat);
	}
	/**
	 * 抽象类和接口都可以作为形参
	 * 传入子类
	 * @param animal
	 */
	public static void greet(Animal animal){
		animal.say("");
	}

}
