package cn.becomegood.Pattern.Factory;

public abstract class AbstractHuman implements Human {
	protected String color="未确立";			//定义皮肤颜色，不同人种可以根据不同该属性区分
	@Override
	public void getColor() {
		System.out.println(color+"人种的皮肤是"+color+"的");
	}

	@Override
	public abstract void talk();

}
