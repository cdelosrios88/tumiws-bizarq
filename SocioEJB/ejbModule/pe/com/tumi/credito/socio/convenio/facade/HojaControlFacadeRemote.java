package pe.com.tumi.credito.socio.convenio.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface HojaControlFacadeRemote {
	
	public List<HojaControlComp> getListaAreaEncargadaDeHojaDeControl(HojaControlComp o) throws BusinessException;
	public Perfil grabarAdendaPerfil(Perfil pPerfil)throws BusinessException;
	
}
