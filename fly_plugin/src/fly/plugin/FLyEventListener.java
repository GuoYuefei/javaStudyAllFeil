package fly.plugin;



import java.util.Random;


import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


@SuppressWarnings("deprecation")
public final class FLyEventListener implements Listener {
    @EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage("Welcome "+event.getPlayer().getName());
		LoginGetWays.putMessage(      event,                        						//调用putmMessageba把所要提示的信息一次性发送给玩家
									  LoginGetWays.loginGetItem(Material.DIAMOND_AXE,1,event),             //斧子  只要在 putMessage这个函数中
									  LoginGetWays.loginGetItem(Material.DIAMOND_PICKAXE,1,event),         //镐子  调用loginGetItem可以把发回的
									  LoginGetWays.loginGetItem(Material.DIAMOND_SWORD,1,event),           //剑    Item统一交给put输出
									  LoginGetWays.loginGetItem(Material.DIAMOND_HOE,1,event),             //锄头
									  
									 /* LoginGetWays.loginGetItem(Material.DIAMOND_BOOTS,10,event),           //护靴
									  LoginGetWays.loginGetItem(Material.DIAMOND_CHESTPLATE,10,event),      //护胸
									  LoginGetWays.loginGetItem(Material.DIAMOND_HELMET,10,event),          //头盔
									  LoginGetWays.loginGetItem(Material.DIAMOND_LEGGINGS,10,event), */         //护腿
									  
									  LoginGetWays.loginGetItem(Material.TORCH,64,event),					//火把
									  LoginGetWays.loginGetItem(Material.IRON_BLOCK,64,event),				//铁块
									  LoginGetWays.loginGetItem(Material.STICK,1,event),         //一个树枝，summon函数中定义了它召唤生物的功能
									  LoginGetWays.loginGetItem(Material.COAL,64,event),          //煤
									  
									  LoginGetWays.loginGetItem(Material.REDSTONE,64,event)       //红石粉
									  
								);			 
	}
    //用树枝右键召唤牛牛
    @EventHandler
	public void summonliving(PlayerInteractEvent event) {	
		if(event.getMaterial()==Material.STICK&&event.getAction().equals(Action.RIGHT_CLICK_AIR)){
			Player player = event.getPlayer();
			Location location = player.getLocation();
			World world = player.getWorld();
			Wolf wolf = world.spawn(location,Wolf.class);
			wolf.setCustomName("小黑");
		}
		if (event.getMaterial()==Material.STICK && event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			Random random = new Random();
			Player player = event.getPlayer();
			Location location = player.getLocation();
			World world = player.getWorld();
			Sheep sheep = world.spawn(location,Sheep.class);
			sheep.setCustomName("小白");
			sheep.setColor(DyeColor.values()[random.nextInt(16)]);
		}
	}


	@EventHandler
	public void getAllThing(PlayerChatEvent event){
		CallGetThings cThings = new CallGetThings(event.getPlayer(),event.getPlayer().getInventory());
		cThings.callToThing(event);
	}
	//改变人物等级
	@EventHandler
	public void changeLevel(PlayerChatEvent event){
		Level level =new Level(event.getPlayer(),event.getPlayer().getLevel());
		//下面直至if几行都是分析玩家发出命令，对命令和参数进行分类
		String str = event.getMessage();
		String str1 = str.substring(0,str.indexOf(' '));
		String str2 = str.substring(str.indexOf(' ')+1,str.length());
		char[] ch = new char[str2.length()];
		str2.getChars(0,str2.length(),ch,0);
		boolean differnum = true;                                //判别玩家有无输入整数的开关
		for (int i = 0; i < ch.length; i++) {
			if('0'>ch[i]||ch[i]>'9'){
				differnum = false;
				break;
			}
		}
		if(differnum){
			int num = 0;                                              //字符转整数
			for(int i=ch.length-1,j=0;i>=0;i--,j++)
			{
				num += (ch[i]-'0')*Math.pow(10,j);
			}
			//if中通过Level的类中定义的函数进行等级变化操作
			if (str1.equals(":up")) {
				level.addLevel(num);
				level.player.sendMessage("你已上升"+num+"等级");
			} else if (str1.equals(":set")) {
				level.setLevel(num);
				level.player.sendMessage("你的等级已置于"+num+"等级");
			}else if (str1.equals(":dec")) {
				level.decLevel(num);
				level.player.sendMessage("你的等级已减少"+num+"等级");
			}
			
		}
		
	}

}


//此类主要用于onJion
class LoginGetWays{
	//判断背包和装备是否存在要赋予的东西，存在不给，不存在赋予。并通过返回值的后缀可以判定执行了if的哪条语句
	public static String loginGetItem(Material item,int num,PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		EntityEquipment equipment = player.getEquipment();
		ItemStack itemStack = new ItemStack(item,num);
		if (inventory.contains(item)||equipJudge(equipment.getHelmet(),itemStack)
									||equipJudge(equipment.getBoots(),itemStack)             //执行if{}的equipJudge判断装备中是否含
									||equipJudge(equipment.getLeggings(),itemStack)		     //有要添加的物品，有返回true
									||equipJudge(equipment.getChestplate(),itemStack)){           
			return item+", ";                                                    	         //后缀运用到下面函数判定
		}
		else {
			inventory.addItem(itemStack);
			return item+"  ";															//一个空格能判定执行的时else语句		
		}
																											
	}
	//将赋予的物品和没赋予的物品进行分类集中输出
	public static void putMessage(PlayerJoinEvent event,String... strs){
		StringBuffer strif = new StringBuffer("");
		StringBuffer strelse = new StringBuffer("");
		Player player = event.getPlayer();
		for(String str:strs){                                                    		//对strs含有的str一个个处理
				if(str.endsWith(", ")){
					strif.append(str);
				}
				else {
					strelse.append(str);													/*setStrelse(getStrelse() + str);*/
				}		
		}
		//判断，非空就发送消息给玩家
		if(strif.length() != 0){
		player.sendMessage("你的背包或装备中已有物品"+strif+"fly插件无法给你这些财富了");
		}
		if(strelse.length() != 0){
		player.sendMessage("fly插件你赋予你这些物品了："+strelse);
		}
}
	
	// 下面函数主要转换两个参数的类型，然后将第一个参数和第二个参数进行比较
	private static boolean equipJudge(ItemStack equip,ItemStack itemStack) { 
		String equipstr = ""+equip;
		String itemStr = ""+itemStack;
		//equipstr.subSequence(10,equipstr.length()-5);
		if (equipstr.equals(itemStr)) {
			return true;
			
		} else {
			return false;	
		}
	}	
	
}


//应用于getallthings的类
class CallGetThings{
	private Player player;
	private PlayerInventory inventory;
	@SuppressWarnings("deprecation")
	public void callToThing(PlayerChatEvent event){
		fastAdd(event,":diamond",Material.DIAMOND,64,"钻石");
		fastAdd(event,":wood",Material.WOOD,64,"木块");
		fastAdd(event,":eye of enden",Material.EYE_OF_ENDER,64,"末影之眼");
		fastAdd(event,":gunpowder",Material.EXPLOSIVE_MINECART,64,"火药");
		fastAdd(event,":flint",Material.FLINT,64,"燧石");
		fastAdd(event,":glowstone",Material.GLOWSTONE,1,"萤石");
		fastAdd(event,":sugar cane",Material.SUGAR_CANE,64,"甘蔗");
		fastAdd(event,":obsidian",Material.OBSIDIAN,64,"黑耀石");
		fastAdd(event,":gold block",Material.GOLD_BLOCK,64,"金块");
		fastAdd(event,":stone",Material.STONE,64,"石头");
		fastAdd(event,":sand",Material.SAND,64,"沙子");
		fastAdd(event,":blaze",Material.BLAZE_ROD,64,"烈焰棒");
		fastAdd(event,":quartz",Material.QUARTZ,64,"石英");
		fastAdd(event,":soul sand",Material.SOUL_SAND,1,"灵魂沙");

		//.........
	}
//该类的构造函数
	public CallGetThings(Player player,PlayerInventory inventory) {
		this.player = player;
		this.inventory = inventory;
	}
	/**
	 * 方便添加物品时增加代码。格式：event，玩家输入信息，添加物品，添加物品数目，提醒玩家是否添加了该物品
	 * @param event
	 * @param in
	 * @param goods
	 * @param num
	 * @param out
	 */
	@SuppressWarnings("deprecation")
	private void fastAdd (PlayerChatEvent event,String in,Material goods,int num,String out) {
		if (event.getMessage().equals(in)) {
			ItemStack itemStack = new ItemStack(goods,num);
			inventory.addItem(itemStack);
			player.sendMessage("你已获得"+num+"个"+out);
		} 
	}

}
final class Level{
	public Player player;
	public int level;
	public void addLevel(int addlevel) {
		player.setLevel(level+addlevel);
	}
	public void setLevel(int setlevel){
		player.setLevel(setlevel);
	}
	public void decLevel(int declevel) {
		player.setLevel(level-declevel);
	}
	public Level(Player player,int level) {
		this.player = player;
		this.level = level;
	}

}
	
	
	
	
	
	
	
	//这里列出对于strif和strelse三种方法：get set 和 add;
	// strif
	/*public static StringBuffer getStrif() {
		return strif;
	}
	public static void setStrif(StringBuffer strif) {
		LoginGetWays.strif = strif;
	}
	public static void addStrif(StringBuffer addstrif){
		LoginGetWays.strif.append(addstrif);
	}
	//sstrelse
	public static StringBuffer getStrelse() {
		return strelse;
	}
	public static void setStrelse(StringBuffer strelse) {
		LoginGetWays.strelse = strelse;
	}
	public static void addStrelse(StringBuffer addstrelse) {
		LoginGetWays.strelse.append(addstrelse);
	}
}
*/



/*Player player = event.getPlayer();
PlayerInventory inventory = player.getInventory();
ItemStack itemStack1 = new ItemStack(Material.DIAMOND_SWORD,1);
ItemStack itemStack2 = new ItemStack(Material.GOLD_PICKAXE,16);
ItemStack itemStack3 = new ItemStack(Material.GOLD_AXE,2);
if (inventory.contains(itemStack1,1)&&inventory.contains(itemStack2,1)&&inventory.contains(itemStack3,1)){
	player.sendMessage("你的背包已有fly能给你的东西了，fly插件无法再给你更多的财富了！！！");
}
else {
	inventory.addItem(itemStack1);
	inventory.addItem(itemStack2);
	inventory.addItem(itemStack3);
	player.sendMessage("你已得到fly插件给你财富了！！！");
}*/