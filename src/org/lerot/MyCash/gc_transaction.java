package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class gc_transaction
{
	final int  IN = -1;
	final int   OUT = +1;
	

	class entry
	{
		
		String account_guid;
		String account_name;
		Integer direction;
		Float value;
		String budget_code;
		String expense_code;
		String memo;

		public String getAccount_guid()
		{
			return account_guid;
		}

		public void setAccount_guid(String account_guid)
		{
			this.account_guid = account_guid;
		}

		public String getAccount_name()
		{
			return account_name;
		}

		public void setAccount_name(String account_name)
		{
			this.account_name = account_name;
		}

		public Integer getDirection()
		{
			return direction;
		}

		public void setDirection(Integer direction)
		{
			this.direction = direction;
		}

		public Float getValue()
		{
			return value;
		}

		public void setValue(Float value)
		{
			this.value = value;
		}

		public String getBudget_code()
		{
			return budget_code;
		}

		public void setBudget_code(String budget_code)
		{
			this.budget_code = budget_code;
		}

		public String getExpense_code()
		{
			return expense_code;
		}

		public void setExpense_code(String expense_code)
		{
			this.expense_code = expense_code;
		}

		public String getMemo()
		{
			return memo;
		}

		public void setMemo(String memo)
		{
			this.memo = memo;
		}

		public entry(ResultSet resset)
		{
			try
			{
				account_guid = resset.getString("account_guid");
				memo = resset.getString("memo");
				budget_code = resset.getString("action");
				String Value_number = resset.getString("value_num");
				String Value_denom = resset.getString("value_denom");
				if (Integer.parseInt(Value_number) < 0)
					direction = OUT;
				else
					direction = IN;
				float txvalue = (float) Float.parseFloat(Value_number) / Float.parseFloat(Value_denom);
				value = ((float) ((Math.round(txvalue * 100.0)) / 100.0));
				gc_account anaccount = MyCash_gui.MyAccounts.get(account_guid);
				if (anaccount != null)
					account_name = anaccount.getName();
				else
					account_name = account_guid;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected String TransactionID;
	protected String Currency_ID;
	protected String Num;
	protected String Post_date;
	protected String Enter_date;
	protected String Description;

	private float totalin;
	private float totalout;
	private float opening_balance;

	protected Set<entry> entries;

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
			entries = new HashSet<entry>();
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	public static gc_transaction getTransaction(String transactionid)
	{

		gc_transaction atrans = null;
		String query = "select * from transactions where guid =\"" + transactionid + "\" order by guid ";
		PreparedStatement st = null;
		ResultSet resset = null;
		Connection con = null;

		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			if (resset.next())
			{
				atrans = new gc_transaction(resset);
				// atrans.transsplits = new gc_transsplits(atrans.TransactionID);
				// atrans.getEntries();
			}
			// System.out.println(" zzquery: "+query);

		} catch (Exception e)
		{
			System.out.println(" in hare 12 " + e);
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
		return atrans;

	}

	public void getEntries()
	{
		entries = new HashSet<entry>();
		String query = "SELECT *  ";
		query += " FROM splits s1  WHERE s1.tx_guid = \"" + this.TransactionID + "\"  order by s1.value_num ";

		PreparedStatement st = null;
		ResultSet resset = null;
		Connection con = null;

		try
		{
			con = utils.myconnect();
			st = con.prepareStatement(query);
			resset = st.executeQuery();
			while (resset.next())
			{
				entry anentry = new entry(resset);
				entries.add(anentry);
			}
		} catch (Exception e)
		{
			System.out.println(" exception transaction : " + this.TransactionID);

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
		if (entries != null)
			// System.out.println(" Entires found "+ entries.size());
			;
		else
			System.out.println(" No Entires found ");
	}

	public void logtoconsol()
	{
		MyCash_gui.outputpanel.append("  " + getPost_date_short() + " " + getDescription() + " :" + getNumber() + " ");
		for (entry anentry : entries)
		{

			float value = (float) anentry.getValue();
			String vf = String.format("%8.2f", value);
			MyCash_gui.outputpanel.append("       $$ Transfer £" + vf + " from:" + anentry.getAccount_name() + " "
					+ anentry.getMemo() + " " + " " + anentry.getBudget_code() + " " + "\n");
		}
	}

	public String toString()
	{
		String output = "";
		String date = " " + getPost_date_short() + " ";
		String adescription = getDescription();
		String snum = utils.leftpad(10, getNumber());
		String vf = String.format("%8.2f", Math.abs(getTotalout()));
		String svalue = "£" + utils.rightpad(8, vf);
		String sdescription = adescription;
		String direction = " ";
		if (entries.size() == 2)
		{
			
				String outaccount = "";
				String inaccount = "";
				
				for (entry anentry : entries)
				{
					if (anentry.direction== OUT)
					{
						outaccount = " FROM "+anentry.getAccount_name();
					}
					if (anentry.direction== IN)
					{
						inaccount =  "   FOR "+ anentry.getAccount_name();
					}
					//direction += utils.direction(anentry.direction) + " " + anentry.getAccount_name();
					String memo = anentry.getMemo();
					if (!sdescription.contains(memo))
					{
						sdescription += " " + anentry.getMemo();
					}
				}
			

			sdescription = utils.leftpad(40, sdescription);
			output += date + sdescription + " "+snum + " " + svalue + outaccount +" "+ inaccount  + "\n";
		} else
		{
			sdescription = utils.leftpad(40, sdescription);
			//output += date + sdescription +" "+ snum + " " + svalue +" FROM" +  "\n";
			for (entry anentry : entries)
			{
				if (anentry.direction== OUT)
				{
					output += date + sdescription +" "+ snum + " " + svalue +" FROM " + anentry.getAccount_name() + "\n";
				}
			}
			for (entry anentry : entries)
			{
				if (anentry.direction== IN)
				{
					String ev = String.format("%8.2f", Math.abs(anentry.getValue()));
					output += "            "+ utils.leftpad(40, anentry.getMemo()) +" "+utils.leftpad(10, anentry.budget_code)+ " £" + utils.rightpad(8, ev)
					+" FOR  " + anentry.getAccount_name() + "\n";
				}
			}
		}
		return output;
	}

	private String getFull_Account_Name()
	{
		// TODO Auto-generated method stub
		return " FAC ";
	}

	public void totalTransfers()
	{
		totalin = 0.0f;
		totalout = 0.0f;
		opening_balance = 0.0f;

		if (Description.toLowerCase().contains("opening"))
		{
			for (entry nextts : entries)
			{
				if (nextts.direction == IN)
					opening_balance += ((float) nextts.getValue());
			}
		} else
		{
			for (entry nextts : entries)
			{
				if (nextts != null)
				{

					if (nextts.direction == IN)
					{
						totalin += nextts.value;
					} else
					{
						totalout += nextts.value;
					}

				}
			}
		}

	}

	public String getNum()
	{
		return Num;
	}

	public void setNum(String num)
	{
		Num = num;
	}

	public float getTotalin()
	{
		return totalin;
	}

	public void setTotalin(float totalin)
	{
		this.totalin = totalin;
	}

	public float getTotalout()
	{
		return totalout;
	}

	public void setTotalout(float totalout)
	{
		this.totalout = totalout;
	}

	public float getOpening_balance()
	{
		return opening_balance;
	}

	public void setOpening_balance(float opening_balance)
	{
		this.opening_balance = opening_balance;
	}

	public void setEntries(Set<entry> entries)
	{
		this.entries = entries;
	}

	public String toCSV()
	{
		return " not yet done gc tranascation 441 ";

	}

}
