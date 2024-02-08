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
	protected int Value_number;
	protected int Value_denom;
	protected int Quantity_number;
	protected int Quantity_denom;
	protected String Lot_ID;
	private String account_name;

	public gc_transsplit(String txid, String splitid)
	{
			gc_transaction atrans = MyCash_gui.MyTransactions.get(txid);
			gc_split asplit = MyCash_gui.MySplits.get(splitid);
			setTransactionID(tx_id);
			setCurrency_ID(atrans.Currency_ID);
			setNumber(atrans.Num);
			setPost_date(atrans.Post_date);
			setEnter_date(atrans.Enter_date);
			setDescription(atrans.Description);
			setSplit_ID(asplit.Split_ID);
			setTx_ID(asplit.Tx_ID);
			setAccount_ID(asplit.Account_ID);
			setMemo(asplit.Memo);
			setAction(asplit.Action);
			setReconcile_state(asplit.Reconcile_date);
			setReconcile_date(asplit.Reconcile_date);
			setValue_number(asplit.Value_number);
			setValue_denom(asplit.Value_denom);
			setQuantity_number(asplit.Quantity_number);
			setQuantity_denom(asplit.Quantity_denom);;
			setLot_ID(asplit.Lot_ID);
			 String dfd = MyCash_gui.MyAccounts.get(getAccount_ID()).getName();
			 setAccount_name(dfd);
		
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
		// TODO Auto-generated method stub
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
		return Num;
	}

	public String getPost_date()
	{
		return Post_date;
	}

	private String getPost_date_short()
	{
		return Post_date.substring(0, 10);
	}

	public int getQuantity_denom()
	{
		return Quantity_denom;
	}

	public int getQuantity_number()
	{
		return Quantity_number;
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

	public int getValue_denom()
	{
		return Value_denom;
	}

	public int getValue_number()
	{
		return Value_number;
	}

	public void logtoconsol()
	{
		String output = "";
	
		output += ("  " + getPost_date_short() + " " + getDescription() + " :" + getNumber() + " ");

		float value = (float) getValue_number() / getValue_denom();
		String vf = String.format("%8.2f", value);
		output += ("        Transfer++ £" + vf + " from:" + getAccount_name()
				+ " to:" + " " + getMemo() + " " + getAction() + " " + "\n");
		MyCash_gui.outputpanel.append(output);
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

	public void setQuantity_denom(int quantity_denom)
	{
		Quantity_denom = quantity_denom;
	}

	public void setQuantity_number(int quantity_number)
	{
		Quantity_number = quantity_number;
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

	public void setValue_denom(int value_denom)
	{
		Value_denom = value_denom;
	}

	public void setValue_number(int value_number)
	{
		Value_number = value_number;
	}

	public String toCSV()
	{
		String output = "";
		String date = getPost_date_short().trim() +", ";
		String sdescription = getDescription().trim();
		String snum =  getNumber().trim()+", ";
		String direction = "";
		String svalue = "";

		float value = (float) getValue_number() / getValue_denom();
		String vf = String.format("%8.2f", Math.abs(value)).trim()+", ";
		// output += " Transfer+ ";
		//svalue = " " + utils.rightpad(8, vf)+", ";

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
		output += date + sdescription +", "+ snum + " " + vf + direction + "\n";
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

		float value = (float) getValue_number() / getValue_denom();
		String vf = String.format("%8.2f", Math.abs(value));
		// output += " Transfer+ ";
		svalue = "£" + utils.rightpad(8, vf);

		if (value > 0)
		{
			direction = " to:" + getAccount_name();
		} else
		{
			direction = "   from:" + getAccount_name();
		}
		if (!output.contains(getMemo()))
		{
			sdescription += " " + getMemo();
		}
		if (!output.contains(getAction()))
		{
			// sdescription += " " + fromsplit.getAction();
		}

		sdescription = utils.leftpad(60, sdescription);
		output += date + sdescription + snum + "Transfer:" + svalue + direction + "\n";
		return output;
	}

}
