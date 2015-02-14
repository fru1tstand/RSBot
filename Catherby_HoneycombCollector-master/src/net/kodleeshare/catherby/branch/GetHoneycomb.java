package net.kodleeshare.catherby.branch;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class GetHoneycomb extends Branch
{
	public static final Area	AREA_WALKTOFENCE	= new Area(new Tile(2766, 3441, 0), new Tile(2770, 3445, 0));
	public static final Area	AREA_BEEHIVES		= new Area(new Tile(2767, 3449, 0), new Tile(2752, 3437, 0));
	public static final Area	AREA_INSIDEWALK		= new Area(new Tile(2762, 3446, 0), new Tile(2766, 3440, 0));
	public static final Tile	TILE_FENCE			= new Tile(2766, 3444, 0);
	public static final int		ID_FENCE_CLOSED		= 1551;
	public static final int		ID_FENCE_OPEN		= 1552;
	public static final int		ID_BEEHIVE			= 68;
	public static final int		ID_HONEYCOMB		= 12156;

	public GetHoneycomb(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return Inventory.contains(GetInsectRepellent.ID_REPELLENT)
				&& !Inventory.isFull();
	}

}
