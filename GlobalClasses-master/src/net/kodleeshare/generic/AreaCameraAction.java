package net.kodleeshare.generic;

import java.util.ArrayList;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Locatable;

/**
 * 
 * @author Kodlee
 * @version 1.1
 */
public class AreaCameraAction
{
	private static ArrayList<AreaCameraAction>	allActions		= new ArrayList<AreaCameraAction>();

	private int[]								yaw				= { 0, 0 };							// min - max
	private int[]								pitch			= { 0, 0 };							// min - max
	private ArrayList<Area>						activateArea	= new ArrayList<Area>();				// areas

	/**
	 * 
	 * @param minYaw
	 *            Minimum yaw angle (negative if past 0 degress)
	 * @param maxYaw
	 *            Maximum yaw angle
	 * @param minPitch
	 *            Minimum pitch angle
	 * @param maxPitch
	 *            Maximum pitch angles
	 * @param areas
	 *            Areas for this camera to be active in
	 */
	public AreaCameraAction(int minYaw, int maxYaw, int minPitch, int maxPitch, Area... areas)
	{
		this.yaw = new int[] { minYaw, maxYaw };
		this.pitch = new int[] { minPitch, maxPitch };
		for (Area iArea : areas)
		{
			this.activateArea.add(iArea);
		}
	}

	public boolean isInArea(Locatable tile)
	{
		for (Area anArea : this.activateArea)
		{
			if (anArea.contains(tile))
				return true;
		}
		return false;
	}

	public void execute()
	{
		int theRoll = Random.nextInt(1, 4);
		if (theRoll == 1)
		{
			randomAngle();
		} else if (theRoll == 2)
		{
			randomPitch();
		} else
		{
			if (Random.nextInt(1, 3) == 1)
			{
				randomAngle();
			} else
			{
				randomPitch();
			}
		}
	}

	public void randomAngle()
	{
		Camera.setAngle(Random.nextInt(this.yaw[0], this.yaw[1] + 1));
	}

	public void randomPitch()
	{
		Camera.setPitch(Random.nextInt(this.pitch[0], this.pitch[1] + 1));
	}

	public void align()
	{
		if (Random.nextInt(0, 2) == 0)
		{
			if (Camera.getYaw() < this.yaw[0]
					|| Camera.getYaw() > this.yaw[1])
				this.randomAngle();
			if (Camera.getPitch() < this.pitch[0]
					|| Camera.getPitch() > this.pitch[1])
				this.randomPitch();
		} else
		{
			if (Camera.getPitch() < this.pitch[0]
					|| Camera.getPitch() > this.pitch[1])
				this.randomPitch();
			if (Camera.getYaw() < this.yaw[0]
					|| Camera.getYaw() > this.yaw[1])
				this.randomAngle();
		}
	}

	public boolean isInView(int yaw, int angle)
	{
		return ((yaw >= this.yaw[0])
				&& (yaw <= this.yaw[1])
				&& (angle >= this.pitch[0]) && (angle <= this.pitch[1])) ? true : false;
	}

	public static void provide(AreaCameraAction... actions)
	{
		for (AreaCameraAction iACA : actions)
			AreaCameraAction.allActions.add(iACA);
	}

	public static void rollForAction(int odds, int outof)
	{
		if (odds > Random.nextInt(1, outof + 1))
			return;
		ArrayList<AreaCameraAction> iActions = AreaCameraAction.getAllAvailable();
		if (iActions.size() == 0)
			return;
		iActions.get(Random.nextInt(0, iActions.size() - 1)).execute();
	}

	public static void rollForAngle(int odds, int outof)
	{
		if (odds > Random.nextInt(1, outof + 1))
			return;
		ArrayList<AreaCameraAction> iActions = AreaCameraAction.getAllAvailable();
		if (iActions.size() == 0)
			return;
		iActions.get(Random.nextInt(0, iActions.size() - 1)).randomAngle();
	}

	public static void rollForPitch(int odds, int outof)
	{
		if (odds > Random.nextInt(1, outof + 1))
			return;
		ArrayList<AreaCameraAction> iActions = AreaCameraAction.getAllAvailable();
		if (iActions.size() == 0)
			return;
		iActions.get(Random.nextInt(0, iActions.size() - 1)).randomPitch();
	}

	public static ArrayList<AreaCameraAction> getAllAvailable()
	{
		ArrayList<AreaCameraAction> actionsToChooseFrom = new ArrayList<AreaCameraAction>();
		for (AreaCameraAction anAction : AreaCameraAction.allActions)
		{
			if (anAction.isInArea(Players.getLocal().getLocation()))
				actionsToChooseFrom.add(anAction);
		}
		return actionsToChooseFrom;
	}

	public static boolean isWithinView()
	{
		ArrayList<AreaCameraAction> actions = AreaCameraAction.getAllAvailable();
		if (actions.size() == 0)
			return true;
		for (AreaCameraAction anAction : actions)
		{
			if (anAction.isInView(Camera.getYaw(), Camera.getPitch()))
				return true;
		}
		return false;
	}

	public static void forceAlignment()
	{
		if (AreaCameraAction.isWithinView())
			return;
		ArrayList<AreaCameraAction> actionToChooseFrom = AreaCameraAction.getAllAvailable();
		if (actionToChooseFrom.size() == 0)
			return;
		AreaCameraAction theAction = actionToChooseFrom.get(Random.nextInt(0, actionToChooseFrom.size()));
		theAction.align();
	}
}
