package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.dom4j.Element;


public  class gc_splits  extends LinkedHashMap<String, gc_split> 
{
private static final long serialVersionUID = 1L;

	private String account_name;
	
	public gc_splits(String name)
	{
		account_name = name;
	}

	public void getSplits()
	{	
		String query = "select * from splits    where   1 order by guid ";
		PreparedStatement st= null;
		ResultSet resset = null;
		Connection con = null;

		int n=0;;
		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				gc_split asplit = new gc_split(resset);
				put(asplit.Split_ID,asplit)	;	
				n=n+1;
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}	finally {
		    try { resset.close(); } catch (Exception e) { /* Ignored */ }
		    try { st.close(); } catch (Exception e) { /* Ignored */ }
		    try { con.close(); } catch (Exception e) { /* Ignored */ }
		}	
	}
	
	public  List<gc_split> select(String transactionID) {

		ArrayList<gc_split> splits = new ArrayList<gc_split>();
		for( java.util.Map.Entry<String, gc_split> gcs : this.entrySet())
		{
			gc_split asplit = gcs.getValue();
			if(asplit.Tx_ID.equalsIgnoreCase(transactionID))
			{
			
				splits.add(asplit);
			}
		}	
		return splits;
	}


	public void setAccountName(gc_accounts myaccounts) 
	{	
		Set<java.util.Map.Entry<String, gc_split>>  es = this.entrySet();
		for( java.util.Map.Entry<String, gc_split> gcs : es)
		{
			gc_split asplit = gcs.getValue();
			asplit.SetAccount_name(myaccounts.get(asplit.getAccount_ID()).getName());
		    //put(asplit.Tx_ID,asplit);	
		}		
	}

	public  void logtoconsol(String indent) 
	{
		System.out.println(indent+" Account :"+ account_name );		
	}
	
	public  void logtoconsol() 
	{
		for( java.util.Map.Entry<String, gc_split> gcs : this.entrySet())
		{
			gc_split asplit = gcs.getValue();
			asplit.logtoconsol("+++");
		}	
		
	}

	

	

	

	

	


}
