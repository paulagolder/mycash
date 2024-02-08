package org.lerot.MyCash;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class gc_book
{

	TreeMap<String, gc_account> accounts;
	List<gc_transaction> transactions;

	
	public void logtoconsol()
	{
		System.out.println(" gnucash dump ");
		  for (Entry<String, gc_account> entry : accounts.entrySet())
		  {
		
			entry.getValue().logtoconsol();
		}	
			System.out.println(" "); 
		  for ( gc_transaction trans : transactions)
		  {
		
			trans.logtoconsol();
		}	
	}




}
