package pe.com.tumi.framework.vista.tag.jsf.renderers;

import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import pe.com.tumi.framework.vista.tag.jsf.components.UISelectOneRadio;

public class HTMLSelectOneRadioRenderer extends Renderer
{
  public void decode(FacesContext paramFacesContext, UIComponent paramUIComponent)
  {
    if ((paramFacesContext == null) || (paramUIComponent == null))
      throw new NullPointerException();
    UISelectOneRadio localUISelectOneRadio = null;
    if ((paramUIComponent instanceof UISelectOneRadio))
      localUISelectOneRadio = (UISelectOneRadio)paramUIComponent;
    else
      return;
    Map localMap = paramFacesContext.getExternalContext().getRequestParameterMap();
    String str1 = strNombre(localUISelectOneRadio, paramFacesContext);
    if (localMap.containsKey(str1))
    {
      String str2 = (String)localMap.get(str1);
      if (str2 != null)
        setSubmittedValue(paramUIComponent, str2);
    }
  }

  public void encodeBegin(FacesContext paramFacesContext, UIComponent paramUIComponent)
    throws IOException
  {
    if ((paramFacesContext == null) || (paramUIComponent == null))
      throw new NullPointerException();
  }

  public void encodeChildren(FacesContext paramFacesContext, UIComponent paramUIComponent)
    throws IOException
  {
    if ((paramFacesContext == null) || (paramUIComponent == null))
      throw new NullPointerException();
  }

  public void encodeEnd(FacesContext paramFacesContext, UIComponent paramUIComponent)
    throws IOException
  {
    if ((paramFacesContext == null) || (paramUIComponent == null))
      throw new NullPointerException();
    UISelectOneRadio localUISelectOneRadio = (UISelectOneRadio)paramUIComponent;
    if (paramUIComponent.isRendered())
    {
      ResponseWriter localResponseWriter = paramFacesContext.getResponseWriter();
      localResponseWriter.write("<input type=\"radio\"");
      localResponseWriter.write(" id=\"" + paramUIComponent.getClientId(paramFacesContext) + "\"");
      localResponseWriter.write(" name=\"" + strNombre(localUISelectOneRadio, paramFacesContext) + "\"");
      if ((localUISelectOneRadio.getStyleClass() != null) && (localUISelectOneRadio.getStyleClass().trim().length() > 0))
        localResponseWriter.write(" class=\"" + localUISelectOneRadio.getStyleClass().trim() + "\"");
      if ((localUISelectOneRadio.getStyle() != null) && (localUISelectOneRadio.getStyle().trim().length() > 0))
        localResponseWriter.write(" style=\"" + localUISelectOneRadio.getStyle().trim() + "\"");
      if ((localUISelectOneRadio.getDisabled() != null) && (localUISelectOneRadio.getDisabled().trim().length() > 0) && (localUISelectOneRadio.getDisabled().trim().equals("true")))
        localResponseWriter.write(" disabled=\"disabled\"");
      if (localUISelectOneRadio.getItemValue() != null)
        localResponseWriter.write(" value=\"" + localUISelectOneRadio.getItemValue().trim() + "\"");
      if ((localUISelectOneRadio.getOnClick() != null) && (localUISelectOneRadio.getOnClick().trim().length() > 0))
        localResponseWriter.write(" onclick=\"" + localUISelectOneRadio.getOnClick().trim() + "\"");
      if ((localUISelectOneRadio.getOnMouseOver() != null) && (localUISelectOneRadio.getOnMouseOver().trim().length() > 0))
        localResponseWriter.write(" onmouseover=\"" + localUISelectOneRadio.getOnMouseOver().trim() + "\"");
      if ((localUISelectOneRadio.getOnMouseOut() != null) && (localUISelectOneRadio.getOnMouseOut().trim().length() > 0))
        localResponseWriter.write(" onmouseout=\"" + localUISelectOneRadio.getOnMouseOut().trim() + "\"");
      if ((localUISelectOneRadio.getOnFocus() != null) && (localUISelectOneRadio.getOnFocus().trim().length() > 0))
        localResponseWriter.write(" onfocus=\"" + localUISelectOneRadio.getOnFocus().trim() + "\"");
      if ((localUISelectOneRadio.getOnBlur() != null) && (localUISelectOneRadio.getOnBlur().trim().length() > 0))
        localResponseWriter.write(" onblur=\"" + localUISelectOneRadio.getOnBlur().trim() + "\"");
      if ((localUISelectOneRadio.getValue() != null) && (localUISelectOneRadio.getItemValue() != null) && (localUISelectOneRadio.getValue().toString().equals(localUISelectOneRadio.getItemValue().toString())))
        localResponseWriter.write(" checked=\"checked\"");
      localResponseWriter.write(">");
      if (localUISelectOneRadio.getItemLabel() != null)
        localResponseWriter.write(localUISelectOneRadio.getItemLabel());
      localResponseWriter.write("</input>");
    }
  }

  public void setSubmittedValue(UIComponent paramUIComponent, Object paramObject)
  {
    if ((paramUIComponent instanceof UIInput))
      ((UIInput)paramUIComponent).setSubmittedValue(paramObject);
  }

  private String strNombre(UISelectOneRadio paramUISelectOneRadio, FacesContext paramFacesContext)
  {
    if ((paramUISelectOneRadio.getOverrideName() != null) && (paramUISelectOneRadio.getOverrideName().equals("true")))
      return paramUISelectOneRadio.getName();
    String str1 = paramUISelectOneRadio.getClientId(paramFacesContext);
    int i = str1.lastIndexOf(":");
    String str2 = "";
    if (i != -1)
    {
      str2 = str1.substring(0, i + 1);
      if (paramUISelectOneRadio.getName() == null)
        str2 = str2 + "generatedRad";
      else
        str2 = str2 + paramUISelectOneRadio.getName();
    }
    return str2;
  }
}