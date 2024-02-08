package org.lerot.MyCash;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;

import org.lerot.mywidgets.jswPanel;

public class jswMyCashTreeScrollPane extends jswPanel
{

	private static final long serialVersionUID = 1L;
	//public JTextArea textArea;
	JScrollPane scrollPane;

	public jswMyCashTreeScrollPane(JTree mylist)
	{
		super("name");
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);

		scrollPane = new JScrollPane(mylist);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		this.add(scrollPane);
	}

	public void setHorizontalScrollBarPolicy(int policy)
	{
		scrollPane.setHorizontalScrollBarPolicy(policy);
	}

	public void setMyBounds(int x, int y, int w, int h)
	{
		scrollPane.setBounds(x, y, w, h);
		this.setBounds(x, y, w, h);
		// System.out.format(" setting bounds %d %d %d %d %n ", x, y, w, h);
	}

	public void setVerticalScrollBarPolicy(int policy)
	{
		scrollPane.setVerticalScrollBarPolicy(policy);
	}

}
