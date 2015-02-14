package net.kodleeshare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kodleeshare.branch.BankProcess;
import net.kodleeshare.branch.TanHidesProcess;
import net.kodleeshare.branch.node.BankItems;
import net.kodleeshare.branch.node.TanHides;
import net.kodleeshare.branch.node.WalkToBank;
import net.kodleeshare.branch.node.WalkToTanner;
import net.kodleeshare.generic.AreaCameraAction;
import net.kodleeshare.paint.PaintHandler;
import net.kodleeshare.paint.PaintVars;
import net.kodleeshare.paint.mouse.BasicCircle;
import net.kodleeshare.paint.mouse.BasicCross;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Fru1tstanD" }, name = "Burthope Hide Tanner", description = "Tans hides in Burthope. Really only does Blue Dragonhide at the moment", version = 0.9)
public class Wrapper extends ActiveScript implements PaintListener, MouseListener
{
	// Our global vars 'n stuffs
	private static boolean			hasLoaded		= false;
	private static boolean			isStopping		= false;

	public static final Manifest	SCRIPT_DATA		= Wrapper.class.getAnnotation(Manifest.class);
	private final List<Node>		jobsCollection	= Collections.synchronizedList(new ArrayList<Node>());
	private Tree					jobContainer	= null;

	@Override
	public void onStart()
	{
		Wrapper.updateStatus("Starting script");
		PaintHandler.setDisplayTitle(Wrapper.SCRIPT_DATA.authors()[0]
				+ " - "
				+ Wrapper.SCRIPT_DATA.name()
				+ " v"
				+ Wrapper.SCRIPT_DATA.version());
		provide(new BankProcess(new Node[] { new WalkToBank(), new BankItems() }));
		provide(new TanHidesProcess(new Node[] { new WalkToTanner(), new TanHides() }));
	}

	@Override
	public void onStop()
	{
		stop();
	}

	@Override
	public int loop()
	{
		if (Wrapper.isStopping)
			return -1;

		if (Wrapper.hasLoaded)
		{}

		if (jobContainer != null)
		{
			final Node job = jobContainer.state();
			if (job != null)
			{
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
		}

		return Random.nextInt(10, 50);
	}

	@Override
	public void onRepaint(Graphics g2)
	{
		Graphics2D g = (Graphics2D) g2;

		// mouse
		BasicCross.Paint(g, 5, PaintVars.degreeRotation, Color.CYAN);
		BasicCircle.Paint(g, 6, 0, Color.BLACK);
		PaintVars.degreeRotation += 3;
		if (PaintVars.degreeRotation >= 360)
			PaintVars.degreeRotation = 0;

		// Display strings
		g.setFont(PaintVars.TXT_STATS);
		g.setColor(new Color(0, 0, 0, 200));

		PaintHandler.setDisplayString("Run time - "
				+ Vars.timer_runtime.toElapsedString(), PaintHandler.Line.FIVE, true);
		PaintHandler.setDisplayString("Status - "
				+ Vars.status, PaintHandler.Line.SIX, true);
		PaintHandler.paint(g, false);
	}

	public synchronized final void provide(final Node... jobs)
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

	public synchronized final void revoke(final Node... jobs)
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

	public final void submit(final Job... jobs)
	{
		for (final Job job : jobs)
		{
			getContainer().submit(job);
		}
	}

	public static final void antiban(boolean canBeIntrusive)
	{
		if (canBeIntrusive)
		{
			// intrusive antibans here
			return;
		}

		if (!Vars.timer_antiban.isRunning())
		{
			AreaCameraAction.rollForAction(1, 10);
			Vars.setRandomAntibanTimer();
		}
	}

	public static void updateStatus(String newS)
	{
		Vars.status = newS;
		System.out.println(newS);
	}

	public static void setLoaded()
	{
		Wrapper.hasLoaded = true;
	}

	public static boolean hasLoaded()
	{
		return Wrapper.hasLoaded;
	}

	public static void stopScript()
	{
		Wrapper.updateStatus("Stopping script.");
		Wrapper.isStopping = true;
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		PaintVars.displayPaint = false;
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		PaintVars.displayPaint = true;
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{}
}
