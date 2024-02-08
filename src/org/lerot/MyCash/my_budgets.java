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

public class my_budgets extends ArrayList<my_budget>
{

	private static final long serialVersionUID = 1L;

	public my_budgets()
	{
		loadBudgets(MyCash_gui.budgetfile);
	}

	private void loadBudgets(String filename)
	{

		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(filename));
			List<Node> nodeList = document.selectNodes("//budget");
			for (Node node : nodeList)
			{
				add(new my_budget((Element) node));

			}

		} catch (DocumentException e)
		{
			System.out.println(" failed opening " + filename + "\n" + e);

		}
	}

}
