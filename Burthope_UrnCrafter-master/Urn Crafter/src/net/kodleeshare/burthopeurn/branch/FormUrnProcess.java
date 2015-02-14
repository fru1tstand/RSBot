package net.kodleeshare.burthopeurn.branch;

import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.generic.Urn;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class FormUrnProcess extends Branch
{
	public static final Area	AREA_WALKTO_WHEEL	= new Area(new Tile(2889, 3499, 0), new Tile(2879, 3503, 0));
	public static final int		ID_SOFTCLAY			= 1761;
	public static final int		ID_WHEEL			= 66848;
	public static final int		ID_OVEN				= 66847;

	public FormUrnProcess(Node[] arg0)
	{
		super(arg0);
	}

	@Override
	public boolean branch()
	{
		return ((((Inventory.getCount(FormUrnProcess.ID_SOFTCLAY) > 1) || (Inventory.contains(Urn.getAllIDunf()))) && (!Inventory.contains(new int[] { SoftenClayProcess.ID_CLAY }))) && !Vars.isBanking);
	}

}
