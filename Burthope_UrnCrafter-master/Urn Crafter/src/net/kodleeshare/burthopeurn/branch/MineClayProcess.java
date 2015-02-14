package net.kodleeshare.burthopeurn.branch;

import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.generic.Urn;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class MineClayProcess extends Branch
{
	public static final Area	AREA_ACTIVE					= new Area(new Tile(2293, 4505, 0), new Tile(2258, 4529, 0));
	public static final Area	AREA_WALKTO_CAVEENTRANCE	= new Area(new Tile(2875, 3498, 0), new Tile(2880, 3506, 0));
	public static final Area	AREA_WALKTO_MINEROCK		= new Area(new Tile(2271, 4522, 0), new Tile(2276, 4528, 0));
	public static final Tile	TILE_LOC_ROCK				= new Tile(2274, 4525, 0);
	public static final int		ID_CAVEEXIT					= 67002;
	public static final int		ID_CAVEENTRANCE				= 66876;
	public static final int[]	ID_ROCKS					= { 67007, 67008 };
	public static final int[]	ID_ROCKS_MINED				= { 72803, 72804 };

	public MineClayProcess(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return (((MineClayProcess.AREA_ACTIVE.contains(Players.getLocal().getLocation()) || Vars.AREA_ACTIVE.contains(Players.getLocal().getLocation()))
				&& !Inventory.contains(Urn.getAllIDnr())
				&& !Inventory.contains(Urn.getAllIDunf()) && !Inventory.isFull()) && !Vars.isBanking);
	}

}
