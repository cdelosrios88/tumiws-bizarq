<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         				-->
	<!-- Autor     : Christian De los Ríos    				-->
	<!-- Modulo    : Créditos                 				-->
	<!-- Prototipo : CONFIGURACIÓN DE APORTES - APORTACIONES-->
	<!-- Fecha     : 06/02/2012               				-->
	
	<script type="text/javascript">
		function jsSeleccionAportacion(itemIdEmpresa, itemIdTipoCaptacion, itemIdCodigo){
			document.getElementById("frmAportacionModalPanel:hiddenIdEmpresa").value=itemIdEmpresa;
			document.getElementById("frmAportacionModalPanel:hiddenIdTipoCaptacion").value=itemIdTipoCaptacion;
			document.getElementById("frmAportacionModalPanel:hiddenIdCodigo").value=itemIdCodigo;
		}
		
		function jsSeleccionMantCuenta(itemIdEmpresa, itemIdTipoCaptacion, itemIdCodigo){
			document.getElementById("frmMantCuentaModalPanel:hiddenEmpresaId").value=itemIdEmpresa;
			document.getElementById("frmMantCuentaModalPanel:hiddenTipoCaptacionId").value=itemIdTipoCaptacion;
			document.getElementById("frmMantCuentaModalPanel:hiddenCodigoId").value=itemIdCodigo;
		}
	</script>
	
	<rich:modalPanel id="panelUpdateDeleteAportacion" width="400" height="155"
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
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAportacion"/>
               <rich:componentControl for="panelUpdateDeleteAportacion" attachTo="hidelinkAportacion" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmAportacionModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar una Aportación?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{aportacionController.irModificarAportaciones}" styleClass="btnEstilos">
                    	<a4j:support oncomplete="disableInputs()"></a4j:support>
                    </h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{aportacionController.eliminarAportacion}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdEmpresa"/>
				<h:inputHidden id="hiddenIdTipoCaptacion"/>
				<h:inputHidden id="hiddenIdCodigo"/>
             </rich:panel>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteMantCuenta" width="450" height="155"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Mantenimiento de Cuenta"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkMantCuenta"/>
               <rich:componentControl for="panelUpdateDeleteMantCuenta" attachTo="hidelinkMantCuenta" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmMantCuentaModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 410px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar un Mantenimiento de Cuenta?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{mantCuentaController.irModificarMantCta}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{mantCuentaController.eliminarMantenimientoCta}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenEmpresaId"/>
				<h:inputHidden id="hiddenTipoCaptacionId"/>
				<h:inputHidden id="hiddenCodigoId"/>
             </rich:panel>
        </h:form>
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
      <rich:tabPanel  activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
        <rich:tab label="Aportaciones" disabled="#{aportacionesController.blnAportaciones}">
         	<a4j:include viewId="/pages/captacion/credito/aportacion/AportacionMain.jsp"/>
    	</rich:tab>
    	<rich:tab label="Mantenimiento de Cuenta" disabled="#{aportacionesController.blnMantCuenta}">
         	<a4j:include viewId="/pages/captacion/credito/mantCuentaBody.jsp"/>
    	</rich:tab>
      </rich:tabPanel>
   </h:form>