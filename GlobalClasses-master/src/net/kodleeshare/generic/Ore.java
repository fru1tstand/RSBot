package net.kodleeshare.generic;

public enum Ore
{
	COAL(453, 32426, 32427, 32428);

	private int		inventoryId;
	private int[]	rockIds;

	Ore(int inventoryId, int... rockIds)
	{
		this.inventoryId = inventoryId;
		this.rockIds = rockIds;
	}

	public int getInventoryId()
	{
		return this.inventoryId;
	}

	public int[] getRockIds()
	{
		return this.rockIds;
	}
}
