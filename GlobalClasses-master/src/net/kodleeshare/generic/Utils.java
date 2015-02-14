package net.kodleeshare.generic;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * 
 * @author Kodlee
 * @version 1.3
 */
public class Utils
{
	/**
	 * Walks to a random point within an area. Usage: Have a class variable of type Tile, then set that tile to equal
	 * walkToArea as well as place it within the parameter
	 * 
	 * @param a
	 *            The area to walk to
	 * @param t
	 *            The destination tile that's currently set
	 * @param location
	 *            The name of the destination
	 * @author Fru1tstanD
	 */
	public static Tile walkToArea(Area a, Tile t)
	{
		if (a == null)
			return null;
		Utils.checkAndSetRun();
		AreaCameraAction.forceAlignment();
		if (t == null
				|| !a.contains(t))
			t = Utils.getRandomTileInArea(a);
		if (!Players.getLocal().isMoving()
				|| ((Calculations.distance(Walking.getDestination(), Players.getLocal().getLocation()) < (1 + Math.random() * 9)) && (Calculations.distance(t, Players.getLocal().getLocation()) > 10)))
		{
			t = Utils.getRandomTileInArea(a);
			Walking.walk(t);
		}
		return t;
	}

	/**
	 * Walks to a tile
	 * 
	 * @param t
	 *            Tile to walk to
	 * @author Fru1tstanD
	 */
	public static void walkToTile(Tile t)
	{
		if (t == null
				|| new Tile(0, 0, 0).equals(t))
			return;
		Utils.checkAndSetRun();
		AreaCameraAction.forceAlignment();
		if (!Players.getLocal().isMoving()
				|| ((Calculations.distance(Walking.getDestination(), Players.getLocal().getLocation()) < (1 + Math.random() * 9)) && (Calculations.distance(t, Players.getLocal().getLocation()) > 10)))
		{
			Walking.walk(t);
		}
	}

	public static void checkAndSetRun()
	{
		if (!Walking.isRunEnabled())
		{
			if (Walking.getEnergy() > Global.energyThreshold)
			{
				Walking.setRun(true);
				Global.energyThreshold = Random.nextInt(25, 90);
			}
		}
	}

	/**
	 * 
	 * @author P3aches
	 * 
	 */
	public interface Cond
	{
		public boolean accept();
	}

	/**
	 * 
	 * @param condition
	 *            The condition to validate to
	 * @param timerLength
	 *            Time before false is given
	 * @return
	 * @author P3aches
	 */
	public static boolean waitFor(final Cond condition, int timerLength)
	{
		final Timer t = new Timer(timerLength);
		while (t.isRunning())
		{
			if (condition.accept())
				return true;
			Task.sleep(100, 150);
		}
		return false;
	}

	/**
	 * 
	 * @param a
	 *            The area to pick a tile from
	 * @return The tile chosen
	 */
	public static Tile getRandomTileInArea(Area a)
	{
		return a.getTileArray()[Random.nextInt(0, a.getTileArray().length)];
	}

	public static final int	SETTING_PD	= 1173;
	public static final int	PD_OFF		= 1879048192;

	public static boolean toggleProductionDialog(boolean state)
	{
		boolean isOff = (Settings.get(Utils.SETTING_PD) == Utils.PD_OFF);
		if (isOff
				&& !state)
			return true;
		if (!isOff
				& state)
			return true;

		Global.updateStatus("Toggling production dialog");

		Widgets.get(548, 43).click(false);
		Task.sleep(300, 500);
		if (!Utils.waitFor(new Cond()
		{
			@Override
			public boolean accept()
			{
				for (String s : Menu.getActions())
					if (s.equals("Toggle Production Dialog"))
						return true;
				return false;
			}
		}, Random.nextInt(1000, 1400)))
			return false;
		final int before = Settings.get(Utils.SETTING_PD);
		Menu.select("Toggle Production Dialog");
		if (!Utils.waitFor(new Cond()
		{
			@Override
			public boolean accept()
			{
				return before != Settings.get(Utils.SETTING_PD);
			}
		}, Random.nextInt(1000, 1200)))
			return false;
		return true;
	}
}
