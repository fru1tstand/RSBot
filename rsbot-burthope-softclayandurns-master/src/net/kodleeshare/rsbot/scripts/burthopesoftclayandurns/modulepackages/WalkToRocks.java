package net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.SynchronousModule;
import net.kodleeshare.rsbot.script.modulescript.module.packages.SequentialPackage;
import net.kodleeshare.rsbot.script.modulescript.utilities.Travel;
import net.kodleeshare.rsbot.scripts.BurthopeSoftClayAndUrns;
import net.kodleeshare.system.Utilities.MinMaxPair;

public class WalkToRocks extends SequentialPackage<Module> {
	private static final Tile CAVE_ROCK_TILE_SE = new Tile(2280,4520);
	private static final Tile CAVE_ROCK_TILE_NW = new Tile(2265,4535);
	
	public static final Area CAVE_ROCK_AREA = new Area(CAVE_ROCK_TILE_NW, CAVE_ROCK_TILE_SE);
	
	private static final int CAVE_ENTRANCE_OBJECT = 86557;
	private static final String CAVE_ENTRANCE_INTERACT = "Enter";
	private static final int CAVE_ENTRANCE_DISTANCE = 10;
	
	private static final MinMaxPair CAVE_ENTRANCE_TURNTO_TIME = new MinMaxPair(1000, 1200);
	private static final MinMaxPair CAVE_ENTRANCE_INTERACT_TIME = new MinMaxPair(1500, 1700);
	
	private static final int CAVE_ROCK_PATH_FLUX = 3;
	private static final Tile[] CAVE_ROCK_PATH = new Tile[] {
		new Tile(2288, 4515),
		new Tile(2282, 4514),
		new Tile(2274, 4515),
		new Tile(2272, 4520)
	};
	
	/**
	 * Walks to the clay rocks starting from outside the cave
	 */
	public WalkToRocks(ClientContext ctx) {
		super(ctx);
		this
		
		//Turn to see cave
		.install(new SynchronousModule(this.ctx, CAVE_ENTRANCE_TURNTO_TIME) {
			@Override protected void syncRun() {
				this.ctx.camera.turnTo(this.ctx.objects.select().id(CAVE_ENTRANCE_OBJECT).peek());
			}
			@Override protected boolean waitFor() { return true; }
			@Override protected boolean waitUntil() {
				return this.ctx.objects.select().id(CAVE_ENTRANCE_OBJECT).peek().inViewport();
			}
			@Override
			public boolean canRun() {
				int d = this.ctx.movement.distance(this.ctx.objects.select().id(CAVE_ENTRANCE_OBJECT).peek());
				return (d < CAVE_ENTRANCE_DISTANCE && d != -1)
						&& !this.ctx.objects.peek().inViewport();
			}
		})
		
		//Enter cave
		.install(new SynchronousModule(this.ctx, CAVE_ENTRANCE_INTERACT_TIME) {
			@Override protected void syncRun() {
				this.ctx.objects.select().id(CAVE_ENTRANCE_OBJECT).peek().interact(CAVE_ENTRANCE_INTERACT);
			}
			@Override protected boolean waitFor() { return true; }
			@Override protected boolean waitUntil() {
				return BurthopeSoftClayAndUrns.CAVE_AREA.contains(this.ctx.players.local());
			}
			@Override
			public boolean canRun() {
				return this.ctx.objects.select().id(CAVE_ENTRANCE_OBJECT).peek().inViewport();
			}
		})
		
		//Walk to the rocks
		.install(new SequentialPackage<SynchronousModule>(this.ctx) {
			{
				this.install(Travel.getSyncRunModulesFromTiles(this.ctx, CAVE_ROCK_PATH_FLUX, CAVE_ROCK_PATH));
			}
			@Override
			public boolean canRun() {
				return CAVE_ROCK_AREA.contains(this.ctx.players.local());
			}
		})
		;
	}

	@Override
	public boolean canRun() {
		// (Near cave entrance || in cave) && backpack not full
		return (this.ctx.movement.distance(this.ctx.objects.select().id(CAVE_ENTRANCE_OBJECT).poll()) < CAVE_ENTRANCE_DISTANCE
				|| BurthopeSoftClayAndUrns.CAVE_AREA.contains(this.ctx.players.local()))
				&& this.ctx.backpack.select().count() != 28;
	}

}
