package pe.com.tumi.common.controller;

import java.util.*;
import java.text.*;
import java.sql.Timestamp;
public class Prueba {
 public static void main(String[] args) {
 try {  String str_date="11/12/2012 20";
  DateFormat formatter ; 
 Date date ; 
  formatter = new SimpleDateFormat("dd/MM/yyyy hh");
  
   date = (Date)formatter.parse(str_date); 
   java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
   
   System.out.println("Today is " +timeStampDate);
   
   Prueba p = new Prueba();
   System.out.println("La hora es: "+ p.obtieneHora(timeStampDate));
   System.out.println("Mes : "+ p.obtieneMesCadena(new Date()));
   System.out.println("Mes Integer : "+ new Integer("2013"+p.obtieneMesCadena(new Date())));
   
   
   
 } catch (ParseException e)
 {System.out.println("Exception :"+e);  }  
//  
//   Prueba p = new Prueba();
//   Date d = new Date();
//   System.out.println("dia hoy:::" +d);
//  // Date date = p.sumarFechasDias(d, 1);
// //  System.out.println("date:::" +date);
//}
 
 }
 
 public static String  obtieneMesCadena(Date date){
		
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("MM");
		String mes = sdf.format(date);
		return mes;
	}
 
 public Date sumarFechasDias(Date fch, int dias) {
     Calendar cal = new GregorianCalendar();
     cal.setTimeInMillis(fch.getTime());
     cal.add(Calendar.DATE, dias);
     return new Date(cal.getTimeInMillis());
 }
 
 public Integer obtieneHora(Date date){
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
}   