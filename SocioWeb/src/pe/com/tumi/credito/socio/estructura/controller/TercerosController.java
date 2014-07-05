package pe.com.tumi.credito.socio.estructura.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.controller.SocioController;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.Persona;

public class TercerosController {
	protected   static Logger 			log = Logger.getLogger(TercerosController.class);
	private List<Terceros> listTerceros = null;
	private List<Terceros> listColumnCpto = null;
	private Integer intFrecuenciaTerceros;
	
	public TercerosController(){
		listColumnCpto = new ArrayList<Terceros>();
		listarTerceros();
		intFrecuenciaTerceros = 0;
	}
	
	public void listarDsctoTerceros(ActionEvent event){
		listarTerceros();
	}
	
	public void consultarTerceros(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.consultarTerceros-------------------------------------");
		listarTerceros();
	}
	
	public void listarTerceros(){
		log.info("-------------------------------------Debugging SocioController.listarTerceros-------------------------------------");
		List<Terceros> listaFilas = null;
		List<Terceros> listaColumnas = null;
		EstructuraFacadeLocal facade;
		Persona persona = null;
		
		try {
			SocioController socioController = (SocioController) getSessionBean("socioController");
			if(socioController!=null && socioController.getBeanSocioComp()!=null && 
					socioController.getBeanSocioComp().getPersona()!=null){
				persona = socioController.getBeanSocioComp().getPersona();
				log.info("persona.getDocumento().getStrNumeroIdentidad(): "+persona.getDocumento().getStrNumeroIdentidad());
				
				facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
				listaFilas = facade.getListaFilaTercerosPorDNI(persona.getDocumento().getStrNumeroIdentidad());
				listaColumnas = facade.getListaColumnaTercerosPorDNI(persona.getDocumento().getStrNumeroIdentidad());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if(listaFilas!=null && listaColumnas!=null){
			log.info("listaFilas.size: "+listaFilas.size());
			log.info("listaColumnas.size: "+listaColumnas.size());
			
			listColumnCpto = new ArrayList<Terceros>();
			
			listaFilas = filtrarPorPeriodoYMes(listaFilas);
			
			for(Terceros tercero : listaColumnas){
				int conta = 0;
				for(Terceros columna : listColumnCpto){
					if(columna.getStrCpto().equals(tercero.getStrCpto())){
						conta++;
						break;
					}
				}
				if(conta==0)listColumnCpto.add(tercero);
			}
			log.info("listColumnCpto.size: "+listColumnCpto.size());
			
			for(Terceros columna : listaColumnas){
				
				for(Terceros fila : listaFilas){
					log.info("fila.getId(): "+fila.getId());
					log.info("fila.getId().getIntPeriodo(): "+fila.getId().getIntPeriodo());
					log.info("fila.getId().getIntMes(): "+fila.getId().getIntMes());
					log.info("tercero.getId(): "+columna.getId());
					log.info("tercero.getId().getIntPeriodo(): "+columna.getId().getIntPeriodo());
					log.info("tercero.getId().getIntMes(): "+columna.getId().getIntMes());
					
					if(fila.getId().getIntPeriodo().equals(columna.getId().getIntPeriodo()) &&
							fila.getId().getIntMes().equals(columna.getId().getIntMes())){
						log.info("tercero.getIntMonto(): "+columna.getIntMonto());
						
						if(fila.getLsMontos()==null){
							fila.setLsMontos(new String[1]);
						}else{
							String values[] = new String[fila.getLsMontos().length+1];
							for(int i=0; i<fila.getLsMontos().length; i++){
								values[i] = fila.getLsMontos()[i];
							}
							fila.setLsMontos(values);
						}
						/*log.info("fila.getLsMontos().length: "+fila.getLsMontos().length);
						BigDecimal aDecimal = new BigDecimal(columna.getIntMonto());  
						BigDecimal bdMonto = aDecimal.setScale(2, aDecimal.ROUND_HALF_UP);  
						String strMonto = bdMonto.toString();
						*/
						fila.getLsMontos()[fila.getLsMontos().length-1]=""+columna.getIntMonto();
					}
				}
			}
			
			setListTerceros(listaFilas);
		}
	}
	
	public List<Terceros> filtrarPorPeriodoYMes(List<Terceros> listaFilas){
		log.info("-------------------------------------Debugging SocioController.filtrarPorPeriodoYMes-------------------------------------");
		GregorianCalendar gcFechaReferencia = new GregorianCalendar();
		System.out.println("intFrecuenciaTerceros: "+intFrecuenciaTerceros);
		if(intFrecuenciaTerceros.equals(0)){
			return listaFilas;
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_TRESMESES)){
			gcFechaReferencia.add(Calendar.MONTH, -3);
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_SEISMESES)){
			gcFechaReferencia.add(Calendar.MONTH, -6);
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_UNANIO)){
			gcFechaReferencia.add(Calendar.MONTH, -12);
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_DOSANIOS)){
			gcFechaReferencia.add(Calendar.MONTH, -24);
		}
		
		List<Terceros> listFilas = new ArrayList<Terceros>();
		for(Terceros fila : listaFilas){
			if(Integer.valueOf(fila.getId().getIntPeriodo())>gcFechaReferencia.get(Calendar.YEAR)){
				listFilas.add(fila);
			}else{
				if(Integer.valueOf(fila.getId().getIntMes())>gcFechaReferencia.get(Calendar.MONTH)){
					listFilas.add(fila);
				}
			}
		}
		
		return listFilas;
	}
	
	//Metodos Utilitarios
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
	//Getters & Setters
	public List<Terceros> getListTerceros() {
		return listTerceros;
	}
	public void setListTerceros(List<Terceros> listTerceros) {
		this.listTerceros = listTerceros;
	}
	public List<Terceros> getListColumnCpto() {
		return listColumnCpto;
	}
	public void setListColumnCpto(List<Terceros> listColumnCpto) {
		this.listColumnCpto = listColumnCpto;
	}
	public Integer getIntFrecuenciaTerceros() {
		return intFrecuenciaTerceros;
	}
	public void setIntFrecuenciaTerceros(Integer intFrecuenciaTerceros) {
		this.intFrecuenciaTerceros = intFrecuenciaTerceros;
	}
}
