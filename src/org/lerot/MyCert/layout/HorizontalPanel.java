package org.lerot.MyCert.layout;
import java.awt.Dimension;
import java.awt.LayoutManager;


public class HorizontalPanel extends jcertPanel 
{

	private static final long serialVersionUID = 1L;
	HorizontalLayout lyt;
	
	public HorizontalPanel(String name)
	{
		setName(name);
		lyt = new HorizontalLayout();
		setLayout(lyt);
	}


	public Dimension getMinimumSize()
	{
		LayoutManager lm = this.getLayout();
		return lm.preferredLayoutSize(this);
	}
		
	
	

}
