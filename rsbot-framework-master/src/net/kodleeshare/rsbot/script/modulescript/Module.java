package net.kodleeshare.rsbot.script.modulescript;

import net.kodleeshare.rsbot.script.ActionType;
import net.kodleeshare.system.Utilities;

public abstract class Module {
	protected ActionType[] moduleActionTypes;
	protected ClientContext ctx;
	
	protected abstract boolean run();
	public abstract boolean canRun();
	
	public final boolean execute() {
		if(this.ctx.isScriptPausing())
			return false;
		if(!this.canRun())
			return false;
		return this.run();
	}
	
	public Module(ClientContext ctx) {
		this.ctx = ctx;
	}
	
	protected void is(ActionType...actionTypes) {
		this.moduleActionTypes = actionTypes;
	}
	public final boolean canRunWith(Module m) {
		for(ActionType a : m.getModuleActionTypes())
			for(ActionType b : this.moduleActionTypes)
				if(a == b)
					return false;
		return true;
	}
	
	public final ActionType[] getModuleActionTypes() {
		return Utilities.cloneArray(ActionType.class, this.moduleActionTypes);
	}
}
