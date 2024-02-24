package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class gc_accounts extends LinkedHashMap<String, gc_account>
{

	private static final long serialVersionUID = 1L;
	private String name;

	public gc_accounts(String aname)
	{
		name = aname;
	}

	public void getAccounts()
	{
		System.out.println(" haer 14 ");
		java.lang.String query = "select * from accounts where 1 order by guid ";
		PreparedStatement st = null;
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
				gc_account anaccount = new gc_account(resset);
				if (anaccount == null)
				{
					System.out.println(" help ");
				} else if (anaccount.getAccount_type().equalsIgnoreCase("Template Root"))
				{
					System.out.println("here");
				} //else if (anaccount.getAccount_type().equalsIgnoreCase("Equity"))
				//{

				//}
			else if (anaccount.getName().toLowerCase().contains("orphan"))
				{

				} else if (anaccount.getName().toLowerCase().contains("imbalance"))
				{

				} else
				{
					anaccount.setTransactions(getTransactions(anaccount));
					put(anaccount.getAccount_id(), anaccount);
					
					n = n + 1;
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

		for (Entry<String, gc_account> entry : this.entrySet())
		{
			gc_account anaccount = entry.getValue();
			if (anaccount.getParent_id() != null)
			{
				gc_account parent = get(anaccount.getParent_id());
				anaccount.setParent_name(parent.getName());
			}
		}

	}

	public gc_accounts getChildrenArray(gc_account anaccount)
	{
		String headkey = anaccount.getAccount_id();

		gc_accounts childaccounts = new gc_accounts("children");
		for (Entry<java.lang.String, gc_account> entry : entrySet())
		{
			gc_account child = entry.getValue();
			if (child.getParent_id() != null)
			{
				if (headkey.equalsIgnoreCase(child.getParent_id()))
				{
					childaccounts.put(child.getAccount_id(), child);
				}
			}
		}
		return childaccounts;
	}

	public void setName(String text)
	{
		name = text;
	}

	public void logtoconsol()
	{
		for (Entry<String, gc_account> entry : this.entrySet())
		{
			gc_account anaccount = entry.getValue();
			anaccount.logtoconsol();
		}
	}

	public gc_account getTop()
	{
		for (Entry<String, gc_account> entry : this.entrySet())
		{
			gc_account anaccount = entry.getValue();
			if (anaccount.getParent_id() == null && anaccount.getName().equalsIgnoreCase("Root Account"))
			{
				return anaccount;
			}
		}
		return null;
	}

	public int indexOf(gc_account ch)
	{
		return this.indexOf(ch);
	}
	
	public gc_transactions getTransactions(gc_account gcaccount)
	{
		
		gc_transactions transaction_list = new gc_transactions();

		String query = "SELECT  s.tx_guid  ";
		query += " FROM splits s WHERE   s.account_guid = \""
				+ gcaccount.getAccount_id() + "\"  ";

		PreparedStatement st = null;
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
	            String txid = resset.getString("tx_guid");
				gc_transaction atransaction = gc_transaction.getTransaction(txid);
				transaction_list.put(atransaction.TransactionID,atransaction);
				n = n + 1;
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
		//System.out.println(" transactions found "+transaction_list.size());
		return transaction_list;
	}

	public void getEntries()
	{
		for( java.util.Map.Entry<String, gc_account>  next : this.entrySet())
		{
			
			gc_account anaccount = next.getValue();
			gc_transactions transactions = anaccount.getTransactions();
			for( java.util.Map.Entry<String, gc_transaction> nextt: transactions.entrySet())
			{
				nextt.getValue().getEntries();
			}
			
		}
		
	}


}
