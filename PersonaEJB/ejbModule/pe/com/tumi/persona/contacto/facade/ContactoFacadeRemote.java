package pe.com.tumi.persona.contacto.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;

@Remote
public interface ContactoFacadeRemote {

	public Domicilio grabarDomicilio(Domicilio o)throws BusinessException;
	public Domicilio getDomicilioPorPK(DomicilioPK pPK) throws BusinessException;
	public List<Domicilio> getListaDomicilio(Integer pId) throws BusinessException;
	public Comunicacion getComunicacionPorPK(ComunicacionPK pPK) throws BusinessException;
	public List<Comunicacion> getListaComunicacion(Integer pId) throws BusinessException;
	public Domicilio eliminarDomicilio(Domicilio o) throws BusinessException;
	public Documento getDocumentoPorIdPersonaYTipoIdentidad(Integer intIdPersona,Integer intTipoIdentidad) throws BusinessException;
}
