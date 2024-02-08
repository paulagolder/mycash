package org.lerot.MyCert;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class PMenuItem extends JMenuItem
{

	private static final long serialVersionUID = 1L;

	public PMenuItem(String label)
	{
		setText(label);
		// setMinimumSize(new Dimension (200,50));
		// setMaximumSize(new Dimension (200,50));
		setFont(MyCert_gui.guiMenufont);
	}

	public PMenuItem(String label, ActionListener al)
	{
		setText(label);
		addActionListener(al);
		setActionCommand(label);
		// setMinimumSize(new Dimension (200,50));
		// setMaximumSize(new Dimension (200,50));
		setFont(MyCert_gui.guiMenufont);

	}

	public PMenuItem(String label, String command, ActionListener al)
	{
		setText(label);
		addActionListener(al);
		setActionCommand(command);
		// setMinimumSize(new Dimension (200,50));
		// setMaximumSize(new Dimension (200,50));
		setFont(MyCert_gui.guiMenufont);

	}

}
