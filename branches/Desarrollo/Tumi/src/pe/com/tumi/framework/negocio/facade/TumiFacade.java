package pe.com.tumi.framework.negocio.facade;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

public class TumiFacade
{

  @Resource
  protected SessionContext context;
}