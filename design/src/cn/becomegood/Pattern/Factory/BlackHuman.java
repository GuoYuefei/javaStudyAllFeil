package cn.becomegood.Pattern.Factory;

public class BlackHuman extends AbstractHuman{
	
	public BlackHuman() {
		color="黑色";
	}
	@Override
	public void talk() {
		// TODO Auto-generated method stub
		System.out.println(color+"人种会说话！！");
	}
	
}
