package org.lerot.MyCert.layout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import org.dom4j.Element;
import org.lerot.MyCert.MyCert_gui;
import org.lerot.MyCert.jcertObject;

public abstract class TextDisplayObject extends DisplayObject 
{
	protected Dimension myMinimumSize = null;
	
	protected FontMetrics fm;
	private Font pFont = null;

	public TextDisplayObject(jcertObject  container)
	{
		super(container);
	}

	@Override
	public int countDisplayObjects()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasContent()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void setPFont(Font pFont)
	{
		this.pFont = pFont;
	}

	public Font getPFont()
	{
		if (pFont == null)
			return MyCert_gui.defaultfont;
		return pFont;
	}

	public boolean hasFont()
	{
		if (pFont == null)
			return false;
		else
			return true;
	}

	public abstract Dimension getTextSize();


	@Override
	public double getMinHeight()
	{
		Dimension dim = getTextSize();
		if (myMinimumSize == null) return dim.height;
		return myMinimumSize.height;
	}

	@Override
	public double getMinWidth()
	{
		Dimension dim = getTextSize();
		if (myMinimumSize == null) return dim.width;
		return myMinimumSize.width;
	}
	
	public void getCertificate(Element newtype){};

}
