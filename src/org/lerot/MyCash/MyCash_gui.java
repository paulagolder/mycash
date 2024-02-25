package org.lerot.MyCash;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.UIManager;

import org.lerot.mywidgets.jswStyle;
import org.lerot.mywidgets.jswStyles;

public class MyCash_gui extends JFrame
{
	static Color backgroundcolor;
	public static JTabbedPane browserpanel;
	public static mainpanel accountpanel;
	public static String accountspath, temppath;

	public static String defaultfontname = "Helvetica";
	public static Font guiMenufont, footnotefont, defaultfont, guiTextFieldfont, headingfont;
	public static JMenuBar mainmenu;
	public static String mce_userpath;
	public static String os;
	private static final long serialVersionUID = 1L;
	public static boolean showborders = false;
	public static String storepath;

	public static gc_transactions MyTransactions;
	public static gc_accounts MyAccounts;
	public static gc_account topaccount;

	private static final double version = 2.1;
	static ListModel mybudgetaccounts;

	static JPanel cards;

	private static jswStyles tablestyles;
	private static jswStyles panelstyles;;
	static String budgetfile;
	public static my_budgets mybudgets;

	public static String mycashexport;
	public static String dburl;
	public static String dbuser;
	public static String dbpassword;
	static JTextArea outputpanel;
	// public static JTextArea outputarea;
	public static JTextArea outputarea;

	public static void initiateStyles()
	{
		tablestyles = jswStyles.getDefaultStyles();
		tablestyles.name = "table";
		jswStyle tablestyle = tablestyles.makeStyle("table");
		tablestyle.putAttribute("background", "red");
		jswStyle rowstyle = tablestyles.makeStyle("row");
		rowstyle.putAttribute("height", "10");
		jswStyle col0style = tablestyles.makeStyle("col_0");
		col0style.putAttribute("fontStyle", Font.BOLD);
		col0style.setHorizontalAlign("RIGHT");
		col0style.putAttribute("minwidth", "true");
		jswStyle col1style = tablestyles.makeStyle("col_1");
		col1style.putAttribute("fontStyle", Font.BOLD);
		col1style.setHorizontalAlign("RIGHT");
		jswStyle col2style = tablestyles.makeStyle("col_2");
		col2style.putAttribute("horizontalAlignment", "RIGHT");
		col2style.putAttribute("minwidth", "true");

		panelstyles = jswStyles.getDefaultStyles();
		panelstyles.name = "panel";
		jswStyle jswWidgetStyles = panelstyles.makeStyle("jswWidget");
		jswWidgetStyles.putAttribute("backgroundColor", "#e0dcdf");
		jswWidgetStyles.putAttribute("boxbackgroundColor", "GREEN");
		jswWidgetStyles.putAttribute("foregroundColor", "Black");
		jswWidgetStyles.putAttribute("borderWidth", "0");
		jswWidgetStyles.putAttribute("fontsize", "14");
		jswWidgetStyles.putAttribute("borderColor", "blue");

		jswStyle jswLabelStyles = panelstyles.makeStyle("jswLabel");
		jswStyle largelabelStyle = panelstyles.makeStyle("largeLabel");
		largelabelStyle.putAttribute("fontsize", "30");
		largelabelStyle.putAttribute("foregroundColor", "Red");

		jswStyle jswButtonStyles = panelstyles.makeStyle("jswButton");
		jswButtonStyles.putAttribute("fontsize", "10");

		jswStyle jswToggleButtonStyles = panelstyles.makeStyle("jswToggleButton");
		jswToggleButtonStyles.putAttribute("foregroundColor", "Red");

		jswStyle jswTextBoxStyles = panelstyles.makeStyle("jswTextBox");

		jswStyle jswTextFieldStyles = panelstyles.makeStyle("jswTextField");
		// jswTextFieldStyles.putAttribute("backgroundColor", "#e0dcdf");

		jswStyle jswDropDownBoxStyles = panelstyles.makeStyle("jswDropDownBox");
		// jswDropDownBoxStyles.putAttribute("backgroundColor","#C0C0C0");

		jswStyle jswhpStyles = panelstyles.makeStyle("jswContainer");
		jswhpStyles.putAttribute("backgroundColor", "#C0C0C0");

		jswStyle jswDropDownContactBoxStyles = panelstyles.makeStyle("jswDropDownContactBox");
		jswDropDownContactBoxStyles.putAttribute("backgroundColor", "#C0C0C0");
		jswDropDownContactBoxStyles.putAttribute("fontsize", "10");

		jswStyle jswScrollPaneStyles = panelstyles.makeStyle("jswScrollPaneStyles");
		jswScrollPaneStyles.putAttribute("backgroundColor", "#C0C0C0");
		jswScrollPaneStyles.putAttribute("fontsize", "10");

		jswStyle jswBorderStyle = panelstyles.makeStyle("borderstyle");
		jswBorderStyle.putAttribute("borderWidth", "1");
		// jswBorderStyle.putAttribute("borderColor", "#C0C0C0");
		jswBorderStyle.putAttribute("borderColor", "black");

		jswStyle hpanelStyle = panelstyles.makeStyle("hpanelstyle");
		hpanelStyle.putAttribute("borderWidth", "2");
		hpanelStyle.putAttribute("borderColor", "blue");
		hpanelStyle.putAttribute("height", "100");

		jswStyle pbStyle = panelstyles.makeStyle("jswPushButton");
		pbStyle.putAttribute("backgroundColor", "#C0C0C0");
		pbStyle.putAttribute("fontsize", "10");

		pbStyle.putAttribute("foregroundColor", "black");
		jswStyle greenfont = panelstyles.makeStyle("greenfont");
		greenfont.putAttribute("foregroundColor", "green");

	}

	public static void main(String[] args)
	{
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo look : looks)
		{
			// System.out.println(look.getClassName());
		}
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		MyCash_gui mframe = new MyCash_gui(200, 200, d.width - 400, d.height - 400);

		mframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		mframe.getContentPane().setLayout(new BoxLayout(mframe.getContentPane(), BoxLayout.Y_AXIS));
		mframe.pack();
		mframe.setVisible(true);
	}

	public String osversion;
	JSplitPane splitPane = null;
	public int treefont, listfont, messagefont, tablefont;
	public int width, height;
	// String mymessage = "";
	private String user;
	private String propsfile;
	private Properties props;

	private String dotmycash;
	private String mycashhome;
	private String userdir;
	private String userhome;
	private String javahome;
	private String desktop;

	public MyCash_gui(int x, int y, int w, int h)
	{

		super("gnucash");

		this.setLocation(x, y);
		this.setBounds(x, y, w, h);
		width = w;
		height = h;
		treefont = 16;
		listfont = 14;
		messagefont = 14;
		tablefont = 12;

		osversion = System.getProperty("os.version");
		os = System.getProperty("os.name");
		userdir = System.getProperty("user.dir");
		userhome = System.getProperty("user.home");
		user = System.getProperty("user.name");
		userdir = System.getProperty("user.dir");
		javahome = System.getProperty("java.home");
		System.out.println(os);
		System.out.println(osversion);
		System.out.println(userdir);
		System.out.println(userhome);
		System.out.println(user);
		System.out.println(javahome);
		try
		{
			// com.sun.java.swing.plaf.motif.MotifLookAndFeel
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			System.err.println("Couldn't use system look and feel.");
		}
		if (os.startsWith("Linux"))
		{
			System.out.println(" Linux identified ");
			dotmycash = "/home/" + user + "/.mycash/";
			mycashhome = "/home/" + user + "/Documents/mycash/";
			mycashexport = "/home/" + user + "/Documents/mycash/export";
			desktop = "/home/" + user + "/Desktop/";
			propsfile = dotmycash + "linux_properties.xml";
		} else if (os.startsWith("Windows"))
		{
			System.out.println(" Windows identified ");
			dotmycash = userhome + "/.mycash/";
			mycashhome = userhome + "/Documents/mycash/";
			mycashexport = userhome + "/Documents/MyCash/";
			desktop = "C:/Users/" + user + "/Desktop/";
			propsfile = dotmycash + "windows_properties.xml";
		} else
		{
			System.out.println(" No  operating system identified  ");
			System.exit(0);
		}
		System.out.println(dotmycash);
		System.out.println(mycashhome);
		System.out.println(mycashexport);
		System.out.println(propsfile);
		budgetfile = dotmycash + "budgets.xml";
		props = readProperties(propsfile);
		dburl = props.getProperty("dburl", "-!-!");
		dbuser = props.getProperty("dbuser", "");
		dbpassword = props.getProperty("dbpassword", "");
		if(dburl.contains("sqlite"))
		{
			System.out.println(" sqlite data  ");
		}
	
		temppath = accountspath + "/temp/";
		guiMenufont = new Font("Dialog", Font.PLAIN, 18);
		guiTextFieldfont = new Font("Dialog", Font.PLAIN, 14);
		footnotefont = new Font("SansSerif", Font.ITALIC, 10);
		headingfont = new Font("SansSerif", Font.ITALIC, 18);
		defaultfont = new Font("Serif", Font.PLAIN, 12);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.out.println("Closing ");
				System.exit(0);
			}
		});

		initiateStyles();
		backgroundcolor = this.getBackground();
		setVisible(true);
		Container contentpane = getContentPane();
		contentpane.setName(dburl);
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setBackground(Color.GREEN);
		setSize(new Dimension(w, h));

		this.setPreferredSize(new Dimension(w, h));
		mainmenu = new JMenuBar();
		this.setJMenuBar(mainmenu);
		accountpanel = new mainpanel();
		contentpane.add(accountpanel);
		accountpanel.setMenu(mainmenu);
		accountpanel.setBackground(Color.green);
		accountpanel.setVisible(true);
		accountpanel.repaint();
		outputpanel = accountpanel.currentdocument.textArea;
		validate();
		pack();

	}

	public java.util.Properties readProperties(String propsfile)
	{
		Properties prop = new Properties();
		try
		{
			prop.loadFromXML(new FileInputStream(propsfile));
			return prop;
		} catch (InvalidPropertiesFormatException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}