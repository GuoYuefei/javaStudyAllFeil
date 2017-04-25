package cn.becomegood.fly.chatroom.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 能够快速的进行数据库操作
 * 除默认数据以外可以使用构造函数直接创建一个自己的数据库连接的对象
 * @author fly
 *
 */
public final class DataUtil {
	public static final int UPDATE = 0;
	public static final int QUERY = 1;
	private int number = 0;
	private ResultSet resultSet = null;
	private String DBMS = "mysql"; // 数据库默认使用mysql
	private String ip = "127.0.0.1"; // 数据库默认地址
	private String port = "3306"; // 数据库默认端口
	private String DBName = "chatroom";
	private String user = "root";
	private String passwd = "123456";

	// 数据库更新数据的简便函数 封装数据库更新时用的代码
	/**
	 * 根据操作指令操作数据库 如果是UPDATE即可调用一次静态的getNumber()得到操作数量 第二次调用无法得到正确的值
	 * 如果是QUERY一次调用限制一个sql语句 如果是QUERY即可调用一次静态的getResultSet()得到查询的结果集 第二次调用无效
	 * 
	 * @param operation
	 *            操作类型 DataUtil中有静态常量选择
	 * @param sql
	 *            需要进行这一操作的
	 * @param add
	 *            如果使用了占位符，就将占位的string一次填入，否则填null
	 * @author fly
	 */
	public boolean dataExe(int operation, String sql, String... add) {
		int i = 1;
		boolean isSuccess = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:" + DBMS
					+ "://" + ip + ":" + port + "/" + DBName, user, passwd);
			if (operation == UPDATE) {
				number = 0;
				PreparedStatement preparedStatement = connection
						.prepareStatement(sql);
				for (String string : add) {
					preparedStatement.setString(i++, string); // 将填满占位符
				}
				number = preparedStatement.executeUpdate();
			} else if (operation == QUERY) {
				resultSet = null;
				PreparedStatement preparedStatement = connection
						.prepareStatement(sql);
				for (String string : add) {
					preparedStatement.setString(i++, string); // 将填满占位符
				}
				resultSet = preparedStatement.executeQuery();
			} else {
				System.err.println("没有输入正确的操作指令");
				isSuccess = false;
			}

		} catch (ClassNotFoundException e) {
			isSuccess = false;
//			e.printStackTrace();
		} catch (SQLException e) {
			isSuccess = false;
//			e.printStackTrace();
		}
		return isSuccess;
	}

	public int getNumber() {
		return number;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	// 以下全部为本类的构造函数 以及 数据库连接所需数据的get与set函数

	public DataUtil(String dBMS, String ip, String port, String dBName,
			String user, String passwd) {
		super();
		DBMS = dBMS;
		this.ip = ip;
		this.port = port;
		DBName = dBName;
		this.user = user;
		this.passwd = passwd;
	}

	public DataUtil() {
		super();
	}

	public DataUtil(String dBName) {
		super();
		DBName = dBName;
	}

	public DataUtil(String dBMS, String dBName) {
		super();
		DBMS = dBMS;
		DBName = dBName;
	}

	public DataUtil(String ip, String port, String dBName, String user,
			String passwd) {
		super();
		this.ip = ip;
		this.port = port;
		DBName = dBName;
		this.user = user;
		this.passwd = passwd;
	}

	public String getDBMS() {
		return DBMS;
	}

	public void setDBMS(String dBMS) {
		DBMS = dBMS;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDBName() {
		return DBName;
	}

	public void setDBName(String dBName) {
		DBName = dBName;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
