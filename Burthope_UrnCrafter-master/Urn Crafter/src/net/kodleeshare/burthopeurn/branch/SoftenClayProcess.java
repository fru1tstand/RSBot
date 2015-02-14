package net.kodleeshare.burthopeurn.branch;

import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.generic.Urn;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class SoftenClayProcess extends Branch
{
	public static final Area	AREA_WALKTO_CAVEENTRANCE	= new Area(new Tile(2294, 4518, 0), new Tile(2289, 4513, 0));
	public static final Area	AREA_WALKTO_WELL			= new Area(new Tile(2892, 3498, 0), new Tile(2899, 3490, 0));
	public static final int		ID_CAVEENTRANCE				= 67002;
	public static final int		ID_CLAY						= 434;
	public static final int		ID_WELL						= 66664;

	public SoftenClayProcess(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return (!Inventory.contains(Urn.getAllIDnr())
				&& !Inventory.contains(Urn.getAllIDunf())
				&& Inventory.isFull()
				&& Inventory.contains(new int[] { SoftenClayProcess.ID_CLAY }) && !Vars.isBanking);
	}

}
