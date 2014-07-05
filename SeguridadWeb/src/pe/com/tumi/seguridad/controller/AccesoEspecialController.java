package pe.com.tumi.seguridad.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.domain.AccesoEspecial;
import pe.com.tumi.seguridad.domain.AccesoEspecialDet;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.RegistroPc;
import pe.com.tumi.seguridad.domain.SolicitudCambio;
import pe.com.tumi.seguridad.service.impl.AccesoEspecialServiceImpl;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.seguridad.service.impl.RegistroPcServiceImpl;
import pe.com.tumi.seguridad.usuario.domain.PerfilUsuario;
import pe.com.tumi.seguridad.usuario.domain.SucursalUsuario;
import pe.com.tumi.usuario.service.impl.UsuarioServiceImpl;

public class AccesoEspecialController extends GenericController {
	private    	AccesoEspecialServiceImpl 	accesoEspecialService; 
	private		List 						beanListAccesosFueraHora;
	private 	AccesoEspecial				beanAccesoFueraHora = new AccesoEspecial();
	private 	Integer						intCboEmpAccesoFH;
	private 	Integer						intCboUsuAccesoFH;
	private 	Integer						intCboResponsableFH;
	private 	Integer						intCboEstadoFH;
	private 	Integer 					intCboMotivoFH;
	private    	UsuarioServiceImpl	 		usuarioPerfilService;
	private 	List 						beanListSucursalesUsuFH;
	private 	List 						beanListPerfilesUsuFH;
	private 	List 						beanListDiasFH = new ArrayList<SelectItem>();
	private 	List		 				listDiasFH = new ArrayList<String>();
	private 	SimpleDateFormat			sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private 	Boolean						blnAccesosFH = false;
	private 	Date						dtInicioFHBusq;
	private 	Date						dtFinFHBusq;
	
	private 	Integer						intCboEmpAccesoSuc;
	private 	Integer						intCboUsuAccesoSuc;
	private 	Integer						intCboResponsableSuc;
	private 	Integer						intCboEstadoSuc;
	private 	Integer 					intCboMotivoSuc;
	private 	Date						dtRangoInicioSuc;
	private 	Date						dtRangoFinSuc;
	private		List 						beanListAccesoSucursales;
	private 	Boolean						blnAccesosSuc = false;
	private 	AccesoEspecial				beanAccesoSucursal = new AccesoEspecial();
	private 	List 						beanListSucursalesUsuSuc;
	private 	List 						beanListPerfilesUsuSuc;
	private 	List 						beanListDiasSuc = new ArrayList<SelectItem>();
	private 	List		 				listDiasSuc = new ArrayList<String>();
	
	private 	Integer						intCboEmpAccesoCab;
	private 	Integer						intCboUsuAccesoCab;
	private 	Integer						intCboResponsableCab;
	private 	Integer						intCboEstadoCab;
	private 	Integer 					intCboMotivoCab;
	private 	Date						dtRangoInicioCab;
	private 	Date						dtRangoFinCab;
	private		List 						beanListAccesoCabinas;
	private 	Boolean						blnAccesosCab = false;
	private 	AccesoEspecial				beanAccesoCabina = new AccesoEspecial();
	private 	List 						beanListDiasCab = new ArrayList<SelectItem>();
	private 	List		 				listDiasCab = new ArrayList<String>();
	private 	List 						beanListSucursalesUsuCab;
	private 	List 						beanListPerfilesUsuCab;
	
	
	public void listarAccesosFueraHora(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.listarAccesosFueraHora-----------------------------");
		
		log.info("getIntCboEmpAccesoFH(): "+getIntCboEmpAccesoFH());
		log.info("getIntCboEstadoFH(): "+getIntCboEstadoFH());
		log.info("getIntCboMotivoFH(): "+getIntCboMotivoFH());
		log.info("getIntCboUsuAccesoFH(): "+getIntCboUsuAccesoFH());
		log.info("getIntCboResponsableFH(): "+getIntCboResponsableFH());
		log.info("getDtInicioFHBusq(): "+getDtInicioFHBusq());
		log.info("getDtFinFHBusq(): "+getDtFinFHBusq());
		
		//Fechas
		String rangoFecha1 = (getDtInicioFHBusq()!=null)?sdf.format(getDtInicioFHBusq()):null;
		String rangoFecha2 = (getDtFinFHBusq()!=null)?sdf.format(getDtFinFHBusq()):null;
		
		HashMap prmtAccesos = new HashMap();
		prmtAccesos.put("pIntIdEmpresa", (getIntCboEmpAccesoFH()==null || getIntCboEmpAccesoFH()==0)?null:getIntCboEmpAccesoFH());
		prmtAccesos.put("pIntIdEstado", (getIntCboEstadoFH()==null || getIntCboEstadoFH()==0)?null:getIntCboEstadoFH());
		prmtAccesos.put("pIntIdMotivo", (getIntCboMotivoFH()==null || getIntCboMotivoFH()==0)?null:getIntCboMotivoFH());
		prmtAccesos.put("pIntIdUsuario", (getIntCboUsuAccesoFH()==null || getIntCboUsuAccesoFH()==0)?null:getIntCboUsuAccesoFH());
		prmtAccesos.put("pIntIdResponsable", (getIntCboResponsableFH()==null || getIntCboResponsableFH()==0)?null:getIntCboResponsableFH());
		prmtAccesos.put("pRangoFecha1", rangoFecha1);
		prmtAccesos.put("pRangoFecha2", rangoFecha2);
		prmtAccesos.put("pTipoConsulta", "fueraHora");
		
		ArrayList listaAccesosFueraHora = new ArrayList();
		listaAccesosFueraHora = listarAccesosEspeciales(event,prmtAccesos);
		setBeanListAccesosFueraHora(listaAccesosFueraHora);
	}
	
	public void listarAccesosSucursales(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.listarAccesosSucursales-----------------------------");
		
		log.info("getIntCboEmpAccesoSuc(): "+getIntCboEmpAccesoSuc());
		log.info("getIntCboEstadoSuc(): "+getIntCboEstadoSuc());
		log.info("getIntCboMotivoSuc(): "+getIntCboMotivoSuc());
		log.info("getIntCboUsuAccesoSuc(): "+getIntCboUsuAccesoSuc());
		log.info("getIntCboResponsableSuc(): "+getIntCboResponsableSuc());
		log.info("getDtRangoInicioSuc(): "+getDtRangoInicioSuc());
		log.info("getDtRangoFinSuc(): "+getDtRangoFinSuc());
		
		//Fechas
		String rangoFecha1 = (getDtRangoInicioSuc()!=null)?sdf.format(getDtRangoInicioSuc()):null;
		String rangoFecha2 = (getDtRangoFinSuc()!=null)?sdf.format(getDtRangoFinSuc()):null;
		
		HashMap prmtAccesos = new HashMap();
		prmtAccesos.put("pIntIdEmpresa", (getIntCboEmpAccesoSuc()==null || getIntCboEmpAccesoSuc()==0)?null:getIntCboEmpAccesoSuc());
		prmtAccesos.put("pIntIdEstado", (getIntCboEstadoSuc()==null || getIntCboEstadoSuc()==0)?null:getIntCboEstadoSuc());
		prmtAccesos.put("pIntIdMotivo", (getIntCboMotivoSuc()==null || getIntCboMotivoSuc()==0)?null:getIntCboMotivoSuc());
		prmtAccesos.put("pIntIdUsuario", (getIntCboUsuAccesoSuc()==null || getIntCboUsuAccesoSuc()==0)?null:getIntCboUsuAccesoSuc());
		prmtAccesos.put("pIntIdResponsable", (getIntCboResponsableSuc()==null || getIntCboResponsableSuc()==0)?null:getIntCboResponsableSuc());
		prmtAccesos.put("pRangoFecha1", rangoFecha1);
		prmtAccesos.put("pRangoFecha2", rangoFecha2);
		prmtAccesos.put("pTipoConsulta", "sucursales");
		
		ArrayList listaAccesosSucursales = new ArrayList();
		listaAccesosSucursales = listarAccesosEspeciales(event,prmtAccesos);
		setBeanListAccesoSucursales(listaAccesosSucursales);
	}
	
	public void listarAccesosCabinas(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.listarAccesosCabinas-----------------------------");
		
		log.info("getIntCboEmpAccesoCab(): "+getIntCboEmpAccesoCab());
		log.info("getIntCboEstadoCab(): "+getIntCboEstadoCab());
		log.info("getIntCboMotivoCab(): "+getIntCboMotivoCab());
		log.info("getIntCboUsuAccesoCab(): "+getIntCboUsuAccesoCab());
		log.info("getIntCboResponsableCab(): "+getIntCboResponsableCab());
		log.info("getDtRangoInicioCab(): "+getDtRangoInicioCab());
		log.info("getDtRangoFinCab(): "+getDtRangoFinCab());
		
		//Fechas
		String rangoFecha1 = (getDtRangoInicioCab()!=null)?sdf.format(getDtRangoInicioCab()):null;
		String rangoFecha2 = (getDtRangoFinCab()!=null)?sdf.format(getDtRangoFinCab()):null;
		
		HashMap prmtAccesos = new HashMap();
		prmtAccesos.put("pIntIdEmpresa", (getIntCboEmpAccesoCab()==null || getIntCboEmpAccesoCab()==0)?null:getIntCboEmpAccesoCab());
		prmtAccesos.put("pIntIdEstado", (getIntCboEstadoCab()==null || getIntCboEstadoCab()==0)?null:getIntCboEstadoCab());
		prmtAccesos.put("pIntIdMotivo", (getIntCboMotivoCab()==null || getIntCboMotivoCab()==0)?null:getIntCboMotivoCab());
		prmtAccesos.put("pIntIdUsuario", (getIntCboUsuAccesoCab()==null || getIntCboUsuAccesoCab()==0)?null:getIntCboUsuAccesoCab());
		prmtAccesos.put("pIntIdResponsable", (getIntCboResponsableCab()==null || getIntCboResponsableCab()==0)?null:getIntCboResponsableCab());
		prmtAccesos.put("pRangoFecha1", rangoFecha1);
		prmtAccesos.put("pRangoFecha2", rangoFecha2);
		prmtAccesos.put("pTipoConsulta", "cabinas");
		
		ArrayList listaAccesosCabinas = new ArrayList();
		listaAccesosCabinas = listarAccesosEspeciales(event,prmtAccesos);
		setBeanListAccesoCabinas(listaAccesosCabinas);
	}

	public ArrayList listarAccesosEspeciales(ActionEvent event, HashMap prmtAccesos) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.listarAccesosEspeciales-----------------------------");
		setService(accesoEspecialService);
		
		ArrayList listaAccesosEspeciales = new ArrayList();
		listaAccesosEspeciales = getService().listarAccesosEspeciales(prmtAccesos);
		log.info("listaAccesosEspeciales.size(): "+listaAccesosEspeciales.size());
		
		for (int i=0; i<listaAccesosEspeciales.size(); i++){
			AccesoEspecial acceso = new AccesoEspecial();
			acceso = (AccesoEspecial)listaAccesosEspeciales.get(i);
			String nombreUsu = acceso.getStrNombreUsuario();
			String apepatUsu = acceso.getStrApepatUsuario();
			String apematUsu = acceso.getStrApematUsuario();
			acceso.setStrFullNameUsu(nombreUsu+" "+apepatUsu+" "+apematUsu);
			String nombreResp = acceso.getStrNombreResp();
			String apepatResp = acceso.getStrApepatResp();
			String apematResp = acceso.getStrApematResp();
			acceso.setStrFullNameResp(nombreResp+" "+apepatResp+" "+apematResp);
			//Fechas
			String fechIni = acceso.getStrFechaInicio();
			String fechFin = acceso.getStrFechaFin();
			acceso.setStrRangoFechas(fechIni+" - "+fechFin);
			//Tipo Consulta
		}
		
		return listaAccesosEspeciales;
	}
	
	public void modificarRegistroAcceso(ActionEvent event) throws DaoException, ParseException{
		log.info("-----------------------Debugging accesoEspecialController.modificarRegistroAcceso-----------------------------");
		String strNombreTab = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelAccesos:hiddenNombreTab");
		log.info("strNombreTab : "+strNombreTab);
		
		if(strNombreTab.equals("accesosFH")){
			modificarAccesoFH(event);
		}else if(strNombreTab.equals("accesosSucursales")){
			modificarAccesoSucursal(event);
		}else if(strNombreTab.equals("accesosCabinas")){
			modificarAccesoCabinas(event);
		}
	}
	
	public void modificarAccesoFH(ActionEvent event) throws DaoException, ParseException{
		log.info("-----------------------Debugging accesoEspecialController.modificarAccesoFH-----------------------------");
		
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = modificarAccesoEspecial(event, listDiasFH);
		setBeanAccesoFueraHora(acceso);
		setBlnAccesosFH(true);
	}
	
	public void modificarAccesoSucursal(ActionEvent event) throws DaoException, ParseException{
		log.info("-----------------------Debugging accesoEspecialController.modificarAccesoSucursal-----------------------------");
		
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = modificarAccesoEspecial(event, listDiasSuc);
		setBeanAccesoSucursal(acceso);
		setBlnAccesosSuc(true);
	}
	
	public void modificarAccesoCabinas(ActionEvent event) throws DaoException, ParseException{
		log.info("-----------------------Debugging accesoEspecialController.modificarAccesoCabinas-----------------------------");
		
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = modificarAccesoEspecial(event, listDiasCab);
		setBeanAccesoCabina(acceso);
		setBlnAccesosCab(true);
	}
	
	public AccesoEspecial modificarAccesoEspecial(ActionEvent event, List listaDias) throws DaoException, ParseException{
		log.info("-----------------------Debugging accesoEspecialController.modificarAccesoEspecial-----------------------------");
		setService(accesoEspecialService);
		
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelAccesos:hiddenIdEmpAcceso");
		log.info("strIdEmpresa : "+strIdEmpresa);
		String strIdUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelAccesos:hiddenIdUsuAcceso");
		log.info("strIdUsuario : "+strIdUsuario);
		String strFechaIni = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelAccesos:hiddenFechaIniAcceso");
		log.info("strFechaIni : "+strFechaIni);
		
		HashMap prmtAccesos = new HashMap();
		prmtAccesos.put("pIntIdEmpresa", (strIdEmpresa!=null)?Integer.parseInt(strIdEmpresa):null);
		prmtAccesos.put("pIntIdUsuario", (strIdUsuario!=null)?Integer.parseInt(strIdUsuario):null);
		prmtAccesos.put("pFechaInicio", strFechaIni);
		
		ArrayList listaAccesoEspecial = new ArrayList();
		listaAccesoEspecial = getService().listarAccesosEspeciales(prmtAccesos);
		log.info("listaRegistroPc.size(): "+listaAccesoEspecial.size());
		
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = (AccesoEspecial)listaAccesoEspecial.get(0);
		log.info("acceso.getIntIdEmpresa(): "+acceso.getIntIdEmpresa());
		log.info("acceso.getIntIdUsuario(): "+acceso.getIntIdUsuario());
		log.info("acceso.getStrFechaInicio(): "+acceso.getStrFechaInicio());
		log.info("acceso.getStrFechaFin(): "+acceso.getStrFechaFin());
		log.info("acceso.getStrObservacion(): "+acceso.getStrObservacion());
		log.info("acceso.getIntIdMotivo(): "+acceso.getIntIdMotivo());
		log.info("acceso.getIntIdResponsable(): "+acceso.getIntIdResponsable());
		log.info("acceso.getIntAccesoFeriados(): "+acceso.getIntAccesoFeriados());
		
		acceso.setDtFechaInicio(sdf.parse(acceso.getStrFechaInicio()));
		acceso.setDtFechaFin(sdf.parse(acceso.getStrFechaFin()));
		log.info("acceso.getDtFechaInicio(): "+acceso.getDtFechaInicio());
		log.info("acceso.getDtFechaFin(): "+acceso.getDtFechaFin());
		
		Boolean feriados = (acceso.getIntAccesoFeriados()==1)?true:false;
		acceso.setBlnAccesoFeriados(feriados);
		
		//Detalle de Accesos Fuera de Hora
		HashMap prmtAccesosDet = new HashMap();
		prmtAccesosDet.put("pIntIdEmpresa", (strIdEmpresa!=null)?Integer.parseInt(strIdEmpresa):null);
		prmtAccesosDet.put("pIntIdUsuario", (strIdUsuario!=null)?Integer.parseInt(strIdUsuario):null);
		prmtAccesosDet.put("pFechaInicio", strFechaIni);
		
		ArrayList listaAccesoDet = new ArrayList();
		listaAccesoDet = getService().listarAccesosDetalle(prmtAccesosDet);
		log.info("listaAccesoDet.size(): "+listaAccesoDet.size());
		
		listaDias.clear();
		for(int i=0; i<listaAccesoDet.size(); i++){
			AccesoEspecialDet accesoDet = new AccesoEspecialDet();
			accesoDet = (AccesoEspecialDet) listaAccesoDet.get(i);
			listaDias.add(""+accesoDet.getIntIdDia());
		}
		
		//Retorna el bean AccesoEspecial
		return acceso;
	}
	
	public void grabarAccesoEspecialFH(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.grabarAccesoEspecialFH-----------------------------");
		setService(accesoEspecialService);
		
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = getBeanAccesoFueraHora();
		grabarAccesoEspecial(event, acceso, getListDiasFH());
		
		listarAccesosFueraHora(event);
	}
	
	public void grabarAccesoSucursal(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.grabarAccesoSucursal-----------------------------");
		setService(accesoEspecialService);
		
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = getBeanAccesoSucursal();
		acceso.setIntIdEmpresaSuc(acceso.getIntIdEmpresa());
		grabarAccesoEspecial(event, acceso, getListDiasSuc());
		
		listarAccesosSucursales(event);
	}
	
	public void grabarAccesoCabina(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.grabarAccesoCabina-----------------------------");
		setService(accesoEspecialService);
		
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = getBeanAccesoCabina();
		acceso.setIntAccesoRemoto(1);
		grabarAccesoEspecial(event, acceso, getListDiasCab());
		
		listarAccesosCabinas(event);
	}
	
	public void grabarAccesoEspecial(ActionEvent event, AccesoEspecial acceso, List listDias) throws DaoException{
		log.info("-----------------------Debugging accesoEspecialController.grabarAccesoEspecialFH-----------------------------");
		setService(accesoEspecialService);
		
		log.info("acceso.getIntIdEmpresa(): "+acceso.getIntIdEmpresa());
		log.info("acceso.getIntIdUsuario(): "+acceso.getIntIdUsuario());
		log.info("acceso.getDtFechaInicio(): "+acceso.getDtFechaInicio());
		log.info("acceso.getDtFechaFin(): "+acceso.getDtFechaFin());
		log.info("acceso.getIntIdSucursal(): "+acceso.getIntIdSucursal());
		log.info("acceso.getIntIdMotivo(): "+acceso.getIntIdMotivo());
		log.info("acceso.getIntIdResponsable(): "+acceso.getIntIdResponsable());
		log.info("acceso.getStrObservacion(): "+acceso.getStrObservacion());
		log.info("acceso.getBlnAccesoFeriados(): "+acceso.getBlnAccesoFeriados());
		//Fechas
		acceso.setStrFechaInicio(sdf.format(acceso.getDtFechaInicio()));
		acceso.setStrFechaFin(sdf.format(acceso.getDtFechaFin()));
		log.info("acceso.getStrFechaInicio(): "+acceso.getStrFechaInicio());
		log.info("acceso.getStrFechaFin(): "+acceso.getStrFechaFin());
		//Feriados
		Integer feriados = (acceso.getBlnAccesoFeriados()==true)?1:0;
		acceso.setIntAccesoFeriados(feriados);
		//Estado
		acceso.setIntIdEstado(1);
		//Sucursal
		if(acceso.getIntIdSucursal()!=null && acceso.getIntIdSucursal()==0){
			acceso.setIntIdSucursal(null);
		}
		
		getService().grabarAccesoEspecial(acceso);
		
		//Grabar Detalle de Accesos Especiales
		ArrayList listaDias = new ArrayList();
		listaDias = (ArrayList) listDias;
		
		String strHoraInicio = acceso.getStrFechaInicio();
		for(int i=0; i<strHoraInicio.length(); i++){
			char c = strHoraInicio.charAt(i);
			if((c+"").equals(" ")){
				strHoraInicio = strHoraInicio.substring(i+1);
				break;
			}
		}
		log.info("strHoraInicio: "+strHoraInicio);
		String strHoraFin = acceso.getStrFechaFin();
		for(int i=0; i<strHoraFin.length(); i++){
			char c = strHoraFin.charAt(i);
			if((c+"").equals(" ")){
				strHoraFin = strHoraFin.substring(i+1);
				break;
			}
		}
		log.info("strHoraFin: "+strHoraFin);
		
		log.info("listaDias.size(): "+listaDias.size());
		for(int i=0; i<listaDias.size(); i++){
			String dia = (String)listaDias.get(i);
			log.info("Dia Checkbox: "+dia);
			AccesoEspecialDet accesoDet = new AccesoEspecialDet();
			accesoDet.setIntIdEmpresa(acceso.getIntIdEmpresa());
			accesoDet.setIntIdUsuario(acceso.getIntIdUsuario());
			accesoDet.setStrFechaInicio(acceso.getStrFechaInicio());
			accesoDet.setIntIdDia(Integer.parseInt(dia));
			accesoDet.setStrHoraInicio(strHoraInicio);
			accesoDet.setStrHoraFin(strHoraFin);
			accesoDet.setIntConta(i);
			getService().grabarAccesoEspecialDet(accesoDet);
		}
	}
	
	public void eliminarAccesoEspecial(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging AccesoEspecialController.eliminarAccesoEspecial-----------------------------");
		setService(accesoEspecialService);
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelAccesos:hiddenIdEmpAcceso");
		log.info("strIdEmpresa : "+strIdEmpresa);
		String strIdUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelAccesos:hiddenIdUsuAcceso");
		log.info("strIdUsuario : "+strIdUsuario);
		String strFechaIni = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelAccesos:hiddenFechaIniAcceso");
		log.info("strFechaIni : "+strFechaIni);
		
		HashMap prmtAccesos = new HashMap();
		prmtAccesos.put("pIntIdEmpresa", (strIdEmpresa!=null)?Integer.parseInt(strIdEmpresa):null);
		prmtAccesos.put("pIntIdUsuario", (strIdUsuario!=null)?Integer.parseInt(strIdUsuario):null);
		prmtAccesos.put("pFechaInicio", strFechaIni);
		
		getService().eliminarAccesoEspecial(prmtAccesos);
		log.info("Se ha eliminado el acceso con fecha: "+strFechaIni);
		listarAccesosFueraHora(event);
	}
	
	public void loadSucursalxUsuarioFH(ValueChangeEvent event) throws DaoException{
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = getBeanAccesoFueraHora();
		ArrayList arraySucPerf = new ArrayList();
		ArrayList listSucursales = new ArrayList();
		ArrayList listPerfiles = new ArrayList();
		arraySucPerf=loadSucursalxUsuario(event,acceso);
		listSucursales=(ArrayList) arraySucPerf.get(0);
		listPerfiles=(ArrayList) arraySucPerf.get(1);
		setBeanListSucursalesUsuFH(listSucursales);
		setBeanListPerfilesUsuFH(listPerfiles);
	}
	
	public void loadSucursalxUsuarioSuc(ValueChangeEvent event) throws DaoException{
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = getBeanAccesoSucursal();
		ArrayList arraySucPerf = new ArrayList();
		ArrayList listSucursales = new ArrayList();
		ArrayList listPerfiles = new ArrayList();
		arraySucPerf=loadSucursalxUsuario(event,acceso);
		listSucursales=(ArrayList) arraySucPerf.get(0);
		listPerfiles=(ArrayList) arraySucPerf.get(1);
		setBeanListSucursalesUsuSuc(listSucursales);
		setBeanListPerfilesUsuSuc(listPerfiles);
	}
	
	public void loadSucursalxUsuarioCab(ValueChangeEvent event) throws DaoException{
		AccesoEspecial acceso = new AccesoEspecial();
		acceso = getBeanAccesoCabina();
		ArrayList arraySucPerf = new ArrayList();
		ArrayList listSucursales = new ArrayList();
		ArrayList listPerfiles = new ArrayList();
		arraySucPerf=loadSucursalxUsuario(event,acceso);
		listSucursales=(ArrayList) arraySucPerf.get(0);
		listPerfiles=(ArrayList) arraySucPerf.get(1);
		setBeanListSucursalesUsuCab(listSucursales);
		setBeanListPerfilesUsuCab(listPerfiles);
	}
	
	public ArrayList loadSucursalxUsuario(ValueChangeEvent event, AccesoEspecial acceso) throws DaoException {
		log.info("-----------------------Debugging accesosEspecialesFiller.reloadSucursalxUsuario()-----------------------------");
		setService(usuarioPerfilService);
		log.info("Se ha seteado usuarioPerfilService");
		
		Integer intIdEmpresa = acceso.getIntIdEmpresa();
		log.info("intIdEmpresa: "+intIdEmpresa);
		Integer idCboUsuario = (Integer)event.getNewValue();
		log.info("idCboUsuario = "+idCboUsuario);
		
		//Arreglo que contiene Sucursales y Perfiles
		ArrayList arraySucPerf = new ArrayList();
		
		//Sucursales del Usuario
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPersona", idCboUsuario);
		prmtBusq.put("pIntIdEmpresa", intIdEmpresa);
	    
		ArrayList listaSucursales = new ArrayList();
		ArrayList arraySucursales = new ArrayList();
	    arraySucursales = getService().listarSucursalUsuario(prmtBusq);
	    
	    for(int i=0; i<arraySucursales.size() ; i++){
	    	SucursalUsuario suc = new SucursalUsuario();
        	HashMap hash = (HashMap) arraySucursales.get(i);
        	log.info("SUCU_IDSUCURSAL_N = "+hash.get("SUCU_IDSUCURSAL_N"));
			int idSucursal = Integer.parseInt("" + hash.get("SUCU_IDSUCURSAL_N"));
			log.info("V_SUCURSAL = "+hash.get("V_SUCURSAL"));
			String nombreSucursal = "" + hash.get("V_SUCURSAL");
			suc.setIntIdSucursal(idSucursal);
			suc.setStrSucursal(nombreSucursal);
			listaSucursales.add(suc);
        }
        //setBeanListSucursalesUsuFH(listaSucursales);
	    arraySucPerf.add(listaSucursales);
        
        //Perfiles del Usuario
        HashMap prmtBusqueda = new HashMap();
        prmtBusqueda.put("pIntIdPersona", idCboUsuario);
        prmtBusqueda.put("pIntIdEmpresa", intIdEmpresa);
	    
		ArrayList listaPerfiles = new ArrayList();
		ArrayList arrayPerfiles = new ArrayList();
	    arrayPerfiles = getService().listarPerfilUsuario(prmtBusqueda);
	    
	    for(int i=0; i<arrayPerfiles.size() ; i++){
	    	PerfilUsuario pf = new PerfilUsuario();
        	HashMap hash = (HashMap) arrayPerfiles.get(i);
        	log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int idPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			log.info("V_PERFIL = "+hash.get("V_PERFIL"));
			String nombrePerfil = "" + hash.get("V_PERFIL");
			pf.setIntIdPerfil(idPerfil);
			pf.setStrDescPerfil(nombrePerfil);
			listaPerfiles.add(pf);
        }
        //setBeanListPerfilesUsuFH(listaPerfiles);
	    arraySucPerf.add(listaPerfiles);
		log.info("Saliendo de accesosEspecialesFiller.reloadSucursalxUsuario()...");
		return arraySucPerf;
	}
	
	public void habilitarGrabarAccesoFH(ActionEvent event){
		log.info("-----------------------Debugging accesosEspecialesFiller.habilitarGrabarAccesoFH()-----------------------------");
		setBlnAccesosFH(true);
		limpiarFormAccesosFH();
	}
	
	public void limpiarFormAccesosFH(){
		log.info("-----------------------Debugging accesosEspecialesFiller.habilitarGrabarAccesoFH----------------------------------");
		AccesoEspecial acceso = new AccesoEspecial();
		setBeanAccesoFueraHora(acceso);
	}
	
	public void cancelarGrabarAccesoFH(ActionEvent event){
		log.info("-----------------------Debugging accesosEspecialesFiller.cancelarGrabarAccesoFH()-----------------------------");
		limpiarFormAccesosFH();
		setBlnAccesosFH(false);
	}
	
	public void habilitarGrabarAccesoSuc(ActionEvent event){
		log.info("-----------------------Debugging accesosEspecialesFiller.habilitarGrabarAccesoSuc()-----------------------------");
		setBlnAccesosSuc(true);
		limpiarFormAccesosSuc();
	}
	
	public void limpiarFormAccesosSuc(){
		log.info("-----------------------Debugging accesosEspecialesFiller.habilitarGrabarAccesosSuc----------------------------------");
		AccesoEspecial acceso = new AccesoEspecial();
		setBeanAccesoSucursal(acceso);
	}
	
	public void cancelarGrabarAccesoSuc(ActionEvent event){
		log.info("-----------------------Debugging accesosEspecialesFiller.cancelarGrabarAccesoFH()-----------------------------");
		limpiarFormAccesosSuc();
		setBlnAccesosSuc(false);
	}
	
	public void habilitarGrabarAccesoCab(ActionEvent event){
		log.info("-----------------------Debugging accesosEspecialesFiller.habilitarGrabarAccesoCab()-----------------------------");
		setBlnAccesosCab(true);
		limpiarFormAccesoCab();
	}
	
	public void limpiarFormAccesoCab(){
		log.info("-----------------------Debugging accesosEspecialesFiller.habilitarGrabarAccesoCab----------------------------------");
		AccesoEspecial acceso = new AccesoEspecial();
		setBeanAccesoCabina(acceso);
	}
	
	public void cancelarGrabarAccesoCab(ActionEvent event){
		log.info("-----------------------Debugging accesosEspecialesFiller.cancelarGrabarAccesoCab()-----------------------------");
		limpiarFormAccesosSuc();
		setBlnAccesosCab(false);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Getters y Setters
	//--------------------------------------------------------------------------------------------------------------------------------
	
	public List getBeanListAccesosFueraHora() {
		return beanListAccesosFueraHora;
	}

	public AccesoEspecialServiceImpl getAccesoEspecialService() {
		return accesoEspecialService;
	}

	public void setAccesoEspecialService(
			AccesoEspecialServiceImpl accesoEspecialService) {
		this.accesoEspecialService = accesoEspecialService;
	}

	public void setBeanListAccesosFueraHora(List beanListAccesosFueraHora) {
		this.beanListAccesosFueraHora = beanListAccesosFueraHora;
	}

	public AccesoEspecial getBeanAccesoFueraHora() {
		return beanAccesoFueraHora;
	}

	public void setBeanAccesoFueraHora(AccesoEspecial beanAccesoFueraHora) {
		this.beanAccesoFueraHora = beanAccesoFueraHora;
	}

	public Integer getIntCboEmpAccesoFH() {
		return intCboEmpAccesoFH;
	}

	public void setIntCboEmpAccesoFH(Integer intCboEmpAccesoFH) {
		this.intCboEmpAccesoFH = intCboEmpAccesoFH;
	}

	public Integer getIntCboUsuAccesoFH() {
		return intCboUsuAccesoFH;
	}

	public void setIntCboUsuAccesoFH(Integer intCboUsuAccesoFH) {
		this.intCboUsuAccesoFH = intCboUsuAccesoFH;
	}

	public UsuarioServiceImpl getUsuarioPerfilService() {
		return usuarioPerfilService;
	}

	public void setUsuarioPerfilService(UsuarioServiceImpl usuarioPerfilService) {
		this.usuarioPerfilService = usuarioPerfilService;
	}

	public List getBeanListSucursalesUsuFH() {
		return beanListSucursalesUsuFH;
	}

	public void setBeanListSucursalesUsuFH(List beanListSucursalesUsuFH) {
		this.beanListSucursalesUsuFH = beanListSucursalesUsuFH;
	}

	public List getBeanListPerfilesUsuFH() {
		return beanListPerfilesUsuFH;
	}

	public void setBeanListPerfilesUsuFH(List beanListPerfilesUsuFH) {
		this.beanListPerfilesUsuFH = beanListPerfilesUsuFH;
	}

	public List getBeanListDiasFH() {
		log.info("-----------------------Debugging accesoEspecialController.getBeanListDiasFH()-----------------------------");
		setService(accesoEspecialService);
		this.beanListDiasFH.clear();
        
        for(int i=1; i<=7; i++){
        	String strDia = "";
        	switch(i){
        	case 1 : strDia="Lunes"; break;
        	case 2 : strDia="Martes"; break;
        	case 3 : strDia="Miércoles"; break;
        	case 4 : strDia="Jueves"; break;
        	case 5 : strDia="Viernes"; break;
        	case 6 : strDia="Sábado"; break;
        	case 7 : strDia="Domingo"; break;
        	}
        	this.beanListDiasFH.add(new SelectItem(i,strDia,"",false,false));
        	SelectItem item = new SelectItem();
        }
        
		return beanListDiasFH;
	}

	public void setBeanListDiasFH(List beanListDiasFH) {
		this.beanListDiasFH = beanListDiasFH;
	}

	public List getListDiasFH() {
		return listDiasFH;
	}

	public void setListDiasFH(List listDiasFH) {
		this.listDiasFH = listDiasFH;
	}

	public Integer getIntCboResponsableFH() {
		return intCboResponsableFH;
	}

	public void setIntCboResponsableFH(Integer intCboResponsableFH) {
		this.intCboResponsableFH = intCboResponsableFH;
	}

	public Boolean getBlnAccesosFH() {
		return blnAccesosFH;
	}

	public void setBlnAccesosFH(Boolean blnAccesosFH) {
		this.blnAccesosFH = blnAccesosFH;
	}

	public Integer getIntCboEstadoFH() {
		return intCboEstadoFH;
	}

	public void setIntCboEstadoFH(Integer intCboEstadoFH) {
		this.intCboEstadoFH = intCboEstadoFH;
	}

	public Integer getIntCboMotivoFH() {
		return intCboMotivoFH;
	}

	public void setIntCboMotivoFH(Integer intCboMotivoFH) {
		this.intCboMotivoFH = intCboMotivoFH;
	}

	public Date getDtInicioFHBusq() {
		return dtInicioFHBusq;
	}

	public void setDtInicioFHBusq(Date dtInicioFHBusq) {
		this.dtInicioFHBusq = dtInicioFHBusq;
	}

	public Date getDtFinFHBusq() {
		return dtFinFHBusq;
	}

	public void setDtFinFHBusq(Date dtFinFHBusq) {
		this.dtFinFHBusq = dtFinFHBusq;
	}

	public Integer getIntCboEmpAccesoSuc() {
		return intCboEmpAccesoSuc;
	}

	public void setIntCboEmpAccesoSuc(Integer intCboEmpAccesoSuc) {
		this.intCboEmpAccesoSuc = intCboEmpAccesoSuc;
	}

	public Integer getIntCboUsuAccesoSuc() {
		return intCboUsuAccesoSuc;
	}

	public void setIntCboUsuAccesoSuc(Integer intCboUsuAccesoSuc) {
		this.intCboUsuAccesoSuc = intCboUsuAccesoSuc;
	}

	public Integer getIntCboResponsableSuc() {
		return intCboResponsableSuc;
	}

	public void setIntCboResponsableSuc(Integer intCboResponsableSuc) {
		this.intCboResponsableSuc = intCboResponsableSuc;
	}

	public Integer getIntCboEstadoSuc() {
		return intCboEstadoSuc;
	}

	public void setIntCboEstadoSuc(Integer intCboEstadoSuc) {
		this.intCboEstadoSuc = intCboEstadoSuc;
	}

	public Integer getIntCboMotivoSuc() {
		return intCboMotivoSuc;
	}

	public void setIntCboMotivoSuc(Integer intCboMotivoSuc) {
		this.intCboMotivoSuc = intCboMotivoSuc;
	}

	public Date getDtRangoInicioSuc() {
		return dtRangoInicioSuc;
	}

	public void setDtRangoInicioSuc(Date dtRangoInicioSuc) {
		this.dtRangoInicioSuc = dtRangoInicioSuc;
	}

	public Date getDtRangoFinSuc() {
		return dtRangoFinSuc;
	}

	public void setDtRangoFinSuc(Date dtRangoFinSuc) {
		this.dtRangoFinSuc = dtRangoFinSuc;
	}

	public List getBeanListAccesoSucursales() {
		return beanListAccesoSucursales;
	}

	public void setBeanListAccesoSucursales(List beanListAccesoSucursales) {
		this.beanListAccesoSucursales = beanListAccesoSucursales;
	}

	public Boolean getBlnAccesosSuc() {
		return blnAccesosSuc;
	}

	public void setBlnAccesosSuc(Boolean blnAccesosSuc) {
		this.blnAccesosSuc = blnAccesosSuc;
	}

	public AccesoEspecial getBeanAccesoSucursal() {
		return beanAccesoSucursal;
	}

	public void setBeanAccesoSucursal(AccesoEspecial beanAccesoSucursal) {
		this.beanAccesoSucursal = beanAccesoSucursal;
	}

	public List getBeanListSucursalesUsuSuc() {
		return beanListSucursalesUsuSuc;
	}

	public void setBeanListSucursalesUsuSuc(List beanListSucursalesUsuSuc) {
		this.beanListSucursalesUsuSuc = beanListSucursalesUsuSuc;
	}

	public List getBeanListPerfilesUsuSuc() {
		return beanListPerfilesUsuSuc;
	}

	public void setBeanListPerfilesUsuSuc(List beanListPerfilesUsuSuc) {
		this.beanListPerfilesUsuSuc = beanListPerfilesUsuSuc;
	}

	public List getBeanListDiasSuc() {
		log.info("-----------------------Debugging accesoEspecialController.getBeanListDiasSuc()-----------------------------");
		setService(accesoEspecialService);
		this.beanListDiasSuc.clear();
        
        for(int i=1; i<=7; i++){
        	String strDia = "";
        	switch(i){
        	case 1 : strDia="Lunes"; break;
        	case 2 : strDia="Martes"; break;
        	case 3 : strDia="Miércoles"; break;
        	case 4 : strDia="Jueves"; break;
        	case 5 : strDia="Viernes"; break;
        	case 6 : strDia="Sábado"; break;
        	case 7 : strDia="Domingo"; break;
        	}
        	this.beanListDiasSuc.add(new SelectItem(i,strDia,"",false,false));
        	SelectItem item = new SelectItem();
        }
        
		return beanListDiasSuc;
	}

	public void setBeanListDiasSuc(List beanListDiasSuc) {
		this.beanListDiasSuc = beanListDiasSuc;
	}

	public List getListDiasSuc() {
		return listDiasSuc;
	}

	public void setListDiasSuc(List listDiasSuc) {
		this.listDiasSuc = listDiasSuc;
	}

	public Integer getIntCboEmpAccesoCab() {
		return intCboEmpAccesoCab;
	}

	public void setIntCboEmpAccesoCab(Integer intCboEmpAccesoCab) {
		this.intCboEmpAccesoCab = intCboEmpAccesoCab;
	}

	public Integer getIntCboUsuAccesoCab() {
		return intCboUsuAccesoCab;
	}

	public void setIntCboUsuAccesoCab(Integer intCboUsuAccesoCab) {
		this.intCboUsuAccesoCab = intCboUsuAccesoCab;
	}

	public Integer getIntCboResponsableCab() {
		return intCboResponsableCab;
	}

	public void setIntCboResponsableCab(Integer intCboResponsableCab) {
		this.intCboResponsableCab = intCboResponsableCab;
	}

	public Integer getIntCboEstadoCab() {
		return intCboEstadoCab;
	}

	public void setIntCboEstadoCab(Integer intCboEstadoCab) {
		this.intCboEstadoCab = intCboEstadoCab;
	}

	public Integer getIntCboMotivoCab() {
		return intCboMotivoCab;
	}

	public void setIntCboMotivoCab(Integer intCboMotivoCab) {
		this.intCboMotivoCab = intCboMotivoCab;
	}

	public Date getDtRangoInicioCab() {
		return dtRangoInicioCab;
	}

	public void setDtRangoInicioCab(Date dtRangoInicioCab) {
		this.dtRangoInicioCab = dtRangoInicioCab;
	}

	public Date getDtRangoFinCab() {
		return dtRangoFinCab;
	}

	public void setDtRangoFinCab(Date dtRangoFinCab) {
		this.dtRangoFinCab = dtRangoFinCab;
	}

	public List getBeanListAccesoCabinas() {
		return beanListAccesoCabinas;
	}

	public void setBeanListAccesoCabinas(List beanListAccesoCabinas) {
		this.beanListAccesoCabinas = beanListAccesoCabinas;
	}

	public Boolean getBlnAccesosCab() {
		return blnAccesosCab;
	}

	public void setBlnAccesosCab(Boolean blnAccesosCab) {
		this.blnAccesosCab = blnAccesosCab;
	}

	public AccesoEspecial getBeanAccesoCabina() {
		return beanAccesoCabina;
	}

	public void setBeanAccesoCabina(AccesoEspecial beanAccesoCabina) {
		this.beanAccesoCabina = beanAccesoCabina;
	}

	public List getBeanListDiasCab() {
		log.info("-----------------------Debugging accesoEspecialController.getBeanListDiasCab()-----------------------------");
		setService(accesoEspecialService);
		this.beanListDiasCab.clear();
        
        for(int i=1; i<=7; i++){
        	String strDia = "";
        	switch(i){
        	case 1 : strDia="Lunes"; break;
        	case 2 : strDia="Martes"; break;
        	case 3 : strDia="Miércoles"; break;
        	case 4 : strDia="Jueves"; break;
        	case 5 : strDia="Viernes"; break;
        	case 6 : strDia="Sábado"; break;
        	case 7 : strDia="Domingo"; break;
        	}
        	this.beanListDiasCab.add(new SelectItem(i,strDia,"",false,false));
        	SelectItem item = new SelectItem();
        }
        
		return beanListDiasCab;
	}

	public void setBeanListDiasCab(List beanListDiasCab) {
		this.beanListDiasCab = beanListDiasCab;
	}

	public List getListDiasCab() {
		return listDiasCab;
	}

	public void setListDiasCab(List listDiasCab) {
		this.listDiasCab = listDiasCab;
	}

	public List getBeanListSucursalesUsuCab() {
		return beanListSucursalesUsuCab;
	}

	public void setBeanListSucursalesUsuCab(List beanListSucursalesUsuCab) {
		this.beanListSucursalesUsuCab = beanListSucursalesUsuCab;
	}

	public List getBeanListPerfilesUsuCab() {
		return beanListPerfilesUsuCab;
	}

	public void setBeanListPerfilesUsuCab(List beanListPerfilesUsuCab) {
		this.beanListPerfilesUsuCab = beanListPerfilesUsuCab;
	}
	
}
