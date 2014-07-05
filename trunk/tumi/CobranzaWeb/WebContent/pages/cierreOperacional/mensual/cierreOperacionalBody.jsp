<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Seguridad : Cooperativa El Tumi        -->
	<!-- Autor     : Christian De los Ríos F.  	-->
	<!-- Modulo    : Cobranza         			-->
	<!-- Prototipo : Cierre Mensual 			-->
	<!-- Fecha     : 24/06/2013             	-->

<script type="text/javascript">
	
</script>
<style type="text/css"></style>

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
	    		<rich:column width="150">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{cierreMensualContabilidadController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<a4j:form id="frmPrincipal" >
  	<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	    <rich:tab label="Créditos" disabled="#{cierreOperacionalController.blnCreditos}">
	     	
	  	</rich:tab>
	  	<rich:tab label="Cobranza" disabled="#{cierreOperacionalController.blnCobranza}">
	     	<a4j:include viewId="/pages/cierreOperacional/mensual/cobranza/tabCobranza.jsp"/>
	  	</rich:tab>
	  	<rich:tab label="Tesorería" disabled="#{cierreOperacionalController.blnTesoreria}">
	     	
	  	</rich:tab>
	  	<rich:tab label="Contabilidad" disabled="#{cierreOperacionalController.blnContabilidad}">
	     	<a4j:include viewId="/pages/cierreOperacional/mensual/contabilidad/contabilidadBody.jsp"/>
	  	</rich:tab>
  	</rich:tabPanel>
</a4j:form>