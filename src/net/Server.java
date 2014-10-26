package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int PORT_NUM = 6560;
	public static final int BACKLOG = 3;
	private static boolean initialized = false;
	private static ServerSocket server = null;
	
	public static void init(){
		if(isInitialized()) return;
		try {
			server = new ServerSocket(PORT_NUM, BACKLOG);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		initialized = true;
	}
	
	protected static Socket getClient(){
		init();
		try {
			return server.accept();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(e.getMessage().hashCode());
		}
		return null;
	}
	
	public static boolean isInitialized(){
		return initialized;
	}
	
	private Server(){}

}
