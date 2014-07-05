package pe.com.tumi.credito.socio.aperturaCuenta.core.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface CuentaFacadeLocal {
	public Cuenta grabarCuenta(Cuenta o, Integer intIdPersona) throws BusinessException;
	public Cuenta modificarCuenta(Cuenta o, Integer intIdPersona) throws BusinessException;
	public CuentaIntegrante getCuentaIntegrantePorPkSocio(CuentaIntegrante o) throws BusinessException;
	public CuentaIntegrante getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(SocioPK pPK,Integer intParaTipoCuenta,Integer intParaTipoIntegrante) throws BusinessException;
	public String getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(Integer intEmpresa,Integer intTipoIntegrante,String strINCuenta) throws BusinessException;
	public Cuenta getCuentaPorIdCuenta(Cuenta o) throws BusinessException;
	public List<CuentaIntegrante> getCuentaIntegrantePorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public Cuenta getCuentaPorId(CuentaId cuentaId) throws BusinessException;
	public Cuenta getCuentaActualPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPKCuenta(CuentaId pPK) throws BusinessException;
	public Cuenta getCuentaPorPkYSituacion(Cuenta cuenta) throws BusinessException;
	public Cuenta modificarCuenta(Cuenta o) throws BusinessException;


	/**
	 * Recupera List<CuentaIntegrante> en base a intPersEmpresaPk y intPersonaIntegrante.
	 * Y cuyo estado de cuenta sean 1 o 3.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<CuentaIntegrante> getCuentaIntegrantePorPkPersona(CuentaIntegranteId o) throws BusinessException;
	public List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(CuentaId o) throws BusinessException;
	//public CuentaIntegrante getCuentaIntegrantePorPKSocioYTipoCuenta(SocioPK pPK,Integer intParaTipoCuenta) throws BusinessException;
	public Cuenta getListaCuentaPorPkTodoEstado(CuentaId cuentaId) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	public List<CuentaIntegrante> getLstPorSocioPKTipoCtaSituacionCta(SocioPK pPK,Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod) throws BusinessException;
	//AUTOR Y FECHA CREACION: FYALICO / 05.12.2013
	public Cuenta grabandoCuenta(Cuenta o) throws BusinessException;
	public CuentaIntegrante grabandoCuentaIntegrante(CuentaIntegrante o) throws BusinessException;
	
	 public CuentaIntegrante getCodPersonaOfCobranza(Integer empresa,
													 Integer cuenta) throws BusinessException;
	
}
