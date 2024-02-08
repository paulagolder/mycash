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

	private float totalin;

	private float totalout;

	private float closing_balance;

	public gc_transsplits()
	{

	}

	public gc_transsplits(gc_account gcaccount)
	{

		String query = "SELECT s1.guid as split1id, t.guid as txid ,s2.guid as split2id ";
		query += " FROM splits s1 , splits s2, transactions t WHERE s1.tx_guid = t.guid  and s2.tx_guid = s1.tx_guid and s1.account_guid != s2.account_guid and s1.account_guid = \""
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
				String txid = resset.getString("txid");

				String split1id = resset.getString("split1id");
				String split2id = resset.getString("split2id");
				gc_transsplit atranssplit = new gc_transsplit(txid, split2id);
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
			} catch (Exception e)
			{
				/* Ignored */ }
			try
			{
				st.close();
			} catch (Exception e)
			{
				/* Ignored */ }
			try
			{
				con.close();
			} catch (Exception e)
			{
				/* Ignored */ }
		}
	}

	public gc_transsplits(String code)
	{

		String query = "SELECT s.guid as split1id, t.guid as txid  ";
		query += " FROM splits s , transactions t WHERE s.tx_guid = t.guid  and t.num = \"" + code
				+ "\"  order by t.post_date ";

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
				String txid = resset.getString("txid");
				String split1id = resset.getString("split1id");
				// String split2id = resset.getString("split2id");
				gc_transsplit atranssplit = new gc_transsplit(txid, split1id);
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
			} catch (Exception e)
			{
				/* Ignored */ }
			try
			{
				st.close();
			} catch (Exception e)
			{
				/* Ignored */ }
			try
			{
				con.close();
			} catch (Exception e)
			{
				/* Ignored */ }
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
		outline += "  closing balance =" + closing_balance + "\n";
		return outline;
	}
	public float getClosing_balance()
	{
		return closing_balance;
	}

	public float getTotalin()
	{
		return totalin;
	}

	public float getTotalout()
	{
		return totalout;
	}

	public void gettranssplits()
	{

		String query = "SELECT s.guid as splitid, t.guid as txid  ";
		query += " FROM splits s , transactions t WHERE s.tx_guid = t.guid ;";

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
				String txid = resset.getString("txid");
				String split1id = resset.getString("splitid");
				// String split2id = resset.getString("split2id");
				gc_transsplit atranssplit = new gc_transsplit(txid, split1id);
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
			} catch (Exception e)
			{
				/* Ignored */ }
			try
			{
				st.close();
			} catch (Exception e)
			{
				/* Ignored */ }
			try
			{
				con.close();
			} catch (Exception e)
			{
				/* Ignored */ }
		}
	}

	public String heading()
	{
		String outline = "  total receipts =" + totalin + " total dispenses =" + totalout + "\n\n";
		return outline;
	}

	public void setClosing_balance(float closing_balance)
	{
		this.closing_balance = closing_balance;
	}

	public void setTotalin(float totalin)
	{
		this.totalin = totalin;
	}

	public void setTotalout(float totalout)
	{
		this.totalout = totalout;
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

	public void totalTransfers()
	{
		totalin = 0.0f;
		totalout = 0.0f;

		for (gc_transsplit nextts : this)
		{
			if (nextts != null)
			{
				if (nextts.Value_number > 0)
				{
					totalin += ((float) nextts.Value_number) / nextts.Value_denom;
				} else
				{
					totalout += ((float) nextts.Value_number) / nextts.Value_denom;
				}
			}
		}
		closing_balance -= totalout;
	}

}
