package pe.com.tumi.popup.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import pe.com.tumi.adminCuenta.controller.PerJuridicaController;
import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.popup.domain.CuentaBancaria;
import pe.com.tumi.popup.service.impl.CtaBancariaServiceImpl; 

public class CtaBancariaController extends GenericController{
	
	private 	CtaBancariaServiceImpl		ctaBancariaService;
	private 	String 						strNombCompleto = "000300199 - Yessica Margot Tucto Ricra";
	private 	Integer 					intCondicion;
	private 	String 						strRol = "Socio";
	private 	CuentaBancaria 				beanCtaBancaria = new CuentaBancaria();
	private 	List						beanListCtaBancaria = new ArrayList();
	
	public void addCtaBancaria(ActionEvent event){
		log.info("-------------------------------------Debugging addCtaBancaria-------------------------------------");
		ArrayList listCtaBancaria = new ArrayList();
		if(beanListCtaBancaria!=null){
			listCtaBancaria = (ArrayList)getBeanListCtaBancaria();
		}
		
		CuentaBancaria cta = getCtaBancariaVal();
		
		//Seteando datos al bean
		CuentaBancaria ctaban = new CuentaBancaria();
		ctaban.setIntIdPersona(cta.getIntIdPersona());
		ctaban.setIntIdCtaBancaria(cta.getIntIdCtaBancaria());
		ctaban.setIntIdBanco(cta.getIntIdBanco());
		ctaban.setIntIdTipoCuenta(cta.getIntIdTipoCuenta());
		ctaban.setIntIdMoneda(cta.getIntIdMoneda());
		ctaban.setIntAbono(cta.getIntAbono());
		ctaban.setIntDepositaCTS(cta.getIntDepositaCTS());
		ctaban.setIntCargos(cta.getIntCargos());
		ctaban.setStrNroCtaBancaria(cta.getStrNroCtaBancaria());
		ctaban.setIntIdEstado(cta.getIntIdEstado());
		ctaban.setStrObservacion(cta.getStrObservacion());
		
		listCtaBancaria.add(ctaban);
		setBeanListCtaBancaria(listCtaBancaria);
	}
	
	public CuentaBancaria getCtaBancariaVal(){
		//Verificando datos de entrada
		log.info("intIdCtaBancaria: "+getBeanCtaBancaria().getIntIdCtaBancaria());
		log.info("intIdBanco: "+getBeanCtaBancaria().getIntIdBanco());
		log.info("intIdTipoCuenta: "+getBeanCtaBancaria().getIntIdTipoCuenta());
		log.info("intIdMoneda: "+getBeanCtaBancaria().getIntIdMoneda());
		log.info("strRazonCuenta.length: "+getBeanCtaBancaria().getStrRazonCuenta().length);
		String strRazonCuenta[] = getBeanCtaBancaria().getStrRazonCuenta(); 
		for(int i=0; i<strRazonCuenta.length; i++){
			if(i==0){
				log.info("strRazonCuenta[0]: "+strRazonCuenta[0]);
				if(strRazonCuenta[0].equals("0")){
					getBeanCtaBancaria().setIntAbono(0);
				}else{
					getBeanCtaBancaria().setIntAbono(1);
				}
			}else if(i==1){
				log.info("strRazonCuenta[1]: "+strRazonCuenta[1]);
				if(strRazonCuenta[1].equals("0")){
					getBeanCtaBancaria().setIntDepositaCTS(0);
				}else{
					getBeanCtaBancaria().setIntDepositaCTS(1);
				}
			}else if(i==2){
				log.info("strRazonCuenta[2]: "+strRazonCuenta[2]);
				if(strRazonCuenta[2].equals("0")){
					getBeanCtaBancaria().setIntCargos(0);
				}else{
					getBeanCtaBancaria().setIntCargos(1);
				}
			}
		}
		log.info("strNroCtaBancaria: "+getBeanCtaBancaria().getStrNroCtaBancaria());
		log.info("intIdEstado: "+getBeanCtaBancaria().getIntIdEstado());
		log.info("strObservacion: "+getBeanCtaBancaria().getStrObservacion());
		
		return beanCtaBancaria;
	}

	public void grabarCtaBancaria(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging grabarCtaBancaria-------------------------------------");
		setCtaBancariaService(ctaBancariaService);
		
		log.info("intIdCtaBancaria: "+getBeanCtaBancaria().getIntIdCtaBancaria());
		log.info("intIdBanco: "+getBeanCtaBancaria().getIntIdBanco());
		log.info("intIdTipoCuenta: "+getBeanCtaBancaria().getIntIdTipoCuenta());
		log.info("intIdMoneda: "+getBeanCtaBancaria().getIntIdMoneda());
		log.info("strRazonCuenta.length: "+getBeanCtaBancaria().getStrRazonCuenta().length);
		String strRazonCuenta[] = getBeanCtaBancaria().getStrRazonCuenta(); 
		log.info("strRazonCuenta[0]: "+strRazonCuenta[0]);
		log.info("strRazonCuenta[1]: "+strRazonCuenta[1]);
		log.info("strRazonCuenta[2]: "+strRazonCuenta[2]);
		log.info("strNroCtaBancaria: "+getBeanCtaBancaria().getStrNroCtaBancaria());
		log.info("intIdEstado: "+getBeanCtaBancaria().getIntIdEstado());
		log.info("strObservacion: "+getBeanCtaBancaria().getStrObservacion());
		
		if(strRazonCuenta[0].equals("0")){
			getBeanCtaBancaria().setIntAbono(0);
		}else{
			getBeanCtaBancaria().setIntAbono(1);
		}
		
		if(strRazonCuenta[1].equals("0")){
			getBeanCtaBancaria().setIntDepositaCTS(0);
		}else{
			getBeanCtaBancaria().setIntDepositaCTS(1);
		}
		
		if(strRazonCuenta[2].equals("0")){
			getBeanCtaBancaria().setIntCargos(0);
		}else{
			getBeanCtaBancaria().setIntCargos(1);
		}
		
		getCtaBancariaService().grabarCtaBancaria(getBeanCtaBancaria());
		log.info("Se guardó Cta. Bancaria con intIdCtaBancariaOut: " + getBeanCtaBancaria().getIntIdCtaBancariaOut());
		
		//Agregar a la Lista
		CuentaBancaria cta = new CuentaBancaria();
		cta.setIntIdPersona(getBeanCtaBancaria().getIntIdPersona());
		cta.setIntIdCtaBancaria(getBeanCtaBancaria().getIntIdCtaBancariaOut());
		cta.setIntIdBanco(getBeanCtaBancaria().getIntIdBanco());
		cta.setIntIdTipoCuenta(getBeanCtaBancaria().getIntIdTipoCuenta());
		cta.setIntIdMoneda(getBeanCtaBancaria().getIntIdMoneda());
		cta.setIntAbono(getBeanCtaBancaria().getIntAbono());
		cta.setIntDepositaCTS(getBeanCtaBancaria().getIntDepositaCTS());
		cta.setIntCargos(getBeanCtaBancaria().getIntCargos());
		cta.setStrNroCtaBancaria(getBeanCtaBancaria().getStrNroCtaBancaria());
		cta.setIntIdEstado(getBeanCtaBancaria().getIntIdEstado());
		cta.setStrObservacion(getBeanCtaBancaria().getStrObservacion());
		
		beanListCtaBancaria.add(cta);
	}
	
	public void verCtaBancaria(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging verCtaBancaria-------------------------------------");
		setCtaBancariaService(ctaBancariaService);
		
		//Parametros de busqueda
		CuentaBancaria cta = new CuentaBancaria();
		log.info("idPersonaCtaBan: " + getRequestParameter("idPersonaCtaBan"));
		log.info("idCtaBancaria: " + getRequestParameter("idCtaBancaria"));
		Integer idpersona = Integer.parseInt(getRequestParameter("idPersonaCtaBan"));
		Integer idCtaBancaria = Integer.parseInt(getRequestParameter("idCtaBancaria"));
		cta.setIntIdPersona(idpersona);
		cta.setIntIdCtaBancaria(idCtaBancaria);
		
		ArrayList arrayCta = new ArrayList();
		arrayCta = getCtaBancariaService().listarCtaBancaria(cta);
		log.info("arrayCta.size: " + arrayCta.size());
		
		cta = (CuentaBancaria) arrayCta.get(0);
		setBeanCtaBancaria(cta);
	}
	
	public void cleanBeanCtaBancaria(ActionEvent event){
		log.info("-----------------------------------Debugging cleanBeanCtaBancaria-----------------------------------");
		//Limpiar el beanCtaBancaria
		CuentaBancaria cleanCta = new CuentaBancaria();
		setBeanCtaBancaria(cleanCta);
	}
	
	//-----------------------------------------------------------------------------------------
	//Getters y Setters
	//-----------------------------------------------------------------------------------------
	public CtaBancariaServiceImpl getCtaBancariaService() {
		return ctaBancariaService;
	}
	public void setCtaBancariaService(CtaBancariaServiceImpl ctaBancariaService) {
		this.ctaBancariaService = ctaBancariaService;
	}
	public String getStrNombCompleto() {
		return strNombCompleto;
	}
	public void setStrNombCompleto(String strNombCompleto) {
		this.strNombCompleto = strNombCompleto;
	}
	public Integer getIntCondicion() {
		return intCondicion;
	}
	public void setIntCondicion(Integer intCondicion) {
		this.intCondicion = intCondicion;
	}
	public String getStrRol() {
		return strRol;
	}
	public void setStrRol(String strRol) {
		this.strRol = strRol;
	}
	public CuentaBancaria getBeanCtaBancaria() {
		return beanCtaBancaria;
	}
	public void setBeanCtaBancaria(CuentaBancaria beanCtaBancaria) {
		this.beanCtaBancaria = beanCtaBancaria;
	}
	public List getBeanListCtaBancaria() {
		return beanListCtaBancaria;
	}
	public void setBeanListCtaBancaria(List beanListCtaBancaria) {
		this.beanListCtaBancaria = beanListCtaBancaria;
	}
}
