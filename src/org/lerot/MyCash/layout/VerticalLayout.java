package org.lerot.MyCert.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

public class VerticalLayout extends jcertLayout
{

	private int vgap;
	private static final int DEFAULT_VGAP = 0;

	public VerticalLayout()
	{
		this(DEFAULT_VGAP);
	}

	public VerticalLayout(int vgap)
	{
		this.vgap = vgap;
	}


	public Dimension preferredLayoutSize(Container parent)
	{
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0)
			vgap = 0;
		Insets insets = parent.getInsets();
		int w = 0;
		int h = 0;

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);

			if (!comp.isVisible())
				continue;
			Dimension d = comp.getPreferredSize();
			if (w < d.width) {
				w = d.width;
			}
			h += d.height;
			if (i != 0) {
				h += this.vgap;
			}
		}
		return new Dimension(insets.left + insets.right + w, insets.top
				+ insets.bottom + h);
	}
	
	public Dimension xpreferredLayoutSize(Container parent)
	{
		return minimumLayoutSize(parent);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0)
			vgap = 0;
		Insets insets = parent.getInsets();
		int w = 0;
		int h = 0;

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			if (!comp.isVisible())
				continue;
			Dimension d = comp.getMinimumSize();
			if (w < d.width) {
				w = d.width;
			}
			h += d.height;
			if (i != 0) {
				h += this.vgap;
			}
		}
		return new Dimension(insets.left + insets.right + w, insets.top
				+ insets.bottom + h);
	}

	@Override
	public void layoutContainer(Container parent)
	{
		//System.out.println(" layout VericalLayout");
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0)
			vgap = 0;
		int availableHeight = 0, cumheight = -vgap, fillweight = 0, cummiddle = 0, cumbottom = 0;
		// int premiddle = 0,preweight = 0, postweight = 0;

		boolean hasBottom = false, hasMiddle = false;
		Insets insets = parent.getInsets();
		int x = insets.left;
		int y = insets.top;
		//Dimension parentSize = parent.getMaximumSize();
		Dimension parentSize = parent.getSize();
		int usableWidth = parentSize.width - insets.left - insets.right;
		availableHeight = parentSize.height - insets.top - insets.bottom;
		boolean useMinimum;
		useMinimum = preferredLayoutSize(parent).height > availableHeight;
		if (useMinimum) {

			// System.out.println(" using minimum"
			// + preferredLayoutSize(parent).height + ">"
			// + availableHeight);
		}
		// useMinimum=false;
		//System.out.println(" useablew "+usableWidth);
			
		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			if(comp instanceof InputPanel)
			{
				//System.out.println(" sizing input cell");
			}
			settings s = getSettings(comp);
			if (comp.isVisible()) {
				if (comp instanceof InputPanel) {
					int z = 1;
				}
				Dimension d = useMinimum(comp, useMinimum);
				if (s.isTrue("FILLH")) {
					cumheight += this.vgap;
					fillweight += d.height;
				} else {
					cumheight += d.height + this.vgap;
				}
				if (s.isTrue("MIDDLE")) {
					if (hasMiddle) {
						cummiddle += d.height + this.vgap;
					} else {
						// premiddle = cumheight;
						// preweight = fillweight;
						cummiddle = d.height + this.vgap;
						hasMiddle = true;
					}
				} else if (hasMiddle)
					hasBottom = true;
				if (s.isTrue("BOTTOM"))
					hasBottom = true;
				if (hasBottom) {
					cumbottom += d.height + this.vgap;
				}
			}
		}

		

		// boolean inmmiddle = false,
		boolean inBottom = false;
		// postweight = fillweight - preweight;
		int bottomHeight = cumbottom;
		float fillratio = 1;
		if (fillweight > 0 && availableHeight > cumheight) {
			fillratio = (float) (availableHeight - cumheight)
					/ (float) fillweight;
		}
		// float prefillratio = (float) (availableHeight - cumheight)
		// / (float) fillweight;
		// float postfillratio = (float) (availableHeight - cumheight)
		// / (float) fillweight;
		if (ncomponents == 1) {
			Component comp = parent.getComponent(0);
			comp.setBounds(x, y, usableWidth, availableHeight);
		} else {
			for(int i=0;i<ncomponents;i++) {
				Component comp = parent.getComponent(i);
				settings s = getSettings(comp);
				if (comp.isVisible()) {
					Dimension d = useMinimum(comp, useMinimum);

					int dheight = d.height;
					if (!hasMiddle) {
						if (s.isTrue("FILLH")) {
							if (ncomponents == 1)
								dheight = availableHeight;
							else
								dheight = (int) (dheight * fillratio);
						}
						if (s.isTrue("BOTTOM")) {
							if (!inBottom) {
								inBottom = true;
								y = availableHeight - bottomHeight + this.vgap
										+ insets.top;
							}
						}
					}
					comp.setBounds(x, y, usableWidth, dheight);
					//System.out.println(" useablew "+usableWidth+" x="+x);
				y += (dheight + this.vgap);
				}
				
			}
		}

	}
}
