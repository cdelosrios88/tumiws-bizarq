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
    <h:form id="frmRolesPopup">
     	<rich:panel style="background-color:#DEEBF5">
             <h:panelGrid>
	             <rich:column style="border:none">
	             	<h:outputText value="Seleccionar Roles: "></h:outputText>
	             </rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgListRoles">
             	<rich:dataTable id="tbRoles" var="item" rowKeyVar="rowKey" sortMode="single" width="200px" 
    							value="#{socioController.listRoles}" rows="10" styleClass="dataTable1">
	                	<rich:column width="29px">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="70">
	                    	<f:facet name="header">
	                            <h:outputText value="Seleccione"></h:outputText>
	                        </f:facet>
	                        <h:selectBooleanCheckbox value="#{item.isChecked}"></h:selectBooleanCheckbox>
	                    </rich:column>
	                    <rich:column>
	                    	<f:facet name="header">
	                            <h:outputText value="Roles"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
	                    </rich:column>
                    <f:facet name="footer">   
	                    <rich:datascroller for="tbRoles" maxPages="10"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="2">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
             						actionListener="#{socioController.seleccionarRoles}"
             						oncomplete="Richfaces.hideModalPanel('mpRoles'),rederRoles()"/>
             	</rich:column>
             	<rich:column>
             		<a4j:commandButton value="Limpiar" reRender="pgListRoles" styleClass="btnEstilos"
             						actionListener="#{socioController.limpiarRoles}"/>
             	</rich:column>
             </h:panelGrid>
         </rich:panel>
     </h:form>