package cn.becomegood.fly.text1;

public class InterFa {
	Animal animal;
	public void catSay(String string) {
		animal=new Cat();
		animal.say(string);
	}
	public void dogSay(String string) {
		animal=new Dog();
		animal.say(string);
	}
}
abstract class Animal{
	abstract void say(String string);
}
class Cat extends Animal{
	public void say(String string){
		System.out.println("miaomiao");
		System.out.println(string);
	}
}
class Dog extends Animal{
	public void say(String string){
		System.out.println("wangwang");
		System.out.println(string);
	}
}