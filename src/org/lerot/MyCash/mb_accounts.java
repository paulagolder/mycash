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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.tree.TreeNode;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class mb_accounts extends ArrayList<mb_account> implements ListModel
{

	private static final long serialVersionUID = 1L;
	
	@Override
	public int getSize()
	{	
		return super.size();
	}

	@Override
	public Object getElementAt(int index)
	{
		return super.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void removeListDataListener(ListDataListener l)
	{
		// TODO Auto-generated method stub
	}

	public void totalTransfers()
	{
		for( mb_account mba : this)
		{
			mba.totalTransfers();
		}
	}
	
	public String budgetsummary()
	{
		String output ="";
		output += heading();
		for( mb_account mba : this)
		{
			output += mba.accountrowToTXT();
		}
		return output;
	}
	
	public String budgetsummaryToCSV()
	{
		String output ="";
		output += headingToCSV();
		for( mb_account mba : this)
		{
			output += mba.accountrowToCSV();
		}
		return output;
	}


	private String headingToCSV()
	{
		String outline = " , ,opening, total, total, closing \n";
		  outline += " , ,balance, receipts, dispenses, balance \n";
		return outline;
	}

	private String heading()
	{	
	        String outline = utils.leftpad(50, "") ;
	        outline += "  opening  total    total  closing \n";
	        outline += utils.leftpad(50, "") ;
	        outline += "  balance  receipts dispenses  balance \n";
	        return outline;		
	}

	

}
