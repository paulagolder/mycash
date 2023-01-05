package org.lerot.MyCert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.lerot.MyCert.layout.templatebody;

public class documentTemplate
{

	public static documentTemplate findTemplate(String templateid)
	{
		for (documentTemplate template : MyCert_gui.templates) {
			if (template.getTemplateID().startsWith(templateid)) {
				return template;
			}
		}
		return null;
	}

	static public List<documentTemplate> getTemplates(String filename)
	{
		ArrayList<documentTemplate> templateList = new ArrayList<documentTemplate>();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(filename));
			List<?> ln = document
					.selectNodes("//sourcedocuments/sourcedocument");
			List<Node> nodelist = (List<Node>) ln;
			for (Node node : nodelist) {
				Element foo = (Element) node;
				documentTemplate newtemplate = new documentTemplate(foo);
				templateList.add(newtemplate);
			}

		} catch (DocumentException e) {
			String message = " failed opening " + filename + e;
			System.out.println(message);
			message = message.replaceAll("(?:xml)+", "xml \n ");
			message = message.replaceAll("(?:Nested exception)+",
					"\n Nested exception \n ");
			message = message.replaceAll("(?:Error)+", "\n Error ");
			message = message.replaceAll(
					"(?:org\\.dom4j\\.DocumentException:)+", " ");

			JOptionPane.showMessageDialog(MyCert_gui.browserpanel, message,
					"Loading Templates", JOptionPane.WARNING_MESSAGE);

		}
		return templateList;
	}

	static public List<documentTemplate> getTemplatesfromstring(String xmlstring)
	{
		ArrayList<documentTemplate> certificatelist = new ArrayList<documentTemplate>();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(xmlstring);
			List<?> ln = document
					.selectNodes("//sourcedocumentlist/sourcedocuments/sourcedocument");
			List<Node> nodelist = (List<Node>) ln;
			for (Node node : nodelist) {
				Element foo = (Element) node;
				documentTemplate newcertificate = new documentTemplate(foo);
				certificatelist.add(newcertificate);
			}

		} catch (DocumentException e) {
			String message = " failed opening " + xmlstring + e;
			System.out.println(message);
			message = message.replaceAll("(?:xml)+", "xml \n ");
			message = message.replaceAll("(?:Nested exception)+",
					"\n Nested exception \n ");
			message = message.replaceAll("(?:Error)+", "\n Error ");
			message = message.replaceAll(
					"(?:org\\.dom4j\\.DocumentException:)+", " ");
			JOptionPane.showMessageDialog(MyCert_gui.browserpanel, message,
					"Loading Templates", JOptionPane.WARNING_MESSAGE);
		}
		return certificatelist;
	}

	private String aame;
	private String backgroundcolor;
	private templatebody template;
	HashMap<String, String> templateAttributes;
	private String country;
	private String documenttype;
	private String heading;
	private String period;
	private String savefilename;
	private String stylesheet;
	private String templateauthor;
	private String templateeditdate;
	private String templateID;
	private String type;
	private String source;

	public documentTemplate(Element element)
	{
		templateAttributes = new HashMap<String, String>();
		backgroundcolor = null;
		template = null;
	
		List<Element> list = element.elements();
		for (Element element2 : list) {
			String ename = element2.getName().toLowerCase();
		
			if (ename.equals("template"))
				loadTemplate(element2);		
			else if (ename.equals("certificate")) {
				template = new templatebody(element2);
			} else {
				if (!ename.equals("sourcedocument")) {
					templateAttributes.put(ename, element2.getText());
					System.out.println("    loading template attribute "
							+ ename + "=" + element2.getText());
				}
			}
		}
	}

	public void loadTemplate(Element element)
	{
		List<Element> list = element.elements();
		for (Element element2 : list) {
			String ename = element2.getName().toLowerCase();		
		  if (ename.equals("stylesheet"))
			setStylesheet(element2.getText());
		else if (ename.equals("heading"))
			setHeading(element2.getText());
		else if (ename.equals("name"))
			setName(element2.getText());
		else if (ename.equals("type"))
			setType(element2.getText());
		else if (ename.equals("country"))
			setCountry(element2.getText());
		else if (ename.equals("period"))
			setPeriod(element2.getText());
		else if (ename.equals("author"))
			setAuthor(element2.getText());
		else if (ename.equals("savefilename"))
			setSavefilename(element2.getText());
		else if (ename.equals("editdate"))
			setEditdate(element2.getText());
		else if (ename.equals("templateid"))
			setTemplateID(element2.getText());
		else if (ename.equals("source"))
			setSource(element2.getText());
		}
		System.out.println(" loaded: " + getName());
	}
	
	public String getAuthor()
	{
		return templateauthor;
	}

	public String getBackgroundcolor()
	{
		return backgroundcolor;
	}

	String getCountry()
	{
		return country;
	}

	public String getDocumenttype()
	{
		return documenttype;
	}

	public String getEditdate()
	{
		return templateeditdate;
	}

	String getHeading()
	{
		return heading;
	}

	public String getName()
	{
		return getAame();
	}

	public JComponent getPanelObject(Document adoc)
	{
		if (template == null) {
			System.out.println(" Problem with certificate ");
			return null;
		}
		JComponent apanel = template.getPanelObject(adoc);
		// apanel.setBorder(utils.setborder("blue", 4));
		apanel.setName(adoc.getName());
		if (MyCert_gui.showborders)
			apanel.setBorder(utils.setborder("blue", 1));
		return apanel;
	}
	
	public void getCertificate(Element adoc)
	{
		if (adoc == null) {
			System.out.println(" Problem with certificate ");
			return;
		}
		template.getCertificate(adoc);
	}

	String getPeriod()
	{
		return period;
	}

	public String getSavefilename()
	{
		if (savefilename == null) {
			savefilename = "certificate.xml";
		}
		if (!savefilename.endsWith(".xml"))
			savefilename += ".xml";
		return savefilename;
	}

	public String getSource()
	{
		return source;
	}

	public String getStylesheet()
	{
		return stylesheet;
	}

	public String getTemplateID()
	{
		return templateID;
	}

	String getType()
	{
		return type;
	}

	public void setAuthor(String author)
	{
		templateauthor = author;
	}

	/*
	 * private void setBackgroundColor(String text) { setBackgroundcolor(text);
	 * }
	 */

	public void setBackgroundcolor(String backgroundcolor)
	{
		this.backgroundcolor = backgroundcolor;
	}

	void setCountry(String acountry)
	{
		country = acountry;
	}

	public void setDocumenttype(String documenttype)
	{
		this.documenttype = documenttype;
	}

	public void setEditdate(String editdate)
	{
		this.templateeditdate = editdate;
	}

	void setHeading(String aheading)
	{
		heading = aheading;
	}

	public void setName(String aname)
	{
		setAame(aname);
	}

	void setPeriod(String period)
	{
		this.period = period;
	}

	public void setSavefilename(String savefilename)
	{
		this.savefilename = savefilename;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public void setStylesheet(String stylesheet)
	{
		this.stylesheet = stylesheet;
	}

	private void setTemplateID(String text)
	{
		templateID = text;
	}

	void setType(String type)
	{
		this.type = type;
	}

	public void toDOM4JDoc(Element root)
	{
		Element template = root.addElement("template");
		utils.addElement(template, "author", getAuthor());
		utils.addElement(template, "editdate", getEditdate());
		utils.addElement(template, "templateID", getTemplateID());
		utils.addElement(template, "name", getName());
		utils.addElement(template, "period", getPeriod());
		utils.addElement(template, "country", getCountry());
	}

	
	public Document makeNewDocument()
	{	
		 Document document = DocumentHelper.createDocument();
		 String stylesheet = getStylesheet();
		 document.addProcessingInstruction("xml-stylesheet ", "type='text/xsl' href='"+stylesheet+"'"); 
	     Element root = document.addElement( "sourcedocument" );
         toDOM4JDoc(root);
         Element edition= root.addElement( "edition" );
         Element template= root.addElement( "template" );
         Element certificate= root.addElement( "certificate" );
         getCertificate(certificate);
         return document;		
	}
	
	@Override
	public String toString()
	{
		return getAame();
	}

	public String getAame() {
		return aame;
	}

	public void setAame(String aame) {
		this.aame = aame;
	}

}
