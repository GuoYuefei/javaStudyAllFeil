package cn.becomegood.fly.JMysqlTest1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class test1{
	
	public static void main(String args[]){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","123456");
			Statement statement = connection.createStatement();
//			statement.executeUpdate("insert into users values" +
//					"('12312','女','17802592322','钱七','10')");
			ResultSet user_nameResultSet = statement.executeQuery("select user_name,user_sex from users " +
					"where user_id=12312");
			user_nameResultSet.next();
			System.out.println("name:"+user_nameResultSet.getString(1)+
					"\nsex:"+user_nameResultSet.getString(2));
			connection.close();
		
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
