package pe.com.tumi.common.util;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjusteMonto {

	public static BigDecimal obtenerMontoAjustado(BigDecimal bdMonto) throws Exception{
		Integer parteEntera = bdMonto.intValue();
		Integer intDecimales = decimales(bdMonto);
		
		Integer modDecimales = intDecimales%10;
		
		if(modDecimales>0 && modDecimales <=2){
			intDecimales = (intDecimales/10)*10;
		}
		if(modDecimales>2 && modDecimales <=7){
			intDecimales = ((intDecimales/10)*10)+5;
		}
		if(modDecimales>7 && modDecimales <=9){
			intDecimales = ((intDecimales/10)*10)+10;
			if(intDecimales.intValue()==100){
				parteEntera = parteEntera + 1;
				intDecimales = 0;
			}
		}
		
		BigDecimal bdMontoAjustado = new BigDecimal(parteEntera+"."+intDecimales);
		bdMontoAjustado.setScale(2);
		return bdMontoAjustado;
	}
	
	 private static BigDecimal redondear2DecimalesArriba(BigDecimal d) {
		 int scale = 2; 
		 int mode = BigDecimal.ROUND_UP;
		 return d.setScale(scale, mode);
	}
	 
	 private static Integer decimales(BigDecimal numero) throws Exception{
		 String strNumero = ""+redondear2DecimalesArriba(numero);
		 String strPartes[] = strNumero.split("\\.");
		 if(strPartes.length<1){
			 return 0;
		 }
		 String parteDecimal = strPartes[1];
		 return Integer.parseInt(parteDecimal);
	 }
	 
	 public static String formatoFecha(Date date){
		 try{
			 Format formatter = new SimpleDateFormat("dd/MM/yy");
			 return formatter.format(date);
		 }catch(Exception e){
			 return "00/00/00";
		 }
	 }
}