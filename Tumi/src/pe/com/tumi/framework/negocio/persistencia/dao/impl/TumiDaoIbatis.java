package pe.com.tumi.framework.negocio.persistencia.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public class TumiDaoIbatis extends SqlMapClientDaoSupport
  implements TumiDao
{
  private String strNombre;

  public String getNameSpace()
  {
    return this.strNombre;
  }

  public void setNameSpace(String paramString)
  {
    this.strNombre = paramString;
  }
}