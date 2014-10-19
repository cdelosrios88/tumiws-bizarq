package pe.com.tumi.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
/**
 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
 * Funcionalidad: Utilitario donde se encontraran las conversiones de fechas y formatos de las mismas usado a lo largo del 
 * desarrollo del ERP El Tumi.
 * @author jchavez
 * @version 1.0
 */
public class MyUtilFormatoFecha {
	protected static Logger log = Logger.getLogger(MyUtil.class);
	
	//Para su uso en cualquier clase ---> MyUtilFormatoFecha.nombre_metodo(valores de ingreso);
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
	 * Funcionalidad: Método que retorna el primer día del mes de un periodo ingresado
	 * @author jchavez
	 * @version 1.0
	 * @param intPeriodo Formato: yyyymm
	 * @return cal.getTime() - primer día del mes.
	 */
	public static Date getPrimerDiaDelMes(Integer intPeriodo) {
		Calendar cal = Calendar.getInstance();
		//seteo el mes y año del periodo soliictado
		cal.set(Calendar.YEAR, Integer.valueOf(intPeriodo.toString().substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(intPeriodo.toString().substring(4, 6))-1);
		//
		cal.set(cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.getActualMinimum(Calendar.DAY_OF_MONTH),
		cal.getMinimum(Calendar.HOUR_OF_DAY),
		cal.getMinimum(Calendar.MINUTE),
		cal.getMinimum(Calendar.SECOND));
		return cal.getTime();
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
	 * Funcionalidad: Método que retorna el último día del mes de un periodo ingresado
	 * @author jchavez
	 * @version 1.0
	 * @param intPeriodo
	 * @return cal.getTime() - último día del mes.
	 */
	public static Date getUltimoDiaDelMes(Integer intPeriodo) {
		Calendar cal = Calendar.getInstance();
		//seteo el mes y año del periodo soliictado
		cal.set(Calendar.YEAR, Integer.valueOf(intPeriodo.toString().substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(intPeriodo.toString().substring(4, 6))-1);
		//
		cal.set(cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.getActualMaximum(Calendar.DAY_OF_MONTH),
		cal.getMaximum(Calendar.HOUR_OF_DAY),
		cal.getMaximum(Calendar.MINUTE),
		cal.getMaximum(Calendar.SECOND));
		return cal.getTime();
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
	 * Funcionalidad: Método que retorna el primer día del mes en formato Date de una fecha ingresada.
	 * @author jchavez
	 * @version 1.0 
	 * @param intPeriodo
	 * @return fecha.getTime() - primer día del mes.
	 */
	public static Date getPrimerDiaDelMes(Calendar fecha) {
		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMinimum(Calendar.DAY_OF_MONTH),
				fecha.getMaximum(Calendar.HOUR_OF_DAY),
				fecha.getMaximum(Calendar.MINUTE),
				fecha.getMaximum(Calendar.SECOND));

		return fecha.getTime();
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
	 * Funcionalidad: Método que retorna el último día del mes en formato Date de una fecha ingresada.
	 * @author jchavez
	 * @version 1.0 
	 * @param intPeriodo
	 * @return fecha.getTime() - último día del mes.
	 */
	public static Date getUltimoDiaDelMes(Calendar fecha) {
		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
				fecha.getMaximum(Calendar.HOUR_OF_DAY),
				fecha.getMaximum(Calendar.MINUTE),
				fecha.getMaximum(Calendar.SECOND));

		return fecha.getTime();
	}	
	
	/**
	 * Método que retorna la fecha actual en formato Timestamp
	 * @return
	 */
	public static Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	/**
	 * Método que retorna los dias entre 2 fechas ingresadas
	 * @param dtFechaInicio
	 * @param dtFechaFin
	 * @return
	 * @throws Exception
	 */
	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
	} 
	
	/**
	 * Método que convierte una fecha de formato Timestamp a Date
	 * @param timestamp
	 * @return
	 */
	public static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
	 * Funcionalidad: Método que retorna una fecha en formato TimeStamp ingresada, a un string con formato 'yyyymm'.
	 * @author jchavez
	 * @version 1.0 
	 * @param tsFecha
	 * @return strPeriodo - Periodo en formato string 'yyyyMM'.
	 */
	public static String convertirTimestampAStringPeriodo(Timestamp tsFecha){
		log.info("Timestamp ingresado: "+tsFecha);
		Date date = new Date(tsFecha.getTime());
		String strPeriodo = new SimpleDateFormat("yyyyMM").format(date);
		log.info("Periodo: "+strPeriodo);
		return strPeriodo;
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
	 * Funcionalidad: Método que retorna un mes en texto
	 * @author jchavez
	 * @version 1.0 
	 * @param tsFechaInicial
	 * @param tsFechaFinal
	 * @return String - nombre del mes.
	 */
	public static String mesesEnTexto(int iMes){
		  switch (iMes){
			case 1:
				return "Enero";
			case 2:
				return "Febrero";
			case 3:
				return "Marzo";	
			case 4:
				return "Abril";
			case 5:
				return "Mayo";
			case 6:
				return "Junio";
			case 7:
				return "Julio";
			case 8:
				return "Agosto";
			case 9:
				return "Septiembre";
			case 10:
				return "Octubre";
			case 11:
				return "Noviembre";
			case 12:
				return "Diciembre";
			default:
				return "ERROR EN LA OBTENCION DE MES";
		  }
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 18.07.2014 / 
	 * Funcionalidad: Método que retorna una fecha en formato TimeStamp ingresada, a un string con formato 'yyyymm'
	 * @author jchavez
	 * @version 1.0 
	 * @param tsFechaInicial
	 * @param tsFechaFinal
	 * @return lstPeriodos - lista de periodos obtenidas.
	 */
	public static List<String> obtenerPeriodosEntreFechas(Timestamp tsFechaInicial, Timestamp tsFechaFinal){
		List<String> lstPeriodos = new ArrayList<String>();
		//1° Damos formato a las fechas ingresadas
		String strFechaInicial = convertirTimestampAStringPeriodo(tsFechaInicial);
		String strFechaFinal = convertirTimestampAStringPeriodo(tsFechaFinal);
		
		for (int i = (Integer.parseInt(strFechaFinal)); i >= (Integer.parseInt(strFechaInicial)); i--) {
			Integer anio = Integer.parseInt((String.valueOf(i).substring(0, 4)));
			Integer mes = Integer.parseInt((String.valueOf(i).substring(4, 6)));
			if (mes.compareTo(1)>=0 && mes.compareTo(12)<=0) {
				lstPeriodos.add(anio+""+((mes<10)?"0"+mes:mes));
			}else if (mes.compareTo(99)==0) {
				anio = anio-1;
			}
		}
		return lstPeriodos;
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 19.07.2014 / 
	 * Funcionalidad: Método que retorna el periodo siguiente en base a un periodo ingresado en formato String.
	 * @author jchavez
	 * @version 1.0 
	 * @param strPeriodoActual
	 * @return intPeriodoSiguiente - Periodo siguiente al ingresado.
	 */
	public static Integer obtenerPeriodoSiguiente(String strPeriodoActual){
		Integer intPeriodoSiguiente = null;
		Integer anio = Integer.parseInt((strPeriodoActual.substring(0, 4)));
		Integer mes = Integer.parseInt((strPeriodoActual.substring(4, 6)));
		if (mes.compareTo(12)==0) {
			intPeriodoSiguiente = Integer.parseInt((anio+1)+"01");
		}else {				
			if ((new Integer(mes+1)).compareTo(10)==-1) {
				intPeriodoSiguiente = Integer.parseInt(anio+"0"+(mes+1));
			}else {
				intPeriodoSiguiente = Integer.parseInt(anio+""+(mes+1));
			}			
		}
		return intPeriodoSiguiente;
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 20.07.2014 / 
	 * Funcionalidad: Método que retorna el periodo anterior en base a un periodo ingresado en formato String.
	 * @author jchavez
	 * @version 1.0 
	 * @param strPeriodoActual
	 * @return intPeriodoAnterior - Periodo anterior al ingresado.
	 */
	public static Integer obtenerPeriodoAnterior(String strPeriodoActual){
		Integer intPeriodoAnterior = null;
		Integer anio = Integer.parseInt((strPeriodoActual.substring(0, 4)));
		Integer mes = Integer.parseInt((strPeriodoActual.substring(4, 6)));
		if (mes.compareTo(1)==0) {
			intPeriodoAnterior = Integer.parseInt((anio-1)+"12");
		}else {				
			if ((new Integer(mes-1)).compareTo(10)==-1) {
				intPeriodoAnterior = Integer.parseInt(anio+"0"+(mes-1));
			}else {
				intPeriodoAnterior = Integer.parseInt(anio+""+(mes-1));
			}
		}
		return intPeriodoAnterior;
	}
}
