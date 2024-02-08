package org.lerot.MyCert.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.lerot.MyCert.MyCert_gui;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.utils;

public class TablePanel extends jcertPanel implements ActionListener
{

	private static final long serialVersionUID = 1L;
	int nrows;
	int ncols;
	TableLayout tlt;
	private int cellborderwidth = 0;
	private Color cellbordercolor = Color.green;
	Document doc;
	JPanel top;

	public TablePanel(int nr, int nc)
	{
		tlt = new TableLayout();
		this.setLayout(tlt);
	}

	public void addCell(JComponent apanel, String set, int r, int c)
	{
		String settings = "";
		if (set != null)
			settings += set;
		settings += " COL=" + c;
		settings += " ROW=" + r;		
		add(settings, apanel);
	}

	public void addRow(Document adoc, jcertObject ahgroup, int row, int datarow)
	{
		if (ahgroup instanceof hgroup) {
			hgroup agroup = (hgroup) ahgroup;
			String obtype = agroup.getObjectPath();
			int rowcount = agroup.getRowcount();
			int maxh = (int) agroup.getMinHeight();
			int col = 0;
			for (jcertObject cll : agroup.getMembers()) {
				if (cll instanceof DisplayObject) {
					DisplayObject dispo = (DisplayObject) cll;
					JComponent jcp = dispo.getPanelObject(adoc, datarow);
					if (col == 0 && obtype != null && rowcount > 1) {
						Popup pp = new Popup(obtype + ":" + datarow,
								MyCert_gui.certificateframeloaderpanel);
						jcp.setComponentPopupMenu(pp);
						setInheritsPopup(jcp);
					}

					String settings = "";
					int colspan = dispo.getColspan();
					if (colspan < 1)
						colspan = 1;
					int width = dispo.getMyWidth();
					if (width > 1) {
						settings += " WIDTH=" + width + " ";
					}
					if (colspan > 1) {
						settings += " COLSPAN=" + colspan + " ";
					}
					int rowspan = dispo.getRowspan();
					if (rowspan < 1)
						rowspan = 1;
					if (rowspan > 1) {
						settings += " ROWSPAN=" + rowspan + " ";
					}
					addCell(jcp, settings, row, col);
					col = col + colspan;

				}
			}
			// tlt.setRow(row, maxh);
		} else
			return;
		tlt.changed();
	}

	private void setInheritsPopup(JComponent jcp)
	{
		jcp.setInheritsPopupMenu(true);
		Component[] cs = jcp.getComponents();

		for (Component c : cs) {
			if (c instanceof JComponent) {
				setInheritsPopup((JComponent) c);
			}
		}

	}

	public void addRow(Document adoc, jcertObject ahgroup, int row)
	{
		addRow(adoc, ahgroup, row, 0);

	}

	int getCellborderwidth()
	{
		return cellborderwidth;
	}

	public void setCellborderwidth(int cellborderwidth)
	{
		this.cellborderwidth = cellborderwidth;
	}

	Color getCellbordercolor()
	{
		return cellbordercolor;
	}

	public void setCellbordercolor(String acolor)
	{
		this.cellbordercolor = utils.color(acolor);
	}

	@Override
	public Dimension getMinimumSize()
	{
		LayoutManager lm = this.getLayout();
		return lm.preferredLayoutSize(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		// System.out.println(" action here "+command);
		if (command.startsWith("NEW:")) {
			String tid = command.substring(4);
			String delims = ":";
			String[] tokens = tid.split(delims);
			String path = tokens[0];
			int rowno = Integer.parseInt(tokens[1]);
			System.out.println(" NEW " + tokens[0] + "-" + rowno);
			// currentdocument.addElement(arg0);
			List nodeList = doc.selectNodes(path);
			Node anode = (Node) nodeList.get(rowno);
			Element parent = anode.getParent();
			List list = parent.content();
			Element newElement = (Element) anode.clone();
			list.add(rowno, newElement);

		}
		if (command.startsWith("DELETE:")) {
			String tid = command.substring(8);
			System.out.println(" DELETE " + tid);

		}

	}
}
