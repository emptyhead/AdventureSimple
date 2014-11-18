package uk.co.amberservices.Adventure;

public class Item
{
	private Integer location;
	private String description;
	private String noun;
	
	public Integer getItemLocation()
	{
		return location;
	}
	
	public String getItemDescription()
	{
		return description;
	}
	
	public String getNoun()
	{
		return noun;
	}
	
	public Item(String description, String noun, Integer location)
	{
		this.noun = noun;
		this.description = description;
		this.location = location;
	}
}
