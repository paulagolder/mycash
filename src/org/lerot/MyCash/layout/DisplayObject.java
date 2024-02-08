package org.lerot.MyCert.layout;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.lerot.MyCert.MyCert_gui;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.styleblock;
import org.lerot.MyCert.utils;

public abstract class DisplayObject extends jcertObject
{

	protected Vector<jcertObject> cells;

	public DisplayObject(jcertObject container)
	{
		super(container);
		counter++;
		idno = counter;
		borderWidth = -1;
		horizontalAlign = null;
		verticalAlign = null;
		foregroundcolor = null;
		bordercolor = "black";
		myWidth = -1;
		myHeight = -1;
		myName = "ID" + idno;
		cells = new Vector<jcertObject>();
	}

	public void add(DisplayObject newcell)
	{
		cells.add(newcell);
	}

	public abstract int countCols();

	public int countDisplayObjects()
	{
		int totrows = 0;
		Vector<jcertObject> children = this.getMembers();
		for (jcertObject aobject : children) {
			if (aobject instanceof DisplayObject) {
				totrows += aobject.countDisplayObjects();
			}
		}
		return totrows;
	}

	public int countFields()
	{
		int totf = 0;
		Vector<jcertObject> children = this.getMembers();
		for (jcertObject aobject : children) {

			if (aobject instanceof InputCell) {
				totf = totf + 1;
			} else if (aobject instanceof LabelCell) {

			} else if (aobject instanceof DisplayObject) {
				totf += ((DisplayObject) aobject).countFields();
			}
		}
		return totf;
	}

	

	public void formatComponent(JComponent apanel)
	{
        
		if (foregroundcolor != null)
			apanel.setForeground(utils.color(foregroundcolor));
		if (backgroundcolor != null) {
			apanel.setBackground(utils.color(backgroundcolor));
			apanel.setOpaque(true);
		} else
			apanel.setOpaque(false);
		if (borderWidth > 0) {
			if (bordercolor == null)
				bordercolor = "BLACK";
			apanel.setBorder(utils.setborder(bordercolor, borderWidth));
		}
		if (apanel instanceof TablePanel) {
			if (cellborderWidth > 0) {
				((TablePanel) apanel).setCellborderwidth(cellborderWidth);
			}
			if (cellbordercolor != null) {
				((TablePanel) apanel).setCellbordercolor(cellbordercolor);
			}
		}

		if (fontsize > 0) {
			apanel.setFont(new Font(MyCert_gui.defaultfontname, Font.PLAIN,
					fontsize));
		} else
			apanel.setFont(MyCert_gui.defaultfont);

		if (fontname != null)
			apanel.setFont(new Font(fontname, utils.fontstyle(fontstyle),
					fontsize));

		if (horizontalAlign != null) {
			if (horizontalAlign.equalsIgnoreCase("left"))
				apanel.setAlignmentX(JLabel.LEFT);
			if (horizontalAlign.equalsIgnoreCase("right"))
				apanel.setAlignmentX(JLabel.RIGHT);
			if (horizontalAlign.equalsIgnoreCase("center"))
				apanel.setAlignmentX(JLabel.CENTER);
		}
		if (verticalAlign != null) {
			if (verticalAlign.equalsIgnoreCase("top"))
				apanel.setAlignmentY(JLabel.TOP);
			if (verticalAlign.equalsIgnoreCase("bottom"))
				apanel.setAlignmentY(JLabel.BOTTOM);
			if (verticalAlign.equalsIgnoreCase("center"))
				apanel.setAlignmentY(JLabel.CENTER);
		}

		if (apanel instanceof PLabel) {
			if (colspan > 1)
				((PLabel) apanel).setColspan(colspan);
			if (rowspan > 1)
				((PLabel) apanel).setRowspan(rowspan);
		}
	}

	public Vector<jcertObject> getMembers()
	{
		return cells;
	}

	public abstract double getMinHeight();

	public abstract double getMinWidth();

	public JComponent getPanelObject(Document adoc)
	{
		return getPanelObject(adoc, 0);
	}

	public abstract JComponent getPanelObject(Document adoc, int row);

	public boolean hasContent()
	{
		int n = countDisplayObjects();
		if (n > 0)
			return true;
		else
			return false;
	}

	public abstract void getCertificate(Element newtype);
	
    public void addwithLayout(jcertPanel target, String setting, JComponent apanel, jcertObject so)
    {
    	String newsetting = setting;
    	String layout = so.getLayout();
    	if(layout!=null)
    	{
    		newsetting = newsetting+layout;
    		//System.out.println(" adding with layout"+newsetting);
    		//do somethng here paul fix
    	}
    	target.add(newsetting,apanel);
    }
    
    public styleblock findStyle(String name)
	{
		Vector<jcertObject> children = this.getMembers();
		for (jcertObject aobject : children) {
			if (aobject instanceof styleblock) {
				String aname = aobject.getName();
				if (aname != null && name.equals(aname)) {
					//System.out.println(" found style "+name);
					return (styleblock) aobject;
				}
			}
		}
		jcertObject aparent = this.getParent();
		if (aparent != null && aparent instanceof DisplayObject) {
			return ((DisplayObject) aparent).findStyle(name);
		} else
			return null;

	}
}
