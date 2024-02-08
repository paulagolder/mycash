package org.lerot.MyCash;

import java.sql.ResultSet;
import java.sql.SQLException;

public class gc_transaction // implements Comparable<gc_transaction>
{

	protected String TransactionID;
	protected String Currency_ID;
	protected String Num;
	protected String Post_date;
	protected String Enter_date;
	protected String Description;

	protected gc_richsplits richsplits;

	public String getTransactionID()
	{
		return TransactionID;
	}

	public void setTransactionID(String transactionID)
	{
		TransactionID = transactionID;
	}

	public String getCurrency_ID()
	{
		return Currency_ID;
	}

	public void setCurrency_ID(String currency_ID)
	{
		Currency_ID = currency_ID;
	}

	public String getNumber()
	{
		return Num;
	}

	public void setNumber(String text)
	{
		Num = text;
	}

	public String getPost_date()
	{
		return Post_date;
	}

	public void setPost_date(String post_date)
	{
		Post_date = post_date;
	}

	public String getEnter_date()
	{
		return Enter_date;
	}

	private String getEnter_date_short()
	{
		return Enter_date.substring(0, 10);
	}

	private String getPost_date_short()
	{
		return Post_date.substring(0, 10);
	}

	public void setEnter_date(String enter_date)
	{
		Enter_date = enter_date;
	}

	public String getDescription()
	{
		return Description;
	}

	public void setDescription(String description)
	{
		Description = description;
	}

	public gc_transaction(ResultSet resset)
	{
		try
		{
			String text = resset.getString("guid");
			setTransactionID(text);
			text = resset.getString("currency_guid");
			setCurrency_ID(text);
			text = resset.getString("num");
			setNumber(text);
			text = resset.getString("post_date");
			setPost_date(text);
			text = resset.getString("enter_date");
			setEnter_date(text);
			text = resset.getString("description");
			setDescription(text);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	public void logtoconsol()
	{
		MyCash_gui.outputpanel
				.append("  " + getPost_date_short() + " " + getDescription() + " :" + getNumber() + " ");
		for (gc_richsplit gcrs : richsplits)
		{
			gc_split fromsplit = MyCash_gui.MySplits.get(gcrs.getFromsplit_id());
			gc_split tosplit = MyCash_gui.MySplits.get(gcrs.getTosplit_id());
			float value = (float) fromsplit.getValue_number() / fromsplit.getValue_denom();
			String vf = String.format("%8.2f", value);
			MyCash_gui.outputpanel.append("        Transfer £" + vf + " from:"
					+ fromsplit.getAccount_name() + " to:" + tosplit.getAccount_name() + " " + fromsplit.getMemo() + " "
					+ tosplit.getMemo() + " " + fromsplit.getAction() + " " + tosplit.getAction() + "\n");
		}
	}

	public String toString()
	{
		String output = "";
		String date = "  " + getPost_date_short() + " " ;
		String sdescription = getDescription() ;
		String snum = utils.leftpad(10, getNumber()) ;
		String direction = " X ";
		String svalue = "99999";
		for (gc_richsplit gcrs : richsplits)
		{
			gc_split fromsplit = MyCash_gui.MySplits.get(gcrs.getFromsplit_id());
			gc_split tosplit = MyCash_gui.MySplits.get(gcrs.getTosplit_id());
			float value = (float) fromsplit.getValue_number() / fromsplit.getValue_denom();
			String vf = String.format("%8.2f", Math.abs(value));
			//output += " Transfer+ ";
			svalue = "£" + utils.rightpad(8, vf);
			
			if (value > 0)
			{
				direction = " from:" + fromsplit.getAccount_name();
			} else
			{
				direction = "   to:" + tosplit.getAccount_name();
			}
			if (!output.contains(fromsplit.getMemo()))
			{
				sdescription += " " + fromsplit.getMemo();
			}
			if (!output.contains(fromsplit.getAction()))
			{
				//sdescription += " " + fromsplit.getAction();
			}
			if (!output.contains(tosplit.getMemo()))
			{
				sdescription += " " + tosplit.getMemo();
			}
			if (!output.contains(tosplit.getAction()))
			{
				//sdescription += " " + tosplit.getAction() ;
			}
			
		}
		sdescription = utils.leftpad(60, sdescription) ;
		output += date+sdescription+snum+"Transfer:"+svalue+direction+"\n";
		return output;

	}

	private String getFull_Account_Name()
	{
		// TODO Auto-generated method stub
		return " FAC ";
	}

}
