package pe.com.tumi.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.convert.Converter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ConvertidorMontos implements Converter{

	NumberFormat formato;
	
	public ConvertidorMontos(){
		super();
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		formato = new DecimalFormat("#,###.00",otherSymbols);
		
	}
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
 
		return null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String strMonto = "";
		
		if(value==null){
			return strMonto;
		}
		
		if(value instanceof BigDecimal){
			BigDecimal bdMonto = (BigDecimal)value;
			strMonto = formato.format(bdMonto); 
		}		
		
		return strMonto;
	}
}
