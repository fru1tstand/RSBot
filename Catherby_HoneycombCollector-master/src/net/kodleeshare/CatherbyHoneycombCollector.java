package net.kodleeshare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.kodleeshare.catherby.branch.BankItems;
import net.kodleeshare.catherby.branch.GetHoneycomb;
import net.kodleeshare.catherby.branch.GetInsectRepellent;
import net.kodleeshare.catherby.branch.node.DepositItems;
import net.kodleeshare.catherby.branch.node.PickupRepellent;
import net.kodleeshare.catherby.branch.node.TakeHoneycomb;
import net.kodleeshare.catherby.branch.node.WalkToBank;
import net.kodleeshare.catherby.branch.node.WalkToBeehive;
import net.kodleeshare.catherby.branch.node.WalkToHouse;
import net.kodleeshare.generic.Global;
import net.kodleeshare.paint.PaintHandler;
import net.kodleeshare.paint.PaintVars;
import net.kodleeshare.paint.cursor.BasicCircle;
import net.kodleeshare.paint.cursor.BasicCross;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;

import us.scriptwith.util.PriceWrapper;

@Manifest(authors = { "Fru1tstanD" }, name = "Catherby Honeycomb Collector", description = "Collects honeycombs from beehives west of Catherby", version = 0.1, hidden = true)
public class CatherbyHoneycombCollector extends ActiveScript implements PaintListener
{
	public static final Manifest	SCRIPT_DATA		= CatherbyHoneycombCollector.class.getAnnotation(Manifest.class);

	public static int				count_pinv		= 0;
	public static int				count_collected	= 0;
	public static PriceWrapper		prices			= new PriceWrapper(GetHoneycomb.ID_HONEYCOMB);

	@Override
	public void onStart()
	{
		PaintHandler.setDisplayTitle(SCRIPT_DATA.authors()[0]
				+ " - "
				+ SCRIPT_DATA.name()
				+ " v"
				+ SCRIPT_DATA.version());
		Global.status = "Starting script";
		CatherbyHoneycombCollector.count_pinv = Inventory.getCount(GetHoneycomb.ID_HONEYCOMB);

		Global.provide(new GetInsectRepellent(new Node[] { new WalkToHouse(), new PickupRepellent() }));
		Global.provide(new BankItems(new Node[] { new WalkToBank(), new DepositItems() }));
		Global.provide(new GetHoneycomb(new Node[] { new WalkToBeehive(), new TakeHoneycomb() }));
	}

	@Override
	public int loop()
	{
		if (Global.isStopping)
			return -1;
		if (!Game.isLoggedIn())
			return Random.nextInt(1000, 2000);
		int hcCount = Inventory.getCount(GetHoneycomb.ID_HONEYCOMB);
		if (hcCount > CatherbyHoneycombCollector.count_pinv)
			count_collected += hcCount
					- count_pinv;
		count_pinv = hcCount;

		if (Global.getJobContainer() != null)
		{
			final Node job = Global.getJobContainer().state();
			if (job != null)
			{
				Global.getJobContainer().set(job);
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

		/******************************* Mouse ****************************/
		BasicCross.Paint(g, 5, PaintVars.degreeRotation, Color.CYAN);
		BasicCircle.Paint(g, 6, 0, Color.BLACK);
		Global.MOUSE_TRAIL.addPos(Mouse.getLocation());
		Global.MOUSE_TRAIL.paint(g, Color.CYAN, 255, true);
		PaintVars.degreeRotation += 3;
		if (PaintVars.degreeRotation >= 360)
			PaintVars.degreeRotation %= 360;
		/****************************************************************/
		PaintHandler.setDisplayString(Global.DF.format(count_collected
				* prices.getPrice(GetHoneycomb.ID_HONEYCOMB))
				+ " GP Earned", PaintHandler.Line.TWO, false);
		PaintHandler.setDisplayString(Global.DF.format(Math.round((((long) count_collected
				* prices.getPrice(GetHoneycomb.ID_HONEYCOMB) * 3600000) / Global.timer_runtime.getElapsed())))
				+ " GP/H", PaintHandler.Line.THREE, false);

		PaintHandler.setDisplayString(count_collected
				+ " Collected", PaintHandler.Line.TWO, true);
		PaintHandler.setDisplayString(Global.DF.format(Math.round((((long) count_collected * 3600000) / Global.timer_runtime.getElapsed())))
				+ " Honey/H", PaintHandler.Line.THREE, true);

		PaintHandler.setDisplayString("Run time - "
				+ Global.timer_runtime.toElapsedString(), PaintHandler.Line.FIVE, true);
		PaintHandler.setDisplayString("Status - "
				+ Global.status, PaintHandler.Line.SIX, true);

		PaintHandler.paint(g, null);
	}
}
