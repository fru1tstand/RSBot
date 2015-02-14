package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.branch.FormUrnProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Urn;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class FireUrn extends Node
{

	@Override
	public boolean activate()
	{
		return (Inventory.getCount(FormUrnProcess.ID_SOFTCLAY) < 2
				&& Inventory.contains(Urn.getAllIDunf()) && !Widgets.get(1251).validate());
	}

	@Override
	public void execute()
	{
		Global.status = "Crafting urns - click on oven";
		SceneObject theFurnace = SceneEntities.getNearest(FormUrnProcess.ID_OVEN);
		if (theFurnace == null)
			return;
		if (!theFurnace.isOnScreen())
			Camera.turnTo(theFurnace);
		if (!Widgets.get(1370).validate())
			theFurnace.interact("Use");
		Global.status = "Crafting urns: waiting (widget)...";
		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Widgets.get(1370).validate();
			}
		}, Random.nextInt(2500, 3000)))
			return;
		Global.status = "Crafting urns: Fire!";
		Widgets.get(1370, 38).click(true);
		Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Widgets.get(1251).validate();
			}
		}, Random.nextInt(2000, 3000));
		Global.status = "Crafting urns: waiting (firing)...";
	}

}
