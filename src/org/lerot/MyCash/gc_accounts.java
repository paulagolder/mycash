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
		java.lang.String query = "select * from accounts where 1 order by guid ";
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
				gc_account anaccount = new gc_account(resset);
				put(anaccount.getAccount_id(), anaccount);
				n = n + 1;
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}
		finally {
		    try { resset.close(); } catch (Exception e) { /* Ignored */ }
		    try { st.close(); } catch (Exception e) { /* Ignored */ }
		    try { con.close(); } catch (Exception e) { /* Ignored */ }
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
			if (anaccount.getParent_id() == null)
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

	

	

	

}
