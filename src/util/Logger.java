package util;

import java.io.PrintStream;
import java.util.Date;

public class Logger {
	public static final Logger DEFAULT = new Logger();

	private final PrintStream out;
	public String format = "[%s]:\t%s";
	
	public Logger(PrintStream out){
		this.out = out;
	}
	
	public Logger(){
		this(System.err);
	}
	
	public void log(String message){
		Date d = new Date();
		out.println(String.format(format, d.toString(), message));
	}
}
