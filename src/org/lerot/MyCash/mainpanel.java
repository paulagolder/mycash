package org.lerot.MyCash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.lerot.MyCash.layout.jswMenuItem;
import org.lerot.MyCash.jswMyCashScrollPane;
import org.lerot.mywidgets.jswButton;
import org.lerot.mywidgets.jswHorizontalPanel;
import org.lerot.mywidgets.jswScrollPane;
import org.lerot.mywidgets.jswStyle;
import org.lerot.mywidgets.jswTextArea;
import org.lerot.mywidgets.jswVerticalPanel;

public class mainpanel extends jswHorizontalPanel
implements ActionListener, ItemListener, ComponentListener, TreeSelectionListener, ListSelectionListener

{
	private static final long serialVersionUID = 1L;
	JTree mytree;
	JPanel leftframe;
	jswMyCashScrollPane currentdocument;
	JMenu newcertificatemenu;
	JScrollPane treeView;
	JScrollPane budgetView;
	String lastclick = "";
	// private JList<mb_account> budgetlist;

	private JList<mb_account> budgetlist;
	private gc_account_tree_node selectedtreenode;
	private mb_account selectedmbaccount;
	private gc_account_tree_node topnode;
	private mb_accounts myba;

	public mainpanel()
	{
		this.addComponentListener(this);
		this.setBackground(Color.pink);

		setName("mainpanel");

		leftframe = new jswVerticalPanel("", false);

		MyCash_gui.MyTransactions = new gc_transactions();
		MyCash_gui.MyTransactions.getTransactions();
		System.out.println("Transactions:" + MyCash_gui.MyTransactions.size());

		// MyCash_gui.MySplits = new gc_splits(" root top");
		// MyCash_gui.MySplits.getSplits();

		MyCash_gui.MyAccounts = new gc_accounts("account list");
		MyCash_gui.MyAccounts.getAccounts();

		MyCash_gui.MySplits = new gc_splits(" root top");
		MyCash_gui.MySplits.getSplits();

		MyCash_gui.topaccount = MyCash_gui.MyAccounts.getTop();
		topnode = new gc_account_tree_node("top", MyCash_gui.topaccount);
		topnode.transsplits.totalTransfers();
		MyCash_gui.mybudgets = new my_budgets();
		myba = MyCash_gui.mybudgets.get(0).getAccounts();
		// myba.totalTransfers();

		jswVerticalPanel treepanel = new jswVerticalPanel("Accounts", true);
		jswHorizontalPanel buttonbar = new jswHorizontalPanel();
		jswButton aoverview = new jswButton(this, "Overview", "treeoverview");
		buttonbar.add(" center ", aoverview);
		treepanel.add(buttonbar);

		jswButton atoverview = new jswButton(this, "Detail", "treedetail");
		buttonbar.add(" center ", atoverview);
		treepanel.add(buttonbar);

		gc_account_tree treemodel = new gc_account_tree(topnode);
		// topnode.getTranssplits().totalTransfers();
		mytree = new JTree(treemodel);
		mytree.addTreeSelectionListener(this);
		jswMyCashTreeScrollPane accountframe = new jswMyCashTreeScrollPane(mytree);
		treepanel.add(mytree);
		leftframe.add(treepanel);

		jswVerticalPanel budgetpanel = new jswVerticalPanel("Budgets", true);
		jswHorizontalPanel bbuttonbar = new jswHorizontalPanel();
		jswButton boverview = new jswButton(this, "Overview", "budgetsoverview");
		bbuttonbar.add(" center ", boverview);
		jswButton bdetail = new jswButton(this, "Detail", "budgetsdetail");
		bbuttonbar.add(" center ", bdetail);
		budgetpanel.add(bbuttonbar);

		budgetlist = new JList(myba);
		// JScrollPane listScroller = new JScrollPane(budgetlist);

		// budgetlist.setBorder(jswStyle.makecborder("Budgets"));
		budgetlist.addListSelectionListener(this);
		jswMyCashListScrollPane budgetframe = new jswMyCashListScrollPane(budgetlist);
		// leftframe.add(" FILLH ", budgetlist);
		budgetpanel.add(" FILLH ", budgetframe);
		leftframe.add(" FILLH ", budgetpanel);
		add(" LEFT WIDTH=300 ", leftframe);

		currentdocument = new jswMyCashScrollPane();

		MyCash_gui.outputarea = currentdocument.textArea;
		add(" FILLW ", currentdocument);
		this.repaint();

		revalidate();
		gc_account_tree_walker atw = new gc_account_tree_walker(" node ", topnode);
		atw.getAllChildren(topnode);
		atw.sortAllChildren(topnode);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if (command.equals("print"))
		{
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Specify a file to save");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
			fc.setFileFilter(filter);
			fc.setCurrentDirectory(new File(MyCash_gui.mycashexport));
			if (lastclick.equalsIgnoreCase("budget"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_" + selectedmbaccount + ".txt";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					save(fileToSave.getPath(), selectedmbaccount.toTxt());
				}
			} else if (lastclick.equalsIgnoreCase("tree"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_" + selectedtreenode.account.getName() + ".txt";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					save(fileToSave.getPath(), selectedtreenode.transsplits.toTXT());
				}
			} else if (lastclick.equalsIgnoreCase("budgetsoverview"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_budgetoverview.txt";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					save(fileToSave.getPath(), myba.budgetsummary());
				}
			} else if (lastclick.equalsIgnoreCase("budgetsdetail"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_budgetdetail.txt";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					String output = "";
					ListModel<mb_account> model = budgetlist.getModel();
					for (int i = 0; i < model.getSize(); i++)
					{
						output += model.getElementAt(i).printdetail() + "\n";
					}
					save(fileToSave.getPath(), output);
				}

			} else if (lastclick.equalsIgnoreCase("treeoverview"))
			{

				String bufilename = MyCash_gui.mycashexport + "/export_budgetdetail.txt";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					String output = "";
					output += " Accounts :" + "\n";

					gc_account_tree_node anode = topnode.getnode("Opening Balances");
					output += anode.printOpeningSummary();
					anode = topnode.getnode("Income");
					output += anode.printIncomeSummary();
					anode = topnode.getnode("Expenses");
					output += anode.printIncomeSummary();
					anode = topnode.getnode("Assets");
					output += anode.printAssetsSummary();
					save(fileToSave.getPath(), output);
				}
			}
		} else if (command.equals("export"))
		{
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Specify a file to save");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
			fc.setFileFilter(filter);
			fc.setCurrentDirectory(new File(MyCash_gui.mycashexport));
			if (lastclick.equalsIgnoreCase("budget"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_" + selectedmbaccount + ".csv";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					save(fileToSave.getPath(), selectedmbaccount.toCsv());
				}
			} else if (lastclick.equalsIgnoreCase("tree"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_" + selectedtreenode.account.getName() + ".cvs";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					save(fileToSave.getPath(), selectedtreenode.transsplits.toCSV());
				}
			} else if (lastclick.equalsIgnoreCase("budgetsoverview"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_budgetoverview.csv";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					save(fileToSave.getPath(), myba.budgetsummaryToCSV());
				}
			} else if (lastclick.equalsIgnoreCase("budgetsdetail"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_budgetdetail.csv";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					String output = "";
					ListModel<mb_account> model = budgetlist.getModel();
					for (int i = 0; i < model.getSize(); i++)
					{
						output += model.getElementAt(i).printdetailToCSV() + "\n";
					}
					save(fileToSave.getPath(), output);
				}

			} else if (lastclick.equalsIgnoreCase("treeoverview"))
			{
				String bufilename = MyCash_gui.mycashexport + "/export_accounts.cvs";
				File file = new File(bufilename);
				fc.setSelectedFile(file);
				String output = selectedtreenode.transsplits.toCSV();
				gc_account_tree_walker atw = new gc_account_tree_walker(" node ", topnode);
				output = atw.toCSV(topnode);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fc.getSelectedFile();
					save(fileToSave.getPath(), output);
				}
			}
		}
		if (command.equals("loadmycash"))
		{
			Connection com = utils.myconnect();
			MyCash_gui.MyAccounts = new gc_accounts("my top");
			MyCash_gui.MyAccounts.getAccounts();
			gc_account actop = MyCash_gui.MyAccounts.getTop();
			System.out.println(" accounts loaded :" + MyCash_gui.MyAccounts.size());
			MyCash_gui.MyTransactions = new gc_transactions();
			MyCash_gui.MyTransactions.getTransactions();
			MyCash_gui.outputpanel.append(" transactions loaded :" + MyCash_gui.MyTransactions.size() + "\n");
			MyCash_gui.MyAccounts.logtoconsol();
			return;
		}
		if (command.equals("treeoverview"))
		{
			MyCash_gui.outputpanel.setText("");
			MyCash_gui.outputpanel.append(" Accounts :" + "\n");

			gc_account_tree_node anode = topnode.getnode("Opening Balances");
			MyCash_gui.outputpanel.append(anode.printOpeningSummary());
			anode = topnode.getnode("Income");
			MyCash_gui.outputpanel.append(anode.printIncomeSummary());
			anode = topnode.getnode("Expenses");
			MyCash_gui.outputpanel.append(anode.printIncomeSummary());
			anode = topnode.getnode("Assets");
			MyCash_gui.outputpanel.append(anode.printAssetsSummary());
			lastclick = "treeoverview";
		}
		if (command.equals("budgetsoverview"))
		{
			MyCash_gui.outputpanel.setText("");
			selectedmbaccount = (mb_account) budgetlist.getSelectedValue();
			MyCash_gui.outputpanel.append(myba.budgetsummary());
			lastclick = "budgetsoverview";
		}
		if (command.equals("budgetsdetail"))
		{
			String output = "";
			MyCash_gui.outputpanel.setText("");
			ListModel<mb_account> model = budgetlist.getModel();
			for (int i = 0; i < model.getSize(); i++)
			{
				output += model.getElementAt(i).printdetail() + "\n";
			}

			MyCash_gui.outputpanel.append(output);
			lastclick = "budgetsdetail";
		}
		if (command.equals("treedetail"))
		{
			MyCash_gui.outputpanel.setText("");
			MyCash_gui.outputpanel.append(" Accounts :" + "\n");
			gc_account_tree_walker atw = new gc_account_tree_walker(" node ", topnode);
			String output = atw.printAllChildren(topnode);
			MyCash_gui.outputpanel.append(output);
			lastclick = "treedetail";

		}

		System.out.println(" selected " + command);
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{

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

		repaint();
	}

	public JMenu getFileMenu()
	{
		JMenu newfilemenu = new JMenu("File");
		JMenuItem newitem = (JMenuItem) (new jswMenuItem("Print (txt)", "print", this));
		newfilemenu.add(newitem);
		JMenuItem anewitem = (JMenuItem) (new jswMenuItem("Export (csv)", "export", this));
		newfilemenu.add(anewitem);
		return newfilemenu;
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		// System.out.println(" Selection"+e);
	}

	public Document loadDocument(String filename)
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(filename));
			return document;
		} catch (DocumentException e)
		{
			// MyCert_gui.messagepanel.error(" failed openingxxx " + filename + " " + e);
		}
		return null;
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
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		MyCash_gui.outputpanel.setText("");

		selectedmbaccount = (mb_account) budgetlist.getSelectedValue();
		String detail = selectedmbaccount.printdetail();
		MyCash_gui.outputpanel.append(detail);
		lastclick = "budget";
	}

	public void valueChanged(TreeSelectionEvent e)
	{
		selectedtreenode = (gc_account_tree_node) mytree.getLastSelectedPathComponent();
		if (selectedtreenode == null)
			return;
		gc_account selectedaccount = selectedtreenode.account;
		MyCash_gui.outputpanel.setText("");
		selectedtreenode.transsplits.totalTransfers();
		MyCash_gui.outputpanel.append(selectedaccount.heading());
		MyCash_gui.outputpanel.append(selectedtreenode.transsplits.heading());
		MyCash_gui.outputpanel.append(selectedtreenode.transsplits.toString());
		MyCash_gui.outputpanel.append(selectedtreenode.transsplits.footing());
		MyCash_gui.outputpanel.append("found " + selectedtreenode.transsplits.size() + "\n");
		lastclick = "tree";
	}

	public int save(String filename, String txtout)
	{

		PrintWriter printWriter;
		int k = 0;
		try
		{
			printWriter = new PrintWriter(filename);
			printWriter.print(txtout);
			printWriter.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return k;

	}

}
