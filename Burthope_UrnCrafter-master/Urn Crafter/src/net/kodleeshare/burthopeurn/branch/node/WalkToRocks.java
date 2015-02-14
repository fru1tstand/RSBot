package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.burthopeurn.branch.MineClayProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class WalkToRocks extends Node
{
	private static Tile	tileToWalkTo	= null;

	@Override
	public boolean activate()
	{
		return (!MineClayProcess.AREA_WALKTO_MINEROCK.contains(Players.getLocal().getLocation()));
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Walking to clay rocks");
		if (Vars.AREA_ACTIVE.contains(Players.getLocal().getLocation())
				&& !(MineClayProcess.AREA_WALKTO_CAVEENTRANCE.contains(Players.getLocal().getLocation())))
		{
			tileToWalkTo = Utils.walkToArea(MineClayProcess.AREA_WALKTO_CAVEENTRANCE, WalkToRocks.tileToWalkTo);
		} else if (MineClayProcess.AREA_WALKTO_CAVEENTRANCE.contains(Players.getLocal().getLocation()))
		{
			Global.status = "Entering cave";
			SceneObject theEntrance = SceneEntities.getNearest(MineClayProcess.ID_CAVEENTRANCE);
			if (theEntrance == null
					|| !theEntrance.validate())
				return;
			if (!theEntrance.isOnScreen())
				Camera.turnTo(theEntrance);
			theEntrance.interact("Enter");
			Utils.waitFor(new Cond()
			{
				public boolean accept()
				{
					return MineClayProcess.AREA_ACTIVE.contains(Players.getLocal().getLocation());
				}
			}, Random.nextInt(1000, 2000));
		} else if (MineClayProcess.AREA_ACTIVE.contains(Players.getLocal().getLocation()))
		{
			tileToWalkTo = Utils.walkToArea(MineClayProcess.AREA_WALKTO_MINEROCK, tileToWalkTo);
		}
	}
}
