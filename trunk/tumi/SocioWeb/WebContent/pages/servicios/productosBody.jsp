<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
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
	</script>
	
	<rich:modalPanel id="panelUpdateDeleteCredito" width="400" height="155"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Eliminar Crédito"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAportacion"/>
               <rich:componentControl for="panelUpdateDeleteCredito" attachTo="hidelinkAportacion" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        <h:form id="frmCreditoModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Modificar o Eliminar un Crédito?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0" style="width:200px;">
                    <h:commandButton value="Modificar" actionListener="#{creditoController.irModificarCredito}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{creditoController.eliminarCredito}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdEmpresa"/>
				<h:inputHidden id="hiddenIdTipoCredito"/>
				<h:inputHidden id="hiddenIdCodigo"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="pAgregarGarantiaPersonal" width="850" height="450"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column>
	              	<h:outputText value="Garantía Preferida Personal"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelGarantia" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pAgregarGarantiaPersonal" attachTo="hidePanelGarantia" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/garantiaPersonalBody.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="pAgregarGarantiaReal" width="900" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column>
	              	<h:outputText value="Garantía Preferida Real"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelGarantiaReal" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pAgregarGarantiaReal" attachTo="hidePanelGarantiaReal" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/garantiaRealBody.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="pAgregarGarantiaAutoliquidable" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column>
	              	<h:outputText value="Garantía Preferida Autoliquidable"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelGarantiaAutoliquidable" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pAgregarGarantiaAutoliquidable" attachTo="hidePanelGarantiaAutoliquidable" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/garantiaRealAutoliquidableBody.jsp"/>
    </rich:modalPanel>
	
	<rich:modalPanel id="pAgregarGarantiaRapidaRealizacion" width="900" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column>
	              	<h:outputText value="Garantía Preferida Rapida Realización"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelGarantiaRapidaRealiz" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pAgregarGarantiaRapidaRealizacion" attachTo="hidePanelGarantiaRapidaRealiz" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/garantiaRapidaRealizacionBody.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="pAgregarDescuentos" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column>
	              	<h:outputText value="DESCUENTOS DE CREDITOS"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelDsctoCredito" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pAgregarDescuentos" attachTo="hidePanelDsctoCredito" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/descuentoCreditoBody.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="pAgregarExcepciones" width="850" height="450"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column>
	              	<h:outputText value="EXCEPCIONES DEL CRÉDITO"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelExcepcion" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pAgregarExcepciones" attachTo="hidePanelExcepcion" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/excepcionBody.jsp"/>
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
      <rich:tabPanel  activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
        <rich:tab label="Cuenta Corriente">
         	
    	</rich:tab>
    	<rich:tab label="Creditos">
         	<a4j:include viewId="/pages/credito/creditoBody.jsp"/>
    	</rich:tab>
    	<rich:tab label="Ahorro">
         	
    	</rich:tab>
      </rich:tabPanel>
   </h:form>