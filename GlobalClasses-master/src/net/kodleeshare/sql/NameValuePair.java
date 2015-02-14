package net.kodleeshare.sql;

public class NameValuePair
{
	private String	name;
	private String	value;

	/**
	 * Creates a new name-value pair
	 * 
	 * @param name
	 * @param value
	 */
	public NameValuePair(String name, String value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return this.name;
	}

	public String getValue()
	{
		return this.value;
	}
}
