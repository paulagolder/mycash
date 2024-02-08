package org.lerot.MyCert;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

public class informationsource
{

	//Vector<appearance> appearances;
	timeperiod date;
	location place;
	String message;
	String savepath;
	String editor;
	String editdate;
	String templateid, templateauthor, templateeditdate, templatename,
			templateperiod, templatecountry;

	documentTemplate currenttemplate;

	public Boolean loadXMLDoc(Document currentdocument)
	{
		message = null;
		if (currentdocument != null)
		{
			loadTemplateDetails(currentdocument);
			loadAuthor(currentdocument);
			if (templateid == null)
			{
				Element atemplateid = (Element) currentdocument
						.selectSingleNode("/*/templateID");
				if (atemplateid != null)
					templateid = atemplateid.getText();
			}

			if (templateid != null)
			{
				currenttemplate = documentTemplate.findTemplate(templateid);
				if (currenttemplate != null)
				{
					return true;
				} else
				{
					addMessage(" notfound template " + templateid);
					return false;
				}
			} else
			{
				addMessage(" notfound a templateID ");
				return false;
			}
		} else
		{
			addMessage(" No document loaded");
			return false;
		}
	}

	public documentTemplate getTemplate()
	{
		return documentTemplate.findTemplate(currenttemplate.getTemplateID());
	}

	public void loadAuthor(Document rootdoc)
	{
		Element author = (Element) rootdoc.selectSingleNode("/*/edition");
		if (author != null)
		{
			List<Element> elist = author.elements();
			for (Element element2 : elist)
			{
				String ename = element2.getName();
				if (ename.equals("savepath"))
				{
					savepath = element2.getText();
				}
				if (ename.equals("editor"))
				{
					editor = element2.getText();
				}
				if (ename.equals("editdate"))
				{
					editdate = element2.getText();
				}
			}
		} else
		{
			addMessage("\"edition\" not found ");
		}

	}

	public void loadTemplateDetails(Document rootdoc)
	{
		// Element templateid = (Element) currentdocument
		// .selectSingleNode("/*/template/templateID");
		Element template = (Element) rootdoc.selectSingleNode("/*/template");
		if (template != null)
		{
			List<Element> elist = template.elements();
			for (Element element2 : elist)
			{
				String ename = element2.getName();
				if (ename.equals("author"))
				{
					templateauthor = element2.getText();
				}
				if (ename.equals("editdate"))
				{
					templateeditdate = element2.getText();
				}
				if (ename.equals("templateID"))
				{
					templateid = element2.getText();
				}
				if (ename.equals("name"))
				{
					templatename = element2.getText();
				}
				if (ename.equals("period"))
				{
					templateperiod = element2.getText();
				}
				if (ename.equals("country"))
				{
					templatecountry = element2.getText();
				}
			}
		} else
		{
			addMessage("\"Template\" not found ");
		}

	}

	private void addMessage(String newmess)
	{
		if (message == null)
			message = newmess;
		else
			message += "\n" + newmess;
	}

}
