package org.lerot.MyCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.dom4j.Element;


public  class gc_split
{

	protected String Split_ID;
	protected String Tx_ID;
	protected String Account_ID;
	protected String Memo;
	protected String Action;
	protected Boolean Reconcile_state;
	protected String Reconcile_date;
	protected int    Value_number;
	protected int    Value_denom;
	protected int    Quantity_number;
	protected int    Quantity_denom;
	protected String Lot_ID;
	

	private String account_name;

	public String getSplit_ID() {
		return Split_ID;
	}

	public void setSplit_ID(String split_ID) {
		Split_ID = split_ID;
	}

	public String getTx_ID() {
		return Tx_ID;
	}

	public void setTx_ID(String tx_ID) {
		Tx_ID = tx_ID;
	}

	public String getAccount_ID() {
		return Account_ID;
	}

	public void setAccount_ID(String account_ID) {
		Account_ID = account_ID;
	}

	public String getMemo() {
		return Memo;
	}

	public void setMemo(String memo) {
		Memo = memo;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	public Boolean getReconcile_state() {
		return Reconcile_state;
	}

	public void setReconcile_state(Boolean reconcile_state) {
		Reconcile_state = reconcile_state;
	}

	public String getReconcile_date() {
		return Reconcile_date;
	}

	public void setReconcile_date(String reconcile_date) {
		Reconcile_date = reconcile_date;
	}

	public int getValue_number() {
		return Value_number;
	}

	public void setValue_number(int value_number) {
		Value_number = value_number;
	}

	public int getValue_denom() {
		return Value_denom;
	}

	public void setValue_denom(int value_denom) {
		Value_denom = value_denom;
	}

	public int getQuantity_number() {
		return Quantity_number;
	}

	public void setQuantity_number(int quantity_number) {
		Quantity_number = quantity_number;
	}

	public int getQuantity_denom() {
		return Quantity_denom;
	}

	public void setQuantity_denom(int quantity_denom) {
		Quantity_denom = quantity_denom;
	}

	public String getLot_ID() {
		return Lot_ID;
	}

	public void setLot_ID(String lot_ID) {
		Lot_ID = lot_ID;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}



	public gc_split(ResultSet resset) 
	{	
		try {
			String text = resset.getString("guid");
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
			text = resset.getString("value_num");
			setValue_number(text);
			text = resset.getString("value_denom");
			setValue_denom(text);
			text = resset.getString("quantity_num");
			setQuantity_num(text);
			text = resset.getString("quantity_denom");
			setquantity_denom(text);
			text = resset.getString("lot_guid");
			setLot_ID(text);			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private void setquantity_denom(String text) {
		setQuantity_denom(utils.Integer(text));

	}

	private void setQuantity_num(String text) {
		setQuantity_number(utils.Integer(text));

	}

	private void setValue_denom(String text) {
		setValue_denom(utils.Integer(text));

	}

	private void setValue_number(String text) {
		setValue_number(utils.Integer(text));

	}

	private void setReconcile_state(String text) {
		if(text.equalsIgnoreCase("n"))
			setReconcile_state( false);
		else 
			setReconcile_state(true);
	}

	

	public  void logtoconsol(String indent) 
	{
		float txvalue = ((float)getValue_number())/getValue_denom();
		System.out.println(indent+" Transfer " +  String.format("%.2f",txvalue) + " From Account :"+ account_name + "  to "+getMemo() );		
	}

	

	public String getAccount_name() {
		
		return  account_name;
	}

	public  void setAccountName(gc_accounts accountlist) 
	{
		
			SetAccount_name(accountlist.get(getAccount_ID()).getName());			
			
	}

	void SetAccount_name(String name) {
		account_name=name;
		
	}

	


}
