package net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages;

import org.powerbot.script.Tile;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.SynchronousModule;
import net.kodleeshare.rsbot.script.modulescript.module.packages.ConditionalPackage;
import net.kodleeshare.rsbot.script.modulescript.module.packages.SequentialPackage;
import net.kodleeshare.rsbot.script.modulescript.utilities.Travel;
import net.kodleeshare.rsbot.scripts.BurthopeSoftClayAndUrns;
import net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.Banking;

public class WalkToBank extends ConditionalPackage<Module> {
	private static final int OUTSIDE_PATH_FLUX = 3;
	private static final int CRAFTING_PATH_FLUX = 3;
	private static final int WELL_PATH_FLUX = 3;
	
	private static final Tile[] OUTSIDE_PATH = {
		new Tile(2892, 3499, 0),
		new Tile(2892, 3507, 0),
		new Tile(2889, 3514, 0),
		new Tile(2890, 3521, 0),
		new Tile(2891, 3528, 0),
		new Tile(2890, 3535, 0)
	};
	private static final Tile[] CRAFTING_PATH = {
		new Tile(2892, 3506, 0),
		new Tile(2889, 3514, 0),
		new Tile(2890, 3521, 0),
		new Tile(2891, 3528, 0),
		new Tile(2890, 3535, 0)
	};
	private static final Tile[] WELL_PATH = {
		new Tile(2892, 3499, 0),
		new Tile(2892, 3507, 0),
		new Tile(2889, 3514, 0),
		new Tile(2890, 3521, 0),
		new Tile(2891, 3528, 0),
		new Tile(2890, 3535, 0)
	};
	
	public WalkToBank(ClientContext ctx) {
		super(ctx);
		
		//From crafting area to bank
		this
		.install(new SequentialPackage<Module>(this.ctx) {
			{
				this.install(Travel.getSyncRunModulesFromTiles(this.ctx, CRAFTING_PATH_FLUX, CRAFTING_PATH));
			}
			@Override public boolean canRun() {
				return Banking.CRAFTING_AREA.contains(this.ctx.players.local());
			}
		})
		
		//From well to bank
		.install(new SequentialPackage<Module>(this.ctx) {
			{
				this.install(Travel.getSyncRunModulesFromTiles(this.ctx, WELL_PATH_FLUX, WELL_PATH));
			}
			@Override public boolean canRun() {
				return Banking.WELL_AREA.contains(this.ctx.players.local());
			}
		})
		
		//All else has failed, catch all other locations and put on path to bank
		.install(new SequentialPackage<Module>(this.ctx) {
			{
				this.install(Travel.getSyncRunModulesFromTiles(this.ctx, OUTSIDE_PATH_FLUX, OUTSIDE_PATH));
			}
			@Override public boolean canRun() {
				return BurthopeSoftClayAndUrns.OUTSIDE_AREA.contains(this.ctx.players.local());
			}
		})
		;
	}
	
	@Override
	public boolean canRun() {
		int d = this.ctx.movement.distance(this.ctx.npcs.select().id(Banking.ID_BANK_TELLER).peek());
		return BurthopeSoftClayAndUrns.OUTSIDE_AREA.contains(this.ctx.players.local())
				&&  (d > BankItems.VIEW_DISTANCE || d == -1);
	}
}
