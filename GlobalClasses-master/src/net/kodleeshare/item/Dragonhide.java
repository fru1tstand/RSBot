package net.kodleeshare.item;

public enum Dragonhide
{
	GREEN(1745, 1746, 1753, 1754, "Green"), BLUE(1751, 1752, 2505, 2506, "Blue"), RED(1749, 1750, 2507, 2508, "Red"), BLACK(1747, 1748, 2509, 2510, "Black"), ROYAL(24372, 24373, 24374, 24375, "Royal");
	
	private String	name;
	private int		id_leather;
	private int		id_leather_noted;
	private int		id_hide;
	private int		id_hide_noted;
	
	Dragonhide(final int id_leather, final int id_leather_noted, final int id_hide, final int id_hide_noted, final String name) {
		this.name = name;
		this.id_leather = id_leather;
		this.id_leather_noted = id_leather_noted;
		this.id_hide = id_hide;
		this.id_hide_noted = id_hide_noted;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getLeatherId()
	{
		return this.id_leather;
	}
	
	public int getLeatherNotedId()
	{
		return this.id_leather_noted;
	}
	
	public int getHideId()
	{
		return this.id_hide;
	}
	
	public int getHideNotedId()
	{
		return this.id_hide_noted;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}
