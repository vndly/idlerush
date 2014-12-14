package com.mauriciotogneri.idlerush.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.mauriciotogneri.idlerush.R;
import com.mauriciotogneri.idlerush.database.Database;
import com.mauriciotogneri.idlerush.database.GameDao;
import com.mauriciotogneri.idlerush.objects.Game;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Button continueGame = (Button)findViewById(R.id.continue_game);
		continueGame.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				continueGame();
			}
		});
		
		Button newGame = (Button)findViewById(R.id.new_game);
		newGame.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				newGame();
			}
		});
		
		Database.initialize(this);
	}
	
	private void continueGame()
	{
		GameDao gameDao = new GameDao();
		Game game = gameDao.getGame(1);
		
		if (game != null)
		{
			startGame(game.getId());
		}
		else
		{
			Toast.makeText(this, "No games saved", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void newGame()
	{
		GameDao gameDao = new GameDao();
		Game game = gameDao.createGame(60 * 2);
		
		startGame(game.getId());
	}
	
	private void startGame(int gameId)
	{
		Intent intent = new Intent(this, GameActivity.class);
		
		Bundle bundle = new Bundle();
		bundle.putInt(GameActivity.PARAMETER_GAME_ID, gameId);
		
		intent.putExtras(bundle);
		
		startActivity(intent);
	}
}