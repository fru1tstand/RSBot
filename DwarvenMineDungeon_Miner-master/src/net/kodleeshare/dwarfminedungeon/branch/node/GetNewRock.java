package net.kodleeshare.dwarfminedungeon.branch.node;

import net.kodleeshare.dwarfminedungeon.branch.MineRocksProcess;
import net.kodleeshare.generic.Global;

import org.powerbot.core.script.job.state.Node;

public class GetNewRock extends Node
{

	@Override
	public boolean activate()
	{
		return (MineRocksProcess.currentRock == null || !MineRocksProcess.currentRock.validate());
	}

	@Override
	public void execute()
	{
		Global.setStatus("Mining: Select new rock");
		MineRocksProcess.setNewRock();
	}

}
