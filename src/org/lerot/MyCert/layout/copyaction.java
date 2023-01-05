package org.lerot.MyCert.layout;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.lerot.MyCert.layout.jcertLayout.settings;

public class copyaction  extends AbstractAction
{
 
	private static final long serialVersionUID = 1L;
    String atext;
    InputPanel acell;
    jcertLayout  thislayout;
	private settings settings;
    
public copyaction(InputPanel cell, settings mysettings, String atext, Integer mnemonic)
  {
	super("copy");
	acell = cell;
	this.atext=atext;
	settings =mysettings;
    putValue(SHORT_DESCRIPTION," copy cell above");
    putValue(MNEMONIC_KEY,"cc");
  }

  public void actionPerformed(ActionEvent e)
  {
  		int ncol = settings.getInteger("COL");
		int nrow = settings.getInteger("ROW");
		 JOptionPane.showMessageDialog(null, "Cell c="+ncol+" r="+nrow);
    acell.setText(atext);
    acell.transferFocus();
  }
}