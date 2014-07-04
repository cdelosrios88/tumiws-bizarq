package pe.com.tumi.empresa.service.impl;

import java.util.List;

import pe.com.tumi.common.service.impl.GenericServiceImpl;
import pe.com.tumi.empresa.service.EmpresaService;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.seguridad.empresa.bo.SucursalBO;
import pe.com.tumi.seguridad.empresa.dao.SucursalDao;
import pe.com.tumi.seguridad.empresa.dao.impl.SucursalDaoIbatis;

public class EmpresaServiceImpl extends GenericServiceImpl implements EmpresaService{
	
}
