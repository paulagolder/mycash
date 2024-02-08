package org.lerot.MyCash;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class gc_account_tree implements TreeModel{
	
	gc_account_tree_node rootaccount;
	
	gc_account_tree (gc_account_tree_node topaccount)
	{
		rootaccount = topaccount;
	}

	@Override
	public Object getRoot()
	{
		return rootaccount;
	}

	@Override
	public Object getChild(Object parent, int index)
	{
		return ((gc_account_tree_node)parent).children.get(index);
	}

	@Override
	public int getChildCount(Object parent)
	{
		// TODO Auto-generated method stub
		return ((gc_account_tree_node)parent).getChildCount();
	}

	@Override
	public boolean isLeaf(Object node)
	{
		return ((gc_account_tree_node)node).isLeaf();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIndexOfChild(Object parent, Object child)
	{
		return ((gc_account_tree_node)parent).getIndexOfChild(child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l)
	{
		// TODO Auto-generated method stub
		
	}

}
