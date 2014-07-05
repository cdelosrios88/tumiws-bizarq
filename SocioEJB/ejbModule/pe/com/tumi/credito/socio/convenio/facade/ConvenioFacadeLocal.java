package pe.com.tumi.credito.socio.convenio.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;

@Local
public interface ConvenioFacadeLocal {
	public Adenda grabarAdenda(Adenda pAdenda)throws BusinessException;
	public Adenda modificarAdenda(Adenda pAdenda)throws BusinessException;
	public Adenda getAdendaPorIdAdenda(AdendaId pk) throws BusinessException;
	//public List<Archivo> grabarListaArchivoDeAdjunto(List<Adjunto> lista, String strCartaPresentacion, String strConvenioSugerido, String strAdendaSugerida) throws BusinessException;
	public Adenda eliminarAdenda(Adenda o) throws BusinessException;
	public Adenda aprobarRechazarAdenda(Adenda o) throws BusinessException;
	public List<ConvenioEstructuraDetalleComp> getListConvenioEstructuraDetalle(AdendaId o) throws BusinessException;
	public List<Persona> getListaRepLegalPorIdPerNaturalYIdPerJuridica(Integer intIdPersona,Integer intTipoVinculo,Integer intIdEmpresaSistema) throws BusinessException;
	public Adenda grabarConvenio(Adenda pAdenda)throws BusinessException;
	public Adenda modificarConvenio(Adenda pAdenda)throws BusinessException;
	public Adenda getConvenioPorIdConvenio(AdendaId pk) throws BusinessException;
	public List<Archivo> grabarListaArchivoDeAdjunto(List<Adjunto> lista, String strCartaPresentacion, String strConvenioSugerido, String strAdendaSugerida, AdendaId Pk) throws BusinessException;
	}
