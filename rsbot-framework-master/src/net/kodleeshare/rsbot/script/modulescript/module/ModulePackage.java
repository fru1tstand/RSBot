package net.kodleeshare.rsbot.script.modulescript.module;

import java.util.ArrayList;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;

public abstract class ModulePackage<T extends Module> extends Module {
	protected ArrayList<T> moduleList;
	
	public ModulePackage(ClientContext ctx) {
		super(ctx);
		this.moduleList = new ArrayList<T>();
	}
	
	public ModulePackage<T> install(T rm) {
		if(rm == null)
			return this;
		this.moduleList.add(rm);
		return this;
	}
	public ModulePackage<T> install(T[] rms) {
		for(T rm : rms)
			this.install(rm);
		return this;
	}
	
	protected final boolean execute(int module) {
		return this.moduleList.get(module).execute();
	}
}
