package pe.com.tumi.seguridad.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import pe.com.tumi.seguridad.domain.OpcionMenu;

public class OpcionMenuConverter implements javax.faces.convert.Converter{

	//converter uses a HashMap for switching from selectItems to your objects  
	 private HashMap map;  
	   
	 public OpcionMenuConverter(List objekts) {  
	         map=new HashMap();  
	         Iterator objectIterator = objekts.iterator();
	         while(objectIterator.hasNext()){
	        	 OpcionMenu o = (OpcionMenu)objectIterator.next();
	        	 map.put(String.valueOf(o.getId()), o);
	         }
	 }
	   
	 public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {  
	         return map.get(string);  
	 }
	    
	 public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {  
	         if(obj instanceof OpcionMenu)   
	                 return String.valueOf(((OpcionMenu)obj).getId());  
	         return null;  
	 }
	

}