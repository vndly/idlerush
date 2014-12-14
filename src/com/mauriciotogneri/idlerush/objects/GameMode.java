package com.mauriciotogneri.idlerush.objects;

import java.util.ArrayList;
import java.util.List;

public class GameMode
{
	public final int time;
	private final String label;
	
	public GameMode(int time)
	{
		this.time = time;
		this.label = getLabel(time);
	}
	
	private String getLabel(int time)
	{
		String result = "";
		
		int hours = time / (60 * 60);
		
		if (hours > 0)
		{
			if (hours == 1)
			{
				result = "1 hour";
			}
			else
			{
				result = hours + " hours";
			}
		}
		else
		{
			int minutes = time / 60;
			
			if (minutes == 1)
			{
				result = "1 minute";
			}
			else
			{
				result = minutes + " minutes";
			}
		}
		
		return result;
	}
	
	@Override
	public String toString()
	{
		return this.label;
	}
	
	public static List<GameMode> defaultList()
	{
		List<GameMode> result = new ArrayList<GameMode>();
		
		result.add(new GameMode(1 * 10 * 60));
		result.add(new GameMode(1 * 30 * 60));
		result.add(new GameMode(1 * 60 * 60));
		result.add(new GameMode(3 * 60 * 60));
		result.add(new GameMode(6 * 60 * 60));
		result.add(new GameMode(12 * 60 * 60));
		result.add(new GameMode(24 * 60 * 60));
		
		return result;
	}
}