package net.kodleeshare.rsbot.script;

public abstract class ScriptRunnable<T> implements Runnable {
	protected T reference;
	public ScriptRunnable(T ref) {
		this.reference = ref;
	}	
}