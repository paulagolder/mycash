package org.lerot.MyCert;

import javax.swing.JMenu;

public class PMenu extends JMenu
{

	private static final long serialVersionUID = 1L;

	public PMenu(String label)
	{
		setText(label);
		// setMinimumSize(new Dimension (200,50));
		// setMaximumSize(new Dimension (200,50));
		setFont(MyCert_gui.guiMenufont);
	}

}
