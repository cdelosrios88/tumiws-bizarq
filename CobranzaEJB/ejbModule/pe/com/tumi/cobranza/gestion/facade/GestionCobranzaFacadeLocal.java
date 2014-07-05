package pe.com.tumi.cobranza.gestion.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.Persona;

@Local
public interface GestionCobranzaFacadeLocal {
	 public List<GestionCobranza> getListaGestionCobranza(GestionCobranza o) throws BusinessException;
     public GestionCobranza getGestionCobranza(GestionCobranza o) throws BusinessException;
     public GestionCobranza grabarGestionCobranza(GestionCobranza o) throws BusinessException ;
     public GestionCobranza modificarGestionCobranza(GestionCobranza o) throws BusinessException ;
     public GestorCobranza existeGestorCobranza(GestorCobranzaId id) throws BusinessException;
     public GestionCobranza elimnarGestionCobranza(GestionCobranza o) throws BusinessException ;
     public List<GestionCobranzaEnt> getListaGestionCobranzaEnt(GestionCobranzaEnt o) throws BusinessException ;
     public void eliminarListaGestionCobranzaEnt(GestionCobranzaEnt o) throws BusinessException ;
     public List<GestionCobranzaSoc>  getListaGestionCobranzaSoc(GestionCobranzaSoc o) throws BusinessException ;
     public void eliminarGestionCobranzaSoc(GestionCobranzaSoc o) throws BusinessException;
     public GestionCobranzaCierre grabarGestionCobranzaCierre(GestionCobranzaCierre o) throws BusinessException;
     public Date obtUltimaFechaGestion(GestionCobranzaCierre o) throws BusinessException;
     public GestorCobranza getGestorCobranzaPorPersona(Persona persona, Integer intIdEmpresa) throws BusinessException;
     //AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
  	 public List<GestionCobranzaSoc> getListaPorCuentaPkYPeriodo(CuentaId pId, String strFechaGestion) throws BusinessException;
}
