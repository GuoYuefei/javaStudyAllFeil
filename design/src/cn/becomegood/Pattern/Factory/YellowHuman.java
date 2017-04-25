package cn.becomegood.Pattern.Factory;

public class YellowHuman extends AbstractHuman{
	
	//默认构造函数修改父类中继承过来的color属性
	public YellowHuman() {
		color="黄色";
	}
	//覆盖父类的抽象方法   其中还有一个方法在父类中已经实现
	@Override
	public void talk() {
		System.out.println(color+"人种会说话，说的大多是双音节词！");
	}
	
	
}
