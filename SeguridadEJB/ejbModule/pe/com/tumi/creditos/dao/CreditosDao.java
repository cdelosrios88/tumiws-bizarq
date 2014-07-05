package pe.com.tumi.creditos.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.domain.Aportes;
import pe.com.tumi.creditos.domain.CondSocio;

public interface CreditosDao {
	
	public abstract ArrayList listarConvenio(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarEstructura(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarEstructuraDet(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarResumenPoblacion(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarPoblacion(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarPoblacionDet(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarCompetencia(Object prmtBusq) throws DaoException;
	
	public abstract void grabarHojaPlaneamiento(Object o) throws DaoException, ParseException;
	
	public abstract void grabarConvEstructDet(Object o) throws DaoException, ParseException;
	
	public abstract void grabarPoblacion(Object o) throws DaoException, ParseException;
	
	public abstract void grabarPoblacionDet(Object o) throws DaoException, ParseException;
	
	public abstract void grabarCompetencia(Object o) throws DaoException, ParseException;
	
	public abstract void grabarAdendaPerfil(Object o) throws DaoException;
	
	public abstract void eliminarPoblacion(Object prmtPob) throws DaoException;
	
	//Metodos de Estructura Organica
	public abstract ArrayList buscarEstrucOrg(Object prmEstructura) throws DaoException;

	public abstract ArrayList listarEstrucOrg(Object prmEstructura) throws DaoException;

	public abstract ArrayList listarEstrucDet(Object prmEstructura) throws DaoException;

	public abstract void grabarEstructuraOrg(Object insti) throws DaoException;

	public abstract void grabarEstructuraDetalle(Object estruc) throws DaoException;
	
	//Métodos de Perfil Convenio
	
	public abstract ArrayList listarControlProceso(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarPerfilConvenio(Object prmtBusq) throws DaoException;
	
	public abstract void grabarAdendaPerfilDet(Object o) throws DaoException;
	
	public abstract void eliminarAdendaPerfil(Object prmtPob) throws DaoException;
	
	public abstract void aprobarRechazarConvenio(Object o) throws DaoException;
	
	//Métodos de Aportaciones
	
	public abstract ArrayList listarAportaciones(Object prmtBusq) throws DaoException;
	
	public abstract ArrayList listarCondSocio(Object prmtBusq) throws DaoException;
	
	public abstract void grabarConfCaptacion(Object o) throws DaoException, ParseException;
	
	public abstract void grabarCondicionSocio(Object o) throws DaoException;
	
	public abstract void eliminarCondSocio(Object prmtCondSoc) throws DaoException;
	
	public abstract void eliminarAportacion(Object prmtPob) throws DaoException;
	
	//Métodos de Mantenimientos de Cuentas	
	public abstract List<Aportes> listarMantCuentas(Aportes aportes) throws DaoException;
	
	public abstract void grabarMantCuentas(Aportes a) throws DaoException;
	
	public abstract void grabarCondSocioMantCuentas(Aportes a) throws DaoException;
	public abstract void grabarCondCapAfectasMantCuentas(Aportes aporte) throws DaoException;
	public abstract void eliminarMantCuenta(Aportes a) throws DaoException;
	public abstract List<CondSocio> listarCondicionSocio(Map prmtBusq) throws DaoException;
}
