package org.lerot.MyCash;

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

import javax.swing.tree.TreeNode;

public class gc_account
{

	private String name;
	private String account_id;
	private String account_type;
	private String commodity_id;
	private String commodity_scu;
	private String parent_id;
	private String description;
	private String parent_name;
	private boolean isExpense;
	private float opening_balance;



	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAccount_id()
	{
		return account_id;
	}

	public void setAccount_id(String account_id)
	{
		this.account_id = account_id;
	}

	public String getAccount_type()
	{
		return account_type;
	}

	public void setAccount_type(String account_type)
	{
		this.account_type = account_type;
	}

	public String getCommodity_id()
	{
		return commodity_id;
	}

	public void setCommodity_id(String commodity_id)
	{
		this.commodity_id = commodity_id;
	}

	public String getCommodity_scu()
	{
		return commodity_scu;
	}

	public void setCommodity_scu(String commodity_scu)
	{
		this.commodity_scu = commodity_scu;
	}

	public String getParent_id()
	{
		return parent_id;
	}

	public void setParent_id(String parent_id)
	{
		this.parent_id = parent_id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setParent_name(String name2)
	{
		parent_name = name2;

	}

	public String toString()
	{
		return this.getName();
	}

	public gc_account(ResultSet resset)
	{
		try
		{
			String text = resset.getString("guid");
			setAccount_id(text);
			text = resset.getString("name");
			setName(text);
			text = resset.getString("account_type");
			setAccount_type(text);
			text = resset.getString("commodity_guid");
			setCommodity_id(text);
			text = resset.getString("commodity_scu");
			setCommodity_scu(text);
			text = resset.getString("parent_guid");
			setParent_id(text);
			text = resset.getString("description");
			setDescription(text);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	
	}

	public gc_account(String text)
	{

		setName(text);
	}

	public void logtoconsol()
	{
		if (getParent_id() != null)
			System.out.println(" account : " + getName() + " type:" + getAccount_type() + " description:"
					+ getDescription() + " parent:" + parent_name);
		else
			System.out.println(" account : " + getName() + " type:" + getAccount_type() + " description:"
					+ getDescription() + " parent: ROOOOT ");
	}

	public String getParent_name()
	{
		return parent_name;
	}

	public boolean getAllowsChildren()
	{
		return true;
	}


	public String heading()
	{
        String outline = name + " " +  description + "\n\n";
     
        return outline;
	}

	public boolean isExpense()
	{
		return isExpense;
	}

	public void setExpense(boolean bool)
	{
		this.isExpense = bool;
	}

	
	
	}
	
	

	

	

	

	
	



