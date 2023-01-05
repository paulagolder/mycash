package org.lerot.MyCert;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.lerot.MyCert.layout.VerticalPanel;

public class certificateeditpanel extends JPanel implements ActionListener,
		ItemListener, ComponentListener

{
	private static final long serialVersionUID = 1L;
	JPanel cert, certframe, footnote, heading;
	Document currentdocument;
	documentTemplate currenttemplate;
	private String filepath;
	JComboBox list;
	DefaultComboBoxModel listModel;
	JMenu newcertificatemenu;
	String savefile = "certificate.xml";
	private PTextField savepath;
	JScrollPane treeView;
	PMenu newfilemenu;
	PMenuItem loadmenu;
	PMenu savemenu;
	VerticalPanel certificate;

	public certificateeditpanel()
	{
		this.addComponentListener(this);
		setName("topPane");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		certframe = new JPanel();
		certframe.setBorder(utils.setborder("pink", 2));
		certframe.setName("certframe");
		certframe.setLayout(new BorderLayout());
		certframe.setOpaque(false);

		heading = new JPanel();
		heading.setName("heading ");
		heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
		heading.setAlignmentX(CENTER_ALIGNMENT);
		certframe.add(heading, BorderLayout.PAGE_START);
		cert = new JPanel();
		cert.setName("cert");
		cert.setAlignmentX(LEFT_ALIGNMENT);
		certframe.add(cert, BorderLayout.CENTER);
		footnote = new JPanel();
		footnote.setName("footnote");
		footnote.setLayout(new BoxLayout(footnote, BoxLayout.X_AXIS));
		footnote.setAlignmentX(CENTER_ALIGNMENT);
		certframe.add(footnote, BorderLayout.PAGE_END);
		certframe.setVisible(true);
		add(certframe);
		cert.validate();
		certframe.validate();
		validate();
		savepath = new PTextField(savefile);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		// System.out.println(" action here "+command);
		if (command.startsWith("NEWAFTER:")) {
			String tid = command.substring(9);
			String delims = ":";
			String[] tokens = tid.split(delims);
			String path = tokens[0];
			int rowno = Integer.parseInt(tokens[1]);
			System.out.println(" NEW " + tokens[0] + "-" + rowno);
			// currentdocument.addElement(arg0);
			List nodeList = currentdocument.selectNodes(path);
			Node anode = (Node) nodeList.get(rowno);
			Element parent = anode.getParent();
			List list = parent.elements();
			Element newElement = (Element) anode.clone();
			list.add(rowno + 1, newElement);
			
			displaycert(currenttemplate, currentdocument);
		}
		if (command.startsWith("NEWBEFORE:")) {
			String tid = command.substring(10);
			String delims = ":";
			String[] tokens = tid.split(delims);
			String path = tokens[0];
			int rowno = Integer.parseInt(tokens[1]);
			System.out.println(" NEWBEFORE " + tokens[0] + "-" + rowno);
			// currentdocument.addElement(arg0);
			List nodeList = currentdocument.selectNodes(path);
			Node anode = (Node) nodeList.get(rowno);
			Element parent = anode.getParent();
			List list = parent.elements();
			Element newElement = (Element) anode.clone();
			list.add(rowno, newElement);
			displaycert(currenttemplate, currentdocument);
		}
		if (command.startsWith("DELETE:")) {
			String tid = command.substring(7);
			String delims = ":";
			String[] tokens = tid.split(delims);
			String path = tokens[0];
			int rowno = Integer.parseInt(tokens[1]);
			System.out.println(" DELETE " + tokens[0] + "-" + rowno);
			List nodeList = currentdocument.selectNodes(path);
			Node anode = (Node) nodeList.get(rowno);
			Element parent = anode.getParent();
			parent.remove(anode);
			displaycert(currenttemplate, currentdocument);

		}
		if (command.startsWith("LOAD:")) {
			String tid = command.substring(5);
			currenttemplate = documentTemplate.findTemplate(tid);
			currentdocument = currenttemplate.makeNewDocument();
			displaycert(currenttemplate,currentdocument);
			savepath.setText(currenttemplate.getSavefilename());
		}
		if (command.equals("saveas")) {
			savefile = savepath.getText();
			if (!savefile.startsWith("/"))
				savefile = MyCert_gui.certificatepath + savefile;
			Document doc = createDocument(savefile);
			//String stylesheet ="freddy";
			//Document styleddoc =  styleDocument (doc, stylesheet) ;
			try {
				serializetoXML(doc, savefile);
				System.out.println(" file saved" + savefile);
			} catch (Exception e2) {
				System.out.println(" file problem " + e2);
			}
		}
		if (command.equals("saveauton")) {
			savefile = savepath.getText();
			if (!savefile.startsWith("/"))
				savefile = MyCert_gui.certificatepath + savefile;
			Document doc = createDocument(savefile);
			Node savepathnode = doc.selectSingleNode("Savepath");
			if (savepathnode != null) {
				String spath = savepathnode.getStringValue();
				if (spath != null)
					savefile = spath;
			}
			savepath.setText(savefile);
			if (!savefile.startsWith("/"))
				savefile = MyCert_gui.certificatepath + savefile;
			try {
				serializetoXML(doc, savefile);
				System.out.println(" file saved" + savefile);
			} catch (Exception e2) {
				System.out.println(" file problem " + e2);
			}
		}
		if (command.equals("load")) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(MyCert_gui.certificatepath));
			fc.setAcceptAllFileFilterUsed(true);
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String fp = "";
				try {
					fp = file.getCanonicalPath();
				} catch (Exception e4) {
					return;
				}
				if (fp == null || fp == "")
					return;
				String filename = file.getName();
				filepath = fp;

				MyCert_gui.messagepanel.display(" opening " + filepath);
				if (filename.endsWith(".xml")) {
					currentdocument = loadDocument(filepath);
					informationsource newsource = new informationsource();
					Boolean successful = newsource.loadXMLDoc(currentdocument);
					if (successful) {
						if (newsource.message != null) {
							JOptionPane.showMessageDialog(
									MyCert_gui.browserpanel, "Loading from "
											+ filename + "\n"
											+ newsource.message,
									"Loading Document ",
									JOptionPane.WARNING_MESSAGE);
						}
						savepath.setText(filepath);
						currenttemplate = newsource.getTemplate();
						// fillTemplate(currenttemplate, currentdocument);
						displaycert(currenttemplate, currentdocument);
					} else {
						savepath.setText("");
						System.out.println(newsource.message);
						JOptionPane.showMessageDialog(MyCert_gui.browserpanel,
								"Loading from " + filename + "\n"
										+ newsource.message,
								"Loading Document ",
								JOptionPane.WARNING_MESSAGE);
					}

				}
			}
		}
		if (command.equals("selected")) {
			System.out.println(" selected ");
		}
		if (command.equals("tempload")) {
			MyCert_gui.selectCard("Template Loader");
		}
		if (command.equals("user")) {
			MyCert_gui.selectCard("User");
		}
		if (command.equals("message")) {
			MyCert_gui.selectCard("Messages");
		}
		if (command.equals("refreshlist")) {
			reloadAll();
		}
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
		if (certificate != null) {
			Dimension dc = certificate.getSize();
			Dimension cd = cert.getSize();
			((VerticalPanel) certificate).updateSize();
		}

	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		if (certificate != null) {
			Dimension dc = certificate.getSize();
			Dimension cd = cert.getSize();
			((VerticalPanel) certificate).updateSize();}
		repaint();
		setMenuVisible(true);
	}

	public Document createDocument(String newsavepath)
	{
		this.updateDoc(currentdocument, newsavepath);
		return currentdocument;
	}

	private void displaycert(documentTemplate acert, Document adoc)
	{
		heading.removeAll();
		heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
		JLabel name = new JLabel(acert.getName());
		name.setFont(MyCert_gui.headingfont);
		name.setAlignmentX(CENTER_ALIGNMENT);
		heading.add(name);
		heading.setBorder(utils.setborder("blue", 2));
		cert.removeAll();
		if (acert.getBackgroundcolor() != null) {
			cert.setBackground(utils.color(acert.getBackgroundcolor()));
			cert.setOpaque(true);
		} else
			cert.setOpaque(false);
		cert.setLayout(new BoxLayout(cert, BoxLayout.Y_AXIS));
		certificate = (VerticalPanel) acert.getPanelObject(adoc);
		if (certificate != null) {
			certificate.addComponentListener(this);
			cert.add(certificate);
			Dimension minSize = new Dimension(5, 100);
			Dimension prefSize = new Dimension(5, 100);
			Dimension maxSize = new Dimension(1200, Short.MAX_VALUE);
			;
			cert.setPreferredSize(maxSize);
			certificate.setPreferredSize(maxSize);
		}
		footnote.removeAll();
		footnote.setLayout(new BoxLayout(footnote, BoxLayout.X_AXIS));
		JLabel author = new JLabel(acert.getAuthor());
		author.setFont(MyCert_gui.footnotefont);
		footnote.add(author);
		footnote.add(Box.createHorizontalGlue());
		JLabel templateid = new JLabel(acert.getTemplateID());
		templateid.setFont(MyCert_gui.footnotefont);
		footnote.add(templateid);
		footnote.add(Box.createHorizontalGlue());
		JLabel editdate = new JLabel(acert.getEditdate());
		editdate.setFont(MyCert_gui.footnotefont);
		footnote.add(editdate);
		footnote.setBorder(utils.setborder("blue", 2));
		cert.setPreferredSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		cert.setVisible(true);
		cert.validate();
		certframe.validate();
		
		if (certificate != null) {
			Dimension dc = certificate.getSize();
			Dimension cd = cert.getSize();
			((VerticalPanel) certificate).updateSize();
		}
		repaint();
	}

	public documentTemplate findCertificate(String targetid)
	{
		for (documentTemplate cert : MyCert_gui.templates) {
			String certid = cert.getTemplateID();
			if (certid != null && cert.getTemplateID().equals(targetid))
				return cert;
		}
		return null;
	}

	public JMenu getFileMenu()
	{
		newfilemenu = new PMenu("File");
		PMenuItem newitem = new PMenuItem("User", "user", this);
		newfilemenu.add(newitem);
		PMenuItem newitem2 = new PMenuItem("Template Loader", "tempload", this);
		newfilemenu.add(newitem2);
		PMenuItem newitem3 = new PMenuItem("Messages", "message", this);
		newfilemenu.add(newitem3);
		return newfilemenu;
	}

	public PMenuItem getLoadMenu()
	{
		loadmenu = new PMenuItem("Load Certificate");
		loadmenu.addActionListener(this);
		loadmenu.setActionCommand("load");
		return loadmenu;
	}

	public JMenu getSaveMenu()
	{
		savemenu = new PMenu("Save Certificate");
		PMenuItem newitem = new PMenuItem("Save As", "saveas", this);
		savemenu.add(newitem);
		PMenuItem newitem2 = new PMenuItem("Save Auto", "saveauto", this);
		savemenu.add(newitem2);
		PMenuItem newitem3 = new PMenuItem("Save", "save", this);
		savemenu.add(newitem3);
		return savemenu;
	}

	public JMenu getTemplateMenu()
	{
		newcertificatemenu = new PMenu("New Certificate");
		makeTemplateMenu();
		return newcertificatemenu;
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		// TODO Auto-generated method stub
	}

	public Document loadDocument(String filename)
	{
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(filename));
			return document;
		} catch (DocumentException e) {
			MyCert_gui.messagepanel.error(" failed opening " + filename + " "
					+ e);
		}
		return null;
	}

	public void makeTemplateMenu()
	{
		newcertificatemenu.removeAll();
		for (documentTemplate cert : MyCert_gui.templates) {
			PMenuItem newitem = new PMenuItem(cert.getName(), "LOAD:"
					+ cert.getTemplateID(), this);
			newcertificatemenu.add(newitem);
		}
		PMenuItem newitem = new PMenuItem("Refresh List", "refreshlist", this);
		newcertificatemenu.add(newitem);
		newcertificatemenu.validate();
		cert.validate();
		certframe.validate();
		repaint();
		repaint();
	}

	public void reloadAll()
	{
		utils.loadAllTemplates();
		makeTemplateMenu();
	}

	public void serializetoXML(Document doc, String filepath) throws Exception
	{
		OutputStream out = new FileOutputStream(filepath);
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(out, outformat);
		writer.write(doc);
		writer.flush();
	}

	public void setMenu(JMenuBar mainmenu)
	{
		mainmenu.add(getFileMenu());
		mainmenu.add(getTemplateMenu());
		mainmenu.add(getSaveMenu());
		mainmenu.add(savepath);
		mainmenu.add(getLoadMenu());
	}

	public void setMenuVisible(boolean vis)
	{
		if (newfilemenu != null) {
			newfilemenu.setVisible(vis);
			savepath.setVisible(vis);
			newcertificatemenu.setVisible(vis);
			savemenu.setVisible(vis);
			loadmenu.setVisible(vis);
		}
	}

	public void updateDoc(Document doc, String newsavepath)
	{
		Element root = doc.getRootElement();
		Node certele =  doc.selectSingleNode("//certificate");
		List<Node> editionnodes = doc.selectNodes("//edition");
		Node editiondetails=null;
		int enodecount = editionnodes.size();
		if(enodecount==0)
		{
			editiondetails = root.addElement("edition");
		}
		else 
			{
			editiondetails = editionnodes.get(0);
			List<Node>  esubnodes = editiondetails.selectNodes("*");
			for(Node dn:esubnodes)
			{
				dn.getParent().remove(dn);
			}
			if(enodecount>1)
			{
				for (int n=1;n<enodecount;n++)
			    {
					Node dn = editionnodes.get(n);
					dn.getParent().remove(dn);
				}
			}
			}
		
		utils.addElement(editiondetails, "savepath", newsavepath);
		utils.addElement(editiondetails, "editor", MyCert_gui.username);
		utils.addElement(editiondetails, "editdate", utils.getTodaysDate());
		List<Node> templatenodes = doc.selectNodes("//template");
		for (Node nd : templatenodes) {
			nd.getParent().remove(nd);
		}
		currenttemplate.toDOM4JDoc(root);
	}
	
	public Document styleDocument( Document document, String xslt )
	   {

	      // create an instance of TransformerFactory
	      TransformerFactory transformerFactory = TransformerFactory
	            .newInstance();
	      // initialize Transformer
	      Transformer transformer = null;
	      try
	      {
	         transformer = transformerFactory.newTransformer( new StreamSource(
	               xslt ) );
	      }
	      catch (TransformerConfigurationException e)
	      {
	         e.printStackTrace();
	      }
	      Document finalDoc = null;
	      if (null != transformer)
	      {
	         DocumentSource source = new DocumentSource( document );
	         DocumentResult result = new DocumentResult();
	         // transform the source to the document result
	         try
	         {
	            transformer.transform( source, result );
	         }
	         catch (TransformerException e)
	         {

	            e.printStackTrace();
	         }

	         // return the transformed document
	         finalDoc = result.getDocument();
	      }
	      return finalDoc;
	   }

}
