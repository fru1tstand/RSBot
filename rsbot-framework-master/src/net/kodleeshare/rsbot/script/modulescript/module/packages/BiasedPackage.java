package net.kodleeshare.rsbot.script.modulescript.module.packages;

import org.powerbot.script.Random;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.ModulePackage;

/**
 * This package runs a random module selected from a normal distribution, randomly selecting the mean
 * {@link Module}, and randomly choosing a standard deviation.
 * @param <T> The {@link Module} type to be stored and run.
 */
public abstract class BiasedPackage<T extends Module> extends ModulePackage<T> {
	public BiasedPackage(ClientContext ctx) {
		super(ctx);
	}

	private int meanModule;
	private double stdev;
	
	@Override
	public final boolean run() {
		if(this.moduleList.size() == 0) {
			System.out.println("Warning: BiasedPackage contains no modules");
			return false;
		}
		if(this.moduleList.size() == 1)
			return this.execute(0);
		return this.execute(Random.nextGaussian(0, this.moduleList.size() - 1, this.meanModule, this.stdev));
	}
	
	@Override
	public final BiasedPackage<T> install(T rm) {
		super.install(rm);
		this.meanModule = Random.nextInt(0, this.moduleList.size());
		this.stdev = Random.nextDouble(0.2, 1.0);
		return this;
	}
}
