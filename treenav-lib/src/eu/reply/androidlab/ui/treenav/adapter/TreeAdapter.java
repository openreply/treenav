/*
 * Copyright (C) 2015 Open Reply
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.reply.androidlab.ui.treenav.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import eu.reply.androidlab.ui.treenav.tree.TreeNode;
import eu.reply.androidlab.ui.treenav.utils.BaseViewHolder;
import eu.reply.androidlab.ui.treenav.utils.CellListFactory;
import eu.reply.androidlab.ui.treenav.utils.CommandTreeNode;

/**
 * <p>Adapter backed by a list of arbitrary objects and used to feed a ListView which should show 
 * information from a Tree-type data source. By default this class is not responsible to fill the 
 * views of the items from the list, this task will be made by an instance of a class that implements 
 * the {@link CellListFactory} interface, which should be provided by the client of this class.</p>
 *
 * @param <T> Type of the data stored in a tree's node.
 *  
 * @version 1.0.0
 * 
 * @author Diego Palomar (d.palomar@replyltd.co.uk)
 *
 */
public class TreeAdapter<T> extends ArrayAdapter<CommandTreeNode<T>> implements OnItemClickListener
{
	
	private Context mContext;
	
	/** Adapter responsible to display the information of the data source in the cells of the ListView. */
	private CellListFactory<T> mListItemAdapter;
	
    /** Contains the list of objects that represent the data of this Adapter.*/
	private List<CommandTreeNode<T>> mDataSourceList;
	
	/** The current maximum depth of the tree, that is the distance between the
      * root of the tree and one of the leaf in the current tree state. */
	private int mCurrentMaxDepth;
	
	/** The previous maximum depth of the tree, updated just before mCurrentMaxDepth change its value. */
	private int mPreviousMaxDepth;
	
	public List<CommandTreeNode<T>> getDataSourceList() {
		return mDataSourceList;
	}
	
	public TreeAdapter(Context context, CellListFactory<T> listItemAdapter, List<CommandTreeNode<T>> initialElements)
	{
		super(context, 0, initialElements);
		
		mContext = context.getApplicationContext();
		
		mListItemAdapter = listItemAdapter;
		
		mDataSourceList = initialElements;

		updateMaxDepth();
	}
	
	@Override
	public int getItemViewType(int position) {
		return getItem(position).getType();
	}

	@Override
	public int getViewTypeCount() {
		return mListItemAdapter.getViewTypeCount();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		CommandTreeNode<T> node = getItem(position);
		
		BaseViewHolder<T> viewHolder = null;
		
	    if (convertView == null)
	    {
	    	int cellType = getItemViewType(position);
	    	
	    	convertView = mListItemAdapter.inflateCellView(mContext, cellType);
	    	
	    	viewHolder = mListItemAdapter.createViewHolder(cellType, convertView);
	        
	        convertView.setTag(viewHolder);
	    } 
	    else 
	    {
	    	viewHolder = (BaseViewHolder<T>) convertView.getTag();
	    }

	    viewHolder.displayNodeData(node, mCurrentMaxDepth);
	    
	    return convertView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{	
		synchronized (mDataSourceList) {

			CommandTreeNode<T> categorySelected = mDataSourceList.get(position);
			
			int levelSelected = categorySelected.getDepthLevel();
			
			if (categorySelected.hasChildren())
			{
				// User has selected a category with sub-categories. Delete from the data source list all 
				// categories which depth level is greater or equal to the level of the category selected so 
				// from now we'll have in the list  only the category selected and its ancestors (before adding its children).
				
				List<TreeNode<T>> itemsToRemove = new ArrayList<TreeNode<T>>();
					
				for (TreeNode<T> node : mDataSourceList)
				{
					if (node.getDepthLevel() >= levelSelected && node != categorySelected)
					{
						itemsToRemove.add(node);
					}
				}
				
				mDataSourceList.removeAll(itemsToRemove);
				
				for (TreeNode<T> child : categorySelected.getChildren()) {
					mDataSourceList.add((CommandTreeNode<T>) child);
				}
				
				mPreviousMaxDepth = mCurrentMaxDepth;
				
				mCurrentMaxDepth = levelSelected + 1;
				
				notifyDataSetChanged();
			}

			categorySelected.execCommand();
		}
	}
	
	public boolean contains(CommandTreeNode<T> element)
	{
		return mDataSourceList.contains(element);
	}
	
	
	private void updateMaxDepth() {
		
		int tempMaxDepth = 0;
		
		for (TreeNode<T> node : mDataSourceList)
		{
			if (node.getDepthLevel() > tempMaxDepth)
			{
				tempMaxDepth = node.getDepthLevel();
			}
		}
		
		mCurrentMaxDepth = tempMaxDepth;
	}
	
	
	@Override
	public void notifyDataSetChanged() {
		updateMaxDepth();
		super.notifyDataSetChanged();
	}

	public int getPreviousMaxDepth() {
		return mPreviousMaxDepth;
	}
	
	public int getCurrentMaxDepth() {
		return mCurrentMaxDepth;
	}

	/**
	 * <p>Causes go back in the tree navigation (back step in the user navigation history) if the
	 * user has moved deep into the tree.</p>
	 * 
	 * @return True if the data source was updated, false otherwise.
	 */
	public boolean goBackOnNavigationHistory()
	{
		// Because the minimum tree depth is 0 and initial depth is 1, if the current tree 
		// depth is greater or equal than 2 implies that the user has moved into the tree.
		
		boolean treeUpdated = false;
		
		int newLevelCategorySelected = mCurrentMaxDepth - 2;
		
		if (newLevelCategorySelected >= 0)
		{
			this.onItemClick(null, null, newLevelCategorySelected, newLevelCategorySelected);
			treeUpdated = true;
		}
		
		return treeUpdated;
	}
}
