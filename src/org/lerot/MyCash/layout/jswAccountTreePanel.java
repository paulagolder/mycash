package org.lerot.MyCash.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.lerot.MyCash.gc_account;
import org.lerot.mywidgets.jswStyle;
import org.lerot.mywidgets.jswStyles;


public class jswAccountTreePanel extends JPanel implements ComponentListener
{

	private static final long serialVersionUID = 1L;

	private class MyRenderer extends JLabel implements TreeCellRenderer
	{

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus)
		{
			if (value instanceof gc_account)
			{
				gc_account so = (gc_account) value;

				setText(so.getName() );
				setFont(new Font("SansSerif", Font.PLAIN, treefont));
				if (sel) setForeground(Color.red);
				else
					setForeground(Color.black);
				return this;
			} else
			{
				setText(value.toString());
				return this;
			}
		}
	}

	static void print(String s)
	{
		System.out.print(s);
	}

	public JPanel commandPanel;

	int linecount;
	String name;
	int panelwidth = 200;
	boolean redisplay;
	public JTree reptree;
	// public JPanel mainPanel;
	public JScrollPane reptreeView;
	public int treefont;

	public jswAccountTreePanel(String inname, TreeNode aNode, int intreefont)
	{
		name = inname;
		treefont = intreefont;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		DefaultTreeModel treeModel = new DefaultTreeModel(aNode);

		reptree = new JTree(treeModel);
		reptreeView = new JScrollPane(reptree);
		reptreeView
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		reptree.setEditable(true);
		reptree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		reptree.setShowsRootHandles(true);

		MyRenderer renderer = new MyRenderer();
		reptree.setCellRenderer(renderer);
		treeModel.reload();
		((DefaultTreeModel)treeModel).nodeStructureChanged((TreeNode)aNode);
		System.out.println(" row count ="+reptree.getRowCount());
		for (int i = 0; i < reptree.getRowCount(); i++) {
			   reptree.expandRow(i);
			}
		commandPanel = new JPanel();
		commandPanel.setMinimumSize(new Dimension(panelwidth, 150));
		commandPanel.setBorder(jswStyle.makecborder("Report Command Panel"));

		this.add(reptreeView);
		this.add(commandPanel);
		this.setPreferredSize(new Dimension(panelwidth, 550));
		this.addComponentListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	public void addTreeModelListener(TreeModelListener tml)
	{
		reptree.getModel().addTreeModelListener(tml);
	}

	public void addTreeSelectionListener(TreeSelectionListener tsl)
	{
		reptree.addTreeSelectionListener(tsl);
	}

	public void CommandPanelHeight(int hieght)
	{

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
		/*int ww = getWidth();
		int hw = getHeight();
		reptreeView.setPreferredSize(new Dimension(ww - 40, hw
				- commandPanel.getHeight() - 30));
		commandPanel.setPreferredSize(new Dimension(ww - 40, commandPanel
				.getHeight()));*/
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
	}

	
}
