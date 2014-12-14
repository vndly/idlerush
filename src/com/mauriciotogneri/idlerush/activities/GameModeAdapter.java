package com.mauriciotogneri.idlerush.activities;

import java.util.List;
import android.content.Context;
import android.widget.ArrayAdapter;
import com.mauriciotogneri.idlerush.objects.GameMode;

public class GameModeAdapter extends ArrayAdapter<GameMode>
{
	public GameModeAdapter(Context context, List<GameMode> list)
	{
		super(context, android.R.layout.simple_spinner_item, list);
		
		setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
}