package net.kodleeshare.catherby.branch;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class GetInsectRepellent extends Branch
{
	public static final Area	AREA_INSIDEHOUSE	= new Area(new Tile(2804, 3455, 0), new Tile(2810, 3446, 0));
	public static final Tile	TILE_DOOR			= new Tile(2805, 3452, 0);
	public static final Tile	TILE_TABLEREPELLENT	= new Tile(2807, 3450, 0);
	public static final int		ID_DOOR_CLOSED		= 1533;
	public static final int		ID_DOOR_OPEN		= 1534;
	public static final int		ID_REPELLENT		= 28;

	public GetInsectRepellent(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return !Inventory.contains(28);
	}

}
