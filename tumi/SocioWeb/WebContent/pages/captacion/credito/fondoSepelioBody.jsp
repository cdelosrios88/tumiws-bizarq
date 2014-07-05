<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         					-->
	<!-- Autor     : Christian De los Ríos    					-->
	<!-- Modulo    : Créditos                 					-->
	<!-- Prototipo : CONFIGURACIÓN DE APORTES - FONDO DE SEPELIO-->
	<!-- Fecha     : 27/03/2012               					-->
	
	<script type="text/javascript">
		function jsSeleccionFondoSepelio(itemIdEmpresa, itemIdTipoCaptacion, itemIdCodigo){
			document.getElementById("frmFondoSepelioModalPanel:hiddenIdEmpresa").value=itemIdEmpresa;
			document.getElementById("frmFondoSepelioModalPanel:hiddenIdTipoCaptacion").value=itemIdTipoCaptacion;
			document.getElementById("frmFondoSepelioModalPanel:hiddenIdCodigo").value=itemIdCodigo;
		}
		
		function jsSeleccionFondoRetiro(itemIdEmpresa, itemIdTipoCaptacion, itemIdCodigo){
			document.getElementById("frmFondoRetiroModalPanel:hiddenIdEmpresa").value=itemIdEmpresa;
			document.getElementById("frmFondoRetiroModalPanel:hiddenIdTipoCaptacion").value=itemIdTipoCaptacion;
			document.getElementById("frmFondoRetiroModalPanel:hiddenIdCodigo").value=itemIdCodigo;
		}
		
		function jsSeleccionAes(itemIdEmpresa, itemIdTipoCaptacion, itemIdCodigo){
			document.getElementById("frmAesModalPanel:hiddenIdEmpresa").value=itemIdEmpresa;
			document.getElementById("frmAesModalPanel:hiddenIdTipoCaptacion").value=itemIdTipoCaptacion;
			document.getElementById("frmAesModalPanel:hiddenIdCodigo").value=itemIdCodigo;
		}
	</script>
	
	<rich:modalPanel id="panelUpdateDeleteFondoSepelio" width="400" height="155"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Aportación"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkFondoSepelio"/>
               <rich:componentControl for="panelUpdateDeleteFondoSepelio" attachTo="hidelinkFondoSepelio" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        <h:form id="frmFondoSepelioModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar un Fondo de Sepelio?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{fondoSepelioController.irModificarFondoSepelio}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{fondoSepelioController.eliminarFondoSepelio}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdEmpresa"/>
				<h:inputHidden id="hiddenIdTipoCaptacion"/>
				<h:inputHidden id="hiddenIdCodigo"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteFondoRetiro" width="400" height="155"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border:none;">
              	<h:outputText value="Actualizar/Eliminar Fondo de Retiro"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkFondoRetiro"/>
               <rich:componentControl for="panelUpdateDeleteFondoRetiro" attachTo="hidelinkFondoRetiro" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmFondoRetiroModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar un Fondo de Retiro?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{fondoRetiroController.irModificarFondoRetiro}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{fondoRetiroController.eliminarFondoRetiro}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdEmpresa"/>
				<h:inputHidden id="hiddenIdTipoCaptacion"/>
				<h:inputHidden id="hiddenIdCodigo"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteAES" width="400" height="155"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Aportación"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAes"/>
               <rich:componentControl for="panelUpdateDeleteAES" attachTo="hidelinkAes" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        <h:form id="frmAesModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar un Fondo de Sepelio?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{aesController.irModificarAes}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{aesController.eliminarAes}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdEmpresa"/>
				<h:inputHidden id="hiddenIdTipoCaptacion"/>
				<h:inputHidden id="hiddenIdCodigo"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
      	<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	       	<rich:tab label="Fondo de Sepelio" disabled="#{fondoSepelioController.blnFondoSepelio}">
	       	 	<a4j:include viewId="/pages/captacion/credito/fondoSepelio/FondoSepelioMain.jsp"/>
	    	</rich:tab>
	    	<rich:tab label="Fondo de Retiro" disabled="#{fondoSepelioController.blnFondoRetiro}">
	       	 	<a4j:include viewId="/pages/captacion/credito/fondoRetiroBody.jsp"/>
	    	</rich:tab>
	    	<rich:tab label="AES" disabled="#{fondoSepelioController.blnAes}">
	       	 	<a4j:include viewId="/pages/captacion/credito/aesBody.jsp"/>
	    	</rich:tab>
      	</rich:tabPanel>
    </h:form>