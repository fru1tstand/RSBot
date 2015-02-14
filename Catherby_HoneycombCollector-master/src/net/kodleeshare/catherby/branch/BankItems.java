package net.kodleeshare.catherby.branch;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class BankItems extends Branch
{
	public static final Area	AREA_BANK	= new Area(new Tile(2805, 3442, 0), new Tile(2813, 3437, 0));

	public BankItems(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return Inventory.isFull();
	}

}
