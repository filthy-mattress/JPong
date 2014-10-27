package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.lwjgl.input.Keyboard;

import game.KeyMode;
import game.LocalPlayer;
import geom.Point;

public class NetPlayerServer extends LocalPlayer{
	public static final String REQ_PREFIX = "!REQ:";
	public static final String RES_PREFIX = "!RES:";
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
	
	Socket client = null;
	PrintStream toClient = null;
	BufferedReader fromClient = null;
	boolean[] keyboardState = new boolean[Keyboard.getKeyCount()];
	
	public NetPlayerServer(Point start, Socket client){
		super(start, KeyMode.WASD);
		try {
			this.toClient = new PrintStream(client.getOutputStream(), true);
			this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		while(!this.fetchKeyboardState()){}
		while(!this.fetchKeyMode()){}
		
	}

	public NetPlayerServer(Point start) {
		this(start, Server.getClient());
	}
	private boolean sendFailure(){
		toClient.println(NetPlayerServer.RES_PREFIX+FAILURE);
		return false;
	}
	private boolean sendSuccess(){
		toClient.println(NetPlayerServer.RES_PREFIX+SUCCESS);
		return true;
	}
	private boolean fetchKeyboardState(){
		toClient.println(NetPlayerServer.REQ_KEYSTATE);
		String startState = getLine(fromClient);
		int i = 0;
		for(String state : startState.split(",")){
			keyboardState[i] = state.equals(""+true);
		}
		return this.sendSuccess();
	}
	
	private boolean fetchKeyMode(){
		toClient.println(NetPlayerServer.REQ_KEYMODE);
		String mode = getLine(fromClient);
		for(KeyMode km : KeyMode.values()){
			if(mode.equalsIgnoreCase(km.toString())){
				this.mode = km;
				this.sendSuccess();
			}
		}
		return this.sendFailure();
	}
	
	public boolean isConnected(){
		return client != null && client.isConnected();
	}

	@Override
	public boolean isGoingUp() {
		return this.keyboardState[this.getUpKey()];
	}

	@Override
	public boolean isGoingDown() {
		return this.keyboardState[this.getDownKey()];
	}

}
