package cn.becomegood.Pattern.TemplateMethod;

/*
 * 创建模板，其他方法为受保护方法。向外只提供一个共有方法。共有方法中已经把汽车的运行的经过排列好！！
 * 当然这个是抽象类，所以方法体不用写。		是我脑子抽风以为是实现类就写好了方法体
 * 已经修改方法
 */
public abstract class HummerModel {
	//是汽车都可以启动
	protected abstract void start();
	//是汽车都可以停车
	protected abstract void stop();
	//是汽车都可以鸣笛
	protected abstract void alarm();
	//是汽车引擎都可以响
	protected abstract void engineBoom();
	
	//对外暴露方法
	public void run(){
		this.start();
		this.engineBoom();
		//要让他叫时才叫
		if (this.isAlarm()) {
			this.alarm();
		}
		this.stop();
	}
	//钩子方法，默认喇叭会响
	protected boolean isAlarm(){
		return true;
	}
	
	//空的方法，子类无需另加方法就可以实现全部功能。		这样可以直接用父类类型来替换所有子类类型
	public void setAlarm(boolean isAlarm){
		
	}
}
