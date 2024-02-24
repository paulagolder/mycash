package org.lerot.MyCash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.tree.TreeModel;

import org.lerot.MyCash.gc_account;

public class gc_account_tree_node
{

	gc_account account;
	List<gc_account_tree_node> children;
	gc_account_tree_node parent;


	public gc_account_tree_node(String name, gc_account topaccount)
	{
		account = topaccount;
		parent = null;
		//account.transactions = new gc_account.transactions();
		children = new ArrayList<gc_account_tree_node>();
		gc_accounts childaccounts = MyCash_gui.MyAccounts.getChildrenArray(topaccount);
		if (childaccounts.size() > 0)
		{
			for (Entry<String, gc_account> next : childaccounts.entrySet())
			{
				children.add(new gc_account_tree_node(this, next.getValue()));
			}
		}
	}

	public gc_account_tree_node(gc_account_tree_node aparent, gc_account childaccount)
	{
		account = childaccount;
		parent = aparent;

		children = new ArrayList<gc_account_tree_node>();
		gc_accounts grandchildaccounts = MyCash_gui.MyAccounts.getChildrenArray(childaccount);
		if (grandchildaccounts.size() > 0)
		{
			for (Entry<String, gc_account> next : grandchildaccounts.entrySet())
			{
				children.add(new gc_account_tree_node(this, next.getValue()));
			}
		} 

	}

	private gc_transactions makeTransactions(gc_account account2)
	{
		gc_transactions ttransactions = new gc_transactions(account2);
		return ttransactions;
	}

	public String toString()
	{
		return account.getName();
	}

	public int getChildCount()
	{
		return children.size();
	}

	public boolean isLeaf()
	{
		if (getChildCount() > 0)
			return false;
		return true;
	}

	public int getIndexOfChild(Object child)
	{
		int i = 0;
		for (gc_account_tree_node achild : children)
		{
			i = i + 1;
			if (achild.account.getAccount_id().equalsIgnoreCase(((gc_account_tree_node) child).account.getAccount_id()))
				return i;
		}
		return -1;
	}

	public gc_account_tree_node getChild(gc_account_tree_node anode, int index)
	{
		return anode.children.get(index);
	}

	public List<gc_account_tree_node> getChildren()
	{
		return children;
	}

	

	public String printSummary()
	{
		String output = " ";
		float totin = account.transactions.getTotalin();
		String vi = String.format("%8.2f", totin);
		float totout = account.transactions.getTotalout();
		String vo = String.format("%8.2f", totout);
		float open = account.transactions.getOpening_balance();
		float close = 1234f;
		String  vopen =  String.format("%8.2f", open);
		String  vclose =  String.format("%8.2f",close);

		output += utils.leftpad(40, account.getName());
		output += utils.rightpad(10, vopen);
		output += utils.rightpad(10, vi);
		output += utils.rightpad(10, vo);
		output += utils.rightpad(10, vclose);
		return output + "\n";
	}

	public String printIncomeSummary()
	{
		String output = " ";
		float totin = account.transactions.getTotalin();
		float totout = account.transactions.getTotalout();
		String vt =  String.format("%8.2f", totout+totin);
		output += utils.leftpad(40, account.getName());
		output += utils.rightpad(10,vt );
		return output + "\n";
	}
	
	public String printOpeningSummary()
	{
		String output = " ";
		float totin = account.transactions.getTotalin();
		float totout = account.transactions.getTotalout();
		float open = account.transactions.getOpening_balance();
		float close = 1234f;

		String vt =  String.format("%8.2f", open);
		output += utils.leftpad(40, "Opening Balances");
		output += utils.rightpad(10,vt );
		return output + "\n";
	}

	public String printAssetsSummary()
	{
		String output = " ";
		float totin = account.transactions.getTotalin();
		float totout = account.transactions.getTotalout();
		float close = 1234f;
		String vt =  String.format("%8.2f", close);
		output += utils.leftpad(40, "Assets");
		output += utils.rightpad(10,vt );
		return output + "\n";
	}
	
	public gc_account_tree_node getnode(String name)
	{
		for (gc_account_tree_node achild : children)
		{	
			if (achild.account.getName().startsWith(name))
				return achild;
		}
		return null;
	}

	public gc_transactions collectTransactions()
	{
		gc_transactions  transactionlist = new gc_transactions();	
		if(children.isEmpty())
		{
			return account.transactions;
		}
		else
		{
		for (gc_account_tree_node achild : children)
		{
			gc_transactions trans = achild.collectTransactions();
			
    		transactionlist.add(trans);
		}
		return transactionlist;
		}
	
	}

	

	

}
