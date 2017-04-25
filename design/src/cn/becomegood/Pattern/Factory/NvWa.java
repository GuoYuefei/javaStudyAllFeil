package cn.becomegood.Pattern.Factory;

/*
 * 女娲类，用于布置场景
 */
public class NvWa {
	public static void main(String[] args) {
		//声明阴阳八卦炉八卦炉
		Factory yinYangLuFactory = new HumanFactory();
		System.out.println("--第一批造出的是白人--");
		Human whiteHuman = yinYangLuFactory.createHuman(WhiteHuman.class);
		whiteHuman.getColor();
		whiteHuman.talk();
		System.out.println("--第二批造出的是黑人--");
		Human blackHuman = yinYangLuFactory.createHuman(BlackHuman.class);
		blackHuman.getColor();
		blackHuman.talk();
		System.out.println("--第三批造出的是黄人--");
		Human yellowHuman = yinYangLuFactory.createHuman(YellowHuman.class);
		yellowHuman.getColor();
		yellowHuman.talk();
	}
}
