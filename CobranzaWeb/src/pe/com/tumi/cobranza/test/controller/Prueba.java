package pe.com.tumi.cobranza.test.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Prueba {

	public static void main(String arg[]){
		 List list = new ArrayList();
		 
		 list.add(new Integer(10));
		 list.add(new Integer(9));
		 list.add(new Integer(12));
		 list.add(new Integer(5));
			 
	     Collections.sort(list);
	     for (int i = 0; i < list.size(); i++) {
	    	 System.out.println("Numero:" + list.get(i));
		}
	}
	
}
