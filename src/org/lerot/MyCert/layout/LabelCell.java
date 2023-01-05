package org.lerot.MyCert.layout;

import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.layout.HJLabel;
import org.lerot.MyCert.layout.VJLabel;

public class LabelCell extends TextDisplayObject
{

	private Vector<String> text = new Vector<String>();

	public LabelCell(DisplayObject parent, Element element)
	{
		super(parent);
		jcertObject labelcellstyle = parent.findStyle("labelcell");

		if (labelcellstyle != null)
			labelcellstyle.copyStyle(this);
		loadAttributes(element);
		if (element.getText() != null && element.getText().trim().length() > 0) {
			text.add(element.getText().trim());
		}
		List<Element> list = element.elements();
		for (Element element2 : list) {
			String ename = element2.getName();
			if (ename.equals("text")) {
				text.add(element2.getText().trim());
			}
		}
		getTextSize();
	}

	public int countCols()
	{
		return 1;
	}

	@Override
	public int countDisplayObjects()
	{
		return 1;
	}

	@Override
	public double getMinHeight()
	{
		getTextSize();
		int padding = getPadding();
		// if(padding==0) padding=5;
		int minheight = myMinimumSize.height + 2 * padding;
		if (myHeight > 0 && myHeight > minheight)
			return myHeight;
		else
			return myMinimumSize.height;
	}

	@Override
	public double getMinWidth()
	{
		getTextSize();
		int padding = getPadding();
		// if(padding==0) padding=5;
		int minwidth = myMinimumSize.width + 2 * padding;
		if (myWidth > 0 && myWidth > minwidth)
			return myWidth;
		else
			return minwidth;
	}

	public JComponent getPanelObject()
	{
		if (isHorizontal()) {
			HJLabel alabel = new HJLabel();
			alabel.setText(text);
			alabel.getSize();
			formatComponent(alabel);
			Dimension d = myMinimumSize;
			return alabel;

		} else {
			VJLabel alabel = new VJLabel();
			alabel.setText(text);
			alabel.getSize();
			formatComponent(alabel);
			Dimension d = myMinimumSize;
			return alabel;
		}
	}

	@Override
	public JComponent getPanelObject(Document adoc)
	{
		return getPanelObject();
	}

	@Override
	public JComponent getPanelObject(Document adoc, int row)
	{
		return getPanelObject();
	}

	@Override
	public Dimension getTextSize()
	{
		JLabel dlabel = new JLabel();
		fm = dlabel.getFontMetrics(getPFont());
		int lineheight = fm.getHeight() + this.getInterblockspacing();
		int numberoflines = text.size();
		int maxlength = 0;
		for (String aline : text) {
			int length = fm.stringWidth(aline);
			if (length > maxlength)
				maxlength = length;
		}
		int textheight = numberoflines * lineheight;
		int textwidth = maxlength;
		int sheight = textheight + 2 * this.getPadding();
		int swidth = textwidth + 2 * this.getPadding();

		if (isHorizontal()) {
			myMinimumSize = new Dimension(swidth, sheight);

		} else {
			myMinimumSize = new Dimension(sheight, swidth);
		}
		return myMinimumSize;

	}

	@Override
	public boolean hasContent()
	{
		if (text.size() > 0)
			return true;
		else
			return false;
	}

	public boolean hasLabel()
	{
		if (text.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public String toString()
	{
		if (text.size() > 1) {
			String out = "";
			out += text.get(0);
			for (int i = 1; i < text.size(); i++) {
				out += " " + text.get(i);
			}

			return out;
		} else if (text.size() == 1) {
			return text.get(0);
		} else
			return " --No Label Text--";
	}

}