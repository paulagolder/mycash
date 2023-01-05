package org.lerot.MyCert;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class templateloaderpanel extends JPanel implements ActionListener,
		ListSelectionListener, ComponentListener
{
	private static final long serialVersionUID = 1L;
	JPanel cert;
	// private String filepath;
	int gridx = 0;
	JList list;
	DefaultListModel listModel;
	JScrollPane listpanel;
	private PButton load, unload;
	JScrollPane treeView;
	PMenu newfilemenu;
	JTextArea messagewindow;

	public templateloaderpanel()
	{
		this.addComponentListener(this);
		setName("leftPane");
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel buttonpane = new JPanel();
		buttonpane.setLayout(new BoxLayout(buttonpane, BoxLayout.Y_AXIS));
		buttonpane.setMaximumSize(new Dimension(80, 500));
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.addListSelectionListener(this);
		listpanel = new JScrollPane(list);
		listpanel.setAlignmentY(Component.TOP_ALIGNMENT);
		listpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonpane.add(listpanel);
		load = new PButton("load");
		load.setActionCommand("load");
		load.addActionListener(this);
		load.setVisible(true);
		buttonpane.add(load);
		load.setAlignmentX(Component.CENTER_ALIGNMENT);
		unload = new PButton("unload");
		unload.setActionCommand("unload");
		unload.addActionListener(this);
		unload.setVisible(true);
		unload.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonpane.add(unload);
		add(buttonpane);
		cert = new JPanel();
		cert.setBorder(utils.setborder());
		// cert.setSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		cert.setVisible(true);
		add(cert);
		setPreferredSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		// reloadAll();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		System.out.println(" action" + command);
		if (command.equals("load"))
		{
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(MyCert_gui.templatepath));
			fc.setAcceptAllFileFilterUsed(true);
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				utils.loadtemplate(file);
				reloadList();
				repaint();
			}
		}
		if (command.equals("user"))
		{
			MyCert_gui.selectCard("User");
		}
		if (command.equals("certedload"))
		{
			MyCert_gui.selectCard("Certificate loader");
		}
		if (command.equals("message"))
		{
			MyCert_gui.selectCard("Messages");
		}
	}

	private void displaycert(documentTemplate acert)
	{
		cert.removeAll();
		JPanel certpanel = new JPanel();
		certpanel.setLayout(new BoxLayout(certpanel, BoxLayout.Y_AXIS));
		JLabel name = new JLabel(acert.getName());
		certpanel.add(name);
		JLabel period = new JLabel(" Period=" + acert.getPeriod());
		certpanel.add(period);
		JLabel country = new JLabel("Country=" + acert.getCountry());
		certpanel.add(country);
		JLabel type = new JLabel(" Type=" + acert.getType());
		certpanel.add(type);
		JLabel author = new JLabel(" Author=" + acert.getAuthor());
		certpanel.add(author);
		JLabel editdate = new JLabel(" Updated=" + acert.getEditdate());
		certpanel.add(editdate);
		cert.add(certpanel);
		cert.setVisible(true);
		cert.validate();
		repaint();
	}

	public void reloadList()
	{
		// utils.loadAllTemplates();
		listModel.clear();
		for (documentTemplate acertificate : MyCert_gui.templates)
		{
			listModel.addElement(acertificate);
		}
		repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting() == false)
		{
			if (list.getSelectedIndex() != -1)
			{
				documentTemplate acert = (documentTemplate) list
						.getSelectedValue();
				displaycert(acert);
			}
		}
	}

	public JMenu getFileMenu()
	{
		newfilemenu = new PMenu("File");
		PMenuItem newitem = new PMenuItem("Certificate Edit", "certedload",
				this);
		newfilemenu.add(newitem);
		PMenuItem newitem2 = new PMenuItem("User", "user", this);
		newfilemenu.add(newitem2);
		PMenuItem newitem3 = new PMenuItem("Messages", "message", this);
		newfilemenu.add(newitem3);
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
		// TODO Auto-generated method stub

	}

}
