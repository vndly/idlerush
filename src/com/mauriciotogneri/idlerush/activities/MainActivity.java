package com.mauriciotogneri.idlerush.activities;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.mauriciotogneri.idlerush.R;
import com.mauriciotogneri.idlerush.database.Database;
import com.mauriciotogneri.idlerush.database.GameDao;
import com.mauriciotogneri.idlerush.objects.Game;

public class MainActivity extends Activity
{
	private GameAdapter gameAdapter;
	private final List<Game> savedGames = new ArrayList<Game>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Database.initialize(this);
		
		ListView listSavedGames = (ListView)findViewById(R.id.saved_games);
		this.gameAdapter = new GameAdapter(this, this.savedGames);
		listSavedGames.setAdapter(this.gameAdapter);
		listSavedGames.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Game game = (Game)parent.getItemAtPosition(position);
				continueGame(game.getId());
			}
		});
		
		Spinner gameMode = (Spinner)findViewById(R.id.game_mode);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.game_modes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gameMode.setAdapter(adapter);
		
		Button newGame = (Button)findViewById(R.id.new_game);
		newGame.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				newGame();
			}
		});
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		GameDao gameDao = new GameDao();
		List<Game> games = gameDao.getGames();
		
		this.savedGames.clear();
		this.savedGames.addAll(games);
		
		this.gameAdapter.notifyDataSetChanged();
	}
	
	private void continueGame(int gameId)
	{
		GameDao gameDao = new GameDao();
		Game game = gameDao.getGame(gameId);
		
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