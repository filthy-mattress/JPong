package main;

import java.util.LinkedList;
import java.util.List;

public class RegThread extends Thread {
	private long ID_MASTER = 0;
	private static final List<RegThread> allThreads = new LinkedList<RegThread>();
	
	public static List<RegThread> getAllThreads(){
		return new LinkedList<RegThread>(allThreads);
	}
	public static List<RegThread> getAllLiveThreads(){
		List<RegThread> res = new LinkedList<RegThread>();
		for(RegThread rt : getAllThreads()){
			if(rt.isAlive()){
				res.add(rt);
			}
		}
		return res;
	}
	public static RegThread getThreadByID(long ID){
		for(RegThread rt : getAllThreads()){
			if(rt.ID == ID){
				return rt;
			}
		}
		return null;
	}
	public static RegThread getThreadByName(String name){
		for(RegThread rt : RegThread.getAllThreads()){
			if(rt.getName().equals(name)){
				return rt;
			}
		}
		return null;
	}
	
	private final long ID = ++ID_MASTER;
	
	private void init(){
		allThreads.add(this);
	}
	
	public void waitForCompletion(){
		while(this.isAlive()){}
	}

	public RegThread() {
		init();
	}

	public RegThread(Runnable target) {
		super(target);
		init();
	}

	public RegThread(String name) {
		super(name);
		init();
	}

	public RegThread(ThreadGroup group, Runnable target) {
		super(group, target);
		init();
	}

	public RegThread(ThreadGroup group, String name) {
		super(group, name);
		init();
	}

	public RegThread(Runnable target, String name) {
		super(target, name);
		init();
	}

	public RegThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		init();
	}

	public RegThread(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		init();
	}
	public long getID() {
		return ID;
	}

}
