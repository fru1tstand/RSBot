package net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;

import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.SynchronousModule;
import net.kodleeshare.rsbot.script.modulescript.module.packages.BiasedPackage;
import net.kodleeshare.rsbot.script.modulescript.module.packages.ConditionalPackage;
import net.kodleeshare.rsbot.script.modulescript.module.packages.RepeatedPackage;
import net.kodleeshare.system.Utilities.MinMaxPair;

public class MineClay extends RepeatedPackage<Module> {
	private static final int MINING_ANIMATION = 625;
	private static final MinMaxPair MINE_ROCK_TIME = new MinMaxPair(10000, 11000);
	private static final MinMaxPair CLICK_TIME = new MinMaxPair(100, 200);
	private static final MinMaxPair HOVER_TIME = new MinMaxPair(300, 500);
	
	private static final String MENU_MINECLAY = "Mine <col=ffff>Clay rock";
	private static final String ROCK_INTERACT = "Mine";
	
	private final MineClaySettings settings;
	
	public MineClay(ClientContext ctx) {
		super(ctx);
		
		this.settings = new MineClaySettings();
		
		this
		//Select next rock
		.install(new Module(this.ctx) {
			@Override protected boolean run() {
				settings.newRock();
				return true;
			}
			@Override public boolean canRun() { return true; }
		})
		//Hover this rock
		.install(new BiasedPackage<Module>(this.ctx) {
			{
				this
				//Actually hover
				.install(new SynchronousModule(this.ctx, HOVER_TIME) {
					@Override protected void syncRun() {
						Rock nextRock = settings.getRock();
						if(!this.ctx.objects
								.select()
								.at(nextRock.location)
								.id(nextRock.id)
								.isEmpty())
							this.ctx.objects.peek().hover();
					}
					@Override protected boolean waitFor() { return true; }
					@Override protected boolean waitUntil() { return true; }
					@Override public boolean canRun() { return true;}
				})
				//Meh... Too lazy
				.install(new Module(this.ctx) {
					@Override protected boolean run() { return true; }
					@Override public boolean canRun() { return true; }
				})
				;
			}
			@Override public boolean canRun() { return true; }
		})
		//Wait until mining is finished
		.install(new SynchronousModule(this.ctx, MINE_ROCK_TIME) {
			@Override protected void syncRun() { }
			@Override protected boolean waitFor() { return true; }
			@Override protected boolean waitUntil() {
				return this.ctx.objects.select().at(settings.getRock().location).id(settings.getRock().id).isEmpty()
						|| this.ctx.players.local().animation() != MINING_ANIMATION;
			}
			@Override public boolean canRun() {
				return this.ctx.players.local().animation() == MINING_ANIMATION;
			}
		})
		//Click this rock
		.install(new ConditionalPackage<Module>(this.ctx) {
			{
				this
				//Already hovering, just click
				.install(new SynchronousModule(this.ctx, CLICK_TIME) {
					@Override protected void syncRun() {
						this.ctx.input.click(true);
					}
					@Override protected boolean waitFor() {
						return this.ctx.players.local().animation() == MINING_ANIMATION;
					}
					@Override protected boolean waitUntil() { return true; }
					@Override public boolean canRun() {
						return this.ctx.menu.items()[0].equals(MENU_MINECLAY);
					}
				})
				.install(new SynchronousModule(this.ctx, CLICK_TIME) {
					@Override protected void syncRun() {
						this.ctx.objects.select().at(settings.getRock().location).id(settings.getRock().id).peek().interact(ROCK_INTERACT);
					}
					@Override protected boolean waitFor() {
						return this.ctx.players.local().animation() == MINING_ANIMATION;
					}
					@Override protected boolean waitUntil() { return true; }
					@Override public boolean canRun() {
						return !this.ctx.objects.select().at(settings.getRock().location).id(settings.getRock().id).isEmpty();
					}
				})
				;
			}
			@Override public boolean canRun() { return true; }
		})
		;
	}

	@Override
	public boolean canRun() {
		return WalkToRocks.CAVE_ROCK_AREA.contains(this.ctx.players.local())
				&& this.ctx.backpack.select().count() != 28;
	}
	
	private enum Rock {
		NORTH(new Tile(2274, 4526), 67008),
		SOUTH(new Tile(2274, 4524), 67007);
		
		private final Tile location;
		private final int id;
		private Rock(Tile location, int id) {
			this.location = location;
			this.id = id;
		}
	}
	
	private static class MineClaySettings {
		private Rock rock;
		public final void newRock() {
			if(this.rock == null) {
				this.rock = (Random.nextBoolean() ? Rock.NORTH : Rock.SOUTH);
			} else {
				this.rock = (this.rock == Rock.NORTH ? Rock.SOUTH : Rock.NORTH);
			}
		}
		public final Rock getRock() {
			return this.rock;
		}
	}
}
