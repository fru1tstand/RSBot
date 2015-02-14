package net.kodleeshare.rsbot.script.modulescript.patterns;

import org.powerbot.script.Random;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.ScriptPattern;
import net.kodleeshare.rsbot.script.modulescript.module.packages.SequentialPackage;

public abstract class SequentialPattern<T extends Module> 
		extends SequentialPackage<T> 
		implements ScriptPattern {
	public SequentialPattern(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public final void runPattern() {
		int cycleModuleFails = 0;
		int failsRemaining = 0;
		while(!this.ctx.isScriptPausing()) {
			for(int i = 0; i < this.moduleList.size(); i++) {
				//TODO: Find better algorithm
				this.ctx.logger.log("Running Package " + this.moduleList.get(i).getClass().getName());
				failsRemaining = Random.nextInt(2, 7);
				while(failsRemaining > 0) {
					if(this.moduleList.get(i).execute()) {
						cycleModuleFails = 0;
						break;
					}
					failsRemaining--;
				}
				if(failsRemaining < 1)
					cycleModuleFails++;
			}
			if(cycleModuleFails > this.moduleList.size()) {
				System.out.println("No modules were able to be run, stopping script.");
				this.ctx.controller.stop();
				break;
			}
		}
	}
	
}
