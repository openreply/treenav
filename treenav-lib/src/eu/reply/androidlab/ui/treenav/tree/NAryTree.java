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

package eu.reply.androidlab.ui.treenav.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a general purpose tree where the nodes are free to have any number of children.
 * The class contains several utility methods to manipulate the tree as well as get and set information.
 * 
 * @author Diego Palomar (d.palomar@replyltd.co.uk)
 * @version 1.0.0
 *
 * @param <T> Type of the data stored in a tree's node.
 */
public class NAryTree<T>
{
	/**
	 * Root node of the data structure.
	 */
	private TreeNode<T> mRoot;

	
	/**
	 * Create a tree with the specified root node.
	 * 
	 * @param root Root node of the data structure.
	 */
	public NAryTree(TreeNode<T> root)
	{
		mRoot = root;
		mRoot.setDepthLevel(0);
	}

	
	/**
	 * Returns whether the tree contains or not elements.
	 * 
	 * @return true if this tree has no elements, false otherwise.
	 */
	public boolean isEmpty()
	{
		return (mRoot == null) ? true : false;
	}

	
	public TreeNode<T> getRoot()
	{
		return mRoot;
	}

	
	public void setRoot(TreeNode<T> root)
	{
		this.mRoot = root;
	}
	
	
	/**
	 * Returns a ordered list with the full path of the node starting from the root of the tree. 
	 * 
	 * @param node
	 * 
	 * @return Ordered list with the full path of the parameter.
	 */
	public ArrayList<TreeNode<T>> getFullPath(TreeNode<T> node)
	{
		ArrayList<TreeNode<T>> fullPath = new ArrayList<TreeNode<T>>();
		searchPath(node, fullPath);
		
		return fullPath;
	}
	
	/**
	 * Auxiliary recursive method to navigate from a specific node to the root of the tree. 
	 */
	private void searchPath(TreeNode<T> node, ArrayList<TreeNode<T>> path)
	{
		path.add(0, node);
		
		TreeNode<T> parent = node.getParent();
		
		if (parent != null)
		{
			searchPath(parent, path);
		}
	}
	
	
	/**
	 * @return Return a list with the preorder traversal of the tree.
	 */
	public ArrayList<TreeNode<T>> getPreOrderTraversal() 
	{
		ArrayList<TreeNode<T>> preOrderList = new ArrayList<TreeNode<T>>();
		
		buildPreOrder(mRoot, preOrderList);
		
		return preOrderList;
	}

	
	/**
	 * Auxiliary recursive method to build the preorder traversal of the tree. 
	 */
	private void buildPreOrder(TreeNode<T> node, ArrayList<TreeNode<T>> preOrderList) 
	{
		preOrderList.add(node);
		
		for (TreeNode<T> child : node.getChildren()) 
		{
			buildPreOrder(child, preOrderList);
		}
	}
	
	/**
	 * Set to each node its depth level in the tree through a level traversal.
	 * 
	 * @return Linked list with the level traversal of the tree.
	 */
	public LinkedList<TreeNode<T>> calcNodesLevels(TreeNode<T> node)
	{
		LinkedList<TreeNode<T>> levelOrderList = new LinkedList<TreeNode<T>>();
		
		List<TreeNode<T>> queue = new ArrayList<TreeNode<T>>();
		
		queue.add((node == null) ? mRoot : node);
		
		TreeNode<T> currentNode;
		
		int currentDepthLevel;
			
		while (!queue.isEmpty())
		{
			currentNode = queue.remove(0);
			
			currentDepthLevel = currentNode.getDepthLevel() + 1;
			
			levelOrderList.add(currentNode);
			
			for (TreeNode<T> child : currentNode.getChildren())
			{
				child.setDepthLevel(currentDepthLevel);
				queue.add(child);
			}
		}
		
		return levelOrderList;
	}
	
	
	/**
	 * 
	 * @return Number of descendants of the node received as parameter.
	 */
	public int getNumberOfDescendants(TreeNode<T> node) 
	{
		int numDescendents = node.getChildren().size();
		
		for (TreeNode<T> child : node.getChildren())
		{
			numDescendents += getNumberOfDescendants(child);
		}

		return numDescendents;
	}
}
