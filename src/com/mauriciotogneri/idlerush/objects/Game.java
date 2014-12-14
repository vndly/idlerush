package com.mauriciotogneri.idlerush.objects;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
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
	private int id = 0;
	
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
	
	public Game(int id, int remainingTime, long totalCoins, int level1, int level2, int level3, int level4, int level5, int level6, int level7, int level8, int level9, int level10)
	{
		this.id = id;
		
		this.remainingTime = remainingTime;
		this.totalCoins = totalCoins;
		
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
		
		claculateRateCoins();
	}
	
	public int getId()
	{
		return this.id;
	}
	
	private void claculateRateCoins()
	{
		this.rateCoins = this.building1.getCps() + this.building2.getCps() + this.building3.getCps() + this.building4.getCps() + this.building5.getCps() + this.building6.getCps() + this.building7.getCps() + this.building8.getCps() + this.building9.getCps() + this.building10.getCps() + 1;
	}
	
	public boolean updateBuilding(int id)
	{
		boolean result = false;
		
		if (this.remainingTime > 0)
		{
			switch (id)
			{
				case 1:
					result = increaseBuilding(this.building1);
					break;
				
				case 2:
					result = increaseBuilding(this.building2);
					break;
				
				case 3:
					result = increaseBuilding(this.building3);
					break;
				
				case 4:
					result = increaseBuilding(this.building4);
					break;
				
				case 5:
					result = increaseBuilding(this.building5);
					break;
				
				case 6:
					result = increaseBuilding(this.building6);
					break;
				
				case 7:
					result = increaseBuilding(this.building7);
					break;
				
				case 8:
					result = increaseBuilding(this.building8);
					break;
				
				case 9:
					result = increaseBuilding(this.building9);
					break;
				
				case 10:
					result = increaseBuilding(this.building10);
					break;
			}
		}
		
		return result;
	}
	
	private boolean increaseBuilding(Building building)
	{
		boolean result = false;
		long nextPrice = building.getNextPrice();
		
		if (nextPrice <= this.totalCoins)
		{
			this.totalCoins -= nextPrice;
			building.increase();
			claculateRateCoins();
			result = true;
		}
		
		return result;
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
	
	public String getRemainingTimeFormatted()
	{
		long millis = this.remainingTime * 1000;
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
		
		return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
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