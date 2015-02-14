package net.kodleeshare.dwarfminedungeon.branch;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class BankProcess extends Branch
{
	public static final Area	AREA_WALKTO	= new Area(new Tile(1041, 4578, 0), new Tile(1045, 4575, 0));

	public BankProcess(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return (Inventory.isFull() && (!Players.getLocal().isInCombat()));
	}
}
