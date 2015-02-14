package net.kodleeshare.rsbot.script.modulescript.module.packages;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.ModulePackage;

/**
 * This packages runs all installed {@link Module}s in the order they were installed until
 * all RunnableModules have been run or one returns false.
 * @param <T> The {@link Module} to be stored and run
 */
public abstract class SequentialPackage<T extends Module> extends ModulePackage<T> {
	public SequentialPackage(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean run() {
		int failures = 0;
		for(int i = 0; i < this.moduleList.size(); i++) {
			this.ctx.logger.log("Running Package " + this.moduleList.get(i).getClass().getName());
			if(!this.execute(i))
				failures++;
		}
		if(this.moduleList.size() == failures)
			return false;
		return true;
	}
}
