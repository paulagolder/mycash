package org.lerot.MyCash;

import java.sql.ResultSet;
import java.sql.SQLException;

public class gc_richsplit
{
	private String tx_id;
	private String fromsplit_id;
	private String tosplit_id;

	public gc_richsplit(ResultSet resset)
	{
		try
		{
			String text = resset.getString("txid");
			setTx_id(text);
			text = resset.getString("fromid");
			setFromsplit_id(text);
			text = resset.getString("toid");
			setTosplit_id(text);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getTx_id()
	{
		return tx_id;
	}

	public void setTx_id(String tx_id)
	{
		this.tx_id = tx_id;
	}

	public String getFromsplit_id()
	{
		return fromsplit_id;
	}

	public void setFromsplit_id(String fromsplit_id)
	{
		this.fromsplit_id = fromsplit_id;
	}

	public String getTosplit_id()
	{
		return tosplit_id;
	}

	public void setTosplit_id(String tosplit_id)
	{
		this.tosplit_id = tosplit_id;
	}

}