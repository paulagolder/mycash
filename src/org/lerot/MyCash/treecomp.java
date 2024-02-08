package org.lerot.MyCert;

import java.util.Comparator;

class TreeComp implements Comparator<documentTemplate>{
	 


	@Override
	public int compare(documentTemplate temp0, documentTemplate temp1) {
		// TODO Auto-generated method stub
		return temp0.getAame().compareTo(temp1.getAame());
	}
     
}
