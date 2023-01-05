package org.lerot.MyCert;

import java.io.File;

public class XMLFilter implements java.io.FileFilter
{
	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;
		String name = f.getName().toLowerCase();
		return name.endsWith(".xml");
	}
}
