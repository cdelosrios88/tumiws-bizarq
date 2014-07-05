package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estructura.dao.EstructuraDao;
import pe.com.tumi.credito.socio.estructura.dao.TercerosDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.EstructuraDaoIbatis;
import pe.com.tumi.credito.socio.estructura.dao.impl.TercerosDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.service.EstructuraService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class TercerosBO {
	
	protected  static Logger log = Logger.getLogger(TercerosBO.class);
	private TercerosDao dao = (TercerosDao)TumiFactory.get(TercerosDaoIbatis.class);
	
	public List<Terceros> getListaFilaTercerosPorDNI(String strDocIdentidad)throws BusinessException {
		List<Terceros> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pDocIdentidad", strDocIdentidad);
			lista = dao.getListaFilaTercerosPorDNI(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Terceros> getListaColumnaTercerosPorDNI(String strDocIdentidad)throws BusinessException {
		List<Terceros> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pDocIdentidad", strDocIdentidad);
			lista = dao.getListaColumnaTercerosPorDNI(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Terceros getPorItemDNI(Terceros o)throws BusinessException {
		Terceros tercer = null;
		List<Terceros> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pDocIdentidad", o.getStrLibeje());
			mapa.put("pItemPadron", o.getId().getIntItemAdministraPadron());
			lista = dao.getPorItemDNI(mapa);	
			
			if(lista != null)
			{
				if(lista.size() == 1)
				{
					tercer = lista.get(0);
					
				}
				else if(lista.size()==0)
				{
					tercer = null;
				}
				else
				{
					tercer = lista.get(0);
				}
			}
			else
			{
				tercer = null;
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return tercer;
	}
	
}
