package org.lerot.MyCash.layout;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.lerot.mywidgets.jswStyle;

public class jswMenuItem extends JMenuItem{
	
	private static final long serialVersionUID = 1L;

	public jswMenuItem(String label, String command, ActionListener ac)
	{
		setText(label);
		setFont(new Font("nonserif", Font.BOLD, 14));
		setActionCommand(command);
		addActionListener(ac);
	}

}
