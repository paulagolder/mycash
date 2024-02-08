package org.lerot.MyCash;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.dom4j.Element;

public class utils
{

	static Connection conn = null;

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

	public static String getTodaysDate()
	{
		String DATE_FORMAT = "yyyy-MM-dd";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		Date d1 = new Date();
		return sdf.format(d1);
	}

	public static Connection myconnect()
	{
		Connection conn = null;
		try
		{
			String dburl = MyCash_gui.dburl;
			String dbuser = MyCash_gui.dbuser;
			String dbpassword = MyCash_gui.dbpassword;
			conn = DriverManager.getConnection(dburl, dbuser,dbpassword);
		} catch (SQLException ex)
		{
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

	public static void connectsqlite()
	{
		String connectstring = "";

		Connection con;
		try
		{
			Class.forName("org.sqlite.JDBC");

			connectstring = "jdbc:sqlite:" + "path";
			con = DriverManager.getConnection(connectstring);
			if (con != null)
			{

				System.out.println("opened :   dblite ");
			} else
				System.out.println("Cannot connect to " + connectstring);
		} catch (Exception e)
		{
			String errorMessage = e.getClass().getName() + ": " + e.getMessage();
			System.out.println("Cannot connect to " + connectstring);
			con = null;
		}

	}

	public static Border setborder(String bordercolor, int borderWidth)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static int fontstyle(String fontstyle)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public static Border setborder()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static Border setborder(Color gray, int borderWidth)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static String getDateElement(Element element2, String string)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static int Integer(String text)
	{
		java.lang.Integer number = Integer.valueOf(text);
		return number;
	}

	public static void ShowFonts()
	{

		Font[] fonts;
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for (int i = 0; i < fonts.length; i++)
		{
			System.out.print(fonts[i].getFontName() + " : ");
			System.out.print(fonts[i].getFamily() + " : ");
			System.out.print(fonts[i].getName());
			System.out.println();
		}

	}
	
	  public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
	        list.sort(Entry.comparingByValue());

	        Map<K, V> result = new LinkedHashMap<>();
	        for (Entry<K, V> entry : list) {
	            result.put(entry.getKey(), entry.getValue());
	        }

	        return result;
	    }

	public static String rightpad(int k, String vf)
	{
		String rp = "           "+vf;
		return rp.substring(rp.length()-k);
	}

	public static String leftpad(int k, String text)
	{
		String rp = text+ String.format("%" + k + "c", ' ');
		return rp.substring(0,k);
	}
	  

	public static String replacecommas(String text)
	{
		String rtext = text.replace(", ","_");
		return rtext.replace(",","_");
		
	}
	  
	  
	  
}
