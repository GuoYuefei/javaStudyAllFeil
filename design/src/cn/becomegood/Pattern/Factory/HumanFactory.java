package cn.becomegood.Pattern.Factory;
/*
 * 
 * 可以使用多个工厂类对不同的类进行分工创造
 */
public class HumanFactory implements Factory {

	//该方法可以用static修饰，之后放弃继承接口
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Human> T createHuman(Class<T> c) {
		Human human = null;
		try {
			//通过反射机制用默认构造函数创建实例
			human = (T)Class.forName(c.getName()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) human;
	}
	

}
