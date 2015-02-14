package net.kodleeshare.branch;

import net.kodleeshare.Vars;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class TanHidesProcess extends Branch
{
	public static final Area	AREA_WALKTO_TANNER	= new Area(new Tile(2889, 3499, 0), new Tile(2885, 3503, 0));
	public static final int		ID_TANNER			= 14877;

	public TanHidesProcess(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return (Inventory.contains(new int[] { Vars.ID_DRAGONHIDE }));
	}

}
