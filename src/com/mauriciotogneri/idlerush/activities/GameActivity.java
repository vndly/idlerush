package com.mauriciotogneri.idlerush.activities;

import android.app.Activity;
import android.os.Bundle;
import com.mauriciotogneri.idlerush.R;

public class GameActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
	}
}