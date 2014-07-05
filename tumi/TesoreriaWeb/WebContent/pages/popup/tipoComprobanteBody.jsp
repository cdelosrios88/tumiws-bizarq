<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO TIPO DE COMPROBANTE  -->
	<!-- Fecha     : 20/04/2012               		-->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
    <h:form id="frmTipoComprobante">
     	<h:panelGroup style="background-color:#DEEBF5">
             <h:panelGrid>
	             <rich:column style="text-align: center;" >
	             	<h:outputText value="Tipos de Comprobante: "></h:outputText>
	             </rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgArrayComprobante">
             	<rich:dataTable id="tbComprobante" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="520px" 
    				value="#{tipoComprobanteController.arrayTipoComprobante}" 
    				rows="10" 
    				styleClass="dataTable1">
	                	<rich:column width="30px">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="50">
	                    	<f:facet name="header">
	                            <h:outputText value="Seleccione"></h:outputText>
	                        </f:facet>
	                        <h:selectBooleanCheckbox value="#{item.blnChecked}"></h:selectBooleanCheckbox>
	                    </rich:column>
	                    <rich:column width="440">
	                    	<f:facet name="header">
	                            <h:outputText value="Tipo de Comprobante"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
	                    </rich:column>
                    <f:facet name="footer">   
	                    <rich:datascroller for="tbComprobante" maxPages="5"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="3">
             	<rich:column width="180">
             	</rich:column>
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
             			actionListener="#{tipoComprobanteController.seleccionarTipoComprobante}"
             			reRender="#{tipoComprobanteController.strIdModalPanel},#{tipoComprobanteController.pgListTipoComprobante}"/>
             	</rich:column>
             	<rich:column>
             		<a4j:commandButton value="Limpiar" reRender="pgListaActEconomica" styleClass="btnEstilos"
             			actionListener="#{tipoComprobanteController.limpiarArrayTipoComprobante}"/>
             	</rich:column>
             </h:panelGrid>
         </h:panelGroup>
     </h:form>