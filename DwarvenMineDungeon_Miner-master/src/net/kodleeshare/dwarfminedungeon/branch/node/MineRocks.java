package net.kodleeshare.dwarfminedungeon.branch.node;

import net.kodleeshare.dwarfminedungeon.Vars;
import net.kodleeshare.dwarfminedungeon.branch.MineRocksProcess;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;

public class MineRocks extends Node
{

	@Override
	public boolean activate()
	{
		return ((MineRocksProcess.currentRock != null)
				&& (MineRocksProcess.currentRock.validate()) && ((Calculations.distance(MineRocksProcess.currentRock, Players.getLocal()) <= Global.getDistanceTolerance()) || (MineRocksProcess.currentRock.isOnScreen())));
	}

	@Override
	public void execute()
	{
		Global.setStatus("Mining: Click rock");
		if (!MineRocksProcess.currentRock.isOnScreen())
			Camera.turnTo(MineRocksProcess.currentRock);
		if (Menu.getActions()[0].equals("Mine")
				&& MineRocksProcess.currentRock.hover())
			Mouse.click(true);
		else
			MineRocksProcess.currentRock.interact("Mine");
		Global.setStatus("Mining: Wait for animation");
		if (!Utils.waitFor(new Cond()
		{
			@Override
			public boolean accept()
			{
				return (Players.getLocal().getAnimation() != -1);
			}
		}, Random.nextInt(2100, 2900)))
			return;
		Global.setStatus("Mining: Waiting...");
		if (!Utils.waitFor(new Cond()
		{
			@Override
			public boolean accept()
			{
				if (!MineRocksProcess.nextRock.equals(MineRocksProcess.getSecondNearestRock())
						|| !MineRocksProcess.nextRock.getLocation().contains(Mouse.getLocation()))
				{
					MineRocksProcess.nextRock = MineRocksProcess.getSecondNearestRock();
					MineRocksProcess.nextRock.hover();
				}
				return (MineRocksProcess.currentRock == null
						|| !MineRocksProcess.currentRock.validate() || Inventory.isFull());
			}
		}, Random.nextInt(22000, 24000)))
			return;

		if (Skills.getExperience(Skills.MINING) != Vars.xp_start)
			Vars.timer_ttl = new Timer(Math.round((Skills.getExperienceToLevel(Skills.MINING, Skills.getLevel(Skills.MINING) + 1))
					/ (((double) Skills.getExperience(Skills.MINING) - Vars.xp_start) / Global.timer_runtime.getElapsed())));

	}
}
