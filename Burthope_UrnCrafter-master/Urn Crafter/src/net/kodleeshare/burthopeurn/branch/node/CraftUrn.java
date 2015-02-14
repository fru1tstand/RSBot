package net.kodleeshare.burthopeurn.branch.node;

import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.burthopeurn.branch.FormUrnProcess;
import net.kodleeshare.generic.AreaCameraAction;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Urn;
import net.kodleeshare.generic.UrnProcess;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;

public class CraftUrn extends Node
{

	@Override
	public boolean activate()
	{
		SceneObject theWheel = SceneEntities.getNearest(FormUrnProcess.ID_WHEEL);
		return (theWheel != null
				&& Calculations.distanceTo(theWheel) <= 3
				&& Inventory.getCount(FormUrnProcess.ID_SOFTCLAY) > 1 && !Widgets.get(1251).validate());
	}

	@Override
	public void execute()
	{
		// 548, 43

		final Urn currentUrn = UrnProcess.getCurrentUrn(Vars.UrnsToMake);
		if (currentUrn == null)
		{
			Global.stopScript();
			return;
		}
		Global.status = "Craftings urns - click wheel";

		SceneObject potWheel = SceneEntities.getNearest(FormUrnProcess.ID_WHEEL);
		if (!Widgets.get(1370).validate())
		{
			if (potWheel == null)
				return;
			if (!potWheel.isOnScreen())
				Camera.turnTo(potWheel);
			potWheel.interact("Form");
		}

		Global.status = "Crafting urns: waiting (widget)...";

		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Widgets.get(1370).validate();
			}
		}, Random.nextInt(3000, 4000)))
			return;

		if (Widgets.get(1370, 73).getText().equals("0"))
		{
			Global.setStatus("Wow. This is awk, Hold on...");
			Widgets.get(1370, 30).click(true);
			if (!Utils.waitFor(new Cond()
			{

				@Override
				public boolean accept()
				{
					return !Widgets.get(1370, 30).isOnScreen();
				}
			}, Random.nextInt(1000, 1400)))
				return;
		}

		final Widget craftingWidget = Widgets.get(1371);
		if (Settings.get(1170) == currentUrn.getIDunf())
		{
			CraftUrn.clickMould();
			return;
		}
		Global.status = "Crafting urns: set type";
		if (Settings.get(1169) != currentUrn.getSettingType())
		{
			craftingWidget.getChild(51).getChild(0).click(true);
			if (!Utils.waitFor(new Cond()
			{
				public boolean accept()
				{
					return craftingWidget.getChild(62).visible();
				}
			}, Random.nextInt(1000, 1500)))
				return;
		}
		if (Settings.get(1170) == currentUrn.getIDunf())
		{
			CraftUrn.clickMould();
			return;
		}
		craftingWidget.getChild(62).getChild(currentUrn.getWidgetType()).click(true);
		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Settings.get(1169) == currentUrn.getSettingType();
			}
		}, Random.nextInt(1500, 2000)))
			return;
		Global.status = "Crafting urn: set strength";
		craftingWidget.getChild(44).getChild(currentUrn.getWidgetStrength()).click(true);
		if (Settings.get(1170) == currentUrn.getIDunf())
		{
			CraftUrn.clickMould();
			return;
		}
		Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return Widgets.get(1251).validate();
			}
		}, Random.nextInt(2000, 3000));
		AreaCameraAction.forceAlignment();
	}

	private static void clickMould()
	{
		Global.status = "Crafting urns: Mould!";
		Widgets.get(1370, 38).click(true);
		Global.status = "Crafting urns: waiting (crafting)...";
		Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return (Widgets.get(1251).validate());
			}
		}, Random.nextInt(2000, 3000));
	}
}
