package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map.Entry;

import org.dom4j.Element;
import org.dom4j.Node;

public class mb_account
{

	private String name;
	private String code;

	private gc_transactions transactions;
	gc_transsplits transsplits;
	private float opening_balance;
	private String description;
	private float totalin;
	private float totalout;
	private float closing_balance;

	public mb_account(Node anode)
	{
		name = ((Element) anode).attributeValue("name");
		code = ((Element) anode).attributeValue("code");
		description = ((Element) anode).attributeValue("description");
		transactions = getTransactions(code);
		transsplits = new gc_transsplits(code);

		if(((Element) anode).attributeValue("opening_balance") != null)
	    	opening_balance =  Float.parseFloat(((Element) anode).attributeValue("opening_balance"));
		else
			opening_balance = 0.00f;
		closing_balance = opening_balance;
	}

	public gc_transactions getTransactions(String code)
	{
		String query = " SELECT UNIQUE(s.tx_guid) as guid  FROM splits s, transactions t  WHERE   s.tx_guid = t.guid  ";
		query += " and t.num = \"" + code + "\"  order by t.post_date ";

		PreparedStatement st= null;
		ResultSet resset = null;
		Connection con = null;

		gc_transactions result = new gc_transactions();
		int n = 0;

		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				String txid = resset.getString("guid");
				gc_transaction newtransaction = MyCash_gui.MyTransactions.get(txid);
				result.put(txid, newtransaction);
				n = n + 1;
			}
			System.out.println(" Loaded budget account :" + name + " transactions =" + n);
		} catch (Exception e)
		{
			System.out.println(e);
		}finally {
		    try { resset.close(); } catch (Exception e) { /* Ignored */ }
		    try { st.close(); } catch (Exception e) { /* Ignored */ }
		    try { con.close(); } catch (Exception e) { /* Ignored */ }
		}
		return result;
	}

	

	public String heading()
	{
        String outline = name + "(" + code + ")" +  description + "\n";
        outline += "  opening balance =" + opening_balance+ "\n";
		outline += "  total receipts =" + totalin + " total dispenses =" + totalout + "\n\n";
        return outline;
	}
	
	
	public String accountrow()
	{
        String outline = utils.leftpad(10, code) + utils.leftpad(40, name+" "+ description) ;
        outline +=  String.format("%8.2f", opening_balance)+" ";
        outline +=  String.format("%8.2f", totalin)+" ";
        outline +=  String.format("%8.2f", totalout)+" ";
		outline +=  "\n";
        return outline;
	}
	
	public String footing()
	{
		 String outline = "\n";
	        outline += "  closing balance =" + closing_balance+ "\n";
	        return outline;
	}

	public void listTransactions()
	{
		if (transactions == null)
		{
			MyCash_gui.outputpanel.append(" no transaction for " + this.name + "\n");
			return;
		}
		if (transactions.size() < 0)
		{
			MyCash_gui.outputpanel.append(" no transactions " + "\n");
		} else
		{
			for (Entry<String, gc_transaction> next : transactions.entrySet())
			{
				if (next.getValue() == null)
				{
					MyCash_gui.outputpanel.append(" no transaction 2 " + next.getValue() + "\n");
				} else
					next.getValue().logtoconsol();
			}
		}
	}

	public void listTranssplits()
	{
		if (transsplits == null)
		{
			MyCash_gui.outputpanel.append(" no transsplits for " + this.name + "\n");
			return;
		}
		if (transsplits.size() < 0)
		{
			MyCash_gui.outputpanel.append(" no transsplits 2 " + "\n");
		} else
		{
			for (gc_transsplit nextts : transsplits)
			{
				if (nextts == null)
				{
					MyCash_gui.outputpanel.append(" no transsplit 3" + "\n");
				} else
				{
					if (nextts.Value_number > 0)
					{
						MyCash_gui.outputpanel.append(nextts.toString());
					}
				}
			}
		}
	}

	public String toString()
	{
		return name + "(" + code + ")" + "\n";
	}

	public void totalTransfers()
	{
		totalin = 0.0f;
		totalout = 0.0f;

		for (gc_transsplit nextts : transsplits)
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
		closing_balance += totalout;
	}

	



}
