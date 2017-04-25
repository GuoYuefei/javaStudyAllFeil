package cn.becomegood.Pattern.TemplateMethod;

public class HummerH2Model extends HummerModel {

	@Override
	protected void start() {
		System.out.println("悍马H2开始启动啦@@@");
	}

	@Override
	protected void stop() {
		System.out.println("悍马H2已经停车啦。。。");
		
	}

	@Override
	protected void alarm() {

		System.out.println("悍马H2开始鸣笛啦！！！！");
	}

	@Override
	protected void engineBoom() {

		System.out.println("悍马H2的引擎开始发生啦###");
	}

	@Override
	protected boolean isAlarm() {
		//默认喇叭一直保持不响，并且不能改变这个状态
		return false;
	}

	
}
