package net.kodleeshare.catherby.branch.node;

import net.kodleeshare.catherby.branch.GetHoneycomb;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class TakeHoneycomb extends Node
{

	@Override
	public boolean activate()
	{
		return GetHoneycomb.AREA_BEEHIVES.contains(Players.getLocal());
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Gathering hunny");
		while (!Inventory.isFull())
		{
			Global.updateStatus("Gathering - Interacting");
			SceneObject s = SceneEntities.getNearest(GetHoneycomb.ID_BEEHIVE);
			if (s == null)
				return;
			if (!s.isOnScreen())
				Camera.turnTo(s);
			s.interact("Take-honey");
			Global.updateStatus("Gathering - Waiting for reset");
			Utils.waitFor(new Cond()
			{
				public boolean accept()
				{
					return Players.getLocal().getAnimation() == -1;
				}
			}, Random.nextInt(600, 800));
			Global.updateStatus("Gathering - Waiting for animation");
			Utils.waitFor(new Cond()
			{
				public boolean accept()
				{
					return Players.getLocal().getAnimation() != -1;
				}
			}, Random.nextInt(300, 500));
		}

		WalkToBeehive.openGate();
	}

}
