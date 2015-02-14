package net.kodleeshare.branch;

import net.kodleeshare.Vars;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class BankProcess extends Branch
{
	public static final Area	AREA_WALKTO_BANK	= new Area(new Tile(2891, 3531, 0), new Tile(2895, 3528, 0));

	public BankProcess(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return (!Inventory.contains(new int[] { Vars.ID_DRAGONHIDE }));
	}

}
