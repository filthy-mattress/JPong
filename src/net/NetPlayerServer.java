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
	public static final String SUCCESS = "Success!";
	public static final String FAILURE = "Failure!";
	
	private static String getLine(BufferedReader br){
		String res = null;
		while(res == null){
			try {
				res = br.readLine();
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

	public NetPlayerServer(Point start) {
		super(start, KeyMode.WASD);
		while(client == null){
			client = Server.getClient();
		}
		try {
			this.toClient = new PrintStream(client.getOutputStream(), true);
			this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		String startState = getLine(fromClient);
		int i = 0;
		for(String state : startState.split(",")){
			keyboardState[i] = state.equals(""+true);
		}
		toClient.println(SUCCESS);
		while (true) {
			String mode = getLine(fromClient);
			if (mode.equals("WASD")) {
				this.mode = KeyMode.WASD;
				break;
			} else if (mode.equals("ARROWS")) {
				this.mode = KeyMode.ARROWS;
				break;
			}else{
				toClient.println(FAILURE);
			}
		}
		toClient.println(SUCCESS);
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
