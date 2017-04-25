package cn.becomegood.Pattern.Factory;

public class WhiteHuman extends AbstractHuman {

	public WhiteHuman() {
		color = "白色";
	}
	@Override
	public void talk() {

		System.out.println(color+"人种会说话，他们大多使用单音节词");
	}

}
