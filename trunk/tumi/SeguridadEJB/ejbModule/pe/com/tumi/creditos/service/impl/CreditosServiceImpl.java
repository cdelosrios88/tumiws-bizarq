package pe.com.tumi.creditos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.dao.CreditosDao;
import pe.com.tumi.creditos.domain.Aportes;
import pe.com.tumi.creditos.domain.CondSocio;
import pe.com.tumi.creditos.service.CreditosService;

public class CreditosServiceImpl implements CreditosService {

	private CreditosDao creditosDAO;

	public CreditosDao getCreditosDAO() {
		return creditosDAO;
	}

	public void setCreditosDAO(CreditosDao creditosDAO) {
		this.creditosDAO = creditosDAO;
	}
	
	public ArrayList buscarEstrucOrg(Object prmEstructura) throws DaoException {
		try{
			return getCreditosDAO().buscarEstrucOrg(prmEstructura);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public ArrayList listarEstrucOrg(Object prmEstructura) throws DaoException {
		try{
			return getCreditosDAO().listarEstrucOrg(prmEstructura);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarEstrucDet(Object prmEstructura) throws DaoException {
		try{
			return getCreditosDAO().listarEstrucDet(prmEstructura);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void grabarEstructuraOrg(Object insti) throws DaoException {
		try{
			getCreditosDAO().grabarEstructuraOrg(insti);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void grabarEstructuraDetalle(Object estruc) throws DaoException {
		try{
			getCreditosDAO().grabarEstructuraDetalle(estruc);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarConvenio(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getCreditosDAO().listarConvenio(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarEstructura(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getCreditosDAO().listarEstructura(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarEstructuraDet(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getCreditosDAO().listarEstructuraDet(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPoblacion(Object prmtBusq) throws DaoException {
		try{
			return getCreditosDAO().listarPoblacion(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPoblacionDet(Object prmtBusq) throws DaoException {
		try{
			return getCreditosDAO().listarPoblacionDet(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarCompetencia(Object prmtBusq) throws DaoException {
		try{
			return getCreditosDAO().listarCompetencia(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarResumenPoblacion(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getCreditosDAO().listarResumenPoblacion(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarHojaPlaneamiento(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarHojaPlaneamiento(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarConvEstructDet(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarConvEstructDet(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarPoblacion(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarPoblacion(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarPoblacionDet(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarPoblacionDet(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarCompetencia(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarCompetencia(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarAdendaPerfil(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarAdendaPerfil(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void eliminarPoblacion(Object prmtPob) throws DaoException {
		try{
			getCreditosDAO().eliminarPoblacion(prmtPob);
		}catch(Exception e) {
			throw new DaoException(e);
		}	
	}
	
	//Métodos de Perfil Convenio
	public ArrayList listarControlProceso(Object prmtBusqEmpresa) throws DaoException {
		try{
			return getCreditosDAO().listarControlProceso(prmtBusqEmpresa);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarPerfilConvenio(Object prmtBusq) throws DaoException {
		try{
			return getCreditosDAO().listarPerfilConvenio(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarAdendaPerfilDet(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarAdendaPerfilDet(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void eliminarAdendaPerfil(Object prmtPob) throws DaoException {
		try{
			getCreditosDAO().eliminarAdendaPerfil(prmtPob);
		}catch(Exception e) {
			throw new DaoException(e);
		}	
	}
	
	public void aprobarRechazarConvenio(Object o) throws DaoException {
		try{
			getCreditosDAO().aprobarRechazarConvenio(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	//Métodos de Aportaciones
	public ArrayList listarAportaciones(Object prmtBusq) throws DaoException {
		try{
			return getCreditosDAO().listarAportaciones(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarCondSocio(Object prmtBusq) throws DaoException {
		try{
			return getCreditosDAO().listarCondSocio(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarConfCaptacion(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarConfCaptacion(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarCondicionSocio(Object o) throws DaoException {
		try{
			getCreditosDAO().grabarCondicionSocio(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void eliminarCondSocio(Object prmtCondSoc) throws DaoException {
		try{
			getCreditosDAO().eliminarCondSocio(prmtCondSoc);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void eliminarAportacion(Object prmtPob) throws DaoException {
		try{
			getCreditosDAO().eliminarAportacion(prmtPob);
		}catch(Exception e) {
			throw new DaoException(e);
		}	
	}
	
	//Métodos de Mantenimiento de Cuentas
	public List<Aportes> listarMantCuentas(Aportes aportes) throws DaoException {
		try{
			return getCreditosDAO().listarMantCuentas(aportes);			
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarMantCuentas(Aportes a) throws DaoException{
		try{
			getCreditosDAO().grabarMantCuentas(a);
		}catch(Exception e) {
			throw new DaoException(e);
		}		
	}
	
	public void grabarCondSocioMantCuentas(Aportes aporte) throws DaoException{
		try{
			getCreditosDAO().grabarCondSocioMantCuentas(aporte);
		}catch(Exception e) {
			throw new DaoException(e);
		}		
	}	
	public void grabarCondCapAfectasMantCuentas(Aportes aporte) throws DaoException{
		try{
			getCreditosDAO().grabarCondCapAfectasMantCuentas(aporte);
		}catch(Exception e) {
			throw new DaoException(e);
		}		
	}	
	public void eliminarMantCuenta(Aportes a) throws DaoException{
		try{
			getCreditosDAO().eliminarMantCuenta(a);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}	
	public List<CondSocio> listarCondicionSocio(Map prmtBusq) throws DaoException {
		try{
			return getCreditosDAO().listarCondicionSocio(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	
}