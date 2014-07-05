package pe.com.tumi.contabilidad.parametro.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class AnexoPopupController {
	private Logger log;
	private Integer intPeriodo;
	private	Integer	intTipoEstadoFinanciero;
	private List listaTipoAnexoPopUp;
	private List<AnexoDetalle> listaAnexoDetallePopUp;
	private Integer intNumeroOperando;
	private	Ratio ratioNuevo;
	//private RatioDetalle 	ratioDetalleSeleccionado;
	private AnexoDetalle	anexoDetalleSeleccionado;
	private	String	strTextoFiltrar;
	private Integer intOrdenCorrelativo;
	private List<AnexoDetalle> listaAnexoDetalle;
	private Usuario usuario;
	
	private String strCallingFormId;
	private String strFormIdPlanCuenta;
	private String strIdListAnexoDetalleCuenta;
	
	private List<AnexoDetalleCuenta> listaAnexoDetalleCuenta;
	
	CierreFacadeLocal cierreFacade;
	TablaFacadeRemote tablaFacade;
	
	public AnexoPopupController(){
		log = Logger.getLogger(this.getClass());
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public void cargarValoresIniciales(){
		
		try {
			cierreFacade = (CierreFacadeLocal) EJBFactory.getLocal(CierreFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			listaAnexoDetallePopUp = new ArrayList<AnexoDetalle>();
			listaTipoAnexoPopUp  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSFINANCIEROS), "B");
			
			strFormIdPlanCuenta = "frmPlanCuentas";
			listaAnexoDetalleCuenta = new ArrayList<AnexoDetalleCuenta>();
		} catch (EJBFactoryException e) {
			log.error(e.getMessage(),e);
		} catch (BusinessException e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void limpiarElemento(ActionEvent event){
		try{
			log.info("--limpiarElemento");
			//ratioDetalleSeleccionado = (RatioDetalle)event.getComponent().getAttributes().get("item");
			//int intIndice = listaRatioDetalleSuperior.indexOf(anexoDetalleSeleccionado);
			
			intNumeroOperando = Integer.parseInt(((String)event.getComponent().getAttributes().get("operando")));
			log.info("intNumeroOperando:"+intNumeroOperando);
			if(intNumeroOperando.equals(new Integer(1))){
				//ratioDetalleSeleccionado.setOperando1(new AnexoDetalle());
			}else if(intNumeroOperando.equals(new Integer(2))){
				//ratioDetalleSeleccionado.setOperando2(new AnexoDetalle());
			}
			
			//log.info("intIndice:"+intIndice);
			//listaRatioDetalleSuperior.set(intIndice, anexoDetalleSeleccionado);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarEstadoFinanciero(){
		try{
			if(intTipoEstadoFinanciero.equals(new Integer(0))){
				return;
			}
			Anexo anexoFiltro = new Anexo();
			anexoFiltro.getId().setIntPersEmpresaAnexo(usuario.getPerfil().getId().getIntPersEmpresaPk());
			anexoFiltro.getId().setIntParaTipoAnexo(intTipoEstadoFinanciero);
			anexoFiltro.getId().setIntContPeriodoAnexo(intPeriodo);
			listaAnexoDetallePopUp = cierreFacade.getListaAnexoDetallePorAnexo(anexoFiltro);
			if(listaAnexoDetallePopUp!=null && !listaAnexoDetallePopUp.isEmpty()){
				iniciarOrdenarListaAnexoDetallePopUp();
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void iniciarOrdenarListaAnexoDetallePopUp(){
		try{
			intOrdenCorrelativo = new Integer(0);
			
			AnexoDetalle anexoTemp = new AnexoDetalle();
			anexoTemp.setIntNivel(1);
			anexoTemp.setIntPosicion(1);
			anexoTemp.setIntItem(1);
			anexoTemp.setIntNivelPadre(0);
			anexoTemp.setIntPosicionPadre(0);
			anexoTemp.setIntItemPadre(0);
			
			for(AnexoDetalle aTemp : listaAnexoDetallePopUp){
				aTemp.setRevisado(Boolean.FALSE);
			}
			log.info("--iniciarOrdenarAnexoTemp");
			ordenarAnexoDetallePopUp(anexoTemp);
			log.info("--finOrdenarAnexoTemp");
			//revisarHabilitaciones();

			//ordenamos por intOrden (sort por defecto)
			Collections.sort(listaAnexoDetallePopUp);
			
			/*for(AnexoTemp aTemp : listaAnexoTemp){
				log.info(aTemp);
			}*/
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void ordenarAnexoDetallePopUp(AnexoDetalle anexoDetalle) throws Exception{
		try{
			//log.info("---------"+anexoTemp);
			List<AnexoDetalle> listaHermanos = buscarListaHermanosPopUp(anexoDetalle);
			//Ordenamos por POSICION
			Collections.sort(listaHermanos, new Comparator<AnexoDetalle>(){
				public int compare(AnexoDetalle uno, AnexoDetalle otro) {
					return uno.getIntPosicion().compareTo(otro.getIntPosicion());
				}
			});
			
			List<AnexoDetalle> listaHijos;
			/*for(AnexoTemp hermano : listaHermanos){
				log.info("--hermanos:"+hermano);
			}*/
			for(AnexoDetalle hermano : listaHermanos){
				if(!hermano.isRevisado()){
					hermano.setRevisado(Boolean.TRUE);
					listaHijos = buscarListaHijosPopUp(hermano);
					aplicarNumeracionPopUp(hermano);
					//si no posee hijos
					if(listaHijos.isEmpty()){
						intOrdenCorrelativo = intOrdenCorrelativo +1;
						hermano.setIntOrden(intOrdenCorrelativo);
						//log.info("*hermano:"+hermano);
					}else{
						intOrdenCorrelativo = intOrdenCorrelativo +1;
						hermano.setIntOrden(intOrdenCorrelativo);
						//log.info("-hermano:"+hermano);
						ordenarAnexoDetallePopUp(listaHijos.get(0));
					}
				}				
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private void aplicarNumeracionPopUp(AnexoDetalle anexoDetalle){
		if(anexoDetalle.getIntNivelPadre().equals(new Integer(0))){
			anexoDetalle.setStrNumeracion(anexoDetalle.getIntPosicion()+"");
		}else{
			AnexoDetalle anexoDetallePadre = buscarPadrePopUp(anexoDetalle);
			String numeracion = "";
			for(int i=0;i<anexoDetalle.getIntNivel();i++){
				numeracion = numeracion + " ";
			}
			numeracion = numeracion + anexoDetallePadre.getStrNumeracion()+"."+anexoDetalle.getIntPosicion();
			anexoDetalle.setStrNumeracion(numeracion);
		}
	}
	
	private AnexoDetalle buscarPadrePopUp(AnexoDetalle anexoDetalleHijo){
		Integer intNivelPadre 	= anexoDetalleHijo.getIntNivelPadre();
		Integer intPosicionPadre= anexoDetalleHijo.getIntPosicionPadre();
		Integer intItemPadre 	= anexoDetalleHijo.getIntItemPadre();
		
		for(Object o : listaAnexoDetallePopUp){
			AnexoDetalle anexoDetalle = (AnexoDetalle)o;
			if(anexoDetalle.getIntNivel().equals(intNivelPadre) 
				&& anexoDetalle.getIntPosicion().equals(intPosicionPadre)
				&& anexoDetalle.getIntItem().equals(intItemPadre)){				
				return anexoDetalle;
			}
		}		
		return null;
	}
	
	private boolean poseeHijos(AnexoDetalle anexoDetalle){
		List<AnexoDetalle> listaHijos = buscarListaHijos(anexoDetalle);
		if(!listaHijos.isEmpty()){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private List<AnexoDetalle> buscarListaHermanosPopUp(AnexoDetalle anexoDetalle){
		Integer intNivelPadre 	= anexoDetalle.getIntNivelPadre();
		Integer intPosicionPadre= anexoDetalle.getIntPosicionPadre();
		Integer intItemPadre 	= anexoDetalle.getIntItemPadre();
		
		List <AnexoDetalle> listaHermanos = new ArrayList<AnexoDetalle>();
		
		for(AnexoDetalle aTemp : listaAnexoDetallePopUp){
			if(aTemp.getIntNivelPadre().equals(intNivelPadre) 
				&& aTemp.getIntPosicionPadre().equals(intPosicionPadre)
				&& aTemp.getIntItemPadre().equals(intItemPadre)){				
				listaHermanos.add(aTemp);
			}
		}		
		return listaHermanos;
	}
	
	private List<AnexoDetalle> buscarListaHijosPopUp(AnexoDetalle anexoDetallePadre){
		Integer intNivelPadre 	= anexoDetallePadre.getIntNivel();
		Integer intPosicionPadre= anexoDetallePadre.getIntPosicion();
		Integer intItemPadre 	= anexoDetallePadre.getIntItem();
		
		List <AnexoDetalle> listaHijos = new ArrayList<AnexoDetalle>();
		
		for(AnexoDetalle aTemp : listaAnexoDetallePopUp){
			if(aTemp.getIntNivelPadre().equals(intNivelPadre) 
				&& aTemp.getIntPosicionPadre().equals(intPosicionPadre)
				&& aTemp.getIntItemPadre().equals(intItemPadre)){				
				listaHijos.add(aTemp);
			}
		}
		
		return listaHijos;
	}
	
	private List<AnexoDetalle> buscarListaHijos(AnexoDetalle anexoDetallePadre){
		Integer intNivelPadre 	= anexoDetallePadre.getIntNivel();
		Integer intPosicionPadre= anexoDetallePadre.getIntPosicion();
		Integer intItemPadre 	= anexoDetallePadre.getIntItem();
		
		List <AnexoDetalle> listaHijos = new ArrayList<AnexoDetalle>();
		
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			if(aTemp.getIntNivelPadre().equals(intNivelPadre) 
				&& aTemp.getIntPosicionPadre().equals(intPosicionPadre)
				&& aTemp.getIntItemPadre().equals(intItemPadre)){				
				listaHijos.add(aTemp);
			}
		}
		
		return listaHijos;
	}
	
	public void seleccionarElemento(ActionEvent event){
		try{
			AnexoDetalle anexoDetalleSeleccionadoPopUp = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			
			log.info("intNumeroOperando:"+intNumeroOperando);
			
			/*if(intNumeroOperando.equals(new Integer(1))){
				ratioDetalleSeleccionado.setOperando1(anexoDetalleSeleccionadoPopUp);
			}else if(intNumeroOperando.equals(new Integer(2))){
				ratioDetalleSeleccionado.setOperando2(anexoDetalleSeleccionadoPopUp);
			}else if(intNumeroOperando.equals(new Integer(0))){
				anexoDetalleSeleccionado.setStrTexto(anexoDetalleSeleccionadoPopUp.getStrTexto());
				anexoDetalleSeleccionado.setAnexoDetalleReferencia(anexoDetalleSeleccionadoPopUp);
				revisarHabilitaciones();
			}*/
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean validarElemento(AnexoDetalle anexoDetalle){
		boolean isValid = false;
		if(listaAnexoDetalleCuenta!=null && !listaAnexoDetalleCuenta.isEmpty()){
			for(AnexoDetalleCuenta anexoDetalleCuenta : listaAnexoDetalleCuenta){
				if(anexoDetalle.getId().getIntItemAnexoDetalle().equals(
						anexoDetalleCuenta.getId().getIntItemAnexoDetalle())){
					isValid = true;
				}
			}
		}
		return isValid;
	}
	
	public void agregarElemento(ActionEvent event){
		try{
			AnexoDetalle anexoDetalleSeleccionadoPopUp = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			
			if(validarElemento(anexoDetalleSeleccionadoPopUp)){
				return;
			}
			
			Tabla tablaEstadoFinanciero = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_ESTADOSFINANCIEROS), 
					anexoDetalleSeleccionadoPopUp.getId().getIntParaTipoAnexo());
			
			AnexoDetalleCuenta anexoDetalleCuenta = new AnexoDetalleCuenta();
			//anexoDetalleCuenta.setId(new AnexoDetalleCuentaId());
			anexoDetalleCuenta.getId().setIntPersEmpresaAnexo(anexoDetalleSeleccionadoPopUp.getId().getIntPersEmpresaAnexo());
			anexoDetalleCuenta.getId().setIntContPeriodoAnexo(anexoDetalleSeleccionadoPopUp.getId().getIntContPeriodoAnexo());
			anexoDetalleCuenta.getId().setIntParaTipoAnexo(anexoDetalleSeleccionadoPopUp.getId().getIntParaTipoAnexo());
			anexoDetalleCuenta.getId().setIntItemAnexoDetalle(anexoDetalleSeleccionadoPopUp.getId().getIntItemAnexoDetalle());
			anexoDetalleCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			anexoDetalleCuenta.setStrTexto(
					anexoDetalleSeleccionadoPopUp.getId().getIntContPeriodoAnexo() + " - " + 
							tablaEstadoFinanciero.getStrDescripcion() + " - " +
							anexoDetalleSeleccionadoPopUp.getStrNumeracion() + ".- " +
							anexoDetalleSeleccionadoPopUp.getStrTexto());
			if(strCallingFormId!=null && strCallingFormId.equals(strFormIdPlanCuenta)){
		    	listaAnexoDetalleCuenta.add(anexoDetalleCuenta);
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void deseleccionarElemento(){
		try{
			
			log.info("intNumeroOperando:"+intNumeroOperando);
			/*
			if(intNumeroOperando.equals(new Integer(1))){
				ratioDetalleSeleccionado.setOperando1(new AnexoDetalle());
			}else if(intNumeroOperando.equals(new Integer(2))){
				ratioDetalleSeleccionado.setOperando2(new AnexoDetalle());
			}else if(intNumeroOperando.equals(new Integer(0))){
				anexoDetalleSeleccionado.setAnexoDetalleReferencia(null);
				anexoDetalleSeleccionado.setStrTexto(null);
				revisarHabilitaciones();
			}*/
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public boolean filtrarElementos(Object actual){
		try{
			 AnexoDetalle anexoDetalleActual = (AnexoDetalle)actual;
			   if (strTextoFiltrar.length()==0) {
			      return true;
			   }
			   if (anexoDetalleActual.getStrTexto().toUpperCase().startsWith(strTextoFiltrar.toUpperCase())) {
			      return true;
			   }else {
				   return false; 
			   }
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return false;
		}
	}
	
	private void revisarHabilitaciones(){
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			aTemp.setHabilitarVerCuentas(Boolean.FALSE);
			aTemp.setHabilitarConfigurar(Boolean.FALSE);
			aTemp.setHabilitarVerReferencia(Boolean.FALSE);
			if(!poseeHijos(aTemp)){
				if(aTemp.getListaAnexoDetalleCuenta()!=null && !aTemp.getListaAnexoDetalleCuenta().isEmpty()){
					aTemp.setHabilitarVerCuentas(Boolean.TRUE);
				
				}else if(aTemp.getListaAnexoDetalleOperador()!=null && !aTemp.getListaAnexoDetalleOperador().isEmpty()){
					aTemp.setHabilitarConfigurar(Boolean.TRUE);
				
				}else if(aTemp.getAnexoDetalleReferencia()!=null){
					aTemp.setHabilitarVerReferencia(Boolean.TRUE);
				
				}else if((aTemp.getListaAnexoDetalleCuenta()==null 
					|| aTemp.getListaAnexoDetalleCuenta().isEmpty()) 
					&&(aTemp.getListaAnexoDetalleOperador()==null
					|| aTemp.getListaAnexoDetalleOperador().isEmpty())
					&& aTemp.getAnexoDetalleReferencia()==null){					
					aTemp.setHabilitarVerCuentas(Boolean.TRUE);
					aTemp.setHabilitarConfigurar(Boolean.TRUE);
					aTemp.setHabilitarVerReferencia(Boolean.TRUE);
				}
			}
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public Integer getIntPeriodo() {
		return intPeriodo;
	}

	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}

	public Integer getIntTipoEstadoFinanciero() {
		return intTipoEstadoFinanciero;
	}

	public void setIntTipoEstadoFinanciero(Integer intTipoEstadoFinanciero) {
		this.intTipoEstadoFinanciero = intTipoEstadoFinanciero;
	}

	public List getListaTipoAnexoPopUp() {
		return listaTipoAnexoPopUp;
	}

	public void setListaTipoAnexoPopUp(List listaTipoAnexoPopUp) {
		this.listaTipoAnexoPopUp = listaTipoAnexoPopUp;
	}

	public List<AnexoDetalle> getListaAnexoDetallePopUp() {
		return listaAnexoDetallePopUp;
	}

	public void setListaAnexoDetallePopUp(List<AnexoDetalle> listaAnexoDetallePopUp) {
		this.listaAnexoDetallePopUp = listaAnexoDetallePopUp;
	}

	public Integer getIntNumeroOperando() {
		return intNumeroOperando;
	}

	public void setIntNumeroOperando(Integer intNumeroOperando) {
		this.intNumeroOperando = intNumeroOperando;
	}

	public Ratio getRatioNuevo() {
		return ratioNuevo;
	}

	public void setRatioNuevo(Ratio ratioNuevo) {
		this.ratioNuevo = ratioNuevo;
	}

	public AnexoDetalle getAnexoDetalleSeleccionado() {
		return anexoDetalleSeleccionado;
	}

	public void setAnexoDetalleSeleccionado(AnexoDetalle anexoDetalleSeleccionado) {
		this.anexoDetalleSeleccionado = anexoDetalleSeleccionado;
	}

	public String getStrTextoFiltrar() {
		return strTextoFiltrar;
	}

	public void setStrTextoFiltrar(String strTextoFiltrar) {
		this.strTextoFiltrar = strTextoFiltrar;
	}

	public Integer getIntOrdenCorrelativo() {
		return intOrdenCorrelativo;
	}

	public void setIntOrdenCorrelativo(Integer intOrdenCorrelativo) {
		this.intOrdenCorrelativo = intOrdenCorrelativo;
	}

	public List<AnexoDetalle> getListaAnexoDetalle() {
		return listaAnexoDetalle;
	}

	public void setListaAnexoDetalle(List<AnexoDetalle> listaAnexoDetalle) {
		this.listaAnexoDetalle = listaAnexoDetalle;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getStrCallingFormId() {
		return strCallingFormId;
	}

	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}

	public List<AnexoDetalleCuenta> getListaAnexoDetalleCuenta() {
		return listaAnexoDetalleCuenta;
	}

	public void setListaAnexoDetalleCuenta(
			List<AnexoDetalleCuenta> listaAnexoDetalleCuenta) {
		this.listaAnexoDetalleCuenta = listaAnexoDetalleCuenta;
	}

	public String getStrFormIdPlanCuenta() {
		return strFormIdPlanCuenta;
	}

	public void setStrFormIdPlanCuenta(String strFormIdPlanCuenta) {
		this.strFormIdPlanCuenta = strFormIdPlanCuenta;
	}

	public String getStrIdListAnexoDetalleCuenta() {
		return strIdListAnexoDetalleCuenta;
	}

	public void setStrIdListAnexoDetalleCuenta(String strIdListAnexoDetalleCuenta) {
		this.strIdListAnexoDetalleCuenta = strIdListAnexoDetalleCuenta;
	}
}