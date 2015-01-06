package eu.reply.androidlab.ui.treenav.utils;

import eu.reply.androidlab.ui.treenav.adapter.TreeAdapter;
import android.content.Context;
import android.view.View;

/**
 * This class has to be extended to provide a Factory that manage the cell views of a {@link ViewGroup}.
 * This interface defines the methods required to allow a concrete class display the tree in a 
 * custom way depending of the cell-type that has to be render.
 * 
 * @param <T> Type of the data that will be displayed in the cells of the ListView.
 * 
 * @author Diego Palomar (d.palomar@replyltd.co.uk)
 */
public abstract class CellListFactory<T> 
{
	/**
	 * Returns the number of view types that will be created by the getView of the adapter. Each type 
	 * represents a set of views that can be converted in getView. If the adapter always returns the same 
	 * type of View for all items, this method should return 1.
	 * 
	 * @return The number of types of Views that will be created by the instance of the {@link TreeAdapter}.
	 */
	public int getViewTypeCount() {
		return 1;
	}

	
	/**
	 * Inflate a new view hierarchy based on the cell type specified.
     *
	 * @param context Context required to retrieve a standard LayoutInflater instance.
	 * @param cellType Cell type of the view that has to be inflated
	 * @return
	 */
	public abstract View inflateCellView(Context context, int cellType);
	
	
	/**
	 * Create a ViewHolder/Wrapper that contains references to the views that make up a cell of the ListView.
	 *
     * @param cellType Cell type of the view that has to be created
	 * @param convertView Root of the view hierarchy that represents the cell.
	 * 
	 * @return A view holder containing references to all views that make up a cell of the ListView.
	 */
	public abstract BaseViewHolder<T> createViewHolder(int cellType, View convertView);
}
