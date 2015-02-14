package net.kodleeshare.rsbot.script.modulescript.module;

import net.kodleeshare.rsbot.game.World;
import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.system.Utilities.MinMaxPair;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;

/**
 * A module that will not return until the actions are completed, the script is stopping or paused, or 
 * {@link #canRun()} returns false.
 */
public abstract class SynchronousModule extends Module {
	private final MinMaxPair minMaxWaitTime;
	public SynchronousModule(ClientContext ctx, int minWait, int maxWait) {
		this(ctx, new MinMaxPair(minWait, maxWait));
	}
	public SynchronousModule(ClientContext ctx, MinMaxPair mm) {
		super(ctx);
		this.minMaxWaitTime = mm;
	}

	private static final int POLL_DELAY = 50;
	/**
	 * The simple action to run
	 * @return True if succeeded
	 */
	protected abstract void syncRun();
	
	/**
	 * The result of the action (eg. Menu opening, player mining, etc)
	 * @return True if the action has been met
	 */
	protected abstract boolean waitFor();
	
	/**
	 * The completed result of the action (eg. Menu is open, player has resulting mining ore in inventory, etc)
	 * @return True if result of action has been met
	 */
	protected abstract boolean waitUntil();
	
	protected final boolean run() {
		this.syncRun();
		this.ctx.antiPattern.resetNextActionTime();
		long sTime = System.currentTimeMillis();
		long waitTime = World.getActionWaitTime();
		while(System.currentTimeMillis() - sTime < waitTime 
				&& !this.waitFor() 
				&& !this.ctx.isScriptPausing())
			Condition.sleep(POLL_DELAY);
		if(!this.waitFor() || this.ctx.isScriptPausing())
			return false;
		World.addReactionTime((int) (System.currentTimeMillis() - sTime));
		long waitUntil = this.ctx.controller.script().getRuntime() 
				+ Random.nextInt(this.minMaxWaitTime.min, this.minMaxWaitTime.max);
		while(!this.waitUntil() 
				&& this.canRun()
				&& !this.ctx.isScriptPausing()
				&& this.ctx.controller.script().getRuntime() <= waitUntil) {
			this.ctx.antiPattern.runPattern();
			Condition.sleep(POLL_DELAY);
		}
		Condition.sleep(Random.getDelay());
		if(!this.waitUntil())
			return false;
		return true;
	}
}
