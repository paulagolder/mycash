package org.lerot.MyCert.layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextPane;

public abstract class PLabel extends JTextPane
{
	private static final long serialVersionUID = 1L;
	protected Color backgroundColor;
	protected Color bordercolor = Color.green;
	protected int borderwidth = 0;
	protected FontMetrics fm;
	protected Color foregroundColor;
	protected int horizontalalignment = JLabel.CENTER;
	protected int padding = 2, interlinedist = 2;
	protected Font pFont;
	protected Vector<String> text;
    private int colspan=1;
    private int rowspan=1;
	protected int verticalalignment = JLabel.CENTER;
	private int minWidth=1;

	public abstract Dimension getTextDimension();

	@Override
	public void setBackground(Color fg)
	{
		backgroundColor = fg;
	}

	@Override
	public Color getBackground()
	{
		return backgroundColor;
	}

	public void setBorderWidth(int bw)
	{
		borderwidth = bw;
	}

	@Override
	public void setFont(Font fnt)
	{
		pFont = fnt;
		super.setFont(pFont);
		fm = this.getFontMetrics(pFont);
	}

	@Override
	public void setForeground(Color fg)
	{
		foregroundColor = fg;
	}

	@Override
	public Color getForeground()
	{
		return foregroundColor;
	}

	public void setHorizontalAlignment(int horizontalalignment)
	{
		this.horizontalalignment = horizontalalignment;
	}

	public void setText(Vector<String> intext)
	{
		for (String astr : intext)
		{
			text.add(astr);
		}
		setTextSize();
		setMinimumSize(getTextDimension());
	}
	
	public void addText(String intext)
	{
		
			text.add(intext);
		
		setTextSize();
		setMinimumSize(getTextDimension());
	}
	
	public abstract void setTextSize();
	
	@Override
	public String getText()
	{
		String out ="";
		for (String astr :text)
		{
			out += astr;
		}
		return out;
	}

	public void setVerticalAlignment(int verticalalignment)
	{
		this.verticalalignment = verticalalignment;
	}

	//public abstract jcertPanel  getPanelObject();
	@Override
	public abstract Dimension getMinimumSize();

	protected int getRowspan()
	{
		return rowspan;
	}

	public void setRowspan(int rowspan)
	{
		this.rowspan = rowspan;
	}

	protected int getColspan()
	{
		return colspan;
	}

	public void setColspan(int colspan)
	{
		this.colspan = colspan;
	}

	public Dimension getMyMinimumSize() {
		Dimension d=getMinimumSize(); // TODO Auto-generated method stub
		if(d.width< minWidth) d.width = minWidth;
		return d;
	}
	
	
	
	
}
