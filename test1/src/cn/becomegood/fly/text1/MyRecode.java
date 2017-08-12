package cn.becomegood.fly.text1;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 一个用于加密的类 该类使用的是可逆加密方式，采用密匙并加密出两段加密字符串
 * 负数编码是取对应正数反码加1
 * @author fly
 * 
 */
public class MyRecode {
	private final int[][] DECOMPOSE = {{ 1,4,3}, {1,3,4}, { 3, 1, 4 } 
						,{ 3, 4, 1 },{ 4, 3, 1 }};		//少一种，配合后面达成伪8进制的效果
	private final int[] TENDIGEST = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15 };

	private String key;
	private String word;
	private int[] index;

	public static void main(String[] args) {
		MyRecode test = new MyRecode("dfsd分解哦", "用中文呢");
		try {
			System.out.println(test.word());
			System.out.println(test.octal());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


//		System.out.println(Integer.toBinaryString(-1&0xff<<3));			//对比发现左移左边的数字不舍去
																		//原因：该值还是int类型
//		System.out.println(Integer.toBinaryString(-1<<3&0xff));
	}

	/**
	 * 二进制，八进制，十六进制混合混顺序的加密方式
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String word() throws UnsupportedEncodingException {
		char[] HEXDIGEST = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] bytes = word.getBytes("UTF-8");
		char[] result = new char[bytes.length * 3];

		// System.out.println(octal());
		int j = 0; // 用于计数 bytes[j]
		int k = 0; // 用于计数 result[k]
		int i = 0; // 用于计数 index[i]
		for (i = 0; i <= index.length; i++) {	
			if (j >= bytes.length)
				break; // bytes遍历完了就跳出循环
			if (i >= index.length) { // 若index不够用就循环
				i = 0;
			}
			int a = DECOMPOSE[index[i]][0]; 	// 被该循环选择的数组的第一个数字
			int b = DECOMPOSE[index[i]][1]; 	// 被该循环选择的数组的第二个数字
			int c = DECOMPOSE[index[i]][2]; 	// 被该循环选择的数组的第三个数字
			//根据上面试验，需要注意左移
			result[k++] = HEXDIGEST[(bytes[j]&0xFF) >>> (8 - a)]; // 分解的第一个数
			result[k++] = HEXDIGEST[((bytes[j]&0xFF) << a &0xFF) >>> (8 - b)]; // 分解的第二个数
			result[k++] = HEXDIGEST[(((bytes[j]&0xFF) <<(a+b)) & 0xff )>>>(8-c)]; // 分解的第三个数
			j++; // j向前递一个
		}
		return new String(result);

	}

	/**
	 * 该方法进行将byte[]进行八进制转化 一个八进制对于3个二进制 一个字节8位，第九位根据index的奇偶性补位，奇补1，偶补2 最终得到
	 * @return 该值可以传输，并且通过解密类获取parse（key）的结果，从而不需要传输密匙，所以说这是伪动态密匙
	 * @throws UnsupportedEncodingException
	 */
	public String octal() throws UnsupportedEncodingException {
		char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7' };
		byte[] bytes = word.getBytes("UTF-8");
		char[] result = new char[bytes.length * 3];		//一个字符分解成了三个，所以结果容量增加三倍
		int k = 0;
		int i = 0;

		for (byte b : bytes) {
			if (i >= index.length) { // 如果溢出就归位循环
				i = 0;
			}
			result[k] = chars[b >>> 6 &0x3]; // 前2位
			result[k] += index[i];			//前一位原本在0-3的范围，现在0-7的范围 达到伪8进制的效果
			k++;i++;
//			System.out.println(b);
//			System.out.println(b>>>3&0x7);
//			System.out.println(b&0x38>>>3);			//移位运算优先级在&之前 故采用先移位后&
			result[k++] = chars[b  >>> 3 &0x7]; // 中间3位
			result[k++] = chars[b & 0x7]; // 最后三位
		}
		return new String(result);
	}

	/**
	 * 解析key 得到各元素小于6的int数组
	 * 
	 * @param key
	 * @return int数组
	 * @throws UnsupportedEncodingException
	 */
	private int[] parseKey(String key) throws UnsupportedEncodingException {
		byte[] keyBytes = toMd5(key);
		int[] turn = new int[keyBytes.length * 2]; // 16*2
		int[] result = new int[turn.length];
		int k = 0;
		for (byte b : keyBytes) {
			turn[k++] = TENDIGEST[b >>> 4 & 0xf]; // 取高四位的值
			turn[k++] = TENDIGEST[b & 0xf]; // 取低四位的值
		}
		k = 0; // 置零，方便下面使用
		for (int i : turn) {
			result[k++] = i % 5; // 用于DECOMPOSE的下标，也用于8升9位补位参照
		}
		return result;

	}

	// 返回MD5的计算结果128位的长整形数
	public byte[] toMd5(String str) throws UnsupportedEncodingException {
		byte[] bytes;
		bytes = str.getBytes("UTF-8");
		try {
			MessageDigest MD = MessageDigest.getInstance("MD5");
			MD.update(bytes);
			byte[] result = MD.digest();
			return result;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null; // 错误就返回null；
		}

	}

	// 构造函数区
	public MyRecode(String word, String key) {
		this(key);
		this.word = word;

	}

	public MyRecode(String key) {
		this.key = key;
		try {
			index=parseKey(key);							// 解析key  将result赋值给index这个全局变量
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	// set和get函数区
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int[][] getDECOMPOSE() {
		return DECOMPOSE;
	}

	public int[] getTENDIGEST() {
		return TENDIGEST;
	}

}
