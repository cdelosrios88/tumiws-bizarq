<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Seguridad : Cooperativa Tumi         -->
	<!-- Autor     : Fredy Ramirez Calderon	  -->
	<!-- Modulo    : Cobranza         -->
	<!-- Prototipo : Gestion Movimiento de Cuenta Corriente  -->
	<!-- Fecha     : 11/12/2012               -->

<script language="JavaScript" src="/js/main.js"  type="text/javascript"></script>

<script>
	function sumarMontoAbono(monto)
	{ 
	
		alert('ds'+document.getElementById('formTransferencia:idMontoTransferencia'));
			
	    
		    
		  
	}
</script>
<rich:modalPanel id="mdpOpcionCuentaCte" width="400" height="140"
		resizeable="false" style="background-color: #DEEBF5;">	 	
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Atender/Ver Movimiento de Cuenta Corriente" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
				<rich:componentControl for="mdpOpcionCuentaCte" operation="hide" event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<h:form id="frmOpcionCuentaCte">
		<rich:panel style="background-color: #DEEBF5; margin: 0 auto;">
			<h:panelGrid columns="1">
				<rich:column style="border:none">
					<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?" />
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="12px" />
			<h:panelGrid columns="2" style="margin: 0 auto;">
				<rich:column style="border:none" id="colBtnAtender" visible="#{!cuentaCteController.btnAtenderDisabled}">
					<a4j:commandButton value="Atender" actionListener="#{cuentaCteController.obtener}" 
							styleClass="btnEstilos" 
							reRender="divFormCuentaCte,btnGrabar" 
							disabled="#{cuentaCteController.btnAtenderDisabled}" 
							oncomplete="Richfaces.hideModalPanel('mdpOpcionCuentaCte')" />
				</rich:column>
				<rich:column style="border:none" id="colBtnVer" visible="#{!cuentaCteController.btnVerDisabled}">
	             	<a4j:commandButton value="Ver" actionListener="#{cuentaCteController.ver}" 
	             			styleClass="btnEstilos"
							disabled="#{cuentaCteController.btnVerDisabled}"
							reRender="divFormCuentaCte,btnGrabar" 
							oncomplete="Richfaces.hideModalPanel('mdpOpcionCuentaCte')" />
				</rich:column>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</rich:modalPanel>


<rich:modalPanel id="mpLicencia" width="900" height="360"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Licencia" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkLicencia"/>
               <rich:componentControl for="mpLicencia" attachTo="hidelinkLicencia" operation="hide" event="onclick"/>
        </h:panelGroup>
        </f:facet>
        <h:form id = "formLicencia">
          <a4j:include viewId="/pages/cuentacte/movimiento/popup/tipomovimiento/licencia.jsp"/>
        </h:form>
</rich:modalPanel>

<rich:modalPanel id="mpCambioGarante" width="990" height="420"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cambio de Garante" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkCambioGarante"/>
               <rich:componentControl for="mpCambioGarante" attachTo="hidelinkCambioGarante" operation="hide" event="onclick"/>
        </h:panelGroup>
        </f:facet>
        <h:form id = "formCambioGarante">
          <a4j:include viewId="/pages/cuentacte/movimiento/popup/tipomovimiento/cambioGarante.jsp"/>
        </h:form>
</rich:modalPanel>

<rich:modalPanel id="mpCambioEntidad" width="990" height="420"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cambio de Entidad" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkCambioEntidad"/>
               <rich:componentControl for="mpCambioEntidad" attachTo="hidelinkCambioEntidad" operation="hide" event="onclick"/>
        </h:panelGroup>
        </f:facet>
        <h:form id = "formCambioEntidad">
          <a4j:include viewId="/pages/cuentacte/movimiento/popup/tipomovimiento/cambioEntidad.jsp"/>
        </h:form>
</rich:modalPanel>

<rich:modalPanel id="mpSocioEstructura" width="696" height="350"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Unidades Ejecutoras"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpSocioEstructura" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclSocioEstructura" viewId="/pages/cuentacte/movimiento/popup/socioDependenciaBody.jsp" layout="block"/>
</rich:modalPanel>

<rich:modalPanel id="mpAlertaEnvioPlanilla" width="496" height="250"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Alerta Envio Planilla"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
       
       <a4j:include id="inclAlertaEnvioPlanilla" viewId="/pages/cuentacte/movimiento/popup/mpAlertaEnvioPlanilla.jsp" layout="block"/>
</rich:modalPanel>


<rich:modalPanel id="mdpCambioCondicion" width="900" height="400"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cambio de Condición" />
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" 
               		styleClass="hidelink" >
					<rich:componentControl for="mdpCambioCondicion" operation="hide"
						event="onclick" />
				</h:graphicImage>
        </h:panelGroup>
        </f:facet>
        <h:form id = "formCambioCondicion">
          <a4j:include viewId="/pages/cuentacte/movimiento/popup/tipomovimiento/cambioCondicion.jsp"/>
        </h:form>
</rich:modalPanel>


<rich:modalPanel id="mpCambioCondicionLaboral" width="820" height="460"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cambio Condición Laboral" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkCambioEntidadLaboral"/>
               <rich:componentControl for="mpCambioCondicionLaboral" attachTo="hidelinkCambioEntidadLaboral" operation="hide" event="onclick"/>
        </h:panelGroup>
        </f:facet>
        <h:form id = "formCambioCondicionLaboral">
          <a4j:include viewId="/pages/cuentacte/movimiento/popup/tipomovimiento/cambioCondicionLaboral.jsp"/>
        </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAdjuntarDocumento" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Documento de Contrato"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarDocumento" operation="hidelink" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/cuentacte/movimiento/popup/mpAdjuntaDocumento.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="mpDescuentoIndebido" width="840" height="420"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Descuento Indebido" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkmpDescuentoIndebido"/>
               <rich:componentControl for="mpDescuentoIndebido" attachTo="hidelinkmpDescuentoIndebido" operation="hide" event="onclick"/>
        </h:panelGroup>
        </f:facet>
        <h:form id = "formDescuentoIndebido">
          <a4j:include viewId="/pages/cuentacte/movimiento/popup/tipomovimiento/descuentoIndebido.jsp"/>
        </h:form>
</rich:modalPanel>


<rich:modalPanel id="mpTransferencia" width="840" height="420"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Transferencia" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkmpTransferencia"/>
               <rich:componentControl for="mpTransferencia" attachTo="hidelinkmpTransferencia" operation="hide" event="onclick"/>
        </h:panelGroup>
        </f:facet>
        <h:form id = "formTransferencia">
          <a4j:include viewId="/pages/cuentacte/movimiento/popup/tipomovimiento/transferencia.jsp"/>
        </h:form>
</rich:modalPanel>


<a4j:include viewId="/pages/cuentacte/movimiento/popup/mpBusqGarante.jsp"/>
<a4j:include viewId="/pages/fileupload/fileupload.jsp"/>

<a4j:jsFunction name="selecTipoContrato" reRender="calIniContrato,calFinContrato"
		actionListener="#{cuentaCteController.resetFechasContrato}">
		<f:param name="pIntTipoContrato"></f:param>
</a4j:jsFunction>

<h:form id="frmPrincipal" >
  	<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled" 
			style="margin:15px; width:970px">
	    <rich:tab label="Movimiento de Cta. Cte.">
	     	<a4j:include id="idIncludeTabCuentaCte" viewId="/pages/cuentacte/movimiento/tabCuentaCte.jsp"/>
	  	</rich:tab>
  	</rich:tabPanel>
</h:form>