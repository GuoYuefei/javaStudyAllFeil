package app1;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class JFrameTest1 {
	
	private static void constructGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame =new JFrame("My First Swing Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("Welcome!");
		frame.add(label);
//		frame.pack();
		frame.setBounds(350,250,300,125);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				
				@Override
				public void run() {

					constructGUI();
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
