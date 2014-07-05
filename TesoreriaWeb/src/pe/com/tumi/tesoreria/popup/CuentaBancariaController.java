package pe.com.tumi.tesoreria.popup;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.CuentaBancariaFinId;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;

public class CuentaBancariaController {
	
	protected   static Logger 		    	log = Logger.getLogger(CuentaBancariaController.class);
	private 	String 						strNombCompleto = "000300199 - Yessica Margot Tucto Ricra";
	private 	Integer 					intCondicion;
	private 	Integer						intRol;
	private 	String 						strRazonCuenta[];
	private 	CuentaBancaria 				beanCtaBancaria;
	private 	List<CuentaBancaria>		listCtaBancariaJuriConve = null;
	private 	List<CuentaBancaria>		listCtaBancariaProveedor = null;
	private 	List<CuentaBancaria>		listCtaBancariaSocioNatu = null;
	private 	List<CuentaBancaria>		listCtaBanSocioNatuEmp = null;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdJuriConve;
	private 	String 						strFormIdJuriProveedor;
	private 	String 						strFormIdSocioNatu;
	private 	String 						strFormIdSocioNatuEmp;
	private 	String 						strIdModalPanel;
	private 	String 						pgListCtaBancaria;
	private		List<Tabla>					listaTablaFin;
	private		TablaFacadeRemote 			tablaFacade;
	private		boolean						habilitarEditar;
	
	public CuentaBancariaController(){
		try{
			strIdModalPanel = "mpCuentaBancaria"; 
			pgListCtaBancaria = "pgContacto,pgCuentaBancaria";
			
			strNombCompleto = "000300199 - Yessica Margot Tucto Ricra";
			intRol = 2;
			intCondicion = 1;
			
			strFormIdJuriConve = "frmPerJuriConve";
			strFormIdSocioNatu = "frmSocioNatural";
			strFormIdSocioNatuEmp = "frmSocioEmpresa";
			strFormIdJuriProveedor = "frmPerJuriProveedor";
			
			listCtaBancariaJuriConve = new ArrayList<CuentaBancaria>();
			listCtaBancariaSocioNatu = new ArrayList<CuentaBancaria>();
			listCtaBanSocioNatuEmp = new ArrayList<CuentaBancaria>();
			listCtaBancariaProveedor = new ArrayList<CuentaBancaria>();
			
			beanCtaBancaria = new CuentaBancaria();
			beanCtaBancaria.setId(new CuentaBancariaPK());
			
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTablaFin = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPORAZONCUENTA));
			/*for(Tabla tabla : listaTablaFin){
				log.info("tab:"+tabla.getIntIdMaestro()+" "+tabla.getIntIdDetalle());
			}*/
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void addCtaBancaria(ActionEvent event){
		try{
			log.info("-------------------------------------Debugging addCtaBancaria-------------------------------------");
		ArrayList<CuentaBancaria> listCtaBancaria = new ArrayList<CuentaBancaria>();
		
		CuentaBancaria cta = getCtaBancariaVal();
		log.info("cta.id.intIdPersona: "+cta.getId().getIntIdPersona());
		log.info("cta.id.intIdCuentaBancaria: "+cta.getId().getIntIdCuentaBancaria());
		log.info("cta.intBancoCod: "+cta.getIntBancoCod());
		log.info("cta.intTipoCuentaCod: "+cta.getIntTipoCuentaCod());
		log.info("cta.intMonedaCod: "+cta.getIntMonedaCod());
		log.info("cta.intMarcaAbono: "+cta.getIntMarcaAbono());
		log.info("cta.intDepositaCts: "+cta.getIntDepositaCts());
		log.info("cta.intMarcaCargo: "+cta.getIntMarcaCargo());
		log.info("cta.strNroCuentaBancaria: "+cta.getStrNroCuentaBancaria());
		log.info("cta.intEstadoCod: "+cta.getIntEstadoCod());
		log.info("cta.strObservacion: "+cta.getStrObservacion());
		
		//Seteando datos al bean
		CuentaBancaria ctaban = new CuentaBancaria();
		ctaban.setId(new CuentaBancariaPK());
		ctaban.getId().setIntIdPersona(cta.getId().getIntIdPersona());
		ctaban.getId().setIntIdCuentaBancaria(cta.getId().getIntIdCuentaBancaria());
		ctaban.setIntBancoCod(cta.getIntBancoCod());
		ctaban.setIntTipoCuentaCod(cta.getIntTipoCuentaCod());
		ctaban.setIntMonedaCod(cta.getIntMonedaCod());
		ctaban.setIntMarcaAbono(cta.getIntMarcaAbono());
		ctaban.setIntDepositaCts(cta.getIntDepositaCts());
		ctaban.setIntMarcaCargo(cta.getIntMarcaCargo());
		ctaban.setStrNroCuentaBancaria(cta.getStrNroCuentaBancaria());
		ctaban.setIntEstadoCod(cta.getIntEstadoCod());
		ctaban.setStrObservacion(cta.getStrObservacion());
		ctaban.setStrCodigoInterbancario(cta.getStrCodigoInterbancario());
		
		/**Parche para soportar cuentaBancariaFin**/
		ctaban.setListaCuentaBancariaFin(new ArrayList<CuentaBancariaFin>());		
		for(Tabla tabla : listaTablaFin){
			if(tabla.getChecked()){
				CuentaBancariaFin cuentaBancariaFin = new CuentaBancariaFin();
				cuentaBancariaFin.setId(new CuentaBancariaFinId());
				cuentaBancariaFin.getId().setIntParaTipoFinCuenta(tabla.getIntIdDetalle());
				ctaban.getListaCuentaBancariaFin().add(cuentaBancariaFin);
			}
		}
		/**Fin parche**/
		
		log.info("strCallingFormId:"+strCallingFormId);
		if(strCallingFormId!=null && strCallingFormId.equals(strFormIdJuriConve)){
			if(listCtaBancariaJuriConve!=null){
				listCtaBancaria = (ArrayList)getListCtaBancariaJuriConve();
			}
			listCtaBancaria.add(ctaban);
			setListCtaBancariaJuriConve(listCtaBancaria);
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdSocioNatu)){
			if(listCtaBancariaSocioNatu!=null){
				listCtaBancaria = (ArrayList)getListCtaBancariaSocioNatu();
			}
			listCtaBancaria.add(ctaban);
			setListCtaBancariaSocioNatu(listCtaBancaria);
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdSocioNatuEmp)){
			if(listCtaBanSocioNatuEmp!=null){
				listCtaBancaria = (ArrayList)getListCtaBanSocioNatuEmp();
			}
			listCtaBancaria.add(ctaban);
			setListCtaBanSocioNatuEmp(listCtaBancaria);
			
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdJuriProveedor)){			
			if(listCtaBancariaProveedor!=null){
				listCtaBancaria = (ArrayList)getListCtaBancariaProveedor();
			}
			listCtaBancaria.add(ctaban);
			setListCtaBancariaProveedor(listCtaBancaria);
		}
		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public CuentaBancaria getCtaBancariaVal(){
		log.info("-------------------------------------Debugging CuentaBancariaController.getCtaBancariaVal-------------------------------------");
		//Verificando datos de entrada
		//log.info("intIdCtaBancaria: "+getBeanCtaBancaria().getId().getIntIdCuentaBancaria());
		log.info("intIdBanco: "+getBeanCtaBancaria().getIntBancoCod());
		log.info("intIdTipoCuenta: "+getBeanCtaBancaria().getIntTipoCuentaCod());
		log.info("intIdMoneda: "+getBeanCtaBancaria().getIntMonedaCod());
		//log.info("strRazonCuenta.length: "+getStrRazonCuenta().length);
		/*String strRazonCuenta[] = getStrRazonCuenta(); 
		for(int i=0; i<strRazonCuenta.length; i++){
			log.info("strRazonCuenta["+i+"]: "+strRazonCuenta[i]);
			if(Integer.parseInt(strRazonCuenta[i]) == Constante.PARAM_T_TIPORAZONCUENTA_ABONOS){
				getBeanCtaBancaria().setIntMarcaAbono(1);
			}else if (Integer.parseInt(strRazonCuenta[i]) == Constante.PARAM_T_TIPORAZONCUENTA_DEPOSITACTS){
				getBeanCtaBancaria().setIntDepositaCts(1);
			}else if (Integer.parseInt(strRazonCuenta[i]) == Constante.PARAM_T_TIPORAZONCUENTA_CARGOS){
				getBeanCtaBancaria().setIntMarcaCargo(1);
			}
		}
		if(getBeanCtaBancaria().getIntMarcaAbono()==null){
			getBeanCtaBancaria().setIntMarcaAbono(0);
		}
		if(getBeanCtaBancaria().getIntMarcaCargo()==null){
			getBeanCtaBancaria().setIntMarcaCargo(0);
		}
		if(getBeanCtaBancaria().getIntDepositaCts()==null){
			getBeanCtaBancaria().setIntDepositaCts(0);
		}*/
		log.info("strNroCtaBancaria: "+getBeanCtaBancaria().getStrNroCuentaBancaria());
		log.info("intIdEstado: "+getBeanCtaBancaria().getIntEstadoCod());
		log.info("strObservacion: "+getBeanCtaBancaria().getStrObservacion());
		
		return beanCtaBancaria;
	}
	
	private void limpiarListaTablaFin(){
		for(Tabla tabla : listaTablaFin){
			tabla.setChecked(Boolean.FALSE);
		}
		
	}
	
	public void verCtaBancariaJuridica(ActionEvent event) {
		log.info("-------------------------------------Debugging CuentaBancariaController.verCtaBancariaJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBancariaJuriConve");
		log.info("rowKeyCtaBancariaJuriConve = "+ rowKey);
		verCtaBancaria(listCtaBancariaJuriConve, rowKey);
	}
	
	public void verCtaBancariaProveedor(ActionEvent event) {
		log.info("-------------------------------------Debugging CuentaBancariaController.verCtaBancariaJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKey");
		log.info("rowKey = "+ rowKey);
		verCtaBancaria(listCtaBancariaProveedor, rowKey);
	}
	
	public void verCtaBancariaSocioNatu(ActionEvent event) {
		log.info("-------------------------------------Debugging CuentaBancariaController.verCtaBancariaSocioNatu-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBanSocioNatu");
		log.info("rowKeyCtaBanSocioNatu = "+ rowKey);
		verCtaBancaria(listCtaBancariaSocioNatu, rowKey);
	}
	
	public void verCtaBanSocioNatuEmp(ActionEvent event) {
		log.info("-------------------------------------Debugging CuentaBancariaController.verCtaBanSocioNatuEmp-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBanSocioNatuEmp");
		log.info("rowKeyCtaBanSocioNatuEmp = "+ rowKey);
		verCtaBancaria(listCtaBanSocioNatuEmp, rowKey);
	}
	
	public void verCtaBancaria(List<CuentaBancaria> listCtaBancaria, String rowKey){
		log.info("-------------------------------------Debugging CuentaBancariaController.verCtaBancaria-------------------------------------");
	    CuentaBancaria cuentaBancaria = null;
		log.info("rowKey: "+rowKey);
	    if(listCtaBancaria!=null){
	    	for(int i=0; i<listCtaBancaria.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					cuentaBancaria = listCtaBancaria.get(i);
					break;
				}
			}
	    }
		
	    if(cuentaBancaria.getId()!=null && cuentaBancaria.getId().getIntIdCuentaBancaria()!=null){
	    	habilitarEditar = Boolean.TRUE;
	    }else{
	    	habilitarEditar = Boolean.FALSE;
	    }
	    
	    /**Inicio parche para sar soporte a CuentaBancariaFin**/
	    limpiarListaTablaFin();
	    if(cuentaBancaria.getListaCuentaBancariaFin()!=null){
	    	log.info("ctabancafin no null");
	    	log.info("cuentaBancaria.getListaCuentaBancariaFin.size:"+cuentaBancaria.getListaCuentaBancariaFin().size());
	    	//Carga CuentaBancariaFin y hace el match con listaTablaFin
	    	for(CuentaBancariaFin cuentaBancariaFin : cuentaBancaria.getListaCuentaBancariaFin()){
	    		log.info("cuentaBancariaFin.getId().getIntParaTipoFinCuenta():"+cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
	    		for(Tabla tabla : listaTablaFin){
	    			log.info("tabla.getIntIdDetalle():"+tabla.getIntIdDetalle());
	    			if(tabla.getIntIdDetalle().equals(cuentaBancariaFin.getId().getIntParaTipoFinCuenta())){
	    				tabla.setChecked(Boolean.TRUE);
	    			}
	    		}
	    	}
	    }else{
	    	log.info("ctabancafin null");
	    }
	    /**Fin parche**/
	    
	    //Comentado para ya no usar el antiguo esquema
	    /*
		String[] strRazonCuentaAux = new String[3];
		String[] strRazonCuenta = null;
		int i = 0;
		if(cuentaBancaria.getIntMarcaAbono()==1){
			i++;
			strRazonCuentaAux[0] = Constante.PARAM_T_TIPORAZONCUENTA_ABONOS.toString();
		}
		if(cuentaBancaria.getIntMarcaCargo()==1){
			i++;
			strRazonCuentaAux[1] = Constante.PARAM_T_TIPORAZONCUENTA_DEPOSITACTS.toString();
		}
		if(cuentaBancaria.getIntDepositaCts()==1){
			i++;
			strRazonCuentaAux[2] = Constante.PARAM_T_TIPORAZONCUENTA_CARGOS.toString();
		}
		strRazonCuenta = new String[i];
		log.info("strRazonCuenta.length: "+strRazonCuenta.length);
		int pos = 0;
		for(int j=0; j<strRazonCuentaAux.length; j++){
			String strRazCta = strRazonCuentaAux[j];
			if(strRazCta!=null){
				strRazonCuenta[pos] = strRazCta;
				pos++;
			}
		}
		setStrRazonCuenta(strRazonCuenta);*/
		setBeanCtaBancaria(cuentaBancaria);
	}
	
	public void quitarCtaBancariaJuridica(ActionEvent event){
		log.info("-------------------------------------Debugging quitarCtaBancariaJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBancariaJuridica");
		quitarCtaBancaria(listCtaBancariaJuriConve, Integer.parseInt(rowKey));
	}
	
	public void quitarCtaBancariaProveedor(ActionEvent event){		
		log.info("-------------------------------------Debugging quitarCtaBancariaJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKey");
		quitarCtaBancaria(listCtaBancariaProveedor, Integer.parseInt(rowKey));
	}
	
	public void quitarCtaBancariaSocioNatu (ActionEvent event){
		log.info("-------------------------------------Debugging quitarCtaBancariaSocioNatu-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBanSocioNatu");
		quitarCtaBancaria(listCtaBancariaSocioNatu, Integer.parseInt(rowKey));
	}
	
	public void quitarCtaBanSocioNatuEmp (ActionEvent event){
		log.info("-------------------------------------Debugging quitarCtaBanSocioNatuEmp-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBanSocioNatuEmp");
		quitarCtaBancaria(listCtaBanSocioNatuEmp, Integer.parseInt(rowKey));
	}
	
	public void quitarCtaBancaria(List<CuentaBancaria> listCtaBancaria, Integer rowKey){
		log.info("-------------------------------------Debugging CuentaBancariaController.quitarCtaBancaria-------------------------------------");
		CuentaBancaria ctabanTmp = null;
		if(listCtaBancaria!=null){
			for(int i=0; i<listCtaBancaria.size(); i++){
				if(rowKey==i){
					CuentaBancaria ctaban = listCtaBancaria.get(i);
					log.info("ctaban.id: "+ctaban.getId());
					if(ctaban.getId()!=null && ctaban.getId().getIntIdCuentaBancaria()!=null){
						ctabanTmp = listCtaBancaria.get(i);
						ctabanTmp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listCtaBancaria.remove(i);
					break;
				}
			}
			if(ctabanTmp!=null && ctabanTmp.getId().getIntIdCuentaBancaria()!=null){
				listCtaBancaria.add(ctabanTmp);
			}
		}
	}
	
	public void showCtaBancariaPerJuridica(ActionEvent event){
		log.info("-----------------------------------Debugging showCtaBancariaPerJuridica-----------------------------------");
		//Limpiar el beanCtaBancaria
		CuentaBancaria cleanCta = new CuentaBancaria();
		cleanCta.setId(new CuentaBancariaPK());
		setBeanCtaBancaria(cleanCta);
		
		setStrCallingFormId(strFormIdJuriConve);
	}
	
	public void showCtaBancariaPerProveedor(ActionEvent event){
		//Limpiar el beanCtaBancaria
		CuentaBancaria cleanCta = new CuentaBancaria();
		cleanCta.setId(new CuentaBancariaPK());
		setBeanCtaBancaria(cleanCta);
		
		habilitarEditar = Boolean.TRUE;
		strCallingFormId = "frmPerJuriProveedor";
		limpiarListaTablaFin();
	}
	
	public void showCtaBancariaSocioNatu(ActionEvent event){
		log.info("-----------------------------------Debugging cleanBeanCtaBancaria-----------------------------------");
		//Limpiar el beanCtaBancaria
		CuentaBancaria cleanCta = new CuentaBancaria();
		cleanCta.setId(new CuentaBancariaPK());
		setBeanCtaBancaria(cleanCta);
		
		setStrCallingFormId(strFormIdSocioNatu);
	}
	
	public void showCtaBanSocioNatuEmp(ActionEvent event){
		log.info("-----------------------------------Debugging showCtaBanSocioNatuEmp-----------------------------------");
		//Limpiar el beanCtaBancaria
		CuentaBancaria cleanCta = new CuentaBancaria();
		cleanCta.setId(new CuentaBancariaPK());
		setBeanCtaBancaria(cleanCta);
		
		setStrCallingFormId(strFormIdSocioNatuEmp);
	}
	
	public void cleanBeanCtaBancaria(ActionEvent event){
		log.info("-----------------------------------Debugging cleanBeanCtaBancaria-----------------------------------");
		//Limpiar el beanCtaBancaria
		CuentaBancaria cleanCta = new CuentaBancaria();
		cleanCta.setId(new CuentaBancariaPK());
		setBeanCtaBancaria(cleanCta);
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(msg));
	}
	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,	new FacesMessage(msg));
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	
	//-----------------------------------------------------------------------------------------
	//Getters y Setters
	//-----------------------------------------------------------------------------------------
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
	public Integer getIntRol() {
		return intRol;
	}
	public void setIntRol(Integer intRol) {
		this.intRol = intRol;
	}
	public CuentaBancaria getBeanCtaBancaria() {
		return beanCtaBancaria;
	}
	public void setBeanCtaBancaria(CuentaBancaria beanCtaBancaria) {
		this.beanCtaBancaria = beanCtaBancaria;
	}
	public List<CuentaBancaria> getListCtaBancariaJuriConve() {
		return listCtaBancariaJuriConve;
	}
	public void setListCtaBancariaJuriConve(
			List<CuentaBancaria> listCtaBancariaJuriConve) {
		this.listCtaBancariaJuriConve = listCtaBancariaJuriConve;
	}
	public String[] getStrRazonCuenta() {
		return strRazonCuenta;
	}
	public void setStrRazonCuenta(String[] strRazonCuenta) {
		this.strRazonCuenta = strRazonCuenta;
	}
	public List<CuentaBancaria> getListCtaBancariaSocioNatu() {
		return listCtaBancariaSocioNatu;
	}
	public void setListCtaBancariaSocioNatu(
			List<CuentaBancaria> listCtaBancariaSocioNatu) {
		this.listCtaBancariaSocioNatu = listCtaBancariaSocioNatu;
	}
	public List<CuentaBancaria> getListCtaBanSocioNatuEmp() {
		return listCtaBanSocioNatuEmp;
	}
	public void setListCtaBanSocioNatuEmp(
			List<CuentaBancaria> listCtaBanSocioNatuEmp) {
		this.listCtaBanSocioNatuEmp = listCtaBanSocioNatuEmp;
	}
	public String getStrCallingFormId() {
		return strCallingFormId;
	}
	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}
	public String getStrFormIdJuriConve() {
		return strFormIdJuriConve;
	}
	public void setStrFormIdJuriConve(String strFormIdJuriConve) {
		this.strFormIdJuriConve = strFormIdJuriConve;
	}
	public String getStrFormIdSocioNatu() {
		return strFormIdSocioNatu;
	}
	public void setStrFormIdSocioNatu(String strFormIdSocioNatu) {
		this.strFormIdSocioNatu = strFormIdSocioNatu;
	}
	public String getStrFormIdSocioNatuEmp() {
		return strFormIdSocioNatuEmp;
	}
	public void setStrFormIdSocioNatuEmp(String strFormIdSocioNatuEmp) {
		this.strFormIdSocioNatuEmp = strFormIdSocioNatuEmp;
	}
	public String getStrIdModalPanel() {
		return strIdModalPanel;
	}
	public void setStrIdModalPanel(String strIdModalPanel) {
		this.strIdModalPanel = strIdModalPanel;
	}
	public String getPgListCtaBancaria() {
		return pgListCtaBancaria;
	}
	public void setPgListCtaBancaria(String pgListCtaBancaria) {
		this.pgListCtaBancaria = pgListCtaBancaria;
	}
	public List<CuentaBancaria> getListCtaBancariaProveedor() {
		return listCtaBancariaProveedor;
	}
	public void setListCtaBancariaProveedor(List<CuentaBancaria> listCtaBancariaProveedor) {
		this.listCtaBancariaProveedor = listCtaBancariaProveedor;
	}
	public List<Tabla> getListaTablaFin() {
		return listaTablaFin;
	}
	public void setListaTablaFin(List<Tabla> listaTablaFin) {
		this.listaTablaFin = listaTablaFin;
	}
	public boolean isHabilitarEditar() {
		return habilitarEditar;
	}
	public void setHabilitarEditar(boolean habilitarEditar) {
		this.habilitarEditar = habilitarEditar;
	}
}
