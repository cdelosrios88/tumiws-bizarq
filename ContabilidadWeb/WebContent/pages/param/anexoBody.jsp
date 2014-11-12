<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modulo    :                	-->
	<!-- Prototipo :  Esquema de EstadosFinancieros-->			
	<!-- Fecha     :                	-->

<rich:modalPanel id="pAlertaRegistro" width="400" height="150"
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
           		<rich:componentControl for="pAlertaRegistro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistro">
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="80">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{anexoController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje,panelBotones"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{anexoController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelTablaAnexo,panelMensaje"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistroRatio" width="400" height="150"
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
           		<rich:componentControl for="pAlertaRegistroRatio" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistroRatio">
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="80">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{ratioController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroRatio')"
             			reRender="contPanelInferiorRatio,panelMensajeRatio,panelBotonesRatio"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{ratioController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroRatio')"
             			reRender="panelTablaRatio,panelMensajeRatio"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pConfigurar" width="740" height="430"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Configurar Operaciones de Elementos"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
       		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pConfigurar" operation="hide" event="onclick"/>
           	</h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/configuracionElemento.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pConfigurarRatio" width="740" height="430"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Configurar Operaciones de Elementos"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
       		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pConfigurarRatio" operation="hide" event="onclick"/>
           	</h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/configuracionElementoRatio.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pVerCuentas" width="680" height="320"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Ver Cuentas Asociadas"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
       		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pVerCuentas" operation="hide" event="onclick"/>
           	</h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/vistaCuenta.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pSeleccionElemento" width="530" height="330"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Seleccionar Elemento"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
       		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionElemento" operation="hide" event="onclick"/>
           	</h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/seleccionElemento.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pSeleccionCuenta" width="740" height="300"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Seleccionar Cuenta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
       		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionCuenta" operation="hide" event="onclick"/>
           	</h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/seleccionCuentaRatio.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistroApertura" width="400" height="150"
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
           		<rich:componentControl for="pAlertaRegistroApertura" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form>
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="80">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{aperturaCierreController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroApertura')"
             			reRender="opPanelInferiorApertura,panelMensajeApertura"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{aperturaCierreController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroApertura')"
             			reRender="panelTablaCierre,panelMensajeApertura,opPanelInferiorApertura"
             			rendered="#{aperturaCierreController.mostrarBtnEliminar}"
        			/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pBuscarCuenta" width="740" height="285"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Selección de Cuenta de Origen"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarCuenta" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/param/popup/seleccionCuentaApertura.jsp"/>
</rich:modalPanel>


<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	<rich:tab label="Ratios y Anexos">
        <a4j:include viewId="/pages/param/tabRatio.jsp"/>
    </rich:tab>
    <rich:tab label="Estados Financieros">
       	<a4j:include viewId="/pages/param/tabEsquema.jsp"/>
    </rich:tab>
    <rich:tab label="Apertura y Cierre">
        <a4j:include viewId="/pages/param/tabAperturaCierre.jsp"/>
    </rich:tab>
</rich:tabPanel>  