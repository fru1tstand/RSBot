package net.kodleeshare.branch.node;

import net.kodleeshare.branch.BankProcess;
import net.kodleeshare.generic.Utils;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

public class WalkToBank extends Node
{
	private static Tile	tileToWalkTo	= null;

	@Override
	public boolean activate()
	{
		return (!BankProcess.AREA_WALKTO_BANK.contains(Players.getLocal().getLocation()));
	}

	@Override
	public void execute()
	{
		WalkToBank.tileToWalkTo = Utils.walkToArea(BankProcess.AREA_WALKTO_BANK, tileToWalkTo, "Bank");
	}

}
