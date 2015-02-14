package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.branch.MineClayProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class MineClay extends Node
{

	@Override
	public boolean activate()
	{
		return (MineClayProcess.AREA_WALKTO_MINEROCK.contains(Players.getLocal().getLocation()) && !(Players.getLocal().getAnimation() == 17310));
	}

	@Override
	public void execute()
	{
		final SceneObject theRock = SceneEntities.getNearest(new Filter<SceneObject>()
		{
			@Override
			public boolean accept(SceneObject filterObj)
			{
				for (int compareInt : MineClayProcess.ID_ROCKS)
				{
					if (compareInt == filterObj.getId())
					{
						if (MineClayProcess.AREA_WALKTO_MINEROCK.contains(filterObj))
							return true;
					}
				}
				return false;
			}
		});
		if (theRock == null)
			return;
		if (!theRock.isOnScreen())
			Camera.turnTo(theRock);
		Global.updateStatus("Mining clay: click rock");
		if (theRock.contains(Mouse.getLocation())
				&& Menu.getActions()[0].equals("Mine"))
		{
			Mouse.click(true);
		} else
		{
			theRock.interact("Mine");
		}
		Task.sleep(100, 350);
		final SceneObject theEmptyRock = SceneEntities.getNearest(MineClayProcess.ID_ROCKS_MINED);
		if (theEmptyRock != null
				&& Inventory.getCount() < 27)
			theEmptyRock.hover();

		Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return (Players.getLocal().getAnimation() == 17310);
			}
		}, Random.nextInt(1000, 1500));

		Global.updateStatus("Mining clay: waiting for ore...");
		Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return !theRock.validate();
			}
		}, Random.nextInt(6000, 7000));
	}
}
