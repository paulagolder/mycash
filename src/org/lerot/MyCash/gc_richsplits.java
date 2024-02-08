package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class gc_richsplits extends  ArrayList<gc_richsplit> 
{

	
	private static final long serialVersionUID = 1L;

	public gc_richsplits(String tx_id)
	{

		String query = "SELECT s1.tx_guid as txid, s1.guid as fromid , s2.guid as toid ";
		query += " FROM splits s1 , splits s2 WHERE s1.tx_guid = s2.tx_guid and s1.guid != s2.guid  ";
		query += " and  s1.tx_guid = '" +tx_id +  "' " ; 

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
				gc_richsplit arichsplit = new gc_richsplit(resset);
				add(arichsplit)	;	
				n=n+1;
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}	finally 
		{
		    try { resset.close(); } catch (Exception e) { /* Ignored */ }
		    try { st.close(); } catch (Exception e) { /* Ignored */ }
		    try { con.close(); } catch (Exception e) { /* Ignored */ }
		}	
	}
	
	
}
