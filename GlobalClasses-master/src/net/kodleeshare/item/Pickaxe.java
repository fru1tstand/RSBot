package net.kodleeshare.item;

import org.powerbot.game.api.wrappers.node.Item;

public enum Pickaxe
{
	BRONZE("Bronze pickaxe", 1, 1265, 1266, 20780),
	IRON("Iron pickaxe", 1, 1267, 1268, 20781),
	DWARVEN("Dwarven army axe", 1, 21340, 0, 0),
	STEEL("Steel pickaxe", 6, 1269, 1270, 20782),
	MITHRIL("Mithril pickaxe", 21, 1273, 1274, 20784),
	ADAMANT("Adamant pickaxe", 31, 1271, 1272, 20783),
	RUNE("Rune pickaxe", 41, 1275, 1276, 20785),
	DRAGON("Dragon pickaxe", 61, 15259, 15260, 20786),
	SACREDCLAY("Sacred clay pickaxe", 40, 14107, 0, 0),
	INFERNO("Inferno adze", 41, 13661, 0, 0);

	private String	name;
	private int		level_mining;
	private int		id_normal;
	private int		id_noted;
	private int		id_gilded;

	Pickaxe(String name, int level_mining, int id_normal, int id_noted, int id_gilded)
	{
		this.name = name;
		this.id_normal = id_normal;
		this.level_mining = level_mining;
		this.id_noted = id_noted;
		this.id_gilded = id_gilded;
	}

	public int getIdNormal()
	{
		return this.id_normal;
	}

	public String getName()
	{
		return this.name;
	}

	public int getLevel()
	{
		return this.level_mining;
	}

	public int getIdNoted()
	{
		return this.id_noted;
	}

	public int getIdGilded()
	{
		return this.id_gilded;
	}

	public static boolean containsNormalPickaxe(Item... items)
	{
		for (Item theItem : items)
			for (Pickaxe thePick : Pickaxe.values())
				if (theItem != null
						&& thePick.getIdNormal() == theItem.getId())
					return true;
		return false;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
