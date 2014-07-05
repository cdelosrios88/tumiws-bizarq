<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca        			-->
	<!-- Modulo    : Servicio                 		-->
	<!-- Prototipo : PROTOTIPO perfil				-->
	<!-- Fecha     : 26/06/2012               		-->

    <h:form id="frmPerfil">
     	<rich:panel style="background-color:#DEEBF5">             
             
			<h:panelGrid columns="2">
				<rich:column style="border:none" width="120">
					<h:outputText value="Tipo de Perfil : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						style="width: 130px;"
						value="#{autorizacionController.perfilFiltro.intTipoPerfil}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERFIL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
							/>
					</h:selectOneMenu>
	            </rich:column>
             </h:panelGrid>
             
             <h:panelGrid columns="3">
				<rich:column style="border:none" width="120">
					<h:outputText value="Nombre de Perfil : "/>
				</rich:column>
				<rich:column>
					<h:inputText size="22" value="#{autorizacionController.perfilFiltro.strDescripcion}"/>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Buscar" action="#{autorizacionController.buscarPerfil}"
	             		reRender="pgListaPerfiles" styleClass="btnEstilos"/>
				</rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgListaPerfiles">
             	<rich:dataTable id="tbPerfiles" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="540px" 
    				value="#{autorizacionController.listaPerfil}"
    				rows="5">
	                <rich:column style="text-align:center" width="90">
	                	<f:facet name="header">
	             			<h:outputText value="Seleccione"/>
	                	</f:facet>
	                	<h:selectBooleanCheckbox value="#{item.checked}"/>	                    
	             	</rich:column>	                
	         		<rich:column width="120px" style="text-align: center">
						<f:facet name="header">
				       		<h:outputText value="Código"/>
						</f:facet>
						<h:outputText value="#{item.id.intIdPerfil}"/>
				 	</rich:column>
				    <rich:column width="230" style="text-align: center">
				    	<f:facet name="header">
				        	<h:outputText value="Nombre"/>
				      	</f:facet>
				      	<h:outputText value="#{item.strDescripcion}"/>
					</rich:column>
					<rich:column width="100" style="text-align: center">
				    	<f:facet name="header">
				        	<h:outputText value="Tipo"/>
				      	</f:facet>
				      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERFIL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intTipoPerfil}"/>
					</rich:column>
				    <f:facet name="footer">
	                	<rich:datascroller for="tbPerfiles" maxPages="20"/>   
	               	</f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="1">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
		             	action="#{autorizacionController.agregarConfServPerfil}"
		             	onclick="Richfaces.hideModalPanel('pBuscarPerfil')"
		             	reRender="contPanelInferior"
		             >
		             </a4j:commandButton>
             	</rich:column>
             </h:panelGrid>			
         </rich:panel>
     </h:form>