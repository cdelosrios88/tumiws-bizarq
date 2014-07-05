package pe.com.tumi.framework.util.fecha;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pe.com.tumi.framework.util.fecha.common.FechaConstant;

public class JFecha
{
  public static Object formatearFecha(Object paramObject)
  {
    String str = null;
    if (paramObject != null)
      str = FechaConstant.strEnlace.format(paramObject);
    return str;
  }

  public static Object formatearFechaHora(Object paramObject)
  {
    String str = null;
    if (paramObject != null)
      str = FechaConstant.strCodigo.format(paramObject);
    return str;
  }

  public static Object formatearFechaHoraMeridiano(Object paramObject)
  {
    String str = null;
    if (paramObject != null)
      str = FechaConstant.strNombre.format(paramObject);
    return str;
  }

  public static java.sql.Date convertirUtildateASqldate(java.util.Date paramDate)
  {
    java.sql.Date localDate = null;
    try
    {
      if (paramDate != null)
        localDate = new java.sql.Date(paramDate.getTime());
    }
    catch (Exception localException)
    {
      System.out.println("Error de Conversion de Fecha");
    }
    return localDate;
  }

  public static Timestamp convertirUtildateASqlTimestamp(java.util.Date paramDate)
  {
    Timestamp localTimestamp = null;
    try
    {
      if (paramDate != null)
        localTimestamp = new Timestamp(paramDate.getTime());
    }
    catch (Exception localException)
    {
      System.out.println("Error de Conversion de Fecha");
    }
    return localTimestamp;
  }

  public static Timestamp convertirStringASqlTimestamp(String paramString)
  {
    java.util.Date localDate = null;
    Timestamp localTimestamp = null;
    try
    {
      localDate = convertirStringAUtilDate(paramString);
      localTimestamp = convertirUtildateASqlTimestamp(localDate);
    }
    catch (Exception localException)
    {
      System.out.println("Error de Conversion de Fecha");
    }
    return localTimestamp;
  }

  public static java.util.Date convertirStringAUtilDate(String paramString)
  {
    Object localObject = null;
    java.util.Date localDate = null;
    try
    {
      localDate = FechaConstant.strNombre.parse(paramString, new ParsePosition(0));
      if (localDate == null)
      {
        localDate = FechaConstant.strCodigo.parse(paramString, new ParsePosition(0));
        if (localDate == null)
          localObject = FechaConstant.strEnlace.parse(paramString, new ParsePosition(0));
        else
          localObject = localDate;
      }
      else
      {
        localObject = localDate;
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error de Conversion de Fecha");
    }
    return (java.util.Date)localObject;
  }

  public static String obtenerAnioActual()
  {
    Calendar localCalendar = Calendar.getInstance();
    return Integer.toString(localCalendar.get(1));
  }

  public static String obtenerMesActual()
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("00");
    Calendar localCalendar = Calendar.getInstance();
    return localDecimalFormat.format(localCalendar.get(2) + 1);
  }

  public static String obtenerDiaActual()
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("00");
    Calendar localCalendar = Calendar.getInstance();
    return localDecimalFormat.format(localCalendar.get(5));
  }

  public static String obtenerHoraActual()
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("00");
    Calendar localCalendar = Calendar.getInstance();
    return localDecimalFormat.format(localCalendar.get(11));
  }

  public static java.util.Date obtenerUtilDateDeFechayHoraMerdianoActual()
  {
    String str = "/";
    return convertirStringAUtilDate(obtenerStringDeFechayHoraMerdianoActualPorSeparadorFecha(str));
  }

  public static Timestamp obtenerTimestampDeFechayHoraActual()
  {
    String str1 = "/";
    String str2 = obtenerStringDeFechayHoraPorSeparadorFecha(str1);
    return convertirStringASqlTimestamp(str2);
  }

  public static String obtenerStringDeFechayHoraPorSeparadorFecha(String paramString)
  {
    DecimalFormat localDecimalFormat = null;
    StringBuffer localStringBuffer = new StringBuffer("");
    Calendar localCalendar = Calendar.getInstance();
    localDecimalFormat = new DecimalFormat("00");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(5))).append(paramString);
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(2) + 1)).append(paramString);
    localStringBuffer.append(Integer.toString(localCalendar.get(1))).append(" ");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(11))).append(":");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(12))).append(":");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(13)));
    return localStringBuffer.toString();
  }

  public static String obtenerStringDeFechayHoraMerdianoActualPorSeparadorFecha(String paramString)
  {
    DecimalFormat localDecimalFormat = null;
    StringBuffer localStringBuffer = new StringBuffer("");
    Calendar localCalendar = Calendar.getInstance();
    localDecimalFormat = new DecimalFormat("00");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(5))).append(paramString);
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(2) + 1)).append(paramString);
    localStringBuffer.append(Integer.toString(localCalendar.get(1))).append(" ");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(11))).append(":");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(12))).append(":");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(13))).append(" ");
    localStringBuffer.append(localCalendar.get(9) == 0 ? "AM" : "PM");
    return localStringBuffer.toString();
  }

  public static String obtenerStringDeHoraMerdianoActual()
  {
    DecimalFormat localDecimalFormat = null;
    StringBuffer localStringBuffer = new StringBuffer("");
    Calendar localCalendar = Calendar.getInstance();
    localDecimalFormat = new DecimalFormat("00");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(11))).append(":");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(12))).append(":");
    localStringBuffer.append(localDecimalFormat.format(localCalendar.get(13))).append(" ");
    localStringBuffer.append(localCalendar.get(9) == 0 ? "AM" : "PM");
    return localStringBuffer.toString();
  }

  public static String obtenerStringDeMerdianoActual()
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    Calendar localCalendar = Calendar.getInstance();
    localStringBuffer.append(localCalendar.get(9) == 0 ? "AM" : "PM");
    return localStringBuffer.toString();
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      System.out.println(obtenerTimestampDeFechayHoraActual());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}