package net.kodleeshare.rsbot.scripts.burthopesoftclayandurns;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.packages.SequentialPackage;
import net.kodleeshare.rsbot.scripts.BurthopeSoftClayAndUrns;
import net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages.BankItems;
import net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages.WalkToBank;

public class Banking extends SequentialPackage<Module> {

	public static final Tile CRAFTING_TILE_SE = new Tile(2890, 3497, 0);
	public static final Tile CRAFTING_TILE_NW = new Tile(2878, 3504, 0);
	public static final Tile WELL_TILE_SE = new Tile(2893, 3498, 0);
	public static final Tile WELL_TILE_NW = new Tile(2899, 3492, 0);
	
	public static final Area CRAFTING_AREA = new Area(CRAFTING_TILE_SE, CRAFTING_TILE_NW);
	public static final Area WELL_AREA = new Area(WELL_TILE_SE, WELL_TILE_NW);
	
	public static final int ID_BANK_BOOTH = 25688;
	public static final int ID_BANK_TELLER = 19086;
	
	public Banking(ClientContext ctx) {
		super(ctx);
		this
		.install(new WalkToBank(ctx))
		.install(new BankItems(ctx));
	}
	
	@Override
	public boolean canRun() {
		return BurthopeSoftClayAndUrns.OUTSIDE_AREA.contains(this.ctx.players.local());
	}
}
