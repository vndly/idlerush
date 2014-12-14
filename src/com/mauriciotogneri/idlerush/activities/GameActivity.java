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
import com.mauriciotogneri.idlerush.objects.Game;
import com.mauriciotogneri.idlerush.objects.buildings.Building;

public class GameActivity extends Activity
{
	private final Runnable cycleTask = new Task();
	private ScheduledExecutorService executor = null;
	private ScheduledFuture<?> scheduledTask = null;
	
	private Game game;
	
	private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
	
	// http://cookieclicker.wikia.com/wiki/Building
	// http://cookieclicker.wikia.com/wiki/Upgrades
	
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
		this.game = new Game(600, 1000, 1234, 6);
		
		updateUI();
	}
	
	private void update()
	{
		this.game.update();
		
		updateUI();
	}
	
	private void updateUI()
	{
		int remainingTime = this.game.getRemainingTime();
		
		long millis = remainingTime * 1000;
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
		
		setTitle("  " + String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
		
		if (remainingTime <= 0)
		{
			gameFinished();
		}
		
		// ---------------
		
		TextView totalCoinsLabel = (TextView)findViewById(R.id.total_coins);
		totalCoinsLabel.setText(this.numberFormat.format(this.game.getTotalCoins()));
		
		TextView rateCoinsLabel = (TextView)findViewById(R.id.rate_coins);
		rateCoinsLabel.setText(this.numberFormat.format(this.game.getRateCoins()) + " / second");
		
		// ---------------
		
		updateBuildingUI(this.game.getBuilding1(), R.id.building_1_level, R.id.building_1_cost, R.id.building_1_total_production, R.id.building_1_production_unit);
	}
	
	private void updateBuildingUI(Building building, int levelId, int costId, int totalProductionId, int productionUnitId)
	{
		TextView building1Count = (TextView)findViewById(levelId);
		building1Count.setText(String.valueOf(building.getLevel()));
		
		TextView building1Cost = (TextView)findViewById(costId);
		building1Cost.setText("Cost: " + this.numberFormat.format(building.getNextPrice()));
		
		TextView building1TotalProduction = (TextView)findViewById(totalProductionId);
		building1TotalProduction.setText("Total: " + this.numberFormat.format(building.getCps()) + " / sec");
		
		TextView building1ProductionUnit = (TextView)findViewById(productionUnitId);
		building1ProductionUnit.setText("Unit: " + this.numberFormat.format(building.getBaseCps()) + " / sec");
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