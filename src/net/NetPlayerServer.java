package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import util.Util;
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
 * |  Local  |      | JPong  |      | Server |      |          |      | JPong  |      | Client |
 * |  Player | ---> | Server | <--- | Player | <--- |          | <--- | Client | <--- | Player |
 * |         |      | Match  |      |        |      |          |      | Match  |      |        |
 * +---------+      +---+----+      +--------+      |  LAN or  |      +----+---+      +--------+
 *                      |                           | Internet |           |
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
	private void send(Object... objects){
		String msg = Util.join(Protocol.SEPARATOR, objects);
		toClient.println(msg);
	}
	private boolean sendFailure(){
		send(Protocol.RES_PREFIX,Protocol.FAILURE);
		return false;
	}
	private boolean sendSuccess(){
		send(Protocol.RES_PREFIX,Protocol.SUCCESS);
		return true;
	}
	private String fetchMessage(){
		return getLine(fromClient);
	}
	
	/**
	 * Reads message and takes appropriate action
	 * @param message Message from client
	 * @return true if processed successfully, false otherwise
	 */
	private boolean readMessage(String message){
		String[] tokens = Protocol.tokenize(message);
		if(tokens.length==0){
			System.err.println("Received message with no tokens!");
			return false;
		}
		String cmd = tokens[0];
		if(cmd.equals(Protocol.SET_PREFIX)){
			if(tokens.length<2){
				System.err.println("Received set command with no property specification!");
				return false;
			}
			if(tokens.length<3){
				System.err.println("Received set command with no value specification!");
				return false;
			}
			String property = tokens[1];
			if(Util.contains(Protocol.BOOL_PROPS, property)){
				boolean b = Boolean.parseBoolean(property);
				if(property.equals(Protocol.GO_UP_PROP)){
					this.goUp = b;
				}else if(property.equals(Protocol.GO_DOWN_PROP)){
					this.goDown=b;
				}else if(property.equals(Protocol.LISTEN_PROP)){
					this.listen=b;
				}else{
					System.err.println("Recieved set command referencing unknown boolean property: "+property);
					return false;
				}
			}
		}
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
