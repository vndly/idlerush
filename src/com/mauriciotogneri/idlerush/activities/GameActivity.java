package com.mauriciotogneri.idlerush.activities;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.Bundle;
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
	
	// http://cookieclicker.wikia.com/wiki/Building
	
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
		this.rateCoins = 1234;
		
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
		NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
		
		TextView totalCoinsLabel = (TextView)findViewById(R.id.total_coins);
		totalCoinsLabel.setText(numberFormat.format(this.totalCoins));
		
		TextView rateCoinsLabel = (TextView)findViewById(R.id.rate_coins);
		rateCoinsLabel.setText(numberFormat.format(this.rateCoins) + " / second");
		
		long millis = this.remainingTime * 1000;
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
		
		setTitle("  " + String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
		
		if (this.remainingTime <= 0)
		{
			gameFinished();
		}
	}
	
	private void gameFinished()
	{
		stopCycleTask();
	}
	
	private class Task implements Runnable
	{
		@Override
		public void run()
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
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