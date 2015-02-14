package net.kodleeshare.rsbot.script.modulescript.utilities;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.module.SynchronousModule;
import net.kodleeshare.system.Utilities.MinMaxPair;

public class Travel {
	private static final int WAIT_DISTANCE_MIN = 8;
	private static final int WAIT_DISTANCE_MAX = 17;
	private static final MinMaxPair WAIT_MINMAX_TIME = new MinMaxPair(3000, 3500);
	private static final int REACHABLE_DISTANCE = 20;
	
	public static final SynchronousModule[] getSyncRunModulesFromTiles(ClientContext ctx, 
			final int pathFlux, 
			final Tile...tiles) {
		SynchronousModule[] rms = new SynchronousModule[tiles.length];
		for(int i = 0; i < tiles.length; i++) {
			final int cPointer = i;
			rms[i] = new SynchronousModule(ctx, WAIT_MINMAX_TIME.min, WAIT_MINMAX_TIME.max) {
				private int waitDist;
				private Tile intendedDestination;
				{
					this.waitDist = Random.nextInt(WAIT_DISTANCE_MIN, WAIT_DISTANCE_MAX);
					if(cPointer == tiles.length - 1)
						this.waitDist = 0;
					this.intendedDestination = new Tile(tiles[cPointer].x() + Random.nextInt(-pathFlux/2, pathFlux/2),
							tiles[cPointer].y() + Random.nextInt(-pathFlux/2, pathFlux/2));
				}
				@Override
				protected void syncRun() {
					this.ctx.movement.step(this.intendedDestination);
				}
				@Override
				protected boolean waitFor() {
					return this.ctx.players.local().inMotion();
				}
				@Override
				protected boolean waitUntil() {
					return this.ctx.movement.distance(this.intendedDestination) <= waitDist
							|| !this.ctx.players.local().inMotion()
							|| this.intendedDestination.distanceTo(this.ctx.movement.destination()) > pathFlux;
				}
				@Override
				public boolean canRun() {
					return this.ctx.movement.distance(this.intendedDestination) < REACHABLE_DISTANCE;
				}
			};
		}
		return rms;
	}
}
