<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca        			-->
	<!-- Modulo    : Créditos                 		-->


<rich:modalPanel id="pBuscarEntidad" width="800" height="500"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Estructura"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarEntidad" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    
    <h:form id="frmEntidad">
     	<rich:panel style="background-color:#DEEBF5" id="panelSeleccionaEstructura">
     	
             <h:panelGrid columns="2">
	             <rich:column style="border:none" width="150">
	             	<h:outputText value="Tipo de Entidad : "></h:outputText>
	             </rich:column>
	             <rich:column>
	             	<h:selectOneMenu
                  		style="width: 150px;"
			        	value="#{requisitosController.estructuraFiltro.id.intNivel}">
			      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	             </rich:column>
             </h:panelGrid>                         
                          
             <h:panelGrid columns="3">
	             <rich:column style="border:none" width="150">
	             	<h:outputText value="Nombre de Entidad : "></h:outputText>
	             </rich:column>
	             <rich:column>
	             	<h:inputText value="#{requisitosController.estructuraFiltro.juridica.strRazonSocial}" size="23"/>
	             </rich:column>
	             <rich:column>
	             	<a4j:commandButton value="Buscar" action="#{requisitosController.buscarEstructuraDetalle}"
	             		reRender="pgListaEstructuras" styleClass="btnEstilos"/>
	             </rich:column>
             </h:panelGrid>
                          
             <h:panelGrid id="pgListaEstructuras">
             	<rich:dataTable id="tbEstructuras" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="750px" 
    				value="#{requisitosController.listaEstructuraDetalle}" 
    				rows="5">
	                <rich:column width="50" style="text-align:center">
	                	<f:facet name="header">
	             			<h:outputText value="Seleccione"/>
	                	</f:facet>
	                	<h:selectBooleanCheckbox value="#{item.checked}"/>	                    
	             	</rich:column>
	                <rich:column width="250" style="text-align: center">
	                	<f:facet name="header">
	                   		<h:outputText value="Nombre"/>
	                   	</f:facet>
	                	<h:outputText value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
	         		</rich:column>
	         		<rich:column width="150" style="text-align: center">
	                	<f:facet name="header">
	                   		<h:outputText value="Tipo Modalidad"/>
	                   	</f:facet>
	                   	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaModalidadCod}"/>
	         		</rich:column>                    
	         		<rich:column width="150" style="text-align: center">
	                	<f:facet name="header">
	                   		<h:outputText value="Tipo Socio"/>
	                   	</f:facet>
	                   	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaTipoSocioCod}"/>
	         		</rich:column>
	         		<rich:column width="150" style="text-align: center">
	                	<f:facet name="header">
	                   		<h:outputText value="Tipo Entidad"/>
	                   	</f:facet>
	                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.estructura.id.intNivel}"/>
	         		</rich:column>
                    <f:facet name="footer">
	                	<rich:datascroller for="tbEstructuras" maxPages="20"/>   
	               	</f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="1">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
		             	action="#{requisitosController.agregarConfServEstructuraDetalle}"
		             	onclick="Richfaces.hideModalPanel('pBuscarEntidad')"
		             	reRender="#{requisitosController.nombrePanelEstructura}">
		             </a4j:commandButton>
             	</rich:column>
             </h:panelGrid>
             			
         </rich:panel>
     </h:form>

</rich:modalPanel>     