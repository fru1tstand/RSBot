package net.kodleeshare.rsbot.script.utilities;

public class Logger {
	private static Logger instance;
	
	public boolean debug;
	
	private Logger() {
		this.debug = false;
	}
	
	public static final Logger getLogger() {
		if(Logger.instance == null)
			Logger.instance = new Logger();
		return instance;
	}
	
	public final void log(String s) {
		if(this.debug)
			System.out.println(s);
	}
}
