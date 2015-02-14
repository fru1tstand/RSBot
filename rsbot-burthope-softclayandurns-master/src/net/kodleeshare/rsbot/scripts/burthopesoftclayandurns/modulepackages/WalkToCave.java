package net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages;

import org.powerbot.script.Tile;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.module.SynchronousModule;
import net.kodleeshare.rsbot.script.modulescript.module.packages.SequentialPackage;
import net.kodleeshare.rsbot.script.modulescript.utilities.Travel;
import net.kodleeshare.rsbot.scripts.BurthopeSoftClayAndUrns;

public class WalkToCave extends SequentialPackage<SynchronousModule> {
	private static int CAVE_PATH_FLUX = 3;
	
	private static Tile[] CAVE_PATH = new Tile[] {
		new Tile(2891, 3530),
		new Tile(2891, 3523),
		new Tile(2887, 3517),
		new Tile(2883, 3509),
		new Tile(2880, 3505)
	};
	
	public WalkToCave(ClientContext ctx) {
		super(ctx);
		this.install(Travel.getSyncRunModulesFromTiles(this.ctx, CAVE_PATH_FLUX, CAVE_PATH));
	}

	@Override
	public boolean canRun() {
		return BurthopeSoftClayAndUrns.OUTSIDE_AREA.contains(this.ctx.players.local()) 
				&& this.ctx.backpack.select().count() != 28;
	}

}
