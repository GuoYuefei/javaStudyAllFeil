package cn.becomegood.testio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Demo1 {
	static String pathString = "/home/fly/Desktop/test";
	static Path path = Paths.get(pathString);
	static Path path0 = Paths.get(pathString, "testdir");
	static Path path1 = Paths.get(pathString, "test");
	static Path path1$1 = Paths.get(pathString, "test1");
	static Path pictursePath = Paths.get(pathString, "faceyellow.jpg");
	static Path createPictursePath = Paths.get(pathString, "create.jpg");

	public static void main(String[] args) {
		/**
		 * 测试path的一些方法
		 */
		// int num=path.getNameCount();
		// System.out.println(num);
		// for(int i=0;i<num;i++){
		// System.out.println(path.getName(i));
		// }
		// System.out.println("--------------------------");
		// System.out.println(path.getFileName());
		// System.out.println(path.getParent());
		// System.out.println(path.getFileSystem());
		// System.out.println(path.getRoot());
		// System.out.println("--------------------");

		/**
		 * 试验创建文件夹或者文件
		 */
		// try {
		// Files.createDirectory(path0);
		// Files.createFile(path1);
		// Files.deleteIfExists(path0);
		// Files.deleteIfExists(path1);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		/**
		 * 适合所有类型文件 将fileToByte返回的byte[]值传递到OStest，重新在另一个文件中还原数据
		 */
		// byte[] temp = FileIO.fileToByte(pictursePath,1008);
		// FileIO.OStest(createPictursePath,temp);
		// FileIO.OSWriter(Paths.get(pathString,"OSWriter"),"你好啊！\n");
		// FileIO.OSPrintWrite(Paths.get(pathString,"OSPringwrite"),path1);
		// FileIO.ISRead(path1);
		// FileIO.PSTest(Paths.get(pathString,"pstest"),"这是一个PrintSteam的一个测试");

		// FileIO.seekableByteChannelTest(Paths.get(pathString,"seekable"));
		FileIO.IStest(path1);

	}

}

class FileIO {
	/**
	 * 定义三个常用的字符集
	 */
	private final static Charset UTF_8 = Charset.forName("UTF-8");
	@SuppressWarnings("unused")
	private final static Charset GBK = Charset.forName("GBK");
	@SuppressWarnings("unused")
	private final static Charset ASCII = Charset.forName("US-ASCII");

	public static SeekableByteChannel seekableByteChannelTest(Path path) {
		try{ SeekableByteChannel seekable = Files.newByteChannel(path,
				StandardOpenOption.READ, StandardOpenOption.WRITE,
				StandardOpenOption.CREATE); 
			ByteBuffer buffer = ByteBuffer.allocate(29);
			buffer.putLong(123456l); // 8
			buffer.putChar('q'); // 2
			buffer.putShort((short) 789); // 2
			buffer.putInt(123456789); // 4
			buffer.put((byte) 120); // 1
			buffer.putFloat(123.1F); // 4
			buffer.putDouble(456.123); // 8
			System.out.println(buffer.position());
			buffer.position(8); // 文件指针
			buffer.limit(17); // 上限
			System.out.println(buffer.position());
			seekable.write(buffer);
			System.out.println(seekable.position());
			return seekable;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将t输出到path所指向的文件中
	 * 
	 * @param path
	 * @param t
	 */
	public static <T> void PSTest(Path path, T t) {
		try{ OutputStream os = Files
				.newOutputStream(path, StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING);
				PrintStream ps = new PrintStream(os, true);
				PrintStream out = System.out;
			System.setOut(ps);
			System.out.println(t);
			System.setOut(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取path指向文件的内容，按行读取
	 * 
	 * @param path
	 */
	public static void ISRead(Path path) {
		try{ /* BufferedReader buffer =Files.newBufferedReader(path,UTF_8); */// 该代码可以实现下面三行的效果！！
		InputStreamReader reader = new InputStreamReader(Files.newInputStream(
				path, StandardOpenOption.READ), UTF_8);
				BufferedReader buffer = new BufferedReader(reader) ;
			String line = buffer.readLine();
			while (line != null) {
				System.out.println(line);
				line = buffer.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件使用了create和truncate_exising参数 通过printWrite流的形式输出到文件 泛型 E
	 * 
	 * @param path
	 *            文件路径，如果不存在文件就创建
	 * @param e
	 *            泛型量
	 */
	public static <E> void OSPrintWrite(Path path, E e) {
		try{ OutputStream os = Files
				.newOutputStream(path, StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING);
				BufferedWriter bufferedWriter = new BufferedWriter(
						new OutputStreamWriter(os, UTF_8));
				PrintWriter pWriter = new PrintWriter(bufferedWriter); 
			pWriter.print(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 文件使用了create和append参数 使用OutputStreamWriter
	 * 
	 * @param path
	 *            写入文件的路径
	 * @param string
	 *            写入的文字
	 */
	public static void OSWriter(Path path, String string) {
		try{ OutputStream os = Files.newOutputStream(path,
				StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				OutputStreamWriter writer = new OutputStreamWriter(os, UTF_8);
				BufferedWriter buffered = new BufferedWriter(writer); 
			buffered.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回path指向文件的byte[] 数据大小不得超过size大小的字节
	 * 
	 * @param path
	 * @param size
	 * @return
	 */
	public static byte[] fileToByte(Path path, int size) {
		try {InputStream inputStream = Files.newInputStream(path);
				BufferedInputStream buffered = new BufferedInputStream(
						inputStream); 
			int i = 0;
			byte[] result = new byte[size];
			byte[] temp = new byte[size + 1];
			i = inputStream.read(temp);
			System.arraycopy(temp, 0, result, 0, size);
			if (i == size + 1) {
				System.out.println("输入的size大小不够");
				return null;
			} else {
				return result;
			}
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 文件使用了create参数 使用OutputStream输出二进制流进OSpath指向的文件
	 * 
	 * @param OSpath
	 * @param b
	 */
	public static void OStest(Path OSpath, byte[] b) {
		try{ OutputStream outputStream = Files.newOutputStream(OSpath,
				StandardOpenOption.CREATE);
				BufferedOutputStream buffered = new BufferedOutputStream(
						outputStream);
			buffered.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * i是对于的字符集编码
	 */
	public static void IStest(Path path1) {
		try{ InputStream inputStream = Files.newInputStream(path1,
				StandardOpenOption.READ);
				BufferedInputStream buffered = new BufferedInputStream(
						inputStream);

			int i = 0;
			int j = 0;
			while (i != -1) {
				// i=buffered.read();
				// byte b = (byte)i;
				// System.out.println(b);
				byte[] b = new byte[1024];
				i = buffered.read(b);
				System.out.println("^^^^^^^^^ " + i + " ^^^^^^^^^");
				for (byte c : b) {
					if (c != 0) {
						++j;
						System.out.println(j + ":" + c);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件使用了append参数 使用OutputStreamWirte写入
	 */
	public static void outputStreamWriterTest(Path path1) {
		String output = new String("大家好！\n");
		try{ OutputStream outputStream = Files.newOutputStream(path1,
				StandardOpenOption.APPEND);
				OutputStreamWriter writer = new OutputStreamWriter(
						outputStream, UTF_8);
			writer.write(output);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 文件使用了create参数 适合小文件 直接通过Files类的方法读取文本文件
	 * 
	 * @param path1
	 */
	public static void readAndWirte(Path path1) {
		String line1 = "文件读写的试验";
		String line2 = "first";
		String line3 = "Holle Word!";
		List<String> lines = Arrays.asList(line1, line2, line3);
		try {
			Files.write(path1, lines, UTF_8, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> linesRead = null;
		try {
			linesRead = Files.readAllLines(path1, UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (linesRead != null) {
			for (String string : linesRead) {
				System.out.println(string);
			}
		}
	}

	/**
	 * 遍历指定路径下的子文件系统 ！！不会遍历子孙文件或者文件夹 外加一个复制文件
	 * 
	 * @param pathString
	 * @param path1
	 */
	public static void goThrough(String pathString, Path path1) {
		Path father = Paths.get("/home/fly/Desktop");
		try{ DirectoryStream<Path> children = Files.newDirectoryStream(father);
			for (Path child : children) {
				System.out.println(child);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * 复制从path1 to path3
		 */
		try {
			Path path3 = Paths.get(pathString, "testdir", "123");
			Files.copy(path1, path3, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
