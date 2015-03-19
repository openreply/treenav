package com.example.treenavsample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import eu.reply.androidlab.ui.treenav.utils.BaseViewHolder;
import eu.reply.androidlab.ui.treenav.utils.CellListFactory;

/**
 * Factory class responsible to provide details about the different view types that have to be shown 
 * in the ListView.
 */
public class MenuCellListFactory extends CellListFactory<String>
{
	private final int CELL_TYPE_COUNT = 1;
	
	@Override
	public int getViewTypeCount() {
		return CELL_TYPE_COUNT;
	}

	@SuppressLint("InflateParams")
	@Override
	public View inflateCellView(Context context, int cellTypeRequested) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);		
		return layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
	}
	
	@Override
	public BaseViewHolder<String> createViewHolder(int cellTypeRequested, View convertView) {
		return new CellViewHolder(convertView);
	}
}
