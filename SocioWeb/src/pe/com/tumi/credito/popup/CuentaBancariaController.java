package pe.com.tumi.credito.popup;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;

public class CuentaBancariaController {
	
	protected   static Logger 		    	log = Logger.getLogger(CuentaBancariaController.class);
	private 	String 						strNombCompleto = "000300199 - Yessica Margot Tucto Ricra";
	private 	Integer 					intCondicion;
	private 	Integer						intRol;
	private 	String 						strRazonCuenta[];
	private 	CuentaBancaria 				beanCtaBancaria;
	private 	List<CuentaBancaria>		listCtaBancariaJuriConve = null;
	private 	List<CuentaBancaria>		listCtaBancariaSocioNatu = null;
	private 	List<CuentaBancaria>		listCtaBanSocioNatuEmp = null;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdJuriConve;
	private 	String 						strFormIdSocioNatu;
	private 	String 						strFormIdSocioNatuEmp;
	private 	String 						strIdModalPanel;
	private 	String 						pgListCtaBancaria;
	
	public CuentaBancariaController(){
		strIdModalPanel = "mpCuentaBancaria"; 
		pgListCtaBancaria = "divFormSocioNatu,pgContacto,pgCuentaBancaria";
		
		strNombCompleto = "000300199 - Yessica Margot Tucto Ricra";
		intRol = 2;
		intCondicion = 1;
		
		strFormIdJuriConve = "frmPerJuriConve";
		strFormIdSocioNatu = "frmSocioNatural";
		strFormIdSocioNatuEmp = "frmSocioEmpresa";
		
		listCtaBancariaJuriConve = new ArrayList<CuentaBancaria>();
		listCtaBancariaSocioNatu = new ArrayList<CuentaBancaria>();
		listCtaBanSocioNatuEmp = new ArrayList<CuentaBancaria>();
		
		beanCtaBancaria = new CuentaBancaria();
		beanCtaBancaria.setId(new CuentaBancariaPK());
	}
	
	public void addCtaBancaria(ActionEvent event){
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
		
		if(strCallingFormId!=null && strCallingFormId.equals(strFormIdJuriConve)){
			if(listCtaBancariaJuriConve!=null){
				listCtaBancaria = (ArrayList<CuentaBancaria>)getListCtaBancariaJuriConve();
			}
			listCtaBancaria.add(ctaban);
			setListCtaBancariaJuriConve(listCtaBancaria);
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdSocioNatu)){
			if(listCtaBancariaSocioNatu!=null){
				listCtaBancaria = (ArrayList<CuentaBancaria>)getListCtaBancariaSocioNatu();
			}
			listCtaBancaria.add(ctaban);
			setListCtaBancariaSocioNatu(listCtaBancaria);
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdSocioNatuEmp)){
			if(listCtaBanSocioNatuEmp!=null){
				listCtaBancaria = (ArrayList<CuentaBancaria>)getListCtaBanSocioNatuEmp();
			}
			listCtaBancaria.add(ctaban);
			setListCtaBanSocioNatuEmp(listCtaBancaria);
		}
	}
	
	public CuentaBancaria getCtaBancariaVal(){
		log.info("-------------------------------------Debugging CuentaBancariaController.getCtaBancariaVal-------------------------------------");
		//Verificando datos de entrada
		log.info("intIdCtaBancaria: "+getBeanCtaBancaria().getId().getIntIdCuentaBancaria());
		log.info("intIdBanco: "+getBeanCtaBancaria().getIntBancoCod());
		log.info("intIdTipoCuenta: "+getBeanCtaBancaria().getIntTipoCuentaCod());
		log.info("intIdMoneda: "+getBeanCtaBancaria().getIntMonedaCod());
		log.info("strRazonCuenta.length: "+getStrRazonCuenta().length);
		String strRazonCuenta[] = getStrRazonCuenta(); 
		for(int i=0; i<strRazonCuenta.length; i++){
			log.info("strRazonCuenta["+i+"]: "+strRazonCuenta[i]);
			if( Integer.parseInt(strRazonCuenta[i]) == Constante.PARAM_T_TIPORAZONCUENTA_ABONOS){
				getBeanCtaBancaria().setIntMarcaAbono(1);
			}else if ( Integer.parseInt(strRazonCuenta[i]) == Constante.PARAM_T_TIPORAZONCUENTA_DEPOSITACTS){
				getBeanCtaBancaria().setIntDepositaCts(1);
			}else if ( Integer.parseInt(strRazonCuenta[i]) == Constante.PARAM_T_TIPORAZONCUENTA_CARGOS){
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
		}
		log.info("strNroCtaBancaria: "+getBeanCtaBancaria().getStrNroCuentaBancaria());
		log.info("intIdEstado: "+getBeanCtaBancaria().getIntEstadoCod());
		log.info("strObservacion: "+getBeanCtaBancaria().getStrObservacion());
		
		return beanCtaBancaria;
	}
	
	public void verCtaBancariaJuridica(ActionEvent event) {
		log.info("-------------------------------------Debugging CuentaBancariaController.verCtaBancariaJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBancariaJuriConve");
		log.info("rowKeyCtaBancariaJuriConve = "+ rowKey);
		verCtaBancaria(listCtaBancariaJuriConve, rowKey);
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
		setStrRazonCuenta(strRazonCuenta);
		setBeanCtaBancaria(cuentaBancaria);
	}
	
	public void quitarCtaBancariaJuridica(ActionEvent event){
		log.info("-------------------------------------Debugging quitarCtaBancariaJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCtaBancariaJuridica");
		quitarCtaBancaria(listCtaBancariaJuriConve, Integer.parseInt(rowKey));
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
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}
	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,
				new FacesMessage(msg));
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
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
}
