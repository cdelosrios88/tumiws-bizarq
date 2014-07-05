package pe.com.tumi.framework.vista.tag.jsf.renderers;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import pe.com.tumi.framework.vista.tag.common.TagUtil;
import pe.com.tumi.framework.vista.tag.jsf.components.UITextOut;

public class HTMLTextOutRenderer extends Renderer
{
  public void decode(FacesContext paramFacesContext, UIComponent paramUIComponent)
  {
  }

  public void encodeEnd(FacesContext paramFacesContext, UIComponent paramUIComponent)
    throws IOException
  {
    Object localObject1 = null;
    Object localObject2 = null;
    UITextOut localUITextOut = (UITextOut)paramUIComponent;
    String str1 = localUITextOut.getClientId(paramFacesContext);
    localObject2 = localUITextOut.getValue();
    if (localObject2 != null)
      localObject1 = localObject2;
    else
      localObject1 = localUITextOut.getCache();
    Object localObject3 = localUITextOut.getProperty();
    String str2 = localUITextOut.getItemValue();
    String str3 = localUITextOut.getItemLabel();
    Object localObject4 = null;
    if ((localObject3 != null) && (!localObject3.toString().equalsIgnoreCase("")) && (localObject1 != null))
      localObject4 = TagUtil.buscarValorByListaCodigoProperty(localObject1, localObject3, str2, str3);
    try
    {
      ResponseWriter localResponseWriter = paramFacesContext.getResponseWriter();
      localResponseWriter.startElement("span", paramUIComponent);
      localResponseWriter.writeAttribute("id", str1, null);
      String str4 = localUITextOut.getStyle();
      if (str4 != null)
        localResponseWriter.writeAttribute("style", str4, null);
      String str5 = localUITextOut.getStyleClass();
      if (str5 != null)
        localResponseWriter.writeAttribute("class", str5, null);
      localResponseWriter.write(localObject4 == null ? "" : localObject4.toString());
      localResponseWriter.endElement("span");
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
}