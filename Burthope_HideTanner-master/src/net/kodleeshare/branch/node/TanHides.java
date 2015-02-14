package net.kodleeshare.branch.node;

import net.kodleeshare.Vars;
import net.kodleeshare.branch.TanHidesProcess;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class TanHides extends Node
{

	@Override
	public boolean activate()
	{
		return (TanHidesProcess.AREA_WALKTO_TANNER.contains(Players.getLocal().getLocation()));
	}

	@Override
	public void execute()
	{
		Vars.status = "Tanning: interact with tanner";
		NPC theTanner = NPCs.getNearest(TanHidesProcess.ID_TANNER);
		if (!theTanner.validate())
			return;
		if (!theTanner.isOnScreen())
			Camera.turnTo(theTanner);
		if (!Widgets.get(1370).validate())
			theTanner.interact("Tan hide");
		if (!Utils.waitFor(new Cond()
		{

			@Override
			public boolean accept()
			{
				return Widgets.get(1370).validate();
			}
		}, Random.nextInt(2500, 3500)))
			return;
		Vars.status = "Tanning: tan!";
		Widgets.get(1370, 38).click(true);
		if (!Utils.waitFor(new Cond()
		{

			@Override
			public boolean accept()
			{
				return !Inventory.contains(new int[] { Vars.ID_DRAGONHIDE });
			}
		}, Random.nextInt(2000, 3000)))
			return;
	}

}
