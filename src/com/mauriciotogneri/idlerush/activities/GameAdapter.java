package com.mauriciotogneri.idlerush.activities;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.idlerush.R;
import com.mauriciotogneri.idlerush.objects.Game;

public class GameAdapter extends ArrayAdapter<Game>
{
	private final LayoutInflater inflater;
	private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
	
	public GameAdapter(Context context, List<Game> list)
	{
		super(context, R.layout.game_row, list);
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Game game = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.game_row, parent, false);
		}
		
		TextView gameMode = (TextView)convertView.findViewById(R.id.game_mode);
		gameMode.setText(game.getGameMode().toString());
		
		TextView remainingTime = (TextView)convertView.findViewById(R.id.remaining_time);
		remainingTime.setText(game.getRemainingTimeFormatted());
		
		TextView currentCoins = (TextView)convertView.findViewById(R.id.current_coins);
		currentCoins.setText(this.numberFormat.format(game.getCurrentCoins()));
		
		TextView totalCoins = (TextView)convertView.findViewById(R.id.total_coins);
		totalCoins.setText(this.numberFormat.format(game.getTotalCoins()));
		
		return convertView;
	}
}