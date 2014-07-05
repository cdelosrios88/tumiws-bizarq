<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO ACTIVIDAD ECONOMICA  -->
	<!-- Fecha     : 20/04/2012               		-->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
    <h:form id="frmActividadEconomica">
     	<h:panelGroup style="background-color:#DEEBF5">
             <h:panelGrid columns="3">
	             <rich:column style="border:none">
	             	<h:outputText value="Act. Económica: "></h:outputText>
	             </rich:column>
	             <rich:column>
	             	<h:inputText value="#{actEconomicaController.strActEconomicaBusq}" size="20"></h:inputText>
	             </rich:column>
	             <rich:column>
	             	<a4j:commandButton value="Buscar" actionListener="#{actEconomicaController.buscarActEconomicaPorDesc}"
	             					   reRender="pgListaActEconomica" styleClass="btnEstilos"/>
	             </rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgListaActEconomica">
             	<rich:dataTable id="tbActEconomica" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="580px" 
    				value="#{actEconomicaController.arrayActividadEconomica}" 
    				rows="8"
    				styleClass="dataTable1">
	                	<rich:column width="30px">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="30">
	                        <f:facet name="header">
	                            <h:outputText value="Seleccione"></h:outputText>
	                        </f:facet>
	                        <h:selectBooleanCheckbox value="#{item.blnChecked}"></h:selectBooleanCheckbox>
	                    </rich:column>
	                    <rich:column width="520">
	                    	<f:facet name="header">
	                            <h:outputText value="Actividad Económica"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
	                    </rich:column>
                    <f:facet name="footer">   
	                    <rich:datascroller for="tbActEconomica" maxPages="10"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="3">
             	<rich:column width="200"></rich:column>
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
             			actionListener="#{actEconomicaController.seleccionarActEconomica}"
             			reRender="#{actEconomicaController.strIdModalPanel},#{actEconomicaController.pgListActividadEconomica}"/>
             	</rich:column>
             	<rich:column>
             		<a4j:commandButton value="Limpiar" styleClass="btnEstilos"
             			reRender="pgListaActEconomica,tbActEconomica"
             			actionListener="#{actEconomicaController.limpiarArrayActividadEconomica}"/>
             	</rich:column>
             </h:panelGrid>
         </h:panelGroup>
     </h:form>