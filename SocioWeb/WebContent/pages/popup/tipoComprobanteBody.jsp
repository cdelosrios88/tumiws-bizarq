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
     	<rich:panel style="background-color:#DEEBF5">
             <h:panelGrid>
	             <rich:column style="border:none">
	             	<h:outputText value="Tipos de Comprobante: "></h:outputText>
	             </rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgArrayComprobante">
             	<rich:dataTable id="tbComprobante" var="item" rowKeyVar="rowKey" sortMode="single" width="290px" 
    							value="#{tipoComprobanteController.arrayTipoComprobante}" rows="10" styleClass="dataTable1">
	                	<rich:column width="29px">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="70">
	                    	<f:facet name="header">
	                            <h:outputText value="Seleccione"></h:outputText>
	                        </f:facet>
	                        <h:selectBooleanCheckbox value="#{item.blnChecked}"></h:selectBooleanCheckbox>
	                    </rich:column>
	                    <rich:column>
	                    	<f:facet name="header">
	                            <h:outputText value="Actividad Económica"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
	                    </rich:column>
                    <f:facet name="footer">   
	                    <rich:datascroller for="tbComprobante" maxPages="10"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="2">
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
         </rich:panel>
     </h:form>