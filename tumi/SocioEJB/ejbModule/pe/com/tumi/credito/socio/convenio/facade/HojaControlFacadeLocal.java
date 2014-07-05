package pe.com.tumi.credito.socio.convenio.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface HojaControlFacadeLocal {
	
	public List<HojaControlComp> getListaAreaEncargadaDeHojaDeControl(HojaControlComp o) throws BusinessException;
	public Perfil grabarAdendaPerfil(Perfil pPerfil)throws BusinessException;
	public Perfil getPerfilPorPKPerfil(Perfil o) throws BusinessException;
	
}
