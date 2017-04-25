package cn.becomegood.Pattern.TemplateMethod;

/*
 * 场景类，用于测试
 */
public class Client {
	
	public static void main(String[] args) {
		
		HummerModel H1 = new HummerH1Model();
		HummerModel H2 = new HummerH2Model();
		
		//1、使用这个方法必须是使用子类作为类型，而不是父类（父类中没有这个方法）
		//2、经过修改后（在父类中添加空的该中方法），可以直接使用父类类型调用该方法
		H1.setAlarm(false);
		H1.run();
		System.out.println();
		H2.run();
	}
}
