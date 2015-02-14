package net.kodleeshare.rsbot.script.modulescript.module;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;

public abstract class TimeableModule extends Module {
	public TimeableModule(ClientContext ctx) {
		super(ctx);
	}

	public abstract int getActionTime();
}
