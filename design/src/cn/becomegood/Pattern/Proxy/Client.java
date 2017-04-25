package cn.becomegood.Pattern.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Date;


/**
 * 这个场景类模板可以记住，暂时不需要太过理解
 * @author fly
 *
 */
public class Client {

	public static void main(String[] args) {
		//定义一个玩家
		IGamePlayer player = new GamePlayer("张三");
		//定义一个hander
		InvocationHandler handler = new GamePlayerIH(player);
		System.out.println("开始时间"+new Date());
		//产生被代理对象类的类加载器
		ClassLoader loader = player.getClass().getClassLoader();
		//产生一个类加载者
		IGamePlayer proxy = (IGamePlayer)Proxy.newProxyInstance(loader,
										new Class[]{IGamePlayer.class},handler);
		//代理上线了
		proxy.login("zhangsan","123456");
		//代理杀怪
		proxy.killBoss();
		//杀怪需要时间的
		for (int i = 0; i <1000000000; i++) {
			for (int j = 0; j < i; j++) {
				
			}
		}
		proxy.killBoss();
		//代理升级
		proxy.upgrade();
		//结束时间
		System.out.println("结束时间："+new Date());
	
	
	}
}
