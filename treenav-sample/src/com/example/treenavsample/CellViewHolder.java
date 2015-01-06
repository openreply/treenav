package com.example.treenavsample;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import eu.reply.androidlab.ui.treenav.utils.BaseViewHolder;
import eu.reply.androidlab.ui.treenav.utils.CommandTreeNode;

public class CellViewHolder  extends BaseViewHolder<String> {

	private TextView mTextView;
	
	public CellViewHolder(View view) {
		super(view);
		
		mTextView = (TextView) view;
	}

	@Override
	public void displayNodeData(CommandTreeNode<String> node, int treeMaxDepth) {		
		
		String cellText     = getText(node, treeMaxDepth);
		int backgroundColor = getBackgroundColor(node, treeMaxDepth);
		
		mTextView.setText(cellText);
		mTextView.setBackgroundColor(backgroundColor);
		mTextView.setTextColor(Color.DKGRAY);
	}
	
	private String getText(CommandTreeNode<String> node, int treeMaxDepth) {
		
		String text = node.getData();
		int nodeDepthLevel = node.getDepthLevel();
		
		if (nodeDepthLevel > 0) {
			for (int i = 0 ; i < nodeDepthLevel ; i++) {
				text = "\t\t" + text;
			}
		}
		return text;
	}
	
	private int getBackgroundColor(CommandTreeNode<String> node, int treeMaxDepth) {
		
		int color = 0;
		
		switch (node.getDepthLevel()) {
		case 0:
			color = 0xFF00A88F;
			break;
			
		case 1:
			color = 0xFF33B8A5;
			break;
			
		case 2:
			color = 0xFF84D4C9;
			break;
			
		case 3:
			color = 0xFFCFEEE9;
			break;
		}
		return color;
	}
}
