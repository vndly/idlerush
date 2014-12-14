package com.mauriciotogneri.idlerush.activities;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.mauriciotogneri.idlerush.R;
import com.mauriciotogneri.idlerush.database.GameDao;
import com.mauriciotogneri.idlerush.objects.Game;
import com.mauriciotogneri.idlerush.objects.buildings.Building;

public class GameActivity extends Activity
{
	private final Runnable cycleTask = new Task();
	private ScheduledExecutorService executor = null;
	private ScheduledFuture<?> scheduledTask = null;
	
	private Game game;
	private final Object gameLock = new Object();
	
	private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
	
	public static final String PARAMETER_GAME_ID = "game_id";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_game);
		
		this.executor = Executors.newScheduledThreadPool(1);
		
		int gameId = getIntent().getIntExtra(GameActivity.PARAMETER_GAME_ID, 0);
		
		GameDao gameDao = new GameDao();
		this.game = gameDao.getGame(gameId);
		
		updateUI();
		
		setIconListener(R.id.building_1_icon, 1);
		setIconListener(R.id.building_2_icon, 2);
		setIconListener(R.id.building_3_icon, 3);
		setIconListener(R.id.building_4_icon, 4);
		setIconListener(R.id.building_5_icon, 5);
		setIconListener(R.id.building_6_icon, 6);
		setIconListener(R.id.building_7_icon, 7);
		setIconListener(R.id.building_8_icon, 8);
		setIconListener(R.id.building_9_icon, 9);
		setIconListener(R.id.building_10_icon, 10);
	}
	
	private void setIconListener(int iconId, final int buildingId)
	{
		ImageButton buildingIcon = (ImageButton)findViewById(iconId);
		buildingIcon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				updateBuilding(buildingId);
			}
		});
	}
	
	private void updateBuilding(int id)
	{
		synchronized (this.gameLock)
		{
			boolean updated = this.game.updateBuilding(id);
			
			if (updated)
			{
				updateUI();
			}
		}
	}
	
	private void update()
	{
		synchronized (this.gameLock)
		{
			this.game.update();
			
			updateUI();
		}
	}
	
	private void updateUI()
	{
		int remainingTime = this.game.getRemainingTime();
		
		long millis = remainingTime * 1000;
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
		
		String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
		
		if (remainingTime >= 60)
		{
			setTitle(Html.fromHtml("<big><font color='#777777'>&nbsp;&nbsp;" + timeFormatted + "</font></big>"));
		}
		else
		{
			setTitle(Html.fromHtml("<big><font color='#FF3C3C'>&nbsp;&nbsp;" + timeFormatted + "</font></big>"));
		}
		
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
		updateBuildingUI(this.game.getBuilding2(), R.id.building_2_level, R.id.building_2_cost, R.id.building_2_total_production, R.id.building_2_production_unit);
		updateBuildingUI(this.game.getBuilding3(), R.id.building_3_level, R.id.building_3_cost, R.id.building_3_total_production, R.id.building_3_production_unit);
		updateBuildingUI(this.game.getBuilding4(), R.id.building_4_level, R.id.building_4_cost, R.id.building_4_total_production, R.id.building_4_production_unit);
		updateBuildingUI(this.game.getBuilding5(), R.id.building_5_level, R.id.building_5_cost, R.id.building_5_total_production, R.id.building_5_production_unit);
		updateBuildingUI(this.game.getBuilding6(), R.id.building_6_level, R.id.building_6_cost, R.id.building_6_total_production, R.id.building_6_production_unit);
		updateBuildingUI(this.game.getBuilding7(), R.id.building_7_level, R.id.building_7_cost, R.id.building_7_total_production, R.id.building_7_production_unit);
		updateBuildingUI(this.game.getBuilding8(), R.id.building_8_level, R.id.building_8_cost, R.id.building_8_total_production, R.id.building_8_production_unit);
		updateBuildingUI(this.game.getBuilding9(), R.id.building_9_level, R.id.building_9_cost, R.id.building_9_total_production, R.id.building_9_production_unit);
		updateBuildingUI(this.game.getBuilding10(), R.id.building_10_level, R.id.building_10_cost, R.id.building_10_total_production, R.id.building_10_production_unit);
	}
	
	private void updateBuildingUI(Building building, int levelId, int costId, int totalProductionId, int productionUnitId)
	{
		TextView building1Count = (TextView)findViewById(levelId);
		building1Count.setText(String.valueOf(building.getLevel()));
		
		TextView building1Cost = (TextView)findViewById(costId);
		building1Cost.setText(this.numberFormat.format(building.getNextPrice()));
		
		TextView building1TotalProduction = (TextView)findViewById(totalProductionId);
		building1TotalProduction.setText(this.numberFormat.format(building.getCps()) + " / sec");
		
		TextView building1ProductionUnit = (TextView)findViewById(productionUnitId);
		building1ProductionUnit.setText(this.numberFormat.format(building.getBaseCps()) + " / sec");
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
		
		synchronized (this.gameLock)
		{
			GameDao gameDao = new GameDao();
			gameDao.updateGame(this.game);
		}
		
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