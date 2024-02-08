package org.lerot.MyCert;

import org.dom4j.Element;
import org.lerot.MyCert.layout.hgroup;
import org.lerot.MyCert.layout.jcertPanel;
import org.lerot.MyCert.layout.DisplayObject;

public class styleblock extends PanelStyleObject
{

	/*public styleblock(org.lerot.jcert.DisplayObject displayObject, Element element)
	{
		super(displayObject);
		//container.copyFormat(this);
		// System.out.println(" creating styleblock in "+container.getName());
		loadAttributes(element);
	}*/

	

	



	public styleblock(DisplayObject displayObject, Element element) {
		super(displayObject);
		loadAttributes(element);
	}


	public jcertPanel getDisplayObject()
	{
		return null;
	}

	@Override
	public boolean hasContent()
	{
		return false;
	}

	public void fillTemplate(Element root)
	{

	}


	@Override
	public int countDisplayObjects()
	{
		return 0;
	}


	public double getMinWidth()
	{
		return 0;
	}

	public double getMinHeight()
	{
		return 0;
	}


	public jcertPanel getPanelObject() 
	{
		return null;
	}

}