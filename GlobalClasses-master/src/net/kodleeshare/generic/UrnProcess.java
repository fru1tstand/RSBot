package net.kodleeshare.generic;

public class UrnProcess
{
	public enum method
	{
		MINE, BANKSOFTCLAY, BANKCLAY;
	}

	private Urn		theUrn;
	private int		count	= 0;
	private int		made	= 0;
	private method	howTo;

	public UrnProcess(Urn theUrn, int quantity, method howTo)
	{
		this.theUrn = theUrn;
		this.count = quantity;
		this.howTo = howTo;
	}

	public void addMade(int quantity)
	{
		this.made += quantity;
	}

	public boolean isComplete()
	{
		return ((this.made >= this.count) && (this.count != -1));
	}

	public Urn getUrn()
	{
		return this.theUrn;
	}

	public int getCountMade()
	{
		return this.made;
	}

	public int getCountToMake()
	{
		return this.count;
	}

	public method getHowTo()
	{
		return this.howTo;
	}

	public static Urn getCurrentUrn(UrnProcess[] UrnsToMake)
	{
		Urn tempUrn = null;
		for (UrnProcess theUrn : UrnsToMake)
		{
			if (theUrn == null)
				continue;
			if (!theUrn.isComplete())
			{
				tempUrn = theUrn.getUrn();
				break;
			}
		}
		return tempUrn;
	}

	public static UrnProcess getCurrentUrnProcess(UrnProcess[] UrnsToMake)
	{
		UrnProcess tempUP = null;
		for (UrnProcess theUrn : UrnsToMake)
		{
			if (!theUrn.isComplete())
			{
				tempUP = theUrn;
				break;
			}
		}
		return tempUP;
	}
}
