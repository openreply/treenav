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

import eu.reply.androidlab.ui.treenav.tree.TreeNode;

/**
 * Base class for the tree nodes. This class has to be used to specify the type of node and the action
 * the cell has to perform when the user selects it.
 * 
 * @author Diego Palomar (d.palomar@replyltd.co.uk)
 * 
 */
public class CommandTreeNode<T> extends TreeNode<T>
{
	public interface Command
	{
		void executeCommand();
	}
	
	private int mType = 0;
	
	private Command mCommand;
	
	public CommandTreeNode() {
		// Empty constructor
	}
	
	public CommandTreeNode(T data)
	{
		super(data);
	}
	
	public CommandTreeNode<T> setCommand(Command command)
	{
		mCommand = command;
		return this;
	}
	
	public boolean hasCommand()
	{
		return mCommand != null ? true : false;
	}

	public CommandTreeNode<T> setType(int type) {
		mType = type;
		return this;
	}
	
	public int getType() {
		return mType;
	}

	public final void execCommand()
	{
		if (mCommand != null) mCommand.executeCommand();
	}
}
