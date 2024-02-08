package org.lerot.MyCert;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PButton extends JButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PButton(String label)
	{
		setText(label);
		setMinimumSize(new Dimension(200, 50));
		setMaximumSize(new Dimension(200, 50));
		setFont(MyCert_gui.guiMenufont);
	}

	public PButton(String label, ActionListener al)
	{
		setText(label);
		addActionListener(al);
		setActionCommand(label);
		setMinimumSize(new Dimension(200, 50));
		setMaximumSize(new Dimension(200, 50));
		setFont(MyCert_gui.guiMenufont);

	}

	public PButton(String label, String command, ActionListener al)
	{
		setText(label);
		addActionListener(al);
		setActionCommand(command);
		setMinimumSize(new Dimension(200, 50));
		setMaximumSize(new Dimension(200, 50));
		setFont(MyCert_gui.guiMenufont);

	}

}
