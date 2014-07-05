package pe.com.tumi.creditos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.dao.CreditosDao;
import pe.com.tumi.creditos.domain.CondSocio;
import pe.com.tumi.creditos.domain.EstructuraOrganica;
import pe.com.tumi.creditos.domain.Aportes;

public interface CreditosService {
	public abstract CreditosDao getCreditosDAO();
	
	public abstract ArrayList listarConvenio(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarEstructura(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarEstructuraDet(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarPoblacion(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarPoblacionDet(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarCompetencia(Object prmtBusq) throws DaoException;
	
	public abstract void grabarHojaPlaneamiento(Object hoja) throws DaoException;
	
	public abstract void grabarConvEstructDet(Object pob) throws DaoException;
	
	public abstract void grabarPoblacion(Object pob) throws DaoException;
	
	public abstract void grabarPoblacionDet(Object pob) throws DaoException;
	
	public abstract void grabarCompetencia(Object comp) throws DaoException;
	
	public abstract void grabarAdendaPerfil(Object hoja) throws DaoException;
	
	public abstract void eliminarPoblacion(Object prmtPerfilDet) throws DaoException;
	
	//Métodos de Control de Proceso
	
	public abstract ArrayList listarControlProceso(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarPerfilConvenio(Object prmtBusq) throws DaoException;
	
	public abstract void grabarAdendaPerfilDet(Object pob) throws DaoException;
	
	public abstract void eliminarAdendaPerfil(Object prmtPerfilDet) throws DaoException;
	
	public abstract void aprobarRechazarConvenio(Object pob) throws DaoException;
	
	//Métodos de Aportaciones
	
	public abstract ArrayList listarAportaciones(Object prmtBusq) throws DaoException;
	

	public abstract ArrayList listarCondSocio(Object prmtBusq) throws DaoException;
	
	public abstract void grabarConfCaptacion(Object aport) throws DaoException;

	public abstract void grabarCondicionSocio(Object cond) throws DaoException;
	
	public abstract void eliminarCondSocio(Object prmtCondSocio) throws DaoException;
	
	public abstract void eliminarAportacion(Object prmtAportacion) throws DaoException;
	

	//Metodos de Estructura Organica
	
	public abstract ArrayList buscarEstrucOrg(Object prmEstructura) throws DaoException;

	public abstract void grabarEstructuraOrg(Object beanInsti) throws DaoException;

	public abstract void grabarEstructuraDetalle(Object insti) throws DaoException;

	public abstract ArrayList listarEstrucDet(Object prmEstructura) throws DaoException;
	
	//Métodos de Mantenimientos
	
	public abstract List<Aportes> listarMantCuentas(Aportes aporte) throws DaoException;

	public abstract void grabarMantCuentas(Aportes aporte) throws DaoException;
	
	public abstract void grabarCondSocioMantCuentas(Aportes aporte) throws DaoException;
	public abstract void grabarCondCapAfectasMantCuentas(Aportes aporte) throws DaoException;
	public abstract void eliminarMantCuenta(Aportes aporte) throws DaoException;
	public abstract List<CondSocio> listarCondicionSocio(Map prmtBusq) throws DaoException;
} 
