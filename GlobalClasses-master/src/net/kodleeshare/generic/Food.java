package net.kodleeshare.generic;

public class Food
{
	private String name = null;
	private int lifepoints = 0;
	private int id = 0;
	
	public static final Food[] allFood = 
		{
			new Food("Anchovies", 200, 319),
			new Food("Bass", 860, 365),
			new Food("Bread", 200, 2309),
			new Food("Cavefish", 1600, 15266),
			new Food("Cod", 360, 339),
			new Food("Cooked Chicken", 200, 2140),
			new Food("Cooked Meat", 200, 2142),
			new Food("Egg Potato", 1100, 7056),
			new Food("Herring", 200, 347),
			new Food("Lobster", 800, 379),
			new Food("Mackerel", 200, 355),
			new Food("Manta Ray", 1820, 391),
			new Food("Monkfish", 1240, 7946),
			new Food("Pike", 400, 351),
			new Food("Potato with cheese", 940, 6705),
			new Food("Rocktail", 1860, 15272),
			new Food("Salmon", 500, 329),
			new Food("Sardine", 200, 325),
			new Food("Sea Turtle", 1640, 397),
			new Food("Shark", 1600, 385),
			new Food("Shrimp", 200, 315),
			new Food("Swordfish", 900, 373),
			new Food("Tiger Shark", 1900, 21521),
			new Food("Trout", 300, 333),
			new Food("Tuna", 600, 361),
			new Food("Tuna Potato", 1700, 7060)
		};
	
	public Food(String name, int lp, int id)
	{
		this.name = name;
		this.lifepoints = lp;
		this.id = id;
	}
	
	public String getName()
	{
		return this.name;
	}
	public int getId()
	{
		return this.id;
	}
	public int getLifePoints()
	{
		return this.lifepoints;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	public static Food getFoodFromName(String name)
	{
		for(Food cFood: Food.allFood)
		{
			if(cFood.getName() == name)
				return cFood;
		}
		return null;
	}
	public static Food getFoodFromId(int id)
	{
		for(Food cFood: Food.allFood)
		{
			if(cFood.getId() == id)
				return cFood;
		}
		return null;
	}
}
