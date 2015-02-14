package net.kodleeshare.branch.node;

import net.kodleeshare.branch.TanHidesProcess;
import net.kodleeshare.generic.Utils;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

public class WalkToTanner extends Node
{
	private static Tile	tileToWalkTo	= null;

	@Override
	public boolean activate()
	{
		return (!TanHidesProcess.AREA_WALKTO_TANNER.contains(Players.getLocal().getLocation()));
	}

	@Override
	public void execute()
	{
		WalkToTanner.tileToWalkTo = Utils.walkToArea(TanHidesProcess.AREA_WALKTO_TANNER, WalkToTanner.tileToWalkTo, "Tanner");
	}

}
