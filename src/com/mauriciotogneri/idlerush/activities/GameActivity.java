package com.mauriciotogneri.idlerush.activities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.Bundle;
import com.mauriciotogneri.idlerush.R;

public class GameActivity extends Activity
{
	private ScheduledExecutorService executor;
	private ScheduledFuture<?> scheduled;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
	}
	
	public void start()
	{
		this.executor = Executors.newScheduledThreadPool(1);
		
		Runnable task = new Task();
		
		this.scheduled = this.executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
	}
	
	public void cancel()
	{
		this.executor.shutdown();
		// this.scheduled.cancel(false);
	}
	
	private class Task implements Runnable
	{
		private long lastTime = System.nanoTime();
		private int a = 0;
		
		@Override
		public void run()
		{
			long currentTime = System.nanoTime();
			System.out.println(currentTime - this.lastTime);
			this.lastTime = currentTime;
			
			this.a++;
			
			if (this.a > 3)
			{
				cancel();
			}
		}
	}
}