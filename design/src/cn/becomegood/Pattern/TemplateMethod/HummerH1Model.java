package cn.becomegood.Pattern.TemplateMethod;
//这个类的鸣笛用用户决定
public class HummerH1Model extends HummerModel{
	private boolean alarmFlag = true;	//要响喇叭
	@Override
	protected void start() {
		System.out.println("悍马H1开始启动啦！！！");
	}

	@Override
	protected void stop() {
		System.out.println("悍马H1已经停车啦。。。");
	}

	@Override
	protected void alarm() {
		System.out.println("悍马H1正在鸣笛。。。嘟嘟嘟嘟。。。");
	}

	@Override
	protected void engineBoom() {
		System.out.println("悍马H1的引擎开始响啦#####");
	}
	
	

	@Override
	protected boolean isAlarm() {
		return alarmFlag;
	}
	
	//要不要喇叭响由用户决定
	public void setAlarm(boolean isAlarm) {
		this.alarmFlag = isAlarm;
	}
	
}
