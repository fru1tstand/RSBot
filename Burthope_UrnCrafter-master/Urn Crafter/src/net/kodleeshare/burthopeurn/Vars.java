package net.kodleeshare.burthopeurn;

import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.UrnProcess;

import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Version 1.1
 */
public class Vars
{
	// project specific
	public static final Area	AREA_ACTIVE	= new Area(new Tile(2875, 3456, 0), new Tile(2921, 3549, 0));
	public static UrnProcess[]	UrnsToMake	= null;
	public static OnStartGUI	GuiSetup	= null;
	public static boolean		isBanking	= false;

	// skills
	public static final int		XP_START	= Skills.getExperience(Skills.CRAFTING);
	public static Timer			timer_ttl	= null;

	public static void updateTtl()
	{
		if (Skills.getExperience(Skills.CRAFTING) != Vars.XP_START)
			Vars.timer_ttl = new Timer(Math.round((Skills.getExperienceToLevel(Skills.CRAFTING, Skills.getLevel(Skills.CRAFTING) + 1))
					/ (((double) Skills.getExperience(Skills.CRAFTING) - Vars.XP_START) / Global.timer_runtime.getElapsed())));
	}

	// information display
	public static int			count_urns			= 0;
	public static UrnProcess	paint_current_urn	= null;
}
