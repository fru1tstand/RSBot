package net.kodleeshare.catherby.branch.node;

import net.kodleeshare.catherby.branch.GetInsectRepellent;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class PickupRepellent extends Node
{

	@Override
	public boolean activate()
	{
		return GetInsectRepellent.AREA_INSIDEHOUSE.contains(Players.getLocal())
				|| GetInsectRepellent.TILE_DOOR.distanceTo() < 6;
	}

	@Override
	public void execute()
	{
		if (!PickupRepellent.openDoor())
			return;

		Global.status = "Attempting to get insect repellent";
		GroundItem repellent = GroundItems.getNearest(GetInsectRepellent.ID_REPELLENT);
		if (repellent == null)
			return;
		if (repellent.getLocation().equals(GetInsectRepellent.TILE_TABLEREPELLENT))
			Mouse.click(repellent.getCentralPoint().x, repellent.getCentralPoint().y - 10, true);
		else
			repellent.click(true);
		if (!Utils.waitFor(new Cond()
		{
			@Override
			public boolean accept()
			{
				return Inventory.contains(GetInsectRepellent.ID_REPELLENT);
			}
		}, Random.nextInt(600, 800)))
			return;

		if (!PickupRepellent.openDoor())
			return;
	}

	public static boolean openDoor()
	{
		Global.status = "Opening door";
		SceneObject theDoor = SceneEntities.getNearest(GetInsectRepellent.ID_DOOR_CLOSED);
		if (theDoor != null)
		{
			Camera.turnTo(theDoor);
			theDoor.interact("Open");
		}
		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return SceneEntities.getNearest(GetInsectRepellent.ID_DOOR_OPEN) != null;
			}
		}, Random.nextInt(400, 600)))
			return false;
		return true;
	}
}
