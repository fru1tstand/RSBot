package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.branch.SoftenClayProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;

public class SoftenClay extends Node
{

	@Override
	public boolean activate()
	{
		return (SoftenClayProcess.AREA_WALKTO_WELL.contains(Players.getLocal().getLocation()) && !Widgets.get(1251).validate());
	}

	@Override
	public void execute()
	{
		Global.status = "Softening clay";
		SceneObject theWell = SceneEntities.getNearest(SoftenClayProcess.ID_WELL);
		if (theWell == null)
			return;
		if (!theWell.isOnScreen())
			Camera.turnTo(theWell);
		Global.status = "Softening clay: click on clay";
		Inventory.selectItem(SoftenClayProcess.ID_CLAY);
		Global.status = "Softening clay: use clay on well";
		if (!Widgets.get(1370).validate())
			theWell.interact("Use");
		final Widget theSoftenClayWidget = Widgets.get(1370);
		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return (theSoftenClayWidget.validate());
			}
		}, Random.nextInt(2500, 3500)))
			return;
		theSoftenClayWidget.getChild(20).click(true);
		Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Widgets.get(1251).validate();
			}
		}, Random.nextInt(1500, 2500));
		Global.status = "Softening clay: waiting (soften)...";
	}

}
