package com.mauriciotogneri.idlerush.activities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.mauriciotogneri.idlerush.R;

public class GameActivity extends Activity
{
	private final Runnable cycleTask = new Task();
	private ScheduledExecutorService executor = null;
	private ScheduledFuture<?> scheduledTask = null;
	
	private long totalCoins = 0;
	private final int rateCoins = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		
		this.executor = Executors.newScheduledThreadPool(1);
	}
	
	private void update()
	{
		this.totalCoins += this.rateCoins;
		
		TextView totalCoinsLabel = (TextView)findViewById(R.id.total_coins);
		totalCoinsLabel.setText(String.valueOf(this.totalCoins));
		
		TextView rateCoinsLabel = (TextView)findViewById(R.id.rate_coins);
		rateCoinsLabel.setText(String.valueOf(this.rateCoins) + " / seconds");
	}
	
	private class Task implements Runnable
	{
		@Override
		public void run()
		{
			update();
		}
	}
	
	private void stopCycleTask()
	{
		if (this.scheduledTask != null)
		{
			this.scheduledTask.cancel(false);
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		stopCycleTask();
		
		this.scheduledTask = this.executor.scheduleWithFixedDelay(this.cycleTask, 0, 1, TimeUnit.SECONDS);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		stopCycleTask();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		stopCycleTask();
		
		if (this.executor != null)
		{
			this.executor.shutdown();
		}
	}
}