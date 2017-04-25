package cn.becomegood.calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;



public class VisualCalendar {
	public static void main(String[] args) throws ParseException {
		loadCalendar();
		while(true){
			loadCalendar(dateIn());
		}
	}

	/**
	 * 将输入的日期所在的月份日历输出！！！
	 * @param Calendar  输入的日期
	 * @author fly
	 * @param calendar
	 */
	public static void loadCalendar(Calendar calendar){
			
			int today_of_month = calendar.get(Calendar.DAY_OF_MONTH);        //记录输入日期
			int maxday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);	  //找出这个月最大天数
			calendar.set(Calendar.DAY_OF_MONTH,1);
			int firstday = calendar.get(Calendar.DAY_OF_WEEK)-1;    
			System.out.println("日\t一\t二\t三\t四\t五\t六");
			for(int i=0;i<firstday;i++){
				System.out.print("\t");
			}
			for(int i=1;i<=maxday;i++){
				if(i==today_of_month){
					System.out.print(i+"*\t");
				}else {
					System.out.print(i+"\t");
				}
				if (firstday!=0) {
					if (i%7==7-firstday) {
						System.out.println();
					}
				} else {
					if(i%7==0){
						System.out.println();
					}
				}
			
		}
	}
	/**
	 * 加载日历方法重载 无参数表示显示当前日期的日历
	 * @author fly
	 */
	public static void loadCalendar(){
			Calendar calendar = Calendar.getInstance();
			int today_of_month = calendar.get(Calendar.DAY_OF_MONTH);        //记录输入日期
			int maxday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);	  //找出这个月最大天数
			calendar.set(Calendar.DAY_OF_MONTH,1);
			int firstday = calendar.get(Calendar.DAY_OF_WEEK)-1;
			System.out.println("\t\t这个月的日历页");
			System.out.println("日\t一\t二\t三\t四\t五\t六");
			for(int i=0;i<firstday;i++){
				System.out.print("\t");
			}
			for(int i=1;i<=maxday;i++){
				if(i==today_of_month){
					System.out.print(i+"*\t");
				}else {
					System.out.print(i+"\t");
				}
				if (firstday!=0) {
					if (i%7==7-firstday) {
						System.out.println();
					}
				} else {
					if(i%7==0){
						System.out.println();
					}
				}
			
		}	
	}
	
	/**
	 * 这是一个静态函数，用于输入年月日，并返回当前输入的年月日。
	 * 当前只支持yyyy-MM-dd、yyyyMMdd这两种格式
	 * @return Calendar
	 * @throws ParseException
	 */
	 public static Calendar dateIn() throws ParseException{
		Date date = new Date();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n请输入查询的年月日（格式1：yyyy-MM-dd OR 格式2：不间断数字如19960313）:");
		String tmp = scanner.nextLine();
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		if(tmp.indexOf('-')==4){
			date = format1.parse(tmp);
		}else {
			date = format2.parse(tmp);
		}

		Calendar calendar = Calendar.getInstance();   //Calendar是抽象类，无构造方法，getInstance返回一个该类
		calendar.setTime(date);
		return calendar;
		
	}
}