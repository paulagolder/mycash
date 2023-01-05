package org.lerot.MyCert.layout;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComponent;

import org.dom4j.Document;
import org.dom4j.Element;
import org.lerot.MyCert.MyCert_gui;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.utils;
import org.lerot.MyCert.layout.CertificatePanel;

public class templatebody extends vgroup
{

	private static final long serialVersionUID = 1L;

	public templatebody(Element element)
	{
		super();
		//System.out.println(" loading template " + this.myName+"="+this.getName());
		this.initialise(null, element);
	}

	public jcertPanel getPanelObject(Document adoc, int row)
	{
		int pad = getPadding();
		CertificatePanel apanel = new CertificatePanel(adoc,this.getName());
		formatComponent(apanel);
		if (pad > 0)
			apanel.add(Box.createRigidArea(new Dimension(0, pad)));
		if (cells.size() > 0) {
			for (jcertObject arow : cells) {
				if (arow instanceof DisplayObject) {
					if (arow.hasContent()) {
						//if(arow instanceof tablegroup)
						{
							//System.out.println("showing  " + arow.myName+"="+arow.getClass().getName());
						}
						JComponent rowpanel = ((DisplayObject) arow)
								.getPanelObject(adoc, row);
						((DisplayObject) arow).formatComponent(rowpanel);
						apanel.add("WIDE", rowpanel);
						if (pad > 0)
							apanel.add(Box
									.createRigidArea(new Dimension(0, pad)));
					} else {
						jcertPanel npanel = new jcertPanel();
						((DisplayObject) arow).formatComponent(npanel);
						npanel.add(Box.createVerticalGlue());
						apanel.add("WIDE", npanel);
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
		;
		return apanel;

	}

	

}
