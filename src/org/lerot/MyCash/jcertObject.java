package org.lerot.MyCert;

import java.awt.Font;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

public abstract class jcertObject
{
	protected static int counter = 0;
	protected String backgroundcolor = null;
	protected String bordercolor = "black";
	protected String cellbordercolor = "black";
	protected int borderWidth = -1;
	protected int cellborderWidth = -1;
	protected int colspan = 1;
	protected int columns = 10;
	private jcertObject parent = null;
	protected String direction = "horizontal";
	protected String fontname = "SansSerif";
	protected int fontsize = 10;
	protected String fontstyle = "PLAIN";
	protected int interblockspacing = 0;
	protected String foregroundcolor = null;
	protected int headingborder = 1;
	protected String horizontalAlign = null;
	protected int idno;
	protected int myHeight;
	protected int myWidth;
	protected String myName;
	private String objecttype=null;

	protected int padding = 0;
	protected int tgap = 1;
	protected int rowspan = 1;
	protected int rowcount;
	protected String verticalAlign = null;
	private String layout = null;

	public jcertObject(jcertObject container)
	{
		this.parent = container;
	}

	public void copyStyle(jcertObject dispobj)
	{
		if (dispobj == null)
			return;
		if (borderWidth > -1) {
			dispobj.setBorderWidth(borderWidth);
		}
		if (bordercolor != null) {
			dispobj.setBordercolor(bordercolor);
		}
		if (foregroundcolor != null) {
			dispobj.setForegroundcolor(foregroundcolor);
		}
		if (backgroundcolor != null) {
			dispobj.setBackgroundcolor(backgroundcolor);
		}
		if (fontsize > 0) {
			dispobj.setFontsize(fontsize);
		}
		if (fontstyle != null) {
			dispobj.setFontstyle(fontstyle);
		}
		if(layout!=null)
		{
			dispobj.setLayout(layout);
		}

	}

	public abstract int countDisplayObjects();

	public String getBackgroundcolor()
	{
		return backgroundcolor;
	}

	public String getBordercolor()
	{
		return bordercolor;
	}

	public int getBorderWidth()
	{
		return borderWidth;
	}

	public int getColspan()
	{
		if (colspan > 0)
			return colspan;
		else
			return 1;
	}

	public String getDirection()
	{
		return direction;
	}

	public Font getFont()
	{
		if (fontsize > 0) {
			return new Font(MyCert_gui.defaultfontname, Font.PLAIN, fontsize);
		} else
			return MyCert_gui.defaultfont;
	}

	public String getFontname()
	{
		return fontname;
	}

	public int getFontsize()
	{
		return fontsize;
	}

	public String getFontstyle()
	{
		return fontstyle;
	}

	public String getForegroundcolor()
	{
		return foregroundcolor;
	}

	public int getHeadingborder()
	{
		return headingborder;
	}

	public String getHorizontalAlign()
	{
		return horizontalAlign;
	}

	public int getInterblockspacing()
	{
		return interblockspacing;
	}

	public int getMyHeight()
	{
		return myHeight;
	}

	public int getMyWidth()
	{
		return myWidth;
	}

	public String getName()
	{
		if (myName != null)
			return myName;
		else
			return "ID" + idno;
	}

	public String getObjectPath()
	{
		String objectpath = null;
		String objtype = this.getObjecttype();
		if (objtype != null)

			objectpath = objtype;

		jcertObject container = this.getParent();
		if (container.getObjecttype() != null) {
			if (objectpath == null)
				objectpath = container.getObjecttype();
			else
				objectpath = container.getObjecttype() + "/" + objectpath;
		}
		while (container.getParent() != null) {
			container = container.getParent();
			objtype = container.getObjecttype();
			if (objtype != null) {
				if (objectpath == null) {
					objectpath = container.getObjecttype();
				} else if (!objectpath.startsWith("/")) {
					if (!objectpath.endsWith(objtype)) {
						objectpath = container.getObjecttype() + "/"
								+ objectpath;
					}
				} else
					objectpath = container.getObjecttype();
			}
		}
		String path = null;
		if (objectpath != null) {
			if (!objectpath.startsWith("//")) {
				if (!objectpath.startsWith("/"))
					path = "//" + objectpath;
				else
					path = "/" + objectpath;
				objectpath = path;
			}
		}
		return objectpath;
	}

	public String getObjecttype()
	{
		return this.objecttype;
	}

	public int getPadding()
	{
		return padding;
	}

	public jcertObject getParent()
	{
		return parent;
	}

	public int getRowcount()
	{
		if (rowcount < 1)
			return 1;
		else
			return rowcount;
	}

	public int getRowspan()
	{
		if (rowspan > 0)
			return rowspan;
		else
			return 1;
	}

	public String getVerticalAlign()
	{
		return verticalAlign;
	}

	public abstract boolean hasContent();

	public boolean hasHorizontalAlign()
	{
		if (horizontalAlign != null)
			return true;
		else
			return false;
	}

	public boolean isHorizontal()
	{
		if (direction != null && direction.startsWith("v"))
			return false;
		else
			return true;
	}

	public void loadAttributes(Element element)
	{
		
		List<Attribute> list = element.attributes();
		for (Attribute attribute : list) {
			String aname = attribute.getName();
			if (aname.equals("myname")) {
				myName = attribute.getStringValue();
			} else if (aname.equals("name")) {
				myName = attribute.getStringValue();
			} else if (aname.equals("objecttype"))
				setObjecttype(attribute.getStringValue());
			else if (aname.equals("width"))
				myWidth = (Integer.parseInt(attribute.getStringValue()));
			else if (aname.equals("columns"))
				columns = (Integer.parseInt(attribute.getStringValue()));
			else if (aname.equals("height"))
				myHeight = (Integer.parseInt(attribute.getStringValue()));
			else if (aname.equals("layout"))
			{
				layout = (attribute.getStringValue());
				//System.out.println("layout added "+layout);
			}
			else if (aname.equals("align"))
				horizontalAlign = (attribute.getStringValue());
			else if (aname.equals("horizontalalign"))
				horizontalAlign = (attribute.getStringValue());
			else if (aname.equals("verticalalign"))
				verticalAlign = (attribute.getStringValue());
			else if (aname.equals("borderwidth")) {
				borderWidth = (Integer.parseInt(attribute.getStringValue()));
			} else if (aname.equals("headingborder"))
				headingborder = (Integer.parseInt(attribute.getStringValue()));
			else if (aname.equals("cellborderwidth"))
				cellborderWidth = (Integer.parseInt(attribute.getStringValue()));
			else if (aname.equals("cellbordercolor"))
				cellbordercolor = (attribute.getStringValue());
			else if (aname.equals("bordercolor"))
				bordercolor = (attribute.getStringValue());
			else if (aname.equals("backgroundcolor")) {
				backgroundcolor = (attribute.getStringValue());
			} else if (aname.equals("foregroundcolor"))
				foregroundcolor = (attribute.getStringValue());
			else if (aname.equals("font-size"))
				fontsize = (Integer.parseInt(attribute.getStringValue()));
			else if (aname.equals("font-style"))
				fontstyle = (attribute.getStringValue());
			else if (aname.equals("direction"))
				direction = (attribute.getStringValue());
			else if (aname.equals("colspan")) {
				colspan = (Integer.parseInt(attribute.getStringValue()));
			} else if (aname.equals("rowspan"))
				rowspan = (Integer.parseInt(attribute.getStringValue()));
			else if (aname.equals("padding")) {
				padding = (Integer.parseInt(attribute.getStringValue()));
			} else if (aname.equals("interblockspacing"))
				interblockspacing = (Integer.parseInt(attribute
						.getStringValue()));
			else if (aname.equals("tgap"))
				tgap = Integer.parseInt(attribute.getStringValue());
			else if (aname.equals("rows"))
				rowcount = Integer.parseInt(attribute.getStringValue());
			else if (!aname.equals("target"))
				System.out.println(" Unrecognised attribute " + aname);

		}
	}

	public void setBackgroundcolor(String backgroundcolor)
	{
		this.backgroundcolor = backgroundcolor;
	}

	public void setBordercolor(String bordercolor)
	{
		this.bordercolor = bordercolor;
	}

	public void setBorderWidth(int border)
	{
		this.borderWidth = border;
	}

	public void setColspan(int colspan)
	{
		this.colspan = colspan;
	}

	public void setDirection(String direction)
	{
		this.direction = direction.toLowerCase();
	}

	public void setFontname(String fontname)
	{
		this.fontname = fontname;
	}

	public void setFontsize(int fontsize)
	{
		this.fontsize = fontsize;
	}

	public void setFontstyle(String fontstyle)
	{
		this.fontstyle = fontstyle;
	}

	public void setForegroundcolor(String foregroundcolor)
	{
		this.foregroundcolor = foregroundcolor;
	}

	public void setHeadingborder(int headingborder)
	{
		this.headingborder = headingborder;
	}

	public void setHorizontalAlign(String ahorizontalAlign)
	{
		String ha = ahorizontalAlign.toLowerCase();
		if (ha.startsWith("l"))
			horizontalAlign = "LEFT";
		else if (ha.startsWith("r"))
			horizontalAlign = "RIGHT";
		else
			horizontalAlign = "CENTER";
	}

	public void setInterblockspacing(int interblockspacing)
	{
		this.interblockspacing = interblockspacing;
	}

	public void setMyHeight(int myHeight)
	{
		this.myHeight = myHeight;
	}

	public void setMyWidth(int myWidth)
	{
		this.myWidth = myWidth;
	}

	public void setName(String name)
	{
		this.myName = name;
	}

	protected void setObjecttype(String objecttype)
	{
		this.objecttype = objecttype;
	}

	public void setPadding(int padding)
	{
		this.padding = padding;
	}

	protected void setParent(jcertObject container)
	{
		this.parent = container;
	}

	public void setRowcount(int rowcount)
	{
		this.rowcount = rowcount;
	}

	public void setRowspan(int rowspan)
	{
		this.rowspan = rowspan;
	}

	public void setVerticalAlign(String averticalAlign)
	{
		String va = averticalAlign.toLowerCase();
		if (va.startsWith("t"))
			verticalAlign = "TOP";
		else if (va.startsWith("b"))
			verticalAlign = "BOTTOM";
		else
			verticalAlign = "MIDDLE";
	}

	public String getLayout()
	{
		return layout;
	}

	protected void setLayout(String layout)
	{
		this.layout = layout;
	}
	
	

}
