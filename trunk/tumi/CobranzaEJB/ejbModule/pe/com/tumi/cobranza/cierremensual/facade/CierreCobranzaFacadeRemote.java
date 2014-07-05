package pe.com.tumi.cobranza.cierremensual.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface CierreCobranzaFacadeRemote {
	public List<CierreCobranzaComp> getListaCierreCobranzaBusq(CierreCobranzaComp o) throws BusinessException;
	public CierreCobranza getCierreCobranza(CierreCobranza o) throws BusinessException;
	public CierreCobranza grabarCierreCobranza(CierreCobranza o) throws BusinessException;
	public CierreCobranza modificarCierreCobranza(CierreCobranza o) throws BusinessException;
	public List<CierreCobranzaOperacion> getListaCierreCobranzaOperacion(CierreCobranza o) throws BusinessException;
	public List<CierreCobranzaPlanilla> getListaCierrePlanillaPorPkCierreCobranza(CierreCobranzaPlanilla o) throws BusinessException;
	
	//Inicio modificación cdelosrios, 27/08/2013
	public List<LibroMayor> buscarLibroMayor(LibroMayor o) throws BusinessException;
	public LibroMayor grabarLibroMayor(LibroMayor o)throws BusinessException;
	public LibroMayor modificarLibroMayor(LibroMayor o)throws BusinessException;
	public List<LibroMayorDetalle> getListaLibroMayorDetallePorLibroMayor(LibroMayor libroMayor) throws BusinessException;
	public List<LibroMayor> getLibroMayorTodos() throws BusinessException;
	//Fin modificación cdelosrios, 27/08/2013
}