package cn.becomegood.fly.text1;

import java.nio.charset.Charset;


/**
 * 解密过程
 * 并非是加密的逆过程 这是因为加密中有部分是不可逆的
 * 而是使用遍历手段得到密文
 * 遍历手段影响速度，但是遍历通过部分逆过程降低到最少
 * @author fly
 *
 */
public class MyDecode {
	//1代表分解1位用二进制，4：16进制，3：8进制
	private final int[][] DECOMPOSE = { { 1,4,3}, {1,3,4}, { 3, 1, 4 } 
					,{ 3, 4, 1 },{ 4, 3, 1 }};
	
	private String octalKey;
	private String ciphertext;				//单词ciphertext,译为密文
	
	
	public static void main(String[] args) {
		MyDecode test = new MyDecode("0C43063130C41C5408106708127A111C5413506", 
							"144346363144345410306550347643345423446");
		
//		System.out.println(Integer.toBinaryString(1*128+1*64+32+4+1));
//		System.out.println((int)Math.pow(2,7));
//		System.out.println(Integer.toBinaryString(-27));
//		System.out.println("int转换成byte:"+(byte)229);			//证明了该转换是根据int储存后8位转换的
		System.out.println(test.parse());
//		test.parse();
	}
	
	//该方法测试时用的，暂且保留做纪念
	void printInts(int[] ints){
		StringBuilder Str = new StringBuilder();
		for (int i : ints) {
			Str.append(i+",");
		}
		System.out.println(Str.toString());
	}
	/**
	 * 解析密文
	 * @return	原文字符串
	 */
	public String parse(){
		int length;
		if (octalKey.length()==ciphertext.length()) {
			length = octalKey.length()/3;
		}else {
			try {
				throw new Exception("两段密文不等长");
			} catch (Exception e) {
				e.printStackTrace();
				return null; 				//还是错误就结束函数返回null;
			}
		}
		//该byte数组记录已经全部还原后的原文byte数组，只需要调用相应编码进行还原字符串就行
		byte[] bytes = new byte[length];		
		int[] temp = new int[length];			//记录一个字节对应的整形信息
		for(int k = 0; k<length;k++){
			boolean flag = false;
			for (int i = 0; i < 5; i++) {		//0-4寻找组态解密
				if (disposeCiphertext(k,i)==disposeKey(k,i)) {	//判断整形是否相等
					temp[k] = disposeCiphertext(k,i);			//相等就找到文本并记录到temp数组
//					System.out.println("找到了");
					flag = true;					//标志改变，表示已经找到
					break;
				}
			}
			if (!flag) {
				try {
					throw new Exception("第"+k+"个解析出错");
				} catch (Exception e) {
					e.printStackTrace();
					return null;								//二次出错，提前结束函数返回null;
				}
			}
		}
		int k = 0;
		for (int i : temp) {
//			System.out.println(i);
			bytes[k] = (byte)i;		//将整形数组记录的内容一一赋值给byte
//			System.out.println(bytes[k]);
			k++;
		}
		String reslut = new String(bytes,Charset.forName("UTF-8"));
		return reslut;
	}
	
	
	/**
	 * 该函数是单字节解析，需要配合for循环遍历
	 * 根据输入的值形成对应该值的解析
	 * 因为加密时用的是key解析之后的数组成员值均小于5,所有i取值在0-4中
	 * @param int num 从0开始    表示解析第几个字节 三个char的值解密一个字节
	 * @param int i 取值范围0-4（两端包含，一般用于循环）
	 */
	private int disposeCiphertext(int num,int i) {
		char[] chars = ciphertext.toCharArray();
		int[] flag = DECOMPOSE[i];		//得到按哪种方式分解 分解方式储存在int数组中了
		int result;
		int[] temp = new int[3];			//三位一转换，主要遇到十六进制的字母需要转换
		for(int j=0;j<3;j++){
			switch (chars[3*num+j]) {
			case '0':
				temp[j] = 0;break;
			case '1':
				temp[j] = 1;break;
			case '2':
				temp[j] = 2;break;
			case '3':
				temp[j] = 3;break;
			case '4':
				temp[j] = 4;break;
			case '5':
				temp[j] = 5;break;
			case '6':
				temp[j] = 6;break;
			case '7':
				temp[j] = 7;break;
			case '8':
				temp[j] = 8;break;
			case '9':
				temp[j] = 9;break;
			case 'A':
				temp[j] = 10;break;
			case 'B':
				temp[j] = 11;break;
			case 'C':
				temp[j] = 12;break;
			case 'D':
				temp[j] = 13;break;
			case 'E':
				temp[j] = 14;break;
			case 'F':
				temp[j] = 15;break;
			}
		}
		result = temp[0]*(int)Math.pow(2,8-flag[0])
						+temp[1]*(int)Math.pow(2,8-flag[0]-flag[1])
						+temp[2]*1;							//2的0次方
		return result;
	}
	
	
	
	
	/**
	 * 该函数是单字节解析的，需要配合for循环
	 * 根据输入的值形成对应该值的解析
	 * 因为加密时用的是key解析之后的数组成员值均小于5,所有i取值在0-4中
	 * @param int num 表示第几个字符 范围从0开始
	 * @param int i 范围0-4 （两端包含，一般用于循环）
	 */
	private int disposeKey(int num,int i){
		char[] chars = octalKey.toCharArray();
/*		char[] tmep = new char[chars.length/3];			//密匙长度一定是3的倍数，不用考虑不能整除
		for(int i=0;i<chars.length;i+=3){
			tmep[i/3] = chars[i];						//每三个数取一个，因为只有这几个数蕴含密匙信息
		}*/
		int result;			//每三个char形成一个int
			
		//八进制转换成10进制
		result = (Integer.parseInt(String.valueOf(chars[3*num+0]))-i)*64	//根据i得到一种情况
						+Integer.parseInt(String.valueOf(chars[3*num+1]))*8
						+Integer.parseInt(String.valueOf(chars[3*num+2]))*1;
		return result;
	}


//	构造函数区
	public MyDecode(String ciphertext, String octalKey) {
		super();
		this.octalKey = octalKey;
		this.ciphertext = ciphertext;
	}


	
	public MyDecode(String octalKey) {
		super();
		this.octalKey = octalKey;
	}
//	get和set函数区
	public String getOctalKey() {
		return octalKey;
	}

	public void setOctalKey(String octalKey) {
		this.octalKey = octalKey;
	}

	public String getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}
	
}
