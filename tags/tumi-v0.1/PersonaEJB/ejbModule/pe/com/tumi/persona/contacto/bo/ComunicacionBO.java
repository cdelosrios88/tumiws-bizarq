package pe.com.tumi.persona.contacto.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.dao.ComunicacionDao;
import pe.com.tumi.persona.contacto.dao.impl.ComunicacionDaoIbatis;
import pe.com.tumi.persona.contacto.domain.Comunicacion;

import pe.com.tumi.persona.contacto.domain.ComunicacionPK;

public class ComunicacionBO {
	
	private ComunicacionDao dao = (ComunicacionDao)TumiFactory.get(ComunicacionDaoIbatis.class);
	protected static Logger log = Logger.getLogger(ComunicacionBO.class);
	
	public Comunicacion grabarComunicacion(Comunicacion o) throws BusinessException{
		Comunicacion dto = null;
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
	
	public Comunicacion modificarComunicacion(Comunicacion o) throws BusinessException{
		Comunicacion dto = null;
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
	
	public Comunicacion getComunicacionPorPK(ComunicacionPK pPK) throws BusinessException{
		Comunicacion domain = null;
		List<Comunicacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("intIdComunicacion", pPK.getIntIdComunicacion());
			lista = dao.getListaComunicacionPorPK(mapa);
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
	
	public List<Comunicacion> getListaComunicacionPorIdPersona(Integer pId) throws BusinessException{
		List<Comunicacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pId);
			lista = dao.getListaComunicacionPorIdPersona(mapa);
			String descCom = "";
			for(int i=0; i<lista.size(); i++){
				Comunicacion com = lista.get(i);
			    if(com.getIntTipoComunicacionCod()!=null && !com.getIntTipoComunicacionCod().equals(0)){
			    	if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO)){
			    		descCom = ("Tipo Línea: "+ (com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_FIJO) ? "Fijo" : 
			    			com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_CLARO) ? "Claro" : 
			    			com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_MOVISTAR) ? "Movistar" :
							com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_NEXTEL) ? "Nextel" :"") + 
							" - Nro.: " + com.getIntNumero());
			        }else if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_CORREO)){
			        	descCom = "E-mail: "+com.getStrDato();
			        }else if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_WEB)){
			        	descCom = "Website: "+com.getStrDato();
			        }
			    }
			    com.setStrComunicacion(descCom);
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
