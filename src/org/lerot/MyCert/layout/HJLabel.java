package org.lerot.MyCert.layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JLabel;

import org.lerot.MyCert.utils;

public class HJLabel extends PLabel 
{

	private static Rectangle paintTextR = new Rectangle();
	private static Insets paintViewInsets = new Insets(0, 0, 0, 0);
	private static Rectangle paintViewR = new Rectangle();
	private static final long serialVersionUID = 1L;
	private int lineheight, textwidth, textheight,columncount;

	public HJLabel()
	{
		bordercolor = Color.black;
		foregroundColor = Color.black;
		pFont = new Font("SansSerif", Font.PLAIN, 12);
		//super.setFont(pFont);
		utils.setJTextPaneFont(this, pFont, foregroundColor);
		fm = this.getFontMetrics(pFont);
		text = new Vector<String>();
		this.setOpaque(true);
		this.setEnabled(false);
	}

	@Override
	public Dimension getMinimumSize()
	{
		int sheight = textheight + 2 * padding;
		int swidth = textwidth + 2 * padding;
		return new Dimension(swidth, sheight);
	}

	@Override
	public Dimension getPreferredSize()
	{
		Dimension dimP = super.getPreferredSize();
		Dimension dimM = getMinimumSize();
		int swidth = dimP.width;
		if (dimM.width > swidth)
			swidth = dimM.width;
		int sheight = dimP.height;
		if (dimM.height > sheight)
			sheight = dimM.height;
		// return new Dimension( dim.height, dim.width +200);
		return new Dimension(swidth, sheight);
	}

	@Override
	public Dimension getTextDimension()
	{
		setTextSize();
		return new Dimension(textwidth, textheight);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setFont(pFont);
		fm = g.getFontMetrics();
		setTextSize();
		paintViewInsets = this.getInsets(paintViewInsets);
		paintViewR.x = paintViewInsets.left;
		paintViewR.y = paintViewInsets.top;
		paintViewR.height = this.getWidth()
				- (paintViewInsets.left + paintViewInsets.right);
		paintViewR.width = this.getHeight()
				- (paintViewInsets.top + paintViewInsets.bottom);
		//paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

		Graphics2D g2d = (Graphics2D) g;
		if (borderwidth > 0)
		{
			g.setColor(bordercolor);
			g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		}
		g2d.setColor(getForeground());
		g2d.setBackground(getBackground());

		int textX = paintTextR.x;
		if (horizontalalignment == JLabel.LEFT)
		{
			textX = paintTextR.x;
		} else if (horizontalalignment == JLabel.CENTER)
		{
			textX = paintTextR.x + (this.getWidth() - textwidth) / 2;
		} else
		// align=right
		{
			textX = paintTextR.x + (this.getWidth() - textwidth);
		}
		int textY = paintTextR.y + fm.getAscent();
		if (verticalalignment == JLabel.BOTTOM)
			textY = paintTextR.y + fm.getAscent()
					+ (this.getHeight() - textheight);
		else if (verticalalignment == JLabel.CENTER)
		{
			textY = paintTextR.y + fm.getAscent()
					+ (this.getHeight() - textheight) / 2;

		} else
		// align= top
		{
			textY = paintTextR.y + fm.getAscent()
					+ (this.getHeight() - textheight);
		}

		for (String aline : text)
		{
			int dx = (textwidth - fm.stringWidth(aline)) / 2;
			g.drawString(aline, textX + dx, textY);
			textY += lineheight;
		}
		 //System.out.println(" painting HJLabel"+ this.getWidth() +" "+ this.getHeight() );
	}

	@Override
	public void setTextSize()
	{
		lineheight = fm.getHeight() + interlinedist;
		int numberoflines = text.size();
		int maxlength = 0;
		columncount=0;
		for (String aline : text)
		{
			int length = fm.stringWidth(aline);
			if (length > maxlength)
				maxlength = length;
			if(columncount<aline.length()) columncount=aline.length();
		}
		textheight = numberoflines * lineheight;
		textwidth = maxlength;
	}



}
