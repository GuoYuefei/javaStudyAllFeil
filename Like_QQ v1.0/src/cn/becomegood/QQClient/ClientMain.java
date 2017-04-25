package cn.becomegood.QQClient;


public class ClientMain {
	
	
//	@SuppressWarnings("deprecation")
	public static void main(String[] args) {			 
			SocketClient client = new SocketClient();
			client.start();
			client.await();
//			client.stop();
			
	}
}
