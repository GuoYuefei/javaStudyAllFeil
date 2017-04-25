package cn.becomegood.Pattern.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 这是动态代理接口的使用，这个使用几乎是固定的，可以记住。当然将来一定有自己的写法
 * @author fly
 *
 */
public class GamePlayerIH implements InvocationHandler {
	
	Class cls = null;
	Object obj = null;
	
	public GamePlayerIH(Object obj) {
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = method.invoke(this.obj,args);
		//
		if (method.getName().equalsIgnoreCase("login")) {
			System.out.println(new Date()+"\t你的帐号登录了游戏！");
		}
		return result;
	}

}
