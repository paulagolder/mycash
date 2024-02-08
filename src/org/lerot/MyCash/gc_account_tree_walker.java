package org.lerot.MyCash;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.tree.TreeModel;

import org.lerot.MyCash.gc_account;

public class gc_account_tree_walker 
{
	gc_account_tree_node topnode;

	public  gc_account_tree_walker(String name, gc_account_tree_node anode)
	{		
		topnode = anode;
		anode.transsplits = new gc_transsplits();
	}
	
	public  void getAllChildren(gc_account_tree_node thisnode)
	{
			
		if (thisnode.children.size() > 0)
		{
			for (gc_account_tree_node next : thisnode.children)
			{
				this.getAllChildren(next);
			}
		}
		else
		{
			 gc_account childaccount = thisnode.account;
			 thisnode.transsplits = new gc_transsplits(childaccount);
			 gc_account_tree_node pnode = thisnode.parent;
			 while(pnode != null)
			 {
				 if(pnode.transsplits == null)				 
				 {
					 pnode.transsplits = new gc_transsplits();
				 }
				 pnode.transsplits.add(thisnode.transsplits );
				 pnode = pnode.parent;
			 }
		}
	}
	
	
	public  void sortAllChildren(gc_account_tree_node thisnode)
	{
			
		if (thisnode.children.size() > 0)
		{
			 System.out.println(thisnode.account.getName()+"="+thisnode.transsplits.size());
			 thisnode.transsplits = thisnode.transsplits.sortTransplits();
			for (gc_account_tree_node next : thisnode.children)
			{
				this.sortAllChildren(next);
			}
		}
		else
		{
			 System.out.println(thisnode.account.getName()+"="+thisnode.transsplits.size());
			 thisnode.transsplits = thisnode.transsplits.sortTransplits();
			
		}
	}
	
	
	
	public  void  totalTransfers(gc_account_tree_node thisnode)
	{
			
		if (thisnode.children.size() > 0)
		{
			float totalin = 0.0f;
			float totalout = 0.0f;
			for (gc_account_tree_node next : thisnode.children)
			{
				totalTransfers(next);
				totalin += next.transsplits.getTotalin();
				totalout += next.transsplits.getTotalout();
			}
			thisnode.getTranssplits().setTotalin(totalin);
			thisnode.getTranssplits().setTotalout(totalout);
		}
		else
		{
			
			    float totalin = 0.0f;
				float totalout = 0.0f;

				for (gc_transsplit nextts : thisnode.transsplits)
				{
					if (nextts != null)
					{

						if (nextts.Value_number > 0)
						{
							totalin += ((float) nextts.Value_number) / nextts.Value_denom;
						} else
						{
							totalout += ((float) nextts.Value_number) / nextts.Value_denom;
						}
					}

				}
				thisnode.transsplits.setTotalin( totalin);
				thisnode.transsplits.setTotalin(totalout);
				//closing_balance -= totalout;
			}
			
		}
	

	
	
	
}
