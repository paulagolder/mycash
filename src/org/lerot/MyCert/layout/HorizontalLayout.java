package org.lerot.MyCert.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

public class HorizontalLayout extends jcertLayout
{
	private static final int DEFAULT_HGAP = 0;
	private int hgap;

	private enum Mode {
		LEFT, MIDDLE, RIGHT
	};

	public HorizontalLayout()
	{
		this(DEFAULT_HGAP);
	}

	public HorizontalLayout(int hgap)
	{
		this.hgap = hgap;
	}

	@Override
	public void layoutContainer(Container parent)
	{
		
		if(parent.getName().startsWith("tag"))
		{
			System.out.println(" in hlayout pw "+parent.getName()+" "+parent.getClass().getName());
		}
		int rightcumwidth = 0, middlecumwidth = 0, leftcumwidth = 0;
		int rightfillweight = 0, middlefillweight = 0, leftfillweight = 0;
		int rightcount = 0, middlecount = 0, leftcount = 0;
		Mode mode = Mode.LEFT;
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0)
			this.hgap = 0;
		Insets insets = parent.getInsets();
		Dimension parentSize = parent.getSize();
		int usableWidth = parentSize.width - insets.left - insets.right;
		int availableHeight = parentSize.height - insets.top - insets.bottom;
		boolean useMinimum;
		useMinimum = preferredLayoutSize(parent).width > usableWidth;
		// useMinimum=false;
		if (ncomponents == 0) {
			this.hgap = 0;
			parent.setBounds(0, 0, parentSize.width, 50);//paul fix
		} else {
			for (int i = 0; i < ncomponents; i++) 
			{
				Component comp = parent.getComponent(i);
				Dimension d = null;
				if(comp instanceof jcertPanel)
				{
					//System.out.println(" in hlayout : jcertpanel ");
					 d = comp.getMinimumSize();
				}else if (comp instanceof PLabel)
				{				
					d = ((PLabel)comp).getMinimumSize();
					//System.out.println(" in hlayout : labelpanel "+d.width);
				}else if (comp instanceof InputPanel)
			    {				
			    	//d = ((InputPanel)comp).getMinimumSize();
			    	d= comp.getMinimumSize();
			    	//System.out.println(" in hlayout : inputpanel "+d.width);
			    }
				else 
				{		
					System.out.println(" in hlayout : unknown panel "+comp.getClass().getName());
				}	
				
				String compname = comp.getName();
				if(comp.getName().startsWith("tag"))
				{
					System.out.println(" in hlayout pw2 "+comp.getName()+" "+comp.getClass().getName());
				}
				settings s = getSettings(comp);
				if (comp.isVisible()) {
					//Dimension d = comp.getMinimumSize();
					int dwidth = d.width;
					if (mode == Mode.LEFT) {
						if (s.isTrue("MIDDLE")) {
							mode = Mode.MIDDLE;
						} else if (s.isTrue("RIGHT")) {
							mode = Mode.RIGHT;
						} else {
							leftcount++;
							if (s.isTrue("FILLW")) {
								leftfillweight += d.width;
							} else {
								leftcumwidth += d.width;
							}
						}
						if (mode == Mode.MIDDLE) {
							if (s.isTrue("RIGHT")) {
								mode = Mode.RIGHT;
							} else {
								middlecount++;
								if (s.isTrue("FILLW")) {
									middlefillweight += d.width;
								} else {
									middlecumwidth += d.width;
								}
							}
						}
						if (mode == Mode.RIGHT) {
							rightcount++;
							if (s.isTrue("FILLW")) {
								rightfillweight += d.width;
							} else {
								rightcumwidth += d.width;
							}
						}
					}
				}
			}
			int xright = 0;
			int xmiddle = 0;
			int gapcount = 0;
			if (leftcount > 1)
				gapcount = leftcount - 1;
			if (middlecount > 1)
				gapcount = gapcount + middlecount - 1;
			if (rightcount > 1)
				gapcount = gapcount + rightcount - 1;
			int cumwidth = leftcumwidth + middlecumwidth + rightcumwidth;
			int fillweight = leftfillweight + middlefillweight
					+ rightfillweight;
			float fillratio=1;
			if(fillweight>0)
			   fillratio = (usableWidth - hgap * gapcount-cumwidth) / fillweight;
			float leftfillratio = fillratio;
			float rightfillratio = fillratio;
			float middlefillratio = fillratio;
			

			if (middlecount > 0) {
				int middlespace = 0;
				int rightspace = 0;
				int leftspace = 0;
				if ((leftcumwidth + leftfillweight) > (rightcumwidth + rightfillweight)) {
					middlespace = (int) (usableWidth - 2 * (leftcumwidth + (leftfillweight * fillratio)));
				} else {
					middlespace = (int) (usableWidth - 2 * (rightcumwidth + (rightfillweight * fillratio)));
				}
				xmiddle = (usableWidth - middlespace) / 2;

			} else {
				// parentSize.width - insets.left - insets.right
				xright = (int) (usableWidth - rightcumwidth
						- (rightfillweight * fillratio) - (rightcount - 1)
						* hgap);

			}
			int x = insets.left;
			int y = insets.top;

			// System.out.println(" in hlayout pw"+parentSize.width+" ymiddle
			// "+xmiddle+ " yright "+xright);
			// System.out.println(" in hlayout cm"+cummiddle+" cumright "+cumright+
			// " cum "+cumwidth);
			mode = Mode.LEFT;
			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				settings s = getSettings(comp);
				if (comp.isVisible()) {
					// Dimension d = useMinimum(comp, useMinimum);
					Dimension d = comp.getMinimumSize();
					int dwidth = d.width;
					if (mode == Mode.LEFT) {
						if (s.isTrue("MIDDLE")) {
							x = xmiddle;
							mode = Mode.MIDDLE;
						} else if (s.isTrue("RIGHT")) {
							x = xright;
							mode = Mode.RIGHT;
						} else {

							if (s.isTrue("FILLW")) {
								dwidth = (int) (dwidth * leftfillratio);
							}
						}
						if (mode == Mode.MIDDLE) {
							if (s.isTrue("RIGHT")) {
								x = xright;
								mode = Mode.RIGHT;
							} else {
								if (s.isTrue("FILLW")) {
									dwidth = (int) (dwidth * middlefillratio);
								}
							}
						}
						if (mode == Mode.RIGHT) {
							if (s.isTrue("FILLW")) {
								dwidth = (int) (dwidth * rightfillratio);
							}
						}
					}
					comp.setBounds(x, y, dwidth, availableHeight);
					x += (dwidth + this.hgap);
				}
			}
		}

	}

	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0)
			this.hgap = 0;
		Insets insets = parent.getInsets();

		int w = 0;
		int h = 0;

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			if (!comp.isVisible())
				continue;
			Dimension d = comp.getMinimumSize();
			if (h < d.height) {
				h = d.height;
			} // if
			w += d.width;
			if (i != 0) {
				w += this.hgap;
			} // if
		} // for
		return new Dimension(insets.left + insets.right + w, insets.top
				+ insets.bottom + h);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0)
			this.hgap = 0;
		Insets insets = parent.getInsets();
		int w = 0;
		int h = 0;

		for (int i = 0; i < ncomponents; i++) {
			Component comp = parent.getComponent(i);
			if (!comp.isVisible())
				continue;
			Dimension d = comp.getPreferredSize();
			if (h < d.height) {
				h = d.height;
			} // if
			w += d.width;
			if (i != 0) {
				w += this.hgap;
			}
		}
		return new Dimension(insets.left + insets.right + w, insets.top
				+ insets.bottom + h);
	}

	@Override
	public void removeLayoutComponent(Component comp)
	{
	}
}