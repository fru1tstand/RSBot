package net.kodleeshare.generic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kodleeshare.paint.mousetrail.BasicLineTrail;
import net.kodleeshare.paint.mousetrail.MouseTrail;
import net.kodleeshare.sql.Sql;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;

public class Global
{

	public static boolean				hasLoaded		= false;
	public static boolean				isStopping		= false;
	private static final List<Node>		jobsCollection	= Collections.synchronizedList(new ArrayList<Node>());
	private static Tree					jobContainer	= null;

	public static final Timer			timer_runtime	= new Timer(0);
	public static Timer					timer_antiban	= new Timer(0);
	public static String				status			= "";
	public static int					energyThreshold	= Random.nextInt(25, 90);
	public static final DecimalFormat	DF				= new DecimalFormat("###,###,###");
	public static final MouseTrail		MOUSE_TRAIL		= new BasicLineTrail(50, 1);

	public static final Sql				SQL_CONN		= new Sql(Sql.Url.SAFECRACK);
	public static final String			USERNAME		= Environment.getDisplayName();

	private static int					distanceTolerance;
	private static Timer				distanceTimer;

	public static final void setRandomAntibanTimer()
	{
		Global.timer_antiban = new Timer(Random.nextInt(0, 900000));
	}

	public static final void setStatus(String status)
	{
		if (!Global.status.equals(status))
			System.out.println(status);
		Global.status = status;
	}

	public static final void updateStatus(String status)
	{
		Global.setStatus(status);
	}

	public static final void antiban(boolean canBeIntrusive)
	{
		if (canBeIntrusive)
		{
			// intrusive antibans here
			return;
		}

		if (!Global.timer_antiban.isRunning())
		{
			AreaCameraAction.rollForAction(1, 10);
			Global.setRandomAntibanTimer();
		}
	}

	public static void setLoaded()
	{
		Global.hasLoaded = true;
	}

	public static boolean hasLoaded()
	{
		return Global.hasLoaded;
	}

	public static void stopScript()
	{
		Global.status = "Stopping script.";
		Global.isStopping = true;
	}

	public static boolean isScriptStopping()
	{
		return Global.isStopping;
	}

	public static final int getDistanceTolerance()
	{
		if (Global.distanceTolerance == 0)
			Global.setNewDistanceTolerance();
		return Global.distanceTolerance;
	}

	private static final void setNewDistanceTolerance()
	{
		if ((Global.distanceTimer != null && Global.distanceTimer.isRunning())
				|| (Global.distanceTolerance == 0))
			return;
		Global.distanceTimer = new Timer(Random.nextInt(1000, 600000));
		Global.distanceTolerance = Random.nextInt(3, 5);
	}

	public static synchronized final void provide(final Node... jobs)
	{
		for (final Node job : jobs)
		{
			if (!jobsCollection.contains(job))
			{
				jobsCollection.add(job);
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}

	public static synchronized final void revoke(final Node... jobs)
	{
		for (final Node job : jobs)
		{
			if (jobsCollection.contains(job))
			{
				jobsCollection.remove(job);
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}

	public static final void submit(ActiveScript wrapper, final Job... jobs)
	{
		for (final Job job : jobs)
		{
			wrapper.getContainer().submit(job);
		}
	}

	public static final Tree getJobContainer()
	{
		return Global.jobContainer;
	}

}
