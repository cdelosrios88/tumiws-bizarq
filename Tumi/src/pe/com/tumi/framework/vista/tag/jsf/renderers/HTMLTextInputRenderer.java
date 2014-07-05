package pe.com.tumi.framework.vista.tag.jsf.renderers;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import pe.com.tumi.framework.vista.tag.common.TagUtil;
import pe.com.tumi.framework.vista.tag.jsf.components.UITextInput;

public class HTMLTextInputRenderer extends Renderer
{
  public void decode(FacesContext paramFacesContext, UIComponent paramUIComponent)
  {
  }

  public void encodeEnd(FacesContext paramFacesContext, UIComponent paramUIComponent)
    throws IOException
  {
    Object localObject1 = null;
    Object localObject2 = null;
    UITextInput localUITextInput = (UITextInput)paramUIComponent;
    String str1 = localUITextInput.getClientId(paramFacesContext);
    localObject2 = localUITextInput.getValue();
    if (localObject2 != null)
      localObject1 = localObject2;
    else
      localObject1 = localUITextInput.getCache();
    String str2 = (String)localUITextInput.getAttributes().get("size");
    String str3 = (String)localUITextInput.getAttributes().get("disabled");
    String str4 = (String)localUITextInput.getAttributes().get("readonly");
    Object localObject3 = localUITextInput.getProperty();
    String str5 = localUITextInput.getItemValue();
    String str6 = localUITextInput.getItemLabel();
    Object localObject4 = null;
    if ((localObject3 != null) && (!localObject3.toString().equalsIgnoreCase("")) && (localObject1 != null))
      localObject4 = TagUtil.buscarValorByListaCodigoProperty(localObject1, localObject3, str5, str6);
    try
    {
      ResponseWriter localResponseWriter = paramFacesContext.getResponseWriter();
      localResponseWriter.startElement("input", paramUIComponent);
      localResponseWriter.writeAttribute("id", str1, null);
      localResponseWriter.writeAttribute("type", "text", null);
      localResponseWriter.writeAttribute("value", localObject4 == null ? "" : localObject4.toString(), null);
      if (str2 != null)
        localResponseWriter.writeAttribute("size", str2, null);
      if ((str3 != null) && (str3.equals("true")))
        localResponseWriter.writeAttribute("disabled", "disabled", null);
      if ((str4 != null) && (str4.equals("true")))
        localResponseWriter.writeAttribute("readonly", "readonly", null);
      String str7 = localUITextInput.getStyle();
      if (str7 != null)
        localResponseWriter.writeAttribute("style", str7, null);
      String str8 = localUITextInput.getStyleClass();
      if (str8 != null)
        localResponseWriter.writeAttribute("class", str8, null);
      localResponseWriter.endElement("input");
    }
    catch (IOException localIOException)
    {
      System.out.println("Error : HTMLTextInputRenderer");
      localIOException.printStackTrace();
    }
  }
}