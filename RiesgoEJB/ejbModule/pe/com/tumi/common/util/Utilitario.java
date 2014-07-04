package pe.com.tumi.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utilitario {
	
	public Date obtieneUltimoDiaMes(Integer intMes, Integer intAnio){
		Date date = new Date();
		String strDate;
		DateFormat formatter;
		Calendar cal = Calendar.getInstance();
		try {
			if(intMes < 10){
				strDate = "01.0" + intMes + "." + intAnio;
			} else {
				strDate = "01." + intMes + "." + intAnio;
			}
			formatter = new SimpleDateFormat("dd.MM.yy");
			date = (Date) formatter.parse(strDate);
			cal.setTime(date);
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.getActualMaximum(Calendar.DAY_OF_MONTH),
					cal.getMaximum(Calendar.HOUR_OF_DAY),
					cal.getMinimum(Calendar.MINUTE),
					cal.getMinimum(Calendar.SECOND));
			date = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
}
