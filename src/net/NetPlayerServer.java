package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import main.RegThread;

import game.Player;
import geom.Point;

/**
 * <p>This class represents a remote player playing a game hosted on this machine.</p>
 * 
 * <p>Framework Diagram:</p>
 * 
 * <p><pre class="code">
 * +---------+      +--------+      +--------+      +----------+      +--------+      +--------+
 * |  Local  |      | JPong  |      | Server |      |  LAN or  |      | JPong  |      | Client |
 * |  Player | ---> | Server | <--- | Player | <--- | Internet | <--- | Client | <--- | Player |
 * |         |      | Match  |      |        |      |          |      | Match  |      |        |
 * +---------+      +---+----+      +--------+      |          |      +----+---+      +--------+
 *                      |                           |          |           |
 *                      v                           |          |           ^
 *                      |                           |          |           |
 *                      +------->--------------->---|          |-->--------+
 *                                                  +----------+
 * </pre></p>
 * 
 * @author trh
 *
 */
public class NetPlayerServer extends Player{
	public static final String REQ_PREFIX = "!REQ:";
	public static final String RES_PREFIX = "!RES:";
	public static final String KEY_PREFIX = "!KEY:";
	public static final int FAILURE = 0;
	public static final int SUCCESS = 1;
	public static final int REQ_KEYSTATE = 2;
	public static final int REQ_KEYMODE = 3;
	
	private static String getLine(BufferedReader br){
		String res = null;
		while(res == null){
			try {
				res = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	final Socket client;
	final PrintStream toClient;
	final BufferedReader fromClient;
	private volatile boolean goUp = false, goDown = false, listen = false;
	RegThread listenThread = null;
	
	public NetPlayerServer(Point start, Socket client, boolean startlisten){
		super(start);
		this.client=client;
		PrintStream tmpToClient = null;
		BufferedReader tmpFromClient = null;
		try {
			tmpToClient = new PrintStream(client.getOutputStream(), true);
			tmpFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		this.toClient = tmpToClient;
		this.fromClient = tmpFromClient;
		this.listenThread = new RegThread(new Runnable(){
			@Override
			public void run() {
				listenLoop();
			}
		}, "net.NetPlayerServer.listenThread");
		this.listenThread.setDaemon(true);
		if(startlisten){
			this.startListening();
		}
	}
	public NetPlayerServer(Point start) {
		this(start, Server.getClient(), true);
	}
	
	public void startListening(){
		if(!listen){
			this.listenThread.start();
		}
	}
	
	/**
	 * Ends the listenThread
	 * @param interrupt If true the thread will be interrupted and ended forcefully. If false it will wait for the thread to die naturally.
	 */
	public void stopListening(boolean interrupt){
		this.listen=false;
		if(interrupt){
			this.listenThread.interrupt();
		}
		while(this.listenThread.isAlive()){}
	}
	private void listenLoop(){
		listen = true;
		while(listen){
			if(readMessage(fetchMessage())){
				sendSuccess();
			}else{
				sendFailure();
			}
		}
	}
	private boolean sendFailure(){
		toClient.println(NetPlayerServer.RES_PREFIX+FAILURE);
		return false;
	}
	private boolean sendSuccess(){
		toClient.println(NetPlayerServer.RES_PREFIX+SUCCESS);
		return true;
	}
	private String fetchMessage(){
		return getLine(fromClient);
	}
	private boolean readMessage(String message){
		
		return true;
	}
	public boolean isConnected(){
		return client != null && client.isConnected();
	}

	@Override
	public boolean isGoingUp() {
		return goUp;
	}

	@Override
	public boolean isGoingDown() {
		return goDown;
	}

}
