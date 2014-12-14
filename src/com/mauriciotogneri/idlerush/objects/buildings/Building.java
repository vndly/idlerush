package com.mauriciotogneri.idlerush.objects.buildings;

public class Building
{
	private int level = 0;
	private final long baseCost;
	private final int baseCps;
	
	// http://cookieclicker.wikia.com/wiki/Building
	// http://cookieclicker.wikia.com/wiki/Upgrades
	
	protected Building(int level, long baseCost, int baseCps)
	{
		this.level = level;
		this.baseCost = baseCost;
		this.baseCps = baseCps;
	}
	
	public void increase()
	{
		this.level++;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public int getBaseCps()
	{
		return this.baseCps;
	}
	
	public int getNextPrice()
	{
		return (int)(this.baseCost * Math.pow(1.15f, this.level));
	}
	
	public int getCps()
	{
		return this.baseCps * this.level;
	}
}