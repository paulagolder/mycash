package org.lerot.MyCert.layout;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public  class  jcertPanel extends JPanel implements  ComponentListener
{
	private static final long serialVersionUID = 1L;

	private int  minWidth = 100;
	protected Color backgroundColor;
	protected Color bordercolor = Color.green;
	protected int borderwidth = 0;

	public jcertPanel()
	{
		
		setName("albert");
	}
	

	public Border setcborder(String label)
	{
		return BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(label), BorderFactory.createEmptyBorder(5,
				5, 5, 5));
	}

	public Border setLineBorder()
	{
		return BorderFactory.createLineBorder(Color.BLACK, 1);
	}

	public Border setLineBorder(int w)
	{
		return BorderFactory.createLineBorder(Color.BLACK, w);
	}

	public Border setborder()
	{
		return BorderFactory.createEmptyBorder(5, 5, 5, 5);
	}

	

	public boolean isSelected()
	{
		return false;
	}

	
	public void layoutContainer()
	{
		LayoutManager lm = this.getLayout();
		if(lm!=null)
		    lm.layoutContainer(this);
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		System.out.println(" resizing certPanel "+getName());		
		
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}



	

}
