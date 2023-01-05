package org.lerot.MyCert.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import org.lerot.MyCert.utils;
import org.lerot.MyCert.layout.jcertLayout.settings;

public class InputPanel extends JTextPane implements FocusListener
{
	private static final long serialVersionUID = 1L;
	private int lineheight, textwidth, textheight, columncount;

	protected Color backgroundColor;
	protected Color bordercolor = Color.green;
	protected int borderwidth = 0;
	protected FontMetrics fm;
	protected Color foregroundColor;
	protected int horizontalalignment = JLabel.CENTER;
	protected int verticalalignment = JLabel.TOP;
	protected int padding = 2, interlinedist = 2;
	protected Font pFont;
	protected Vector<String> text;
	private int datarow;
	private int minwidth=10;
	InputCell cell;

	public Action nextFocusAction = new AbstractAction("Move Focus Forwards") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt)
		{
			// System.out.println(" move forward"+((InputPanel)evt.getSource()).getText());
			((Component) evt.getSource()).transferFocus();
		}
	};
	protected static String savedtext;
	
	public Action copycontent = new AbstractAction("Copy Content") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt)
		{
			Component  comp = ((Component) evt.getSource());
			if(comp instanceof JTextPane)
			{
				String text = ((JTextPane)comp).getText();
				System.out.println ( " Cell contains "+text);
				savedtext = text;
				((Component) evt.getSource()).transferFocus();
			}
		
		}
	};
	
	public Action pastecontent = new AbstractAction("Paste Content") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt)
		{
			Component  comp = ((Component) evt.getSource());
			if(comp instanceof JTextPane)
			{
				((JTextPane)comp).setText(savedtext);
				System.out.println ( " Cell contains "+text);
				((Component) evt.getSource()).transferFocus();
			}
		
		}
	};
	
	public Action copyabove = new AbstractAction("Copy Above Content") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt)
		{
			Component  comp = ((Component) evt.getSource());
			if(comp instanceof JTextPane)
			{
				JTextPane jtp = ((JTextPane)comp);
				InputPanel ipp = (InputPanel)jtp;
				String cv = ipp.cell.getCellValue(datarow - 1);
			
				System.out.println ( " Cell contains "+cv);
				((JTextPane)comp).setText(cv);
				((Component) evt.getSource()).transferFocus();
			}
		
		}
	};

	public Action prevFocusAction = new AbstractAction("Move Focus Backwards") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt)
		{
			((Component) evt.getSource()).transferFocusBackward();
		};

	};

	private int charwidth;
	private int minWidth;
	private int datacolumn;

	public InputPanel(InputCell acell, int row,  String tooltip)
	{
		super();
		cell = acell;
		datarow = row;
		
		Color backgroundcolor = Color.decode("#E4DCDC");
		setBackground(backgroundcolor);
		setOpaque(true);
		setForeground(Color.blue);
		setBorder(utils.setborder(Color.GRAY, 1));
		setEditable(true);
		setMinWidth(acell.getMyWidth());
		this.setToolTipText(tooltip);
		this.addFocusListener(this);
		pFont = new Font("SansSerif", Font.PLAIN, 10);
		utils.setJTextPaneFont(this, pFont, Color.blue);
		super.setFont(pFont);
		fm = this.getFontMetrics(pFont);
		text = new Vector<String>();
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("TAB"),
				"helpAction");
		this.getActionMap().put("helpAction", nextFocusAction);
		
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke('C',java.awt.event.InputEvent.ALT_MASK ),
				"copycontent");
		this.getActionMap().put("copycontent",copycontent);
		
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke('V',java.awt.event.InputEvent.ALT_MASK ),
				"pastecontent");
		this.getActionMap().put("pastecontent",pastecontent);
		
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke('D',java.awt.event.InputEvent.ALT_MASK ),
				"copyabove");
		this.getActionMap().put("copyabove",copyabove);
		
		if(acell.isTableCell())
		{
			TableLayout tlt = (TableLayout) (this.getParent().getParent()).getLayout();
			settings mysettings = tlt.getSettings(this);
		copyaction acopyaction = new copyaction(this, mysettings, "stuff", new Integer(KeyEvent.VK_D));
		this.getActionMap().put("copyabove", acopyaction);
		}
		
	}

	private void setMinWidth(int myWidth) {
		this.minWidth= myWidth;
		
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		InputPanel ip1 = (InputPanel) e.getSource();
		String oldvalue = cell.getCellValue(datarow);
		ip1.setText(oldvalue);
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		InputPanel ip1 = (InputPanel) e.getSource();
		String newvalue = ip1.getText();
		String oldvalue = cell.getCellValue(datarow);

		if (oldvalue != null) 
		{
			if (!oldvalue.equals(newvalue)) {
				System.out.println(" value changed new:" + newvalue + "  old:"
						+ oldvalue);
				cell.updateValue(datarow, newvalue);
			}
		} else {
			if (newvalue != null && !newvalue.equalsIgnoreCase("")) 
			{
				System.out.println(" new value :" + newvalue);
				cell.updateValue(datarow, newvalue);
			}
			else
			{
				System.out.println(" no change:") ;
				//old is null new is null so do nothing 
			}
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		setTextSize();

		this.repaint();

	}
	
	public Dimension getPreferredSize()
	{
	
		Dimension d = super.getPreferredSize();
		if(d.width < this.minWidth) 
		{
			d.width = this.getminWidth();
		}
		
		return d ;
	}

	public Dimension getMinimumSize()
	{
		Dimension d = super.getMinimumSize();
		if(d.width < this.minWidth) 
		{
			d.width = this.getminWidth();
		}
		
		
		return d ;
	}

	

	private int getminWidth() {

		return minWidth;
	}

	private void setMySize(Dimension d)
	{
		setTextSize();
		int mincol = d.width / charwidth;
		int columns = mincol;
		// setColumns(mincol);
		setMinimumSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
		System.out.println(" columns=" + columns + " w=" + d.width + " h="
				+ d.height + " charwidth=" + charwidth + " text=" + getText());

		// super.setSize( d);

	}

	public void setTextSize()
	{
		text.add(getText());
		lineheight = fm.getHeight() + interlinedist;
		int numberoflines = text.size();
		int maxlength = 0;
		columncount = 0;
		for (String aline : text) {
			int length = fm.stringWidth(aline);
			if (length > maxlength)
				maxlength = length;
			if (columncount < aline.length())
				columncount = aline.length();
		}
		textheight = numberoflines * lineheight;
		textwidth = maxlength;

		if (columncount > 0)
			charwidth = textwidth / columncount;
		else
			charwidth = fm.charWidth('M');
		if(textwidth < minWidth) textwidth= minwidth;
	}

	
	

}
