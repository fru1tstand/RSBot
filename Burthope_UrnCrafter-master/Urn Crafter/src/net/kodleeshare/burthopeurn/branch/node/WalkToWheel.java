package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.branch.FormUrnProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class WalkToWheel extends Node
{
	private static Tile	tileToWalkTo	= null;

	@Override
	public boolean activate()
	{
		SceneObject theWheel = SceneEntities.getNearest(FormUrnProcess.ID_WHEEL);
		return (theWheel == null || Calculations.distanceTo(theWheel) > 3);
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Walking to spinner");
		WalkToWheel.tileToWalkTo = Utils.walkToArea(FormUrnProcess.AREA_WALKTO_WHEEL, WalkToWheel.tileToWalkTo);
	}

}
