package org.lerot.MyCash.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.lerot.mywidgets.jswHorizontalLayout;
import org.lerot.mywidgets.jswPanel;

public class jswMyCashScrollPane extends jswPanel
{

	private static final long serialVersionUID = 1L;
	public JTextArea textArea;
	JScrollPane scrollPane;

	public jswMyCashScrollPane()
	{
        super("name");
		this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
         
        textArea = new JTextArea();
        textArea.setForeground(Color.GREEN);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setText("This is some text");
        textArea.setFont(new Font("Monospaced", Font.ITALIC, 11));
        textArea.setForeground(Color.white);
        textArea.setBackground(Color.lightGray);
         
        scrollPane = new JScrollPane(textArea);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        this.add(scrollPane);
	}

	
	public void setMyBounds(int x, int y, int w, int h)
	{
		scrollPane.setBounds(x, y, w, h);
		this.setBounds(x, y, w, h);
		// System.out.format(" setting bounds %d %d %d %d %n ", x, y, w, h);
	}

	public void setHorizontalScrollBarPolicy(int policy)
	{
		scrollPane.setHorizontalScrollBarPolicy(policy);
	}

	public void setVerticalScrollBarPolicy(int policy)
	{
		scrollPane.setVerticalScrollBarPolicy(policy);
	}

}
