package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.burthopeurn.branch.MineClayProcess;
import net.kodleeshare.burthopeurn.branch.SoftenClayProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class WalkToWell extends Node
{
	private static Tile	tileToWalkTo	= null;

	@Override
	public boolean activate()
	{
		return (!SoftenClayProcess.AREA_WALKTO_WELL.contains(Players.getLocal().getLocation()));
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Walking to well");
		if (MineClayProcess.AREA_ACTIVE.contains(Players.getLocal().getLocation())
				&& !SoftenClayProcess.AREA_WALKTO_CAVEENTRANCE.contains(Players.getLocal().getLocation()))
		{
			WalkToWell.tileToWalkTo = Utils.walkToArea(SoftenClayProcess.AREA_WALKTO_CAVEENTRANCE, WalkToWell.tileToWalkTo);
		} else if (SoftenClayProcess.AREA_WALKTO_CAVEENTRANCE.contains(Players.getLocal().getLocation()))
		{
			Global.status = "Exiting cave";
			SceneObject theExit = SceneEntities.getNearest(SoftenClayProcess.ID_CAVEENTRANCE);
			if (theExit == null)
				return;
			if (!theExit.isOnScreen())
				Camera.turnTo(theExit);
			theExit.interact("Exit");
			Utils.waitFor(new Cond()
			{
				public boolean accept()
				{
					return Vars.AREA_ACTIVE.contains(Players.getLocal().getLocation())
							&& Camera.getYaw() == 90;
				}
			}, Random.nextInt(1000, 2000));
			WalkToWell.tileToWalkTo = null;
			Task.sleep(300, 500);
		} else if (Vars.AREA_ACTIVE.contains(Players.getLocal().getLocation()))
		{
			tileToWalkTo = Utils.walkToArea(SoftenClayProcess.AREA_WALKTO_WELL, tileToWalkTo);
		}
	}

}
