<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->		
	<!-- Fecha     : 05/10/2012               -->
	<!-- Modificado: Junior Chavez 10.12.2013 -->	


<rich:modalPanel id="pAlertaRegistroCD" width="340" height="120" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;"><h:outputText value="Alerta"/></rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAlertaRegistroCD" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    
    <h:form>
    	<h:panelGroup style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="60">
	    		</rich:column>
	    		<rich:column style="border:none">
	    			<a4j:commandButton value="Ver" styleClass="btnEstilos"
            			action="#{cierreDiarioController.verRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroCD')"
             			reRender="contPanelInferiorCD,panelMensajeCD,panelBotonesCD"/>
        			<a4j:commandButton value="Anular" styleClass="btnEstilos"
            			onclick="Richfaces.showModalPanel('pAnularCierre');Richfaces.hideModalPanel('pAlertaRegistroCD');"
             			reRender="formAnularCierre"
             			rendered="#{cierreDiarioController.poseePermisoAnular}"/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAnularCierre" width="450" height="170" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;"><h:outputText value="Anular"/></rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAnularCierre" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="formAnularCierre">
    	<h:panelGroup style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column>
	    		<h:outputText value="Esta por anular el cierre de la sucursal #{cierreDiarioController.registroSeleccionado.sucursal.juridica.strSiglas}
	    		 con fecha #{cierreDiarioController.registroSeleccionado.tsFechaCierreArqueo}"/>
	    		</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="5px"/>
	    	<h:panelGrid columns="6">
				<rich:column width="120" style="vertical-align: top"><h:outputText value="Observación :"/></rich:column>
				<rich:column><h:inputTextarea rows="2" cols="55" value="#{cierreDiarioController.registroSeleccionado.strObersvacionAnula}"/></rich:column>
			</h:panelGrid>
	    	<rich:spacer height="12px"/>
	    	<h:panelGrid columns="2">
	    		<rich:column width="130"></rich:column>
	    		<rich:column style="border:none">
	    			<a4j:commandButton value="Anular" styleClass="btnEstilos"
            			action="#{cierreDiarioController.anularCierre}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroCD')"
             			reRender="contPanelInferiorCD,panelTablaResultadosCD,panelMensajeCD"
             			oncomplete="Richfaces.hideModalPanel('pAnularCierre')"/>
        			<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
            			onclick="Richfaces.hideModalPanel('pAnularCierre')"/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>


<rich:modalPanel id="pCambioSucursal" width="500" height="180" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;"><h:outputText value="Cambio de Sucursal"/></rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pCambioSucursal" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    
    <h:form>
    	<h:panelGroup style="background-color:#DEEBF5">
	    	<h:panelGrid columns="4">
	    		<rich:column width="110">
					<h:outputText value="Sucursal :" />
				</rich:column>
				<rich:column width="150">
					<h:selectOneMenu id="cboSucursal" style="width: 140px;"
						onchange="getSubSucursal(#{applicationScope.Constante.ONCHANGE_VALUE})"
						value="#{cierreDiarioController.intCambioIdSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel"
							value="#{cierreDiarioController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}"
							itemLabel="#{sel.juridica.strSiglas}"/>
					</h:selectOneMenu>
				</rich:column>
	    		<rich:column width="120">
					<h:outputText value="Sub-Sucursal :" />
				</rich:column>
				<rich:column width="120">
					<h:selectOneMenu id="cboSubSucursal" style="width: 110px;"
			            value="#{cierreDiarioController.intCambioIdSubSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{cierreDiarioController.listJuridicaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
					</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	    			<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
            			action="#{cierreDiarioController.actualizarCierreDiario}"
             			onclick="Richfaces.hideModalPanel('pCambioSucursal')"
             			reRender="contPanelInferiorCD,panelMensajeCD,panelBotonesCD"/>
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>

<a4j:region>
	<a4j:jsFunction name="getSubSucursal" reRender="cboSubSucursal"
		action="#{cierreDiarioController.getListSubSucursal}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:outputText value="#{cierreFondosController.inicioPage}" />
<h:outputText value="#{cierreDiarioController.inicioPage}" />

<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">

		<rich:tab label="Cierre de Fondos" disabled="#{!cierreFondosController.poseePermiso}">
            <a4j:include viewId="/pages/cierre/tabCierreFondos.jsp"/>
     	</rich:tab>
     	
     	<rich:tab label="Cierre Diario y Arqueo" disabled="#{!cierreDiarioController.poseePermiso}">
        	<a4j:include viewId="/pages/cierre/tabCierreDiario.jsp"/>
    	</rich:tab>
		
		<rich:tab label="Saldos de Caja" disabled="#{!saldoController.poseePermiso}">
        	<a4j:include viewId="/pages/cierre/tabSaldo.jsp"/>
    	</rich:tab>
    	
</rich:tabPanel>