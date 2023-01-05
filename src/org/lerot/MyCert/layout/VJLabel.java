package org.lerot.MyCert.layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import org.lerot.MyCert.utils;

public class VJLabel extends PLabel
{

	private static final long serialVersionUID = 1L;
	protected boolean clockwise = false;
	private int lineheight, textwidth, textheight;

	public VJLabel()
	{
		bordercolor = Color.CYAN;
		foregroundColor = Color.black;
		pFont = new Font("SansSerif", Font.PLAIN, 12);
		utils.setJTextPaneFont(this, pFont, foregroundColor);
		// super.setFont(pFont);
		fm = this.getFontMetrics(pFont);
		text = new Vector<String>();
		this.setOpaque(false);
		this.setEnabled(false);
	}

	@Override
	public Dimension getMinimumSize()
	{
		int twidth = textwidth + 4 * padding;
		int theight = textheight + 2 * padding;
		return new Dimension(theight, twidth);
	}

	@Override
	public Dimension getPreferredSize()
	{
		setTextSize();
		Dimension dimP = super.getPreferredSize();
		Dimension dimM = getMinimumSize();
		int swidth = dimP.height;
		if (dimM.width > swidth)
			swidth = dimM.width;
		int sheight = dimP.width;
		if (dimM.height > sheight)
			sheight = dimM.height;
		// return new Dimension( dim.height, dim.width +200);
		return new Dimension(swidth, sheight);
	}

	@Override
	public Dimension getTextDimension()
	{
		// return new Dimension(textwidth,textheight);
		return getMinimumSize();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setFont(pFont);
		fm = g.getFontMetrics();
		setTextSize();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform tr = g2d.getTransform();
		if (clockwise) {
			g2d.rotate(Math.PI / 2);
			g2d.translate(0, -this.getWidth());
		} else {
			g2d.rotate(-Math.PI / 2);
			g2d.translate(-this.getHeight(), 0);
		}

		g2d.setColor(getForeground());
		g2d.setBackground(getBackground());
		g.setColor(getForeground());
		if (borderwidth > 0) {
			g2d.setColor(bordercolor);
			// g2d.setColor(Color.RED);
			g2d.drawRect(1, 1, getHeight() - 2, getWidth() - 2);
		}
		g2d.setColor(getForeground());
		int textX = (this.getHeight() - textwidth) / 2;
		int textY = padding + ((this.getWidth() - textheight) / 2) + lineheight;
		// int textX = (this.getWidth() - textwidth) / 2 ;
		// int textY = (this.getHeight() - textheight) / 2;
		for (String aline : text) {
			int dx = (textwidth - fm.stringWidth(aline)) / 2;
			g.drawString(aline, textX + dx, textY);
			textY += lineheight;
		}
		g2d.setTransform(tr);
	}

	@Override
	public void setText(Vector<String> intext)
	{
		for (String astr : intext) {
			text.add(astr);
		}
	}

	@Override
	public void setTextSize()
	{
		lineheight = fm.getHeight() + interlinedist;
		int numberoflines = text.size();
		int maxlength = 0;
		for (String aline : text) {
			int length = fm.stringWidth(aline);
			if (length > maxlength)
				maxlength = length;
		}
		textheight = numberoflines * lineheight;
		textwidth = maxlength;
	}

}
