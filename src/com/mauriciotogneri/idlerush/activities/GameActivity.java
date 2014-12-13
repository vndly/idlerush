package com.mauriciotogneri.idlerush.activities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.mauriciotogneri.idlerush.R;

public class GameActivity extends Activity
{
	private final Runnable cycleTask = new Task();
	private ScheduledExecutorService executor = null;
	private ScheduledFuture<?> scheduledTask = null;
	
	private int remainingTime = 0;
	private long totalCoins = 0;
	private int rateCoins = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_game);
		
		this.executor = Executors.newScheduledThreadPool(1);
		
		initialize();
	}
	
	private void initialize()
	{
		this.remainingTime = 600;
		
		this.totalCoins = 0;
		this.rateCoins = 1;
		
		updateUI();
	}
	
	private void update()
	{
		this.totalCoins += this.rateCoins;
		this.remainingTime--;
		
		updateUI();
	}
	
	private void updateUI()
	{
		TextView totalCoinsLabel = (TextView)findViewById(R.id.total_coins);
		totalCoinsLabel.setText(String.valueOf(this.totalCoins));
		
		TextView rateCoinsLabel = (TextView)findViewById(R.id.rate_coins);
		rateCoinsLabel.setText(String.valueOf(this.rateCoins) + " / seconds");
		
		setTitle("  " + this.remainingTime);
	}
	
	private class Task implements Runnable
	{
		private long lastTime = System.nanoTime();
		
		@Override
		public void run()
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					long currentTime = System.nanoTime();
					Log.e("TEST", "TIME; " + (currentTime - Task.this.lastTime) / 1E9);
					Task.this.lastTime = currentTime;
					
					update();
				}
			});
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
		
		this.scheduledTask = this.executor.scheduleWithFixedDelay(this.cycleTask, 1, 1, TimeUnit.SECONDS);
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