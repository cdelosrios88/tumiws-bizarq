package pe.com.tumi.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import pe.com.tumi.seguridad.domain.OpcionMenu;

public class ItemHelper {
	
	private static final SelectItem opcionSeleccionar = new SelectItem(Constante.NO_OPTION_SELECTED,Constante.OPTION_SELECT);
	private static Logger log = Logger.getLogger(ItemHelper.class); 
	
	public static SelectItem[] listToSelect(List list, String label) {
		SelectItem[] items = new SelectItem[list.size()];
		int x = 0;
		try {
			Iterator listIterator = list.iterator();
			while(listIterator.hasNext()){
				Object object = listIterator.next();
				items[x] = new SelectItem(object,BeanUtils.getSimpleProperty(object, label));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(e);
		}
		x++;
		return items;
	}
	
	public static List listToListOfSelectItems(List list, String label) {
		List items = new ArrayList();
		try {
			Iterator listIterator = list.iterator();
			while(listIterator.hasNext()){
				Object object = listIterator.next();
				items.add(new SelectItem(object,BeanUtils.getSimpleProperty(object, label)));
			}			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(e);
		}
		return items;
	}
	/**
	 * Retorna una lista con objetos <b>SelectItem</b>. El value será el segundo parámetro enviado y 
	 * el label será el tercer parámetro.
	 * @param list lista con objetos SelectItem
	 * @param value atributo del objeto que servirá como value del SelectItem
	 * @param label atributo del objeto que servirá como label del SelectItem
	 * @return lista de objetos <b>SelectItem</b>
	 */
	public static List listToListOfSelectItems(List list, String value,String label) {
		List items = new ArrayList();
		try {
			Iterator listIterator = list.iterator();
			while(listIterator.hasNext()){
				Object object = listIterator.next();
				items.add(new SelectItem(BeanUtils.getSimpleProperty(object, value),BeanUtils.getSimpleProperty(object, label)));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(e);
		}
		return items;
	}
	
	public static List listToListOfSelectItemsSelectOptionAddedAndSorted(Collection list, String value,String label) {
		List items = new ArrayList();
		items.add(opcionSeleccionar);
		try {
			Iterator listIterator = list.iterator();
			while(listIterator.hasNext()){
				Object object = listIterator.next();
				items.add(new SelectItem(BeanUtils.getSimpleProperty(object, value),BeanUtils.getSimpleProperty(object, label)));
			}			
			items = sortSelectItemList(items);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(e);
		}
		return items;
	}
	
	public static SelectItem[] listToListArrayOfSelectItems(Collection list, String value,String label) {
		SelectItem[] items = new SelectItem[list.size()];
		try {
			int cont = 0;
			Iterator listIterator = list.iterator();
			while(listIterator.hasNext()){
				Object object = listIterator.next();
				items[cont] = new SelectItem(BeanUtils.getSimpleProperty(object, value).toString(),BeanUtils.getSimpleProperty(object, label));
				cont++;
			}				
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(e);
		}
		return items;
	}
	
	public static List selectListToObjectList(List list){
		List result = new ArrayList();
		Iterator listIterator = list.iterator();
		while(listIterator.hasNext()){
			SelectItem item = (SelectItem) listIterator.next();
			result.add(item.getValue());
		}		
		return result;
	}
	
	public static List selectListToObjectList(List list, Class clase){
		List result = new ArrayList();
		Iterator listIterator = list.iterator();
		while(listIterator.hasNext()){
			SelectItem item = (SelectItem) listIterator.next();
			result.add(item.getValue());
		}			
		return result;
	}
	/**
	 * Devuelve List<SelectItem> a partir de un objecto {@link #Set}
	 * @param list
	 * @param label
	 * @return
	 */
	public static List setToListOfSelectItems(Set list, String label) {
		List items = new ArrayList();
		try {
			Iterator iteratorSet = list.iterator();
			while(iteratorSet.hasNext()){
				Object object = iteratorSet.next();
				items.add(new SelectItem(object,BeanUtils.getSimpleProperty(object, label)));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(e);
		}
		return items;
	}
	public static List setToListOfSelectItems(Set list, String id, String label) {
		List items = new ArrayList();
		try {
			Iterator iteratorSet = list.iterator();
			while(iteratorSet.hasNext()){
				Object object = iteratorSet.next();
				items.add(new SelectItem(BeanUtils.getSimpleProperty(object, id),BeanUtils.getSimpleProperty(object, label)));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.debug(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(e);
		}
		return items;
	}
	/**
	 * Devuelve un List en base al Set enviado como parámetro
	 * @param list objeto set que será convertido a list
	 * @return lista
	 */
	public static List setToList(Set list) {
		List items = new ArrayList();
		
		try {
			Iterator iteratorSet = list.iterator();
			while(iteratorSet.hasNext()){
				Object object = iteratorSet.next();
				items.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
		}
		return items;
	}
	
	/**
	 * Ordena una lista, que contiene objetos SelectItem, según el label de cada SelectItem.<br/>
	 * (El label y el value deben ser del tipo String)
	 * @param lista List de SelectItems
	 */
	public static List sortSelectItemList(List lista){
		Iterator iterador = lista.iterator();
		List ids = new ArrayList();
		Hashtable hashContent = new Hashtable();
		while(iterador.hasNext()){
			SelectItem item = (SelectItem)iterador.next();
			ids.add(item.getLabel());
			hashContent.put(item.getLabel(), item.getValue());
		}
		Collections.sort(ids);
		lista = new ArrayList();
		
		SelectItem newItem = null;
		for(int i=0;i<ids.size();i++){
			newItem = new SelectItem(hashContent.get(ids.get(i)),(String)ids.get(i));
			
			lista.add(newItem);
		}
		return lista;
	}
	
	/**
	 * Ordena una lista, que contiene objetos SelectItem, según el label de cada SelectItem.<br/>
	 * (El label y el value deben ser del tipo String)
	 * @param lista List de SelectItems
	 */
	public static List sortSelectItemListByName(List lista){
		Iterator iterador = lista.iterator();
		List ids = new ArrayList();
		Hashtable hashContent = new Hashtable();
		while(iterador.hasNext()){
			SelectItem item = (SelectItem)iterador.next();
			ids.add(item.getLabel().toUpperCase());
			hashContent.put(item.getLabel().toUpperCase(), item.getValue());
		}
		Collections.sort(ids);
		lista = new ArrayList();
		for(int i=0;i<ids.size();i++){
			lista.add(new SelectItem(hashContent.get(ids.get(i)),(String)ids.get(i)));
		}
		return lista;
	}

	/**
	 * Devuelve un hashtable cuya llave es el label del SelectItem y el valor es el objeto
	 * @param lista
	 * @return
	 */
	public static Hashtable list2HashTable(List lista){
		Hashtable tmp = new Hashtable();
		Iterator listaIterator = lista.iterator();
		while(listaIterator.hasNext()){
			OpcionMenu item = (OpcionMenu) listaIterator.next();
			tmp.put(item.getNombre(), item);
		}
		return tmp;
	}
	/**
	 * Retorna la fecha del sistema
	 * @return un objeto Date
	 * @throws ServiceException
	 */
	public static Date getCurrentDate() throws ServiceException {
		try {
			return new GregorianCalendar().getTime();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**
	 * Devuelve la traza de error de una excepción
	 * @param e Exception
	 * @return cadena de texto con la traza del error
	 */
	public static String getStackTrace(Exception e){
		StringWriter sr = new StringWriter(0);
        PrintWriter pw = new PrintWriter(sr,true);
        e.printStackTrace(pw);
        return sr.toString();
	}
}
