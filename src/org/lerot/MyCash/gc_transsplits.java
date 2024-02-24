package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class gc_transsplits extends ArrayList<gc_transsplit>
{

	private static final long serialVersionUID = 1L;
	

	public gc_transsplits()
	{

	}

	public gc_transsplits(gc_transaction transaction)
	{

		String query = "SELECT *  ";
		query += " FROM splits s , transactions t WHERE s.tx_guid = t.guid   and t.guid = \""
				+ transaction.getTransactionID() + "\"  order by t.post_date ";
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
				gc_transsplit atranssplit = new gc_transsplit(resset);
				add(atranssplit);
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

	}

	public gc_transsplits(gc_account gcaccount)
	{

		// String query = "SELECT s1.guid as split1id, t.guid as txid ";

		String query = "SELECT *  ";
		query += " FROM splits s1 , transactions t WHERE s1.tx_guid = t.guid   and s1.account_guid = \""
				+ gcaccount.getAccount_id() + "\"  order by t.post_date ";

		PreparedStatement st = null;
		ResultSet resset = null;
		Connection con = null;

		int n = 0;
		;
		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				// String txid = resset.getString("txid");

				// String split1id = resset.getString("split1id");
				gc_transsplit atranssplit = new gc_transsplit(resset);
				add(atranssplit);
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
	}

	public gc_transsplits(String code)
	{
		String query = "";
		if (code == null || code == "")
		{
			query = "SELECT *  ";
			query += " FROM splits s , transactions t WHERE s.tx_guid = t.guid  and ( t.num = \"\" or t.num is null ) order by t.post_date ";

		} else
		{
			query = "SELECT * ";
			query += " FROM splits s , transactions t WHERE s.tx_guid = t.guid  and t.num = \"" + code
					+ "\"  order by t.post_date ";
		}

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
				// String txid = resset.getString("txid");
				// String split1id = resset.getString("split1id");
				// gc_split split = MyCash_gui.MySplits.get(split1id);

				String Account_ID = resset.getString("account_guid");
				gc_account account = MyCash_gui.MyAccounts.get(Account_ID);
				if (account.getAccount_type().equalsIgnoreCase("Expense"))
				{
					gc_transsplit atranssplit = new gc_transsplit(resset);
					add(atranssplit);
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
	}

	public void add(gc_transsplits transsplits)
	{
		for (gc_transsplit tsplit : transsplits)
		{
			add(tsplit);
		}
	}

	public String footing()
	{
		String outline = "\n";
		return outline;
	}

	

	public String heading()
	{
				return " FRED" ;
	}

	

	public gc_transsplits sortTransplits()
	{
		ArrayList<gc_transsplit> unsorted = new ArrayList<gc_transsplit>();
		for (gc_transsplit next : this)
		{
			unsorted.add(next);
		}
		Collections.sort(unsorted, new Comparator<gc_transsplit>() {
			public int compare(gc_transsplit o1, gc_transsplit o2)
			{
				return o1.getPost_date().compareTo(o2.getPost_date());
			}
		});
		gc_transsplits sorted = new gc_transsplits();
		for (gc_transsplit next : unsorted)
		{
			sorted.add(next);
		}
		return sorted;
	}

	public String toCSV()
	{
		String outstring = "";
		for (gc_transsplit tsplit : this)
		{
			outstring += tsplit.toCSV() + "\n";
		}
		return outstring;
	}

	public String toString()
	{
		String outstring = "";
		for (gc_transsplit tsplit : this)
		{
			outstring += tsplit.toString();
		}
		return outstring;
	}

	

	

	public String toTXT()
	{
		String outstring = "";
		for (gc_transsplit tsplit : this)
		{
			outstring += tsplit.toString();
		}
		return outstring;
	}

	

}
