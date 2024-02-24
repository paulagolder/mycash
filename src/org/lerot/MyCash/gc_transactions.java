package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class gc_transactions extends LinkedHashMap<String, gc_transaction>
{

	private static final long serialVersionUID = 1L;
	
	private float totalin = 0.0f;
	private float totalout = 0.0f;
	private float opening_balance = 0.0f;

	
	public gc_transactions(gc_account gcaccount)
	{
		System.out.println(" haer 13 ");
		String query = "select t.guid  from splits s , transactions t where s.tx_id = t.guid and s.tx_guid =\""+ gcaccount.getAccount_id()+"\" ";
	
		PreparedStatement st= null;
		ResultSet resset = null;
		Connection con = null;
		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{  
				 	String TransactionID = resset.getString("guid");
					gc_transaction transaction = gc_transaction.getTransaction(TransactionID);				
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}finally {
		    try { 
		    	resset.close(); 
		    st.close(); 
		     con.close(); } catch (Exception e) { /* Ignored */ }
		}
		
	}

	public gc_transactions()
	{
		totalin = 0.0f;
		totalout = 0.0f;
		 opening_balance = 0.0f;
	}
	
	public static gc_transactions gettransactions(String code)
	{
		gc_transactions trans = new gc_transactions();
		String query = "";
		if (code == null || code == "")
		{
			query = "SELECT t.*  ";
			query += " FROM  transactions t , splits s , accounts a WHERE t.guid = s.tx_guid and s.account_guid = a.guid and a.account_type = \"EXPENSE\" and ( t.num = \"\" or t.num is null ) order by t.post_date ";

		} else
		{
			query = "SELECT t.* ";
			query += " FROM  transactions t WHERE  t.num = \"" + code
					+ "\"  order by t.post_date ";
		}

		PreparedStatement st = null;
		ResultSet resset = null;
		Connection con = null;
		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				 gc_transaction atrans = new gc_transaction(resset);
				 atrans.getEntries();
				 atrans.totalTransfers();
				{				
					trans.put(atrans.TransactionID, atrans);
				}
			}
		} catch (Exception e)
		{
			System.out.println(e);
		} finally
		{
			try
			{
				resset.close();
				st.close();
				con.close();
			} catch (Exception e)
			{
			}
		}
       
		return trans;
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
		System.out.println(" haer 15 ");
		String query = "select * from transactions where 1 order by guid ";
		PreparedStatement st= null;
		ResultSet resset = null;
		Connection con = null;
		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				gc_transaction atrans = new gc_transaction(resset);
				atrans.getEntries();
				put(atrans.TransactionID, atrans);
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

	public void totalTransfers()
	{
		 totalin = 0.0f;
		 totalout = 0.0f;
		 opening_balance = 0.0f;
		for(java.util.Map.Entry<String, gc_transaction> nextt : this.entrySet())
		{
			gc_transaction atransaction = (gc_transaction)nextt.getValue();
			 atransaction.totalTransfers();
			 totalin +=  atransaction.getTotalin();
			 totalout +=  atransaction.getTotalout();
		     opening_balance  +=  atransaction.getOpening_balance();
		}
	}
	
	public String heading()
	{
		String output = "totalin ="+totalin+" totalout="+totalout;
		return output+"\n";
	}	

	public float getTotalin()
	{
		return totalin;
	}

	public void setTotalin(float totalin)
	{
		this.totalin = totalin;
	}

	public float getTotalout()
	{
		return totalout;
	}

	public void setTotalout(float totalout)
	{
		this.totalout = totalout;
	}

	public float getOpening_balance()
	{
		return opening_balance;
	}

	public void setOpening_balance(float opening_balance)
	{
		this.opening_balance = opening_balance;
	}

	public String toCSV()
	{
		// paul TODO Auto-generated method stub
		return null;
	}

	public boolean isNull()
	{
		if(size()>0) return false;
		if (opening_balance > 0)
			return false;
		if (totalin > 0)
			return false;
		if (totalout > 0)
			return false;
		return true;
	}

	public String toTXT()
	{
		// TODO Auto-generated method stub
		return " txxxxxxt ";
	}

	public String list()
	{
		String output ="";
		for(java.util.Map.Entry<String, gc_transaction> nextt : this.entrySet())
		{
			output += ((gc_transaction)nextt.getValue()).toString();
			 
		}
		return output;
	}



	

	
	

}
