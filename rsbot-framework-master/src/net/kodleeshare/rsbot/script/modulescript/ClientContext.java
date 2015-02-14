package net.kodleeshare.rsbot.script.modulescript;

import net.kodleeshare.rsbot.script.modulescript.patterns.AntiPattern;
import net.kodleeshare.rsbot.script.utilities.Logger;

public class ClientContext extends org.powerbot.script.rt6.ClientContext {
	public final AntiPattern antiPattern;
	public final Logger logger;
	public ClientContext(org.powerbot.script.rt6.ClientContext ctx, AntiPattern antiPattern) {
		super(ctx);
		if(antiPattern == null)
			antiPattern = new AntiPattern(this) {};
		this.antiPattern = antiPattern;
		this.logger = Logger.getLogger();
	}
	public final boolean isScriptPausing() {
		return this.controller.isSuspended() || this.controller.isStopping();
	}
}
