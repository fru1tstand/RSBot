package net.kodleeshare.rsbot.script.modulescript.module.packages;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.ModulePackage;

/**
 * This package runs the first module that {@link #canRun()} and returns the result of running it. Otherwise
 * false if no module {@link #canRun()}
 * @param <T> The {@link Module} to be stored and run
 */
public abstract class ConditionalPackage<T extends Module> extends ModulePackage<T> {
	public ConditionalPackage(ClientContext ctx) {
		super(ctx);
	}
	
	/**
	 * Returns whatever module {@link #getRunModule()}'s run returns or false if no module was found
	 */
	@Override
	public final boolean run() {
		T rm = this.getRunModule();
		if(rm != null)
			return rm.execute();
		return false;
	}
	
	/**
	 * @return The module that would run if {@link #run()} was called.
	 */
	public final T getRunModule() {
		for(T rm : this.moduleList) {
			if(rm.canRun())
				return rm;
		}
		return null;
	}
}
