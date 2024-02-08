package org.lerot.MyCert.layout;

import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JComponent;

import org.dom4j.Document;
import org.dom4j.Element;
import org.lerot.MyCert.MyCert_gui;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.styleblock;
import org.lerot.MyCert.utils;

public class vgroup extends DisplayObject
{
	public vgroup()
	{
		super(null);
	}

	public vgroup(DisplayObject container)
	{
		super(container);
	}

	public vgroup(DisplayObject container, Element element)
	{
		super(container);
		if (container != null)
			container.copyStyle(this);
		this.setHorizontalAlign("left");
		initialise(container, element);
		loadAttributes(element);
	}

	public int countCols()
	{

		int maxc = 0;
		Vector<jcertObject> children = this.getMembers();
		for (jcertObject aobject : children) {
			if (aobject instanceof DisplayObject) {
				int c = ((DisplayObject) aobject).countCols();
				if (c > maxc)
					maxc = c;
			}
		}
		return maxc;
	}

	@Override
	public double getMinHeight()
	{
		double totheight = 0;
		Vector<jcertObject> children = this.getMembers();
		for (jcertObject aobject : children) {
			if (aobject instanceof DisplayObject) {
				double cellheight = ((DisplayObject) aobject).getMinHeight();
				totheight += cellheight;
			}
		}
		if (myHeight > 0 && myHeight > totheight)
			return myHeight;
		return totheight;
	}

	@Override
	public double getMinWidth()
	{
		double maxwidth = 0.0;
		Vector<jcertObject> children = this.getMembers();
		for (jcertObject aobject : children) {
			if (aobject instanceof DisplayObject) {
				double cellwidth = ((DisplayObject) aobject).getMinWidth();
				if (cellwidth > maxwidth)
					maxwidth = cellwidth;
			}
		}
		if (myWidth > 0 && myWidth > maxwidth)
			return myWidth;
		return maxwidth;
	}

	@Override
	public jcertPanel getPanelObject(Document adoc, int row)
	{
		int pad = getPadding();
		VerticalPanel apanel = new VerticalPanel(this.getName());
		formatComponent(apanel);
		if (pad > 0)
			apanel.add(Box.createRigidArea(new Dimension(0, pad)));
		if (cells.size() > 0) {
			for (jcertObject arow : cells) {
				if (arow instanceof DisplayObject) {
					if (arow.hasContent()) {
						JComponent rowpanel = ((DisplayObject) arow)
								.getPanelObject(adoc, row);
						((DisplayObject) arow).formatComponent(rowpanel);
						addwithLayout(apanel,"WIDE", rowpanel,arow);
						// System.out.println(" adding row "+((DisplayObject)
						// arow).getName());
						if (pad > 0)
							apanel.add(Box
									.createRigidArea(new Dimension(0, pad)));
					} else {
						jcertPanel npanel = new jcertPanel();
						((DisplayObject) arow).formatComponent(npanel);
						npanel.add(Box.createVerticalGlue());
						addwithLayout(apanel,"WIDE",  npanel,arow);
					}
				}
			}
		}
		if (pad > 0)
			apanel.add(Box.createRigidArea(new Dimension(0, pad)));
		if (MyCert_gui.showborders)
			apanel.setBorder(utils.setborder("blue", 1));
		Dimension min = apanel.getMinimumSize();
		apanel.setMinimumSize(min);
		apanel.setMaximumSize(min);
		return apanel;

	}

	public void initialise(DisplayObject container, Element element)
	{
		//jcertObject labelcellstyle = null;
		//jcertObject inputcellstyle = null;
		if (container != null) {
			container.copyStyle(this);
			/*inputcellstyle = findStyle("inputcell");
			container.copyFormat(inputcellstyle);
			if (inputcellstyle == null)
				inputcellstyle = this;
			labelcellstyle = findStyle("labelcell");
			container.copyFormat(labelcellstyle);
			if (labelcellstyle == null)
				labelcellstyle = this;*/
		}
		loadAttributes(element);

		List<Element> list = element.elements();

		for (Element element2 : list) {
			String ename = element2.getName();
			if (ename.equals("table")) {
				tablegroup newgroup = new tablegroup(this, element2);
				if (newgroup != null)
					cells.add(newgroup);
			}
			if (ename.equals("label")) {
				LabelCell alabel = new LabelCell(this, element2);
				cells.add(alabel);
			}
			if (ename.equals("input")) {
				InputCell anInput = new InputCell(this, element2);
				cells.add(anInput);
			}
			if (ename.equals("hgroup") || ename.equals("hblock")) {
				hgroup newblock = new hgroup(this, element2);
				if (newblock != null)
					cells.add(newblock);
			}
			if (ename.equals("vblock") || ename.equals("vgroup")) {
				vgroup newblock = new vgroup(this, element2);
				if (newblock != null)
					cells.add(newblock);
			}
			if (ename.equals("styleblock")) {
				styleblock newblock = new styleblock(this, element2);
				if (newblock != null)
					cells.add(newblock);
			}
		}
	}
	
	public void getCertificate(Element adoc)
	{
		
		if (cells.size() > 0) {
			for (jcertObject arow : cells) {
				if (arow instanceof DisplayObject) {
					if (arow.hasContent()) {
						((DisplayObject) arow).getCertificate( adoc);			
					}
				}
			}
		}
	
		
	}

}
