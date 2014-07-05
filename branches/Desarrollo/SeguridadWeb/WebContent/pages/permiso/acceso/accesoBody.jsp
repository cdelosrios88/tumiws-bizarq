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

<rich:modalPanel id="pBuscarUsuario" width="650" height="348"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Usuario"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarUsuario" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<a4j:include viewId="/pages/permiso/acceso/popups/usuarioBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistroFH" width="400" height="160"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Alerta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAlertaRegistroFH" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistroFH">
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="1">
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{accesoController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroFH')"
             			reRender="contPanelInferiorFH,panelMensajeFH"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{accesoController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroFH')"
             			reRender="panelTablaAccesosFH,panelMensajeFH,contPanelInferiorFH"
             			rendered="#{accesoController.mostrarBtnEliminar}"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistroS" width="400" height="160"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Alerta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAlertaRegistroS" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistroS">
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="1">
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{accesoController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroS')"
             			reRender="contPanelInferiorS,panelMensajeS"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{accesoController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroS')"
             			reRender="panelTablaAccesosS,panelMensajeS,contPanelInferiorS"
             			rendered="#{accesoController.mostrarBtnEliminar}"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistroC" width="400" height="160"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Alerta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAlertaRegistroC" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistroC">
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="1">
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{accesoController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroC')"
             			reRender="contPanelInferiorC,panelMensajeC"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{accesoController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroC')"
             			reRender="panelTablaAccesosC,panelMensajeC,contPanelInferiorC"
             			rendered="#{accesoController.mostrarBtnEliminar}"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<h:panelGroup style="border:1px solid #17356f; width:1000px; background-color:#DEEBF5;">
<rich:panel style="margin-left: auto; margin-right: auto;width:900px;">
<h:form id="frmPrincipal">
	<rich:tabPanel>
    	<rich:tab label="Acceso Fuera de Hora" actionListener="#{accesoController.mostrarFueraHora}">
        	<a4j:include viewId="/pages/permiso/acceso/tabFueraHora.jsp"/>
    	</rich:tab>		
		<rich:tab label="Acceso desde otra sucursal" actionListener="#{accesoController.mostrarSucursal}">
        	<a4j:include viewId="/pages/permiso/acceso/tabSucursal.jsp"/>
	    </rich:tab>
	    <rich:tab label="Acceso por cabina" actionListener="#{accesoController.mostrarCabina}">
        	<a4j:include viewId="/pages/permiso/acceso/tabCabina.jsp"/>
	    </rich:tab>
  	</rich:tabPanel>
</h:form>
</rich:panel>
</h:panelGroup>