package pe.com.tumi.cobranza.cierremensual.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.Persona;

@Local
public interface CierreCobranzaFacadeLocal {
	public List<CierreCobranzaComp> getListaCierreCobranzaBusq(CierreCobranzaComp o) throws BusinessException;
	public CierreCobranza getCierreCobranza(CierreCobranza o) throws BusinessException;
	public CierreCobranza grabarCierreCobranza(CierreCobranza o) throws BusinessException;
	public CierreCobranza modificarCierreCobranza(CierreCobranza o) throws BusinessException;
	public List<CierreCobranzaOperacion> getListaCierreCobranzaOperacion(CierreCobranza o) throws BusinessException;
	public List<CierreCobranzaPlanilla> getListaCierrePlanillaPorPkCierreCobranza(CierreCobranzaPlanilla o) throws BusinessException;
	
	//Inicio modificaci�n cdelosrios, 27/08/2013
	public List<LibroMayor> buscarLibroMayor(LibroMayor o) throws BusinessException;
	public LibroMayor grabarLibroMayor(LibroMayor o)throws BusinessException;
	public LibroMayor modificarLibroMayor(LibroMayor o)throws BusinessException;
	public List<LibroMayorDetalle> getListaLibroMayorDetallePorLibroMayor(LibroMayor libroMayor) throws BusinessException;
	public List<LibroMayor> getLibroMayorTodos() throws BusinessException;
	//Fin modificaci�n cdelosrios, 27/08/2013
}
