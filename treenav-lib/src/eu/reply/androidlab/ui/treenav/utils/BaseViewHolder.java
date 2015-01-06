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