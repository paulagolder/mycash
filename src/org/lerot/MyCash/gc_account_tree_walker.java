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

	public gc_account_tree_walker(String name, gc_account_tree_node anode)
	{
		topnode = anode;
		//anode.transsplits = new gc_transsplits();
	}

	public void getAllChildren(gc_account_tree_node thisnode)
	{

		if (thisnode.children.size() > 0)
		{
			for (gc_account_tree_node next : thisnode.children)
			{
				this.getAllChildren(next);
			}
		} else
		{
			gc_account childaccount = thisnode.account;
			//thisnode.transsplits = new gc_transsplits(childaccount);
			gc_account_tree_node pnode = thisnode.parent;
			while (pnode != null)
			{
			/*	if (pnode.transsplits == null)
				{
					pnode.transsplits = new gc_transsplits();
				}
				pnode.transsplits.add(thisnode.transsplits);*/
				pnode = pnode.parent;
			}
		}
	}

	public void sortAllChildren(gc_account_tree_node thisnode)
	{

		if (thisnode.children.size() > 0)
		{
			//thisnode.transsplits = thisnode.transsplits.sortTransplits();
			for (gc_account_tree_node next : thisnode.children)
			{
				this.sortAllChildren(next);
			}
		} else
		{
			//thisnode.transsplits = thisnode.transsplits.sortTransplits();
		}
	}

	public void totalTransfers(gc_account_tree_node thisnode)
	{
		float totalin = 0.0f;
		float totalout = 0.0f;
		float closing_balance = 0.0f;
		float opening_balance = 0.0f;
		if(thisnode.account.getName().equalsIgnoreCase("income"))
		{
			System.out.println(" ");
		}
		if (thisnode.children.size() > 0)
		{
			for (gc_account_tree_node next : thisnode.children)
			{
				totalTransfers(next);		
				totalin += next.account.transactions.getTotalin();
				totalout += next.account.transactions.getTotalout();
				opening_balance += next.account.transactions.getOpening_balance();			
			}
			thisnode.account.transactions.setTotalin(totalin);
			thisnode.account.transactions.setTotalout(totalout);
			thisnode.account.transactions.setOpening_balance(opening_balance);
		} else
		{		
            thisnode.account.transactions.totalTransfers();
		}
	}

	
	
	
	
	public String printAllChildren(gc_account_tree_node thisnode)
	{
		String output = "";
        gc_account thisacc = thisnode.account;
        if(!(thisnode.account.transactions.isNull())) 
        	{
        output += "\n"+thisacc.heading();
        output += "   Opening Balance: " + thisnode.account.transactions.getOpening_balance();
        output += " Income: "+thisnode.account.transactions.getTotalin();
        output += " Expense: "+thisnode.account.transactions.getTotalout()+"\n\n";
       // output += " Balance: "+thisnode.transsplits.getClosing_balance()+"\n\n";
        	}
		if (thisnode.children.size() > 0)
		{
			for (gc_account_tree_node next : thisnode.children)
			{
				output += this.printAllChildren(next);
			}
		} else
		{
			gc_account childaccount = thisnode.account;
			output += thisnode.account.transactions.toString();
			return output;
		}
		return output;

	}

	public String toCSV(gc_account_tree_node thisnode)
	{
		String output = "";

		if (thisnode.children.size() > 0)
		{
			for (gc_account_tree_node next : thisnode.children)
			{
				output += this.toCSV(next);
			}
		} else
		{
			gc_account childaccount = thisnode.account;
			output += thisnode.account.transactions.toCSV();
			return output;
		}
		return output;
	}

}
