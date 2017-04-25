package cn.becomegood.Pattern.Factory;

//定义工厂接口
public interface Factory {
	public <T extends Human> T createHuman(Class<T> c);
}
