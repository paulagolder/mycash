package org.lerot.MyCash;

import java.sql.ResultSet;
import java.sql.SQLException;

public class gc_transsplit 
{

	private String tx_id;
	private String split_id;

	protected String TransactionID;
	protected String Currency_ID;
	protected String Num;
	protected String Post_date;
	protected String Enter_date;
	protected String Description;
	protected String Split_ID;
	protected String Tx_ID;
	protected String Account_ID;
	protected String Memo;
	protected String Action;
	protected Boolean Reconcile_state;
	protected String Reconcile_date;
	protected float Value;
	protected float Quantity;
	protected String Lot_ID;
	private String account_name;
	


	
	private void setQuantity(float quantity)
	{
		// TODO Auto-generated method stub
		
	}

	public gc_transsplit(ResultSet resset)
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
				text = resset.getString("guid");
				setSplit_ID(text);
				text = resset.getString("tx_guid");
				setTx_ID(text);
				text = resset.getString("account_guid");
				setAccount_ID(text);
				text = resset.getString("memo");
				setMemo(text);
				text = resset.getString("action");
				setAction(text);
				text = resset.getString("reconcile_state");
				setReconcile_state(text);
				text = resset.getString("reconcile_date");
				setReconcile_date(text);
				String Value_number = resset.getString("value_num");	
				String Value_denom = resset.getString("value_denom");;
				String Quantity_number = resset.getString("quantity_num");
				String Quantity_denom = resset.getString("quantity_denom");
				text = resset.getString("lot_guid");
				setLot_ID(text);			
				float txvalue = (float) Float.parseFloat(Value_number)/Float.parseFloat(Value_denom);
				setValue( (float) ((Math.round(txvalue * 100.0)) / 100.0));
				float txquantity = (float) Float.parseFloat(Quantity_number)/Float.parseFloat(Quantity_denom);
				setQuantity( (float) ((Math.round(txquantity * 100.0)) / 100.0));
				 String dfd = MyCash_gui.MyAccounts.get(getAccount_ID()).getName();
				 setAccount_name(dfd);
			} catch (SQLException e)
			{

				e.printStackTrace();
			}
		
			
		
		
	}



	private void setValue(float dbl)
	{
		Value = dbl;
		
	}

	public String getAccount_ID()
	{
		return Account_ID;
	}

	public String getAccount_name()
	{
		return account_name;
	}

	public String getAction()
	{
		return Action;
	}

	public String getCurrency_ID()
	{
		return Currency_ID;
	}

	public String getDescription()
	{
		return Description;
	}

	public String getEnter_date()
	{
		return Enter_date;
	}

	private String getEnter_date_short()
	{
		return Enter_date.substring(0, 10);
	}

	private String getFull_Account_Name()
	{
		return " FAC ";
	}

	public String getLot_ID()
	{
		return Lot_ID;
	}

	public String getMemo()
	{
		return Memo;
	}

	public String getNum()
	{
		return Num;
	}

	public String getNumber()
	{
		if(Num == null) return "";
		else return Num;
	}

	public String getPost_date()
	{
		return Post_date;
	}

	private String getPost_date_short()
	{
		return Post_date.substring(0, 10);
	}



	public String getReconcile_date()
	{
		return Reconcile_date;
	}

	public Boolean getReconcile_state()
	{
		return Reconcile_state;
	}

	public String getSplit_ID()
	{
		return split_id;
	}

	public String getTransactionID()
	{
		return TransactionID;
	}

	public String getTx_ID()
	{
		return tx_id;
	}



	public void logtoconsol()
	{
		String output = "";
	
		output += ("  " + getPost_date_short() + " " + getDescription() + " :" + getNumber() + " ");

		float value = (float) getValue() ;
		String vf = String.format("%8.2f", value);
		output += ("        Transfer++ £" + vf + " from:" + getAccount_name()
				+ " to:" + " " + getMemo() + " " + getAction() + " " + "\n");
		MyCash_gui.outputpanel.append(output);
	}

	float getValue()
	{
		return Value;
	}

	public void setAccount_ID(String account_ID)
	{
		Account_ID = account_ID;
	}

	public void setAccount_name(String account_name)
	{
		this.account_name = account_name;
	}

	public void setAction(String action)
	{
		Action = action;
	}

	public void setCurrency_ID(String currency_ID)
	{
		Currency_ID = currency_ID;
	}

	public void setDescription(String description)
	{
		Description = description;
	}

	public void setEnter_date(String enter_date)
	{
		Enter_date = enter_date;
	}

	public void setLot_ID(String lot_ID)
	{
		Lot_ID = lot_ID;
	}

	public void setMemo(String memo)
	{
		Memo = memo;
	}

	public void setNum(String num)
	{
		Num = num;
	}

	public void setNumber(String text)
	{
		Num = text;
	}

	public void setPost_date(String post_date)
	{
		Post_date = post_date;
	}

	

	public void setReconcile_date(String reconcile_date)
	{
		Reconcile_date = reconcile_date;
	}

	public void setReconcile_state(Boolean reconcile_state)
	{
		Reconcile_state = reconcile_state;
	}

	private void setReconcile_state(String text)
	{
		if (text.equalsIgnoreCase("n"))
			setReconcile_state(false);
		else
			setReconcile_state(true);
	}

	public void setSplit_ID(String text)
	{
		split_id = text;
	}

	public void setTransactionID(String transactionID)
	{
		TransactionID = transactionID;
	}

	public void setTx_ID(String text)
	{
		tx_id = text;
	}



	public String toCSV()
	{
		String output = "";
		String date = getPost_date_short().trim() ;
		String sdescription = getDescription().trim();
		String snum =  getNumber().trim();
		String direction = "";
		String svalue = "";

		float value = (float) getValue() ;
		String vf = String.format("%8.2f", Math.abs(value)).trim();
	
		if (value > 0)
		{
			direction = "to " + getAccount_name().trim();
		} else
		{
			direction = "from " + getAccount_name().trim();
		}
		if (!output.contains(getMemo()))
		{
			sdescription += " " + getMemo().trim();
		}
		if (!output.contains(getAction()))
		{
			// sdescription += " " + fromsplit.getAction();
		}

		sdescription =  utils.replacecommas(sdescription.trim())+ ", ";
		output += date +", "+  sdescription +", "+ snum + ", " + vf + ", "+direction + "\n";
		return output;

	}

	public String toString()
	{
		String output = "";
		String date = "  " + getPost_date_short() + " ";
		String sdescription = getDescription();
		String snum = utils.leftpad(10, getNumber());
		String direction = "";
		String svalue = "";

		float value = (float) getValue() ;
		String vf = String.format("%8.2f", Math.abs(value));
		// output += " Transfer+ ";
		svalue = "£" + utils.rightpad(8, vf);

		if (value > 0)
		{
			direction = "   to:" + getAccount_name();
		} else
		{
			direction = " from:" + getAccount_name();
		}
		if (!output.contains(getMemo())  )
		{
			sdescription += " " + getMemo();
		}
		if (!output.contains(getAction()))
		{
			// sdescription += " " + fromsplit.getAction();
		}

		sdescription = utils.leftpad(60, sdescription);
		output += date + sdescription + snum + "zzTransfer:" + svalue + direction + "\n";
		return output;
	}

}
