package pe.com.tumi.common.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.parameter.BasicParameterMapping;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;
import com.ibatis.sqlmap.engine.mapping.result.BasicResultMapping;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMapping;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.type.DomTypeMarker;

import pe.com.tumi.admFormDoc.domain.AdmFormDoc;
import pe.com.tumi.common.dao.GenericDao;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
//import pe.com.telefonica.seguridad.domain.Usuario;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.empresa.domain.Domicilio;
import pe.com.tumi.empresa.domain.Empresa;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.seguridad.domain.AccesoEspecial;
import pe.com.tumi.seguridad.domain.AccesoEspecialDet;
import pe.com.tumi.seguridad.domain.AccesoExternoPc;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.AuditoriaSistemas;
import pe.com.tumi.seguridad.domain.Sesiones;
import pe.com.tumi.seguridad.domain.DataObjects;
import pe.com.tumi.seguridad.domain.PerfilMof;
import pe.com.tumi.seguridad.domain.RegistroPc;
import pe.com.tumi.seguridad.domain.ReglamentoPolitica;
import pe.com.tumi.seguridad.domain.SolicitudCambio;
import pe.com.tumi.seguridad.usuario.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.usuario.domain.Perfil;
import pe.com.tumi.seguridad.usuario.domain.PerfilDet;
import pe.com.tumi.seguridad.usuario.domain.PerfilUsuario;
import pe.com.tumi.seguridad.usuario.domain.SubSucursalUsuario;
import pe.com.tumi.seguridad.usuario.domain.SucursalUsuario;
import pe.com.tumi.seguridad.usuario.domain.Usuario;
import pe.com.tumi.horario.domain.Horario;

public class GenericDaoIbatis extends SqlMapClientDaoSupport implements GenericDao {

	private String nameSpace;
	private String prefixColumnName;
	private String idTable;
	protected static Logger log = Logger.getLogger(GenericDaoIbatis.class);
	
	public void delete (Long id) throws DaoException {
		try{
			getSqlMapClientTemplate().delete(getNameSpace() + ".delete", id);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	} 
	
	public void delete (Object o) throws DaoException {
		Long id = null;
		try{
			id = Long.valueOf((BeanUtils.getProperty(o, Constante.PERSISTENCE_FIELD_ID)));
			getSqlMapClientTemplate().delete(getNameSpace() + ".delete", id);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void delete (Long[] idsToDelete) throws DaoException {
		Long id = null;
		try{
			getSqlMapClientTemplate().getSqlMapClient().startTransaction();
			getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for (int i = 0; i < idsToDelete.length; i++) {
				id = idsToDelete[i];
				getSqlMapClientTemplate().delete(getNameSpace() + ".delete", id);
			}
			getSqlMapClient().executeBatch();
			getSqlMapClientTemplate().getSqlMapClient().commitTransaction();
		 } catch(Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		} finally{
			 try {
				getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		 }
	}
	
	public void deleteRelationalTables (Long id1, Collection collection) throws DaoException {
		HashMap map = new HashMap();
		map.put("id1", id1);
		try {
			getSqlMapClient().startTransaction();
			getSqlMapClient().startBatch();
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				Long id2 = Long.valueOf(BeanUtils.getProperty(object, Constante.PERSISTENCE_FIELD_ID));
				map.put("id2", id2);
				getSqlMapClient().insert(getNameSpace()+ ".deleteRelations", map);
			}
			getSqlMapClient().executeBatch();
			getSqlMapClient().commitTransaction();
		}catch (SQLException e) {
			throw new DaoException(e);
		} catch (IllegalAccessException e) {
			throw new DaoException(e);
		} catch (InvocationTargetException e) {
			throw new DaoException(e);
		} catch (NoSuchMethodException e) {
			throw new DaoException(e);
		}finally{
			 try {
				getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			 }catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		}
	}
	
	public Object findById (Long id) throws DaoException {
		HashMap map = new HashMap();
		map.put("condition", getPrefixColumnName() + "_" + getIdTable() + " = " + id);
		try{
			return getSqlMapClientTemplate().queryForObject(getNameSpace() + ".find", map);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	//TaT!
	public List findByObject (Object o) throws DaoException {
		HashMap map = new HashMap();
		SqlMapClientImpl sqlMapClient = (SqlMapClientImpl)getSqlMapClientTemplate().getSqlMapClient();
		ResultMap resultMap = sqlMapClient.getDelegate().getResultMap(getNameSpace() + ".result");
		ResultMapping[] arrResultMapping = resultMap.getResultMappings();
		BasicResultMapping basicReultMapping = null;
		
		String condition = " 1 = 1 ";
		String tmp = "";
		try{
			for (int i = 0; i < arrResultMapping.length; i++) {
				basicReultMapping = (BasicResultMapping)arrResultMapping[i];
				if(basicReultMapping.getPropertyName() != null){
					tmp = BeanUtils.getProperty(o, basicReultMapping.getPropertyName());
					if(tmp!=null && !tmp.equals("")) condition += " AND " + basicReultMapping.getColumnName() + " = '" + tmp + "' ";
				}
			}
			map.put("condition", condition);
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".find", map);
		}catch(Exception e){
			throw new DaoException(e);				
		}
	}
	
	public List findAll() throws DaoException {
		HashMap map = new HashMap();
		map.put("condition", "");
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".find" , map);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}
	
	/**Autor : Eyder Uceda Lopez
	 * Fecha : 23062011
	 * Metodo que realiza la busqueda de Ceritificaciones
	 * @return Lista de documentos
	 * @throws DaoException
	 */
	public List findDocumentos(String certificado ,String fehcaInicio ,
							   String fechaFin ,String moneda ,String usuario ) throws DaoException {
		HashMap map = new HashMap();
		map.put("certificado" , certificado);
		map.put("fehcaInicio" , fehcaInicio);
		map.put("fechaFin"    , fechaFin);
		map.put("moneda"      , moneda);
		map.put("usuario"     , usuario);		
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() +".finddocumentos" , map);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}
	
	 /****************************************************************************/
	 /*  Nombre : updateCertificados                    						*/ 
	 /*  Param. : Object       													*/ 
	 /*                     													*/
	 /*  Objetivo: Actualizar datos de los certificados             			*/
	 /*                     													*/
	 /*  Retorno : void															*/
	 /***************************************************************************/

	
	public List find(String condition) throws DaoException {
		HashMap map = new HashMap();
		map.put("condition", condition);
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".find" , map);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}
	
	public List findByQueryWithDesc(String queryName) throws DaoException{
		HashMap map = new HashMap();
		map.put("condition", queryName);
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".findwithdesc" , map);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}
	
	public List findByNamedQuery(String queryName) throws DaoException{
		try{
			return getSqlMapClientTemplate().queryForList(queryName);
		}catch(Exception e){
			throw new DaoException(e);
		}		
	}
	
	public List findByNamedQuery(String queryName, Map parameters) throws DaoException{
		try{
			return getSqlMapClientTemplate().queryForList(queryName,parameters);
		}catch(Exception e){
			throw new DaoException(e);
		}		
	}
	
	public Connection getConnection() throws DaoException {
		Connection cnx = null;
		try {
			cnx = getSqlMapClientTemplate().getDataSource().getConnection();
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return cnx;
	}

	public List getNodes(Long id) throws DaoException{
		HashMap map = new HashMap();
		if (id!=null)map.put("condition", getPrefixColumnName() + "_NSUPR_ID = " + id );
		else map.put("condition", getPrefixColumnName() + "_NSUPR_ID is null ");
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".find", map);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public List getTreeByUser(Long id, Long sistema) throws DaoException {
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("sistema", sistema);
		try {
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".findTree" , map);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void update (Object o) throws DaoException {
		try{
			getSqlMapClientTemplate().update(getNameSpace()+ ".update", o);
		}catch(Exception e) {
			throw new DaoException(e);	
		}			
	}
	
	public void save (Object o) throws DaoException {
		try{
			if (BeanUtils.getProperty(o, Constante.PERSISTENCE_FIELD_ID) != null){
				getSqlMapClientTemplate().update(getNameSpace() + ".save", o);
			}else{
				getSqlMapClientTemplate().insert(getNameSpace() + ".insert", o);
			}
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void save(Collection collection) throws DaoException{
		int rowsUpdated = 0;
		try {
			getSqlMapClient().startTransaction();
			getSqlMapClient().startBatch();
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				if (BeanUtils.getProperty(object, Constante.PERSISTENCE_FIELD_ID) != null){
					getSqlMapClient().update(getNameSpace()+ ".save", object);
				}else{
					getSqlMapClient().insert(getNameSpace()+ ".insert", object);
				}
			}
			rowsUpdated = getSqlMapClient().executeBatch();
			getSqlMapClient().commitTransaction();
		}catch (SQLException e) {
			throw new DaoException(e);
		} catch (IllegalAccessException e) {
			throw new DaoException(e);
		} catch (InvocationTargetException e) {
			throw new DaoException(e);
		} catch (NoSuchMethodException e) {
			throw new DaoException(e);
		} finally{
			 try {
				getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			 } catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		}
	}
	
	public void saveRelationalTables(Long id, Collection collection) throws DaoException{
		HashMap map = new HashMap();
		map.put("id1", id);
		try {
			getSqlMapClient().startTransaction();
			getSqlMapClient().startBatch();
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				map.put("id2", BeanUtils.getProperty(object, Constante.PERSISTENCE_FIELD_ID));
				getSqlMapClient().update(getNameSpace()+ ".saveRelations", map);
			}
			getSqlMapClient().executeBatch();
			getSqlMapClient().commitTransaction();
		}catch (SQLException e) {
			throw new DaoException(e);
		} catch (IllegalAccessException e) {
			throw new DaoException(e);
		} catch (InvocationTargetException e) {
			throw new DaoException(e);
		} catch (NoSuchMethodException e) {
			throw new DaoException(e);
		} finally{
			 try {
				getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			 } catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		}
	}

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

	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}

	public String getIdTable() {
		return idTable;
	}

	public List getOpcionesxRol(Long id) throws DaoException {
		HashMap map = new HashMap();
		map.put("id", id);
		try {
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".findOpciones" , map);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List listarOpcionesxUsuario(Long sistema) throws DaoException {
		HashMap map = new HashMap();
		map.put("sistema", sistema);
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".listarOpcionesxUsuario", map);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public List listarUsuariosInactivos(Long sistema, Long limite) throws DaoException{
		HashMap map = new HashMap();
		map.put("sistema", sistema);
		map.put("limite", limite);
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".listarUsuariosInactivos", map);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public List listarUsuariosLogueados(Long sistema, Date fechaInicial, Date fechaFinal) throws DaoException {
		HashMap map = new HashMap();
		map.put("sistema", sistema);
		map.put("fechaInicial", fechaInicial);
		map.put("fechaFinal", fechaFinal);
		try{
			return getSqlMapClientTemplate().queryForList(getNameSpace() + ".listarUsuariosLogueados", map);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public ArrayList getControlesxRol(Long id) {
		log.info("Entrando al método getControlesxRol(Long id)...");
		ArrayList rs = null;
		HashMap map = new HashMap();
		map.put("id_rol", id);
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getControlesxRol", map);
		log.info("Ejecutado correctamente: (ResultSet) getSqlMapClientTemplate().queryForList(getNameSpace() + .getControlesxRol, map)");
		return rs;
	}

	/*public ArrayList listarEmpresas(Object prmtBusqEmpresa) throws DaoException{
		log.info("Entrando al método listarEmpresas()");
		HashMap prmtBusquedaEmpresa = new HashMap();
		HashMap prmtBusqueda = (HashMap)prmtBusqEmpresa;
		log.info("Parametros de Busqueda de Empresas: ");
		log.info("-------------------------------------");
		log.info("prmIdEmpresa = "+prmtBusqueda.get("prmIdEmpresa"));
		log.info("txtEmpresa = "+prmtBusqueda.get("pTxtEmpresa"));
		log.info("txtRuc = "+prmtBusqueda.get("pTxtRuc"));
		log.info("cboTipoEmpresa = "+prmtBusqueda.get("pCboTipoEmpresa"));
		log.info("cboEstadoEmpresa = "+prmtBusqueda.get("pCboEstadoEmpresa"));
		
		prmtBusquedaEmpresa.put("prmIdEmpresa", prmtBusqueda.get("prmIdEmpresa"));
		prmtBusquedaEmpresa.put("pTxtEmpresa", prmtBusqueda.get("pTxtEmpresa"));
		prmtBusquedaEmpresa.put("pTxtRuc", prmtBusqueda.get("pTxtRuc"));
		prmtBusquedaEmpresa.put("pCboTipoEmpresa", prmtBusqueda.get("pCboTipoEmpresa"));
		prmtBusquedaEmpresa.put("pCboEstadoEmpresa", prmtBusqueda.get("pCboEstadoEmpresa"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEmpresas", prmtBusquedaEmpresa);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getControlesxRol, map)");
		return rs;
	}*/
	
	public ArrayList listarEmpresasUsuario(Object prmtBusqEmpresa) throws DaoException{
		log.info("Entrando al método listarEmpresas()");
		HashMap prmtBusquedaEmpresa = new HashMap();
		HashMap prmtBusqueda = (HashMap)prmtBusqEmpresa;
		log.info("Parametros de Busqueda de Empresas: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona = "+prmtBusqueda.get("pIntIdPersona"));
		int idUsuario = 0;
		if(!(""+prmtBusqueda.get("pIntIdPersona")).equals("")){
			idUsuario = Integer.parseInt(""+prmtBusqueda.get("pIntIdPersona"));
		}
		
		prmtBusquedaEmpresa.put("pIntIdPersona", idUsuario);
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEmpresaUsuario", prmtBusquedaEmpresa);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaEmpresaUsuario, map)");
		return rs;
	}
	
	public ArrayList listarEmpresas2(Object prmtBusqEmpresa) throws DaoException{
		log.info("Entrando al método listarEmpresas2()");		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEmpresas", "");
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getControlesxRol, map)");
		return rs;
	}
	
	public void grabarEmpresa(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarEmpresa-------------------");
	    
		Empresa emp = new Empresa();
		emp=(Empresa)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarEmpresa(Object o)");
		
		log.info("--------------------Tabla PER_PERSONA----------------------------");
		log.info("emp.getIntIdPersona() "      	   + emp.getIntIdPersona());
		log.info("emp.getIntIdtipopersona() "      + emp.getIntIdtipopersona());
		log.info("emp.getIntRuc() "       		   + emp.getIntRuc());
		log.info("emp.getIntIdestado_persona() "   + emp.getIntIdestado_persona());
		log.info("--------------------Tabla PER_JURIDICA---------------------------");
		log.info("emp.getStrRazonsocial() "        + emp.getStrRazonsocial());
		log.info("emp.getStrSiglas() " 		       + emp.getStrSiglas());
		log.info("emp.getIntIdtipoempresa() "      + emp.getIntIdtipoempresa());
		log.info("--------------------Tabla SEG_EMPRESA----------------------------");
		log.info("emp.getStrTiemposesion() "       + emp.getStrTiemposesion());
		log.info("emp.getStrAlertasesion()" 	   + emp.getStrAlertasesion());
		log.info("emp.getIntIdestado_empresa() "   + emp.getIntIdestado_empresa());
		log.info("emp.getStrVigenciaclaves() "	   + emp.getStrVigenciaclaves());
		log.info("emp.getStrAlertacaducidad() "    + emp.getStrAlertacaducidad());
		log.info("emp.getStrIntentosingreso() "    + emp.getStrIntentosingreso());
		log.info("emp.getStrControlhoraingreso()"  + emp.getBlnControlhoraingreso());
		log.info("emp.getStrControlregistro() "    + emp.getBlnControlregistro());
		log.info("emp.getStrControlcambioclave() " + emp.getBlnControlcambioclave());
			
		HashMap map = new HashMap();
		// Tabla PER_PERSONA 
		map.put("pIntIdPersona", emp.getIntIdPersona());
		map.put("pIntIdtipopersona", emp.getIntIdtipopersona());
		map.put("pIntRuc", emp.getIntRuc());
		// Tabla PER_JURIDICA
		map.put("pStrRazonsocial", emp.getStrRazonsocial());
		map.put("pStrSiglas", emp.getStrSiglas());
		map.put("pIntIdtipoempresa", emp.getIntIdtipoempresa());
		// Tabla SEG_EMPRESA
		map.put("pStrTiemposesion", emp.getStrTiemposesion());
		map.put("pStrAlertasesion", emp.getStrAlertasesion());
		map.put("pIntIdestado_empresa", emp.getIntIdestado_empresa());
		map.put("pStrVigenciaclaves", emp.getStrVigenciaclaves()); 
		map.put("pStrAlertacaducidad", emp.getStrAlertacaducidad());
		map.put("pStrIntentosingreso", emp.getStrIntentosingreso());
		map.put("pStrControlhoraingreso", emp.getBlnControlhoraingreso()==true?"true":"false");
		map.put("pStrControlregistro", emp.getBlnControlregistro()==true?"true":"false"); 
		map.put("pStrControlcambioclave", emp.getBlnControlcambioclave()==true?"true":"false");
		log.info("Se cargó el Hashmap map.");
		log.info("Advertencia getNameSpace() :" +getNameSpace() + ".grabarEmpresa");
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarEmpresa", map);
			log.info("Se invocó al procedure *.grabarEmpresa");
		}catch (Exception e){
			log.info("ERROR EN  grabarEmpresa GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
		
	}
	
	public void grabarSucursal(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarSucursal-------------------");
	    
		Sucursal suc = new Sucursal();
		suc=(Sucursal)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarSucursal(Object o)");
		log.info("--------------------Tabla PER_PERSONA----------------------------");
		log.info("suc.getIntIdpersona() = "        + suc.getIntPersPersonaPk());
		log.info("suc.getIntIdtipopersona() = "    + 2);
		log.info("--------------------Tabla PER_JURIDICA---------------------------");
		log.info("suc.getStrNombreComercial() = "  + suc.getJuridica().getStrNombreComercial());
		log.info("suc.getStrSiglas() = "      	   + suc.getJuridica().getStrSiglas());
		log.info("--------------------Tabla SEG_SUCURSAL----------------------------");
		log.info("suc.getIntIdempresa() = " 	   + suc.getId().getIntPersEmpresaPk());
		log.info("emp.getIntIdtiposucursal() = "   + suc.getIntIdTipoSucursal());
		log.info("emp.getIntIdestado() = "   	   + suc.getIntIdEstado());
		log.info("suc.getIntIdZonal() = "   	   + suc.getIntIdZonal());
		
		HashMap map = new HashMap();
		// Tabla PER_PERSONA 
		map.put("pIntIdpersona", 		suc.getIntPersPersonaPk());
		map.put("pIntIdtipopersona", 	2);
		// Tabla PER_JURIDICA
		map.put("pStrNombreComercial", 	suc.getJuridica().getStrNombreComercial());
		map.put("pStrSiglas", 			suc.getJuridica().getStrSiglas());
		// Tabla SEG_SUCURSAL
		map.put("pIntIdempresa", 		suc.getId().getIntPersEmpresaPk());
		map.put("pCboTipoSucursal", 	suc.getIntIdTipoSucursal());
		map.put("pCboEstadoSucursal", 	suc.getIntIdEstado());
		map.put("pIdZonal", 			suc.getIntIdZonal());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSucursal", map);
			log.info("Parametro - pIntIdSucursal: "+ map.get("pIntIdSucursal"));
			int idSucursal = (Integer) map.get("pIntIdSucursal");
			suc.getId().setIntIdSucursal(idSucursal);
			log.info("idSucursal: "+ idSucursal);
			log.info("Se invocó al procedure *.grabarSucursal");
		}catch (Exception e){
			log.info("ERROR EN  grabarSucursal GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarArea(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarArea-------------------");
	    
		Area area = new Area();
		area=(Area)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarArea(Object o)");
		
		log.info("--------------------Tabla SEG_AREA----------------------------");
		//log.info("area.getIntIdEmpresa() 	= " + area.getIntIdEmpresa());
		//log.info("area.getIntIdSucursal() 	= " + area.getIntIdSucursal());
		log.info("area.getStrDescripcion() 	= " + area.getStrDescripcion());
		//log.info("area.getStrAbreviatura() 	= " + area.getStrAbreviatura());
		//log.info("area.getStrIdTipoArea() 	= " + area.getStrIdTipoArea());
		log.info("area.getIntIdEstado() 	= " + area.getIntIdEstado());
		//log.info("area.getIntIdArea() 		= " + area.getIntIdArea());
		
		HashMap map = new HashMap();
		// Tabla SEG_AREA
		//map.put("pIntIdempresa", 		area.getIntIdEmpresa());
		//map.put("pIntIdsucursal", 		area.getIntIdSucursal());
		map.put("pStrNombre", 			area.getStrDescripcion());
		//map.put("pStrAbreviatura", 		area.getStrAbreviatura());
		//map.put("pCboTipoArea", 		area.getStrIdTipoArea());
		map.put("pCboEstadoArea", 		area.getIntIdEstado());
		//map.put("pIntIdArea", 			area.getIntIdArea());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarArea", map);
			log.info("Parametro - pIntIdArea2: "+ map.get("pIntIdArea2"));
			int idArea = (Integer)map.get("pIntIdArea2");
			//area.setIntIdArea(idArea);
			log.info("idArea: "+ idArea);
			log.info("Se invocó al procedure *.grabarArea");
		}catch (Exception e){
			log.info("ERROR EN  grabarSucursal GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarDomicilio(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarDomicilio-------------------");
		Domicilio dom = new Domicilio();
		dom=(Domicilio)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarDomicilio(Object o)");
		log.info("--------------------Tabla PER_DOMICILIO----------------------------");
		log.info("dom.getIntIdpersona() = "        + dom.getIntIdpersona());
		log.info("dom.getIntIdTipoDomicilio() = "  + dom.getIntIdTipoDomicilio());
		log.info("dom.getIntIdTipoDireccion() = "  + dom.getIntIdTipoDireccion());
		log.info("dom.getIntIdTipoVivienda() = "   + dom.getIntIdTipoVivienda());
		log.info("dom.getIntIdTipoVia() = " 	   + dom.getIntIdTipoVia());
		log.info("dom.getStrNombreVia() = "   	   + dom.getStrNombreVia());
		log.info("dom.getIntInterior() = "   	   + dom.getIntInterior());
		log.info("dom.getIntIdTipoZona() = "   	   + dom.getIntIdTipoZona());
		log.info("dom.getStrNombreZona() = "   	   + dom.getStrNombreZona());
		log.info("dom.getStrReferencia() = "   	   + dom.getStrReferencia());
		log.info("dom.getIntIdDepartamento() = "   + dom.getIntIdDepartamento());
		log.info("dom.getIntIdProvincia() = "      + dom.getIntIdProvincia());
		log.info("dom.getIntIdDistrito() = "   	   + dom.getIntIdDistrito());
		log.info("dom.getFgCroquis() = "   	   	   + (dom.isFgCroquis()==true?"1":"0"));
		log.info("dom.isFgCorrespondencia() = "    + (dom.isFgCorrespondencia()==true?"1":"0"));
		log.info("dom.getStrObservacion() = "      + dom.getStrObservacion());
		
		HashMap map = new HashMap();
		// Tabla PER_DOMICILIO 
		map.put("pIntIdpersona", 		dom.getIntIdpersona());
		map.put("pIntIdTipoDomicilio", 	dom.getIntIdTipoDomicilio());
		map.put("pIntIdTipoDireccion", 	dom.getIntIdTipoDireccion());
		map.put("pIntIdTipoVivienda", 	dom.getIntIdTipoVivienda());
		map.put("pIntIdTipoVia", 		dom.getIntIdTipoVia());
		map.put("pStrNombreVia", 		dom.getStrNombreVia());
		map.put("pIntNroVia", 			dom.getIntNroVia());
		map.put("pIntInterior", 		dom.getIntInterior());
		map.put("pIntIdTipoZona", 		dom.getIntIdTipoZona());
		map.put("pStrNombreZona", 		dom.getStrNombreZona());
		map.put("pStrReferencia", 		dom.getStrReferencia());
		map.put("pIntIdDepartamento", 	dom.getIntIdDepartamento());
		map.put("pIntIdProvincia", 		dom.getIntIdProvincia());
		map.put("pIntIdDistrito", 		dom.getIntIdDistrito());
		map.put("pFgCroquis", 			dom.isFgCroquis()==true?"1":"0");
		map.put("pFgCorrespondencia", 	dom.isFgCorrespondencia()==true?"1":"0");
		map.put("pStrObservacion", 		dom.getStrObservacion());
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarDomicilio", map);
			log.info("Se invocó al procedure *.grabarDomicilio");
		}catch (Exception e){
			log.info("ERROR EN  grabarDomicilio GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	/*public void grabarTercero(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarTercero-------------------");
		SucursalCodigo ter = new SucursalCodigo();
		ter=(SucursalCodigo)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarTercero(Object o)");
		log.info("--------------------Tabla PER_DOMICILIO----------------------------");
		log.info("ter.getIntIdSucursal() = "        + ter.getIntIdSucursal());
		log.info("ter.getStrNombre() = "  			+ ter.getStrNombre());
		log.info("ter.getIntActivo() = "  			+ ter.getIntActivo());
		log.info("ter.getStrCodigo() = "  			+ ter.getStrCodigo());
		
		HashMap map = new HashMap();
		// Tabla PER_DOMICILIO 
		map.put("pIntIdSucursal", 		ter.getIntIdSucursal());
		map.put("pStrNombre", 			ter.getStrNombre());
		map.put("pIntActivo", 			ter.getIntActivo());
		map.put("pIntCodigo", 			ter.getStrCodigo());
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarTercero", map);
			log.info("Se invocó al procedure *.grabarTercero");
		}catch (Exception e){
			log.info("ERROR EN  grabarTercero GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}*/
	
	/*public void grabarSubsucursal(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarSubsucursal-------------------");
		Subsucursal sub = new Subsucursal();
		sub = (Subsucursal)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarSubsucursal(Object o)");
		log.info("--------------------Tabla PER_SUBSUCURSAL----------------------------");
		log.info("sub.getIntIdEmpresa() = "         + sub.getIntIdEmpresa());
		log.info("sub.getIntIdSucursal() = "        + sub.getIntIdSucursal());
		log.info("sub.getIntIdSubSucursal() = " 	+ sub.getIntIdSubSucursal());
		log.info("sub.getStrNombre() = "  			+ sub.getStrNombre());
		log.info("sub.getIntIdEstado() = " 			+ sub.getIntIdEstado());
		
		HashMap map = new HashMap();
		// Tabla SEG_M_SUCURSALDETALLE
		map.put("pIntIdEmpresa", 		sub.getIntIdEmpresa());
		map.put("pIntIdSucursal", 		sub.getIntIdSucursal());
		//map.put("pIntIdSubSucursal", 	sub.getIntIdSubSucursal());
		map.put("pStrNombre", 			sub.getStrNombre());
		map.put("pIntIdEstado", 		sub.getIntIdEstado());
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSubsucursal", map);
			log.info("Se invocó al procedure *.grabarSubsucursal");
		}catch (Exception e){
			log.info("ERROR EN grabarSubsucursal GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}*/
	
	public ArrayList listarSucursales(Object prmtBusqSucursales) throws DaoException{
		log.info("Entrando al método listarSucursales()");
		HashMap prmtBusqueda = (HashMap)prmtBusqSucursales;
		log.info("Parametros de Busqueda de Sucursales: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona 	= "+prmtBusqueda.get("pIntIdPersona"));
		log.info("pCboTipoSucursal 	= "+prmtBusqueda.get("pCboTipoSucursal"));
		log.info("pCboEstadoSucursal ="+prmtBusqueda.get("pCboEstadoSucursal"));
		log.info("pTxtSucursal 		="+prmtBusqueda.get("pTxtSucursal"));
		log.info("pIntIdempresa 	= "+prmtBusqueda.get("pIntIdempresa"));
		log.info("pIntIdSucursal 	= "+prmtBusqueda.get("pIntIdSucursal"));
		log.info("pIntIdTercero 	= "+prmtBusqueda.get("pIntIdTercero"));
		log.info("pIntIdSubSucursal = "+prmtBusqueda.get("pIntIdSubSucursal"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSucursales", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSucursales, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarAreas(Object prmtBusqAreas) throws DaoException{
		log.info("Entrando al método listarSucursales()");
		HashMap prmtBusqueda = (HashMap)prmtBusqAreas;
		log.info("Parametros de Busqueda de Sucursales: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = 	"+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pCboTipoArea = 	"+prmtBusqueda.get("pCboTipoArea"));
		log.info("pCboEstadoArea = 	"+prmtBusqueda.get("pCboEstadoArea"));
		log.info("pCboSucursal = 	"+prmtBusqueda.get("pCboSucursal"));
		log.info("pTxtArea = 		"+prmtBusqueda.get("pTxtArea"));
		log.info("pIntIdArea = 		"+prmtBusqueda.get("pIntIdArea"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAreas", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAreas, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarAreaCodigo(Object prmtBusqAreaCodigo) throws DaoException{
		log.info("Entrando al método listarAreaCodigo()");
		HashMap prmtBusqueda = (HashMap)prmtBusqAreaCodigo;
		log.info("Parametros de Busqueda de AreaCodigo: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa  = "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdSucursal = "+prmtBusqueda.get("pIntIdSucursal"));
		log.info("pIntIdArea 	 = "+prmtBusqueda.get("pIntIdArea"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAreaCodigo", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAreaCodigo, prmtBusqueda)");
		return rs;
	}
	/*
	public void grabarAreaCodigo(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging GenericDaoIbatis.grabarAreaCodigo-------------------");
		AreaCodigo arcod = new AreaCodigo();
		arcod=(AreaCodigo)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarAreaCodigo(Object o)");
		log.info("--------------------Tabla SEG_AREACODIGO----------------------------");
		log.info("arcod.getIntIdEmpresa() = "        + arcod.getIntIdEmpresa());
		log.info("arcod.getIntIdSucursal() = "       + arcod.getIntIdSucursal());
		log.info("arcod.getIntIdArea() = "        	 + arcod.getIntIdArea());
		log.info("arcod.getStrNombre() = "        	 + arcod.getStrNombre());
		log.info("arcod.getStrIdTipoActivo() = "     + arcod.getStrIdTipoActivo());
		log.info("arcod.getStrCodigoArea() = "     	 + arcod.getStrCodigoArea());
		
		HashMap map = new HashMap();
		// Tabla SEG_AREACODIGO
		map.put("pIntIdEmpresa", 		arcod.getIntIdEmpresa());
		map.put("pIntIdSucursal", 		arcod.getIntIdSucursal());
		map.put("pIntIdArea", 			arcod.getIntIdArea());
		map.put("pStrNombre", 			arcod.getStrNombre());
		map.put("pStrTipoActivo", 		arcod.getStrIdTipoActivo());
		map.put("pIntCodigoArea", 		arcod.getStrCodigoArea());

		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarAreaCodigo", map);
			log.info("Se invocó al procedure *.grabarAreaCodigo");
		}catch (Exception e){
			log.info("ERROR EN  grabarAreaCodigo GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	*/
	public ArrayList listarZonal(Object prmtBusqZonal) throws DaoException{
		log.info("Entrando al método GenericDaoIbatis()listarZonal()");
		HashMap prmtBusqueda = (HashMap)prmtBusqZonal;
		log.info("Parametros de Busqueda de Zonal: ");
		log.info("-------------------------------------");
		log.info("pIntIdpersona = 	"+prmtBusqueda.get("pIntIdpersona"));
		log.info("pIntIdZonal = 	"+prmtBusqueda.get("pIntIdZonal"));
		log.info("pTxtEmpresaZonal ="+prmtBusqueda.get("pTxtEmpresaZonal"));
		log.info("pCboTipoZonal = 	"+prmtBusqueda.get("pCboTipoZonal"));
		log.info("pCboEstadoZonal =	"+prmtBusqueda.get("pCboEstadoZonal"));
		log.info("pTxtZonal = 		"+prmtBusqueda.get("pTxtZonal"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		
		if(prmtBusqueda.get("pIntIdZonal")==null){
			rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaZonal", prmtBusqueda);
			log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaZonal, prmtBusqueda)");
		}else{
			rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getZonal", prmtBusqueda);
			log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getZonal, prmtBusqueda)");
		}
		
		return rs;
	}

	public void eliminarEmpresas(Object prmtEmpresa) throws DaoException {
		
		HashMap map = new HashMap();
		map = (HashMap) prmtEmpresa;
		
		try{
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarEmpresa", map);
			log.info("Se invocó al procedure *.eliminarEmpresa");
		}catch (Exception e){
			log.info("ERROR EN  .eliminarEmpresa GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarSucursales(Object prmtSucursal) throws DaoException {
		
		HashMap map = new HashMap();
		map = (HashMap) prmtSucursal;
		
		try{
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarSucursal", map);
			log.info("Se invocó al procedure *.eliminarEmpresa");
		}catch (Exception e){
			log.info("ERROR EN  .eliminarSucursal GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarAreas(Object prmtArea) throws DaoException {
		
		HashMap map = new HashMap();
		map = (HashMap) prmtArea;
		
		try{
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarArea", map);
			log.info("Se invocó al procedure *.eliminarArea");
		}catch (Exception e){
			log.info("ERROR EN  .eliminarArea GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarDomicilio(Object prmtBusqDomicilio) throws DaoException{
		log.info("Entrando al método listarDomicilio()");
		HashMap prmtBusqueda = (HashMap)prmtBusqDomicilio;
		log.info("Parametros de Busqueda de Dirección: ");
		log.info("-------------------------------------");
		log.info("pIntIdpersona = "+prmtBusqueda.get("pIntIdpersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDomicilio", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaDomicilio, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarTercero(Object prmtBusqTercero) throws DaoException{
		log.info("Entrando al método listarTercero()");
		HashMap prmtBusqueda = (HashMap)prmtBusqTercero;
		log.info("Parametros de Busqueda de Tercero: ");
		log.info("-------------------------------------");
		log.info("pIntIdpersona = "+prmtBusqueda.get("pIntIdpersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTercero", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaTercero, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarSubsucursal(Object prmtBusqSubsucursal) throws DaoException{
		log.info("Entrando al método listarSubsucursal()");
		HashMap prmtBusqueda = (HashMap)prmtBusqSubsucursal;
		log.info("Parametros de Busqueda de Subsucursal: ");
		log.info("-------------------------------------");
		log.info("pIntIdpersona = "+prmtBusqueda.get("pIntIdpersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSubsucursal", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSubsucursal, prmtBusqueda)");
		return rs;
	}
	
	public void eliminarZonal(Object prmtZonal)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtZonal;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarZonal").toString(), map);
	        log.info("Se invoc\363 al procedure *.eliminarZonal");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN  .eliminarZonal GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public ArrayList listarPerNatural(Object prmtBusqPerNat) throws DaoException{
	    log.info("Entrando al m\351todo listarPerNatural()");
	    HashMap prmtBusqueda = (HashMap)prmtBusqPerNat;
	    log.info("Parametros de Busqueda de Zonal: ");
	    log.info("-------------------------------------");
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList((new StringBuilder(String.valueOf(getNameSpace()))).append(".getListaPerNatural").toString(), prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerNatural, prmtBusqueda)");
	    return rs;
	}
	
	//Metodos para Perfil
	public ArrayList listarPerfiles(Object prmtBusqPerfil) throws DaoException{
		log.info("Entrando al método listarPerfiles()");
		HashMap prmtBusqueda = (HashMap)prmtBusqPerfil;
		log.info("Parametros de Busqueda de Perfiles: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdPerfil = "+prmtBusqueda.get("pIntIdPerfil"));
		log.info("pTxtEmpresaPerfil = "+prmtBusqueda.get("pTxtEmpresaPerfil"));
		log.info("pTxtPerfil = "+prmtBusqueda.get("pTxtPerfil"));
		log.info("pCboTipoPerfil = "+prmtBusqueda.get("pCboTipoPerfil"));
		log.info("pCboEstadoPerfil = "+prmtBusqueda.get("pCboEstadoPerfil"));
		log.info("pTxtFecIni = "+prmtBusqueda.get("pTxtFecIni"));
		log.info("pTxtFecFin = "+prmtBusqueda.get("pTxtFecFin"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfiles", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfiles, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPerfiles1(Object prmtBusqPerfil) throws DaoException{
		log.info("Entrando al método listarPerfiles()");
		HashMap prmtBusqueda = (HashMap)prmtBusqPerfil;
		log.info("Parametros de Busqueda de Perfiles: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdPerfil = "+prmtBusqueda.get("pIntIdPerfil"));
		log.info("pTxtEmpresaPerfil = "+prmtBusqueda.get("pTxtEmpresaPerfil"));
		log.info("pTxtPerfil = "+prmtBusqueda.get("pTxtPerfil"));
		log.info("pCboTipoPerfil = "+prmtBusqueda.get("pCboTipoPerfil"));
		log.info("pCboEstadoPerfil = "+prmtBusqueda.get("pCboEstadoPerfil"));
		log.info("pTxtFecIni = "+prmtBusqueda.get("pTxtFecIni"));
		log.info("pTxtFecFin = "+prmtBusqueda.get("pTxtFecFin"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfiles1", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfiles, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPerfiles2(Object prmtBusqPerfil) throws DaoException{
		log.info("Entrando al método listarPerfiles()");
		HashMap prmtBusqueda = (HashMap)prmtBusqPerfil;
		log.info("Parametros de Busqueda de Perfiles: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdPerfil = "+prmtBusqueda.get("pIntIdPerfil"));
		log.info("pTxtEmpresaPerfil = "+prmtBusqueda.get("pTxtEmpresaPerfil"));
		log.info("pTxtPerfil = "+prmtBusqueda.get("pTxtPerfil"));
		log.info("pCboTipoPerfil = "+prmtBusqueda.get("pCboTipoPerfil"));
		log.info("pCboEstadoPerfil = "+prmtBusqueda.get("pCboEstadoPerfil"));
		log.info("pTxtFecIni = "+prmtBusqueda.get("pTxtFecIni"));
		log.info("pTxtFecFin = "+prmtBusqueda.get("pTxtFecFin"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfiles2", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfiles2, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPerfusuario(Object prmtBusqPerfil) throws DaoException{
		log.info("Entrando al método listarPerfusuario()");
		/*HashMap prmtBusqueda = (HashMap)prmtBusqPerfil;
		log.info("Parametros de Busqueda de Perfiles: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdUsuario = "+prmtBusqueda.get("pIntIdUsuario"));
		log.info("pIntIdPerfil = "+prmtBusqueda.get("pIntIdPerfil"));
		log.info("pTxtEmpresaPerfil = "+prmtBusqueda.get("pTxtEmpresaPerfil"));
		log.info("pIntIdSucursal = "+prmtBusqueda.get("pIntIdSucursal"));
		log.info("pTxtPerfil = "+prmtBusqueda.get("pTxtPerfil"));
		log.info("pCboTipoPerfil = "+prmtBusqueda.get("pCboTipoPerfil"));
		log.info("pCboEstadoPerfil = "+prmtBusqueda.get("pCboEstadoPerfil"));
		log.info("pTxtFecIni = "+prmtBusqueda.get("pTxtFecIni"));
		log.info("pTxtFecFin = "+prmtBusqueda.get("pTxtFecFin"));*/
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfusuario", prmtBusqPerfil);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfusurio, prmtBusqueda)");
		return rs;
	}
	
	public void grabarUsuario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarUsuario-------------------");
	    Usuario usu = new Usuario();
		usu = (Usuario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarUsuario(Object o)");
		log.info("--------------------Tabla PER_PERSONA----------------------------");
		log.info("usu.getIntIdPersona() = "        + usu.getIntIdPersona());
		log.info("usu.getIntIdTipoPersona() = "	   + usu.getIntIdTipoPersona());
		log.info("usu.getStrRazonSocial() = "  	   + usu.getStrRazonSocial());
		log.info("usu.getIntIdTipoDoc() = "        + usu.getIntIdTipoDoc());
		log.info("usu.getIntNroDoc() = " 	   	   + usu.getStrNroDoc());
		log.info("usu.getStrApepat() = " 	   	   + usu.getStrApepat());
		log.info("usu.getStrApemat() = " 	   	   + usu.getStrApemat());
		log.info("usu.getStrNombres() = " 	   	   + usu.getStrNombres());
		log.info("--------------------Tabla SEG_M_USUARIO----------------------------");
		log.info("usu.getIntIdTipoUsuario() = "    + usu.getIntIdTipoUsuario());
		log.info("usu.getStrNombreUsuario() = "    + usu.getStrNombreUsuario());
		log.info("usu.getStrClaveUsuario()  = "    + usu.getStrClaveUsuario());
		log.info("usu.getChkValidHoraIng()  = "    + (usu.getChkValidHoraIng() == true ? 1: 0 ));
		log.info("usu.getChkValidCambiarClave()= " + (usu.getChkValidCambiarClave() == true ? 1 : 0 ));
		log.info("usu.getChkVigenciaClave() = "    + (usu.getChkVigenciaClave()== true ? 1 : 0 ));
		log.info("usu.getIntNroDias() 		= "    + usu.getIntNroDias());
		log.info("usu.getIntIdEstado() 		= "    + usu.getIntIdEstado());
		log.info("usu.getStrImagen() 		= "	   + usu.getStrImagen());
		
		HashMap map = new HashMap();
		// Tabla SEG_M_USUARIO 
		map.put("pIntIdPersona", 		usu.getIntIdPersona());
		map.put("pIntIdTipoPersona", 	usu.getIntIdTipoPersona());
		map.put("pStrRazonSocial", 		usu.getStrRazonSocial());
		map.put("pIntIdTipoDoc",		usu.getIntIdTipoDoc());
		map.put("pStrNroDoc", 			usu.getStrNroDoc());
		map.put("pStrApepat", 			usu.getStrApepat());
		map.put("pStrApemat", 			usu.getStrApemat());
		map.put("pStrNombres", 			usu.getStrNombres());
		map.put("pIntIdTipoUsuario",	usu.getIntIdTipoUsuario());
		map.put("pStrUsuario", 			usu.getStrNombreUsuario());
		map.put("pStrClave", 			usu.getStrClaveUsuario());
		map.put("pChkHoraIngreso", 		(usu.getChkValidHoraIng() == true ? 1: 0 ));
		map.put("pChkCambioClave", 		(usu.getChkValidCambiarClave() == true ? 1 : 0 ));
		map.put("pChkVigenciaClave", 	(usu.getChkVigenciaClave()== true ? 1 : 0 ));
		map.put("pChkDiasVigencia", 	usu.getIntNroDias());
		map.put("pIntIdEstado", 		usu.getIntIdEstado());
		map.put("pStrImagen", 			usu.getStrImagen());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarUsuario", map);
			log.info("Parametro - pIntIdPersona: "+ map.get("pIntIdPersona"));
			int idPersona = (Integer)map.get("pIntIdPersona");
			usu.setIntIdPersona(idPersona);
			log.info("idPersona: "+ idPersona);
			log.info("Se invocó al procedure *.grabarUsuario");
		}catch (Exception e){
			log.info("ERROR EN  grabarUsuario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	/*public void grabarComunicacion(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarComunicacion-------------------");
	    Comunicacion com = new Comunicacion();
		com = (Comunicacion)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarComunicacion(Object o)");
		log.info("--------------------Tabla PER_COMUNICACION----------------------------");
		log.info("usu.getIntIdPersona() = "        	   + com.getIntIdPersona());
		log.info("com.getIntIdComunicacion() = "       + com.getIntIdComunicacion());
		log.info("com.getIntIdTipoComunicacion() = "   + com.getIntIdTipoComunicacion());
		log.info("com.getIntIdSubTipoComunicacion()= " + com.getIntIdSubTipoComunicacion());
		log.info("com.getIntIdTipoLinea() = "          + com.getIntIdTipoLinea());
		log.info("com.getIntNro() = " 	   	    	   + com.getIntNro());
		log.info("com.getIntAnexo() = "   	    	   + com.getIntAnexo());
		log.info("com.getStrDescripcion() = "    	   + com.getStrDescripcion());
		
		log.info("com.getChkCasoEmerg(): "+com.getChkCasoEmerg());
		
		log.info("com.getChkCasoEmerg() = "	    	   + (com.getChkCasoEmerg()==false||com.getChkCasoEmerg()==null?0:1));
		
		HashMap map = new HashMap();
		// Tabla PER_COMUNICACION
		map.put("pIntIdPersona", 			com.getIntIdPersona());
		map.put("pIntIdComunicacion", 		com.getIntIdComunicacion());
		map.put("pIntIdTipoCom", 			com.getIntIdTipoComunicacion());
		map.put("pIntIdSubTipoCom", 		com.getIntIdSubTipoComunicacion());
		map.put("pIntIdTipoLinea",			com.getIntIdTipoLinea());
		map.put("pIntNro", 					com.getIntNro());
		map.put("pIntAnexo", 				com.getIntAnexo());
		map.put("pStrDescripcion", 			com.getStrDescripcion());
		map.put("pChkCasoEmerg", 			(com.getChkCasoEmerg()==false||com.getChkCasoEmerg()==null?0:1));
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarComunicacion", map);
			log.info("Se invocó al procedure *.grabarComunicacion");
		}catch (Exception e){
			log.info("ERROR EN  grabarUsuario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}*/
	
	public void grabarPerfilDet(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarPerfilDet-------------------");
	    PerfilDet perdet = new PerfilDet();
		perdet=(PerfilDet)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarPerfilDet(Object o)");
		log.info("--------------------Tabla SEG_V_PERFILDETALLE----------------------");
		log.info("perdet.getIntIdEmpresa() = "        + perdet.getIntIdEmpresa());
		log.info("perdet.getIntIdPerfil() = "    	  + perdet.getIntIdPerfil());
		log.info("perdet.getStrIdTransaccion() = "    + perdet.getStrIdTransaccion());
		log.info("perdet.getIntIdEstado() 		= "   + perdet.getIntIdEstado());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarPerfilDet", perdet);
			log.info("Se invocó al procedure *.grabarPerfilDet");
		}catch (Exception e){
			log.info("ERROR EN  grabarPerfilDet GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarPerfilUsuario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarPerfilUsuario-------------------");
	    PerfilUsuario perf = new PerfilUsuario();
		perf = (PerfilUsuario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarPerfilUsuario(Object o)");
		log.info("--------------------Tabla SEG_V_USUARIOPERFIL--------------------");
		log.info("perf.getIntIdEmpresa() = "  + perf.getIntIdEmpresa());
		log.info("perf.getIntIdPersona() = "  + perf.getIntIdPersona());
		log.info("perf.getIntIdPerfil()  = "  + perf.getIntIdPerfil());
		log.info("perf.getDaFecIni 		 = "  + perf.getDaFecIni());
		log.info("perf.getDaFecFin 		 = "  + perf.getDaFecFin());
		
		HashMap map = new HashMap();
		map.put("pIntIdEmpresa", 			perf.getIntIdEmpresa());
		map.put("pIntIdPersona", 			perf.getIntIdPersona());
		map.put("pIntIdPerfil", 			perf.getIntIdPerfil());
		map.put("pStrDaFecIni", 			perf.getDaFecIni());
		map.put("pStrDaFecFin", 			perf.getDaFecFin());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarPerfilUsuario", map);
			log.info("Se invocó al procedure *.grabarPerfilUsuario");
		}catch (Exception e){
			log.info("ERROR EN  grabarPerfilUsuario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarEmpresaUsuario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarEmpresaUsuario-------------------");
	    EmpresaUsuario emp = new EmpresaUsuario();
		emp = (EmpresaUsuario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarComunicacion(Object o)");
		log.info("--------------------Tabla SEG_V_EMPRESAUSUARIO--------------------");
		log.info("emp.getIntIdPersona() = "   + emp.getIntIdPersona());
		log.info("emp.getIntIdEmpresa() = "   + emp.getIntIdEmpresa());
		
		HashMap map = new HashMap();
		map.put("pIntIdPersona", 			emp.getIntIdPersona());
		map.put("pIntIdEmpresa", 			emp.getIntIdEmpresa());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarEmpresaUsuario", map);
			log.info("Se invocó al procedure *.grabarEmpresaUsuario");
		}catch (Exception e){
			log.info("ERROR EN  grabarEmpresaUsuario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarSucursalUsuario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarSucursalUsuario-------------------");
		SucursalUsuario suc = new SucursalUsuario();
		suc = (SucursalUsuario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarSucursalUsuario(Object o)");
		log.info("--------------------Tabla SEG_V_USUARIOSUCURSAL------------------");
		log.info("suc.getIntIdEmpresa() = "  + suc.getIntIdEmpresa());
		log.info("suc.getIntIdPersona() = "  + suc.getIntIdPersona());
		log.info("suc.getIntIdSucursal()= "  + suc.getIntIdSucursal());
		log.info("suc.getDaFecIni 		= "  + suc.getDaFecIni());
		log.info("suc.getDaFecFin 		= "  + suc.getDaFecFin());
		
		HashMap map = new HashMap();
		map.put("pIntIdEmpresa", 			suc.getIntIdEmpresa());
		map.put("pIntIdPersona", 			suc.getIntIdPersona());
		map.put("pIntIdSucursal", 			suc.getIntIdSucursal());
		map.put("pStrDaFecIni", 			suc.getDaFecIni());
		map.put("pStrDaFecFin", 			suc.getDaFecFin());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSucursalUsuario", map);
			log.info("Se invocó al procedure *.grabarSucursalUsuario");
		}catch (Exception e){
			log.info("ERROR EN  grabarSucursalUsuario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarPerfil(Object prmtPerfil)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtPerfil;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarPerfil").toString(), map);
	        log.info("Se invocó al procedure *.eliminarPerfil");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN  .eliminarPerfil GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void eliminarPerfilDet(Object prmtPerfilDet)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtPerfilDet;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarPerfilDet").toString(), map);
	        log.info("Se invocó al procedure *.eliminarPerfilDet");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarPerfilDet GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public ArrayList listarMenues(Object prmtBusqMenu) throws DaoException{
		log.info("Entrando al método listarMenues()");
		HashMap prmtBusqueda = (HashMap)prmtBusqMenu;
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMenues", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaMenues, prmtBusqueda)");
		return rs;
	}
	
	public void grabarSubSucursalUsuario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarSubSucursalUsuario-------------------");
		SubSucursalUsuario subsuc = new SubSucursalUsuario();
		subsuc = (SubSucursalUsuario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarSucursalUsuario(Object o)");
		log.info("--------------------Tabla SEG_V_USUARIOSUBSUCURSAL------------------");
		log.info("suc.getIntIdEmpresa() = "  	+ subsuc.getIntIdEmpresa());
		log.info("suc.getIntIdPersona() = "  	+ subsuc.getIntIdPersona());
		log.info("suc.getIntIdSucursal()= "  	+ subsuc.getIntIdSucursal());
		log.info("subsuc.getIntIdSubSucursal()="+ subsuc.getIntIdSubSucursal());
		log.info("suc.getDaFecIni 		= "  	+ subsuc.getDaFecIni());
		log.info("suc.getDaFecFin 		= "  	+ subsuc.getDaFecFin());
		
		HashMap map = new HashMap();
		map.put("pIntIdEmpresa", 			subsuc.getIntIdEmpresa());
		map.put("pIntIdPersona", 			subsuc.getIntIdPersona());
		map.put("pIntIdSucursal", 			subsuc.getIntIdSucursal());
		map.put("pIntIdSubSucursal", 		subsuc.getIntIdSubSucursal());
		map.put("pStrDaFecIni", 			subsuc.getDaFecIni());
		map.put("pStrDaFecFin", 			subsuc.getDaFecFin());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSubSucursalUsuario", map);
			log.info("Se invocó al procedure *.grabarSubSucursalUsuario");
		}catch (Exception e){
			log.info("ERROR EN  grabarSubSucursalUsuario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarUsuarioDet(Object prmtUsuarioDet)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtUsuarioDet;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarUsuarioDet").toString(), map);
	        log.info("Se invocó al procedure *.eliminarUsuarioDet");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarUsuarioDet GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void eliminarSubSucursalUsuario(Object prmtSubSucursal)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtSubSucursal;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarSubSucursalUsuario").toString(), map);
	        log.info("Se invocó al procedure *.eliminarSubSucursalUsuario");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarSubSucursalUsuario GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void eliminarSucursalUsuario(Object prmtSucursal)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtSucursal;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarSucursalUsuario").toString(), map);
	        log.info("Se invocó al procedure *.eliminarSucursalUsuario");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarSucursalUsuario GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void eliminarPerfilUsuario(Object prmtPerfil)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtPerfil;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarPerfilUsuario").toString(), map);
	        log.info("Se invocó al procedure *.eliminarPerfilUsuario");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarPerfilUsuario GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void eliminarEmpresaUsuario(Object prmtEmpresa)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtEmpresa;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarEmpresaUsuario").toString(), map);
	        log.info("Se invocó al procedure *.eliminarEmpresaUsuario");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarEmpresaUsuario GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	/*public void grabarZonal(Object o) throws DaoException, ParseException {
	    log.info("-----------------Debugging GenericDaoIbatis.grabarZonal-------------------");
	    Zonal zon = new Zonal();
	    zon = (Zonal)o;
	    log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarZonal(Object o)");
	    log.info("--------------------Tabla PER_NATURAL----------------------------");
	    log.info("zon.getIntIdEmpresa() = "+zon.getIntIdEmpresa());
	    log.info("zon.getIntIdZonal() = "+zon.getIntIdZonal());
	    log.info("zon.getIntIdPersona() = "+zon.getIntIdPersona());
	    log.info("zon.getIntIdTipoZonal() = "+zon.getIntIdTipoZonal());
	    log.info("zon.getIntIdResponsable() = "+zon.getIntIdResponsable());
	    log.info("zon.getIntIdResponsable() = "+zon.getIntIdSucursal());
	    log.info("zon.getIntIdEstadoZonal = "+zon.getIntIdEstadoZonal());
	    log.info("zon.getStrNombreZonal() = " + zon.getStrNombreZonal());
	    log.info("zon.getStrAbreviatura() = " + zon.getStrAbreviatura());
	    log.info("zon.getIntIdZonalOut() = "+zon.getIntIdZonalOut());
	    HashMap map = new HashMap();
	    map.put("pIntIdEmpresa", zon.getIntIdEmpresa());
	    map.put("pIntIdZonal", zon.getIntIdZonal());
	    map.put("pIntIdPersona", zon.getIntIdPersona());
	    map.put("pIntIdTipoZonal", zon.getIntIdTipoZonal());
	    map.put("pIntIdResponsable", zon.getIntIdResponsable());
	    map.put("pIntIdSucursal", zon.getIntIdSucursal());
	    map.put("pIntIdEstadoZonal", zon.getIntIdEstadoZonal());
	    map.put("pStrNombreZonal", zon.getStrNombreZonal());
	    map.put("pStrAbreviatura", zon.getStrAbreviatura());
	    map.put("pIntIdZonalOut", zon.getIntIdZonalOut());
	    log.info("Se cargó el Hashmap map.");
	    log.info("getNameSpace() :" + getNameSpace()+".grabarZonal");
	    try
	    {
	        getSqlMapClientTemplate().insert(getNameSpace()+".grabarZonal", map);
	        log.info("Se invocó al procedure *.grabarZonal");
	    }
	    catch(Exception e)
	    {
	        log.info("ERROR EN  grabarZonal GenericDaoIbatis "+e.getMessage());
	        throw new DaoException(e);
	    }
	    log.info("map.get(pIntIdZonalOut) :"+map.get("pIntIdZonalOut"));
	    if(map.get("pIntIdZonalOut")!=null){
	    	zon.setIntIdZonalOut(Integer.parseInt(""+map.get("pIntIdZonalOut")));
	    }   
	}*/

	public ArrayList listarAdminMenu(Object prmtBusq) throws DaoException {
	    log.info("Entrando al método listarAdminMenu()");
	    HashMap prmtBusqueda = (HashMap)prmtBusq;
	    log.info("Parametros de Busqueda de AdminMenu: ");
	    log.info("-------------------------------------");
	    log.info("pIntIdPadre = "+prmtBusqueda.get("pIntIdPadre"));
	    log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
	    log.info("pIntIdTipoMenu = "+prmtBusqueda.get("pIntIdTipoMenu"));
	    log.info("pIntIdEstadoMenu = "+prmtBusqueda.get("pIntIdEstadoMenu"));
	    log.info("pIntIdTransaccion = "+prmtBusqueda.get("pIntIdTransaccion"));
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAdminMenu", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAdminMenu, prmtBusqueda)");
	    return rs;
	}
	
	public ArrayList listarMenuPerfil(Object prmtBusq) throws DaoException {
	    log.info("Entrando al método listarMenuPerfil()");
	    HashMap prmtBusqueda = (HashMap)prmtBusq;
	    log.info("Parametros de Busqueda de MenuPerfil: ");
	    log.info("--------------------------------------");
	    log.info("pIntIdPadre = 	" + prmtBusqueda.get("pIntIdPadre"));
	    log.info("pIntIdEmpresa = 	" + prmtBusqueda.get("pIntIdEmpresa"));
	    log.info("pIntIdPerfil = 	" + prmtBusqueda.get("pIntIdPerfil"));
	    log.info("pStrNivel = 		" + prmtBusqueda.get("pStrNivel"));
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMenuPerfil", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaMenuPerfil, prmtBusqueda)");
	    return rs;
	}
	
	public ArrayList listarMenuPerfil1(Object prmtBusq) throws DaoException {
	    log.info("Entrando al método listarMenuPerfil()");
	    HashMap prmtBusqueda = (HashMap)prmtBusq;
	    log.info("Parametros de Busqueda de MenuPerfil: ");
	    log.info("--------------------------------------");
	    log.info("pIntIdPadre = 	" + prmtBusqueda.get("pIntIdPadre"));
	    log.info("pIntIdEmpresa = 	" + prmtBusqueda.get("pIntIdEmpresa"));
	    log.info("pIntIdPerfil = 	" + prmtBusqueda.get("pIntIdPerfil"));
	    log.info("pStrNivel = 		" + prmtBusqueda.get("pStrNivel"));
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMenuPerfil1", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaMenuPerfil, prmtBusqueda)");
	    return rs;
	}
	
	public ArrayList listarPerfilDetalle(Object prmtBusq) throws DaoException {
	    log.info("Entrando al método listarPerfilDetalle()");
	    HashMap prmtBusqueda = (HashMap)prmtBusq;
	    log.info("Parametros de Busqueda de PerfilDetalle: ");
	    log.info("-------------------------------------");
	    log.info("pIntIdPerfil = "+prmtBusqueda.get("pIntIdPerfil"));
	    log.info("pStrIdPadre  = "+prmtBusqueda.get("pStrIdPadre"));
	    log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfilDetalle", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfilDetalle, prmtBusqueda)");
	    return rs;
	}
	
	public ArrayList listarMenuPerfUsuario(Object prmtBusqMenu) throws DaoException {
	    log.info("Entrando al método listarMenuPerfUsuario()");
	    HashMap prmtBusqueda = (HashMap)prmtBusqMenu;
	    log.info("Parametros de Busqueda de MenuPerfUsuario: ");
	    log.info("-------------------------------------");
	    log.info("pIntIdPerfil = "+Integer.parseInt(""+prmtBusqueda.get("pIntIdPerfil")));
	    log.info("pIntIdUsuario = "+Integer.parseInt(""+prmtBusqueda.get("pIntIdUsuario")));
	    log.info("pStrIdPadre  = "+prmtBusqueda.get("pStrIdPadre"));
	    log.info("pIntIdEmpresa = "+Integer.parseInt(""+prmtBusqueda.get("pIntIdEmpresa")));
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMenuPerfUsuario", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfilDetalle, prmtBusqueda)");
	    return rs;
	}

	public void grabarMenu(Object o) throws DaoException, ParseException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarMenu-------------------");
	    AdminMenu mnu = (AdminMenu)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarMenu(Object o)");
		log.info("--------------------Tabla SEG_M_TRANSACCIONES----------------------------");
		log.info("mnu.getIntIdEmpresa() = "        	+ mnu.getIntIdEmpresa());
		log.info("mnu.getIntTipoMenu() = "    	   	+ mnu.getIntTipoMenu());
		log.info("mnu.getIntIdEstado() = "    	   	+ mnu.getIntIdEstado());
		log.info("mnu.getStrIdTransaccion() = "    	+ mnu.getStrIdTransaccion());
		log.info("mnu.getIntCrecimiento() = "      	+ mnu.getIntCrecimiento());
		log.info("mnu.getIntMenuOrden() = "    	   	+ mnu.getIntMenuOrden());
		log.info("mnu.getIntFinalMenu() = "   	   	+ mnu.getIntFinalMenu());
		log.info("mnu.getStrNombre() = "   	   	   	+ mnu.getStrNombre());
		log.info("mnu.getStrIdTransaccionPadre() = "+ mnu.getStrIdTransaccionPadre());
		log.info("mnu.getIntNivelMenu() = "			+ mnu.getIntNivelMenu());
		
		HashMap map = new HashMap();
		// Tabla SEG_M_TRANSACCIONES 
		map.put("pIntIdEmpresa",		 mnu.getIntIdEmpresa());
		map.put("pIntTipoMenu",  		 mnu.getIntTipoMenu());
		map.put("pIntIdEstado",  		 mnu.getIntIdEstado());
		map.put("pStrIdTransaccion", 	 mnu.getStrIdTransaccion());
		map.put("pIntCrecimiento",		 mnu.getIntCrecimiento());
		map.put("pIntMenuOrden", 		 mnu.getIntMenuOrden());
		map.put("pIntFinalMenu", 		 mnu.getIntFinalMenu());
		map.put("pStrNombre", 			 mnu.getStrNombre());
		map.put("pStrIdTransaccionPadre",mnu.getStrIdTransaccionPadre());
		map.put("pIntNivelMenu",		 mnu.getIntNivelMenu());
		map.put("pStrAccion",		 	 "loginController."+mnu.getStrAccion());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarMenu", map);
			log.info("Se invocó al procedure *.grabarMenu");
		}catch (Exception e){
			log.info("ERROR EN  grabarMenu GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public void eliminarMenu(Object prmtMenu) throws DaoException {
		HashMap map = new HashMap();
	    map = (HashMap)prmtMenu;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarMenu", map);
	        log.info("Se invocó al procedure *.eliminarMenu");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarMenu GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}

	public ArrayList listarDataObjects(Object prmt) throws DaoException {
		log.info("Entrando al método listarDataObjects()");
	    HashMap prmtBusqueda = (HashMap)prmt;
	    log.info("Parametros de Busqueda de DataObjects: ");
	    log.info("-------------------------------------");
	    log.info("pStrIdTransaccion :"+prmtBusqueda.get("pStrIdTransaccion"));
	    log.info("pIntIdEmpresa: "+prmtBusqueda.get("pIntIdEmpresa"));
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDataObjects", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaTablas, prmtBusqueda)");
	    return rs;
	}

	public void grabarDataObjects(Object obj) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarDataObjects-------------------");
	    DataObjects dobj = (DataObjects)obj;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarMenu(Object o)");
		log.info("--------------------Tabla SEG_M_TRANSACCIONES----------------------------");
		log.info("dobj.getIntCodigo() = " + dobj.getIntCodigo());
		log.info("dobj.getIntIdEmpresa() = " + dobj.getIntIdEmpresa());
		log.info("dobj.getStrIdTransaccion() = " + dobj.getStrIdTransaccion());
		log.info("dobj.getIntConta() = " + dobj.getIntConta());
		
		HashMap map = new HashMap();
		// Tabla SEG_M_TRANSACCIONES 
		map.put("pIntIdCodigo", dobj.getIntCodigo());
		map.put("pIntIdEmpresa", dobj.getIntIdEmpresa());
		map.put("pStrIdTransaccion", dobj.getStrIdTransaccion());
		map.put("pIntConta", dobj.getIntConta());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarDataObjects", map);
			log.info("Se invocó al procedure *.grabarDataObject");
		}catch (Exception e){
			log.info("ERROR EN  grabarDataObjects GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public ArrayList listarSolicitudes(Object prmtSolicitudes)
			throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarSolicitudes()");
	    HashMap prmtBusqueda = (HashMap)prmtSolicitudes;
	    log.info("Parametros de Busqueda de listarSolicitudes: ");
	    log.info("-------------------------------------");
	    log.info("pIntIdSolicitud: "+prmtBusqueda.get("pIntIdSolicitud"));
	    log.info("pIntIdEmpresa: "+prmtBusqueda.get("pIntIdEmpresa"));
	    log.info("pIntDesarrollador: "+prmtBusqueda.get("pIntDesarrollador"));
	    log.info("pIntSolicitante: "+prmtBusqueda.get("pIntSolicitante"));
	    log.info("pIntTipoCambio: "+prmtBusqueda.get("pIntTipoCambio"));
	    log.info("pIntEstado: "+prmtBusqueda.get("pIntEstado"));
	    log.info("pIntClase: "+prmtBusqueda.get("pIntClase"));
	    log.info("pIntAnexos: "+prmtBusqueda.get("pIntAnexos"));
	    log.info("pIntRangoFech: "+prmtBusqueda.get("pIntRangoFech"));
	    log.info("pStrFechRango1: "+prmtBusqueda.get("pStrFechRango1"));
	    log.info("pStrFechRango2: "+prmtBusqueda.get("pStrFechRango2"));
	    
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSolicitudes", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSolicitudes, prmtBusqueda)");
	    return rs;
	}

	public void grabarSolicitud(Object obj) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarSolicitud-------------------");
	    SolicitudCambio sol = (SolicitudCambio)obj;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarSolicitud(Object o)");
		log.info("--------------------Tabla SEG_V_SOLICCAMBIO----------------------------");
		log.info("sol.getIntIdSolicitud()() = " + sol.getIntIdSolicitud());
		log.info("sol.getStrIdTransaccion() = " + sol.getStrIdTransaccion());
		log.info("sol.getIntIdEmpresa() = " + sol.getIntIdEmpresa());
		log.info("sol.getStrFechEntrega() = " + sol.getStrFechEntrega());
		log.info("sol.getIntTipoCambio() = " + sol.getIntTipoCambio());
		log.info("sol.getIntIdEstado() = " + sol.getIntIdEstado());
		log.info("sol.getStrObserv() = " + sol.getStrObserv());
		log.info("sol.getStrDescripcion() = " + sol.getStrDescripcion());
		log.info("sol.getStrJustificacion() = " + sol.getStrJustificacion());
		log.info("sol.getStrFinalidad() = " + sol.getStrFinalidad());
		log.info("sol.getIntClase() = " + sol.getIntClase());
		log.info("sol.getStrFechSolicitud() = " + sol.getStrFechSolicitud());
		log.info("sol.getIntIdDesarrollador() = " + sol.getIntIdDesarrollador());
		log.info("sol.getIntIdSolicitante() = " + sol.getIntIdSolicitante());
		log.info("sol.getIntIdCIO() = " + sol.getIntIdCIO());
		log.info("sol.getIntAnexos() = " + sol.getIntAnexos());
		log.info("sol.getStrFechPrueba() = " + sol.getStrFechPrueba());		
		sol.setIntAnexos(0);//Anexos
		
		HashMap map = new HashMap();
		// Tabla SEG_V_SOLICCAMBIO 
		map.put("intIdEmpresa", sol.getIntIdEmpresa());
		map.put("strIdTransaccion", sol.getStrIdTransaccion());
		map.put("intIdSolicitud", sol.getIntIdSolicitud());
		map.put("strDescripcion", sol.getStrDescripcion());		
		map.put("strJustificacion", sol.getStrJustificacion());
		map.put("strFinalidad", sol.getStrFinalidad());
		map.put("intAnexos", sol.getIntAnexos());
		map.put("intTipoCambio", sol.getIntTipoCambio());		
		map.put("intClase", sol.getIntClase());
		map.put("strFechSolicitud", sol.getStrFechSolicitud());
		map.put("strFechPrueba", sol.getStrFechPrueba());
		map.put("intIdDesarrollador", sol.getIntIdDesarrollador());		
		map.put("intIdSolicitante", sol.getIntIdSolicitante());
		map.put("strFechEntrega", sol.getStrFechEntrega());
		map.put("strObserv", sol.getStrObserv());
		map.put("intIdCIO", sol.getIntIdCIO());
		map.put("intIdEstado", sol.getIntIdEstado());		
		map.put("intIdSolicitudOut", 0);
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSolicitud", sol);
			log.info("Se invocó al procedure *.grabarSolicitud");
		}catch (Exception e){
			log.info("ERROR EN  grabarSolicitud GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public ArrayList listarRegistroPc(Object prmtRegPc) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarRegistroPc()");
		HashMap prmtBusqueda = (HashMap)prmtRegPc;
		log.info("Parametros de Busqueda de Registro de Pc: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa: "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdSucursal: "+prmtBusqueda.get("pIntIdSucursal"));
		log.info("pIntIdArea: "+prmtBusqueda.get("pIntIdArea"));
		log.info("pIntIdEstado: "+prmtBusqueda.get("pIntIdEstado"));
		log.info("pIntIdRegPc: "+prmtBusqueda.get("pIntIdRegPc"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaRegistroPc", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaRegistroPc, prmtBusqueda)");
		return rs;
	}

	public void grabarRegistroPc(Object obj) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarRegistroPc-------------------");
	    RegistroPc regPc = (RegistroPc)obj;
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarRegistroPc", regPc);
			log.info("Se invocó al procedure *.grabarRegistroPc");
		}catch (Exception e){
			log.info("ERROR EN  grabarRegistroPc GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public ArrayList listarAccesosEspeciales(Object prmtAccesos)
			throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAccesosEspeciales()");
		HashMap prmtBusqueda = (HashMap)prmtAccesos;
		log.info("Parametros de Busqueda de Accesos Especiales: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa: "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdUsuario: "+prmtBusqueda.get("pIntIdUsuario"));
		log.info("pFechaInicio: "+prmtBusqueda.get("pFechaInicio"));
		log.info("pIntIdEstado: "+prmtBusqueda.get("pIntIdEstado"));
		log.info("pIntIdMotivo: "+prmtBusqueda.get("pIntIdMotivo"));		
		log.info("pIntIdResponsable: "+prmtBusqueda.get("pIntIdResponsable"));		
		log.info("pRangoFecha1: "+prmtBusqueda.get("pRangoFecha1"));
		log.info("pRangoFecha2: "+prmtBusqueda.get("pRangoFecha2"));
		log.info("pTipoConsulta: "+prmtBusqueda.get("pTipoConsulta"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAccesosEspeciales", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAccesosFueraHora, prmtBusqueda)");
		return rs;
	}
	
	//Métodos para Permiso
	public ArrayList listarPermisos(Object prmtBusqPermisos) throws DaoException {
		log.info("Entrando al método listarPermisos()");
		HashMap prmtBusqueda = (HashMap)prmtBusqPermisos;
		log.info("Parametros de Busqueda de Permiso: ");
		log.info("------------------------------------");
		log.info("pIntIdUsuario = "			+ prmtBusqueda.get("pIntIdUsuario"));
		log.info("pIntIdEmpresa = "			+ prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pTxtEmpresa = "			+ prmtBusqueda.get("pTxtEmpresa"));
		log.info("pCboTipoPerfilPermiso = "	+ prmtBusqueda.get("pCboTipoPerfilPermiso"));
		log.info("pCboEstadoPerfilPermiso ="+ prmtBusqueda.get("pCboEstadoPerfilPermiso"));
		log.info("pTxtUsuario = "			+ prmtBusqueda.get("pTxtUsuario"));
		log.info("pDaFecIni ="				+ prmtBusqueda.get("pDaFecIni"));
		log.info("pDaFecFin ="				+ prmtBusqueda.get("pDaFecFin"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPermisos", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPermisos, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarUsuariosEmpresa(Object prmtBusqUsrEmp) throws DaoException{
		log.info("Entrando al método listarUsuariosEmpresa()");
		HashMap prmtBusqueda = (HashMap)prmtBusqUsrEmp;
		log.info("Parametros de Busqueda de Usuarios x Empresa: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaUsuariosEmpresa", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaUsuariosEmpresa, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarUsuariosEmpresa1(Object prmtBusqUsrEmp) throws DaoException{
		log.info("Entrando al método listarUsuariosEmpresa()");
		HashMap prmtBusqueda = (HashMap)prmtBusqUsrEmp;
		log.info("Parametros de Busqueda de Usuarios x Empresa: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaUsuariosEmpresa1", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaUsuariosEmpresa, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPerfilesxUsuario(Object prmtBusqPerfUsr) throws DaoException{
		log.info("Entrando al método listarPerfilesxUsuario()");
		HashMap prmtBusqueda = (HashMap)prmtBusqPerfUsr;
		log.info("Parametros de Busqueda de Perfiles x Usuario: ");
		log.info("-------------------------------------");
		log.info("pIntIdUsuario = "+prmtBusqueda.get("pIntIdUsuario"));
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfilesxUsuario", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfilesxUsuario, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarMenuesPermiso(Object prmtBusqMenuesPermiso) throws DaoException {
		log.info("Entrando al método listarMenuesPermiso()");
		HashMap prmtBusqueda = (HashMap)prmtBusqMenuesPermiso;
		log.info("Parametros de Busqueda de Permiso: ");
		log.info("------------------------------------");
		log.info("pIntIdPerfil = "			+ prmtBusqueda.get("pIntIdPerfil"));
		log.info("pIntIdEmpresa = "			+ prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdPersona = "			+ prmtBusqueda.get("pIntIdPersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMenuesPermiso", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaMenuesPermiso, prmtBusqueda)");
		return rs;
	}
	
	public void grabarPermiso(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarPermiso-------------------");
		AdminMenu menu = new AdminMenu();
		menu = (AdminMenu)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarUsuario(Object o)");
		log.info("--------------------Tabla PER_PERSONA----------------------------");
		log.info("usu.getIntIdPersona() = "        + menu.getIntIdEmpresa());
		log.info("usu.getIntIdPersona() = "	  	   + menu.getIntIdPersona());
		log.info("usu.getStrIdTransaccion() = "    + menu.getStrIdTransaccion());
		log.info("perm.getDaFecIni(): 	   "	   + menu.getDaFecIni());
	    log.info("perm.getDaFecFin(): 	   "	   + menu.getDaFecFin());
		log.info("perm.getIntIdEstado() = "    	   + (menu.getChkPerfil()==true?1:0));
		
		HashMap map = new HashMap();
		// Tabla SEG_PERFIL 
		map.put("pIntIdEmpresa", 		menu.getIntIdEmpresa());
		map.put("pIntIdPersona", 		menu.getIntIdPersona());
		map.put("pIntIdPerfil", 		menu.getIntIdPerfil());
		map.put("pStrIdTransaccion",  	menu.getStrIdTransaccion());
		map.put("pDaFecIni",			menu.getDaFecIni());
		map.put("pDaFecFin", 			menu.getDaFecFin());
		map.put("pIntIdEstado",			(menu.getChkPerfil()==true?1:0));
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarPermiso", map);
			log.info("Se invocó al procedure *.grabarPermiso");
		}catch (Exception e){
			log.info("ERROR EN  grabarPermiso GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarPermiso(Object perm) throws DaoException {
		HashMap map = new HashMap();
	    map = (HashMap)perm;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarPermiso", map);
	        log.info("Se invocó al procedure *.eliminarPermiso");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarPermiso GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}
	
	public void deletePermiso(Object perm) throws DaoException {
		HashMap map = new HashMap();
	    map = (HashMap)perm;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".deletePermiso", map);
	        log.info("Se invocó al procedure *.deletePermiso");
	    }catch(Exception e){
	        log.info("ERROR EN .deletePermiso GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}
	
	//Metodos para Horarios
	public ArrayList listarHorarios(Object prmtBusqHorario) throws DaoException{
		log.info("Entrando al método listarHorarios()");
		HashMap prmtBusqueda = (HashMap)prmtBusqHorario;
		log.info("Parametros de Busqueda de Horarios: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pTxtEmpresa 	= "+prmtBusqueda.get("pTxtEmpresa"));
		log.info("pCboTipoSucursal="+prmtBusqueda.get("pCboTipoSucursal"));
		log.info("pCboEstado 	= "+prmtBusqueda.get("pCboEstado"));
		log.info("pStrFecIni 	= "+prmtBusqueda.get("pStrFecIni"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaHorarios", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaHorarios, prmtBusqueda)");
		return rs;
	}
	
	public void grabarHorario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarHorario-------------------");
		Horario hor = new Horario();
		hor = (Horario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarHorario(Object o)");
		log.info("--------------------Tabla SEG_V_DIASACCESOS--------------------------");
		log.info("hor.getIntIdEmpresa() = "        + hor.getIntIdEmpresa());
		log.info("hor.getIntIdTipoSucursal = "     + hor.getIntIdTipoSucursal());
		log.info("hor.getDaFecIni() = "   		   + hor.getDaFecIni()==null?"":hor.getDaFecIni());
		log.info("hor.getDaFecFin() = "   	   	   + hor.getDaFecFin()==null?"":hor.getDaFecFin());
		log.info("hor.getChkFeriado() = "      	   + (hor.getChkFeriado()==true?1:0));
		log.info("hor.getStrArchivo() = "   	   + hor.getStrAdjunto());
		
		HashMap map = new HashMap();
		// Tabla SEG_PERFIL 
		map.put("pIntIdEmpresa", 		hor.getIntIdEmpresa());
		map.put("pIntIdTipoSucursal",	hor.getIntIdTipoSucursal());
		map.put("pDaFecIni", 			hor.getDaFecIni()==null?"":hor.getDaFecIni());
		map.put("pDaFecFin", 			hor.getDaFecFin()==null?"":hor.getDaFecFin());
		map.put("pChkFeriado", 			hor.getChkFeriado()==true?1:0);
		map.put("pStrAdjunto", 			hor.getStrAdjunto());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarHorario", map);
			log.info("Se invocó al procedure *.grabarHorario");
		}catch (Exception e){
			log.info("ERROR EN  grabarHorario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarHorarioDet(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarHorarioDet-------------------");
		Horario hor = new Horario();
		hor = (Horario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarHorarioDet(Object o)");
		log.info("--------------------Tabla SEG_V_DIASACCESOSDETALLE----------------------");
		log.info("hor.getIntIdEmpresa() = "        + hor.getIntIdEmpresa());
		log.info("hor.getIntIdTipoSucursal = "     + hor.getIntIdTipoSucursal());
		log.info("hor.getDaFecIni() = "   		   + hor.getDaFecIni()==null?"":hor.getDaFecIni());
		log.info("hor.getChkLunes() = "   		   + (hor.getChkLunes()==true?1:0));
		log.info("getStrTiempoIniL() = "   		   + hor.getStrTiempoIniL());
		log.info("getStrTiempoFinL() = "   		   + hor.getStrTiempoFinL());
		log.info("hor.getChkMartes() = "   		   + (hor.getChkMartes()==true?2:0));
		log.info("getStrTiempoIniM() = "   		   + hor.getStrTiempoIniM());
		log.info("getStrTiempoFinM() = "   		   + hor.getStrTiempoFinM());
		log.info("hor.getChkMiercoles() = "   	   + (hor.getChkMiercoles()==true?3:0));
		log.info("getStrTiempoIniMi() = "  		   + hor.getStrTiempoIniMi());
		log.info("getStrTiempoFinMi() = "  		   + hor.getStrTiempoFinMi());
		log.info("hor.getChkJueves() = "   		   + (hor.getChkJueves()==true?4:0));
		log.info("getStrTiempoIniJ() = "   		   + hor.getStrTiempoIniJ());
		log.info("getStrTiempoFinJ() = "   		   + hor.getStrTiempoFinJ());
		log.info("hor.getChkViernes() = "   	   + (hor.getChkViernes()==true?5:0));
		log.info("getStrTiempoIniV() = "   		   + hor.getStrTiempoIniV());
		log.info("getStrTiempoFinV() = "   		   + hor.getStrTiempoFinV());
		log.info("hor.getChkSabado() = "   		   + (hor.getChkSabado()==true?6:0));
		log.info("getStrTiempoIniS() = "   		   + hor.getStrTiempoIniS());
		log.info("getStrTiempoFinS() = "   		   + hor.getStrTiempoFinS());
		log.info("hor.getChkDomingo() = "   	   + (hor.getChkDomingo()==true?7:0));
		log.info("getStrTiempoIniD() = "   		   + hor.getStrTiempoIniD());
		log.info("getStrTiempoFinD() = "   		   + hor.getStrTiempoFinD());
		HashMap map = new HashMap();
		// Tabla SEG_PERFIL 
		map.put("pIntIdEmpresa", 		hor.getIntIdEmpresa());
		map.put("pIntIdTipoSucursal",	hor.getIntIdTipoSucursal());
		map.put("pDaFecIni", 			hor.getDaFecIni()==null?"":hor.getDaFecIni());
		map.put("pIntIdDiaL",			(hor.getChkLunes()==true?1:1));
		map.put("pIntStrTimeIniL",		hor.getStrTiempoIniL());
		map.put("pIntStrTimeFinL",		hor.getStrTiempoFinL());
		map.put("pIntIdDiaM",			(hor.getChkMartes()==true?2:2));
		map.put("pIntStrTimeIniM",		hor.getStrTiempoIniM());
		map.put("pIntStrTimeFinM",		hor.getStrTiempoFinM());
		map.put("pIntIdDiaMi",			(hor.getChkMiercoles()==true?3:3));
		map.put("pIntStrTimeIniMi",		hor.getStrTiempoIniMi());
		map.put("pIntStrTimeFinMi",		hor.getStrTiempoFinMi());
		map.put("pIntIdDiaJ",			(hor.getChkJueves()==true?4:4));
		map.put("pIntStrTimeIniJ",		hor.getStrTiempoIniJ());
		map.put("pIntStrTimeFinJ",		hor.getStrTiempoFinJ());
		map.put("pIntIdDiaV",			(hor.getChkViernes()==true?5:5));
		map.put("pIntStrTimeIniV",		hor.getStrTiempoIniV());
		map.put("pIntStrTimeFinV",		hor.getStrTiempoFinV());
		map.put("pIntIdDiaS",			(hor.getChkSabado()==true?6:6));
		map.put("pIntStrTimeIniS",		hor.getStrTiempoIniS());
		map.put("pIntStrTimeFinS",		hor.getStrTiempoFinS());
		map.put("pIntIdDiaD",			(hor.getChkDomingo()==true?7:7));
		map.put("pIntStrTimeIniD",		hor.getStrTiempoIniD());
		map.put("pIntStrTimeFinD",		hor.getStrTiempoFinD());
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarHorarioDet", map);
			log.info("Se invocó al procedure *.grabarHorarioDet");
		}catch (Exception e){
			log.info("ERROR EN  grabarHorarioDet GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarHorariosDet(Object prmtBusqHorarioDet) throws DaoException{
		log.info("Entrando al método listarHorariosDet()");
		HashMap prmtBusqueda = (HashMap)prmtBusqHorarioDet;
		log.info("Parametros de Busqueda de HorariosDet:");
		log.info("--------------------------------------");
		log.info("pIntIdEmpresa 	= "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pCboTipoSucursal	= "+prmtBusqueda.get("pCboTipoSucursal"));
		log.info("pDaFecIni 		= "+prmtBusqueda.get("pDaFecIni"));
		log.info("pIntDiaSemana		= "+prmtBusqueda.get("pIntDiaSemana"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaHorariosDet", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaHorariosDet, prmtBusqueda)");
		return rs;
	}
	
	public void eliminarHorario(Object prmtHorario)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtHorario;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarHorario").toString(), map);
	        log.info("Se invocó al procedure *.eliminarHorario");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN  .eliminarHorario GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	///////////////////////////////////

	public void grabarPerfil(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarPerfil-------------------");
	    Perfil per = new Perfil();
		per=(Perfil)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarPerfil(Object o)");
		log.info("--------------------Tabla SEG_PERFIL----------------------------");
		log.info("per.getIntIdEmpresa() = "        + per.getIntIdEmpresa());
		log.info("per.getIntIdPerfil() = "    	   + per.getIntIdPerfil());
		log.info("per.getStrDescripcion() = "  	   + per.getStrDescripcion());
		log.info("per.getIntIdTipoPerfil() = "     + per.getIntIdTipoPerfil());
		log.info("per.getIntIdEstado() = " 	   	   + per.getIntIdEstado());
		log.info("per.getDaFecIni() = "   		   + per.getDaFecIni()==null?"":per.getDaFecIni());
		log.info("per.getDaFecFin() = "   	   	   + per.getDaFecFin()==null?"":per.getDaFecFin());
		
		HashMap map = new HashMap();
		// Tabla SEG_PERFIL 
		map.put("pIntIdEmpresa", 		per.getIntIdEmpresa());
		map.put("pIntIdPerfil", 		per.getIntIdPerfil());
		map.put("pStrDescripcion", 		per.getStrDescripcion());
		map.put("pIntIdTipoPerfil",		per.getIntIdTipoPerfil());
		map.put("pIntIdEstado", 		per.getIntIdEstado());
		map.put("pDaFecIni", 			per.getDaFecIni()==null?"":per.getDaFecIni());
		map.put("pDaFecFin", 			per.getDaFecFin()==null?"":per.getDaFecFin());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarPerfil", map);
			log.info("Parametro - pIntIdPerfil: "+ map.get("pIntIdPerfil"));
			int idPerfil = (Integer)map.get("pIntIdPerfil");
			per.setIntIdPerfil(idPerfil);
			log.info("idPerfil: "+ idPerfil);
			log.info("Se invocó al procedure *.grabarPerfil");
		}catch (Exception e){
			log.info("ERROR EN  grabarPerfil GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public ArrayList listarComunicacion(Object prmtBusqComunicacion) throws DaoException{
		log.info("Entrando al método listarComunicacion()");
		HashMap prmtBusqueda = (HashMap)prmtBusqComunicacion;
		log.info("Parametros de Busqueda de Comunicación: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona = "+prmtBusqueda.get("pIntIdPersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaComunicacion", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaComunicacion, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarEmpresaUsuario(Object prmtBusqEmpresaUsuario) throws DaoException{
		log.info("Entrando al método listarEmpresaUsuario()");
		HashMap prmtBusqueda = (HashMap)prmtBusqEmpresaUsuario;
		log.info("Parametros de Busqueda de Empresa Usuario: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona = "+prmtBusqueda.get("pIntIdPersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEmpresaUsuario", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaEmpresaUsuario, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarPerfilUsuario(Object prmtBusqPerfilUsuario) throws DaoException{
		log.info("Entrando al método listarPerfilUsuario()");
		HashMap prmtBusqueda = (HashMap)prmtBusqPerfilUsuario;
		log.info("Parametros de Busqueda de Perfil Usuario: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona = "+prmtBusqueda.get("pIntIdPersona"));
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfilUsuario", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfilUsuario, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarSubSucursalUsuario(Object prmtBusqSubSucursalUsuario) throws DaoException{
		log.info("Entrando al método listarSubSucursalUsuario()");
		HashMap prmtBusqueda = (HashMap)prmtBusqSubSucursalUsuario;
		log.info("Parametros de Busqueda de Sub Sucursal Usuario: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona = "+prmtBusqueda.get("pIntIdPersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSubSucursalUsuario", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSubSucursalUsuario, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarSubSucursales(Object prmtBusqSubSucursales) throws DaoException{
		log.info("Entrando al método listarSubSucursales()");
		HashMap prmtBusqueda = (HashMap)prmtBusqSubSucursales;
		log.info("Parametros de Busqueda de SubSucursales: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdSucursal = "+prmtBusqueda.get("pIntIdSucursal"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSubSucursales", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSubSucursales, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarSucursalUsuario(Object prmtBusqSucursalUsuario) throws DaoException{
		log.info("Entrando al método listarPerfilUsuario()");
		HashMap prmtBusqueda = (HashMap)prmtBusqSucursalUsuario;
		log.info("Parametros de Busqueda de Sucursal Usuario: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona = "+prmtBusqueda.get("pIntIdPersona"));
		log.info("pIntIdEmpresa = "+prmtBusqueda.get("pIntIdEmpresa"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSucursalUsuario", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSucursalUsuario, prmtBusqueda)");
		return rs;
	}

	//Métodos para Usuario
	public ArrayList listarUsuarios(Object prmtBusqUsuario) throws DaoException{
		log.info("Entrando al método listarUsuarios()");
		HashMap prmtBusqueda = (HashMap)prmtBusqUsuario;
		log.info("Parametros de Búsqueda de Usuarios: ");
		log.info("-------------------------------------");
		log.info("pIntIdUsuario = "+prmtBusqueda.get("pIntIdUsuario"));
		log.info("pCboEmpresaUsuario = "+prmtBusqueda.get("pCboEmpresaUsuario"));
		log.info("pCboTipoUsuario = "+prmtBusqueda.get("pCboTipoUsuario"));
		log.info("pCboEstadoUsuario = "+prmtBusqueda.get("pCboEstadoUsuario"));
		log.info("pCboPerfil = "+prmtBusqueda.get("pCboPerfil"));
		log.info("pTxtUsuario = "+prmtBusqueda.get("pTxtUsuario"));
		log.info("pCboTipoSucursal = "+prmtBusqueda.get("pCboTipoSucursal"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaUsuarios", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaUsuarios, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarAccesosDetalle(Object prmtAccesosDet)
			throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAccesosDetalle()");
		HashMap prmtBusqueda = (HashMap)prmtAccesosDet;
		log.info("Parametros de Busqueda de Accesos Fuera de Hora: ");
		log.info("-------------------------------------");
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAccesosDetalle", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAccesosDetalle, prmtBusqueda)");
		return rs;
	}

	public void grabarAccesoEspecial(Object prmt) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarAccesoEspecial-------------------");
	    AccesoEspecial acceso = (AccesoEspecial)prmt;
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarAccesoEspecial", acceso);
			log.info("Se invocó al procedure *.grabarAccesoEspecial");
		}catch (Exception e){
			log.info("ERROR EN  grabarAccesoEspecial GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public void grabarAccesoEspecialDet(Object prmt) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarAccesoEspecialDet-------------------");
	    AccesoEspecialDet accesoDet = (AccesoEspecialDet)prmt;
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarAccesoEspecialDet", accesoDet);
			log.info("Se invocó al procedure *.grabarAccesoEspecialDet");
		}catch (Exception e){
			log.info("ERROR EN  grabarAccesoEspecialDet GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public void eliminarAccesoEspecial(Object prmtAccesos) throws DaoException {
		HashMap map = new HashMap();
	    map = (HashMap)prmtAccesos;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarAccesoEspecial", map);
	        log.info("Se invocó al procedure *.eliminarAccesoEspecial");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarAccesoEspecial GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}

	public void eliminarRegistroPc(Object prmtRegPc) throws DaoException {
		RegistroPc map = new RegistroPc();
	    map = (RegistroPc)prmtRegPc;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarRegistroPc", map);
	        log.info("Se invocó al procedure *.eliminarRegistroPc");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarRegistroPc GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}

	public ArrayList listarMotivosAcceso(Object prmtMotivos)
			throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarMotivosAcceso()");
		HashMap prmtBusqueda = (HashMap)prmtMotivos;
		log.info("Parametros de Busqueda de Accesos Especiales: ");
		log.info("-------------------------------------");
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaMotivosAcceso", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaMotivosAcceso, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarRegPol(Object prmtBusq) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarRegPol()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("Parametros de Busqueda de Reglamentos y Politicas: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa: "+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdPerfil: "+prmtBusqueda.get("pIntIdPerfil"));
		log.info("pStrIdTransaccion: "+prmtBusqueda.get("pStrIdTransaccion"));
		log.info("pIntIdEstado: "+prmtBusqueda.get("pIntIdEstado"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaRegPol", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaRegPol, prmtBusqueda)");
		return rs;
	}
	
	//Relación Formulario - Documentación
	public void grabarFormDoc(Object o) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarFormDoc-----------------");
		AdmFormDoc formdoc = (AdmFormDoc)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarFormDoc(Object o)");
		log.info("--------------------Tabla SEG_V_DOCPROCESO----------------------------");
		log.info("per.getIntIdEmpresa()"		+ formdoc.getIntIdEmpresa());
	    log.info("formdoc.getIntIdPerfil()"		+ formdoc.getIntIdPerfil());
	    log.info("formdoc.getStrIdTransaccion()"+ formdoc.getStrIdTransaccion());
	    log.info("formdoc.getIntVersionDoc()" 	+ formdoc.getIntVersionDoc());
	    log.info("formdoc.getStrArchDoc()"  	+ formdoc.getStrArchDoc());
	    log.info("formdoc.getIntChkPerfil()"	+ formdoc.getIntPerfil());
	    log.info("formdoc.getIntIdEstado()" 	+ formdoc.getIntIdEstado());
		
		HashMap map = new HashMap();
		// Tabla SEG_V_DOCUMENTOS 
		map.put("pIntIdEmpresa",		 formdoc.getIntIdEmpresa());
		map.put("pIntIdPerfil",  		 formdoc.getIntIdPerfil());
		map.put("pStrIdTransaccion",	 formdoc.getStrIdTransaccion());
		map.put("pIntIdTipoDoc",	 	 formdoc.getIntIdTipoDoc());
		map.put("pIntIdTipoDemo",	 	 null);
		map.put("pIntIdVersionDoc",	 	 formdoc.getIntVersionDoc());
		map.put("pStrArcDoc", 	 		 formdoc.getStrArchDoc());
		map.put("pIntIdEmpresaUsu", 	 null);
		map.put("pIntIdUsuario", 	 	 null);
		map.put("pIntPerfil", 		  	 formdoc.getIntPerfil());
		map.put("pIntIdEstado",  		 formdoc.getIntIdEstado());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarFormDoc", map);
			log.info("Se invocó al procedure *.grabarFormDoc");
		}catch (Exception e){
			log.info("ERROR EN  grabarFormDoc GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarFormDemo(Object o) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarFormDemo-----------------");
		AdmFormDoc formdoc = (AdmFormDoc)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarFormDemo(Object o)");
		log.info("--------------------Tabla SEG_V_DOCDEMO----------------------------");
		log.info("per.getIntIdEmpresa()"		+ formdoc.getIntIdEmpresa());
	    log.info("formdoc.getIntIdPerfil()"		+ formdoc.getIntIdPerfil());
	    log.info("formdoc.getStrIdTransaccion()"+ formdoc.getStrIdTransaccion());
	    log.info("formdoc.getIntVersionDemo()"  + formdoc.getIntVersionDemo());
	    log.info("formdoc.getStrArchDoc()"  	+ formdoc.getStrArchDemo());
	    log.info("formdoc.getIntIdEmpresaUsu()"	+ null);
	    log.info("formdoc.getIntIdUsuario()"	+ null);
	    log.info("formdoc.getIntChkPerfil()"	+ formdoc.getIntPerfil());
	    log.info("formdoc.getIntIdEstado()" 	+ formdoc.getIntIdEstado());
		
		HashMap map = new HashMap();
		// Tabla SEG_M_TRANSACCIONES 
		map.put("pIntIdEmpresa",		 formdoc.getIntIdEmpresa());
		map.put("pIntIdPerfil",  		 formdoc.getIntIdPerfil());
		map.put("pStrIdTransaccion",	 formdoc.getStrIdTransaccion());
		map.put("pIntIdTipoDoc",	 	 null);
		map.put("pIntIdTipoDemo",	 	 formdoc.getIntIdTipoDemo());
		//map.put("pIntIdVersionDemo",	 formdoc.getIntVersionDemo());
		//map.put("pStrArcDemo", 	 	 formdoc.getStrArchDemo());
		map.put("pIntIdVersionDemo",	 formdoc.getIntVersionDemo());
		map.put("pStrArcDemo", 	 		 formdoc.getStrArchDemo());
		map.put("pIntIdEmpresaUsu", 	 null);
		map.put("pIntIdUsuario", 	 	 null);
		map.put("pIntPerfil", 		  	 formdoc.getIntPerfil());
		map.put("pIntIdEstado",  		 formdoc.getIntIdEstado());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarFormDemo", map);
			log.info("Se invocó al procedure *.grabarFormDemo");
		}catch (Exception e){
			log.info("ERROR EN  grabarFormDemo GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarFormAdmDoc(Object prmtBusq) throws DaoException {
	    log.info("Entrando al método listarFormAdmDoc()");
	    HashMap prmtBusqueda = (HashMap)prmtBusq;
	    log.info("Parametros de Busqueda de AdminMenu: ");
	    log.info("-------------------------------------");
	    log.info("pIntIdEmpresa  = " + prmtBusqueda.get("pIntIdEmpresa"));
	    log.info("pIntIdPerfil   = " + prmtBusqueda.get("pIntIdPerfil"));
	    log.info("pStrIdTransaccion = " + prmtBusqueda.get("pStrIdTransaccion"));
	    log.info("pIntIdVersionDoc  = " + prmtBusqueda.get("pIntIdVersionDoc"));
	    log.info("pIntIdVersionDemo = " + prmtBusqueda.get("pIntIdVersionDemo"));
	    log.info("pStrArchDoc    = " + prmtBusqueda.get("pStrArchDoc"));
	    log.info("pStrArchDemo   = " + prmtBusqueda.get("pStrArchDemo"));
	    log.info("pTxtEmpresa    = " + prmtBusqueda.get("pTxtEmpresa"));
	    log.info("pCboEstado     = " + prmtBusqueda.get("pCboEstado"));
	    log.info("pTxtPerfil     = " + prmtBusqueda.get("pTxtPerfil"));
	    
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFormAdmDoc", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaFormAdmDoc, prmtBusqueda)");
	    return rs;
	}
	
	public void eliminarAdmFormDoc(Object prmtAdmFormDoc)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtAdmFormDoc;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarAdmFormDoc").toString(), map);
	        log.info("Se invocó al procedure *.eliminarAdmFormDoc");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN  .eliminarAdmFormDoc GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	//Relación Perfil / MOF
	public ArrayList listarPerfilMof(Object prmtBusq) throws DaoException {
	    log.info("Entrando al método listarPerfilMof()");
	    HashMap prmtBusqueda = (HashMap)prmtBusq;
	    log.info("Parametros de Búsqueda de PerfilMof: ");
	    log.info("-------------------------------------");
	    log.info("pTxtEmpresa  = " + prmtBusqueda.get("pTxtEmpresa"));
	    log.info("pTxtPerfil   = " + prmtBusqueda.get("pTxtPerfil"));
	    log.info("pCboEstado   = " + prmtBusqueda.get("pCboEstado"));
	    log.info("pIntIdEmpresa= " + prmtBusqueda.get("pIntIdEmpresa"));
	    log.info("pIntIdPerfil= "  + prmtBusqueda.get("pIntIdPerfil"));
	    log.info("pIntIdVersion= " + prmtBusqueda.get("pIntIdVersion"));
	    
	    ArrayList rs = new ArrayList();
	    log.info("Hashmap seteado.");
	    rs = (ArrayList)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerfilMof", prmtBusqueda);
	    log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerfilMof, prmtBusqueda)");
	    return rs;
	}
	
	public void grabarPerfilMof(Object o) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarPerfilMof-----------------");
		PerfilMof perm = (PerfilMof)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarPerfilMof(Object o)");
		log.info("--------------------Tabla SEG_V_MOF----------------------------");
		log.info("per.getIntIdEmpresa()"		+ perm.getIntIdEmpresa());
	    log.info("formdoc.getIntIdPerfil()"		+ perm.getIntIdPerfil());
	    log.info("formdoc.getIntVersionDoc()" 	+ perm.getIntVersion());
	    log.info("formdoc.getStrArchDoc()"  	+ perm.getStrArchivo());
	    log.info("formdoc.getIntIdEstado()" 	+ perm.getIntIdEstado());
	    log.info("formdoc.getIntChkPerfil()"	+ perm.getIntPerfil());
		
		HashMap map = new HashMap();
		// Tabla SEG_V_MOF 
		map.put("pIntIdEmpresa",		 perm.getIntIdEmpresa());
		map.put("pIntIdPerfil",  		 perm.getIntIdPerfil());
		map.put("pIntIdVersion",	 	 perm.getIntVersion());
		map.put("pStrArcDoc", 	 		 perm.getStrArchivo());
		map.put("pIntIdEmpresaUsu", 	 null);
		map.put("pIntIdUsuario", 	 	 null);
		map.put("pIntIdEstado",  		 perm.getIntIdEstado());
		map.put("pIntPerfil", 		  	 perm.getIntPerfil());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarPerfilMof", map);
			log.info("Se invocó al procedure *.grabarPerfilMof");
		}catch (Exception e){
			log.info("ERROR EN  grabarFormDoc GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	public void eliminarPerfilMof(Object prmtMenu) throws DaoException {
		HashMap map = new HashMap();
	    map = (HashMap)prmtMenu;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarPerfilMof", map);
	        log.info("Se invocó al procedure *.eliminarPerfilMof");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarPerfilMof GenericDaoIbatis");
	        throw new DaoException(e);
	    }
	}
	
	public void updateOrganigrama(Object o) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.updateOrganigrama-----------------");
		PerfilMof perm = (PerfilMof)o;
		
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarPerfilMof(Object o)");
		log.info("--------------------Tabla SEG_EMPRESA----------------------------");
		log.info("per.getIntIdEmpresa()"		+ perm.getIntIdEmpresa());
	    log.info("formdoc.getIntIdPerfil()"		+ perm.getIntIdPerfil());
		
		HashMap map = new HashMap();
		// Tabla SEG_V_MOF 
		map.put("pIntIdEmpresa",		 perm.getIntIdEmpresaOrg());
		map.put("pStrArchivo", 	 		 perm.getStrArchOrg());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".updateOrganigrama", map);
			log.info("Se invocó al procedure *.updateOrganigrama");
		}catch (Exception e){
			log.info("ERROR EN  updateOrganigrama GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public void grabarRegPol(Object regPol) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarRegPol-------------------");
	    ReglamentoPolitica pRegPol = (ReglamentoPolitica)regPol;
	    HashMap map = new HashMap();	    
	    map.put("intIdEmpresa",			pRegPol.getIntIdEmpresa());
	    map.put("intIdEstado",		 	pRegPol.getIntIdEstado());
	    map.put("intPerfilTodos",		pRegPol.getIntPerfilTodos());
	    map.put("intIdPerfil",		 	pRegPol.getIntIdPerfil());
	    map.put("strIdTransaccion",		pRegPol.getStrIdTransaccion());
	    map.put("intIdTipoDocumento",	pRegPol.getIntIdTipoDocumento());
	    map.put("strArchivo",		 	pRegPol.getStrArchivo());
	    map.put("intIdEmpUsu",		 	pRegPol.getIntIdEmpUsu());
	    map.put("intIdUsuario",		 	pRegPol.getIntIdUsuario());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarRegPol", map);
			log.info("Se invocó al procedure *.grabarRegPol");
		}catch (Exception e){
			log.info("ERROR EN  grabarRegPol GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public void eliminarRegPol(Object regPol) throws DaoException {
		ReglamentoPolitica map = new ReglamentoPolitica();
	    map = (ReglamentoPolitica)regPol;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarRegPol", map);
	        log.info("Se invocó al procedure *.eliminarRegPol");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarRegPol GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}

	public ArrayList listarAccesosExtPc(Object prmtAccesosExtPc)
			throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAccesosExtPc()");
		AccesoExternoPc prmtBusqueda = (AccesoExternoPc)prmtAccesosExtPc;
		log.info("Parametros de Busqueda de Accesos Externos Pc: ");
		log.info("-------------------------------------");
		log.info("intIdEmpresa: "+prmtBusqueda.getIntIdEmpresa());
		log.info("intIdSucursal: "+prmtBusqueda.getIntIdSucursal());
		log.info("intIdArea: "+prmtBusqueda.getIntIdArea());
		log.info("intIdComputadora: "+prmtBusqueda.getIntIdComputadora());
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAccesosExtPc", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAccesosExtPc, prmtBusqueda)");
		return rs;
	}

	public void grabarAccesoExtPc(Object accesoExtPc) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarAccesoExtPc-------------------");
	    AccesoExternoPc pAccesoExtPc = (AccesoExternoPc)accesoExtPc;
	    log.info("pAccesoExtPc.getIntIdEmpresa(): "+pAccesoExtPc.getIntIdEmpresa());
	    log.info("pAccesoExtPc.getIntIdSucursal(): "+pAccesoExtPc.getIntIdSucursal());
	    log.info("pAccesoExtPc.getIntIdArea(): "+pAccesoExtPc.getIntIdArea());
	    log.info("pAccesoExtPc.getIntIdComputadora(): "+pAccesoExtPc.getIntIdComputadora());
	    log.info("pAccesoExtPc.getIntIdTipoAcceso(): "+pAccesoExtPc.getIntIdTipoAcceso());
	    log.info("pAccesoExtPc.getIntIdEstado(): "+pAccesoExtPc.getIntIdEstado());
	    log.info("pAccesoExtPc.getIntConta(): "+pAccesoExtPc.getIntConta());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarAccesoExtPc", pAccesoExtPc);
			log.info("Se invocó al procedure *.grabarAccesoExtPc");
		}catch (Exception e){
			log.info("ERROR EN  .grabarAccesoExtPc GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}

	public void eliminarSolCambio(Object prmtSolicitud) throws DaoException {
	    HashMap map = (HashMap)prmtSolicitud;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarSolCambio", map);
	        log.info("Se invocó al procedure *.eliminarSolCambio");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarSolCambio GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}

	public ArrayList listarSesiones(Object prmtSesiones) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarSesiones()");
		Sesiones prmtBusqueda = (Sesiones)prmtSesiones;
		log.info("Parametros de Busqueda de Sesiones: ");
		log.info("-------------------------------------");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSesiones", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSesiones, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList loginUsuarios(String codigo, String contrasena,String idEmpresa) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.loginUsuarios()");
		HashMap prmtBusquedaUsuario = new HashMap();
		//HashMap prmtUsuario = (HashMap)usuario;
		prmtBusquedaUsuario.put("pTxtUsuario", codigo);
		prmtBusquedaUsuario.put("pTxtContrasena", contrasena);
		prmtBusquedaUsuario.put("pTxtEmpresa", new Integer(idEmpresa));
		//prmtBusquedaUsuario.put("pTxtPerfil", perfil);
		
		log.info("Parametros de Busqueda de Usuarios: ");
		log.info("-------------------------------------");

		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");		
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLoginUsuarios", prmtBusquedaUsuario);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getLoginUsuarios, prmtUsuario)");
		return rs;
	}
	
	/*public ArrayList numIntentos(int codEmpresa) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.loginUsuarios()");
		HashMap prmtBusquedaUsuario = new HashMap();
		//HashMap prmtUsuario = (HashMap)usuario;
		prmtBusquedaUsuario.put("pIntIdEmpresa", codEmpresa);
		
		log.info("Parametros de Busqueda de Usuarios: ");
		log.info("-------------------------------------");

		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");		
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getNumIntentos", prmtBusquedaUsuario);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getLoginUsuarios, prmtUsuario)");
		return rs;
	}*/
	
	public ArrayList fecRegistro(int codUsuario) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.loginUsuarios()");
		HashMap prmtBusquedaUsuario = new HashMap();
		//HashMap prmtUsuario = (HashMap)usuario;
		prmtBusquedaUsuario.put("pIntIdUsuario", codUsuario);
		
		log.info("Parametros de Busqueda de Usuarios: ");
		log.info("-------------------------------------");

		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");		
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getFecRegistro", prmtBusquedaUsuario);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getLoginUsuarios, prmtUsuario)");
		return rs;
	}
	
	public ArrayList errorIntentos(int codUsuario) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.loginUsuarios()");
		HashMap prmtBusquedaUsuario = new HashMap();
		//HashMap prmtUsuario = (HashMap)usuario;
		prmtBusquedaUsuario.put("pIntIdUsuario", codUsuario);
		
		log.info("Parametros de Busqueda de Usuarios: ");
		log.info("-------------------------------------");

		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");		
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getErrorIntentos", prmtBusquedaUsuario);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getErrorIntentos, prmtUsuario)");
		return rs;
	}
	
	public void grabarErrorIntentos(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarEmpresaUsuario-------------------");
	    Usuario usu = new Usuario();
		usu = (Usuario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarErrorIntentos(Object o)");
		log.info("--------------------Tabla SEG_M_USUARIO--------------------");
		log.info("usu.getIntIdPersona() = "   + usu.getIntIdPersona());
		log.info("usu.getIntErrIntentos() = "   + usu.getIntErrIntentos());
		
		HashMap map = new HashMap();
		map.put("pIntIdPersona", 	usu.getIntIdPersona());
		map.put("pIntNumIntentos", 	usu.getIntErrIntentos());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarErrorIntentos", map);
			log.info("Se invocó al procedure *.grabarErrorIntentos");
		}catch (Exception e){
			log.info("ERROR EN  grabarErrorIntentos GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void bloquearUsuario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarEmpresaUsuario-------------------");
	    Usuario usu = new Usuario();
		usu = (Usuario)o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarBloqueoUsuario(Object o)");
		log.info("--------------------Tabla SEG_M_USUARIO--------------------");
		log.info("usu.getIntIdPersona() = "   + usu.getIntIdPersona());
		
		HashMap map = new HashMap();
		map.put("pIntIdPersona", 	usu.getIntIdPersona());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarBloqueoUsuario", map);
			log.info("Se invocó al procedure *.grabarBloqueoUsuario");
		}catch (Exception e){
			log.info("ERROR EN  grabarBloqueoUsuario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	//Auditoría
	public ArrayList listarCols(Object prmtBusq) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarCols()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("Parametros de Busqueda de Reglamentos y Politicas: ");
		log.info("-------------------------------------");
		log.info("pDaFecIni: "+prmtBusqueda.get("pDaFecIni"));
		log.info("pDaFecFin: "+prmtBusqueda.get("pDaFecFin"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCols", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaCols, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarAplicacion(Object prmtBusq) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAplicacion()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("Parametros de Busqueda de Reglamentos y Politicas: ");
		log.info("-------------------------------------");
		log.info("pIntIdEmpresa: "		+prmtBusqueda.get("pIntIdEmpresa"));
		log.info("pIntIdTipoAplicacion:"+prmtBusqueda.get("pIntIdTipoAplicacion"));
		log.info("pStrIdTransacciones:" +prmtBusqueda.get("pStrIdTransacciones"));
		log.info("pIntIdPersona: "		+prmtBusqueda.get("pIntIdPersona"));
		log.info("pIntIdTipoReporte: "	+prmtBusqueda.get("pIntIdTipoReporte"));
		log.info("pDaFecIni: "			+prmtBusqueda.get("pDaFecIni"));
		log.info("pDaFecFin: "			+prmtBusqueda.get("pDaFecFin"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAplicacion", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAplicacion, prmtBusqueda)");
		return rs;
	}

	public void eliminarSesiones(Object audisis) throws DaoException {
		Sesiones map = (Sesiones)audisis;
	    try{
	        getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarSesiones", map);
	        log.info("Se invocó al procedure *.eliminarSesiones");
	    }catch(Exception e){
	        log.info("ERROR EN .eliminarSesiones GenericDaoIbatis ");
	        throw new DaoException(e);
	    }
	}

	public ArrayList listarColumnas(Object prmtColumnas) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarColumnas()");
		HashMap prmtBusqueda = (HashMap)prmtColumnas;
		log.info("Parametros de Busqueda de Columnas: ");
		log.info("-------------------------------------");
		log.info("pNombreTabla: "+prmtBusqueda.get("pNombreTabla"));
		log.info("Hashmap seteado.");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaColumnas", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaColumnas, prmtBusqueda)");
		return rs;
	}

	public ArrayList listarAuditoriaTablas(Object prmtAudisis) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarAuditoriaTablas()");
		AuditoriaSistemas prmtBusqueda = (AuditoriaSistemas)prmtAudisis;
		log.info("Parametros de busqueda de Auditoria de Tablas: ");
		log.info("-----------------------------------------------");
		log.info("strFechaInicio: "+prmtBusqueda.getStrFechaInicio());
		log.info("strFechaFin: "+prmtBusqueda.getStrFechaFin());
		log.info("Parametros seteados.");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAuditoriaTablas", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaAuditoriaTablas, prmtBusqueda)");
		return rs;
	}

	public void grabarSocaDetalle(Object obj) throws DaoException {
		log.info("-----------------Debugging GenericDaoIbatis.grabarSocaDetalle-------------------");
	    DataObjects dobj = (DataObjects)obj;
		
		log.info("PARAMETROS DE ENTRDA ...");
		log.info("--------------------Tabla SEG_V_SOCADETALLE----------------------------");
		log.info("dobj.getIntCodigo() = " + dobj.getIntCodigo());
		log.info("dobj.getIntIdEmpresa() = " + dobj.getIntIdEmpresa());
		log.info("dobj.getStrIdTransaccion() = " + dobj.getStrIdTransaccion());
		log.info("dobj.getIntIdSolicitud() = " + dobj.getIntIdSolicitud());
		log.info("dobj.getIntConta() = " + dobj.getIntConta());
		
		HashMap map = new HashMap();
		// Tabla SEG_M_TRANSACCIONES 		
		map.put("pIntIdEmpresa", dobj.getIntIdEmpresa());
		map.put("pStrIdTransaccion", dobj.getStrIdTransaccion());
		map.put("pIntIdSocaItem", dobj.getIntIdSolicitud());
		map.put("pIntDiccCodigo", dobj.getIntCodigo());
		map.put("pIntConta", dobj.getIntConta());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSocaDetalle", map);
			log.info("Se invocó al procedure *.grabarSocaDetalle");
		}catch (Exception e){
			log.info("ERROR EN  grabarDataObjects GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarFormReport(Object prmtBusq) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.listarFormReport()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("Parametros de Búsqueda de Formulario y Reportes:");
		log.info("-------------------------------------");
		log.info("pDaFecIni: "		  +prmtBusqueda.get("pDaFecIni"));
		log.info("pDaFecFin: "		  +prmtBusqueda.get("pDaFecFin"));
		log.info("pIdTransacciones: " +prmtBusqueda.get("pIdTransacciones"));
		log.info("pIdDataObjects: "	  +prmtBusqueda.get("pIdDataObjects"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFormReport", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaFormReport, prmtBusqueda)");
		return rs;
	}

	public ArrayList obtenetIdTransaccion(Object menuIdTran) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.obtenetIdTransaccion()");
		AdminMenu prmtBusqueda = (AdminMenu)menuIdTran;
		log.info("Parametros de busqueda de Auditoria de Tablas: ");
		log.info("-----------------------------------------------");
		log.info("strIdTransaccionPadre: "+prmtBusqueda.getStrIdTransaccionPadre());
		log.info("intNivelMenu: "+prmtBusqueda.getIntNivelMenu());
		log.info("Parametros seteados.");
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getIdTransaccion", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList("+getNameSpace()+".getIdTransaccion, prmtBusqueda)");
		return rs;
	}
	
}