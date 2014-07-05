package pe.com.tumi.credito.socio.captacion.domain;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

public class TimePicker {
	private ArrayList<SelectItem> hora = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> minutos = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> segundos = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> periodicidad = new ArrayList<SelectItem>();
	  
    public TimePicker() {
        this.hora = new ArrayList<SelectItem>();   
        for(int i=0; i<25 ; i++){
        	String strHora=""+i;
        	if(strHora.length()==1){
        		strHora="0"+strHora;
        	}
        	this.hora.add(new SelectItem(strHora,strHora));
        }
        
        this.minutos = new ArrayList<SelectItem>();   
        for(int i=0; i<60 ; i++){
        	String strMinutos=""+i;
        	if(strMinutos.length()==1){
        		strMinutos="0"+strMinutos;
        	}
        	this.minutos.add(new SelectItem(strMinutos,strMinutos));
        } 
        
        this.segundos = new ArrayList<SelectItem>();   
        for(int i=0; i<60 ; i++){
        	String strSegundos=""+i;
        	if(strSegundos.length()==1){
        		strSegundos="0"+strSegundos;
        	}
        	this.segundos.add(new SelectItem(strSegundos,strSegundos));
        }
        
        int multiplo = 18;
        this.periodicidad = new ArrayList<SelectItem>();   
        for(int i=3; i<=multiplo ; i++){
        	if (esMultiplo(multiplo,i)){
        		this.periodicidad.add(new SelectItem(i));
        	}
        }
    }
    
    public static boolean esMultiplo(int n1,int n2){
    	if (n1%n2==0)
    		return true;
    	else
    		return false;
    }
    
    public ArrayList<SelectItem> getHora() {   
        return this.hora;   
    } 
    
    public ArrayList<SelectItem> getMinutos() {   
        return this.minutos;   
    } 
    
    public ArrayList<SelectItem> getSegundos() {   
        return this.segundos;   
    }
    
    public ArrayList<SelectItem> getPeriodicidad() {   
        return this.periodicidad;   
    } 
}
