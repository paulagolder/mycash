package org.lerot.MyCert.layout;

import java.awt.Dimension;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.lerot.MyCert.jcertObject;
import org.lerot.MyCert.layout.DisplayObject;
import org.lerot.MyCert.layout.templatebody;

public class InputCell extends TextDisplayObject
{
	private Vector<String> targets;
	private String objectpath;
	private Document doc;
	templatebody topcert;
	private int datarowcount = 0;

	public InputCell(DisplayObject parent,Element element)
	{
		super(parent);
		jcertObject inputcellstyle = parent.findStyle("inputcell");
		setDirection("horizontal");		
		if (inputcellstyle != null)
			inputcellstyle.copyStyle(this);
		targets = new Vector<String>();
		loadAttributes(element);
		List<Attribute> list = element.attributes();
		for (Attribute attribute : list) {
			String name = attribute.getName();
			if (name.equals("target"))
			{
				String celltarget = attribute.getStringValue();
				if (celltarget != null && celltarget.length() > 0) 
				{
					StringTokenizer st = new StringTokenizer(celltarget, ":");
					while (st.hasMoreTokens()) 
					{
						String token = st.nextToken();
						targets.add(token);
					}
				}
			}
		}
		setObjectPath();
	}

	@Override
	public int countCols()
	{
		return 1;
	}

	@Override
	public int countDisplayObjects()
	{
		return 1;
	}

	public templatebody findCert()
	{
		objectpath = null;
		jcertObject container = this.getParent();
		if (container.getObjecttype() != null)
			objectpath = container.getObjecttype();
		while (container.getParent() != null) {
			container = container.getParent();
			String objtype = container.getObjecttype();
			if (objtype != null) {
				if (objectpath == null) {
					objectpath = container.getObjecttype();
				} else if (!objectpath.startsWith("/")) {
					if (!objectpath.endsWith(objtype)) {
						objectpath = container.getObjecttype() + "/"
								+ objectpath;
					}
				} else
					objectpath = container.getObjecttype();
			}
		}

		if (container instanceof templatebody)
			return (templatebody) container;
		else
			return null;
	}

	public String getCellValue(Document doc, int rownumber)
	{
		if (doc != null) {
			List<Node> nodes = doc.selectNodes( objectpath);
			if (rownumber < 0 || rownumber >= nodes.size()) {
				//System.out.println(" cellvalue 1 " + objectpath);
				return "row " + rownumber;
			}
			Element element = (Element) nodes.get(rownumber);
			String value = getCellValue(element);
			return value;
		} else
			return "";
	}

	private String getCellValue(Element element)
	{
		int counttest = 0, goodtest = 0;
		for (String target : targets) {
			String value = null;
			String v = null;
			int k = target.indexOf("=");
			if (k > 0) {
				v = target.substring(k + 1);
				target = target.substring(0, k);
				try {
					Object obj = element.selectSingleNode(target);
					if (obj instanceof Element) {
						Element cellvalue = (Element) obj;
						if (cellvalue != null) {
							value = cellvalue.getText();
						}
					}
				} catch (RuntimeException e) {
					return null;
				}
				;
				if (v != null && value != null) {
					counttest++;
					if (value.equals(v))
						goodtest++;
				}
			}
		}

		if (goodtest == counttest) {
			for (String target : targets) {
				String value = null;
				int k = target.indexOf("=");
				if (k == -1) {
					try {
						Object obj = element.selectSingleNode(target);
						if (obj instanceof Element) {
							Element cellvalue = (Element) obj;
							if (cellvalue != null) {
								value = cellvalue.getText();
								return value;
							}

						}
					} catch (RuntimeException e) {

					}
				}
			}
		}
		return null;
	}

	public String getCellValue(int rownumber)
	{
		if (doc != null) {
			List<Node> nodes = doc.selectNodes( objectpath);
			if (rownumber < 0 || rownumber >= nodes.size()) {
				System.out.println(" cellvalue 2" + objectpath);
				return "row " + rownumber;
			}
			Element element = (Element) nodes.get(rownumber);
			String value = getCellValue(element);
			return value;
		} else
			return "";
	}

	public int getDatarowcount()
	{
		return datarowcount;
	}

	public double getMinHeight(int row)
	{
		Dimension dim = getTextSize(row);
		if (this.getMyHeight() < 1)
			return dim.height;
		else
			return this.getMyHeight();
	}

	public double getMinWidth(int row)
	{
		Dimension dim = getTextSize(row);
		if (this.getMyWidth() < 1)
			//return dim.width;
			return 100;
		else
			return this.getMyWidth();
	}

	public String getObjectpath()
	{
		return objectpath;
	}

	@Override
	public JComponent getPanelObject(Document adoc)
	{
		return getPanelObject(adoc, 0);
	}

	@Override
	public JComponent getPanelObject(Document adoc, int row)
	{
		doc = adoc;
		InputPanel alabel = new InputPanel(this, row, getTargets());
	
		formatComponent(alabel);
		alabel.setText(getCellValue(doc, row));
		return alabel;
	}

	public String getTargets()
	{
		String tgt = "";
		for (String tg : targets) {
			tgt += ":" + tg;
		}
		return tgt;
	}

	@Override
	public Dimension getTextSize()
	{
		return getTextSize(0);
	}

	public Dimension getTextSize(int row)
	{
		Dimension dim = new Dimension(10, 10);
		if (row < 0 || row > datarowcount)
			return dim;
		JLabel dlabel = new JLabel();
		fm = dlabel.getFontMetrics(getPFont());
		int lineheight = fm.getHeight() + this.getInterblockspacing();
		int numberoflines = 1;
		String value = getCellValue(row);
		if(value==null) value="WWW";
		int length = fm.stringWidth(value);
		int textheight = numberoflines * lineheight;
		int textwidth = length+40;//paul fix
		int sheight = textheight + 2 * this.getPadding();
		int swidth = textwidth + 2 * this.getPadding();
		if (isHorizontal()) {
			return new Dimension(swidth, sheight);
		} else {
			return new Dimension(sheight, swidth);
		}

	}

	@Override
	public boolean hasContent()
	{
		return true;
	}

	public void setDatarowcount(int datarowcount)
	{
		this.datarowcount = datarowcount;
	}

	public void setObjectpath(String objectpath)
	{
		this.objectpath = objectpath;
	}

	public void setObjectPath()
	{
		objectpath = null;
		jcertObject container = this.getParent();
		if (container.getObjecttype() != null)
			objectpath = container.getObjecttype();
		while (container.getParent() != null) {
			container = container.getParent();
			String objtype = container.getObjecttype();
			if (objtype != null) {
				if (objectpath == null) {
					objectpath = container.getObjecttype();
				} else if (!objectpath.startsWith("/")) {
					if (!objectpath.endsWith(objtype)) {
						objectpath = container.getObjecttype() + "/"
								+ objectpath;
					}
				} else
					objectpath = container.getObjecttype();
			}
		}
		String path = objectpath;
		if (objectpath != null) {
			if (!objectpath.startsWith("//")) {
				if (!objectpath.startsWith("/"))
					path = "//" + objectpath;
				else
					path = "/" + objectpath;
				objectpath = path;
			}
		}
	}

	@Override
	public String toString()
	{
		return " target=" + getTargets();
	}

	public void updateValue(int rownumber, String newvalue)
	{

		if (doc != null) {
			List<Node> nodes = doc.selectNodes(objectpath);
			if (rownumber < 0 || rownumber >= nodes.size()) {
				System.out.println(" cellvalue 3" + objectpath);
				return;
			}
			Element element = (Element) nodes.get(rownumber);

			for (String target : targets) {
				String value = null;
				int k = target.indexOf("=");
				if (k == -1) {
					Object obj = element.selectSingleNode(target);
					if (obj instanceof Element) {

						Element cellvalue = (Element) obj;
						if (cellvalue != null) {
							cellvalue.setText(newvalue);
						}
					} else {
						Node newnode = element.addElement(target);
						newnode.setText(newvalue);
					}

				}
			}
		}

	}

	public boolean isTableCell() {
		// TODO Auto-generated method stub
		return false;
	}
	


}