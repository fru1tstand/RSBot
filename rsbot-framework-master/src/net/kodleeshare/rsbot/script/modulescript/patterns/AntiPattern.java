package net.kodleeshare.rsbot.script.modulescript.patterns;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.ScriptPattern;
import net.kodleeshare.rsbot.script.modulescript.module.TimeableModule;
import net.kodleeshare.rsbot.script.modulescript.module.packages.ConditionalPackage;

public abstract class AntiPattern
		extends ConditionalPackage<TimeableModule> 
		implements ScriptPattern {

	private long nextActionTime;
	
	public AntiPattern(ClientContext ctx) {
		super(ctx);
		this.nextActionTime = 0;
	}

	@Override
	public final void runPattern() {
		if(this.canRun()) {
			TimeableModule tm = this.getRunModule();
			if(tm != null && tm.execute())
				this.nextActionTime = tm.getActionTime() + this.ctx.controller.script().getRuntime();
		}
	}
	
	@Override
	public final boolean canRun() {
		return this.ctx.controller.script().getRuntime() > this.nextActionTime;
	}
	
	public final void resetNextActionTime() {
		this.nextActionTime = 0;
	}
}
