package net.kodleeshare.dwarfminedungeon.branch.node;

import net.kodleeshare.dwarfminedungeon.Vars;
import net.kodleeshare.dwarfminedungeon.branch.BankProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class WalkToBank extends Node
{
	public static Tile	tileToWalkTo	= null;

	@Override
	public boolean activate()
	{
		SceneObject theBox = DepositBox.getNearest();
		return ((theBox == null
				|| !theBox.validate() || (!theBox.isOnScreen() && (Calculations.distance(theBox, Players.getLocal()) > Global.getDistanceTolerance()))) && !DepositBox.isOpen());
	}

	@Override
	public void execute()
	{
		Global.setStatus("Walking to deposit box");
		SceneObject theBox = DepositBox.getNearest();
		if (theBox == null
				|| !theBox.validate())
			WalkToBank.tileToWalkTo = Utils.walkToArea(BankProcess.AREA_WALKTO, WalkToBank.tileToWalkTo);
		else
			Utils.walkToTile(theBox.getLocation().randomize(3, 3));
		Vars.getCurrentCount();
	}

}
