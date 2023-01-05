package org.lerot.MyCert.layout;

import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;

import org.dom4j.Document;
import org.dom4j.Element;
import org.lerot.MyCert.MyCert_gui;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.styleblock;
import org.lerot.MyCert.utils;
import org.lerot.MyCert.layout.HorizontalPanel;
import org.lerot.MyCert.layout.jcertPanel;

public class hgroup extends DisplayObject
{
	
	public hgroup(jcertObject  container, Element element)
	{
		super(container);
		container.copyStyle(this);
		this.setHorizontalAlign("left");
		loadAttributes(element);
		if(this.getName().startsWith("tag"))
		{
			System.out.println( " loading hgroup "+ this.getName());
		}
		
		

		List<Element> elist = element.elements();
		for (Element element2 : elist) {
			String ename = element2.getName();

			if (ename.equals("table")) {
				tablegroup newgroup = new tablegroup(this, element2);
				if (newgroup != null)
					cells.add(newgroup);
			}
			if (ename.equals("vgroup")) {
				vgroup newrowgroup = new vgroup(this, element2);
				if (newrowgroup != null)
					cells.add(newrowgroup);
			}
			if (ename.equals("tcell")) {
				vgroup newrowgroup = new vgroup(this, element2);
				if (newrowgroup != null)
					cells.add(newrowgroup);
			}
			if (ename.equals("hgroup") ) {
				hgroup newblockgroup = new hgroup(this, element2);
				if (newblockgroup != null)
					cells.add(newblockgroup);
			}
		
			if (ename.equals("input")) {
				InputCell anInput = new InputCell(this, element2);
				cells.add(anInput);
			}
			if (ename.equals("label")) {
				LabelCell anInput = new LabelCell(this,element2);
				cells.add(anInput);
			}
			if (ename.equals("styleblock")) {
				styleblock newblock = new styleblock((DisplayObject)this, element2);
				if (newblock != null)
					cells.add(newblock);
			}
		}
	}
	
	@Override
	public int countDisplayObjects()
	{
		int maxrows = 0;
		Vector<jcertObject> children = this
				.getMembers();
		for (jcertObject aobject : children) {
			if(aobject instanceof DisplayObject)
			{
			int rowcount = aobject.countDisplayObjects();
			if (rowcount > maxrows)
				maxrows = rowcount;
			}
		}
		return maxrows;
	}

	@Override
	public double getMinHeight()
	{
		double minheight = 0;
		Vector<jcertObject> children = this
				.getMembers();
		for (jcertObject aobject : children) {
			double cellheight = ((DisplayObject)aobject).getMinHeight();
			if (minheight < cellheight)
				minheight = cellheight;
		}
		if (myHeight > 0 && myHeight > minheight)
			return myHeight;
		else
			return minheight;
	}

	@Override
	public double getMinWidth()
	{
		double totwidth = 0;
		Vector<jcertObject> children = this
				.getMembers();
		for (jcertObject aobject : children) {
			double cellwidth = ((DisplayObject)aobject).getMinWidth();
			totwidth += cellwidth;
		}
		if (myWidth > 0 && myWidth > totwidth)
			return myWidth;
		return totwidth;
	}


	@Override
	public jcertPanel getPanelObject(Document adoc,int row)
	{
		HorizontalPanel bgpanel = new HorizontalPanel(this.getName());
		formatComponent(bgpanel);
		if(this.getName().startsWith("tag"))
		{
			System.out.println(" displaying hgroup  " + getName());
		}
		if (MyCert_gui.showborders)
			bgpanel.setBorder(utils.setborder("yellow", 1));
		if (cells.isEmpty()) {
		//	System.out.println(" setting empty hgroup  " + getName());
			bgpanel.setMaximumSize(new Dimension(10, 5));
			return bgpanel;
		} else {
			for (jcertObject agroup : cells) {
				if (agroup instanceof DisplayObject) {
					
						if (agroup.hasContent()) {
							JComponent apanel = ((DisplayObject)agroup).getPanelObject(adoc,row);
							apanel.setName(agroup.getName());
							((DisplayObject) agroup).formatComponent(apanel);
							addwithLayout(bgpanel,"TALL", apanel,agroup);
						} else {
							jcertPanel apanel = new jcertPanel();
							apanel.setName(agroup.getName());
							((DisplayObject) agroup).formatComponent(apanel);
							addwithLayout(bgpanel,"TALL", apanel,agroup);
						}
					
				}
			}
			Dimension min = new Dimension((int) getMinWidth(),
					(int) getMinHeight());
			// bgpanel.setBorder(utils.setborder(Color.yellow,2));
			// Dimension min = bgpanel.getMinimumSize();
			bgpanel.setMinimumSize(min);
		//	System.out.println(" setting hgroup  " + getName() + " "
		//			+ min.width + " " + min.height);
			return bgpanel;
		}
	}

	

	public int countCols()
	{
		int c = 0;
		Vector<jcertObject> children = this
				.getMembers();
		for (jcertObject aobject : children) {
			if(aobject instanceof DisplayObject)
			{
			  c= c+((DisplayObject)aobject).countCols();
			}
		}
		
		return c;
	}
	
	public  void getCertificate(Element newtype){};

}