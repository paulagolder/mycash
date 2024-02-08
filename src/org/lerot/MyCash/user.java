package org.lerot.MyCert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class user
{

	private String name;
	private String email;
	private String ftphost;
	private String ftpusername;
	private String ftppassword;
	private String ftpdirectory;
	String message;

	public user(String aname)
	{
		name = aname;
	}

	public void loadUser(String filename)
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(filename));
			Element root = document.getRootElement();
			if (root != null)
			{
				List<Element> elist = root.elements();
				for (Element element2 : elist)
				{

					String ename = element2.getName();
					if (ename.equals("name"))
					{
						name = element2.getText();
					}
					if (ename.equals("email"))
					{
						email = element2.getText();
					}
					if (ename.equals("ftphost"))
					{
						setFtphost(element2.getText());
					}
					if (ename.equals("ftpusername"))
					{
						setFtpusername(element2.getText());
					}
					if (ename.equals("ftppassword"))
					{
						setFtppassword(element2.getText());
					}
					if (ename.equals("ftpdirectory"))
					{
						setFtpdirectory(element2.getText());
					}
				}
			}

		} catch (DocumentException e)
		{
			message = " failed opening " + filename + e;
			name = null;
		}
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public void saveUser()
	{

		String savefile = MyCert_gui.certificatepath + "user.xml";
		;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("user");
		utils.addElement(root, "name", name);
		utils.addElement(root, "email", email);
		utils.addElement(root, "ftphost", getFtphost());
		utils.addElement(root, "ftpusername", getFtpusername());
		utils.addElement(root, "ftppassword", getFtppassword());
		utils.addElement(root, "ftpdirectory", getFtpdirectory());
		OutputStream out;
		try
		{
			out = new FileOutputStream(savefile);
			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(out, outformat);
			writer.write(document);
			writer.flush();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	String getFtphost()
	{
		return ftphost;
	}

	void setFtphost(String ftphost)
	{
		this.ftphost = ftphost;
	}

	String getFtpusername()
	{
		return ftpusername;
	}

	void setFtpusername(String ftpusername)
	{
		this.ftpusername = ftpusername;
	}

	String getFtppassword()
	{
		return ftppassword;
	}

	void setFtppassword(String ftppassword)
	{
		this.ftppassword = ftppassword;
	}

	String getFtpdirectory()
	{
		return ftpdirectory;
	}

	void setFtpdirectory(String ftpdirectory)
	{
		this.ftpdirectory = ftpdirectory;
	}
}
