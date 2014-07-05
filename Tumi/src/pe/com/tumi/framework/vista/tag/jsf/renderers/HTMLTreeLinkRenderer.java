package pe.com.tumi.framework.vista.tag.jsf.renderers;

import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;

import pe.com.tumi.framework.util.reflection.JReflection;
import pe.com.tumi.framework.vista.tag.common.TagUtil;
import pe.com.tumi.framework.vista.tag.jsf.components.UITreeLinkOut;

public class HTMLTreeLinkRenderer extends Renderer
{
  private int strNombre;

  public void decode(FacesContext paramFacesContext, UIComponent paramUIComponent)
  {
  }

  public void encodeEnd(FacesContext paramFacesContext, UIComponent paramUIComponent)
    throws IOException
  {
    Object localObject = null;
    String str = null;
    String[] arrayOfString = (String[])null;
    HttpServletRequest localHttpServletRequest = null;
    UITreeLinkOut localUITreeLinkOut = (UITreeLinkOut)paramUIComponent;
    try
    {
      localObject = localUITreeLinkOut.getValue();
      this.strNombre = 0;
      if (localObject != null)
      {
        localHttpServletRequest = (HttpServletRequest)paramFacesContext.getExternalContext().getRequest();
        str = localUITreeLinkOut.getLinkOnClick();
        arrayOfString = TagUtil.getParametroDeOnClick(str);
        ResponseWriter localResponseWriter = paramFacesContext.getResponseWriter();
        tree(localResponseWriter, paramUIComponent, (List)localObject, arrayOfString, localHttpServletRequest.getContextPath());
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void tree(ResponseWriter paramResponseWriter, UIComponent paramUIComponent, List paramList, String[] paramArrayOfString, String paramString)
    throws IOException
  {
    Object localObject1 = null;
    Object localObject2 = null;
    String str = null;
    UITreeLinkOut localUITreeLinkOut = (UITreeLinkOut)paramUIComponent;
    paramResponseWriter.startElement("table", paramUIComponent);
    paramResponseWriter.writeAttribute("id", localUITreeLinkOut.getId() + "Tabla" + this.strNombre, null);
    paramResponseWriter.writeAttribute("cellpadding", "0", null);
    paramResponseWriter.writeAttribute("cellspacing", "0", null);
    for (int i = 0; i < paramList.size(); i++)
    {
      paramResponseWriter.startElement("tr", paramUIComponent);
      localObject1 = paramList.get(i);
      localObject2 = JReflection.getProperty(localObject1, localUITreeLinkOut.getProperty());
      if ((localObject2 != null) && (!localObject2.toString().equals("")))
      {
        this.strNombre += 1;
        paramResponseWriter.startElement("td", paramUIComponent);
        paramResponseWriter.writeAttribute("valign", "top", null);
        paramResponseWriter.startElement("img", paramUIComponent);
        paramResponseWriter.writeAttribute("id", localUITreeLinkOut.getId() + "Imagen" + this.strNombre, null);
        paramResponseWriter.writeAttribute("src", paramString + "/remoting/?parametro=treeColapsar.gif", null);
        paramResponseWriter.writeAttribute("border", "0", null);
        paramResponseWriter.endElement("img");
        paramResponseWriter.endElement("td");
        paramResponseWriter.startElement("td", paramUIComponent);
        paramResponseWriter.startElement("a", paramUIComponent);
        paramResponseWriter.writeAttribute("href", "#", "href");
        paramResponseWriter.writeAttribute("onClick", "j$.tree('" + localUITreeLinkOut.getId() + "','" + this.strNombre + "')", null);
        paramResponseWriter.writeAttribute("class", "controlLink", null);
        paramResponseWriter.write(String.valueOf(JReflection.getProperty(localObject1, localUITreeLinkOut.getLinkLabel())));
        paramResponseWriter.endElement("a");
        tree(paramResponseWriter, paramUIComponent, (List)localObject2, paramArrayOfString, paramString);
        paramResponseWriter.endElement("td");
      }
      else
      {
        paramResponseWriter.startElement("td", paramUIComponent);
        paramResponseWriter.startElement("img", paramUIComponent);
        paramResponseWriter.writeAttribute("src", paramString + "/remoting/?parametro=treeItem.gif", null);
        paramResponseWriter.writeAttribute("border", "0", null);
        paramResponseWriter.endElement("img");
        paramResponseWriter.endElement("td");
        paramResponseWriter.startElement("td", paramUIComponent);
        paramResponseWriter.startElement("a", paramUIComponent);
        paramResponseWriter.writeAttribute("href", "#", "href");
        str = localUITreeLinkOut.getLinkOnClick();
        for (int j = 0; j < paramArrayOfString.length; j++)
          str = str.replace(paramArrayOfString[j], String.valueOf(JReflection.getProperty(localObject1, paramArrayOfString[j])));
        paramResponseWriter.writeAttribute("onClick", str, null);
        if (localUITreeLinkOut.getLinkTarget() != null)
          paramResponseWriter.writeAttribute("target", localUITreeLinkOut.getLinkTarget(), null);
        paramResponseWriter.writeAttribute("class", "controlLink", null);
        paramResponseWriter.write(String.valueOf(JReflection.getProperty(localObject1, localUITreeLinkOut.getLinkLabel())));
        paramResponseWriter.endElement("a");
        paramResponseWriter.endElement("td");
      }
      paramResponseWriter.endElement("tr");
    }
    paramResponseWriter.endElement("table");
  }
}