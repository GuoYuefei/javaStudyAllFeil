package fly.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class FlyPlugin extends JavaPlugin
{
	public void onEnable(){
		getLogger().info("我的0.6版本的插件已被调用");
		//插件被调用时服务器端能够用提示！
		getServer().getPluginManager().registerEvents(new FLyEventListener(),this);	
	}

}
