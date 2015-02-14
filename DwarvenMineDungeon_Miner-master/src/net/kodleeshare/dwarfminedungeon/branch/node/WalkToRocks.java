package net.kodleeshare.dwarfminedungeon.branch.node;

import net.kodleeshare.dwarfminedungeon.branch.MineRocksProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;

public class WalkToRocks extends Node
{

	@Override
	public boolean activate()
	{
		return ((MineRocksProcess.currentRock != null)
				&& (MineRocksProcess.currentRock.validate())
				&& (Calculations.distance(MineRocksProcess.currentRock, Players.getLocal()) > Global.getDistanceTolerance()) && (!MineRocksProcess.currentRock.isOnScreen()));
	}

	@Override
	public void execute()
	{
		Global.setStatus("Walking to rocks");
		Utils.walkToTile(MineRocksProcess.currentRock.getLocation().randomize(3, 3));
	}

}
