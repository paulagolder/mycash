package org.lerot.MyCert.layout;

import org.dom4j.Document;

public class CertificatePanel extends VerticalPanel
{
   
	private static final long serialVersionUID = 1L;
    Document sourcedocument;
	
	public CertificatePanel(Document doc,String name)
	{
		super(name);
		sourcedocument = doc;

	}
}
