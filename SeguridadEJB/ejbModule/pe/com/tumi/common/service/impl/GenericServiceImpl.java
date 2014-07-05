package pe.com.tumi.common.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pe.com.tumi.common.dao.GenericDao;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.seguridad.domain.AccesoEspecial;
import pe.com.tumi.seguridad.domain.Sesiones;

public class GenericServiceImpl implements GenericService {

	private GenericDao genericDao;
	
	public Object findById(Long id) throws DaoException{
		try{
			return getDAO().findById(id);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public List findByQueryWithDesc(String queryName) throws DaoException{
		try {
			return getDAO().findByQueryWithDesc(queryName);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public List findAll() throws DaoException{
		try{
			return getDAO().findAll();
		}catch(Exception e) {
			throw new DaoException(e);			
		}		
	}
	
/**Autor : Eyder Uceda Lopez
 * Fecha : 20110624
 * Metodo de Busqueda de Certificaciones 	
 */
	public List findDocumentos(String certificado ,String fehcaInicio ,String fechaFin ,
							   String moneda ,String usuario) throws DaoException{
		try{
			return getDAO().findDocumentos(certificado,fehcaInicio,fechaFin,moneda,usuario);
		}catch(Exception e) {
			throw new DaoException(e);			
		}		
	}
	
	public Object findByObject(Object o) throws DaoException{
		try{
			return getDAO().findByObject(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}		
	}
	
	public void save (Object o) throws DaoException{
		try{
			getDAO().save(o);
		}catch(Exception e) {
			throw new DaoException(e);	
		}	
	}

	public void save (Collection collection) throws DaoException{
		try{
			getDAO().save(collection);
		}catch(Exception e) {
			throw new DaoException(e);	
		}	
	}
	
	public void saveRelationalTables(Long id, Collection collection) throws DaoException{
		try{
			getDAO().saveRelationalTables(id, collection);
		}catch(Exception e) {
			throw new DaoException(e);	
		}	
	}

	
	public void update (Object o) throws DaoException{
		try{
			getDAO().update(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public void delete (Long id) throws DaoException{
		try {
			getDAO().delete(id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void delete (Long[] idsToDelete) throws DaoException{
		try {
			getDAO().delete(idsToDelete);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void deleteRelationalTables (Long id, Collection collection) throws DaoException{
		try {
			getDAO().deleteRelationalTables(id, collection);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void delete (Object o) throws DaoException{
		try {
			getDAO().delete(o);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public List findByNamedQuery(String queryName) throws DaoException{
		try {
			return getDAO().findByNamedQuery(queryName);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public List findByNamedQuery(String queryName, Map parameters) throws DaoException{
		try {
			return getDAO().findByNamedQuery(queryName , parameters);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public Connection getConnection() throws DaoException {
		try {
			return getDAO().getConnection();
		} catch (Exception e) {
			throw new DaoException(e);
		}		
	}

	public void setGenericDAO(GenericDao GenericDao) {
		this.genericDao = GenericDao;
	}

	public GenericDao getDAO() {
		return genericDao;
	}
	
	public void grabarEmpresa(Object o) throws DaoException{
		try{
			getDAO().grabarEmpresa(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}		
	}

	/*public ArrayList listarEmpresas(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getDAO().listarEmpresas(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}*/
	
	public ArrayList listarEmpresasUsuario(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getDAO().listarEmpresasUsuario(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarEmpresas2(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getDAO().listarEmpresas2(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void grabarSucursal(Object o) throws DaoException {
		try{
			getDAO().grabarSucursal(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarArea(Object o) throws DaoException {
		try{
			getDAO().grabarArea(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarDomicilio(Object o) throws DaoException {
		try{
			getDAO().grabarDomicilio(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	/*public void grabarTercero(Object o) throws DaoException {
		try{
			getDAO().grabarTercero(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarSubsucursal(Object o) throws DaoException {
		try{
			getDAO().grabarSubsucursal(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}*/
	/*
	public void grabarAreaCodigo(Object o) throws DaoException {
		try{
			getDAO().grabarAreaCodigo(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	*/
	public ArrayList listarSucursales(Object prmtBusqSucursales) throws DaoException {
		try{
			return getDAO().listarSucursales(prmtBusqSucursales);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarSubSucursales(Object prmtBusqSubSucursales) throws DaoException {
		try{
			return getDAO().listarSubSucursales(prmtBusqSubSucursales);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarAreas(Object prmtBusqAreas) throws DaoException {
		try{
			return getDAO().listarAreas(prmtBusqAreas);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarZonal(Object prmtBusqZonal) throws DaoException {
		try{
			return getDAO().listarZonal(prmtBusqZonal);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarDomicilio(Object prmtBusqSucursales) throws DaoException {
		try{
			return getDAO().listarDomicilio(prmtBusqSucursales);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarTercero(Object prmtBusqSubsucursal) throws DaoException {
		try{
			return getDAO().listarTercero(prmtBusqSubsucursal);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarSubsucursal(Object prmtBusqSubsucursal) throws DaoException {
		try{
			return getDAO().listarSubsucursal(prmtBusqSubsucursal);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarAreaCodigo(Object prmtBusqAreaCodigo) throws DaoException {
		try{
			return getDAO().listarAreaCodigo(prmtBusqAreaCodigo);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public List listarOpcionesxUsuario(Long sistema) throws DaoException {
		try {
			return getDAO().listarOpcionesxUsuario(sistema);
		} catch (Exception e) {
			throw new DaoException(e);
		}	
	}
	
	public List listarUsuariosInactivos(Long sistema, Long limite) throws DaoException {
		try {
			return getDAO().listarUsuariosInactivos(sistema, limite);
		} catch (Exception e) {
			throw new DaoException(e);
		}	
	}
	
	public List listarUsuariosLogueados(Long sistema, Date fechaInicial, Date fechaFinal) throws DaoException {
		try {
			return getDAO().listarUsuariosLogueados(sistema, fechaInicial, fechaFinal);
		} catch (Exception e) {
			throw new DaoException(e);
		}	
	}

	public void eliminarEmpresa(Object prmtEmpresa) throws DaoException {
		try{
			getDAO().eliminarEmpresas(prmtEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void eliminarSucursal(Object prmtSucursal) throws DaoException {
		try{
			getDAO().eliminarSucursales(prmtSucursal);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public ArrayList listarPerNatural(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getDAO().listarPerNatural(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	/*public void grabarZonal(Object o) throws DaoException{
		try{
			getDAO().grabarZonal(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}		
	}*/
	
	public void eliminarZonal(Object prmtZonal) throws DaoException {
		try{
			getDAO().eliminarZonal(prmtZonal);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	//Metodos para Perfil
	
	public ArrayList listarPerfiles(Object prmtBusqPerfil) throws DaoException {
		try{
			return getDAO().listarPerfiles(prmtBusqPerfil);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPerfiles1(Object prmtBusqPerfil) throws DaoException {
		try{
			return getDAO().listarPerfiles1(prmtBusqPerfil);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPerfiles2(Object prmtBusqPerfil) throws DaoException {
		try{
			return getDAO().listarPerfiles2(prmtBusqPerfil);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPerfusuario(Object prmtBusqPerfil) throws DaoException {
		try{
			return getDAO().listarPerfusuario(prmtBusqPerfil);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarPerfil(Object o) throws DaoException {
		try{
			getDAO().grabarPerfil(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarPerfilDet(Object o) throws DaoException {
		try{
			getDAO().grabarPerfilDet(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void eliminarPerfil(Object prmtPerfil) throws DaoException {
		try{
			getDAO().eliminarPerfil(prmtPerfil);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void eliminarPerfilDet(Object prmtPerfilDet) throws DaoException {
		try{
			getDAO().eliminarPerfilDet(prmtPerfilDet);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public ArrayList listarMenues(Object prmtBusqPerfil) throws DaoException {
		try{
			return getDAO().listarMenues(prmtBusqPerfil);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarAdminMenu(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarAdminMenu(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarMenuPerfil(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarMenuPerfil(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarMenuPerfil1(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarMenuPerfil1(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPerfilDetalle(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarPerfilDetalle(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarMenuPerfUsuario(Object prmtBusqMenu) throws DaoException {
		try{
			return getDAO().listarMenuPerfUsuario(prmtBusqMenu);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	//Metodos para Usuario
	
	public ArrayList listarUsuarios(Object prmtBusqUsuario) throws DaoException {
		try{
			return getDAO().listarUsuarios(prmtBusqUsuario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarComunicacion(Object prmtBusqComunicacion) throws DaoException {
		try{
			return getDAO().listarComunicacion(prmtBusqComunicacion);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarEmpresaUsuario(Object prmtBusqEmpresaUsuario) throws DaoException {
		try{
			return getDAO().listarEmpresaUsuario(prmtBusqEmpresaUsuario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPerfilUsuario(Object prmtBusqPerfilUsuario) throws DaoException {
		try{
			return getDAO().listarPerfilUsuario(prmtBusqPerfilUsuario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarSucursalUsuario(Object prmtBusqSucursalUsuario) throws DaoException {
		try{
			return getDAO().listarSucursalUsuario(prmtBusqSucursalUsuario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarSubSucursalUsuario(Object prmtBusqSubSucursalUsuario) throws DaoException {
		try{
			return getDAO().listarSubSucursalUsuario(prmtBusqSubSucursalUsuario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarUsuario(Object o) throws DaoException {
		try{
			getDAO().grabarUsuario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	/*public void grabarComunicacion(Object o) throws DaoException {
		try{
			getDAO().grabarComunicacion(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}*/
	
	public void grabarEmpresaUsuario(Object o) throws DaoException {
		try{
			getDAO().grabarEmpresaUsuario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarPerfilUsuario(Object o) throws DaoException {
		try{
			getDAO().grabarPerfilUsuario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarSucursalUsuario(Object o) throws DaoException {
		try{
			getDAO().grabarSucursalUsuario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarSubSucursalUsuario(Object o) throws DaoException {
		try{
			getDAO().grabarSubSucursalUsuario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void eliminarUsuarioDet(Object prmtUsuarioDet) throws DaoException {
		try{
			getDAO().eliminarUsuarioDet(prmtUsuarioDet);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void eliminarSubSucursalUsuario(Object prmtSubSucursal) throws DaoException {
		try{
			getDAO().eliminarSubSucursalUsuario(prmtSubSucursal);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void eliminarSucursalUsuario(Object prmtSucursal) throws DaoException {
		try{
			getDAO().eliminarSucursalUsuario(prmtSucursal);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void eliminarPerfilUsuario(Object prmtPerfil) throws DaoException {
		try{
			getDAO().eliminarPerfilUsuario(prmtPerfil);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void eliminarEmpresaUsuario(Object prmtEmpresa) throws DaoException {
		try{
			getDAO().eliminarEmpresaUsuario(prmtEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	///////////////////////////////////////////////////////////////
	public void eliminarMenu(Object prmtMenu) throws DaoException {
		try{
			getDAO().eliminarMenu(prmtMenu);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}

	public ArrayList listarDataObjects(Object prmt) throws DaoException {
		try{
			return getDAO().listarDataObjects(prmt);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	//Métodos para Permiso
	
	public ArrayList listarPermisos(Object prmtBusqPermisos) throws DaoException {
		try{
			return getDAO().listarPermisos(prmtBusqPermisos);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPerfilesxUsuario(Object prmtBusqPerfUsr) throws DaoException {
		try{
			return getDAO().listarPerfilesxUsuario(prmtBusqPerfUsr);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarMenuesPermiso(Object prmtBusqMenuPermiso) throws DaoException {
		try{
			return getDAO().listarMenuesPermiso(prmtBusqMenuPermiso);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarPermiso(Object o) throws DaoException {
		try{
			getDAO().grabarPermiso(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void eliminarPermiso(Object prmtMenu) throws DaoException {
		try{
			getDAO().eliminarPermiso(prmtMenu);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void deletePermiso(Object prmtPermiso) throws DaoException {
		try{
			getDAO().deletePermiso(prmtPermiso);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	//Metodos para Horarios
	
	public ArrayList listarHorarios(Object prmtBusqHorario) throws DaoException {
		try{
			return getDAO().listarHorarios(prmtBusqHorario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarHorario(Object o) throws DaoException {
		try{
			getDAO().grabarHorario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarHorarioDet(Object o) throws DaoException {
		try{
			getDAO().grabarHorarioDet(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarHorariosDet(Object prmtBusqHorario) throws DaoException {
		try{
			return getDAO().listarHorariosDet(prmtBusqHorario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void eliminarHorario(Object prmtHorario) throws DaoException {
		try{
			getDAO().eliminarHorario(prmtHorario);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}

	public void grabarDataObjects(Object obj) throws DaoException {
		try{
			getDAO().grabarDataObjects(obj);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList listarSolicitudes(Object prmtSolicitudes)
			throws DaoException {
		try{
			return getDAO().listarSolicitudes(prmtSolicitudes);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarUsuariosEmpresa(Object prmtBusqUsrEmpresa) throws DaoException {
		try{
			return getDAO().listarUsuariosEmpresa(prmtBusqUsrEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarUsuariosEmpresa1(Object prmtBusqUsrEmpresa) throws DaoException {
		try{
			return getDAO().listarUsuariosEmpresa1(prmtBusqUsrEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void grabarSolicitud(Object obj) throws DaoException {
		try{
			getDAO().grabarSolicitud(obj);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList listarRegistroPc(Object prmtRegPc) throws DaoException {
		try{
			return getDAO().listarRegistroPc(prmtRegPc);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void grabarRegistroPc(Object obj) throws DaoException {
		try{
			getDAO().grabarRegistroPc(obj);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public ArrayList listarAccesosEspeciales(Object prmtAccesos)
			throws DaoException {
		try{
			return getDAO().listarAccesosEspeciales(prmtAccesos);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	//Relación Formulario - Documentación
	public void grabarFormDoc(Object o) throws DaoException {
		try{
			getDAO().grabarFormDoc(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarFormDemo(Object o) throws DaoException {
		try{
			getDAO().grabarFormDemo(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarFormAdmDoc(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarFormAdmDoc(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void eliminarAdmFormDoc(Object prmtAdmFormDoc) throws DaoException {
		try{
			getDAO().eliminarAdmFormDoc(prmtAdmFormDoc);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	//Relación Perfil / MOF
	public ArrayList listarPerfilMof(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarPerfilMof(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarPerfilMof(Object o) throws DaoException {
		try{
			getDAO().grabarPerfilMof(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void eliminarPerfilMof(Object o) throws DaoException {
		try{
			getDAO().eliminarPerfilMof(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void updateOrganigrama(Object o) throws DaoException {
		try{
			getDAO().updateOrganigrama(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	//Administracion de Menu
	public void grabarMenu(Object o) throws DaoException {
		try{
			getDAO().grabarMenu(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList listarAccesosDetalle(Object prmtAccesosDet)
			throws DaoException {
		try{
			return getDAO().listarAccesosDetalle(prmtAccesosDet);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void grabarAccesoEspecial(Object acceso) throws DaoException {
		try{
			getDAO().grabarAccesoEspecial(acceso);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void grabarAccesoEspecialDet(Object prmt)
			throws DaoException {
		try{
			getDAO().grabarAccesoEspecialDet(prmt);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void eliminarAccesoEspecial(Object prmtAccesos) throws DaoException {
		try{
			getDAO().eliminarAccesoEspecial(prmtAccesos);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}

	public void eliminarRegistroPc(Object prmtRegPc) throws DaoException {
		try{
			getDAO().eliminarRegistroPc(prmtRegPc);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}

	public ArrayList listarMotivosAcceso(Object prmtMotivos)
			throws DaoException {
		try{
			return getDAO().listarMotivosAcceso(prmtMotivos);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public ArrayList listarRegPol(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarRegPol(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public void grabarRegPol(Object regPol) throws DaoException {
		try{
			getDAO().grabarRegPol(regPol);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void eliminarRegPol(Object prmtRegPol) throws DaoException {
		try{
			getDAO().eliminarRegPol(prmtRegPol);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList listarAccesosExtPc(Object prmtAccesosExtPc)
			throws DaoException {
		try{
			return getDAO().listarAccesosExtPc(prmtAccesosExtPc);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public void grabarAccesoExtPc(Object accesoExtPc) throws DaoException {
		try{
			getDAO().grabarAccesoExtPc(accesoExtPc);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void eliminarSolCambio(Object prmtSolicitud) throws DaoException {
		try{
			getDAO().eliminarSolCambio(prmtSolicitud);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList listarSesiones(Object prmtSesiones) throws DaoException {
		try{
			return getDAO().listarSesiones(prmtSesiones);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	} 
	
	//Auditoría
	public ArrayList listarCols(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarCols(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarAplicacion(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarAplicacion(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public void eliminarSesiones(Object audisis) throws DaoException {
		try{
			getDAO().eliminarSesiones(audisis);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList listarColumnas(Object prmtColumnas) throws DaoException {
		try{
			return getDAO().listarColumnas(prmtColumnas);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarFormReport(Object prmtBusq) throws DaoException {
		try{
			return getDAO().listarFormReport(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList listarAuditoriaTablas(Object prmtAudisis) throws DaoException {
		try{
			return getDAO().listarAuditoriaTablas(prmtAudisis);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public void grabarSocaDetalle(Object obj) throws DaoException {
		try{
			getDAO().grabarSocaDetalle(obj);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList loginUsuarios(String codigo, String contrasena,String idEmpresa) throws DaoException {
		try{
			return getDAO().loginUsuarios(codigo, contrasena,idEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	/*public ArrayList numIntentos(int codEmpresa) throws DaoException {
		try{
			return getDAO().numIntentos(codEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}*/
	
	public ArrayList fecRegistro(int codUsuario) throws DaoException {
		try{
			return getDAO().fecRegistro(codUsuario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList errorIntentos(int codUsuario) throws DaoException {
		try{
			return getDAO().errorIntentos(codUsuario);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarErrorIntentos(Object o) throws DaoException {
		try{
			getDAO().grabarErrorIntentos(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void bloquearUsuario(Object o) throws DaoException {
		try{
			getDAO().bloquearUsuario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public void eliminarArea(Object obj) throws DaoException {
		try{
			getDAO().eliminarAreas(obj);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}

	public ArrayList obtenetIdTransaccion(Object menuIdTran) throws DaoException {
		try{
			return getDAO().obtenetIdTransaccion(menuIdTran);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
}