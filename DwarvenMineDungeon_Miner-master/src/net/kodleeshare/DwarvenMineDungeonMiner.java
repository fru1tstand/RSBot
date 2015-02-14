package net.kodleeshare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.kodleeshare.dwarfminedungeon.Vars;
import net.kodleeshare.dwarfminedungeon.branch.BankProcess;
import net.kodleeshare.dwarfminedungeon.branch.MineRocksProcess;
import net.kodleeshare.dwarfminedungeon.branch.node.DepositItems;
import net.kodleeshare.dwarfminedungeon.branch.node.GetNewRock;
import net.kodleeshare.dwarfminedungeon.branch.node.MineRocks;
import net.kodleeshare.dwarfminedungeon.branch.node.OpenBank;
import net.kodleeshare.dwarfminedungeon.branch.node.WalkToBank;
import net.kodleeshare.dwarfminedungeon.branch.node.WalkToRocks;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Ore;
import net.kodleeshare.paint.PaintHandler;
import net.kodleeshare.paint.PaintVars;
import net.kodleeshare.paint.cursor.BasicCircle;
import net.kodleeshare.paint.cursor.BasicCross;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Fru1tstanD" }, description = "Mines coal in Dwarven Mine Mysterious Entrance", name = "Dwarven Dungeon Miner", version = 0.9)
public class DwarvenMineDungeonMiner extends ActiveScript implements PaintListener, MouseListener
{
	public static final Manifest	SCRIPT_DATA	= DwarvenMineDungeonMiner.class.getAnnotation(Manifest.class);

	@Override
	public void onStart()
	{
		PaintHandler.setDisplayTitle(DwarvenMineDungeonMiner.SCRIPT_DATA.authors()[0]
				+ " - "
				+ DwarvenMineDungeonMiner.SCRIPT_DATA.name()
				+ " v"
				+ DwarvenMineDungeonMiner.SCRIPT_DATA.version());

		MineRocksProcess.id_rocks = Ore.COAL.getRockIds();
		MineRocksProcess.id_rocks_inventory = new int[] { Ore.COAL.getInventoryId() };
		Vars.count_start = Inventory.getCount(MineRocksProcess.id_rocks_inventory);

		Global.provide(new MineRocksProcess(new Node[] { new GetNewRock(), new WalkToRocks(), new MineRocks() }));
		Global.provide(new BankProcess(new Node[] { new WalkToBank(), new OpenBank(), new DepositItems() }));
	}

	@Override
	public int loop()
	{

		Tree jobContainer = Global.getJobContainer();
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

		// random crap
		g.setColor(Color.CYAN);
		if (MineRocksProcess.currentRock != null
				&& MineRocksProcess.currentRock.validate())
			for (Polygon p : MineRocksProcess.currentRock.getLocation().getBounds())
				g.drawPolygon(p);
		g.setColor(Color.BLUE);
		if (MineRocksProcess.nextRock != null
				&& MineRocksProcess.nextRock.validate())
			for (Polygon p : MineRocksProcess.nextRock.getLocation().getBounds())
				g.drawPolygon(p);

		// mouse
		BasicCross.Paint(g, 5, PaintVars.degreeRotation, Color.CYAN);
		BasicCircle.Paint(g, 6, 0, Color.BLACK);
		Global.MOUSE_TRAIL.addPos(Mouse.getLocation());
		Global.MOUSE_TRAIL.paint(g, Color.CYAN, 255, true);

		PaintVars.degreeRotation += 3;
		if (PaintVars.degreeRotation >= 360)
			PaintVars.degreeRotation = 0;

		g.setFont(PaintVars.TXT_STATS);
		g.setColor(new Color(0, 0, 0, 200));

		g.setColor(Color.CYAN);

		PaintHandler.setDisplayString("Ores Mined - "
				+ Global.DF.format(Vars.getCurrentCount()), PaintHandler.Line.THREE, true);
		PaintHandler.setDisplayString("Ores/H - "
				+ Global.DF.format(Math.round(((double) 3600000
						* Vars.getCurrentCount() / Global.timer_runtime.getElapsed()))), PaintHandler.Line.FOUR, true);
		PaintHandler.setDisplayString("Run time - "
				+ Global.timer_runtime.toElapsedString(), PaintHandler.Line.FIVE, true);
		PaintHandler.setDisplayString("Status - "
				+ Global.status, PaintHandler.Line.SIX, true);

		PaintHandler.setDisplayString(Global.DF.format(Skills.getExperience(Skills.MINING)
				- Vars.xp_start)
				+ " XP Gained", PaintHandler.Line.THREE, false);
		PaintHandler.setDisplayString(Global.DF.format(Math.round((((long) Skills.getExperience(Skills.MINING) - Vars.xp_start) * 3600000 / Global.timer_runtime.getElapsed())))
				+ " XP/H", PaintHandler.Line.FOUR, false);
		PaintHandler.setDisplayString(Vars.timer_ttl.toRemainingString()
				+ " 'till level", PaintHandler.Line.FIVE, false);

		PaintHandler.paint(g, null);
	}

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
	public void mouseClicked(MouseEvent arg0)
	{}

	@Override
	public void mousePressed(MouseEvent arg0)
	{}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{}
}
