package net.kodleeshare.generic;

import net.kodleeshare.Vars;
import net.kodleeshare.Wrapper;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * 
 * @author Kodlee
 * @version 1.2
 */
public class Utils
{
	/**
	 * Walks to a random point within an area. Usage: Have a class variable of type Tile, then set that tile to equal
	 * walkToArea as well as place it within the parameter
	 * 
	 * @param a
	 *            The area to walk to4
	 * @param t
	 *            The destination tile that's currently set
	 * @param location
	 *            The name of the destination
	 * @author Fru1tstanD
	 */
	public static Tile walkToArea(Area a, Tile t, String location)
	{
		Utils.checkAndSetRun();
		AreaCameraAction.forceAlignment();
		Wrapper.antiban(false);
		if (t == null)
			t = Utils.getRandomTileInArea(a);
		if (!Players.getLocal().isMoving()
				|| ((Calculations.distance(Walking.getDestination(), Players.getLocal().getLocation()) < (1 + Math.random() * 9)) && (Calculations.distance(t, Players.getLocal().getLocation()) > 10)))
		{
			t = Utils.getRandomTileInArea(a);
			Walking.walk(t);
			Wrapper.updateStatus("Walking to "
					+ location
					+ " "
					+ t.toString());
		}
		Wrapper.antiban(false);
		return t;
	}

	/**
	 * Walks to a tile
	 * 
	 * @param t
	 *            Tile to walk to
	 * @author Fru1tstanD
	 */
	public static void walkToTile(Tile t, String location)
	{
		Utils.checkAndSetRun();
		AreaCameraAction.forceAlignment();
		Wrapper.antiban(false);
		if (!Players.getLocal().isMoving()
				|| ((Calculations.distance(Walking.getDestination(), Players.getLocal().getLocation()) < (1 + Math.random() * 9)) && (Calculations.distance(t, Players.getLocal().getLocation()) > 10)))
		{
			Walking.walk(t);
			Wrapper.updateStatus("Walking to "
					+ location
					+ " "
					+ t.toString());
		}
		Wrapper.antiban(false);
	}

	public static void checkAndSetRun()
	{
		if (!Walking.isRunEnabled())
		{
			if (Walking.getEnergy() > Vars.energyThreshold)
			{
				Walking.setRun(true);
				Vars.energyThreshold = Random.nextInt(25, 90);
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
}
