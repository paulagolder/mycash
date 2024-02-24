package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Element;
import org.dom4j.Node;

public class mb_account
{

	private String name;
	private String code;
	gc_transactions transactions;

	private float opening_balance;
	private String description;
	private float totalin;
	private float totalout;
	private float closing_balance;
	ArrayList<mb_transfer> transfers;

	public mb_account(Node anode)
	{
		transfers = new ArrayList<mb_transfer>();
		if (anode != null)
		{
			name = ((Element) anode).attributeValue("name");
			code = ((Element) anode).attributeValue("code");
			description = ((Element) anode).attributeValue("description");
			List<Node> transferList = anode.selectNodes("transfer");
			for (Node tnode : transferList)
			{
				mb_transfer newtrans = new mb_transfer(tnode);
				transfers.add(newtrans);
				if (newtrans.getLabel().toLowerCase().contains("opening"))
				{
					opening_balance = newtrans.getValue();
				}
			}
			transactions = gc_transactions.gettransactions(code);
			totalTransfers();
			closing_balance = opening_balance + totalin - totalout;
		} else
		{
			name = "Unallocated ";
			code = null;
			description = "Un-budgeted expenditure";
			transactions = gc_transactions.gettransactions("");
			opening_balance = 0;
			closing_balance = 0;
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public float getOpening_balance()
	{
		return opening_balance;
	}

	public void setOpening_balance(float opening_balance)
	{
		this.opening_balance = opening_balance;
	}

	public String getDescription()
	{
		if (description == null)
			return "";
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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

	public float getClosing_balance()
	{
		return closing_balance;
	}

	public void setClosing_balance(float closing_balance)
	{
		this.closing_balance = closing_balance;
	}

	public String heading()
	{
		String outline = name + "(" + code + ")" + description + "\n";
		outline += "  opening balance =" + opening_balance + "\n";
		outline += "  total receipts =" + totalin + " total dispenses =" + totalout + "\n";
		outline += "  closing balance =" + closing_balance + "\n\n";
		return outline;
	}

	public String accountrowToTXT()
	{
		String outline = utils.leftpad(10, code) + utils.leftpad(40, name + " " + getDescription());
		outline += String.format("%8.2f", opening_balance) + " ";
		outline += String.format("%8.2f", totalin) + " ";
		outline += String.format("%8.2f", totalout) + " ";
		outline += String.format("%8.2f", closing_balance) + " ";
		outline += "\n";
		return outline;
	}

	public String footing()
	{
		String outline = "\n";
		outline += "  closing balance =" + closing_balance + "\n";
		return outline;
	}

	public String listTransactions()
	{

		String output = "";
		if (transactions == null)
		{
			output += " no transsplits for " + this.name + "\n";

		} else if (transactions.size() < 1)
		{
			output += " no transsplits 2 " + "\n";

		} else
		{
			for (Entry<String, gc_transaction> nextts : transactions.entrySet())
			{
				if (nextts == null)
				{
					output += " no transsplit 3" + "\n";
				} else
				{
					if (((gc_transaction) nextts.getValue()).getTotalin() > 0)
					{
						output += ((gc_transaction) nextts.getValue()).toString();
					}
				}
			}
		}
		return output;
	}

	public String toString()
	{
		return name + "(" + code + ")" + "\n";
	}

	public void totalTransfers()
	{
		totalin = 0.0f;
		totalout = 0.0f;
		for (mb_transfer atrans : transfers)
		{
			if (!atrans.getLabel().toLowerCase().contains("opening"))
			{
				if (atrans.getValue() > 0)
				{
					totalin += atrans.getValue();
				} else
				{
					totalout += atrans.getValue();
				}
			}

		}

		for (Entry<String, gc_transaction> nextts : transactions.entrySet())
		{
			if (nextts != null)
			{
				totalin += ((float) nextts.getValue().getTotalin());
				totalout += ((float) nextts.getValue().getTotalout());
			}
		}
	}

	public String printdetail()
	{
		String output = "\n";
		output += name + " " + getDescription() + " ";
		output += accountrowToTXT()+"\n";
		for (mb_transfer atran : transfers)
		{
			output += " " + atran.print() + "\n";
		}
		for (Entry<String, gc_transaction> nextt : transactions.entrySet())
		{
			output +=  ((gc_transaction) nextt.getValue()).toString() ;
		}

		return output;
	}

	public String printdetailToCSV()
	{
		String output = "";
		output += name + ", " + getDescription() + ", ";
		output += accountrowToCSV();
		for (mb_transfer atran : transfers)
		{
			output += " " + atran.printCSV() + "\n";
		}
		output += this.listTransactions();
		return output;
	}

	public String toTxt()
	{
		String output = "";
		if (transactions == null)
		{
			output += " no transsplits for " + this.name + "\n";

		} else if (transactions.size() < 0)
		{
			output += " no transsplits 2 " + "\n";

		} else
		{
			for (Entry<String, gc_transaction> nextts : transactions.entrySet())
			{
				if (nextts == null)
				{
					output += " no transsplit 3" + "\n";
				} else
				{
					if (nextts.getValue().getTotalin() > 0)
					{
						output += nextts.toString();
					}
				}
			}
		}
		return output;
	}

	public String toCsv()
	{
		String output = "";
		if (transactions == null)
		{
			return " No transactions ";

		} else if (transactions.size() < 0)
		{
			return " No transactions ";

		} else
		{
			for (Entry<String, gc_transaction> nextts : transactions.entrySet())
			{
				if (nextts == null)
				{
					// return null;
				} else
				{
					if (nextts.getValue().getTotalin() > 0)
					{
						output += nextts.getValue().toCSV();
					}
				}
			}
			return output;
		}

	}

	public String accountrowToCSV()
	{
		String outline = code + ", " + name + ", " + opening_balance + ", " + totalin + ", " + totalout + ", "
				+ closing_balance + "\n";
		return outline;

	}

}
