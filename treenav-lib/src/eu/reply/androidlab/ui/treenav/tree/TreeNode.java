package eu.reply.androidlab.ui.treenav.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a general purpose node free to have any number of children.
 * 
 * @author Diego Palomar
 * @version 1.0.0
 *
 * @param <T> Type of the data stored into the node.
 */
public class TreeNode<T>
{ 
	/**
	 * Data stored in the node.
	 */
    public T mData;
    
    /**
     * Contains the list of children
     */
    public List<TreeNode<T>> mChildrenList;

    /**
     * Parent of the node. 
     */
    private TreeNode<T> mParent;
    
    /**
     * Depth level of the node into the tree. For this value to be assigned should 
     * first calculated the level traversal of the tree to which it belongs.
     */
    private int mDepthLevel;
    
    
    /**
     * Simple constructor used to create a node.
     */
    public TreeNode() 
    {
        super();
        mChildrenList = new ArrayList<TreeNode<T>>();
    }

    /**
     * Simple constructor used to create a node.
     * 
     * @param data The data to be stored in the node.
     */
    public TreeNode(T data) 
    {
        this();
        setData(data);
    }
    
    public TreeNode<T> getParent() 
    {
        return mParent;
    }

    public void setParent(TreeNode<T> parent) 
    {
        mParent = parent;
    }

    public int getNumberOfChildren() 
    {
        return mChildrenList.size();
    }

    public boolean hasChildren() 
    {
        return !mChildrenList.isEmpty();
    }
    
    public List<TreeNode<T>> getChildren() {
        return mChildrenList;
    }
    
    public int getChildIndex(TreeNode<T> child) 
    {
    	return getChildren().indexOf(child);
    }
    
    public void setChildren(List<TreeNode<T>> children)
    {
        for (TreeNode<T> child : children)
        {
            child.setParent(this);
        }
            
        mChildrenList = children;
    }

    public void addChild(TreeNode<T> child) 
    {
        child.setParent(this);
        child.setDepthLevel(mDepthLevel + 1);
        
        mChildrenList.add(child);
        
        if (child.hasChildren()) {
        	updateChildrenDepthLevel(child);
        }
    }
    
    private void updateChildrenDepthLevel(TreeNode<T> parentNode) {
    	
    	List<TreeNode<T>> children = parentNode.getChildren();
    	
    	for (TreeNode<T> child : children) 
    	{
    		child.setDepthLevel(parentNode.getDepthLevel() + 1);
    		
    		if (child.hasChildren()) 
    		{
    			updateChildrenDepthLevel(child);
    		}
    	}
    }
    
    public void addAllChildren(List<? extends TreeNode<T>> children)
    {
        for (TreeNode<T> child : children) 
        {
        	addChild(child);
        }
    }

    public void addChildAt(int index, TreeNode<T> child) throws IndexOutOfBoundsException 
    {
        child.setParent(this);
        child.setDepthLevel(mDepthLevel + 1);
        
        mChildrenList.add(index, child);
    }

    public void removeChildren() 
    {
        mChildrenList.clear();
    }

    public void removeChildAt(int index) throws IndexOutOfBoundsException 
    {
        mChildrenList.remove(index);
    }
    
    /**
     * Removes all children from the specified index.
     * 
     * @param index Index from which the child nodes has to be removed.
     */
    public void removeChildrenFrom(int index) 
    {
    	int numChildren = mChildrenList.size();
    	
    	if (index < 0 || index >= numChildren) 
    	{
    		throw new IndexOutOfBoundsException("Trying to remove children from index " + 
    				index + ". Current number of children " + numChildren);
    	}
    	
    	// Offset required because we're going to modifying a list from a loop, 
    	// so the list indexes will be different compared with the original list.
    	int indexOffset = 0;
    	
        for (int i = index ; i < numChildren ; i++) 
        {
        	removeChildAt(i - indexOffset++);
        }
    }

    public TreeNode<T> getChildAt(int index) throws IndexOutOfBoundsException 
    {
        return mChildrenList.get(index);
    }

    public T getData() 
    {
        return mData;
    }

    public void setData(T data) 
    {
        mData = data;
    }
    
    public int getDepthLevel() 
    {
        return mDepthLevel;
    }

    public void setDepthLevel(int level)
    {
        mDepthLevel = level;
    }

    public String toString() 
    {
        StringBuilder objectDescription = new StringBuilder();
        objectDescription.append(mData.toString()).append(" (L");
        objectDescription.append(mDepthLevel).append(")");
        
        return objectDescription.toString();
    }

    public boolean equals(TreeNode<T> node) 
    {
        return mData.equals(node.getData());
    }
}