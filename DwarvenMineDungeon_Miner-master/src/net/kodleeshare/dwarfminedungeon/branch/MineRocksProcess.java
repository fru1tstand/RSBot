package net.kodleeshare.dwarfminedungeon.branch;

import net.kodleeshare.generic.Global;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class MineRocksProcess extends Branch
{
	public static int[]			id_rocks			= new int[0];
	public static int[]			id_rocks_inventory	= new int[0];
	public static SceneObject	currentRock			= null;
	public static SceneObject	nextRock			= null;

	public MineRocksProcess(Node[] nodes)
	{
		super(nodes);
	}

	@Override
	public boolean branch()
	{
		return ((!Inventory.isFull()) && (!Players.getLocal().isInCombat()));
	}

	public static final void setNewRock()
	{
		if (MineRocksProcess.currentRock != null
				&& MineRocksProcess.currentRock.validate())
			return;
		if (MineRocksProcess.id_rocks.length == 0)
		{
			Global.setStatus("No rocks selected to mine!");
			return;
		}
		MineRocksProcess.currentRock = SceneEntities.getNearest(MineRocksProcess.id_rocks);
		MineRocksProcess.nextRock = MineRocksProcess.getSecondNearestRock();
	}

	public static final SceneObject getSecondNearestRock()
	{
		SceneObject someObject = SceneEntities.getNearest(new Filter<SceneObject>()
		{
			@Override
			public boolean accept(SceneObject t)
			{
				for (int i : MineRocksProcess.id_rocks)
				{
					if (i == t.getId())
						return (!t.equals(MineRocksProcess.currentRock));
				}
				return false;
			}
		});
		return someObject;
	}
}
