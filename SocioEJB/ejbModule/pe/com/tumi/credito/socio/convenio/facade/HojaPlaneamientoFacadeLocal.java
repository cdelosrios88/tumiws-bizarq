package pe.com.tumi.credito.socio.convenio.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.credito.socio.convenio.domain.composite.ConvenioComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface HojaPlaneamientoFacadeLocal {
	
	public List<HojaPlaneamientoComp> getListaHojaPlaneamientoCompDeBusquedaAdenda(HojaPlaneamientoComp o) throws BusinessException;
	public Adenda getAdendaPorIdAdenda(AdendaId pPK) throws BusinessException;
	public List<ConvenioComp> getListaConvenioCompDeBusqueda(ConvenioComp o) throws BusinessException;
	public List<ConvenioComp> getListaConvenioDet(ConvenioComp o) throws BusinessException;
	public List<ConvenioComp> getListaConvenioPorTipoConvenio(ConvenioComp o) throws BusinessException;
}
