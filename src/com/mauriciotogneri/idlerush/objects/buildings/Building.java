package com.mauriciotogneri.idlerush.objects.buildings;

public class Building
{
	private int level = 0;
	private final long baseCost;
	private final float baseCps;
	
	// http://cookieclicker.wikia.com/wiki/Building
	// http://cookieclicker.wikia.com/wiki/Upgrades
	
	protected Building(int level, long baseCost, float baseCps)
	{
		this.level = level;
		this.baseCost = baseCost;
		this.baseCps = baseCps;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public float getBaseCps()
	{
		return this.baseCps;
	}
	
	public int getNextPrice()
	{
		return (int)(this.baseCost * Math.pow(1.15f, this.level));
	}
	
	public float getCps()
	{
		return this.baseCps * this.level;
	}
}