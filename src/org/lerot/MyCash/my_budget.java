package org.lerot.MyCash;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.tree.TreeNode;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class my_budget
{

	private String name;
	private String description;
	private mb_accounts accounts;




	public my_budget(Element node)
	{
		loadBudget(node);
	}

	private gc_transactions transactions;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String toString()
	{
		return this.getName();
	}

	public void loadBudget(Element node)
	{
		accounts = new mb_accounts();

		name = node.attributeValue("name");
		description = node.attributeValue("description");
	
		List<Node> accountslist = node.selectNodes("account");
		for (Node anode : accountslist)
		{
			accounts.add(new mb_account(anode));
			
		}

	}

	public mb_accounts getAccounts()
	{
		return accounts;
	}

	public void setAccounts(mb_accounts accounts)
	{
		this.accounts = accounts;
	}

	public gc_transactions getTransactions()
	{
		return transactions;
	}

	public void setTransactions(gc_transactions transactions)
	{
		this.transactions = transactions;
	}

}
