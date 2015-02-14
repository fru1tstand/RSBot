package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.burthopeurn.branch.BankProcess;
import net.kodleeshare.burthopeurn.branch.FormUrnProcess;
import net.kodleeshare.burthopeurn.branch.SoftenClayProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.UrnProcess;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;
import net.kodleeshare.item.Pickaxe;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Bank.Amount;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

public class BankItems extends Node
{

	@Override
	public boolean activate()
	{
		return (BankProcess.AREA_WALKTO_BANK.contains(Players.getLocal().getLocation()));
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Bank: open");
		if (!Bank.isOpen())
			Bank.open();

		Global.updateStatus("Bank: waiting (open)...");
		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Widgets.get(762).validate();
			}
		}, Random.nextInt(1500, 2100)))
			return;

		UrnProcess currUrn = UrnProcess.getCurrentUrnProcess(Vars.UrnsToMake);
		int urnsMade = Inventory.getCount(currUrn.getUrn().getIDnr());

		Global.updateStatus("Bank: deposit");

		if (Pickaxe.containsNormalPickaxe(Inventory.getItems()))
			while (Inventory.getCount() > ((Pickaxe.containsNormalPickaxe(Inventory.getItems())) ? 1 : 0))
			{
				for (Item theItem : Inventory.getAllItems(false))
				{
					if (theItem == null
							|| Inventory.getCount(theItem.getId()) == 0)
						continue;
					if (!Pickaxe.containsNormalPickaxe(theItem))
					{
						final int prev = Inventory.getCount();
						Bank.deposit(theItem.getId(), Amount.ALL);
						Utils.waitFor(new Cond()
						{
							@Override
							public boolean accept()
							{
								return Inventory.getCount() != prev;
							}
						}, Random.nextInt(400, 600));
						break;
					}
				}

			}
		else
			Bank.depositInventory();

		currUrn.addMade(urnsMade);
		Vars.count_urns += urnsMade;
		Vars.paint_current_urn = currUrn;

		if (UrnProcess.getCurrentUrnProcess(Vars.UrnsToMake) == null)
		{
			if (Widgets.get(762).validate())
				Bank.close();
			Global.stopScript();
		}
		currUrn = UrnProcess.getCurrentUrnProcess(Vars.UrnsToMake);

		if (currUrn != null
				&& currUrn.getHowTo() == UrnProcess.method.BANKCLAY)
		{
			if (Bank.getItemCount(SoftenClayProcess.ID_CLAY) != 0)
				Bank.withdraw(SoftenClayProcess.ID_CLAY, Amount.ALL);
		} else if (currUrn != null
				&& currUrn.getHowTo() == UrnProcess.method.BANKSOFTCLAY)
		{
			if (Bank.getItemCount(FormUrnProcess.ID_SOFTCLAY) != 0)
				Bank.withdraw(FormUrnProcess.ID_SOFTCLAY, Amount.ALL);
		}
		Vars.isBanking = false;
		Vars.updateTtl();
	}

}
