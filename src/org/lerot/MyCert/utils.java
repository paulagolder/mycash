package org.lerot.MyCert;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.dom4j.Branch;
import org.dom4j.Element;
import org.dom4j.Node;

public class utils
{

	public static void setJTextPaneFont(JTextPane jtp, Font font, Color c)
	{
		MutableAttributeSet attrs = jtp.getInputAttributes();
		StyleConstants.setFontFamily(attrs, font.getFamily());
		StyleConstants.setFontSize(attrs, font.getSize());
		StyleConstants.setItalic(attrs, (font.getStyle() & Font.ITALIC) != 0);
		StyleConstants.setBold(attrs, (font.getStyle() & Font.BOLD) != 0);
		StyleConstants.setForeground(attrs, c);
		StyledDocument doc = jtp.getStyledDocument();

		// Replace the style for the entire document. We exceed the length
		// of the document by 1 so that text entered at the end of the
		// document uses the attributes.
		doc.setCharacterAttributes(0, doc.getLength() + 1, attrs, false);
	}

	public static Element addElement(Element root, String name, String text)
	{
		if (text == null)
			return null;
		Element el = root.addElement(name);
		el.addText(text);
		return el;
	}

	public static Element addElement(Node rootnode, String name, String text)
	{
		if (text == null)
			return null;
		Element el = ((Branch) rootnode).addElement(name);
		el.addText(text);
		return el;

	}

	public static Color color(String colorname)
	{
		if (colorname == null || colorname.length() < 1)
			return Color.black;
		if (colorname.startsWith("#"))
			return Color.decode(colorname);
		String bc = colorname.toLowerCase();
		if (bc.startsWith("bla"))
			return Color.black;
		if (bc.startsWith("blu"))
			return Color.blue;
		if (bc.startsWith("gra"))
			return Color.gray;
		if (bc.startsWith("gre"))
			return Color.green;
		if (bc.startsWith("ye"))
			return Color.yellow;
		if (bc.startsWith("wh"))
			return Color.white;
		if (bc.startsWith("re"))
			return Color.red;
		if (bc.startsWith("pi"))
			return Color.pink;
		if (bc.startsWith("si"))
			return Color.lightGray;
		else
			return Color.black;
	}

	public static String fetch(String address) throws MalformedURLException,
			IOException
	{
		URL url = new URL(address);
		return (String) url.getContent();
	}

	public static int fontstyle(String style)
	{
		if (style == null)
			return Font.PLAIN;
		int istyle = 0;
		String astyle = " " + style.toLowerCase();
		if (astyle.indexOf("bold") > 0)
			istyle += Font.BOLD;
		if (astyle.indexOf("italic") > 0)
			istyle += Font.ITALIC;
		return istyle;
	}

	public static String getTodaysDate()
	{
		String DATE_FORMAT = "yyyy-MM-dd";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				DATE_FORMAT);
		Date d1 = new Date();
		return sdf.format(d1);
	}

	public static void GetURLInfo(String urlstr) throws IOException
	{
		URL url = new URL(urlstr);
		URLConnection u = url.openConnection();
		// Display the URL address, and information about it.
		System.out.println(u.getURL().toExternalForm() + ":");
		System.out.println("  Content Type: " + u.getContentType());
		System.out.println("  Content Length: " + u.getContentLength());
		System.out.println("  Last Modified: " + new Date(u.getLastModified()));
		System.out.println("  Expiration: " + u.getExpiration());
		System.out.println("  Content Encoding: " + u.getContentEncoding());
		System.out.println("First five lines:");
		BufferedReader d = new BufferedReader(new InputStreamReader(
				u.getInputStream()));
		for (int i = 0; i < 10; i++) {
			String line = d.readLine();
			if (line == null)
				break;
			System.out.println("  " + line);
		}

	}

	public static void loadAllTemplates()
	{
		String ftphost = MyCert_gui.currentuser.getFtphost();
		String ftpuser = MyCert_gui.currentuser.getFtpusername();
		String ftpdirectory = MyCert_gui.currentuser.getFtpdirectory();
		String ftppassword = MyCert_gui.currentuser.getFtppassword();
		
		MyCert_gui.templates.clear();
		MyCert_gui.selectCard("Messages");
		//loadcollectionfromFTP("ftp.lerot.org", "jcert@lerot.org", "/Templates");
		loadcollectionfromFTP(ftphost, ftpuser, ftpdirectory, ftppassword);
		loadcollectionfromDev();
	}

	static void loadtemplate(File file)
	{
		String filename = file.getName();
		String fp = "";
		try {
			fp = file.getCanonicalPath();
		} catch (Exception e4) {
			return;
		}
		if (fp == null || fp == "")
			return;
		if (filename.endsWith(".xml")) {
			// try
			{
				// System.out.println(" loading template "+ filename );
				List<documentTemplate> certlist = documentTemplate
						.getTemplates(fp);
				for (documentTemplate acertificate : certlist) {
					MyCert_gui.templates.add(acertificate);
				}
			} // catch (Exception e3)
				// {
				// System.out.println("loaded error " + filename+e3);
				// }

		}
	}

	public static void loadcollectionfromFTP(String host, String user,
			String directory, String password)
	{
		if(host == null) return;
		MyCert_gui.messagepanel.display("Connecting to ftp");
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host);
			ftp.login(user, password);
			MyCert_gui.messagepanel.display("Connected to " + host + " " + user
					+ " " + directory);
			MyCert_gui.messagepanel.display(ftp.getReplyString());
			if (ftp == null)
				return;
			try {
				Thread.sleep(1000);
			} catch (Throwable t) {
				throw new OutOfMemoryError("An Error has occured");
			}
			ftp.enterLocalPassiveMode();
			ftp.cwd(directory);
			MyCert_gui.messagepanel.display(ftp.getReplyString());
			FTPFile[] files = ftp.listFiles();
			for (int i = 0; i < files.length; i++) {
				FTPFile sfile = files[i];
				String sname = sfile.getName();
				if (sname.endsWith(".xml")) {
					MyCert_gui.messagepanel.display("loading " + sname
							+ " from " + MyCert_gui.temppath + "temp_" + sname);
					String ffile = sname;
					FileOutputStream out = new FileOutputStream(
							MyCert_gui.temppath + "temp_" + sname);
					boolean result = ftp.retrieveFile(ffile, out);
					out.close();
					if (!result) {
						MyCert_gui.messagepanel.display("loaded error in "
								+ sname);
					} else
						utils.loadtemplate(new File(MyCert_gui.temppath
								+ "temp_" + sname));
				}
			}
			ftp.logout();
			ftp.disconnect();
		} catch (SocketException e) {
			MyCert_gui.messagepanel.display("error Connecting to ftp" + e);
		} catch (IOException e) {
			MyCert_gui.messagepanel.display("error2 Connecting to ftp" + e);
		}

	}

	public static void loadcollectionfromDev()
	{
		MyCert_gui.messagepanel.display("Connecting to dev..");
		String devpath = MyCert_gui.certificatepath + "Dev";
		File dev = new File(devpath);

		if (!dev.exists())
			return;
		if (!dev.isDirectory())
			return;
		// Loop thru files and directories in this path
		String[] files = dev.list();
		for (int i = 0; i < files.length; i++) {
			String sname = files[i];

			if (sname.endsWith(".xml")) {
				File f2 = new File(devpath, sname);
				if (f2.isFile()) {
					utils.loadtemplate(new File(devpath, sname));
					MyCert_gui.messagepanel.display("loading " + sname
							+ " from dev ");
				}
			}
		}

	}

	private static String readFileAsString(String filePath)
			throws java.io.IOException
	{
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	public static Border setborder()
	{
		return setborder(Color.black, 1);
	}

	public static Border setborder(Color bc, int width)
	{
		return BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(bc, width),
				BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	public static Border setborder(String bordercolor, int border)
	{
		return setborder(color(bordercolor), border);
	}

	public static Border setcborder(String label)
	{
		return BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(label),
				BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	public static String printd(Dimension d)
	{
		return "(" + d.width + "," + d.height + ")";
	}

	public static void sleep(int secs)
	{
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException ie) {
			// System.out.println(ie.getMessage());
		}

	}

}
