package com.mauriciotogneri.idlerush.database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mauriciotogneri.idlerush.objects.Game;

public class GameDao
{
	private static final String TABLE_NAME = "GAME";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_TIME = "time";
	private static final String COLUMN_COINS = "coins";
	private static final String COLUMN_BUILDING_1 = "building_1";
	private static final String COLUMN_BUILDING_2 = "building_2";
	private static final String COLUMN_BUILDING_3 = "building_3";
	private static final String COLUMN_BUILDING_4 = "building_4";
	private static final String COLUMN_BUILDING_5 = "building_5";
	private static final String COLUMN_BUILDING_6 = "building_6";
	private static final String COLUMN_BUILDING_7 = "building_7";
	private static final String COLUMN_BUILDING_8 = "building_8";
	private static final String COLUMN_BUILDING_9 = "building_9";
	private static final String COLUMN_BUILDING_10 = "building_10";
	
	public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + GameDao.TABLE_NAME + " (" + //
	    GameDao.COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + //
	    GameDao.COLUMN_TIME + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_COINS + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_1 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_2 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_3 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_4 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_5 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_6 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_7 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_8 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_9 + " INTEGER NOT NULL, " + //
	    GameDao.COLUMN_BUILDING_10 + " INTEGER NOT NULL" + //
	    ");";
	
	public Game getGame(int gameId)
	{
		Game result = null;
		
		SQLiteDatabase database = null;
		Cursor cursor = null;
		
		try
		{
			database = Database.getInstance();
			
			String[] columns = new String[]
				{
				    GameDao.COLUMN_ID, //
				    GameDao.COLUMN_TIME, //
				    GameDao.COLUMN_COINS, //
				    GameDao.COLUMN_BUILDING_1, //
				    GameDao.COLUMN_BUILDING_2, //
				    GameDao.COLUMN_BUILDING_3, //
				    GameDao.COLUMN_BUILDING_4, //
				    GameDao.COLUMN_BUILDING_5, //
				    GameDao.COLUMN_BUILDING_6, //
				    GameDao.COLUMN_BUILDING_7, //
				    GameDao.COLUMN_BUILDING_8, //
				    GameDao.COLUMN_BUILDING_9, //
				    GameDao.COLUMN_BUILDING_10
				};
			
			String selectionClause = GameDao.COLUMN_ID + "= ?";
			
			String[] selectionArgs = new String[]
				{
					String.valueOf(gameId)
				};
			
			cursor = database.query(GameDao.TABLE_NAME, columns, selectionClause, selectionArgs, null, null, null);
			
			if (cursor.moveToFirst())
			{
				result = getGame(cursor);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeCursor(cursor);
			Database.closeDatabase(database);
		}
		
		return result;
	}
	
	public List<Game> getGames()
	{
		List<Game> result = new ArrayList<Game>();
		
		SQLiteDatabase database = null;
		Cursor cursor = null;
		
		try
		{
			database = Database.getInstance();
			
			String[] columns = new String[]
				{
				    GameDao.COLUMN_ID, //
				    GameDao.COLUMN_TIME, //
				    GameDao.COLUMN_COINS, //
				    GameDao.COLUMN_BUILDING_1, //
				    GameDao.COLUMN_BUILDING_2, //
				    GameDao.COLUMN_BUILDING_3, //
				    GameDao.COLUMN_BUILDING_4, //
				    GameDao.COLUMN_BUILDING_5, //
				    GameDao.COLUMN_BUILDING_6, //
				    GameDao.COLUMN_BUILDING_7, //
				    GameDao.COLUMN_BUILDING_8, //
				    GameDao.COLUMN_BUILDING_9, //
				    GameDao.COLUMN_BUILDING_10
				};
			
			cursor = database.query(GameDao.TABLE_NAME, columns, null, null, null, null, null);
			
			cursor.moveToFirst();
			
			while (!cursor.isAfterLast())
			{
				result.add(getGame(cursor));
				
				cursor.moveToNext();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeCursor(cursor);
			Database.closeDatabase(database);
		}
		
		return result;
	}
	
	private Game getGame(Cursor cursor)
	{
		int id = cursor.getInt(0);
		int time = cursor.getInt(1);
		long coins = cursor.getLong(2);
		int building1 = cursor.getInt(3);
		int building2 = cursor.getInt(4);
		int building3 = cursor.getInt(5);
		int building4 = cursor.getInt(6);
		int building5 = cursor.getInt(7);
		int building6 = cursor.getInt(8);
		int building7 = cursor.getInt(9);
		int building8 = cursor.getInt(10);
		int building9 = cursor.getInt(11);
		int building10 = cursor.getInt(12);
		
		return new Game(id, time, coins, building1, building2, building3, building4, building5, building6, building7, building8, building9, building10);
	}
	
	public boolean updateGame(Game game)
	{
		boolean result = false;
		SQLiteDatabase database = null;
		
		try
		{
			database = Database.getInstance();
			
			ContentValues values = new ContentValues();
			values.put(GameDao.COLUMN_TIME, game.getRemainingTime());
			values.put(GameDao.COLUMN_COINS, game.getTotalCoins());
			values.put(GameDao.COLUMN_BUILDING_1, game.getBuilding1().getLevel());
			values.put(GameDao.COLUMN_BUILDING_2, game.getBuilding2().getLevel());
			values.put(GameDao.COLUMN_BUILDING_3, game.getBuilding3().getLevel());
			values.put(GameDao.COLUMN_BUILDING_4, game.getBuilding4().getLevel());
			values.put(GameDao.COLUMN_BUILDING_5, game.getBuilding5().getLevel());
			values.put(GameDao.COLUMN_BUILDING_6, game.getBuilding6().getLevel());
			values.put(GameDao.COLUMN_BUILDING_7, game.getBuilding7().getLevel());
			values.put(GameDao.COLUMN_BUILDING_8, game.getBuilding8().getLevel());
			values.put(GameDao.COLUMN_BUILDING_9, game.getBuilding9().getLevel());
			values.put(GameDao.COLUMN_BUILDING_10, game.getBuilding10().getLevel());
			
			String whereClause = GameDao.COLUMN_ID + " = ?";
			
			String[] whereArgs = new String[]
				{
					String.valueOf(game.getId())
				};
			
			int rowsAffected = database.update(GameDao.TABLE_NAME, values, whereClause, whereArgs);
			
			result = (rowsAffected == 1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeDatabase(database);
		}
		
		return result;
	}
	
	public Game createGame(int time)
	{
		Game result = null;
		SQLiteDatabase database = null;
		
		try
		{
			database = Database.getInstance();
			
			ContentValues values = new ContentValues();
			values.put(GameDao.COLUMN_TIME, time);
			values.put(GameDao.COLUMN_COINS, 0);
			values.put(GameDao.COLUMN_BUILDING_1, 0);
			values.put(GameDao.COLUMN_BUILDING_2, 0);
			values.put(GameDao.COLUMN_BUILDING_3, 0);
			values.put(GameDao.COLUMN_BUILDING_4, 0);
			values.put(GameDao.COLUMN_BUILDING_5, 0);
			values.put(GameDao.COLUMN_BUILDING_6, 0);
			values.put(GameDao.COLUMN_BUILDING_7, 0);
			values.put(GameDao.COLUMN_BUILDING_8, 0);
			values.put(GameDao.COLUMN_BUILDING_9, 0);
			values.put(GameDao.COLUMN_BUILDING_10, 0);
			
			int id = (int)database.insert(GameDao.TABLE_NAME, null, values);
			
			if (id > 0)
			{
				result = new Game(id, time, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeDatabase(database);
		}
		
		return result;
	}
}