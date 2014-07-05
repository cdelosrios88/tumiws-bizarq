package pe.com.tumi.persona.empresa.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.empresa.dao.JuridicaDao;
import pe.com.tumi.persona.empresa.dao.impl.JuridicaDaoIbatis;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class JuridicaBO {
	
	protected  static Logger log = Logger.getLogger(JuridicaBO.class);
	private JuridicaDao dao = (JuridicaDao)TumiFactory.get(JuridicaDaoIbatis.class);
	
	public Juridica grabarJuridica(Juridica o) throws BusinessException{
		log.info("-----------------------Debugging JuridicaBO.grabarJuridica-----------------------------");
		Juridica dto = null;
		try{
			log.info(o);
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Juridica modificarJuridica(Juridica o) throws BusinessException{
		Juridica dto = null;
		try{
			log.info(o);
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Juridica getJuridicaPorPK(Integer pIntPK) throws BusinessException{
		Juridica domain = null;
		List<Juridica> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pIntPK);
			lista = dao.getListaJuridicaPorPK(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<Juridica> getListaJuridicaPorInPk(String pStrPK) throws BusinessException{
		List<Juridica> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("csvIdPersona", pStrPK);
			lista = dao.getListaJuridicaPorInPk(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Juridica> getListaJuridicaPorInPkLikeRazon(String pCsvIdPersona,String pStrRazonSocial) throws BusinessException{
		List<Juridica> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("csvIdPersona", pCsvIdPersona);
			mapa.put("strRazonSocial", pStrRazonSocial);
			lista = dao.getListaJuridicaPorInPkLikeRazon(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Juridica> getListaJuridicaDeEmpresa() throws BusinessException{
		List<Juridica> lista = null;
		try{
			HashMap mapa = new HashMap();
			lista = dao.getListaJuridicaDeEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Juridica> getListJuridicaBusqueda(Juridica o) throws BusinessException {
		log.info("-----------------------Debugging JuridicaBO.getJuridicaBusqueda-----------------------------");
		List<Juridica> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", o.getIntIdPersona());
			mapa.put("pStrRazonSocial", o.getStrRazonSocial());
			mapa.put("pStrNombreComercial", o.getStrNombreComercial());
			mapa.put("pStrSiglas", o.getStrSiglas());
			mapa.put("pDtFechaInscripcion", o.getDtFechaInscripcion());
			mapa.put("pIntNroTrabajadores", o.getIntNroTrabajadores());
			mapa.put("pIntTipoEmpresaCod", o.getIntTipoEmpresaCod());
			mapa.put("pIntTipoContribuyenteCod", o.getIntTipoContribuyenteCod());
			mapa.put("pIntCondContribuyente", o.getIntCondContribuyente());
			mapa.put("pIntEstadoContribuyenteCod", o.getIntEstadoContribuyenteCod());
			mapa.put("pIntEmisionComprobante", o.getIntEmisionComprobante());
			mapa.put("pIntSistemaContable", o.getIntSistemaContable());
			mapa.put("pIntComercioExterior", o.getIntComercioExterior());
			mapa.put("pDtFechaInicioAct", o.getDtFechaInicioAct());
			mapa.put("pStrFichaRegPublico", o.getStrFichaRegPublico());
			
			lista = dao.getJuridicaBusqueda(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Juridica> getListaPorRazonSocial(String strRazonSocial) throws BusinessException{
		List<Juridica> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("strRazonSocial", strRazonSocial);
			lista = dao.getListaJuridicaPorRazonSocial(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Juridica> getListaPorNombreComercial(String strNombreComercial) throws BusinessException{
		List<Juridica> lista = null;
		try{
			log.info(strNombreComercial);
			HashMap mapa = new HashMap();
			mapa.put("strNombreComercial", strNombreComercial);
			lista = dao.getListaJuridicaPorNombreComercial(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	

	public List<Juridica> listaJuridicaWithFile(String strProg,
												String strCodEst) throws BusinessException{
		List<Juridica> lista = null;
		try{
			
			HashMap mapa = new HashMap();
			mapa.put("strProg", strProg);
			mapa.put("strCodEst", strCodEst);
			lista = dao.listaJuridicaWithFile(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
