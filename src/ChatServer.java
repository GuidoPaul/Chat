/*************************************************************************
	> File Name: ChatServer.java
	> Author: Bslin
	> Mail:  
	> Created Time: 2014年02月08日 星期六 17时40分59秒
 ************************************************************************/

import java.awt.*;
import java.net.*;
import java.io.*;

public class ChatServer {

	boolean started = false;
	ServerSocket ss = null;

	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;
		} catch (BindException e) {
			System.out.println("The port is in use...");
			System.out.println("Please close the related procedure and re run the server!");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while(started) {
				Socket s = ss.accept();
				Client c = new Client(s);
System.out.println("a client connected!");
				new Thread(c).start();
				// dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Client implements Runnable {
		private Socket s;
		private DataInputStream dis = null;
		private boolean bConnected = false;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				bConnected = true;
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				while(bConnected) {
					String str = dis.readUTF();
					System.out.println(str);
				}
			} catch (EOFException e) {
				System.out.println("A client closed!");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dis != null) {
						dis.close();
					}
					if(s != null) {
						s.close();
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
