package net.kodleeshare.catherby.branch.node;

import net.kodleeshare.catherby.branch.BankItems;
import net.kodleeshare.catherby.branch.GetInsectRepellent;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Bank.Amount;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

public class DepositItems extends Node
{

	@Override
	public boolean activate()
	{
		return BankItems.AREA_BANK.contains(Players.getLocal());
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Banking - Opening bank");
		if (!Bank.isOpen())
			Bank.open();
		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Bank.isOpen();
			}
		}, Random.nextInt(1200, 1500)))
			return;
		Global.updateStatus("Banking - Depositing items");
		while (Inventory.getCount() > 1)
		{
			for (Item i : Inventory.getAllItems(false))
			{
				if (i == null
						|| Inventory.getCount(i.getId()) == 0
						|| i.getId() == GetInsectRepellent.ID_REPELLENT)
					continue;
				Global.updateStatus("Banking - Depsiting "
						+ i.getName());
				final int pc = Inventory.getCount();
				Bank.deposit(i.getId(), Amount.ALL);
				Utils.waitFor(new Cond()
				{
					public boolean accept()
					{
						return pc != Inventory.getCount();
					}
				}, Random.nextInt(400, 600));
				break;
			}
		}
	}

}
