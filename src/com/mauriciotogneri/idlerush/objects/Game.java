package com.mauriciotogneri.idlerush.objects;

import com.mauriciotogneri.idlerush.objects.buildings.Building;
import com.mauriciotogneri.idlerush.objects.buildings.Building1;

public class Game
{
	private int remainingTime = 0;
	private long totalCoins = 0;
	private int rateCoins = 0;
	
	private final Building1 building1;
	
	public Game(int remainingTime, long totalCoins, int rateCoins, int building1Level)
	{
		this.remainingTime = remainingTime;
		this.totalCoins = totalCoins;
		this.rateCoins = rateCoins;
		
		this.building1 = new Building1(building1Level);
	}
	
	public void update()
	{
		this.totalCoins += this.rateCoins;
		this.remainingTime--;
	}
	
	public long getTotalCoins()
	{
		return this.totalCoins;
	}
	
	public int getRateCoins()
	{
		return this.rateCoins;
	}
	
	public int getRemainingTime()
	{
		return this.remainingTime;
	}
	
	public Building getBuilding1()
	{
		return this.building1;
	}
}