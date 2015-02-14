package net.kodleeshare.branch.node;

import net.kodleeshare.Vars;
import net.kodleeshare.Wrapper;
import net.kodleeshare.branch.BankProcess;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Bank.Amount;
import org.powerbot.game.api.util.Random;

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
		Vars.status = "Banking: opening";
		if (!Widgets.get(762).validate())
			Bank.open();
		if (!Utils.waitFor(new Cond()
		{

			@Override
			public boolean accept()
			{
				return Widgets.get(762).validate();
			}
		}, Random.nextInt(1500, 2500)))
			return;
		Vars.status = "Banking: deposit all";
		if (Inventory.getCount() > 0)
		{
			Widgets.get(762, 34).click(true);
			if (!Utils.waitFor(new Cond()
			{

				@Override
				public boolean accept()
				{
					return Inventory.getCount() == 0;
				}
			}, Random.nextInt(2000, 3000)))
				return;
		}
		if (Bank.getItemCount(Vars.ID_DRAGONHIDE) < 1)
		{
			Vars.status = "No more hides, stopping script";
			Wrapper.stopScript();
			return;
		}
		Vars.status = "Banking: withdraw dragonhide";
		if (Inventory.getCount() != 28)
		{
			Bank.withdraw(Vars.ID_DRAGONHIDE, Amount.ALL);
			Utils.waitFor(new Cond()
			{

				@Override
				public boolean accept()
				{
					return Inventory.getCount() == 28;
				}
			}, Random.nextInt(2000, 3000));
		}

	}

}
