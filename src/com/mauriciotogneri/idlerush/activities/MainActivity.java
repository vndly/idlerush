package com.mauriciotogneri.idlerush.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.mauriciotogneri.idlerush.R;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Button start = (Button)findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				startGame();
			}
		});
		
		startGame();
	}
	
	private void startGame()
	{
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}