package com.mauriciotogneri.idlerush.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
	private static final int VERSION = 1;
	private static Database instance;
	
	private Database(Context context)
	{
		super(context, "database.db", null, Database.VERSION);
	}
	
	public static void initialize(Context context)
	{
		Database.instance = new Database(context);
	}
	
	public static synchronized SQLiteDatabase getInstance()
	{
		return Database.instance.getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(GameDao.TABLE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
	
	public static void closeDatabase(SQLiteDatabase database)
	{
		if (database != null)
		{
			try
			{
				database.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void closeCursor(Cursor cursor)
	{
		if (cursor != null)
		{
			try
			{
				cursor.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}