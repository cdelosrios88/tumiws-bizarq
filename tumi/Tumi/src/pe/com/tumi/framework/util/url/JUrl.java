package pe.com.tumi.framework.util.url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JUrl
{
  public static URLConnection getConnection(String paramString)
  {
    URL localURL = null;
    URLConnection localURLConnection = null;
    try
    {
      localURL = new URL(paramString);
      localURLConnection = localURL.openConnection();
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localURLConnection;
  }
}