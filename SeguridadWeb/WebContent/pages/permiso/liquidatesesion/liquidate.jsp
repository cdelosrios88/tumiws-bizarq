<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         	-->
	<!-- Autor     : Arturo Julca   			-->
	<!-- Modulo    :                			-->
	<!-- Prototipo :  Acceso Especial			-->			
	<!-- Fecha     :                			-->

<h:panelGroup style="border:1px solid #17356f; width:1000px; background-color:#DEEBF5;">
<rich:panel style="margin-left: auto; margin-right: auto;width:900px;">
<h:form id="frmPrincipal">
	<rich:tabPanel>
    	<rich:tab label="Sesión Web" actionListener="#{liquidateSessionController.mostrarSessionWeb}">
        	<a4j:include viewId="/pages/permiso/liquidatesesion/tabSessionWeb.jsp"/>
    	</rich:tab>		
  	</rich:tabPanel>
</h:form>
</rich:panel>
</h:panelGroup>