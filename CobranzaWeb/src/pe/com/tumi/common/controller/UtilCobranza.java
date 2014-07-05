package pe.com.tumi.common.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

public class UtilCobranza {

	public static final Timestamp  convierteStringATimestamp(String str_date){
		DateFormat formatter ; 
		Date date ; 
		java.sql.Timestamp timeStampDate = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		try{
		    date = (Date)formatter.parse(str_date); 
		    timeStampDate = new  Timestamp(date.getTime());
		    System.out.println("Today is " +timeStampDate);
		}
		catch (ParseException e){
			System.out.println("Exception :"+e);  
		}  
		 
		return timeStampDate;
	 }
	
	public static final String convierteDateAString(Date date){
		
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(date);
		
		return fecha;
	}
	
//	public static final Integer obtieneHora(Date date){
//		String  hora ="";
//		     String [] horaTarde ={"12","13","14","15","16","17","18","19","20","21","22","23"};
//		
//			java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("hh");
//			java.text.SimpleDateFormat turno =new java.text.SimpleDateFormat("a");
//			String turnos = turno.format(date);
//			
//			if (turnos.toUpperCase().equals("PM")){
//				for (int i = 0; i < horaTarde.length; i++) {
//					Integer horaPM = new Integer(horaTarde[i]);
//					hora = sdf.format(date);
//					if (new Integer[i].equals(hora)){
//						hora = horaPM.toString();
//					}
//				}
//		    }
//			
//			return new Integer(hora);
//	}
	
	 public static final Integer obtieneHora(Date date){
			String  hora ="";
			     String [] horaTarde ={"13","14","15","16","17","18","19","20","21","22","23"};
			
				java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("hh");
				java.text.SimpleDateFormat turno =new java.text.SimpleDateFormat("a");
				String turnos = turno.format(date);
				System.out.println("Turno :"+turnos.toUpperCase());
				if (turnos.toUpperCase().equals("PM")){
					for (int i = 0; i < horaTarde.length; i++) {
						hora = sdf.format(date);
						if ((i+1) == (new Integer(hora).intValue())){
							hora = new Integer(horaTarde[i]).toString();
							break;
						}
					}
			    }
				else{
					    hora = sdf.format(date);
				}
				return new Integer(hora);
	}
	  
	
	public static final Integer obtieneMinuto(Date date){
		
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("mm");
		String minuto = sdf.format(date);
		return new Integer(minuto);
	}
	
	public static final Integer obtieneMes(Date date){
		
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("MM");
		String mes = sdf.format(date);
		return new Integer(mes);
	}
	
   public static String  obtieneAnio(Date date){
		
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy");
		String anio = sdf.format(date);
		return anio;
	}
	
   public static final Timestamp obtieneFechaActualEnTimesTamp(){
		
	    java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
		java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
		utilDate = new java.util.Date(System.currentTimeMillis());
		java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime());
		java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate1.getTime());
		System.out.println(ts);
		ts = new java.sql.Timestamp(sqlDate2.getTime());
	
	 return ts;
	} 
   
   public static final String obtieneMesCadena(Date date){
 		
 		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("MM");
 		String minuto = sdf.format(date);
 		return minuto;
 	}
   
   
   public static final Integer obtieneMinNumero(List<Integer> datos){
	   int n = 0,menor = 99999;
	   for (Integer numero : datos) {
			System.out.println("Ingresa un numero"+numero);
			n = numero;
			if(n < menor){
			   menor = n;
			}
			System.out.println("El numero menor es :" + menor);
		}
	   
       return menor;
   }
   
   
   public static final boolean esMenorRangoFechas(Date fechaFin, Date fecha)
   {
	   boolean result = false;
	   java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
       String strFecha = sdf.format(fecha);
       
       java.text.SimpleDateFormat sdf2=new java.text.SimpleDateFormat("dd/MM/yyyy");
       String strFechaFin = sdf2.format(fechaFin); 
       if ((fecha.before(fechaFin))  || strFecha.equals(strFechaFin)){
		   result = true;
	   }
	  return result;
   }
   
   public static final boolean esMayorRangoFechas(Date fechaIni,Date fecha)
   {
	   boolean result = false;
	   
	   java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
       String strFecha = sdf.format(fecha);
       
       java.text.SimpleDateFormat sdf1=new java.text.SimpleDateFormat("dd/MM/yyyy");
       String strFechaIni = sdf1.format(fechaIni);
       
       if ( fecha.after(fechaIni) || (strFecha.equals(strFechaIni))){
		   result = true;
	   }
	  return result;
   }
   
   public static final boolean esDentroRangoFechas(Date fechaIni, Date fechaFin, Date fecha){
	   boolean result = false;
	   
	    java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
	             String strFecha = sdf.format(fecha);
	    java.text.SimpleDateFormat sdf1=new java.text.SimpleDateFormat("dd/MM/yyyy");
	             String strFechaIni = sdf1.format(fechaIni);
	    java.text.SimpleDateFormat sdf2=new java.text.SimpleDateFormat("dd/MM/yyyy");
	             String strFechaFin = sdf2.format(fechaFin);
	   if ((fecha.before(fechaFin) && fecha.after(fechaIni)) || (strFecha.equals(strFechaIni) || strFecha.equals(strFechaFin))){
		   result = true;
	   }
	  return result;
   }
   
   public static final boolean esMayorOIgualFechaActual(Date fecha){
	   boolean result = false;
	   
	     Date fechaActual = new Date();
	    java.text.SimpleDateFormat sdf  = new java.text.SimpleDateFormat("dd/MM/yyyy");
	                   String strFecha  = sdf.format(fecha);
	    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd/MM/yyyy");
	                 String strFechaActual = sdf1.format(fechaActual);
	   
	   if (fecha.after(fechaActual) || strFecha.equals(strFechaActual)){
		   result = true;
	   }
	  return result;
   }
   public static final boolean esDistintoaFechaActual(Date fecha){
	   boolean result = false;
	   
	     Date fechaActual = new Date();
	    java.text.SimpleDateFormat sdf  = new java.text.SimpleDateFormat("dd/MM/yyyy");
	                   String strFecha  = sdf.format(fecha);
	    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd/MM/yyyy");
	                 String strFechaActual = sdf1.format(fechaActual);
	   
	   if (strFecha.equals(strFechaActual)){
		   result = true;
	   }
	  return result;
   }
   
   public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		return (int)(((dtFechaFin.getTime() - dtFechaInicio.getTime())+1) / (1000 * 60 * 60 * 24) );
	}
   
   public static Timestamp sumarUnDiaAFecha(Timestamp tsFechaInicio){
  	 java.sql.Timestamp timeStampUnDia = null;
  	 Date datte = new Date(tsFechaInicio.getTime());
  	 DateTime dt = new DateTime(datte);
  	 timeStampUnDia = new Timestamp(dt.plusDays(1).getMillis());
  	 return timeStampUnDia;
   }
   
   public static String  obtieneAnioCadena(Date date){
		
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy");
		String anio = sdf.format(date);
		return anio;
	}
   public static Timestamp obtenerUltimoDiaDelMesAnioPeriodo(Integer periodo){
  	 java.sql.Timestamp timeStampDate = null;    	
		String dateStop = periodo.toString();    	 
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		try{
			Date d2 = null;
			d2 = format.parse(dateStop);
			DateTime fecha = new DateTime(d2);
			DateTime fechaFinMes=fecha.dayOfMonth().withMaximumValue(); 
			timeStampDate = new Timestamp(fechaFinMes.getMillis());	 			
		} catch (Exception e) {
			e.printStackTrace();
		 }
  	 return timeStampDate;
   }
}
