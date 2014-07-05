package pe.com.tumi.framework.servicio.seguridad.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SeguridadUtil
{
  public static String strNombre(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = null;
    int i = 0;
    String str2 = null;
    String str3 = null;
    str1 = paramHttpServletRequest.getLocalAddr();
    i = paramHttpServletRequest.getLocalPort();
    str2 = paramHttpServletRequest.getParameter("path");
    if ((str1 != null) && (str2 != null))
    {
      str3 = "http://" + str1 + ":" + i + str2;
      paramHttpServletRequest.getSession().setAttribute("atrStrServerName", str1);
      paramHttpServletRequest.getSession().setAttribute("atrStrServerPort", Integer.valueOf(i));
      paramHttpServletRequest.getSession().setAttribute("atrStrContextPath", str2);
      paramHttpServletRequest.getSession().setAttribute("atrStrHostPort", strSession(paramHttpServletRequest));
    }
    return str3;
  }

  public static String strCodigo(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    str1 = String.valueOf(paramHttpServletRequest.getSession().getAttribute("atrStrServerName"));
    str2 = String.valueOf(paramHttpServletRequest.getSession().getAttribute("atrStrServerPort"));
    str3 = String.valueOf(paramHttpServletRequest.getSession().getAttribute("atrStrContextPath"));
    if ((str1 != null) && (str2 != null) && (str3 != null))
      str4 = "http://" + str1 + ":" + str2 + str3;
    return str4;
  }

  public static String strEnlace(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    str1 = paramHttpServletRequest.getServerName();
    str2 = String.valueOf(paramHttpServletRequest.getSession().getAttribute("atrStrHostPort"));
    str3 = paramHttpServletRequest.getContextPath();
    if ((str1 != null) && (str3 != null))
      if (str2 != null)
        str4 = "http://" + str1 + ":" + str2 + str3;
      else
        str4 = "http://" + str1 + str3;
    return str4;
  }

  public static String strSession(HttpServletRequest paramHttpServletRequest)
  {
    String str = null;
    String[] arrayOfString = (String[])null;
    str = paramHttpServletRequest.getHeader("Host");
    arrayOfString = str.split(":");
    if (arrayOfString.length > 1)
      str = arrayOfString[1];
    else
      str = "80";
    return str;
  }
}