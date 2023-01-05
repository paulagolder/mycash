package org.lerot.MyCert.layout;

import java.awt.Graphics;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.styleblock;
import org.lerot.MyCert.layout.TablePanel;
import org.lerot.MyCert.layout.jcertPanel;

public class tablegroup extends DisplayObject
{
	// Vector<jcertObject> cells;
	double[] widths;
	int ncols;
	Graphics g;

	public tablegroup(DisplayObject container, Element element)
	{
		super(container);
		container.copyStyle(this);
		loadAttributes(element);
		List<Element> elist = element.elements();
		for (Element element2 : elist) {
			String ename = element2.getName();
			if (ename.equals("hgroup") || ename.equals("trow")) {
				hgroup newrow = new hgroup(this, element2);
				if (newrow != null) {
					cells.add(newrow);
				}
			}
			if (ename.equals("styleblock")) {
				styleblock newblock = new styleblock(this, element2);
				if (newblock != null)
					cells.add(newblock);
			}
		}
	}

	@Override
	public jcertPanel getPanelObject(Document adoc, int row)
	{
		//System.out.println("showing  " + this.myName+"="+this.getClass().getName());
		return getPanelObject(adoc);
	}

	@Override
	public void getCertificate(Element adoc)
	{
		Element tabletype =adoc;
		 String ttype= this.getObjecttype();
		 if(ttype!=null )
		 {
			 tabletype = adoc.addElement(ttype);
		 }
	
		for (jcertObject ahgroup : cells) {
				if (ahgroup instanceof hgroup) {
					Element  rowtype=tabletype;
					hgroup arow = (hgroup)ahgroup;		
					String  rtype= arow.getObjecttype();
					 if(rtype!=null)
					 {
						
				    		int rcount=ahgroup.getRowcount();
				    		int nrows = arow.getRowcount();
				    		if(rcount<1) rcount=1;
				    		if(rcount<nrows)rcount=nrows;				    		
				    		if(rcount>0)
				    		{
				    			for(int datarow =0; datarow<rcount;datarow++)
				    			{
				    				rowtype = tabletype.addElement(rtype);
				    			}
				    		}
				    
				    	}
					}
					
					
				
		}

		
	}
	
	
	
	
	
	
	
	@Override
	public jcertPanel getPanelObject(Document adoc)
	{
		int nr = this.countDisplayObjects();
		int nc = this.countCols();
		setWidths();
		TablePanel bgpanel = new TablePanel(nr, nc);
		formatComponent(bgpanel);
		//System.out.println(" setting table " + getName());
	    int tablerow = 0;
		for (jcertObject ahgroup : cells) {
			if (ahgroup instanceof DisplayObject) {
				if (ahgroup instanceof hgroup) {
					hgroup arow = (hgroup)ahgroup;
					
					int fieldcount = arow.countFields();
				//	System.out.println(" ----------- fieldcount " + fieldcount);
					if(fieldcount >0)
					{
					    String otype = arow.getObjectPath();
				    	if(otype!=null)
				    	{   
				    		int rcount=ahgroup.getRowcount();
				    		int nrows = arow.getRowcount();
				    		if(rcount<1) rcount=1;
				    		if(rcount<nrows)rcount=nrows;
				    		
				    		if(adoc!=null)
				    		{
				    		 int ncount = adoc.selectNodes(otype).size();
				    		 if(ncount>rcount)rcount=ncount;
				    		}
				    //		System.out.println(" ----------- path "+otype+" rowcount " + rcount);
				    		if(rcount>0)
				    		{
				    			for(int datarow =0; datarow<rcount;datarow++)
				    			{
				    		//		System.out.println(" ----------- adding "+tablerow+" datarow " + datarow);
				    				bgpanel.addRow(adoc, ahgroup, tablerow,datarow);
				    				tablerow++;
				    			}
				    		}
				    
				    	}
					}
					else
					{
					bgpanel.addRow(adoc, ahgroup,tablerow);
					tablerow++;
					}
					
				} else {
					System.out.println(" not part of a table "
							+ ahgroup.getClass().getName());
				}
			}
		}
		bgpanel.repaint();
		return bgpanel;
	}

	private double[] getWidths()
	{
		return widths;
	}

	@Override
	public int countDisplayObjects()
	{
		return cells.size();
	}

	public double setWidths()
	{
		int mc = countCols();
	//	widths = new double[mc];// paul fix
		widths = new double[20];// paul fix
		for (int i = 0; i < mc; i++)
			widths[i] = 0.0;
		for (jcertObject ahgroup : cells) {
			if (ahgroup instanceof hgroup) {
				int c = 0;
				for (jcertObject acell : ((hgroup) ahgroup).getMembers()) {
					if (acell instanceof DisplayObject) {
						double cellwidth = ((DisplayObject) acell)
								.getMinWidth();
						if (widths[c] < cellwidth)
							widths[c] = cellwidth;
						c++;
					}
				}
			}
		}
		double totwidth = 0.0;
		for (int i = 0; i < mc; i++)
			totwidth += widths[i];
		return totwidth;
	}

	public int countCols()
	{
		int maxcols = 0;
		for (jcertObject ahgroup : cells) {
			if (ahgroup instanceof hgroup) {
				int c = 0;
				for (jcertObject acell : ((hgroup) ahgroup).getMembers()) {

					if (acell instanceof DisplayObject) {
						c = c + ((DisplayObject)acell).countCols();
					}
				}
				if (c > maxcols)
					maxcols = c;
			}
		}
		return maxcols;
	}

	@Override
	public double getMinWidth()
	{
		double w = setWidths();
		if (myWidth > 0 && myWidth > w)
			return myWidth;
		return w;
	}

	@Override
	public double getMinHeight()
	{
		double totheight = 0.0;

		for (jcertObject ahgroup : cells) {
			if (ahgroup instanceof hgroup) {
				for (jcertObject acell : ((hgroup) ahgroup).getMembers()) {
					if (acell instanceof DisplayObject) {
						double rowheight = ((DisplayObject) acell)
								.getMinHeight();
						totheight += rowheight;
					}
				}
			}
		}
		if (myHeight > 0 && myHeight > totheight)
			return myHeight;
		return totheight;
	}

	

}
