package pe.com.tumi.creditos.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.tumi.admFormDoc.domain.AdmFormDoc;
import pe.com.tumi.common.dao.impl.GenericDaoIbatis;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.dao.CreditosDao;
import pe.com.tumi.creditos.domain.AdendaPerfil;
import pe.com.tumi.creditos.domain.Aportes;
import pe.com.tumi.creditos.domain.CondSocio;
import pe.com.tumi.creditos.domain.EstructuraHojaPlan;
import pe.com.tumi.creditos.domain.Competencia;
import pe.com.tumi.creditos.domain.Aportes;
import pe.com.tumi.creditos.domain.HojaPlaneamiento;
import pe.com.tumi.creditos.domain.ConvenioEstructuraDet;
import pe.com.tumi.creditos.domain.PerfilConvenio;
import pe.com.tumi.creditos.domain.Poblacion;
import pe.com.tumi.creditos.domain.PoblacionDet;
import pe.com.tumi.creditos.domain.EstructuraOrganica;

public class CreditosDaoIbatis extends SqlMapClientDaoSupport implements CreditosDao {
	private String nameSpace;
	private String prefixColumnName;
	private String idTable;
	protected static Logger log = Logger.getLogger(GenericDaoIbatis.class);
	
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getPrefixColumnName() {
		return prefixColumnName;
	}
	public void setPrefixColumnName(String prefixColumnName) {
		this.prefixColumnName = prefixColumnName;
	}
	public String getIdTable() {
		return idTable;
	}
	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}
	
	public ArrayList listarConvenio(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarSucursales()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("Parametros de Busqueda de Sucursales: ");
		log.info("-------------------------------------");
		log.info("pIntIdConvenio = 		"+prmtBusqueda.get("pIntIdConvenio"));
		log.info("pIntIdAmpliacion = 	"+prmtBusqueda.get("pIntIdAmpliacion"));
		log.info("pIntIdNivel = 		"+prmtBusqueda.get("pIntIdNivel"));
		log.info("pIntIdEstado = 		"+prmtBusqueda.get("pIntIdEstado"));
		log.info("pIntIdSucursal = 		"+prmtBusqueda.get("pIntIdSucursal"));
		log.info("pIntIdModalidad = 	"+prmtBusqueda.get("pIntIdModalidad"));
		log.info("pStrEntidad = 		"+prmtBusqueda.get("pStrEntidad"));
		log.info("pIntIdTipoSocio = 	"+prmtBusqueda.get("pIntIdTipoSocio"));
		log.info("pIntIdDonacion = 		"+prmtBusqueda.get("pIntIdDonacion"));
		log.info("pIntIdRanFec = 		"+prmtBusqueda.get("pIntIdRanFec"));
		log.info("pDaFecIni = 			"+prmtBusqueda.get("pDaFecIni"));
		log.info("pDaFecFin = 			"+prmtBusqueda.get("pDaFecFin"));
		log.info("pChkDocAdjuntos = 	"+prmtBusqueda.get("pChkDocAdjuntos"));
		log.info("pChkCartaAutoriz = 	"+prmtBusqueda.get("pChkCartaAutoriz"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaHojaPlan", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaHojaPlan, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarEstructura(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarEstructura()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pStrNombEnt = 		" + prmtBusqueda.get("pStrNombEnt"));
		log.info("pIntIdNivel = 		" + prmtBusqueda.get("pIntIdNivel"));
		log.info("pIntIdTipoConvenio = 	" + prmtBusqueda.get("pIntIdTipoConvenio"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEstructura", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaEstructura, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarEstructuraDet(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarEstructura()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIntIdConvenio = " + prmtBusqueda.get("pIntIdConvenio"));
		log.info("pIntIdEmpresa = " + prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdCodigo =  " + prmtBusqueda.get("pIntIdCodigo"));
		log.info("pIntIdNivel =   " + prmtBusqueda.get("pIntIdNivel"));
		log.info("pIntIdCaso =    " + prmtBusqueda.get("pIntIdCaso"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEstructDet", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaEstructDet, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarResumenPoblacion(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarEstructura()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIntIdPeriodo = 	" + prmtBusqueda.get("pIntIdPeriodo"));
		log.info("pIntIdMes = 		" + prmtBusqueda.get("pIntIdMes"));
		log.info("pIntIdEmpresa = 	" + prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdNivel = 	" + prmtBusqueda.get("pIntIdNivel"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaResumenPoblacion", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaResumenPoblacion, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPoblacion(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarPoblacion()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIntIdConvenio = " + prmtBusqueda.get("pIntIdConvenio"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPoblacion", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPoblacion, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPoblacionDet(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarPoblacion()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIntIdConvenio = " + prmtBusqueda.get("pIntIdConvenio"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPoblacionDet", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPoblacionDet, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarCompetencia(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarCompetencia()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIntIdConvenio = " + prmtBusqueda.get("pIntIdConvenio"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCompetencia", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaEstructDet, prmtBusqueda)");
		return rs;
	}
	
	public void grabarHojaPlaneamiento(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarHojaPlaneamiento-------------------");
	    HojaPlaneamiento hojaPlan = new HojaPlaneamiento();
		hojaPlan = (HojaPlaneamiento) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarHojaPlaneamiento(Object o)");
		log.info("--------------------Tabla CRE_M_CONVENIO----------------------------");
		log.info("usu.getIntIdPersona() = "        + hojaPlan.getnIdConvenio());
		
		log.info("--------------------Tabla CRE_M_ADEMDA----------------------------");
		log.info("hojaPlan.getnIdConvenio() 	= "    + hojaPlan.getnIdConvenio());
		log.info("hojaPlan.getnIdAmpliacion() 	= "    + hojaPlan.getnIdAmpliacion());
		log.info("hojaPlan.getnIdEstado() 		= "    + hojaPlan.getnIdEstado());
		log.info("hojaPlan.getnIdSucursal()  	= "    + hojaPlan.getnIdSucursal());
		log.info("hojaPlan.getDaFecIni()  		= "    + hojaPlan.getDaFecIni());
		log.info("hojaPlan.getDaFecFin()  		= "    + hojaPlan.getDaFecFin());
		log.info("hojaPlan.getnOpcionFiltroCredito()= "+ hojaPlan.getnOpcionFiltroCredito());
		log.info("hojaPlan.getDaFecRegistro()	= "    + hojaPlan.getDaFecRegistro());
		log.info("hojaPlan.getnTipoRetencion() 		= "+ hojaPlan.getnTipoRetencion());
		log.info("hojaPlan.getFlRetencion() 		= "+ hojaPlan.getFlRetencion());
		log.info("hojaPlan.getnAutorizacion() 		= "+ (hojaPlan.getbAutorizacion()==true?1:0));
		log.info("hojaPlan.getnDonacion() 			= "+ (hojaPlan.getbDonacion()==true?1:0));
		log.info("hojaPlan.getsCartaPresent() 		= "+ hojaPlan.getsCartaPresent());
		log.info("hojaPlan.getsConvSugerido() 		= "+ hojaPlan.getsConvSugerido());
		log.info("hojaPlan.getsAdendaSugerida() 	= "+ hojaPlan.getsAdendaSugerida());
		
		HashMap map = new HashMap();
		// Tabla CRE_M_ADEMDA 
		map.put("pIntIdConvenio", 		hojaPlan.getnIdConvenio());
		map.put("pIntIdAmpliacion", 	hojaPlan.getnIdAmpliacion());
		map.put("pIntIdEstado", 		hojaPlan.getnIdEstado());
		map.put("pIntIdSucursal", 		hojaPlan.getnIdSucursal());
		map.put("pDaFecIni", 			hojaPlan.getDaFecIni());
		map.put("pDaFecFin", 			hojaPlan.getDaFecFin());
		map.put("pIntIdTipoOpcFiltro",	hojaPlan.getnOpcionFiltroCredito());
		map.put("pDaFecSuscipcion", 	hojaPlan.getDaFecRegistro());
		map.put("pIntTipoRetencion",	hojaPlan.getnTipoRetencion());
		map.put("pDoRetencion",			hojaPlan.getFlRetencion());
		map.put("pIntCartaAutoriz", 	(hojaPlan.getbAutorizacion()==true?1:0));
		map.put("pIntDonacion",			(hojaPlan.getbDonacion()==true?1:0));
		map.put("pStrCartaPresent",		hojaPlan.getsCartaPresent());
		map.put("pStrConvSugerido",		hojaPlan.getsConvSugerido());
		map.put("pStrAdendaSug",		hojaPlan.getsAdendaSugerida());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarHojaPlaneamiento", map);
			log.info("Parametro - pIntIdConvenio: "+ map.get("pIntIdConvenio"));
			int idConvenio   = (Integer)map.get("pIntIdConvenio");
			int idAmpliacion = (Integer)map.get("pIntIdAmpliacion");
			hojaPlan.setnIdConvenio(idConvenio);
			hojaPlan.setnIdAmpliacion(idAmpliacion);
			log.info("idConvenio: 	"+ idConvenio);
			log.info("idAmpliacion: "+ idAmpliacion);
			log.info("Se invocó al procedure *.grabarHojaPlaneamiento");
		}catch (Exception e){
			log.info("ERROR EN  grabarUsuario PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarConvEstructDet(Object o) throws DaoException, ParseException {
		log.info("-----------------Debugging grabarConvEstructDet-------------------");
	    ConvenioEstructuraDet conv = (ConvenioEstructuraDet) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarConvEstructDet(Object o)");
		log.info("--------------------Tabla CRE_M_CONVESTRUCDET----------------------------");
		log.info("pob.getnIdConvenio() 	= " + conv.getnIdConvenio());
		log.info("pob.getnIdEmpresa() 	= " + conv.getnIdEmpresa());
		log.info("pob.getnIdNivel()  	= " + conv.getnIdNivel());
		log.info("pob.getnIdCodigo()	= "	+ conv.getnIdCodigo());
		log.info("pob.getnIdCaso() 		= " + conv.getnIdCaso());
		log.info("pob.getnPadron() 		= " + conv.getnPadron());
		//log.info("pob.getFlDistancia() 	= " + conv.getFlDistancia());
		
		HashMap map = new HashMap();
		// Tabla CRE_M_CONVESTRUCDET
		map.put("pIntIdConvenio", 		conv.getnIdConvenio());
		map.put("pIntIdEmpresa", 		conv.getnIdEmpresa());
		map.put("pIntIdNivel", 			conv.getnIdNivel());
		map.put("pIntIdCodigo", 		conv.getnIdCodigo());
		map.put("pIntIdCaso", 			conv.getnIdCaso());
		map.put("pIntPadron", 			conv.getnPadron());
		//map.put("pFlDistancia",			conv.getFlDistancia());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarConvEstructDet", map);
			log.info("Se invocó al procedure *.grabarConvEstructDet");
		}catch (Exception e){
			log.info("ERROR EN  grabarConvEstructDet PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarPoblacion(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarPoblacion-------------------");
	    Poblacion pob = (Poblacion) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarPoblacion(Object o)");
		log.info("--------------------Tabla CRE_M_CONVPOBLACION----------------------------");
		log.info("pob.getnIdConvenio() 		= " + pob.getnIdConvenio());
		log.info("pob.getnIdCorr() 			= " + pob.getnIdCorr());
		log.info("pob.getsIdDescripcion() 	= " + pob.getsCentroTrabajo());
		log.info("pob.getnDistancia() 		= " + pob.getFlDistancia());
		
		HashMap map = new HashMap();
		// Tabla CRE_M_CONVPOBLACION
		map.put("pIntIdConvenio", 		pob.getnIdConvenio());
		map.put("pIntIdCorr", 			pob.getnIdCorr());
		map.put("pStrDescripcion", 		pob.getsCentroTrabajo());
		map.put("pFlDistancia", 		pob.getFlDistancia());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarConvPoblacion", map);
			/*log.info("Parametro - pIntIdCorr: "+ map.get("pIntIdCorr"));
			int idCorr = (Integer)map.get("pIntIdCorr");
			pob.setnIdCorr(idCorr);
			log.info("idCorr: "+ idCorr);*/
			log.info("Se invocó al procedure *.grabarConvPoblacion");
		}catch (Exception e){
			log.info("ERROR EN grabarConvPoblacion PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarPoblacionDet(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarPoblacionDet-------------------");
	    PoblacionDet pobd = (PoblacionDet) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarPoblacionDet(Object o)");
		log.info("--------------------Tabla CRE_M_CONVPDETALLE----------------------------");
		log.info("pobd.getnIdConvenio() 	= " + pobd.getnIdConvenio());
		log.info("pobd.getnIdCorr() 		= " + pobd.getnIdCorr());
		log.info("pobd.getnTipoTrabajador()	= " + pobd.getnTipoTrabajador());
		log.info("pobd.getnTipoSocio() 		= " + pobd.getnTipoSocio());
		log.info("pobd.getnPadron() 		= " + pobd.getnPadron());
		
		HashMap map = new HashMap();
		// Tabla CRE_M_CONVPDETALLE
		map.put("pIntIdConvenio", 		pobd.getnIdConvenio());
		map.put("pIntIdCorr", 			pobd.getnIdCorr());
		map.put("pIntTipoTrabajador", 	pobd.getnTipoTrabajador());
		map.put("pIntTipoSocio", 		pobd.getnTipoSocio());
		map.put("pIntPadron", 			pobd.getnPadron());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarConvPoblacionDet", map);
			log.info("Se invocó al procedure *.grabarConvPoblacionDet");
		}catch (Exception e){
			log.info("ERROR EN  grabarPoblacionEntidad PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarCompetencia(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarCompetencia-------------------");
	    Competencia comp = (Competencia) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarCompetencia(Object o)");
		log.info("--------------------Tabla CRE_M_CONVCOMPETENCIA----------------------------");
		log.info("pob.getnIdConvenio() 		= " + comp.getnIdConvenio());
		log.info("comp.getsEntidadFinanc() 	= " + comp.getsEntidadFinanc());
		log.info("comp.getnSocios()  		= " + comp.getnSocios());
		log.info("comp.getFlPlazoPrestamo()	= "	+ comp.getFlPlazoPrestamo());
		log.info("comp.getFlInteres()		= " + comp.getFlInteres());
		log.info("comp.getFlMontoAporte()	= " + comp.getFlMontoAporte());
		//log.info("comp.getFlMontoMantenimiento()	= " + comp.getFlMontoMantenimiento());
		log.info("comp.getsServOfrec()		= " + comp.getsServOfrec());
		
		HashMap map = new HashMap();
		// Tabla CRE_M_CONVCOMPETENCIA
		map.put("pIntIdConvenio", 		comp.getnIdConvenio());
		map.put("pStrEntidadFinanc",	comp.getsEntidadFinanc());
		map.put("pIntSocios", 			comp.getnSocios());
		map.put("pFlPlazos",	 		comp.getFlPlazoPrestamo());
		map.put("pFlInteres", 			comp.getFlInteres());
		map.put("pFlMontoAporte",		comp.getFlMontoAporte());
		map.put("pStrServicios",		comp.getsServOfrec());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCompetencia", map);
			log.info("Se invocó al procedure *.grabarCompetencia");
		}catch (Exception e){
			log.info("ERROR EN  grabarCompetencia PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarAdendaPerfil(Object o) throws DaoException{
		log.info("-----------------Debugging grabarPoblacion-------------------");
	    AdendaPerfil aden = (AdendaPerfil) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarAdendaPerfil(Object o)");
		log.info("--------------------Tabla CRE_M_ADEMDAPERFIL----------------------------");
		log.info("aden.getnIdConvenio() 	= " + aden.getnIdConvenio());
		log.info("aden.getnIdAmpliacion()	= " + aden.getnIdAmpliacion());
		log.info("aden.getnIdEmpresa() 		= " + aden.getnIdEmpresa());
		log.info("aden.getnIdPerfil() 		= " + aden.getnIdPerfil());
		
		HashMap map = new HashMap();
		// Tabla CRE_M_ADEMDAPERFIL
		map.put("pIntIdConvenio", 		aden.getnIdConvenio());
		map.put("pIntIdAmpliacion",		aden.getnIdAmpliacion());
		map.put("pIntIdEmpresa", 		aden.getnIdEmpresa());
		//map.put("pIntIdPerfil", 		aden.getnIdPerfil());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarAdendaPerfil", map);
			log.info("Se invocó al procedure *.grabarAdendaPerfil");
		}catch (Exception e){
			log.info("ERROR EN grabarAdendaPerfil PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarPoblacion(Object prmtPob)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtPob;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarPoblacion").toString(), map);
	        log.info("Se invocó al procedure *.eliminarPoblacion");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarPoblacion PopupDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	//Métodos de Perfil Convenio
	
	public ArrayList listarControlProceso(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarControlProceso()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("Parametros de Busqueda de Control de Proceso: ");
		log.info("-------------------------------------");
		log.info("pIntIdAmpliacion  = 	"+prmtBusqueda.get("pIntIdAmpliacion"));
		log.info("pIntIdConvenio 	=	"+prmtBusqueda.get("pIntIdConvenio"));
		log.info("pIntIdSucursal 	=	"+prmtBusqueda.get("pIntIdSucursal"));
		log.info("pIntIdEstado 		=	"+prmtBusqueda.get("pIntIdEstado"));
		log.info("pStrEntidad 		=	"+prmtBusqueda.get("pStrEntidad"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaControlProceso", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaControlProceso, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPerfilConvenio(Object prmtBusq) throws DaoException{
		log.info("Entrando al método listarPerfilConvenio()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIntIdConvenio 	= 	" + prmtBusqueda.get("pIntIdConvenio"));
		log.info("pIntIdAmpliacion  = 	" + prmtBusqueda.get("pIntIdAmpliacion"));
		log.info("pIntIdEmpresa 	= 	" + prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdPerfil  	= 	" + prmtBusqueda.get("pIntIdPerfil"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfilConvenio", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfilConvenio, prmtBusqueda)");
		return rs;
	}
	
	public void grabarAdendaPerfilDet(Object o) throws DaoException {
		log.info("-----------------Debugging grabarAdendaPerfilDet-------------------");
	    PerfilConvenio perfconv = (PerfilConvenio) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarAdendaPerfilDet(Object o)");
		log.info("--------------------Tabla Cre_M_AdemdaPerfilDetalle----------------------------");
		log.info("pob.getnIdConvenio() 		= " + perfconv.getInIdConvenio());
		log.info("pob.getInIdAmpliacion() 	= " + perfconv.getInIdAmpliacion());
		log.info("pob.getnIdEmpresa() 		= " + perfconv.getInIdEmpresa());
		log.info("pob.getInIdPerfil() 		= " + perfconv.getInIdPerfil());
		log.info("pob.getInIdValidacion() 	= " + perfconv.getInIdValidacion());
		log.info("pob.getInValorPerfDet() 	= " + perfconv.getInValorPerfDet());
		log.info("pob.getStObservacion() 	= " + perfconv.getStObservacion());
		
		HashMap map = new HashMap();
		// Tabla Cre_M_AdemdaPerfilDetalle
		map.put("pIntIdConvenio", 		perfconv.getInIdConvenio());
		map.put("pIntIdAmpliacion",		perfconv.getInIdAmpliacion());
		map.put("pIntIdEmpresa",		perfconv.getInIdEmpresa());
		map.put("pIntIdPerfil", 		perfconv.getInIdPerfil());
		map.put("pIntIdValidacion", 	perfconv.getInIdValidacion());
		map.put("pIntIdValorPefDet",	perfconv.getInValorPerfDet());
		map.put("pStrObservacion",		perfconv.getStObservacion());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarAdendaPerfilDet", map);
			log.info("Se invocó al procedure *.grabarAdendaPerfilDet");
		}catch (Exception e){
			log.info("ERROR EN  grabarAdendaPerfilDet PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarAdendaPerfil(Object prmtAdPerf)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap) prmtAdPerf;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarAdendaPerfil").toString(), map);
	        log.info("Se invocó al procedure *.eliminarAdendaPerfil");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarAdendaPerfil PopupDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void aprobarRechazarConvenio(Object o) throws DaoException {
		HashMap map = new HashMap();
	    map = (HashMap) o;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".aprobarRechazarConv").toString(), map);
	        log.info("Se invocó al procedure *.aprobarRechazarConv");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .aprobarRechazarConv PopupDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	//Métodos de Aportaciones
	public ArrayList listarAportaciones(Object prmtAport) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAportaciones()");
		Aportes prmtBusqueda = (Aportes) prmtAport;
		log.info("Parametros de Busqueda de Aportaciones: ");
		log.info("----------------------------------------");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAportaciones", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAportaciones, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarCondSocio(Object prmtBusq) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAportaciones()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIdParametro 	= 	" + prmtBusqueda.get("pIdParametro"));
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCondSocio", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaCondSocio, prmtBusqueda)");
		return rs;
	}
	
	public void grabarConfCaptacion(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarConfCaptacion-------------------");
	    Aportes aport = (Aportes) o;
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarConfCaptacion(Object o)");
		log.info("--------------------Tabla CRE_M_CONFCAPTACION----------------------------");
		log.info("aport.getIntIdEmpresa() 			= "+ aport.getIntIdEmpresa());
		log.info("aport.getIntIdTipoCaptacion() 	= "+ aport.getIntIdTipoCaptacion());
		log.info("aport.getIntIdCodigo() 			= "+ aport.getIntIdCodigo());
		log.info("aport.getStrDescripcion()  		= "+ aport.getStrDescripcion());
		log.info("aport.getDaFecIni()		  		= "+ aport.getDaFecIni());
		log.info("aport.getDaFecFin()		  		= "+ aport.getDaFecFin());
		log.info("aport.getIntIdTipoPersona()		= "+ aport.getIntIdTipoPersona());
		log.info("aport.getIntIdRol()				= "+ aport.getIntIdRol());
		log.info("aport.getIntIdTipoDcto()	 		= "+ aport.getIntIdTipoDcto());
		log.info("aport.getIntIdTipoConfig() 		= "+ aport.getIntIdTipoConfig());
		log.info("aport.getFlValorConfig()	 		= "+ aport.getFlValorConfig());
		log.info("aport.getIntIdAplicacion()		= "+ aport.getIntIdAplicacion());
		log.info("aport.getIntIdMoneda() 			= "+ aport.getIntIdMoneda());
		log.info("aport.getFlTem() 					= "+ aport.getFlTem());
		log.info("aport.getIntIdTasaNaturaleza() 	= "+ aport.getIntIdTasaNaturaleza());
		log.info("aport.getIntIdTasaFormula() 		= "+ aport.getIntIdTasaFormula());
		log.info("aport.getFlTea() 					= "+ aport.getFlTea());
		log.info("aport.getFlTna() 					= "+ aport.getFlTna());
		log.info("aport.getIntEdadLimite() 			= "+ aport.getIntEdadLimite());
		log.info("aport.getIntIdEstSolicitud() 		= "+ aport.getIntIdEstSolicitud());
		log.info("aport.getIntIdEstado() 			= "+ aport.getIntIdEstado());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarConfCaptacion", aport);
			log.info("Parametro - pIntIdConvenio: "+ aport.getIntIdCodigo());
			Integer idCodigo   = (Integer) aport.getIntIdCodigo();
			aport.setIntIdCodigo(idCodigo);
			log.info("idCodigo: 	"+ idCodigo);
			log.info("Se invocó al procedure *.grabarConfCaptacion");
		}catch (Exception e){
			log.info("ERROR EN  grabarConfCaptacion CreditosDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarCondicionSocio(Object o) throws DaoException{
		log.info("-----------------Debugging grabarCondicionSocio-------------------");
	    CondSocio condsoc = (CondSocio) o;
		log.info("PARAMETROS DE ENTRADA ... CreditosDaoIbatis.grabarCondicionSocio(Object o)");
		log.info("--------------------Tabla CRE_V_CONFCAPTCONDICION----------------------");
		log.info("condsoc.getIntIdEmpresa() 		= "+ condsoc.getIntIdEmpresa());
		log.info("condsoc.getIntIdTipoCaptacion() 	= "+ condsoc.getIntIdTipoCaptacion());
		log.info("condsoc.getIntIdCodigo() 			= "+ condsoc.getIntIdCodigo());
		log.info("condsoc.getIntIdCondSocio()  		= "+ condsoc.getIntIdCondSocio());
		log.info("condsoc.getIntIdValor()		  	= "+ condsoc.getIntIdValor());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCondicionSocio", condsoc);
			log.info("Se invocó al procedure *.grabarCondicionSocio");
		}catch (Exception e){
			log.info("ERROR EN  grabarCondicionSocio CreditosDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarCondSocio(Object prmtCondSoc)throws DaoException {
	    Aportes aport = (Aportes) prmtCondSoc;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarCondSocio").toString(), aport);
	        log.info("Se invocó al procedure *.eliminarCondSocio");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarCondSocio GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void eliminarAportacion(Object prmtAport)throws DaoException {
		Aportes aport = (Aportes) prmtAport;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarAportacion").toString(), aport);
	        log.info("Se invocó al procedure *.eliminarAportacion");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarAportacion PopupDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}	
	
	//Métodos de Mantenimiento de cuentas
	public List<Aportes> listarMantCuentas(Aportes aportes) throws DaoException {		
		log.info("Entrando al método CreditosDaoIbatis.listarMantCuentas()");
		//System.out.println("lo que trae desde el paràmetro _aportes = "+_aportes);
		//Aportes aportes = (Aportes) _aportes;
		System.out.println(" ");
		System.out.println("Aportes aportes = "+aportes);
		//Aportes aportes = _aportes;
		log.info("Parametros de Busqueda de Aportes: ");
		log.info("----------------------------------------");	
		List<Aportes> rs = new ArrayList<Aportes>();
		rs = (ArrayList<Aportes>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMantCuentas", aportes);
		for(Aportes a :rs){
			System.out.println("LISTAR MANTCUENTAS");
			System.out.println("== ====== = =========");
			System.out.println("getIntIdEmpresa :\t"+a.getIntIdEmpresa());		
			System.out.println("getIntIdTipoCaptacion :\t"+a.getIntIdTipoCaptacion());			
			System.out.println("IdCodigo :\t"+a.getIntIdCodigo());			
			System.out.println("getStrDescripcion :\t"+a.getStrDescripcion());			
			/*System.out.println("getStrFecIni :\t"+a.getStrFecIni());
			System.out.println("getStrFecFin :\t"+a.getStrFecFin());
	    	System.out.println("getIntIdTipoPersona :\t"+a.getIntIdTipoPersona());
			System.out.println("getIntIdRol :\t"+a.getIntIdRol());
			System.out.println("getIntIdTipoDscto :\t"+a.getIntIdTipoDcto());			
			System.out.println("getIntIdTipoConfig :\t"+a.getIntIdTipoConfig());			
			System.out.println("getIntFlValorConfig :\t"+a.getIntFlValorConfig());			
			System.out.println("IntIdAplicacion :\t"+a.getIntIdAplicacion());			
			System.out.println("getIntIdMoneda :\t"+a.getIntIdMoneda());			
			System.out.println("getIntFltem :\t"+a.getIntFltem());			
			System.out.println("getIntIdTasaNaturaleza :\t"+a.getIntIdTasaNaturaleza());			
			System.out.println("getIntIdTasaFormula :\t"+a.getIntIdTasaFormula());			
			System.out.println("getIntFltea :\t"+a.getIntFltea());					
			System.out.println("getIntFltna :\t"+a.getIntFltna());					
			System.out.println("getIntEdadLimite :\t"+a.getIntEdadLimite());	*/		
			System.out.println("getIntIdEstadoSolic :\t"+a.getIntIdEstSolicitud());			
			System.out.println("getIntIdEstado :\t"+a.getIntIdEstado());
			System.out.println("getIntIdTipoCondicionLaboral :\t"+a.getIntIdTipoCondicionLaboral());
			System.out.println("getIntIdTipoCondicionLaboral :\t"+a.getIntIdTipoCondicionLaboral());
		}
		System.out.println("rs:");
		System.out.println(rs);		
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaMantCuentas)");
		return rs;
	}
	/*
	//plrt
	public void grabarCondSocioMantCuentas(Object o) throws DaoException{
		log.info("-----------------Debugging grabarCondSocioMantCuentas-------------------");
		Aportes mantCuenta = (Aportes) o;
		
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarCondSocioMantCuentas(Object o)");
		log.info("--------------------Tabla CRE_V_CONFCAPTCONDICION----------------------------");
		log.info("mantCuenta.getIntIdEmpresa()	 		= " + mantCuenta.getIntIdEmpresa());
		log.info("mantCuenta.getIntIdTipoCaptacion()	= " + mantCuenta.getIntIdTipoCaptacion());
		log.info("mantCuenta.getIntIdCodigo())			= " + mantCuenta.getIntIdCodigo());
		log.info("mantCuenta.getIntIdCondicionSocio()	= " + mantCuenta.getIntIdCondicionSocio());
		log.info("mantCuenta.getIntIdValor() 			= " + mantCuenta.getIntIdValor());
		
		HashMap map = new HashMap();
		// Tabla CRE_V_CONFCAPTCONDICION
		map.put("pIntIdEmpresa", 		mantCuenta.getIntIdEmpresa());
		map.put("pIntIdTipoCaptacion", 	mantCuenta.getIntIdTipoCaptacion());
		map.put("pIntIdCodigo", 		mantCuenta.getIntIdCodigo());
		map.put("pIntIdCondSocio", 		mantCuenta.getIntIdCondicionSocio());
		map.put("pIntIdValor", 			mantCuenta.getIntIdValor());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCondSocioMantCuentas", map);
			log.info("Se invocó al procedure *.grabarCondSocioMantCuentas");
		}catch (Exception e){
			log.info("ERROR EN  grabarCondSocioMantCuentas PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}*/	
	
	public ArrayList buscarEstrucOrg(Object prmEstructura) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.buscarEstrucOrg()");
		EstructuraOrganica prmtBusqueda = (EstructuraOrganica)prmEstructura;
		log.info("Parametros de busqueda de Estructura Organica: ");
		log.info("-----------------------------------------------");
		log.info("intTipoEntidad: "+prmtBusqueda.getIntTipoEntidad());
		log.info("intNivel: "+prmtBusqueda.getIntNivel());
		log.info("strNombreEntidad: "+prmtBusqueda.getStrNombreEntidad());
		log.info("intCasoConfig: "+prmtBusqueda.getIntCasoConfig());
		log.info("intTipoSocio: "+prmtBusqueda.getIntTipoSocio());
		log.info("intModalidad: "+prmtBusqueda.getIntModalidad());
		log.info("strFechaDesde: "+prmtBusqueda.getStrFechaDesde());
		log.info("strFechaHasta: "+prmtBusqueda.getStrFechaHasta());
		log.info("intSucursal: "+prmtBusqueda.getIntSucursal());
		log.info("intSubsucursal: "+prmtBusqueda.getIntSubsucursal());
		log.info("strCodigoExterno: "+prmtBusqueda.getStrCodigoExterno());
		log.info("Parametros seteados.");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqEstructuraOrganica", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getBusqEstructuraOrganica, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarEstrucOrg(Object prmEstructura) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarEstrucOrg()");
		EstructuraOrganica prmtBusqueda = (EstructuraOrganica)prmEstructura;
		log.info("Parametros de busqueda de Estructura Organica: ");
		log.info("-----------------------------------------------");
		log.info("intCodigoEstructura: "+prmtBusqueda.getIntCodigoEstructura());
		log.info("intIdEmpresa: "+prmtBusqueda.getIntIdEmpresa());
		log.info("intNivel: "+prmtBusqueda.getIntNivel());
		log.info("strCodigoExterno: "+prmtBusqueda.getStrCodigoExterno());
		log.info("intTipoEntidadTerceros: "+prmtBusqueda.getIntTipoEntidadTerceros());
		log.info("strNombreEntidad: "+prmtBusqueda.getStrNombreEntidad());
		log.info("intCasoConfig: "+prmtBusqueda.getIntCasoConfig());
		log.info("intTipoSocio: "+prmtBusqueda.getIntTipoSocio());
		log.info("intModalidad: "+prmtBusqueda.getIntModalidad());
		log.info("intSucursal: "+prmtBusqueda.getIntSucursal());
		log.info("intSubsucursal: "+prmtBusqueda.getIntSubsucursal());
		log.info("strFechaDesde: "+prmtBusqueda.getStrFechaDesde());
		log.info("strFechaHasta: "+prmtBusqueda.getStrFechaHasta());
		log.info("Parametros seteados.");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEstructuraOrganica", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaEstructuraOrganica, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarEstrucDet(Object prmEstructura) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarEstrucDet");
		EstructuraOrganica prmtBusqueda = (EstructuraOrganica)prmEstructura;
		log.info("Parametros de busqueda de Estructura Detalle: ");
		log.info("-----------------------------------------------");
		log.info("intCodigoEstructura: "+prmtBusqueda.getIntCodigoEstructura());
		log.info("intIdEmpresa: "+prmtBusqueda.getIntIdEmpresa());
		log.info("intNivel: "+prmtBusqueda.getIntNivel());
		log.info("strCodigoExterno: "+prmtBusqueda.getStrCodigoExterno());
		log.info("intTipoEntidadTerceros: "+prmtBusqueda.getIntTipoEntidadTerceros());
		log.info("strNombreEntidad: "+prmtBusqueda.getStrNombreEntidad());
		log.info("intCasoConfig: "+prmtBusqueda.getIntCasoConfig());
		log.info("intTipoSocio: "+prmtBusqueda.getIntTipoSocio());
		log.info("intModalidad: "+prmtBusqueda.getIntModalidad());
		log.info("intSucursal: "+prmtBusqueda.getIntSucursal());
		log.info("intSubsucursal: "+prmtBusqueda.getIntSubsucursal());
		log.info("strFechaDesde: "+prmtBusqueda.getStrFechaDesde());
		log.info("strFechaHasta: "+prmtBusqueda.getStrFechaHasta());
		log.info("Parametros seteados.");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEstrucDet", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaEstrucDet, prmtBusqueda)");
		return rs;
	}
	
	public void grabarEstructuraOrg(Object estruc) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.grabarEstructuraOrg()");
		EstructuraOrganica prmt = (EstructuraOrganica)estruc;	
		log.info("Parametros de busqueda de Estructura Organica: ");
		log.info("-----------------------------------------------");
		log.info("intIdEmpresa: "+prmt.getIntIdEmpresa());
		log.info("intNivel: "+prmt.getIntNivel());
		log.info("intCodigoEstructura: "+prmt.getIntCodigoEstructura());
		log.info("intIdPersona: "+prmt.getIntIdPersona());
		log.info("strFechaRegistro: "+prmt.getStrFechaRegistro());
		log.info("intTipoEntidad: "+prmt.getIntTipoEntidad()); //IdGrupo
		log.info("intEstadoDocumento: "+prmt.getIntEstadoDocumento());
		log.info("intIdUsuario: "+prmt.getIntIdUsuario());
		log.info("intNivelRel "+prmt.getIntNivelRel());
		log.info("intCodigoRel: "+prmt.getIntCodigoRel());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarEstructuraOrganica", prmt);
			log.info("Se invocó al procedure *.grabarEstructuraOrganica");
		}catch (Exception e){
			log.info("ERROR EN  grabarEstructuraOrganica CreditosDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarEstructuraDetalle(Object estruc) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.grabarEstructuraDetalle()");
		EstructuraOrganica prmt = (EstructuraOrganica)estruc;	
		log.info("Parametros de busqueda de Estructura Organica: ");
		log.info("-----------------------------------------------");
		log.info("intIdEmpresa: "+prmt.getIntIdEmpresa());
		log.info("intNivel: "+prmt.getIntNivel());
		log.info("intCodigoEstructura: "+prmt.getIntCodigoEstructura());
		log.info("intCasoConfig: "+prmt.getIntCasoConfig());
		log.info("intTipoSocio: "+prmt.getIntTipoSocio());
		log.info("intModalidad: "+prmt.getIntModalidad());
		log.info("intSucursal: "+prmt.getIntSucursal()); 
		log.info("intSubsucursal: "+prmt.getIntSubsucursal());
		log.info("intDiaEnviado: "+prmt.getIntDiaEnviado());
		log.info("intFechaEnviado: "+prmt.getIntFechaEnviado());
		log.info("intDiaEfectuado: "+prmt.getIntDiaEfectuado());
		log.info("intFechaEnviado: "+prmt.getIntFechaEnviado());
		log.info("intDiaCheque: "+prmt.getIntDiaCheque());
		log.info("intFechaCheque: "+prmt.getIntFechaCheque());
		log.info("strCodigoExterno: "+prmt.getStrCodigoExterno());
		log.info("intIdUsuario: "+prmt.getIntIdUsuario());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarEstructuraDetalle", prmt);
			log.info("Se invocó al procedure *.grabarEstructuraDetalle");
		}catch (Exception e){
			log.info("ERROR EN  grabarEstructuraDetalle CreditosDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	//Créditos mantenimiento de cuenta plrt
	public void grabarMantCuentas(Aportes aporte) throws DaoException{
		log.info("-----------------Debugging grabarMantCuentas-------------------");
		//Aportes aportes = (Aportes) o;
		//Aportes aporte = _aportes;
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarMantCuentas(Object o)");
		
		log.info("--------------------Tabla CRE_M_CONFCAPTACION----------------------------");
		log.info("mantCuenta.getIntIdEmpresa()	 		= " + aporte.getIntIdEmpresa());
		log.info("mantCuenta.getIntIdTipoCaptacion()	= " + aporte.getIntIdTipoCaptacion());
		log.info("mantCuenta.getIntIdCodigo())			= " + aporte.getIntIdCodigo());
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pIntIdEmpresa", 		aporte.getIntIdEmpresa());
		System.out.println("getIntIdEmpresa :\t"+aporte.getIntIdEmpresa());
		
		map.put("pIntIdTipoCaptacion", 	aporte.getIntIdTipoCaptacion());
		System.out.println("getIntIdTipoCaptacion :\t"+aporte.getIntIdTipoCaptacion());
		
		map.put("pIntIdCodigo", 		aporte.getIntIdCodigo());
		System.out.println("IdCodigo :\t"+aporte.getIntIdCodigo());
		
		map.put("pStrDescripcion", 		aporte.getStrDescripcion());
		System.out.println("getStrDescripcion :\t"+aporte.getStrDescripcion());
		
		map.put("pStrFecIni", 			aporte.getStrFecIni());
		System.out.println("getStrFecIni :\t"+aporte.getStrFecIni());
		
		map.put("pStrFecFin", 			aporte.getStrFecFin());
		System.out.println("getStrFecFin :\t"+aporte.getStrFecFin());   
    	
		map.put("pIntIdTipoPersona", 	aporte.getIntIdTipoPersona());
    	System.out.println("getIntIdTipoPersona :\t"+aporte.getIntIdTipoPersona());
		
    	map.put("pIntIdRol", 			aporte.getIntIdRol());
		System.out.println("getIntIdRol :\t"+aporte.getIntIdRol());
		map.put("intIdTipoDcto", 		aporte.getIntIdTipoDcto());//pIntIdTipoDscto
		System.out.println("getIntIdTipoDscto :\t"+aporte.getIntIdTipoDcto());
		map.put("pIntIdTipoConfig", 	aporte.getIntIdTipoConfig());
		System.out.println("getIntIdTipoConfig :\t"+aporte.getIntIdTipoConfig());
		map.put("pIntFlValorConfig", 	aporte.getIntFlValorConfig());
		System.out.println("getIntFlValorConfig :\t"+aporte.getIntFlValorConfig());
		map.put("pIntIdAplicacion", 	aporte.getIntIdAplicacion());
		System.out.println("IntIdAplicacion :\t"+aporte.getIntIdAplicacion());
		map.put("pIntIdMoneda", 		aporte.getIntIdMoneda());
		System.out.println("getIntIdMoneda :\t"+aporte.getIntIdMoneda());
		map.put("pIntFltem", 			aporte.getIntFltem());
		System.out.println("getIntFltem :\t"+aporte.getIntFltem());
		map.put("pIntIdTasaNaturaleza",	aporte.getIntIdTasaNaturaleza());
		System.out.println("getIntIdTasaNaturaleza :\t"+aporte.getIntIdTasaNaturaleza());
		map.put("pIntIdTasaFormula", 	aporte.getIntIdTasaFormula());
		System.out.println("getIntIdTasaFormula :\t"+aporte.getIntIdTasaFormula());
		map.put("pIntFltea", 			aporte.getIntFltea());
		System.out.println("getIntFltea :\t"+aporte.getIntFltea());		
		map.put("pIntFltna", 			aporte.getIntFltna());
		System.out.println("getIntFltna :\t"+aporte.getIntFltna());		
		map.put("pIntEdadLimite", 		aporte.getIntEdadLimite());
		System.out.println("getIntEdadLimite :\t"+aporte.getIntEdadLimite());
		map.put("intIdEstSolicitud", 	aporte.getIntIdEstSolicitud());//pIntIdEstadoSolic
		System.out.println("getIntIdEstadoSolic :\t"+aporte.getIntIdEstSolicitud());
		map.put("pIntIdEstado", 		aporte.getIntIdEstado());
		System.out.println("getIntIdEstado :\t"+aporte.getIntIdEstado());
		map.put("pIntIdTipoCondicionLaboral", aporte.getIntIdTipoCondicionLaboral());
		System.out.println("getIntIdTipoCondicionLaboral :\t"+aporte.getIntIdTipoCondicionLaboral());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarMantCuentas", map);
			Integer intCodigo=(Integer)map.get("pIntIdCodigo");
			aporte.setIntIdCodigo(intCodigo);			
			log.info("Se invocó al procedure *.grabarMantCuentas");
		}catch (Exception e){
			log.info("ERROR EN  grabarMantCuentas PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}	
	
	//plrt
	public void grabarCondSocioMantCuentas(Aportes aporte) throws DaoException{
		log.info("-----------------Debugging grabarCondSocioMantCuentas-------------------");
		//Aportes aporte = _aporte;
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarCondSocioMantCuentas(Object o)");
		log.info("--------------------Tabla CRE_V_CONFCAPTCONDICION----------------------------");
		log.info("mantCuenta.getIntIdEmpresa()	 		= " + aporte.getIntIdEmpresa());
		log.info("mantCuenta.getIntIdTipoCaptacion()	= " + aporte.getIntIdTipoCaptacion());
		log.info("mantCuenta.getIntIdCodigo())			= " + aporte.getIntIdCodigo());
		log.info("mantCuenta.getIntIdCondicionSocio()	= " + aporte.getIntIdCondSocio());
		log.info("mantCuenta.getIntIdValor() 			= " + aporte.getIntIdValor());		
		Map<String, Integer> map = new HashMap<String, Integer>();
		// Tabla CRE_V_CONFCAPTCONDICION
		map.put("pIntIdEmpresa", 		aporte.getIntIdEmpresa());
		map.put("pIntIdTipoCaptacion", 	aporte.getIntIdTipoCaptacion());
		map.put("pIntIdCodigo", 		aporte.getIntIdCodigo());
		map.put("pIntIdCondSocio", 		aporte.getIntIdCondSocio());
		map.put("pIntIdValor", 			aporte.getIntIdValor());		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCondSocioMantCuentas", map);
			log.info("Se invocó al procedure *.grabarCondSocioMantCuentas");
		}catch (Exception e){
			log.info("ERROR EN  grabarCondSocioMantCuentas PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	public void grabarCondCapAfectasMantCuentas(Aportes aporte) throws DaoException{
		log.info("-----------------Debugging grabarCondCapAfectasMantCuentas-------------------");
		//Aportes aporte = _aporte;
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.grabarCondCapAfectasMantCuentas(Object o)");
		log.info("--------------------Tabla CRE_V_CONFCAPTCONDICION----------------------------");
		log.info("mantCuenta.getIntIdEmpresa()	 		= " + aporte.getIntIdEmpresa());
		log.info("mantCuenta.getIntIdTipoCaptacion()	= " + aporte.getIntIdTipoCaptacion());
		log.info("mantCuenta.getIntIdCodigo())			= " + aporte.getIntIdCodigo());
		log.info("aporte.getIntIdTipoCaptacionAfecta()	= " + aporte.getIntIdTipoCaptacionAfecta());			
		Map<String, Integer> map = new HashMap<String, Integer>();
		// Tabla CRE_V_CONFCAPTAFECTAS
		map.put("pIntIdEmpresa", 			aporte.getIntIdEmpresa());
		map.put("pIntIdTipoCaptacion", 		aporte.getIntIdTipoCaptacion());
		map.put("pIntIdCodigo", 			aporte.getIntIdCodigo());
		map.put("pIntIdTipoCaptacionAfecta",aporte.getIntIdTipoCaptacionAfecta());			
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCondCapAfectasMantCuentas", map);
			log.info("Se invocó al procedure *.grabarCondCapAfectasMantCuentas");
		}catch (Exception e){
			log.info("ERROR EN  grabarCondCapAfectasMantCuentas PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}		
	}
	public void eliminarMantCuenta(Aportes aportes)throws DaoException {
		//Aportes aport = (Aportes) prmtAport;
	 	//Aportes aportes = _aportes;
		log.info("PARAMETROS DE ENTRADA ... PopupDaoIbatis.eliminarMantCuenta(Aportes a)");
		
		log.info("--------------------Tabla CRE_M_CONFCAPTACION----------------------------");
		log.info("mantCuenta.getIntIdEmpresa()	 		= " + aportes.getIntIdEmpresa());
		log.info("mantCuenta.getIntIdTipoCaptacion()	= " + aportes.getIntIdTipoCaptacion());
		log.info("mantCuenta.getIntIdCodigo())			= " + aportes.getIntIdCodigo());
		
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("intIdEmpresa", 		aportes.getIntIdEmpresa());
		System.out.println("getIntIdEmpresa :\t"+aportes.getIntIdEmpresa());
		
		map.put("intIdTipoCaptacion", 	aportes.getIntIdTipoCaptacion());
		System.out.println("getIntIdTipoCaptacion :\t"+aportes.getIntIdTipoCaptacion());
		
		map.put("intIdCodigo", 		aportes.getIntIdCodigo());
		System.out.println("IdCodigo :\t"+aportes.getIntIdCodigo());
	 	
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarMantCuenta").toString(), aportes);
	        log.info("Se invocó al procedure *.eliminarMantCuenta");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarMantCuenta PopupDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	public List<CondSocio> listarCondicionSocio(Map prmtBusqueda) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAportaciones()");
		//HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("pIdParametro 	= 	" + prmtBusqueda.get("pIdParametro"));
		
		List<CondSocio> rs = new ArrayList<CondSocio>();
		rs = (ArrayList<CondSocio>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCondicionSocio", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaCondicionSocio, prmtBusqueda)");
		return rs;
	}
	
}
