package org.lerot.MyCert;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class messagepanel extends JPanel implements ActionListener,
		ComponentListener
{
	private static final long serialVersionUID = 1L;
	JTextArea certpanel;

	private PButton clear;

	PMenu newfilemenu;

	public messagepanel()
	{
		this.addComponentListener(this);
		setName("leftPane");
		setLayout(new BorderLayout());
		certpanel = new JTextArea();

		certpanel.setBorder(utils.setborder());

		certpanel.setVisible(true);
		JScrollPane scrollpane = new JScrollPane(certpanel);
		scrollpane.setPreferredSize(new Dimension(500, 500));
		add(scrollpane, BorderLayout.CENTER);
		JPanel buttonpane = new JPanel();
		buttonpane.setLayout(new BoxLayout(buttonpane, BoxLayout.X_AXIS));
		buttonpane.setMaximumSize(new Dimension(80, 500));
		clear = new PButton("Clear");
		clear.setActionCommand("clear");
		clear.addActionListener(this);
		clear.setVisible(true);
		clear.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonpane.add(clear);
		add(buttonpane, BorderLayout.PAGE_END);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		System.out.println(" action" + command);
		if (command.equals("clear"))
		{
			certpanel.removeAll();
		}

		if (command.equals("tempload"))
		{
			MyCert_gui.selectCard("Template Loader");
		}
		if (command.equals("certedload"))
		{
			MyCert_gui.selectCard("Certificate loader");
		}
	}

	public JMenu getFileMenu()
	{
		newfilemenu = new PMenu("File");
		PMenuItem newitem = new PMenuItem("Certificate Edit", "certedload",
				this);
		newfilemenu.add(newitem);
		PMenuItem newitem2 = new PMenuItem("Template Loader", "tempload", this);
		newfilemenu.add(newitem2);

		return newfilemenu;
	}

	public void setMenu(JMenuBar mainmenu)
	{
		mainmenu.add(getFileMenu());
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		setMenuVisible(false);
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		setMenuVisible(true);

	}

	public void setMenuVisible(boolean vis)
	{
		newfilemenu.setVisible(vis);
	}

	public void display(String string)
	{
		certpanel.append(string + "\n");

	}

	public void error(String string)
	{
		display(string);

	}

}
