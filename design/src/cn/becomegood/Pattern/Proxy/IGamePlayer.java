package cn.becomegood.Pattern.Proxy;

public interface IGamePlayer {
	//接口的方法默认是公共抽象的（public abstract）
	//玩家要玩总是要登录的把
	void login(String user,String passwd);
	//玩家都是可以去击杀boss获得经验的把
	void killBoss();
	//玩家是可以的升级的，管它用什么方法升级
	void upgrade();
}
