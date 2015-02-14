package net.kodleeshare.dwarfminedungeon;

import net.kodleeshare.dwarfminedungeon.branch.MineRocksProcess;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Timer;

public class Vars
{

	/*
	 * Project specific
	 */
	public static int	count_stored	= 0;
	public static int	count_inventory	= 0;
	public static int	count_start		= 0;
	public static int	xp_start		= Skills.getExperience(Skills.MINING);
	public static Timer	timer_ttl		= new Timer(0);

	public static int getCurrentCount()
	{
		if (MineRocksProcess.id_rocks.length == 0)
			return 0;
		if (!DepositBox.isOpen())
			Vars.count_inventory = Inventory.getCount(MineRocksProcess.id_rocks_inventory);
		return Vars.count_inventory
				+ Vars.count_stored
				- Vars.count_start;

	}

	public Vars()
	{}

}
