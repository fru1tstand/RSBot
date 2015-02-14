package net.kodleeshare;

import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Version 1.1
 * 
 */
public class Vars
{
	public static final Timer	timer_runtime	= new Timer(0);
	public static Timer			timer_antiban	= new Timer(0);
	public static String		status			= "";
	public static int			energyThreshold	= Random.nextInt(25, 90);

	// project specific
	public static final Area	AREA_ACTIVE		= new Area(new Tile(2875, 3456, 0), new Tile(2921, 3549, 0));
	public static final int		ID_DRAGONHIDE	= 1747;

	// skills

	public Vars()
	{}

	public static void setRandomAntibanTimer()
	{
		Vars.timer_antiban = new Timer(Random.nextInt(0, 900000));
	}
}
