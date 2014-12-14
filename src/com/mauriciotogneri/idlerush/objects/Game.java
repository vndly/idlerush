package com.mauriciotogneri.idlerush.objects;

import com.mauriciotogneri.idlerush.objects.buildings.Building;
import com.mauriciotogneri.idlerush.objects.buildings.Building1;
import com.mauriciotogneri.idlerush.objects.buildings.Building10;
import com.mauriciotogneri.idlerush.objects.buildings.Building2;
import com.mauriciotogneri.idlerush.objects.buildings.Building3;
import com.mauriciotogneri.idlerush.objects.buildings.Building4;
import com.mauriciotogneri.idlerush.objects.buildings.Building5;
import com.mauriciotogneri.idlerush.objects.buildings.Building6;
import com.mauriciotogneri.idlerush.objects.buildings.Building7;
import com.mauriciotogneri.idlerush.objects.buildings.Building8;
import com.mauriciotogneri.idlerush.objects.buildings.Building9;

public class Game
{
	private int remainingTime = 0;
	private long totalCoins = 0;
	private int rateCoins = 0;
	
	private final Building1 building1;
	private final Building2 building2;
	private final Building3 building3;
	private final Building4 building4;
	private final Building5 building5;
	private final Building6 building6;
	private final Building7 building7;
	private final Building8 building8;
	private final Building9 building9;
	private final Building10 building10;
	
	public Game(int remainingTime, long totalCoins, int rateCoins, int level1, int level2, int level3, int level4, int level5, int level6, int level7, int level8, int level9, int level10)
	{
		this.remainingTime = remainingTime;
		this.totalCoins = totalCoins;
		this.rateCoins = rateCoins;
		
		this.building1 = new Building1(level1);
		this.building2 = new Building2(level2);
		this.building3 = new Building3(level3);
		this.building4 = new Building4(level4);
		this.building5 = new Building5(level5);
		this.building6 = new Building6(level6);
		this.building7 = new Building7(level7);
		this.building8 = new Building8(level8);
		this.building9 = new Building9(level9);
		this.building10 = new Building10(level10);
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
	
	public Building getBuilding2()
	{
		return this.building2;
	}
	
	public Building getBuilding3()
	{
		return this.building3;
	}
	
	public Building getBuilding4()
	{
		return this.building4;
	}
	
	public Building getBuilding5()
	{
		return this.building5;
	}
	
	public Building getBuilding6()
	{
		return this.building6;
	}
	
	public Building getBuilding7()
	{
		return this.building7;
	}
	
	public Building getBuilding8()
	{
		return this.building8;
	}
	
	public Building getBuilding9()
	{
		return this.building9;
	}
	
	public Building getBuilding10()
	{
		return this.building10;
	}
}