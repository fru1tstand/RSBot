package net.kodleeshare.rsbot.scripts;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.rt6.Menu;
import org.powerbot.script.rt6.Menu.Command;

import net.kodleeshare.rsbot.script.ModuleScript;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.patterns.AntiPattern;
import net.kodleeshare.rsbot.script.modulescript.patterns.SequentialPattern;
import net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.Banking;
import net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages.MineClay;

@Manifest(description = "A testing script", name = "A script", properties = "client=6")
public class BurthopeSoftClayAndUrns extends ModuleScript<SequentialPattern<Module>> {
	public static final Tile OUTSIDE_TILE_SE = new Tile(2910, 3482);
	public static final Tile OUTSIDE_TILE_NW = new Tile(2878, 3544);
	public static final Tile CAVE_TILE_NE = new Tile(2295, 4535);
	public static final Tile CAVE_TILE_SW = new Tile (2240, 4490);
	public static final Area OUTSIDE_AREA = new Area(OUTSIDE_TILE_SE, OUTSIDE_TILE_NW);
	public static final Area CAVE_AREA = new Area(CAVE_TILE_NE, CAVE_TILE_SW);
	
	public BurthopeSoftClayAndUrns() {
		this.ctx.logger.debug = true;
		this.script = new SequentialPattern<Module>(this.ctx) {
			{
				//this.install(new Banking(this.ctx));
				this.install(new MineClay(this.ctx));
			}
			@Override
			public boolean canRun() {
				return true;
			}
		};
		
		this.readyToStart = true;
	}
	
	@Override
	protected void onStart() {
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AntiPattern getAntiPatternInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
