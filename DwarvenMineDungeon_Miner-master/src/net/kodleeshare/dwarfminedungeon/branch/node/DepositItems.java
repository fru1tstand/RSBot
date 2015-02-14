package net.kodleeshare.dwarfminedungeon.branch.node;

import net.kodleeshare.dwarfminedungeon.Vars;
import net.kodleeshare.dwarfminedungeon.branch.MineRocksProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Random;

public class DepositItems extends Node
{

	@Override
	public boolean activate()
	{
		return (DepositBox.isOpen());
	}

	@Override
	public void execute()
	{
		Global.setStatus("Banking: deposit all inventory");

		final int prevLen = DepositBox.getItems().length;
		Vars.count_stored = Vars.getCurrentCount();
		Vars.count_inventory = 0;
		Vars.count_start = 0;
		// DepositBox.deposit(453, Amount.ALL);
		DepositBox.depositInventory();
		if (!Utils.waitFor(new Cond()
		{

			@Override
			public boolean accept()
			{
				return (DepositBox.getItems().length != prevLen);
			}
		}, Random.nextInt(1500, 2000)))
			return;

		if (MineRocksProcess.nextRock != null)
			Utils.walkToTile(MineRocksProcess.nextRock.getLocation().randomize(3, 3));
		else
			DepositBox.close();
	}

}
