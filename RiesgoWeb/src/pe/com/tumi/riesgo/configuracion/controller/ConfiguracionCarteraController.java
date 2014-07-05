package pe.com.tumi.riesgo.configuracion.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;
import pe.com.tumi.riesgo.cartera.domain.Plantilla;
import pe.com.tumi.riesgo.cartera.domain.PlantillaDetalle;
import pe.com.tumi.riesgo.cartera.domain.Prociclico;
import pe.com.tumi.riesgo.cartera.domain.Producto;
import pe.com.tumi.riesgo.cartera.domain.Provision;
import pe.com.tumi.riesgo.cartera.domain.Tiempo;
import pe.com.tumi.riesgo.cartera.facade.CarteraFacadeLocal;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ConfiguracionCarteraController {

	protected static Logger log = Logger.getLogger(ConfiguracionCarteraController.class);
	
	private TablaFacadeRemote tablaFacade;
	//private CarteraFacadeLocal carteraFacade;
	private CarteraFacadeLocal carteraFacade;

	private Cartera carteraFiltro;
	private Cartera carteraNuevo;
	private Cartera registroSeleccionado;
	private Producto productoFiltro;
	
	private List<Cartera> listaCartera;
	private List<Tabla> listaProducto;
	private List<Especificacion> listaEspecificacionInterfazCredito;
	private List<Especificacion> listaEspecificacionInterfazProciclico;
	private List<Tiempo> listaTiempoInterfaz;
	
	private List<PlantillaDetalle> listaPlantillaDetalleCredito;
	private List<PlantillaDetalle> listaPlantillaDetalleProciclico;
	private List<Especificacion> listaEspecificacionCredito;
	private List<Especificacion> listaEspecificacionProciclico;
	private List<Tiempo> listaTiempo;
	private List<Plantilla> listaPlantilla;
	private List<Tabla> listaTablaTipoRiesgo;
	
	private String mensajeOperacion;
	private String strNombreProductoFiltro;
	
	//valores para identificar los tipos de columnas [en validaciones]
	private final Integer filaValores = 1;
	private final Integer filaNula = 2;
	private final Integer filaError = 3;

	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean habilitarFechaFin;
	private boolean habilitarProvisionCreditos;
	private boolean habilitarProvisionProciclica;
	private boolean habilitarTiempoEvaluacion;
	private boolean habilitarModelosContables;
	private boolean seleccionaIndeterminado;
	private boolean buscarVigente;
	private boolean buscarCaduco;
	private boolean mostrarFilaOtrosProductos;
	
	private Usuario usuario;
	
	
	public ConfiguracionCarteraController(){
		cargarValoresIniciales();
	}
	
	private void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				buscarVigente = Boolean.FALSE;
				buscarCaduco = Boolean.FALSE;
				
				carteraFiltro = new Cartera();
				carteraNuevo = new Cartera(); 
				listaCartera = new ArrayList<Cartera>();
				listaProducto = new ArrayList<Tabla>();
				productoFiltro = new Producto();
				
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				//carteraFacade = (CarteraFacadeLocal) EJBFactory.getLocal(CarteraFacadeLocal.class);
				carteraFacade = (CarteraFacadeLocal) EJBFactory.getLocal(CarteraFacadeLocal.class);
				
				listaTablaTipoRiesgo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCATEGORIADERIESGO));
				listaPlantillaDetalleCredito = carteraFacade.getPlantillaDetalleTodo(Constante.PARAM_T_TIPOPROVISION_CREDITOS);
				listaPlantillaDetalleProciclico = carteraFacade.getPlantillaDetalleTodo(Constante.PARAM_T_TIPOPROVISION_PROCICLICA);
				listaPlantilla = carteraFacade.getPlantillaTodo();
				
				cargarListaProducto();
				cargarListaEspecificacion();
				cargarListaTiempo();
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	private void cargarListaTiempo(){
		Tiempo tiempo = null;
		listaTiempo = new ArrayList<Tiempo>();
		listaTiempoInterfaz = new ArrayList<Tiempo>();
		
		//Solo tomamos en cuenta las plantillas para provision de creditos [excepto el tipoSbs 9]
		for(Plantilla plantilla : listaPlantilla){	
			if(plantilla.getId().getIntParaTipoProvisionCod().equals(Constante.PARAM_T_TIPOPROVISION_CREDITOS) 
				&& !plantilla.getId().getIntParaTipoSbsCod().equals(Constante.PARAM_T_TIPO_CREDITOSBS_TODOS)){
				for(Tabla tabla : listaTablaTipoRiesgo){					
					tiempo = new Tiempo();
					tiempo.getId().setIntParaTipoSbsCod(plantilla.getId().getIntParaTipoSbsCod());
					tiempo.getId().setIntParaTipoProvisionCod(plantilla.getId().getIntParaTipoProvisionCod());				
					tiempo.setIntItemTipoCategoriaRiesgoCod(tabla.getIntIdDetalle());
					tiempo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaTiempo.add(tiempo);
				}
			}			
		}
		
		
		//Carga la listaTiempoInterfaz usando Plantilla (solo con tipoProvision = 1)
		//Solo tomamos en cuenta las plantillas para provision de creditos [excepto el tipoSbs 9]
		for(Plantilla plantilla : listaPlantilla){
			if(plantilla.getId().getIntParaTipoProvisionCod().equals(Constante.PARAM_T_TIPOPROVISION_CREDITOS)
					&& !plantilla.getId().getIntParaTipoSbsCod().equals(Constante.PARAM_T_TIPO_CREDITOSBS_TODOS)){
				for(Tabla tabla : listaTablaTipoRiesgo){
					tiempo = new Tiempo();
					tiempo.getId().setIntParaTipoSbsCod(plantilla.getId().getIntParaTipoSbsCod());
					tiempo.getId().setIntParaTipoProvisionCod(plantilla.getId().getIntParaTipoProvisionCod());				
					tiempo.setIntItemTipoCategoriaRiesgoCod(tabla.getIntIdDetalle());
					tiempo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaTiempoInterfaz.add(tiempo);
				}
			}
		}
	}
	
	private void cargarListaEspecificacion(){
		listaEspecificacionCredito = new ArrayList<Especificacion>();
		listaEspecificacionInterfazCredito = new ArrayList<Especificacion>();
		listaEspecificacionProciclico = new ArrayList<Especificacion>();
		listaEspecificacionInterfazProciclico = new ArrayList<Especificacion>();
		
		int cantidadEspecificacionCredito = 36;
		int cantidadEspecificacionProciclico = 17;
		
		Especificacion especificacion = null;
		Provision provision = null;
		
		
		//Carga de Especificaciones y Provisiones para Credito
		for(int i=0;i<cantidadEspecificacionCredito;i++){			
			especificacion = new Especificacion();
			for(Tabla tabla : listaTablaTipoRiesgo){
				provision = new Provision();
				provision.setIntParaTipoCategoriaRiesgoCod(tabla.getIntIdDetalle());
				provision.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				especificacion.getListaProvision().add(provision);
			}
			listaEspecificacionInterfazCredito.add(especificacion);
		}
		
		for(PlantillaDetalle plantillaDetalle : listaPlantillaDetalleCredito){
			especificacion = new Especificacion();
			especificacion.setIntItemPlantillaDetalle(plantillaDetalle.getId().getIntItemPlantillaDetalle());
			especificacion.setIntParaTipoProvisionCod(plantillaDetalle.getId().getIntParaTipoProvisionCod());
			especificacion.setIntParaTipoSbsCod(plantillaDetalle.getId().getIntParaTipoSbsCod());
			especificacion.setPlantillaDetalle(plantillaDetalle);
			for(Tabla tabla : listaTablaTipoRiesgo){
				provision = new Provision();
				provision.setIntParaTipoCategoriaRiesgoCod(tabla.getIntIdDetalle());
				provision.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				especificacion.getListaProvision().add(provision);
			}
			listaEspecificacionCredito.add(especificacion);
		}
		
		
		//Carga de Especificaciones y Prociclicos
		for(int i=0;i<cantidadEspecificacionProciclico;i++){			
			especificacion = new Especificacion();
			especificacion.setProciclico(new Prociclico());
			listaEspecificacionInterfazProciclico.add(especificacion);
		}
		
		for(PlantillaDetalle plantillaDetalle : listaPlantillaDetalleProciclico){
			especificacion = new Especificacion();
			especificacion.setIntItemPlantillaDetalle(plantillaDetalle.getId().getIntItemPlantillaDetalle());
			especificacion.setIntParaTipoProvisionCod(plantillaDetalle.getId().getIntParaTipoProvisionCod());
			especificacion.setIntParaTipoSbsCod(plantillaDetalle.getId().getIntParaTipoSbsCod());
			especificacion.setPlantillaDetalle(plantillaDetalle);			
			listaEspecificacionProciclico.add(especificacion);
		}
	}
	
	private void limpiarListaTiempo(){
		for(Tiempo tiempo : listaTiempo){
			tiempo.setIntTiempo(null);
		}
		for(Object o : listaTiempoInterfaz){
			((Tiempo)o).setIntTiempo(null);
		}
	}
	
	private void limpiarListaEspecificacion(){
		for(Especificacion especificacion : listaEspecificacionCredito){
			for(Provision provision : especificacion.getListaProvision()){
				provision.setBdMontoProvision(null);
			}
		}
		for(Object o : listaEspecificacionInterfazCredito){
			for(Provision provision : ((Especificacion)o).getListaProvision()){
				provision.setBdMontoProvision(null);
			}
		}
		
		for(Especificacion especificacion : listaEspecificacionProciclico){
			especificacion.getProciclico().setBdMontoProvision(null);
			especificacion.getProciclico().setBdMontoConstitucion2(null);
			especificacion.getProciclico().setBdMontoConstitucion4(null);
			especificacion.getProciclico().setBdMontoConstitucion6(null);			
		}
		for(Object o : listaEspecificacionInterfazProciclico){
			((Especificacion)o).getProciclico().setBdMontoProvision(null);
			((Especificacion)o).getProciclico().setBdMontoConstitucion2(null);
			((Especificacion)o).getProciclico().setBdMontoConstitucion4(null);
			((Especificacion)o).getProciclico().setBdMontoConstitucion6(null);
		}
	}
	
	private void filtrarListaEspecificacionCredito(){
		
		List<Provision> listaProv = null;
		List<Provision> listaProvAux = null;
		boolean especificacionPoseeMontoProvision = Boolean.FALSE;
		//List<Especificacion> listaEspecificacionAux = new ArrayList<Especificacion>();
		Especificacion especificacionAux = null;
		carteraNuevo.getListaEspecificacionProvision().clear();
		for(Especificacion especificacion : listaEspecificacionCredito){
			listaProv = especificacion.getListaProvision();
			listaProvAux = new ArrayList<Provision>();
			especificacionPoseeMontoProvision = Boolean.FALSE;
			for(Provision provision : listaProv){
				if(provision.getBdMontoProvision()!=null){
					especificacionPoseeMontoProvision = Boolean.TRUE;
					listaProvAux.add(provision);
					//break;
				}
			}
			if(especificacionPoseeMontoProvision){
				especificacionAux = new Especificacion();
				especificacionAux.setIntItemPlantillaDetalle(especificacion.getIntItemPlantillaDetalle());
				especificacionAux.setIntParaTipoProvisionCod(especificacion.getIntParaTipoProvisionCod());
				especificacionAux.setIntParaTipoSbsCod(especificacion.getIntParaTipoSbsCod());
				especificacionAux.setListaProvision(listaProvAux);
				carteraNuevo.getListaEspecificacionProvision().add(especificacionAux);
			}
		}		
	}
	
	
	private void filtrarListaEspecificacionProciclico(){
		
		carteraNuevo.getListaEspecificacionProciclico().clear();
		Prociclico prociclico = null;
		for(Especificacion especificacion : listaEspecificacionProciclico){
			prociclico = especificacion.getProciclico();
			if(prociclico.getBdMontoProvision()!=null && prociclico.getBdMontoConstitucion2()!=null 
					&& prociclico.getBdMontoConstitucion4() !=null	&& prociclico.getBdMontoConstitucion6()!=null){
				carteraNuevo.getListaEspecificacionProciclico().add(especificacion);
			}			
		}		
	}
	
	public void seleccionarCaduco(){
		buscarVigente = Boolean.FALSE;
	}
	
	public void seleccionarVigente(){
		buscarCaduco = Boolean.FALSE;
	}
	
	
	private void procesarListaEspecificacionProciclicoDeBdAInterfaz(){
		for(Especificacion especificacionBD : listaEspecificacionProciclico){
			switch (especificacionBD.getIntItemPlantillaDetalle()){
				case	64	:
					procesarEspecificacionProciclicoBD(especificacionBD, 0);
					break;					
				case	65	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 1);
					break;
					
					
				case	72	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 2);
					break;
				case	73	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 3);
					break;
					
					
				case	80	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 4);
					break;
				case	81	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 5);
					break;
					
					
				case	88	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 6);
					break;
				case	89	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 7);
					break;
					
					
				case	96	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 8);
					break;
				case	97	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 9);
					break;
					
					
				case	104	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 10);
					break;
				case	105	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 11);
					break;
					
					
				case	112	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 12);
					break;
				case	113	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 13);
					break;
				case	114	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 14);
					break;
					
				
				case	120	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 15);
					break;
				case	121	:	
					procesarEspecificacionProciclicoBD(especificacionBD, 16);
					break;
			}
		}
	}
	
	private void procesarEspecificacionProciclicoBD(Especificacion especificacionBD,int indice){
		Especificacion especificacion = (Especificacion)(listaEspecificacionInterfazProciclico.get(indice));
		especificacion.getProciclico().setBdMontoProvision(especificacionBD.getProciclico().getBdMontoProvision());
		especificacion.getProciclico().setBdMontoConstitucion2(especificacionBD.getProciclico().getBdMontoConstitucion2());
		especificacion.getProciclico().setBdMontoConstitucion4(especificacionBD.getProciclico().getBdMontoConstitucion4());
		especificacion.getProciclico().setBdMontoConstitucion6(especificacionBD.getProciclico().getBdMontoConstitucion6());
	}
	
	private void procesarEspecificacionCreditoBD(Especificacion especificacionBD,int indice){
		for(Provision provisionBD : especificacionBD.getListaProvision()){
			for(Provision provisionUI : ((Especificacion)listaEspecificacionInterfazCredito.get(indice)).getListaProvision()){
				if(provisionUI.getIntParaTipoCategoriaRiesgoCod().equals(provisionBD.getIntParaTipoCategoriaRiesgoCod())){
					provisionUI.setBdMontoProvision(provisionBD.getBdMontoProvision());
				}
			}			
		}
	}
	
	private void procesarListaTiempoDeBDAInterfaz(){
		for(Tiempo tiempo : listaTiempo){
			buscarTiempoInterfazPorTipoSBSYCategoria(tiempo.getId().getIntParaTipoSbsCod(), tiempo.getIntItemTipoCategoriaRiesgoCod()).setIntTiempo(tiempo.getIntTiempo());
		}
	}
	
	private void procesarListaEspecificacionCreditoDeBdAInterfaz(){
		for(Especificacion especificacionBD : listaEspecificacionCredito){
			switch (especificacionBD.getIntItemPlantillaDetalle()){
				case	1	:
					procesarEspecificacionCreditoBD(especificacionBD, 0);
					break;
				case	2	:
					procesarEspecificacionCreditoBD(especificacionBD, 1);
					break;
				case	4	:
					procesarEspecificacionCreditoBD(especificacionBD, 2);
					break;
				case	6	:	
					procesarEspecificacionCreditoBD(especificacionBD, 3);
					break;
							
					
				case	8	:	
					procesarEspecificacionCreditoBD(especificacionBD, 4);
					break;					
				case	9	:	
					procesarEspecificacionCreditoBD(especificacionBD, 5);
					break;					
				case	11	:	
					procesarEspecificacionCreditoBD(especificacionBD, 6);
					break;					
				case	13	:	
					procesarEspecificacionCreditoBD(especificacionBD, 7);
					break;
					
					
				case	15	:	
					procesarEspecificacionCreditoBD(especificacionBD, 8);
					break;					
				case	16	:	
					procesarEspecificacionCreditoBD(especificacionBD, 9);
					break;					
				case	18	:	
					procesarEspecificacionCreditoBD(especificacionBD, 10);
					break;				
				case	20	:	
					procesarEspecificacionCreditoBD(especificacionBD, 11);
					break;
				
					
				case	22	:	
					procesarEspecificacionCreditoBD(especificacionBD, 12);
					break;					
				case	23	:	
					procesarEspecificacionCreditoBD(especificacionBD, 13);
					break;					
				case	25	:	
					procesarEspecificacionCreditoBD(especificacionBD, 14);
					break;					
				case	27	:	
					procesarEspecificacionCreditoBD(especificacionBD, 15);
					break;
					
				
				case	29	:	
					procesarEspecificacionCreditoBD(especificacionBD, 16);
					break;					
				case	30	:	
					procesarEspecificacionCreditoBD(especificacionBD, 17);
					break;					
				case	32	:	
					procesarEspecificacionCreditoBD(especificacionBD, 18);
					break;				
				case	34	:	
					procesarEspecificacionCreditoBD(especificacionBD, 19);
					break;
					
					
				case	36	:
					procesarEspecificacionCreditoBD(especificacionBD, 20);
					break;					
				case	37	:
					procesarEspecificacionCreditoBD(especificacionBD, 21);
					break;					
				case	39	:
					procesarEspecificacionCreditoBD(especificacionBD, 22);
					break;					
				case	41	:
					procesarEspecificacionCreditoBD(especificacionBD, 23);
					break;
				
					
				case	45	:
					procesarEspecificacionCreditoBD(especificacionBD, 24);
					break;					
				//case	44	:
					//procesarEspecificacionCreditoBD(especificacionBD, 25);
					//break;					
				case	44	:	
					procesarEspecificacionCreditoBD(especificacionBD, 26);
					break;
				//case	48	:	
					//procesarEspecificacionCreditoBD(especificacionBD, 27);
					//break;
				case	46	:	
					procesarEspecificacionCreditoBD(especificacionBD, 28);
					break;
				
					
				case	52	:	
					procesarEspecificacionCreditoBD(especificacionBD, 29);
					break;					
				case	53	:	
					procesarEspecificacionCreditoBD(especificacionBD, 30);
					break;					
				case	55	:	
					procesarEspecificacionCreditoBD(especificacionBD, 31);
					break;					
				case	57	:	
					procesarEspecificacionCreditoBD(especificacionBD, 32);
					break;
					
					
				case	59	:	
					procesarEspecificacionCreditoBD(especificacionBD, 33);
					break;					
				case	60	:	
					procesarEspecificacionCreditoBD(especificacionBD, 34);
					break;					
				
					
				case	62	:	
					procesarEspecificacionCreditoBD(especificacionBD, 35);
					break;					
			}
		}
	}
	
	
	private Especificacion buscarEspecificacionCreditoPorPlantillaDetalle(Integer idPlantillaDetalle){
		for(Especificacion especificacion : listaEspecificacionCredito){
			if(especificacion.getIntItemPlantillaDetalle().equals(idPlantillaDetalle)){
				return especificacion;
			}
		}
		return null;
	}
	
	private Especificacion buscarEspecificacionProciclicoPorPlantillaDetalle(Integer idPlantillaDetalle){
		for(Especificacion especificacion : listaEspecificacionProciclico){
			if(especificacion.getIntItemPlantillaDetalle().equals(idPlantillaDetalle)){
				return especificacion;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private void imprimirPro(List<Especificacion> listaEsp){
		Prociclico pro = null;
		for(Object esp : listaEsp){
			pro = ((Especificacion)esp).getProciclico();
			log.info(pro.getBdMontoProvision()+" "+pro.getBdMontoConstitucion2()+" "+pro.getBdMontoConstitucion4()+" "+pro.getBdMontoConstitucion6());			
		}
	}
	
	private void imprimirTiempo(List<Tiempo> listaTiempo){
		//Prociclico pro = null;
		int i=0;
		String con  = "";
		for(Object o : listaTiempo){
			Tiempo tiempo = (Tiempo)o;
			con = con + " "+tiempo.getIntTiempo();
			if(i%5==4){
				log.info(con);
				con = "";
			}
			i++;
		}
	}
	
	private Tiempo buscarTiempoPorTipoSBSYCategoria(Integer idTipoSBS, Integer tipoCategoria){
		for(Tiempo tiempo : listaTiempo){
			if(tiempo.getId().getIntParaTipoSbsCod().equals(idTipoSBS)
				&& tiempo.getIntItemTipoCategoriaRiesgoCod().equals(tipoCategoria)){
				return tiempo;
			}
		}
		return null;
	}
	
	private Tiempo buscarTiempoInterfazPorTipoSBSYCategoria(Integer idTipoSBS, Integer tipoCategoria){
		for(Object o : listaTiempoInterfaz){
			Tiempo tiempo = (Tiempo)o;
			if(tiempo.getId().getIntParaTipoSbsCod().equals(idTipoSBS)
				&& tiempo.getIntItemTipoCategoriaRiesgoCod().equals(tipoCategoria)){
				return tiempo;
			}
		}
		return null;
	}
	
	private void procesarTiempoInterfaz(Integer tipoSBS){
		Tiempo tiempoInterfaz = null;		
		Tiempo tiempo = null;
		
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get((tipoSBS-1)*5+0);
		tiempo = buscarTiempoPorTipoSBSYCategoria(tipoSBS,1);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get((tipoSBS-1)*5+1);
		tiempo = buscarTiempoPorTipoSBSYCategoria(tipoSBS,2);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get((tipoSBS-1)*5+2);
		tiempo = buscarTiempoPorTipoSBSYCategoria(tipoSBS,3);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get((tipoSBS-1)*5+3);
		tiempo = buscarTiempoPorTipoSBSYCategoria(tipoSBS,4);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get((tipoSBS-1)*5+4);
		tiempo = buscarTiempoPorTipoSBSYCategoria(tipoSBS,5);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		
	}
	
	private void procesarListaTiempoDeInterfazABD()throws Exception{
		try{
		Tiempo tiempoInterfaz = null;		
		Tiempo tiempo = null;
		
		//imprimir(listaEspecificacionCredito);
		log.info("--interfazTiempo");
		imprimirTiempo(listaTiempoInterfaz);
		
		procesarTiempoInterfaz(1);
		procesarTiempoInterfaz(2);
		procesarTiempoInterfaz(3);
		procesarTiempoInterfaz(4);
		procesarTiempoInterfaz(5);
		procesarTiempoInterfaz(6);
		procesarTiempoInterfaz(7);
		procesarTiempoInterfaz(8);
		//procesarTiempoInterfaz(10);		
		
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get(40);
		tiempo = buscarTiempoPorTipoSBSYCategoria(9,1);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get(41);
		tiempo = buscarTiempoPorTipoSBSYCategoria(9,2);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get(42);
		tiempo = buscarTiempoPorTipoSBSYCategoria(9,3);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get(43);
		tiempo = buscarTiempoPorTipoSBSYCategoria(9,4);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());
		
		tiempoInterfaz = (Tiempo)listaTiempoInterfaz.get(44);
		tiempo = buscarTiempoPorTipoSBSYCategoria(9,5);
		tiempo.setIntTiempo(tiempoInterfaz.getIntTiempo());		
		
		log.info("--tiempoPer");
		imprimirTiempo(listaTiempo);
		
		filtrarListaTiempo();
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private void filtrarListaTiempo(){
		carteraNuevo.getListaTiempo().clear();
		for(Tiempo tiempo : listaTiempo){
			if(tiempo.getIntTiempo()!=null){
				log.info(tiempo);
				//Para cualquier fila de tiempo (excepto fila 9 / sbs 10)
				if(!tiempo.getId().getIntParaTipoSbsCod().equals(Constante.PARAM_T_TIPO_CREDITOSBS_OTROSPRODUCTOS)){
					carteraNuevo.getListaTiempo().add(tiempo);
				//Para el caso de la fila 9 / sbs 10 se añade un Tiempo por cada producto especial seleccionado
				}else{					
					for(Object o : listaProducto){
						Tabla tabla = (Tabla)o;
						if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA)) 
							&& tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPOCUENTA_FONDOSEPELIO) 
							&& tabla.getChecked()){
							Tiempo tiempoAux = new Tiempo();
							tiempoAux.getId().setIntParaTipoSbsCod(tiempo.getId().getIntParaTipoSbsCod());
							tiempoAux.getId().setIntParaTipoProvisionCod(tiempo.getId().getIntParaTipoProvisionCod());
							tiempoAux.setIntItemTipoCategoriaRiesgoCod(tiempo.getIntItemTipoCategoriaRiesgoCod());
							tiempoAux.setIntTiempo(tiempo.getIntTiempo());
							tiempoAux.setIntParaEstadoCod(tiempo.getIntParaEstadoCod());
							//CGD-11.01.2014
							//tiempoAux.setIntParaTipoOperacion(Constante.PARAM_T_IDENTIFICACAPTACION);
							//tiempoAux.setIntParaTipoConcepto(tabla.getIntIdDetalle());
							log.info("fondosepe:"+tiempoAux);
							carteraNuevo.getListaTiempo().add(tiempoAux);
						}
						if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA)) 
							&& tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPOCUENTA_FONDORETIRO) 
							&& tabla.getChecked()){
							Tiempo tiempoAux = new Tiempo();
							tiempoAux.getId().setIntParaTipoSbsCod(tiempo.getId().getIntParaTipoSbsCod());
							tiempoAux.getId().setIntParaTipoProvisionCod(tiempo.getId().getIntParaTipoProvisionCod());
							tiempoAux.setIntItemTipoCategoriaRiesgoCod(tiempo.getIntItemTipoCategoriaRiesgoCod());
							tiempoAux.setIntTiempo(tiempo.getIntTiempo());
							tiempoAux.setIntParaEstadoCod(tiempo.getIntParaEstadoCod());
							//CGD-11.01.2014
							//tiempoAux.setIntParaTipoOperacion(Constante.PARAM_T_IDENTIFICACAPTACION);
							//tiempoAux.setIntParaTipoConcepto(tabla.getIntIdDetalle());
							log.info("fondoreti:"+tiempoAux);
							carteraNuevo.getListaTiempo().add(tiempoAux);
						}
						if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA)) 
							&& tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPOCUENTA_MANTENIMIENTOCUENTA) 
							&& tabla.getChecked()){
							Tiempo tiempoAux = new Tiempo();
							tiempoAux.getId().setIntParaTipoSbsCod(tiempo.getId().getIntParaTipoSbsCod());
							tiempoAux.getId().setIntParaTipoProvisionCod(tiempo.getId().getIntParaTipoProvisionCod());
							tiempoAux.setIntItemTipoCategoriaRiesgoCod(tiempo.getIntItemTipoCategoriaRiesgoCod());
							tiempoAux.setIntTiempo(tiempo.getIntTiempo());
							tiempoAux.setIntParaEstadoCod(tiempo.getIntParaEstadoCod());
							//CGD-11.01.2014
							//tiempoAux.setIntParaTipoOperacion(Constante.PARAM_T_IDENTIFICACAPTACION);
							//tiempoAux.setIntParaTipoConcepto(tabla.getIntIdDetalle());
							log.info("mantenim:"+tiempo);
							carteraNuevo.getListaTiempo().add(tiempoAux);
						}
						if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO)) 
							&& tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD) 
							&& tabla.getChecked()){
							Tiempo tiempoAux = new Tiempo();
							tiempoAux.getId().setIntParaTipoSbsCod(tiempo.getId().getIntParaTipoSbsCod());
							tiempoAux.getId().setIntParaTipoProvisionCod(tiempo.getId().getIntParaTipoProvisionCod());
							tiempoAux.setIntItemTipoCategoriaRiesgoCod(tiempo.getIntItemTipoCategoriaRiesgoCod());
							tiempoAux.setIntTiempo(tiempo.getIntTiempo());
							tiempoAux.setIntParaEstadoCod(tiempo.getIntParaEstadoCod());
							//CGD-11.01.2014
							//tiempoAux.setIntParaTipoOperacion(Constante.PARAM_T_IDENTIFICACREDITO);
							//tiempoAux.setIntParaTipoConcepto(tabla.getIntIdDetalle());
							log.info("actividad:"+tiempoAux);
							carteraNuevo.getListaTiempo().add(tiempoAux);
						}						
					}
				}				
			}
		}		
	}
	
	private Especificacion procesarEspecificacionProciclicoInterfaz(Especificacion especificacion, Especificacion especificacionInterfaz, 
			Integer intIdPlantillaDetalle){
		
		especificacion = buscarEspecificacionProciclicoPorPlantillaDetalle(intIdPlantillaDetalle);
		
		especificacion.getProciclico().setIntParaTipoCategoriaRiesgoCod(Constante.PARAM_T_TIPOCATEGORIADERIESGO_NORMAL);
		especificacion.getProciclico().setBdMontoProvision(especificacionInterfaz.getProciclico().getBdMontoProvision());
		especificacion.getProciclico().setBdMontoConstitucion2(especificacionInterfaz.getProciclico().getBdMontoConstitucion2());
		especificacion.getProciclico().setBdMontoConstitucion4(especificacionInterfaz.getProciclico().getBdMontoConstitucion4());
		especificacion.getProciclico().setBdMontoConstitucion6(especificacionInterfaz.getProciclico().getBdMontoConstitucion6());
		especificacion.getProciclico().setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		return especificacion;
	}
	
	private void procesarListaEspecificacionProciclicoDeInterfazABD()throws Exception{
		try{
			Especificacion especificacionInterfaz = null;		
			Especificacion especificacion = null;
			
			log.info("prociclico interfaz");
			//imprimirPro(listaEspecificacionInterfazProciclico);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(0);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,64);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(1);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,65);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,66);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,67);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,68);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,69);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,70);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,71);
			
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(2);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,72);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(3);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,73);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,74);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,75);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,76);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,77);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,78);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,79);
			
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(4);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,80);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(5);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,81);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,82);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,83);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,84);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,85);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,86);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,87);
			
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(6);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,88);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(7);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,89);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,90);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,91);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,92);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,93);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,94);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,95);
			
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(8);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,96);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(9);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,97);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,98);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,99);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,100);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,101);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,102);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,103);
			
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(10);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,104);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(11);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,105);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,106);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,107);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,108);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,109);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,110);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,111);
			
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(12);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,112);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(13);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,113);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,117);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(14);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,114);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,115);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,116);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,118);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,119);
			
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(15);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,120);
			
			especificacionInterfaz = (Especificacion)listaEspecificacionInterfazProciclico.get(16);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,121);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,122);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,123);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,124);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,125);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,126);
			especificacion = procesarEspecificacionProciclicoInterfaz(especificacion, especificacionInterfaz,127);
			
			//log.info("--prociclico persiste antes filtro");
			//imprimirPro(listaEspecificacionProciclico);
			filtrarListaEspecificacionProciclico();
			//log.info("--prociclico persiste luego filtro");
			//imprimirPro(carteraNuevo.getListaEspecificacionProciclico());
			
		} catch(Exception e){
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private Especificacion procesarEspecificacionCreditoInterfaz(Especificacion especificacion, Especificacion especificacionInterfaz, BigDecimal normal){
		especificacion.getListaProvision().get(0).setBdMontoProvision(normal);
		especificacion.getListaProvision().get(1).setBdMontoProvision(especificacionInterfaz.getListaProvision().get(1).getBdMontoProvision());
		especificacion.getListaProvision().get(2).setBdMontoProvision(especificacionInterfaz.getListaProvision().get(2).getBdMontoProvision());
		especificacion.getListaProvision().get(3).setBdMontoProvision(especificacionInterfaz.getListaProvision().get(3).getBdMontoProvision());
		especificacion.getListaProvision().get(4).setBdMontoProvision(especificacionInterfaz.getListaProvision().get(4).getBdMontoProvision());		
		return especificacion;
	}
	
	private void procesarListaEspecificacionCreditoDeInterfazABD()throws Exception{
		try{
		Especificacion especificacionInterfaz = null;		
		Especificacion especificacion = null;
		BigDecimal normal = null;
		
		//imprimir(listaEspecificacionCredito);
		
		// Crédito Corporativo
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(0); 
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(1);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(1);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(2);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(2);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(4);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(3);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(6);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);	
		
		// Grandes Empresas
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(4);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(8);	
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(5);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(9);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(6);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(11);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(7);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(13);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
				
		// Medianas Empresas
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(8);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(15);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(9);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(16);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(10);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(18);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(11);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(20);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);		
		
		// Pequeñas Empresas
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(12);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(22);		
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(13);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(23);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(14);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(25);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(15);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(27);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);		
		
		// Microempresas
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(16);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(29);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(17);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(30);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(18);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(32);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(19);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(34);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);		
		
		// Consumo Revolvente
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(20);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(36);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(21);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(37);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(22);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(39);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		// Consumo No Revolvente
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(23);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(41);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(24);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(45);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(26);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(44);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(28);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(46);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		// Hipotecario para Vivienda
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(29);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(52);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(30);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(53);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(31);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(55);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(32);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(57);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);		
		
		// Todos los Créditos	
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(33);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(59);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(34);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(60);
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);		
		
		// Otros Productos
		especificacionInterfaz = (Especificacion)listaEspecificacionInterfazCredito.get(35);
		especificacion = buscarEspecificacionCreditoPorPlantillaDetalle(62);
		normal = especificacionInterfaz.getListaProvision().get(0).getBdMontoProvision();
		especificacion = procesarEspecificacionCreditoInterfaz(especificacion, especificacionInterfaz,normal);
		
		
		log.info("--Credito antes de filtro");
		//imprimir(listaEspecificacionCredito);
		
		filtrarListaEspecificacionCredito();
		
		log.info("--Credito luego de filtro");
		//imprimir(carteraNuevo.getListaEspecificacionProvision());
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	@SuppressWarnings("unused")
	private void imprimir(List<Especificacion> listaEsp){
		String str2 = "";
		List<Provision> listaP2 = null;
		for(Especificacion esp : listaEsp){
			listaP2 = esp.getListaProvision();
			for(Provision pr : listaP2){
				str2 = str2 + pr.getBdMontoProvision()+"    ";
			}
			log.info(str2);
			str2 = "";
		}
	}	
	
	private void cargarListaProducto(){
		try{
			listaProducto = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA));
			List<Tabla> listaProductoAux = new ArrayList<Tabla>();
			for(Object o : listaProducto){
				Tabla tabla = (Tabla)o;
				if(tabla.getStrIdAgrupamientoA()!=null && tabla.getStrIdAgrupamientoA().contains("E")){
					listaProductoAux.add(tabla);
				}
			}
			listaProducto = listaProductoAux;
			listaProducto.addAll(tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO)));
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			habilitarFechaFin = Boolean.TRUE;
			
			habilitarProvisionCreditos = Boolean.FALSE;
			habilitarProvisionProciclica = Boolean.FALSE;
			habilitarTiempoEvaluacion = Boolean.FALSE;
			habilitarModelosContables = Boolean.FALSE;
			seleccionaIndeterminado = Boolean.FALSE;
			carteraNuevo = new Cartera();
			limpiarChecks(listaProducto);
			limpiarListaEspecificacion();
			//limpiarListaTiempo();
			cargarListaTiempo();
			mostrarFilaOtrosProductos = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void buscar(){
		try{
			if(carteraFiltro.getIntParaEstadoCod()==Constante.PARAM_T_ESTADOUNIVERSAL_TODOS){
				carteraFiltro.setIntParaEstadoCod(null);
			}
			procesarNombreProducto();
			listaCartera = carteraFacade.buscarCartera(carteraFiltro, productoFiltro, buscarVigente, buscarCaduco);
			//listaCartera = carteraFacade.buscarCartera(carteraFiltro);
			productoFiltro = new Producto();
			registrarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	
	private void procesarNombreProducto(){
		log.info("--strNombreProducto:"+strNombreProductoFiltro);
		if(strNombreProductoFiltro.toUpperCase().contains("TODOS")){
			productoFiltro.getId().setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOCUENTA_TODOS);			
		}else{
			Tabla tabla = null;
			for(Object o : listaProducto){
				tabla = (Tabla)o;
				if(tabla.getStrDescripcion().contains(strNombreProductoFiltro)){
					log.info("encontro:"+tabla.getIntIdMaestro());
					break;
				}
			}
			log.info("ES:"+tabla.getIntIdMaestro());
			//Si el producto que se selecciono es un Credito
			if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO)))
				productoFiltro.getId().setIntParaTipoOperacionCod(Constante.PARAM_T_IDENTIFICACREDITO);
				
			//Si el producto que se selecciono es una Captacion
			if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA)))
				productoFiltro.getId().setIntParaTipoOperacionCod(Constante.PARAM_T_IDENTIFICACAPTACION);
			
			productoFiltro.getId().setIntParaTipoConceptoCod(tabla.getIntIdDetalle());
		}
		log.info(productoFiltro);
	}
	
	
	private Integer validarFilaEspecificacion(List<Provision> listaProvision){
		boolean existeValorEnFila = Boolean.FALSE;
		boolean existeNuloEnFila = Boolean.FALSE;
		Integer tipoFila = null;

		for(int i=1;i<listaProvision.size();i++){
			if(listaProvision.get(i).getBdMontoProvision()!=null){
				existeValorEnFila = Boolean.TRUE;
			}else{
				existeNuloEnFila = Boolean.TRUE;
			}
		}
		if(existeValorEnFila && existeNuloEnFila){
			tipoFila = filaError;
		}else{
			if(existeValorEnFila){
				tipoFila = filaValores;
			}
			if(existeNuloEnFila){
				tipoFila = filaNula;
			}
		}
		return tipoFila;
	}
	
	private Boolean validarTipoFilas(BigDecimal normal, HashSet<Integer> tipoFilas){
		boolean normalEsNulo = Boolean.FALSE;
		if(normal==null)
			normalEsNulo = Boolean.TRUE; 
		else 
			normalEsNulo = Boolean.FALSE;
		
		if(tipoFilas.contains(filaError))
			return Boolean.FALSE;
		
		if(normalEsNulo){
			if(tipoFilas.contains(filaValores))	return Boolean.FALSE;			
		}else{
			if(!tipoFilas.contains(filaValores))return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/** 
	 * Método que valida que se haya configurado al menos 
	 * una Provisión de Crédito o un Procíclico.
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * GTorresBrousset 2014.02.10
	 */
	private Boolean validarConfiguracionProvisiones() throws Exception {
		try {			
			for(Object o : listaEspecificacionInterfazCredito) {
				Especificacion e = (Especificacion) o;
				if(e.getListaProvision().get(1).getBdMontoProvision() != null) {
					return Boolean.TRUE;
				}
			}
			
			for(Object o : listaEspecificacionInterfazProciclico){
				Prociclico prociclico = ((Especificacion)o).getProciclico();
				if(prociclico.getBdMontoProvision() != null) {
					return Boolean.TRUE;
				}
			}

			return Boolean.FALSE;
		} catch(Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	/** 
	 * Método que valida que se hayan configurado los Tiempos de Evaluación 
	 * de cada Crédito configurado en la Provisión de Créditos.
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * GTorresBrousset 2014.02.10
	 */
	private Boolean validarListaTiempoCredito() throws Exception {
		try {
			Especificacion especificacion = null;
			BigDecimal normal[] = new BigDecimal[10];

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(0));
			normal[0] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(4));
			normal[1] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(8));
			normal[2] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(12));
			normal[3] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(16));
			normal[4] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(20));
			normal[5] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(23));
			normal[6] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(29));
			normal[7] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(33));
			normal[8] = especificacion.getListaProvision().get(0).getBdMontoProvision();

			especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(35));
			normal[9] = especificacion.getListaProvision().get(0).getBdMontoProvision();
			
			for(int i = 0; i < normal.length; i++) {
				if(normal[i] != null) {
					if(i == 8) {
						for(int j = 0; j < listaTiempoInterfaz.size() - 5; j++) {
							if(((Tiempo)listaTiempoInterfaz.get(j)).getIntTiempo() == null) {
								return Boolean.FALSE;
							}
						}
					}
					if(i == 9) {
						if(mostrarFilaOtrosProductos){
							if(((Tiempo)listaTiempoInterfaz.get((i-1)*5)).getIntTiempo() == null) {
									return Boolean.FALSE;
							}
						}
					} else if(((Tiempo)listaTiempoInterfaz.get(i*5)).getIntTiempo() == null) {
							return Boolean.FALSE;
					}
				}
			}
			
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	/** 
	 * Método que valida que se hayan configurado los Tiempos de Evaluación 
	 * de cada Crédito configurado en la Provisión de Procíclicos.
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * GTorresBrousset 2014.02.10
	 */
	private Boolean validarListaTiempoProciclico() throws Exception {
		try {
			BigDecimal montoProvision[] = new BigDecimal[listaEspecificacionInterfazProciclico.size()];
			for(int i = 0; i < listaEspecificacionInterfazProciclico.size(); i++){
				Prociclico prociclico = ((Especificacion)listaEspecificacionInterfazProciclico.get(i)).getProciclico();
				montoProvision[i] = prociclico.getBdMontoProvision();
			}
			
			for(int i = 0; i < montoProvision.length; i++) {
				if(montoProvision[i] != null) {
					if(((Tiempo)listaTiempoInterfaz.get((i/2)*5)).getIntTiempo() == null) {
						return Boolean.FALSE;
					}
				}
			}
			
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private Boolean validarListaTiempoInterfaz() throws Exception{
		try{
			HashSet<Integer> columnasTiempo = new HashSet<Integer>();
			
			for(int i=0;i<listaTiempoInterfaz.size()-4;i++){
				Tiempo tiempo = null;
				if(i%5==0){
					columnasTiempo.clear();
					tiempo = (Tiempo)listaTiempoInterfaz.get(i);
					columnasTiempo.add(tiempo.getIntTiempo());
					tiempo = (Tiempo)listaTiempoInterfaz.get(i+1);
					columnasTiempo.add(tiempo.getIntTiempo());
					tiempo = (Tiempo)listaTiempoInterfaz.get(i+2);
					columnasTiempo.add(tiempo.getIntTiempo());
					tiempo = (Tiempo)listaTiempoInterfaz.get(i+3);
					columnasTiempo.add(tiempo.getIntTiempo());
					tiempo = (Tiempo)listaTiempoInterfaz.get(i+4);
					columnasTiempo.add(tiempo.getIntTiempo());
					
					if(columnasTiempo.contains(null)){
						columnasTiempo.remove(null);
						if(!columnasTiempo.isEmpty()){
							return Boolean.FALSE;
						}
					}
				}
			}
			return Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private Boolean validarListaEspecificacionInterfazProciclico(){
		
		HashSet<BigDecimal> columnasEspecificacion = new HashSet<BigDecimal>();
		for(Object o : listaEspecificacionInterfazProciclico){
			columnasEspecificacion.clear();
			Prociclico prociclico = ((Especificacion)o).getProciclico();
			columnasEspecificacion.add(prociclico.getBdMontoProvision());
			columnasEspecificacion.add(prociclico.getBdMontoConstitucion2());
			columnasEspecificacion.add(prociclico.getBdMontoConstitucion4());
			columnasEspecificacion.add(prociclico.getBdMontoConstitucion6());		

			if(columnasEspecificacion.contains(null)){
				columnasEspecificacion.remove(null);
				if(!columnasEspecificacion.isEmpty()){
					return Boolean.FALSE;
				}
			}
		}
		
		return Boolean.TRUE;
	}
	
	private Boolean validarListaEspecificacionInterfazCredito(){
		
		Especificacion especificacion = null;
		BigDecimal normal = null;
		
		HashSet<Integer> tipoFilas = new HashSet<Integer>();
		
		/* Crédito Corporativo */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(0));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(1));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(2));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(3));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Grandes Empresas */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(4));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(5));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(6));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(7));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Medianas Empresas */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(8));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(9));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(10));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(11));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Pequeñas Empresas */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(12));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(13));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(14));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(15));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Microempresas */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(16));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(17));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(18));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(19));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Consumo Revolvente */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(20));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(21));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));	
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(22));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));	

		/* Consumo No Revolvente */	
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(23));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(24));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		//especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(25));
		//tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));	
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(26));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		//especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(27));
		//tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(28));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Hipotecario para Vivienda */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(29));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(30));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(31));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(32));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Todos los Créditos */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(33));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();
		
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(34));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));		
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		/* Otros Productos */
		tipoFilas.clear();
		especificacion = ((Especificacion)listaEspecificacionInterfazCredito.get(35));
		tipoFilas.add(validarFilaEspecificacion(especificacion.getListaProvision()));
		normal = especificacion.getListaProvision().get(0).getBdMontoProvision();		
		
		if(!validarTipoFilas(normal, tipoFilas))	return Boolean.FALSE;
		
		return Boolean.TRUE;
	}
	
	public void grabar(){
		boolean exito = Boolean.FALSE;
		String mensaje = "";
		try{
			//Inicio validaciones
			if(carteraNuevo.getStrNombre() == null || carteraNuevo.getStrNombre().isEmpty()){
				mensaje="Ocurrió un error durante el registro. Debe ingresar el Nombre de la Cartera de Créditos.";
				return;
			}
			
			boolean seSeleccionoUnProducto = Boolean.FALSE;
			for(Object o : listaProducto){
				Tabla tabla = (Tabla)o;
				if(tabla.getChecked()){
					seSeleccionoUnProducto = Boolean.TRUE;
					break;
				}
			}
			
			if(!seSeleccionoUnProducto){
				mensaje="Ocurrió un error durante el registro. Debe seleccionar al menos un Tipo de Producto.";
				return;
			}
			
			//Validación de Tipo de Cobranza
			//GTorresBrousset 2014.02.07
			if(carteraNuevo.getIntParaTipoCobranzaCod() == null) {
				mensaje = "Ocurrió un error durante el registro. Debe ingresar el Tipo de Cobranza.";
				return;
			}
			
			if(carteraNuevo.getDtFechaInicio()==null){
				mensaje = "Ocurrió un error durante el registro. Debe ingresar el Inicio del Rango de Fecha.";
				return;
			}
			
			if(habilitarFechaFin && carteraNuevo.getDtFechaFin()==null){
				mensaje = "Ocurrió un error durante el registro. Debe ingresar el Fin del Rango de Fecha.";
				return;
			}
			
			if(!validarListaEspecificacionInterfazCredito()){
				mensaje = "Ocurrió un error durante el registro. Debe ingresar correctamente las Provisiones de Crédito.";
				return;
			}
			
			if(!validarListaEspecificacionInterfazProciclico()){
				mensaje = "Ocurrió un error durante el registro. Debe ingresar correctamente los Prociclicos.";
				return;
			}

			//Valida el registro de al menos una Provisión de Crédito o un Procíclico
			//GTorresBrousset 2014.02.07
			if(!validarConfiguracionProvisiones()) {
				mensaje = "Ocurrió un error durante el registro. Debe ingresar al menos una Provisión de Crédito o un Procíclico.";
				return;
			}

			if(!validarListaTiempoInterfaz()){
				mensaje = "Ocurrió un error durante el registro. Debe ingresar correctamente los Tiempos.";
				return;
			}

			//Validación de Tiempo de Evaluación por cada Provisión ingresada
			//GTorresBrousset 2014.02.07
			if(!validarListaTiempoCredito()) {
				mensaje = "Ocurrió un error durante el registro. Debe ingresar los Tiempos correspondientes a las Provisiones ingresadas.";
				return;
			}

			//Validación de Tiempo de Evaluación por cada Procíclico ingresado
			//GTorresBrousset 2014.02.07
			if(!validarListaTiempoProciclico()) {
				mensaje = "Ocurrió un error durante el registro. Debe ingresar los Tiempos correspondientes a los Procíclicos ingresados.";
				return;
			}
			//Fin de validaciones
			
			procesarListaEspecificacionCreditoDeInterfazABD();
			procesarListaEspecificacionProciclicoDeInterfazABD();
			procesarListaTiempoDeInterfazABD();
			
			//Si es un nuevo registro a grabar
			if(registrarNuevo){
				agregarListaProducto();
				
				carteraNuevo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				//Para pruebas en QC usuario 93
				//carteraNuevo.setIntPersPersonaRegistraPk(Constante.PARAM_USUARIOSESION);
				carteraNuevo.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
				carteraNuevo.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				carteraNuevo.setDtFechaRegistra(new Date());
				/*log.info("grab");
				for(Tiempo t: carteraNuevo.getListaTiempo()){
					log.info("t:"+t);
				}*/
				carteraFacade.grabarCartera(carteraNuevo);
				habilitarGrabar = Boolean.FALSE;
				mensaje = "Se registró correctamente la cartera de créditos " + carteraNuevo.getStrNombre() + ".";
			
			//Si se esta modificando un registro
			}else{
				agregarListaProducto();
				carteraFacade.modificarCartera(carteraNuevo);
				habilitarGrabar = Boolean.FALSE;
				mensaje = "Se modificó correctamente la cartera de créditos " + carteraNuevo.getStrNombre() + ".";				
			}
			exito = Boolean.TRUE;
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			buscar();
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error durante el proceso de registro de cartera.";
			log.error(e.getMessage(),e);
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro de cartera.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	
	private void agregarListaProducto(){
		try {
			if(registrarNuevo){
				//Manejo de Producto cuando la cartera se registra
				carteraNuevo.getListaProducto().clear();
				for(Object o : listaProducto){
					Tabla tabla = (Tabla)o;
					Producto producto = new Producto();
					
					//log.info("tabla:"+tabla.getIntIdMaestro()+" "+tabla.getIntIdDetalle()+" "+tabla.getStrDescripcion());
					//Si se trata de una captacion
					if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA)))
						producto.getId().setIntParaTipoOperacionCod(Constante.PARAM_T_IDENTIFICACAPTACION);					
					//Si se trata de un credito
					if(tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO)))
						producto.getId().setIntParaTipoOperacionCod(Constante.PARAM_T_IDENTIFICACREDITO);
					
					producto.getId().setIntParaTipoConceptoCod(tabla.getIntIdDetalle());
					
					if(tabla.getChecked())
						producto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					else
						producto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					carteraNuevo.getListaProducto().add(producto);
					//log.info(producto);
				}
			}else{
				
				/*System.out.println("+++++++++++++++++++++++ listaProducto +++++++++++++++++++++++++++++");
				for(Object oh : listaProducto){
					Tabla tabla = (Tabla)oh;
					System.out.println("DESCRIPCION--> "+tabla.getStrDescripcion());
					System.out.println("MAESTRO--> "+tabla.getIntIdMaestro());
					System.out.println("DETALLE--> "+tabla.getIntIdDetalle());
					System.out.println("CHECK--> "+tabla.getChecked());
					System.out.println("ESTADO--> "+tabla.getIntEstado());
					System.out.println("---------------------------------------------------------------------------");
				}
				System.out.println("+++++++++++++++++++++++ carteraNuevo.getListaProducto() +++++++++++++++++++++++++++++");	
				for(Producto productoh : carteraNuevo.getListaProducto()){
					System.out.println("TIPO OPERACION--> "+productoh.getId().getIntParaTipoOperacionCod());
					System.out.println("TIPO CONCEPTO--> "+productoh.getId().getIntParaTipoConceptoCod());
					System.out.println("---------------------------------------------------------------------------");
				}*/
					
					
				//Manejo de producto cuando la cartera se modifica.
				List<Producto> listaProductoAux = new ArrayList<Producto>();
				for(Object o : listaProducto){
					Tabla tabla = (Tabla)o;
					Producto productoPersiste = null;
					for(Producto producto : carteraNuevo.getListaProducto()){
						if(producto.getId().getIntParaTipoOperacionCod().equals(Constante.PARAM_T_IDENTIFICACAPTACION)){
							if(producto.getId().getIntParaTipoConceptoCod().equals(tabla.getIntIdDetalle()) 
								&& tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA))){
								productoPersiste = producto;
								break;
							}
						}
						if(producto.getId().getIntParaTipoOperacionCod().equals(Constante.PARAM_T_IDENTIFICACREDITO)){
							if(producto.getId().getIntParaTipoConceptoCod().equals(tabla.getIntIdDetalle()) 
								&& tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO))){
								productoPersiste = producto;
								break;
							}
						}
					}
					if(productoPersiste != null && tabla.getChecked() != null){
						if(tabla.getChecked()){
							productoPersiste.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						}else{
							productoPersiste.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
						}
						listaProductoAux.add(productoPersiste);	
					}
									
				}
				carteraNuevo.setListaProducto(listaProductoAux);
			}
		} catch (Exception e) {
			log.error("Error en agregarListaProducto ---> "+e);
		}
		
	}
	
	private void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}
	
	public void manejarIndeterminado(){
		if(seleccionaIndeterminado == Boolean.TRUE){
			habilitarFechaFin = Boolean.FALSE;
			carteraNuevo.setDtFechaFin(null);
		}else{
			habilitarFechaFin = Boolean.TRUE;
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Cartera)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+registroSeleccionado);
			if(registroSeleccionado.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistro();
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				mostrarMensajeExito = Boolean.FALSE;
				mostrarMensajeError = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void limpiarChecks(List<Tabla> lista){
		for(int i=0;i<lista.size();i++){
			((Tabla)lista.get(i)).setChecked(Boolean.FALSE);
		}
	}
	
	private List<Provision> obtenerListaProvision(Especificacion especificacionCarga) throws Exception{
		List<Provision> listaProvisionCarga = null;
		try{
			List<Provision> listaProvisionAux = new ArrayList<Provision>();
			listaProvisionCarga = carteraFacade.getListaProvisionPorEspecificacion(especificacionCarga);
			for(Provision provision : listaProvisionCarga){
				if(provision.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					listaProvisionAux.add(provision);
				}
			}
			listaProvisionCarga = listaProvisionAux;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
		return listaProvisionCarga;
	}
	
	private void cargarRegistro()throws BusinessException{
		try{
			carteraNuevo = registroSeleccionado;
			limpiarChecks(listaProducto);
			for(int i = 0; i < carteraNuevo.getListaProducto().size(); i++){
				if(carteraNuevo.getListaProducto().get(i).getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					/* Se corrigió la colocación de los checks en la Lista Productos. */
					/* GTorresBrousset 2014.02.08 */
					for(int j = 0; j < listaProducto.size(); j++){
						if(((Tabla)listaProducto.get(j)).getIntIdDetalle().
								equals(carteraNuevo.getListaProducto().get(i).getId().getIntParaTipoConceptoCod())) {
							if((carteraNuevo.getListaProducto().get(i).getId().getIntParaTipoOperacionCod() == 1 &&
									((Tabla)listaProducto.get(j)).getIntIdMaestro() == 71) || 
									(carteraNuevo.getListaProducto().get(i).getId().getIntParaTipoOperacionCod() == 2 &&
											((Tabla)listaProducto.get(j)).getIntIdMaestro() == 89)) {
								((Tabla)listaProducto.get(j)).setChecked(Boolean.TRUE);
								break;
							}
						}
					}
				}
			}
			
			limpiarListaEspecificacion();
			List<Especificacion> listaEspecificacionCarga = carteraFacade.getListaEspecificacionPorIntItemCartera(carteraNuevo.getIntItemCartera());
			
			Prociclico prociclico = null;
			List<Provision> listaProvisionCarga = null;
			//Especificacion especificacion = null;
			int j = 0;
			for(Especificacion especificacionCarga : listaEspecificacionCarga){
				//Si es una especificacion de creditos
				if(especificacionCarga.getIntParaTipoProvisionCod().equals(Constante.PARAM_T_TIPOPROVISION_CREDITOS)){
					listaProvisionCarga = obtenerListaProvision(especificacionCarga);
					especificacionCarga.setListaProvision(listaProvisionCarga);
					//Obtiene el indice [j] de la Especificacion (en Persiste) que hace match con la especificacion que viene de BD 
					//[esto para tener acceso a la lista de Provision (en Persiste)]. Necesitamos la referencia desde la lista para poder actualizar
					//por el apuntador de la lista.
					for(int i=0;i<listaEspecificacionCredito.size();i++){
						j=i;
						if(listaEspecificacionCredito.get(i).getIntItemPlantillaDetalle().equals(especificacionCarga.getIntItemPlantillaDetalle())){
							break;
						}
					}
					//Una vez poseemos la Especificacion buscamos dentro de su lista de Provisiones para ver cual hace match con la provision que
					//tenemos desde base de datos (provisionCarga)
					for(Provision provisionCarga : listaProvisionCarga){
						//reemplazarProvision(provisionCarga,especificacion);
						for(Provision provision : listaEspecificacionCredito.get(j).getListaProvision()){
							if(provision.getIntParaTipoCategoriaRiesgoCod().equals(provisionCarga.getIntParaTipoCategoriaRiesgoCod())){
								provision.setBdMontoProvision(provisionCarga.getBdMontoProvision());
							}
						}
					}
				//Si es una especificacion de prociclicos
				}else{
					//obtenemos el prociclico asociado a la especificacion
					prociclico = carteraFacade.getProciclicoPorEspecificacion(especificacionCarga);
					log.info("carga:"+prociclico);
					//Solo tomamos en cuenta los prociclicos que se encuentren activos
					if(prociclico.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
						//Ubicamos la especificacion correspondiente en listaEspecificacionProciclico (persiste)
						for(int i=0;i<listaEspecificacionProciclico.size();i++){
							j=i;
							if(listaEspecificacionProciclico.get(i).getIntItemPlantillaDetalle().equals(especificacionCarga.getIntItemPlantillaDetalle())){
								break;
							}
						}
						//actualizamos la especificacion en persiste
						listaEspecificacionProciclico.get(j).getProciclico().setBdMontoProvision(prociclico.getBdMontoProvision());
						listaEspecificacionProciclico.get(j).getProciclico().setBdMontoConstitucion2(prociclico.getBdMontoConstitucion2());
						listaEspecificacionProciclico.get(j).getProciclico().setBdMontoConstitucion4(prociclico.getBdMontoConstitucion4());
						listaEspecificacionProciclico.get(j).getProciclico().setBdMontoConstitucion6(prociclico.getBdMontoConstitucion6());						
					}					
				}				
			}
			
			
			//log.info("--CARGA");
			//imprimirPro(listaEspecificacionProciclico);
			//log.info("--LUEGO");
			//imprimir(listaEspecificacion);
			
			procesarListaEspecificacionCreditoDeBdAInterfaz();
			procesarListaEspecificacionProciclicoDeBdAInterfaz();
			
			//cargamos listaTiempo
			limpiarListaTiempo();
			List<Tiempo> listaTiempoCarga = carteraFacade.getListaTiempoPorCartera(carteraNuevo);			
			for(Tiempo tiempo : listaTiempoCarga){
				if(tiempo.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					buscarTiempoPorTipoSBSYCategoria(tiempo.getId().getIntParaTipoSbsCod(), tiempo.getIntItemTipoCategoriaRiesgoCod()).setIntTiempo(tiempo.getIntTiempo());	
				}				
			}
			procesarListaTiempoDeBDAInterfaz();
			
			if(carteraNuevo.getDtFechaFin()==null){
				seleccionaIndeterminado = Boolean.TRUE;
				habilitarFechaFin = Boolean.FALSE;
			}else{
				seleccionaIndeterminado = Boolean.FALSE;
				habilitarFechaFin = Boolean.TRUE;
			}
			seleccionarProducto();
			mostrarProvisionCreditos();
			registrarNuevo = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
	
	public void modificarRegistro(){
		try {
			cargarRegistro();	
			habilitarGrabar = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		log.info("eliminar:"+registroSeleccionado);
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			listaCartera.remove(registroSeleccionado);
			//Para pruebas en QC ser usa el usuario 93
			//registroSeleccionado.setIntPersPersonaEliminaPk(Constante.PARAM_USUARIOSESION);
			registroSeleccionado.setIntPersPersonaEliminaPk(usuario.getIntPersPersonaPk());
			registroSeleccionado.setDtFechaElimina(new Date());
			registroSeleccionado = carteraFacade.eliminarCartera(registroSeleccionado);
		} catch (BusinessException e) {
			log.error(e.getMessage(),e);
		} catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(registroSeleccionado.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
			exito = Boolean.TRUE;
			mensaje = "El proceso de eliminación se realizo correctamente";
		}else{
			mensaje = "Ocurrio un error durante el proceso de eliminación";
		}
		mostrarMensaje(exito, mensaje);
		mostrarPanelInferior = Boolean.FALSE;
	}
	
	public void mostrarProvisionCreditos(){
		habilitarProvisionCreditos = Boolean.TRUE;
		habilitarProvisionProciclica = Boolean.FALSE;
		habilitarTiempoEvaluacion = Boolean.FALSE;
		habilitarModelosContables = Boolean.FALSE;
	}
	
	public void mostrarProvisionProciclica(){
		habilitarProvisionCreditos = Boolean.FALSE;
		habilitarProvisionProciclica = Boolean.TRUE;
		habilitarTiempoEvaluacion = Boolean.FALSE;
		habilitarModelosContables = Boolean.FALSE;
	}

	public void mostrarTiempoEvaluacion(){
		habilitarProvisionCreditos = Boolean.FALSE;
		habilitarProvisionProciclica = Boolean.FALSE;
		habilitarTiempoEvaluacion = Boolean.TRUE;
		habilitarModelosContables = Boolean.FALSE;
	}

	public void mostrarModelosContables(){
		habilitarProvisionCreditos = Boolean.FALSE;
		habilitarProvisionProciclica = Boolean.FALSE;
		habilitarTiempoEvaluacion = Boolean.FALSE;
		habilitarModelosContables = Boolean.TRUE;
	}
	
	public void seleccionarProducto(){
		Tabla tabla = null;
		mostrarFilaOtrosProductos = Boolean.FALSE;
		for(Object o : listaProducto){
			tabla = (Tabla)o;
			if(tabla.getChecked()){
				log.info("tab: "+tabla.getIntIdMaestro()+" "+tabla.getIntIdDetalle()+" "+tabla.getStrDescripcion());
				if( (	tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA))
							&& (	tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPOCUENTA_FONDOSEPELIO)) 
									||tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPOCUENTA_FONDORETIRO)
									||tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPOCUENTA_MANTENIMIENTOCUENTA)) 
					|| (tabla.getIntIdMaestro().equals(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO))  
							&& tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD))){
					mostrarFilaOtrosProductos = Boolean.TRUE;
					break;
				}
			}			
		}		
		log.info("mostrarFilaOtrosProductos:"+mostrarFilaOtrosProductos);
	}
	
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public Cartera getCarteraFiltro() {
		return carteraFiltro;
	}
	public void setCarteraFiltro(Cartera carteraFiltro) {
		this.carteraFiltro = carteraFiltro;
	}
	public List<Cartera> getListaCartera() {
		return listaCartera;
	}
	public void setListaCartera(List<Cartera> listaCartera) {
		this.listaCartera = listaCartera;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}
	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}
	public boolean isMostrarMensajeExito() {
		return mostrarMensajeExito;
	}
	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}
	public boolean isMostrarMensajeError() {
		return mostrarMensajeError;
	}
	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public boolean isRegistrarNuevo() {
		return registrarNuevo;
	}
	public void setRegistrarNuevo(boolean registrarNuevo) {
		this.registrarNuevo = registrarNuevo;
	}
	public boolean isHabilitarGrabar() {
		return habilitarGrabar;
	}
	public void setHabilitarGrabar(boolean habilitarGrabar) {
		this.habilitarGrabar = habilitarGrabar;
	}
	public Cartera getCarteraNuevo() {
		return carteraNuevo;
	}
	public void setCarteraNuevo(Cartera carteraNuevo) {
		this.carteraNuevo = carteraNuevo;
	}
	public List<Tabla> getListaProducto() {
		return listaProducto;
	}
	public void setListaProducto(List<Tabla> listaProducto) {
		this.listaProducto = listaProducto;
	}
	public boolean isHabilitarFechaFin() {
		return habilitarFechaFin;
	}
	public void setHabilitarFechaFin(boolean habilitarFechaFin) {
		this.habilitarFechaFin = habilitarFechaFin;
	}
	public boolean isHabilitarProvisionCreditos() {
		return habilitarProvisionCreditos;
	}
	public void setHabilitarProvisionCreditos(boolean habilitarProvisionCreditos) {
		this.habilitarProvisionCreditos = habilitarProvisionCreditos;
	}
	public boolean isHabilitarProvisionProciclica() {
		return habilitarProvisionProciclica;
	}
	public void setHabilitarProvisionProciclica(boolean habilitarProvisionProciclica) {
		this.habilitarProvisionProciclica = habilitarProvisionProciclica;
	}
	public boolean isHabilitarTiempoEvaluacion() {
		return habilitarTiempoEvaluacion;
	}
	public void setHabilitarTiempoEvaluacion(boolean habilitarTiempoEvaluacion) {
		this.habilitarTiempoEvaluacion = habilitarTiempoEvaluacion;
	}
	public boolean isHabilitarModelosContables() {
		return habilitarModelosContables;
	}
	public void setHabilitarModelosContables(boolean habilitarModelosContables) {
		this.habilitarModelosContables = habilitarModelosContables;
	}
	public boolean isSeleccionaIndeterminado() {
		return seleccionaIndeterminado;
	}
	public void setSeleccionaIndeterminado(boolean seleccionaIndeterminado) {
		this.seleccionaIndeterminado = seleccionaIndeterminado;
	}
	public boolean isBuscarVigente() {
		return buscarVigente;
	}
	public void setBuscarVigente(boolean buscarVigente) {
		this.buscarVigente = buscarVigente;
	}
	public boolean isBuscarCaduco() {
		return buscarCaduco;
	}
	public void setBuscarCaduco(boolean buscarCaduco) {
		this.buscarCaduco = buscarCaduco;
	}
	public Cartera getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(Cartera registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public List<Especificacion> getListaEspecificacionInterfazCredito() {
		return listaEspecificacionInterfazCredito;
	}
	public void setListaEspecificacionInterfazCredito(List<Especificacion> listaEspecificacionInterfazCredito) {
		this.listaEspecificacionInterfazCredito = listaEspecificacionInterfazCredito;
	}
	public List<Especificacion> getListaEspecificacionInterfazProciclico() {
		return listaEspecificacionInterfazProciclico;
	}
	public void setListaEspecificacionInterfazProciclico(List<Especificacion> listaEspecificacionInterfazProciclico) {
		this.listaEspecificacionInterfazProciclico = listaEspecificacionInterfazProciclico;
	}
	public List<Tiempo> getListaTiempoInterfaz() {
		return listaTiempoInterfaz;
	}
	public void setListaTiempoInterfaz(List<Tiempo> listaTiempoInterfaz) {
		this.listaTiempoInterfaz = listaTiempoInterfaz;
	}
	public boolean isMostrarFilaOtrosProductos() {
		return mostrarFilaOtrosProductos;
	}
	public void setMostrarFilaOtrosProductos(boolean mostrarFilaOtrosProductos) {
		this.mostrarFilaOtrosProductos = mostrarFilaOtrosProductos;
	}
	public String getStrNombreProductoFiltro() {
		return strNombreProductoFiltro;
	}
	public void setStrNombreProductoFiltro(String strNombreProductoFiltro) {
		this.strNombreProductoFiltro = strNombreProductoFiltro;
	}
}