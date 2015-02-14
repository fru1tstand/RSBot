package net.kodleeshare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import net.kodleeshare.burthopeurn.OnStartGUI;
import net.kodleeshare.burthopeurn.Vars;
import net.kodleeshare.burthopeurn.branch.BankProcess;
import net.kodleeshare.burthopeurn.branch.FormUrnProcess;
import net.kodleeshare.burthopeurn.branch.MineClayProcess;
import net.kodleeshare.burthopeurn.branch.SoftenClayProcess;
import net.kodleeshare.burthopeurn.branch.node.BankItems;
import net.kodleeshare.burthopeurn.branch.node.CraftUrn;
import net.kodleeshare.burthopeurn.branch.node.FireUrn;
import net.kodleeshare.burthopeurn.branch.node.MineClay;
import net.kodleeshare.burthopeurn.branch.node.SoftenClay;
import net.kodleeshare.burthopeurn.branch.node.WalkToBank;
import net.kodleeshare.burthopeurn.branch.node.WalkToRocks;
import net.kodleeshare.burthopeurn.branch.node.WalkToWell;
import net.kodleeshare.burthopeurn.branch.node.WalkToWheel;
import net.kodleeshare.generic.AreaCameraAction;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.UrnProcess;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.paint.PaintHandler;
import net.kodleeshare.paint.PaintVars;
import net.kodleeshare.paint.cursor.BasicCircle;
import net.kodleeshare.paint.cursor.BasicCross;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

@Manifest(authors = { "Fru1tstanD" }, name = "Burthope Urn Crafter", description = "Mines clay and crafts urns in Burthope", topic = 930513, version = 1.3)
public class BurthopeUrnCrafter extends ActiveScript implements PaintListener, MouseListener
{
	public static final Manifest	SCRIPT_DATA	= BurthopeUrnCrafter.class.getAnnotation(Manifest.class);

	@Override
	public void onStart()
	{
		Global.updateStatus("Starting script");
		PaintHandler.setDisplayTitle(BurthopeUrnCrafter.SCRIPT_DATA.authors()[0]
				+ " - "
				+ BurthopeUrnCrafter.SCRIPT_DATA.name()
				+ " v"
				+ BurthopeUrnCrafter.SCRIPT_DATA.version());
		AreaCameraAction.provide(new AreaCameraAction(110, 230, 50, 96, new Area(new Tile(2889, 3498, 0), new Tile(2881, 3507, 0))), new AreaCameraAction(0, 360, 50, 96, Vars.AREA_ACTIVE), new AreaCameraAction(0, 360, 40, 96,
				MineClayProcess.AREA_ACTIVE));
		Vars.GuiSetup = new OnStartGUI();

		while (!Utils.toggleProductionDialog(true))
		{
			Task.sleep(300, 500);
		}
	}

	@Override
	public void onStop()
	{
		if (Vars.GuiSetup != null)
			Vars.GuiSetup.dispose();
		stop();
	}

	@Override
	public int loop()
	{
		if (Global.isStopping)
			return -1;

		if (Global.hasLoaded)
		{
			Global.provide(new MineClayProcess(new Node[] { new WalkToRocks(), new MineClay() }));
			Global.provide(new SoftenClayProcess(new Node[] { new WalkToWell(), new SoftenClay() }));
			Global.provide(new FormUrnProcess(new Node[] { new WalkToWheel(), new CraftUrn(), new FireUrn() }));
			Global.provide(new BankProcess(new Node[] { new WalkToBank(), new BankItems() }));
			for (UrnProcess theUrnProcess : Vars.UrnsToMake)
			{
				if (theUrnProcess == null)
					continue;
				if (theUrnProcess.getUrn().getLevel() > Skills.getLevel(Skills.CRAFTING))
				{
					Global.updateStatus("Your crafting level is too low!");
					JOptionPane.showMessageDialog(null, "Your crafting level is too low to make the selected Urns");
					Global.stopScript();
					return -1;
				}
			}
			UrnProcess currProcess = UrnProcess.getCurrentUrnProcess(Vars.UrnsToMake);
			Vars.paint_current_urn = currProcess;
			if (currProcess.getHowTo() != UrnProcess.method.MINE)
				Vars.isBanking = true;
			Global.hasLoaded = false;
		}

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

		// mouse
		BasicCross.Paint(g, 5, PaintVars.degreeRotation, Color.CYAN);
		BasicCircle.Paint(g, 6, 0, Color.BLACK);
		Global.MOUSE_TRAIL.addPos(Mouse.getLocation());
		Global.MOUSE_TRAIL.paint(g, Color.CYAN, 255, true);

		PaintVars.degreeRotation += 3;
		if (PaintVars.degreeRotation >= 360)
			PaintVars.degreeRotation = 0;

		// Display strings
		g.setFont(PaintVars.TXT_STATS);
		g.setColor(new Color(0, 0, 0, 200));

		if (Vars.paint_current_urn != null)
		{
			PaintHandler.setDisplayString("Current made - "
					+ Vars.paint_current_urn.getCountMade(), PaintHandler.Line.ONE, true);
			PaintHandler.setDisplayString("Current to make - "
					+ ((Vars.paint_current_urn.getCountToMake() == -1) ? "unlimited" : Vars.paint_current_urn.getCountToMake()), PaintHandler.Line.TWO, true);
			PaintHandler.setDisplayString("Current urn - "
					+ Vars.paint_current_urn.getUrn().getName(), PaintHandler.Line.THREE, true);
		}
		PaintHandler.setDisplayString("Total made - "
				+ Vars.count_urns, PaintHandler.Line.FOUR, true);
		PaintHandler.setDisplayString("Run time - "
				+ Global.timer_runtime.toElapsedString(), PaintHandler.Line.FIVE, true);
		PaintHandler.setDisplayString("Status - "
				+ Global.status, PaintHandler.Line.SIX, true);

		PaintHandler.setDisplayString(Global.DF.format(Math.round((((double) Skills.getExperience(Skills.CRAFTING) - Vars.XP_START) * 3600000 / Global.timer_runtime.getElapsed())))
				+ " XP/H", PaintHandler.Line.FOUR, false);
		PaintHandler.setDisplayString(((Vars.timer_ttl != null) ? Vars.timer_ttl.toRemainingString()
				+ " 'till level" : "[Calculating TTL]"), PaintHandler.Line.FIVE, false);

		PaintHandler.paint(g, null);
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
