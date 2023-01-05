package org.lerot.MyCert;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class userpanel extends JPanel implements ActionListener,
		ComponentListener
{
	private static final long serialVersionUID = 1L;
	JPanel certpanel;

	private PButton update;
	JTextField name, date, email;
	PMenu newfilemenu;
	private JTextField ftpdirectory,ftphost,ftpuser, ftppassword;

	public userpanel()
	{
		this.addComponentListener(this);
		setName("leftPane");
		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		certpanel = new JPanel();
		certpanel.setLayout(new GridLayout(0, 2));

		JLabel namel = new JLabel("Current User");
		certpanel.add(namel);
		name = new JTextField();
		certpanel.add(name);
		JLabel emaill = new JLabel("User email");
		certpanel.add(emaill);
		email = new JTextField();
		certpanel.add(email);
		JLabel ftphl = new JLabel("FTP Host");
		certpanel.add(ftphl);
		 ftphost = new JTextField();
		certpanel.add(ftphost);
		JLabel ftpul = new JLabel("FTP User");
		certpanel.add(ftpul);
		 ftpuser = new JTextField();
		certpanel.add(ftpuser);
		JLabel ftpdl = new JLabel("FTP directory");
		certpanel.add(ftpdl);
		ftpdirectory = new JTextField();
		certpanel.add(ftpdirectory);
		JLabel ftppl = new JLabel("FTP Password");
		certpanel.add(ftppl);
		ftppassword = new JTextField();
		certpanel.add(ftppassword);
		
		JLabel datel = new JLabel("Current Date");
		certpanel.add(datel);
		date = new JTextField();
		certpanel.add(date);
		add(certpanel);
		certpanel.setBorder(utils.setborder());

		certpanel.setVisible(true);
		add(certpanel);
		JPanel buttonpane = new JPanel();
		buttonpane.setLayout(new BoxLayout(buttonpane, BoxLayout.X_AXIS));
		buttonpane.setMaximumSize(new Dimension(80, 500));
		update = new PButton("update");
		update.setActionCommand("update");
		update.addActionListener(this);
		update.setVisible(true);
		update.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonpane.add(update);
		add(buttonpane);
		setVisible(true);
		certpanel.setPreferredSize(new Dimension(500,200));

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		System.out.println(" action" + command);
		if (command.equals("update"))
		{
			MyCert_gui.currentuser.setName(name.getText());
			MyCert_gui.currentuser.setEmail(email.getText());
			MyCert_gui.currentuser.setFtphost(ftphost.getText());
			MyCert_gui.currentuser.setFtpusername(ftpuser.getText());
			MyCert_gui.currentuser.setFtpdirectory(ftpdirectory.getText());
			MyCert_gui.currentuser.setFtppassword(ftppassword.getText());
			MyCert_gui.currentuser.saveUser();
		}

		if (command.equals("tempload"))
		{
			MyCert_gui.selectCard("Template Loader");
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

	private void displayuser()
	{
		user cuser = MyCert_gui.currentuser;
		if (cuser == null)
			return;
		name.setText(cuser.getName());
		email.setText(cuser.getEmail());
		date.setText(utils.getTodaysDate());
	}

	public JMenu getFileMenu()
	{
		newfilemenu = new PMenu("File");
		PMenuItem newitem = new PMenuItem("Certificate Edit", "certedload",
				this);
		newfilemenu.add(newitem);
		PMenuItem newitem2 = new PMenuItem("Template Loader", "tempload", this);
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
		displayuser();
	}

	public void setMenuVisible(boolean vis)
	{
		newfilemenu.setVisible(vis);
	}

	public void createDocument(String filepath)
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("user");
		OutputStream out;
		try
		{
			out = new FileOutputStream(filepath);
			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(out, outformat);
			writer.write(document);
			writer.flush();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return;
	}

}
