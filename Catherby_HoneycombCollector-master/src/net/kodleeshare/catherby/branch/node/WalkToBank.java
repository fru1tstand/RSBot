package net.kodleeshare.catherby.branch.node;

import net.kodleeshare.catherby.branch.BankItems;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

public class WalkToBank extends Node
{
	private Tile	ttwt	= null;

	@Override
	public boolean activate()
	{
		return !BankItems.AREA_BANK.contains(Players.getLocal());
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Walking to bank");
		this.ttwt = Utils.walkToArea(BankItems.AREA_BANK, this.ttwt);
	}

}
