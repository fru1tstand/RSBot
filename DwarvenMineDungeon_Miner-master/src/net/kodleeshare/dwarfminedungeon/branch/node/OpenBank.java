package net.kodleeshare.dwarfminedungeon.branch.node;

import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class OpenBank extends Node
{

	@Override
	public boolean activate()
	{
		SceneObject theBox = DepositBox.getNearest();
		return ((!DepositBox.isOpen()
				&& (theBox != null) && (theBox.validate())) && (theBox.isOnScreen() || Calculations.distance(theBox, Players.getLocal()) <= Global.getDistanceTolerance()));
	}

	@Override
	public void execute()
	{
		SceneObject theBox = DepositBox.getNearest();
		Global.setStatus("Banking: opening deposit box");
		if (!theBox.isOnScreen())
			Camera.turnTo(theBox);
		theBox.interact("Deposit");
		if (!Utils.waitFor(new Cond()
		{

			@Override
			public boolean accept()
			{
				return DepositBox.isOpen();
			}
		}, Random.nextInt(2500, 3000)))
			return;
	}

}
