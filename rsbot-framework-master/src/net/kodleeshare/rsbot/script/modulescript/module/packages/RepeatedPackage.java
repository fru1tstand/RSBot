package net.kodleeshare.rsbot.script.modulescript.module.packages;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;

/**
 * 
 * @author Kodlee
 *
 * @param <T>
 */
public abstract class RepeatedPackage<T extends Module> extends SequentialPackage<T> {
	public RepeatedPackage(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean run() {
		while(this.canRun()) {
			super.run();
		}
		return true;
	}
}
