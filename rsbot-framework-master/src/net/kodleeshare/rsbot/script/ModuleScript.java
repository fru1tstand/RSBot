package net.kodleeshare.rsbot.script;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.ScriptPattern;
import net.kodleeshare.rsbot.script.modulescript.patterns.AntiPattern;

import org.powerbot.script.AbstractScript;

public abstract class ModuleScript<P extends ScriptPattern>
		extends AbstractScript<org.powerbot.script.rt6.ClientContext> {
	protected ClientContext ctx;
	protected P script;
	
	protected abstract void onStart();
	protected abstract void onStop();
	protected abstract void onPause();
	protected abstract void onResume();
	protected abstract AntiPattern getAntiPatternInstance();
	protected boolean readyToStart = false;
	
	{
		this.getExecQueue(State.START).add(new ScriptRunnable<ModuleScript<P>>(this) {
			@Override 
			public void run() { 
				this.reference.onStart();
				if(this.reference.readyToStart)
					this.reference.script.runPattern();
				this.reference.readyToStart = true;
			}
		});
		this.getExecQueue(State.STOP).add(new ScriptRunnable<ModuleScript<P>>(this) {
			@Override public void run() { this.reference.onStop(); }
		});
		this.getExecQueue(State.SUSPEND).add(new ScriptRunnable<ModuleScript<P>>(this) {
			@Override public void run() { this.reference.onPause(); }
		});
		this.getExecQueue(State.RESUME).add(new ScriptRunnable<ModuleScript<P>>(this) {
			@Override public void run() { this.reference.onResume(); }
		});
		this.ctx = new ClientContext(super.ctx, this.getAntiPatternInstance());
	}
	
	
}
