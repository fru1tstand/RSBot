package net.kodleeshare.rsbot.script.modulescript.module.predefined;

import org.powerbot.script.Locatable;
import org.powerbot.script.rt6.Interactive;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.module.SynchronousModule;
import net.kodleeshare.system.Utilities.MinMaxPair;

public class TurnToSyncModule<T extends Interactive & Locatable> extends SynchronousModule {
	private static final MinMaxPair DEFAULT_TURNTO_TIME = new MinMaxPair(1000, 1200);
	
	protected final TurnToObject<T> thing;
	
	public TurnToSyncModule(ClientContext ctx, TurnToObject<T> thing) {
		this(ctx, DEFAULT_TURNTO_TIME, thing);
	}
	public TurnToSyncModule(ClientContext ctx, MinMaxPair mm, TurnToObject<T> thing) {
		super(ctx, mm);
		this.thing = thing;
	}

	@Override
	protected void syncRun() {
		this.ctx.camera.turnTo(this.thing.poll());
	}

	@Override
	protected boolean waitFor() { return true; }

	@Override
	protected boolean waitUntil() {
		return this.thing.poll().inViewport();
	}

	@Override
	public boolean canRun() {
		return this.thing.poll() != this.thing.nil()
				&& !this.thing.poll().inViewport()
				&& this.thing.canRun();
	}
	
	public static abstract class TurnToObject<T> {
		public abstract T poll();
		public abstract T nil();
		public boolean canRun() { return true; }
	}
}
