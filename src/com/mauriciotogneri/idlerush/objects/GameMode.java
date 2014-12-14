package com.mauriciotogneri.idlerush.objects;

public class GameMode
{
	public final int time;
	private final String label;
	
	public GameMode(int time, String label)
	{
		this.time = time;
		this.label = label;
	}
	
	@Override
	public String toString()
	{
		return this.label;
	}
}