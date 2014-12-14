package com.mauriciotogneri.idlerush.activities;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class DefaultDialogSelectableList<Type>
{
	private final Context context;
	private String title = "";
	private int iconId = 0;
	private boolean cancelable = true;
	private OnSelectItem<Type> callbackItemSelected = null;
	private final List<DefaultSelectableListItem<Type>> list = new ArrayList<DefaultSelectableListItem<Type>>();
	
	public DefaultDialogSelectableList(Context context)
	{
		this.context = context;
	}
	
	public DefaultDialogSelectableList<Type> setTitle(String title)
	{
		this.title = title;
		
		return this;
	}
	
	public DefaultDialogSelectableList<Type> setIcon(int iconId)
	{
		this.iconId = iconId;
		
		return this;
	}
	
	public DefaultDialogSelectableList<Type> setCancelable(boolean cancelable)
	{
		this.cancelable = cancelable;
		
		return this;
	}
	
	public DefaultDialogSelectableList<Type> setOnItemSelected(OnSelectItem<Type> callbackSelectItem)
	{
		this.callbackItemSelected = callbackSelectItem;
		
		return this;
	}
	
	public DefaultDialogSelectableList<Type> addItem(String title, Type code, int icon)
	{
		this.list.add(new DefaultSelectableListItem<Type>(title, code, icon));
		
		return this;
	}
	
	public DefaultDialogSelectableList<Type> addItem(String title, Type code)
	{
		this.list.add(new DefaultSelectableListItem<Type>(title, code));
		
		return this;
	}
	
	public void display()
	{
		ListAdapter adapter = new ArrayAdapter<DefaultSelectableListItem<Type>>(this.context, android.R.layout.select_dialog_item, android.R.id.text1, this.list)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);
				TextView text = (TextView)view.findViewById(android.R.id.text1);
				text.setCompoundDrawablesWithIntrinsicBounds(DefaultDialogSelectableList.this.list.get(position).getIcon(), 0, 0, 0);
				
				return view;
			}
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
		builder.setTitle(this.title);
		builder.setCancelable(this.cancelable);
		
		if (this.iconId != 0)
		{
			builder.setIcon(this.iconId);
		}
		
		builder.setAdapter(adapter, new OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int index)
			{
				select(DefaultDialogSelectableList.this.list.get(index).getCode());
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void select(Type item)
	{
		if (this.callbackItemSelected != null)
		{
			this.callbackItemSelected.onItemSelected(item);
		}
	}
	
	private static class DefaultSelectableListItem<ItemType>
	{
		private final String itemTitle;
		private final ItemType itemCode;
		private final int itemIcon;
		
		public DefaultSelectableListItem(String title, ItemType code, int icon)
		{
			this.itemTitle = title;
			this.itemCode = code;
			this.itemIcon = icon;
		}
		
		public DefaultSelectableListItem(String title, ItemType code)
		{
			this.itemTitle = title;
			this.itemCode = code;
			this.itemIcon = 0;
		}
		
		public int getIcon()
		{
			return this.itemIcon;
		}
		
		public ItemType getCode()
		{
			return this.itemCode;
		}
		
		@Override
		public String toString()
		{
			return ("   " + this.itemTitle);
		}
	}
	
	public interface OnSelectItem<Type>
	{
		void onItemSelected(Type item);
	}
}