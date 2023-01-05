package org.lerot.MyCert.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class VerticalPanel extends jcertPanel
{

	private static final long serialVersionUID = 1L;
	VerticalLayout lyt;
	
	public VerticalPanel(String name)
	{
		setName(name);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		lyt=new VerticalLayout();
		setLayout(lyt);
	}

//	public VerticalPanel(String title, boolean titledborder)
//	{
//		setAlignmentX(Component.LEFT_ALIGNMENT);
//		setLayout(new VerticalLayout());
//		// if(title.length()>0)
//		{
//			setName(title);
//			if (titledborder)
//				setBorder(setcborder(title));
//		}
//	}

	@Override
	public Dimension getMinimumSize()
	{
		LayoutManager lm = this.getLayout();
		return lm.preferredLayoutSize(this);
	}

	public void updateSize()
	{
		LayoutManager lm = this.getLayout();
		lm.layoutContainer(this);
		Dimension cd = this.getSize();
		// System.out.println(
		// " ---------VerticalPanel        w="+cd.width+" h="+ cd.height);
	}

}
