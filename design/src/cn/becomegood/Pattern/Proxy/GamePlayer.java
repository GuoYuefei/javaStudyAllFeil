package cn.becomegood.Pattern.Proxy;

import java.util.Date;

public class GamePlayer implements IGamePlayer {
	
	private String name = "";
	public GamePlayer(String name) {
		this.name = name;
	}

	@Override
	public void login(String user, String passwd) {
		System.out.println(new Date()+"\t登录名为"+user+"的用户"+this.name+"登录成功！");
	}

	@Override
	public void killBoss() {
		System.out.println(new Date()+"\t"+this.name+"击杀了BOSS");
	}

	@Override
	public void upgrade() {
		System.out.println(new Date()+"\t"+this.name+"升了一级！");
	}

}
