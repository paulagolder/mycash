package org.lerot.MyCash;

import org.dom4j.Element;
import org.dom4j.Node;

public class mb_transfer
{
	private String date;
	private String label;
	private float value;

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}



	public mb_transfer(Node anode)
	{

		date = ((Element) anode).attributeValue("date");
		label = ((Element) anode).attributeValue("label");
		value = Float.parseFloat(((Element) anode).attributeValue("value"));

	}

	public String toString()
	{
		return date + "" + label + " " + String.format("%.2f", value) + "\n";
	}

	public String print()
	{
		return  utils.rightpad(10, date)+ " " + utils.leftpad( 40,label) + " " + String.format("%.2f", value);
	}

	public String printCSV()
	{
		return  date+ ", " + label + ", " + String.format("%.2f", value);
	}

}
