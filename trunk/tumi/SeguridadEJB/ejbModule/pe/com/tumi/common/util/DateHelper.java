package pe.com.tumi.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {

	public static Date getFechaActual(){
		Calendar calendar = new GregorianCalendar();
		return calendar.getTime();
	}

	/**
	 * @author TaT!
	 * Devuelve la diferencia en dias de dos fechas ingresadas
	 * @param fecha final
	 * @param fecha inicial
	 * @return dias
	 * */
	public static double fechasDiferenciaEnDias(Date fechaFinal, Date fechaInicial) {
		GregorianCalendar ci= new GregorianCalendar() ;
		if (fechaInicial!=null){
			ci.setTime(fechaInicial);
		}else{
			ci.setTime(Constante.FECHA_INICIO);
		}
		GregorianCalendar cf= new GregorianCalendar() ;;
		cf.setTime(fechaFinal);
		double from = ci.getTime().getTime(); 
		double to = cf.getTime().getTime();
		double difference = to - from;
		double days = difference/(1000*60*60*24);
		return days;
		}
	}
