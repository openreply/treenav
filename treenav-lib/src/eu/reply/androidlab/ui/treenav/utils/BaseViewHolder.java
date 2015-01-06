package eu.reply.androidlab.ui.treenav.utils;

import eu.reply.androidlab.ui.treenav.adapter.TreeAdapter;
import android.view.View;


/**
 * Base class that has to to be extended for all ViewHolders used by the {@link TreeAdapter}.
 * 
 * @param <T> Type of the data that has to be render.
 *  
 * @version 1.0.0
 * 
 * @author Diego Palomar (d.palomar@replyltd.co.uk)
 * 
 */
public abstract class BaseViewHolder<T> {
	
	private int type;

	public BaseViewHolder(View view) {
	}
	
	/**
	 * Method invoked to display in the corresponding views the data of the model instance.
	 * 
	 * @param data Data model instance that has to be render.
	 */
	public abstract void displayNodeData(CommandTreeNode<T> node, int treeMaxDepth);
	
	/**
	 * Return the cell type that this ViewHolder represents.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set the cell type that this ViewHolder represents.
	 */
	public void setType(int type) {
		this.type = type;
	}
}