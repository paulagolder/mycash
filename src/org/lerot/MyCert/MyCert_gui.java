package org.lerot.MyCert;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;



public class MyCert_gui extends JFrame
{
	static Color backgroundcolor;
	public static JTabbedPane browserpanel;
	public static certificateeditpanel certificateframeloaderpanel;
	public static String certificatepath, temppath;
	public static Vector<documentTemplate> certificates = new Vector<documentTemplate>();
	public static String defaultfontname = "Helvetica";
	public static Font guiMenufont, footnotefont, defaultfont,
			guiTextFieldfont, headingfont;
	public static JMenuBar mainmenu;
	public static String mce_userpath;
	public static String os;
	private static final long serialVersionUID = 1L;
	public static boolean showborders = false;
	public static String storepath;
	static templateloaderpanel templateloaderpanel;
	static messagepanel messagepanel;
	static userpanel userframepanel;
	public static String templatepath;
	static TreeSet<documentTemplate> templates ;
	private static final double version = 2.1;
	public static String username;
	public static String userdir,userhome;
	public static user currentuser;
	

	public static void main(String[] args)
	{

	 //	PlasticLookAndFeel laf = new Plastic3DLookAndFeel();
    	//PlasticLookAndFeel.setCurrentTheme(new ExperienceBlue());
    //	try {
	//		UIManager.setLookAndFeel(laf);
	//	} catch (UnsupportedLookAndFeelException e) {
	//		e.printStackTrace();
	//	}
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo look : looks) {
		System.out.println(look.getClassName());
		}
		/*try
		{
			PlasticLookAndFeel laf = new Plastic3DLookAndFeel();
		    PlasticLookAndFeel.setCurrentTheme(new ExperienceBlue());
			UIManager.setLookAndFeel(laf);
		} catch (Exception e)
		{
		}*/
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		;
		MyCert_gui mframe = new MyCert_gui(200, 200, d.width - 400,
				d.height - 400);

		mframe.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		mframe.getContentPane().setLayout(
				new BoxLayout(mframe.getContentPane(), BoxLayout.Y_AXIS));
		mframe.pack();
		mframe.setVisible(true);
	}
	public String osversion;
	JSplitPane splitPane = null;;
	public int treefont, listfont, messagefont, tablefont;
	static JPanel cards;
	public int width, height;
	String mymessage="";

	public MyCert_gui(int x, int y, int w, int h)
	{
		super("CERTIFICATES");
		this.setLocation(x, y);
		width = w;
		height = h;
		treefont = 16;
		listfont = 14;
		messagefont = 14;
		tablefont = 12;
		String userhome = "";
		userdir = System.getProperty("user.dir");
	    System.out.println(userdir);
		username = System.getProperty("user.name");
		osversion = System.getProperty("os.version");
		os = System.getProperty("os.name");
		 //System.out.println(os);
		if (os.contains("Vista"))
		{
			initVista();
		} 
		else if (os.contains(" XP"))
		{
			int k=0;
			if(userdir.indexOf("Documents and Settings")>0)
			{
				int l= userdir.indexOf("Documents and Settings");
				 k = userdir.indexOf("\\", l+23);
			}
			else
			      k = userdir.indexOf("\\", 9);
			userhome = userdir.substring(0, k) + "/";
			 System.out.println(userhome);
			File f = new File(userhome + "Certificates");
			if (f.exists())
			{
				certificatepath = f.getAbsolutePath();
				System.out.println(certificatepath);
			} else
			{
				f = new File(userhome + "My Documents/Certificates");
				if (f.exists())
				{
					certificatepath = f.getAbsolutePath();
					System.out.println(certificatepath);
				} else
				{
					certificatepath = "";
					System.out.println(" not found ");
					mymessage += "Cannot find a Certificates folder";
				}
			}
		} else if (os.equals("Linux"))
		{
			userhome = "/home/"+ username+"/";
			certificatepath = userhome + "public_html/Certificates/";
		}
		else
		{
			certificatepath = "";
			mymessage += "\nCannot recognise operating system : "+ os;
		}
		
		templates = new TreeSet<documentTemplate>(new TreeComp());
		
		mymessage += "\nOS = "+os;
		mymessage += "\nLaunch path ="+userdir ;
		mymessage += "\nCertificate path ="+certificatepath ;
		templatepath = certificatepath + "/Templates/";
		// certificatepath = "/home/paul/Certificates/";
		temppath = certificatepath + "/temp/";

		loadUser();

		guiMenufont = new Font("Dialog", Font.PLAIN, 18);
		guiTextFieldfont = new Font("Dialog", Font.PLAIN, 14);
		footnotefont = new Font("SansSerif", Font.ITALIC, 10);
		headingfont = new Font("SansSerif", Font.ITALIC, 18);
		defaultfont = new Font("Serif", Font.PLAIN, 12);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.out.println("Closing ");
				System.exit(0);
			}
		});
		backgroundcolor = this.getBackground();
		setVisible(true);
		Container contentpane = getContentPane();
		JFrame.setDefaultLookAndFeelDecorated(true);
		setSize(new Dimension(w, h));
		cards = new JPanel(new CardLayout());
		cards.setPreferredSize(new Dimension(w, h));
		mainmenu = new JMenuBar();
		this.setJMenuBar(mainmenu);
		messagepanel = new messagepanel();
		templateloaderpanel = new templateloaderpanel();
		certificateframeloaderpanel = new certificateeditpanel();
		userframepanel = new userpanel();
		templateloaderpanel.setMenu(mainmenu);
		userframepanel.setMenu(mainmenu);
		certificateframeloaderpanel.setMenu(mainmenu);
		messagepanel.setMenu(mainmenu);
		cards.add(templateloaderpanel, "Template Loader");
		cards.add(certificateframeloaderpanel, "Certificate loader");
		cards.add(userframepanel, "User");
		cards.add(messagepanel, "Messages");

		contentpane.add(cards);
		selectCard("Messages");
		MyCert_gui.messagepanel.display(mymessage);
		if (certificatepath != null)
		{
			utils.loadAllTemplates();
			templateloaderpanel.reloadList();
			certificateframeloaderpanel.makeTemplateMenu();
			utils.sleep(5);
			selectCard("Certificate loader");
		} else
		{
			//MyCert_gui.selectCard("Messages");
			//MyCert_gui.messagepanel.display(mymessage);
		}
		pack();

	}

	public static void selectCard(String string)
	{
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, string);
	}

	public boolean loadUser()
	{
		currentuser = new user("anon");
		currentuser.loadUser(certificatepath + "user.xml");
		if (currentuser.getName() == null)
		{
			currentuser.setName(username);
			return false;
		} else
			return true;

	}
	
	private void initVista()
	{
		
			int k = userdir.indexOf("\\", 9);
			System.out.println(userdir);
			userhome = userdir.substring(0, k) + "/";
			System.out.println(userhome);
			String root = userhome;
			File f = searchforfile(userhome , "Certificates");
			
			if (f!=null)
			{
				certificatepath = f.getAbsolutePath();
				System.out.println("found"+certificatepath);
			} else
			{
				if(root.endsWith("workspace/"))root= root.substring(0,root.length()-10);
				if(root.endsWith("applications/"))root= root.substring(0,root.length()-13);
				 f = searchforfile(root , "Certificates");		
				if (f!=null)
				{
					certificatepath = f.getAbsolutePath();
					System.out.println(certificatepath);
				} else
				{
					certificatepath = "";
					mymessage = "Cannot find a Certificates folder";
				}
			}
		} 

	
	private File searchforfile(String root,String filename)
	{
		File f = new File(root + filename);
		System.out.println("trying "+f.getAbsolutePath());
		if (f.exists())
		{
			return f;
		} else
		{
			f = new File(root + "Documents/"+filename);
			System.out.println("trying "+f.getAbsolutePath());
			if (f.exists())
			{
				return f;
			} else
			{
				return null;
			}
		}
	}
	

}