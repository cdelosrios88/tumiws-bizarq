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
     	<rich:panel style="background-color:#DEEBF5">
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
             	<rich:dataTable id="tbActEconomica" var="item" rowKeyVar="rowKey" sortMode="single" width="500px" 
    							value="#{actEconomicaController.arrayActividadEconomica}" rows="5">
	                	<rich:column style="border: 1px solid #C0C0C0" width="29px">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column style="border: 1px solid #C0C0C0" width="29">
	                        <h:selectBooleanCheckbox value="#{item.blnChecked}"></h:selectBooleanCheckbox>
	                    </rich:column>
	                    <rich:column style="border: 1px solid #C0C0C0" width="420">
	                    	<f:facet name="header">
	                            <h:outputText value="Actividad Económica" styleClass="tamanioLetra"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
	                    </rich:column>
                    <f:facet name="footer">   
	                    <rich:datascroller for="tbActEconomica" maxPages="5"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="2">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
             						actionListener="#{actEconomicaController.seleccionarActEconomica}"
             						reRender="#{actEconomicaController.pgListActividadEconomica}"
             						oncomplete="Richfaces.hideModalPanel('#{actEconomicaController.strIdModalPanel}')"/>
             	</rich:column>
             	<rich:column>
             		<a4j:commandButton value="Limpiar" reRender="pgListaActEconomica,tbActEconomica" styleClass="btnEstilos"
             						actionListener="#{actEconomicaController.limpiarArrayActividadEconomica}"/>
             	</rich:column>
             </h:panelGrid>
         </rich:panel>
     </h:form>