<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca	    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO ACTIVIDAD ECONOMICA  -->
	<!-- Fecha     : 11/05/2012               		-->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
    <h:form id="frmEntidad">
     	<rich:panel style="background-color:#DEEBF5">
             <h:panelGrid columns="3">
	             <rich:column style="border:none">
	             	<h:outputText value="Entidad "></h:outputText>
	             </rich:column>
	             <rich:column>
	             	<h:inputText value="#{padronController.adminPadronFiltroSolicitud.estructura.juridica.strRazonSocial}" size="20"></h:inputText>
	             </rich:column>
	             <rich:column>
	             	<a4j:commandButton value="Buscar" actionListener="#{padronController.buscarAdminPadronPorNombre}"
	             					   reRender="pgListaAdminPadrones" styleClass="btnEstilos"/>
	             </rich:column>
             </h:panelGrid>
             <h:panelGrid id="pgListaAdminPadrones">
             	<rich:dataTable id="tbAdminPadrones" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="700px" 
    				value="#{padronController.listaAdminPadronesParaSolicitud}" 
    				rows="5">
	                <rich:column width="30px" style="text-align: center;">
	                	<h:outputText value="#{rowKey + 1}"></h:outputText>
	              	</rich:column>
	                <rich:column width="70" style="text-align: center;">
	                	<f:facet name="header">
	              			<h:outputText value="Seleccione"></h:outputText>
	                	</f:facet>
	                	<h:selectBooleanCheckbox value="#{item.checked}"></h:selectBooleanCheckbox>
	             	</rich:column>
	                <rich:column width="150" style="text-align: center;">
	                	<f:facet name="header">
	                    	<h:outputText value="Tipo de Archivo"></h:outputText>
	                  	</f:facet>
	                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOARCHIVOPADRON}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaTipoArchivoPadronCod}"/>
	              	</rich:column>
	              	<rich:column width="150" style="text-align: center;">
	                	<f:facet name="header">
	                    	<h:outputText value="Entidad"></h:outputText>
	                  	</f:facet>
	                    <h:outputText value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
	              	</rich:column>
	              	<rich:column width="100" style="text-align: center;">
	                	<f:facet name="header">
	                    	<h:outputText value="Periodo"></h:outputText>
	                  	</f:facet>
	                    <h:outputText value="#{item.id.intMes}-#{item.id.intPeriodo}"></h:outputText>  
	              	</rich:column>
	          		<rich:column width="100" style="text-align: center;">
	                	<f:facet name="header">
	                    	<h:outputText value="Tipo de Planilla"></h:outputText>
	                  	</f:facet>
	                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaModalidadCod}"/>   
	              	</rich:column>
	              	<rich:column width="100" style="text-align: center;">
	                	<f:facet name="header">
	                    	<h:outputText value="Tipo de Socio"></h:outputText>
	                  	</f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaTipoSocioCod}"/>  
	              	</rich:column>
                    <f:facet name="footer">   
	                    <rich:datascroller for="tbAdminPadrones" maxPages="5"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="1">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
             			actionListener="#{padronController.seleccionarPadrones}"
             			onclick="Richfaces.hideModalPanel('pBuscarAdminPadron')"
             			reRender="panelPadronesSinSolicitud"
             		/>
             	</rich:column>
             </h:panelGrid>
         </rich:panel>
     </h:form>