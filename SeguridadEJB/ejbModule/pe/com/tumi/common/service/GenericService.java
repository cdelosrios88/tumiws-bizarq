package pe.com.tumi.common.service;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.com.tumi.common.dao.GenericDao;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.seguridad.domain.AccesoEspecial;
import pe.com.tumi.seguridad.domain.AccesoExternoPc;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.AuditoriaSistemas;
import pe.com.tumi.seguridad.domain.DataObjects;
import pe.com.tumi.seguridad.domain.Sesiones;
import pe.com.tumi.seguridad.domain.ReglamentoPolitica;

public interface GenericService {
	
	public abstract void delete (Long id) throws DaoException;
	
	public abstract void delete (Long[] idsToDelete) throws DaoException;
	
	public abstract void delete (Object o) throws DaoException;
	
	public abstract void deleteRelationalTables (Long id , Collection collection) throws DaoException;

	public abstract List findAll() throws DaoException;	
	
	public abstract Object findById (Long id) throws DaoException;

	public abstract List findByNamedQuery(String queryName) throws DaoException;
	
	public abstract List findByNamedQuery(String queryName, Map parameters) throws DaoException;

	public abstract List findByQueryWithDesc(String queryName) throws DaoException;

	public abstract Object findByObject(Object o) throws DaoException;
	
	public abstract Connection getConnection() throws DaoException;

	public abstract void update (Object o) throws DaoException;

	public abstract void save (Object o) throws DaoException;
	
	public abstract void save(Collection collection) throws DaoException;
	
	public abstract void saveRelationalTables(Long id, Collection collection) throws DaoException;
	
	public abstract GenericDao getDAO();

	public abstract void grabarEmpresa (Object o) throws DaoException;

	//public abstract ArrayList listarEmpresas(Object prmtBusqEmpresa) throws DaoException;
	
	public abstract ArrayList listarEmpresasUsuario(Object prmtBusqEmpresa) throws DaoException;
	
	public abstract ArrayList listarEmpresas2(Object prmtBusqEmpresa) throws DaoException;

	public abstract void grabarSucursal(Object suc) throws DaoException;
	
	public abstract void grabarArea(Object area) throws DaoException;
	
	public abstract void grabarDomicilio(Object dom) throws DaoException;
	
	//public abstract void grabarTercero(Object ter) throws DaoException;
	
	//public abstract void grabarSubsucursal(Object sub) throws DaoException;
	
	//public abstract void grabarAreaCodigo(Object sub) throws DaoException;

	public abstract ArrayList listarSucursales(Object prmtBusqSucursales)throws DaoException;
	
	public abstract ArrayList listarSubSucursales(Object prmtBusqSubSucursales)throws DaoException;
	
	public abstract ArrayList listarAreas(Object prmtBusqAreas)throws DaoException;
	
	public abstract ArrayList listarZonal(Object prmtBusqZonal)throws DaoException;
	
	public abstract ArrayList listarDomicilio(Object prmtBusqDomicilio)throws DaoException;
	
	public abstract ArrayList listarTercero(Object prmtBusqTercero)throws DaoException;
	
	public abstract ArrayList listarSubsucursal(Object prmtBusqSubsucursal)throws DaoException;
	
	public abstract ArrayList listarAreaCodigo(Object prmtBusqAreaCodigo)throws DaoException;
	
	public abstract List listarOpcionesxUsuario(Long sistema) throws DaoException;
	
	public abstract List listarUsuariosInactivos(Long sistema, Long limite) throws DaoException;
	
	public abstract List listarUsuariosLogueados(Long sistema, Date fechaInicial, Date fechaFinal) throws DaoException;

	public abstract void eliminarEmpresa(Object prmtEmpresa) throws DaoException;
	
	public abstract void eliminarSucursal(Object prmtSucursal) throws DaoException;

	public abstract void eliminarArea(Object prmtArea) throws DaoException;

	public abstract ArrayList listarPerNatural(Object prmtBusqEmpresa) throws DaoException;

	//public abstract void grabarZonal(Object o) throws DaoException;

	public abstract void eliminarZonal(Object prmtZonal) throws DaoException;
	
	//Metodos para Perfil
	
	public abstract ArrayList listarPerfiles(Object prmtBusqPerfil) throws DaoException;
	
	public abstract ArrayList listarPerfiles1(Object prmtBusqPerfil) throws DaoException;
	
	public abstract ArrayList listarPerfiles2(Object prmtBusqPerfil) throws DaoException;
	
	public abstract ArrayList listarPerfusuario(Object prmtBusqPerfil) throws DaoException;
	
	public abstract void grabarPerfil(Object per) throws DaoException;
	
	public abstract void grabarPerfilDet(Object per) throws DaoException;
	
	public abstract void eliminarPerfil(Object prmtPerfil) throws DaoException;
	
	public abstract void eliminarPerfilDet(Object prmtPerfilDet) throws DaoException;
	
	public abstract ArrayList listarMenues(Object prmtBusqMenu) throws DaoException;

	public abstract ArrayList listarAdminMenu(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarMenuPerfil(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarMenuPerfil1(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarPerfilDetalle(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarMenuPerfUsuario(Object prmtBusqMenu) throws DaoException;
	
	//Métodos para Usuarios
	
	public abstract ArrayList listarUsuarios(Object prmtBusqUsuario) throws DaoException;
	
	public abstract ArrayList listarComunicacion(Object prmtBusqComunicacion)throws DaoException;
	
	public abstract ArrayList listarEmpresaUsuario(Object prmtBusqEmpresaUsuario)throws DaoException;
	
	public abstract ArrayList listarPerfilUsuario(Object prmtBusqPerfilUsuario)throws DaoException;
	
	public abstract ArrayList listarSucursalUsuario(Object prmtBusqSucursalUsuario)throws DaoException;
	
	public abstract ArrayList listarSubSucursalUsuario(Object prmtBusqSubSucursalUsuario)throws DaoException;
	
	public abstract void grabarUsuario(Object usu) throws DaoException;
	
	//public abstract void grabarComunicacion(Object com) throws DaoException;
	
	public abstract void grabarEmpresaUsuario(Object emp) throws DaoException;
	
	public abstract void grabarPerfilUsuario(Object perf) throws DaoException;
	
	public abstract void grabarSucursalUsuario(Object suc) throws DaoException;
	
	public abstract void grabarSubSucursalUsuario(Object suc) throws DaoException;
	
	public abstract void eliminarUsuarioDet(Object prmtUsuarioDet) throws DaoException;
	
	public abstract void eliminarSubSucursalUsuario(Object subsuc) throws DaoException;
	
	public abstract void eliminarSucursalUsuario(Object suc) throws DaoException;
	
	public abstract void eliminarPerfilUsuario(Object perf) throws DaoException;
	
	public abstract void eliminarEmpresaUsuario(Object emp) throws DaoException;
	
	public abstract ArrayList loginUsuarios(String codigo, String contrasena,String idEmpresa) throws DaoException;
	
	//public abstract ArrayList numIntentos(int codEmpresa) throws DaoException;
	
	public abstract ArrayList fecRegistro(int codUsuario) throws DaoException;
	
	public abstract ArrayList errorIntentos(int codUsuario) throws DaoException;
	
	public abstract void grabarErrorIntentos(Object o) throws DaoException, ParseException;
	
	public abstract void bloquearUsuario(Object o) throws DaoException, ParseException;
	
	///////////////////////////////////////////////////////////////////////////////
	
	public abstract void eliminarMenu(Object prmtMenu) throws DaoException;

	public abstract ArrayList listarDataObjects(Object prmtTablas) throws DaoException;
	
	//Métodos para Permiso
	
	public abstract ArrayList listarPermisos(Object prmtBusqPermisos)throws DaoException;
	
	public abstract ArrayList listarUsuariosEmpresa(Object prmtBusqUsrEmpresa)throws DaoException;
	
	public abstract ArrayList listarUsuariosEmpresa1(Object prmtBusqUsrEmpresa)throws DaoException;
	
	public abstract ArrayList listarPerfilesxUsuario(Object prmtBusqPerfUsr)throws DaoException;
	
	public abstract ArrayList listarMenuesPermiso(Object prmtBusqMenuPerm)throws DaoException;
	
	public abstract void grabarPermiso(Object perm) throws DaoException;
	
	public abstract void eliminarPermiso(Object perm) throws DaoException;
	
	public abstract void deletePermiso(Object perm) throws DaoException;//cambia de estado
	
	//Métodos para Horarios
	
	public abstract ArrayList listarHorarios(Object prmtBusqHorario) throws DaoException;
	
	public abstract void grabarHorario(Object per) throws DaoException;
	
	public abstract void grabarHorarioDet(Object hor) throws DaoException;
	
	public abstract ArrayList listarHorariosDet(Object prmtBusqHorarioDet) throws DaoException;
	
	public abstract void eliminarHorario(Object prmtHorario) throws DaoException;
	
	//Administracion de Menu
	public abstract void grabarMenu(Object obj) throws DaoException;
	
	public abstract void grabarDataObjects(Object obj) throws DaoException;
	
	public abstract ArrayList listarSolicitudes(Object obj) throws DaoException;
	
	public abstract void grabarSolicitud(Object obj) throws DaoException;
	
	//Registro de Pcs
	public abstract ArrayList listarRegistroPc(Object prmtRegPc) throws DaoException;
	
	public abstract void grabarRegistroPc(Object obj) throws DaoException;
	
	//Accesos Especiales
	public abstract ArrayList listarAccesosEspeciales(Object prmtAccesos) throws DaoException;

	public abstract ArrayList listarAccesosDetalle(Object prmtAccesosDet) throws DaoException;

	public abstract void grabarAccesoEspecial(Object acceso) throws DaoException;

	public abstract void grabarAccesoEspecialDet(Object prmt) throws DaoException;

	public abstract void eliminarAccesoEspecial(Object prmtAccesos) throws DaoException;

	public abstract void eliminarRegistroPc(Object prmtRegPc) throws DaoException;

	public abstract ArrayList listarMotivosAcceso(Object prmtMotivos) throws DaoException;

	public abstract ArrayList listarRegPol(Object prmtBusq) throws DaoException; 
	
	//Relación Formulario - Documentación
	public abstract void grabarFormDoc(Object o) throws DaoException;
	
	public abstract void grabarFormDemo(Object o) throws DaoException;
	
	public abstract ArrayList listarFormAdmDoc(Object prmtBusq) throws DaoException;
	
	public abstract void eliminarAdmFormDoc(Object prmtAdmFormDoc) throws DaoException;
	
	//Relación Perfil / MOF
	public abstract ArrayList listarPerfilMof(Object prmtBusq) throws DaoException;
	
	public abstract void grabarPerfilMof(Object o) throws DaoException;
	
	public abstract void eliminarPerfilMof(Object o) throws DaoException;

	public abstract void updateOrganigrama(Object o) throws DaoException;

	public abstract void grabarRegPol(Object regPol) throws DaoException;

	public abstract void eliminarRegPol(Object prmtRegPol) throws DaoException;

	public abstract ArrayList listarAccesosExtPc(Object prmtAccesosExtPc) throws DaoException;

	public abstract void grabarAccesoExtPc(Object accesoExtPc) throws DaoException;

	public abstract void eliminarSolCambio(Object prmtSolicitud) throws DaoException;

	public abstract ArrayList listarSesiones(Object prmtSesiones) throws DaoException;
	
	//Auditoría
	public abstract ArrayList listarCols(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarAplicacion(Object prmtBusq) throws DaoException;

	public abstract void eliminarSesiones(Object audisis) throws DaoException;

	public abstract ArrayList listarColumnas(Object prmtColumnas) throws DaoException;

	public abstract ArrayList listarAuditoriaTablas(Object prmtAudisis) throws DaoException;

	public abstract void grabarSocaDetalle(Object obj) throws DaoException;
	
	public abstract ArrayList listarFormReport(Object prmtBusq) throws DaoException;

	public abstract ArrayList obtenetIdTransaccion(Object menuIdTran) throws DaoException;
	
}