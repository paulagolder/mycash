package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class gc_transactions extends LinkedHashMap<String, gc_transaction>
{

	private static final long serialVersionUID = 1L;

	public gc_transactions(gc_account gcaccount)
	{
        gc_splits splits = new gc_splits(gcaccount.getName());
        
		String query = "select * from splits s where s.tx_guid =\""+ gcaccount.getAccount_id()+"\" ";
		PreparedStatement st= null;
		ResultSet resset = null;
		Connection con = null;

		int n = 0;

		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				
					gc_split asplit = new gc_split(resset);
					splits.put(asplit.Split_ID, asplit)	;	
					n=n+1;			
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}finally {
		    try { resset.close(); } catch (Exception e) { /* Ignored */ }
		    try { st.close(); } catch (Exception e) { /* Ignored */ }
		    try { con.close(); } catch (Exception e) { /* Ignored */ }
		}
		
		for(java.util.Map.Entry<String, gc_split> next  : splits.entrySet())
		{
			gc_split asplit = next.getValue();
			gc_transaction atrans = MyCash_gui.MyTransactions.get(asplit.Tx_ID);
			put(atrans.TransactionID,atrans);
		}
		
		
	
	}

	public gc_transactions()
	{
		// TODO Auto-generated constructor stub
	}

	public void add(gc_transactions transactions)
	{
		for (java.util.Map.Entry<String, gc_transaction> next : transactions.entrySet())
		{
			put(next.getKey(), next.getValue());
		}
	}


	
	public void getTransactions()
	{

		String query = "select * from transactions where 1 order by guid ";
		PreparedStatement st= null;
		ResultSet resset = null;
		Connection con = null;

		int n = 0;

		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				gc_transaction atrans = new gc_transaction(resset);
				atrans.richsplits = new gc_richsplits(atrans.TransactionID);
				put(atrans.TransactionID, atrans);
				n = n + 1;
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}finally {
		    try { resset.close(); } catch (Exception e) { /* Ignored */ }
		    try { st.close(); } catch (Exception e) { /* Ignored */ }
		    try { con.close(); } catch (Exception e) { /* Ignored */ }
		}
	}

	public String toString()
	{
		String output = "";
		for (java.util.Map.Entry<String, gc_transaction> next : this.entrySet())
		{
			output += next.getValue().toString() ;
		}
		return output;
	}

	public gc_transactions sortTransactions(gc_transactions inlist)
	{
		ArrayList<gc_transaction> unsorted = new ArrayList<gc_transaction>();
		for (java.util.Map.Entry<String, gc_transaction> next : inlist.entrySet())
		{
			unsorted.add(next.getValue());
		}
		Collections.sort(unsorted,new Comparator<gc_transaction>() 
		{
            public int compare(gc_transaction o1, gc_transaction o2) {return o1.getPost_date().compareTo(o2.getPost_date()); }
        });
			
		gc_transactions sorted = new gc_transactions();
		for (gc_transaction next : unsorted)
		{
			sorted.put(next.TransactionID, next);
		}
		return sorted;
	}


	
	

}
