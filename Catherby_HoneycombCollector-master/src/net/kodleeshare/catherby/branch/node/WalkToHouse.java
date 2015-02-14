package net.kodleeshare.catherby.branch.node;

import net.kodleeshare.catherby.branch.GetInsectRepellent;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;

public class WalkToHouse extends Node
{

	@Override
	public boolean activate()
	{
		return !GetInsectRepellent.AREA_INSIDEHOUSE.contains(Players.getLocal())
				&& GetInsectRepellent.TILE_DOOR.distanceTo() > 6;
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Walking to da houseee");
		Utils.walkToTile(GetInsectRepellent.TILE_DOOR);
	}

}
