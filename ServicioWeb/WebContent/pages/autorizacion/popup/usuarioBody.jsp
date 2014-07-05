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
					<h:outputText value=" Perfil : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
                  		style="width: 200px;"
			        	value="#{autorizacionController.usuarioCompFiltro.intIdPerfil}">
			      		<f:selectItem itemLabel="Todos" itemValue="#{applicationScope.Constante.PARAM_COMBO_TODOS}"/>
			        	<tumih:selectItems var="sel" value="#{autorizacionController.listaPerfilPersiste}"
							itemValue="#{sel.id.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	            </rich:column>
             </h:panelGrid>
             
             <h:panelGrid columns="3">
				<rich:column style="border:none" width="120">
					<h:outputText value="Nombre de Perfil : "/>
				</rich:column>
				<rich:column>
					<h:inputText size="35" value="#{autorizacionController.usuarioCompFiltro.usuario.strUsuario}"/>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Buscar" action="#{autorizacionController.buscarUsuarioComp}"
	             		reRender="pgListaUsuarios" styleClass="btnEstilos"/>
				</rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgListaUsuarios">
             	<rich:dataTable id="tbUsuarios" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="650px" 
    				value="#{autorizacionController.listaUsuarioComp}"
    				rows="5">
	                <rich:column style="text-align:center" width="90">
	                	<f:facet name="header">
	             			<h:outputText value="Seleccione"/>
	                	</f:facet>
	                	<h:selectBooleanCheckbox value="#{item.checked}"/>
	             	</rich:column>	                
	         		<rich:column width="230" style="text-align: center">
				    	<f:facet name="header">
				        	<h:outputText value="Nombre"/>
				      	</f:facet>
				      	<h:outputText value="#{item.usuario.strUsuario}"/>
					</rich:column>
					<rich:column width="330" style="text-align: center">
				    	<f:facet name="header">
				        	<h:outputText value="Perfil"/>
				      	</f:facet>
						<h:outputText value="#{item.strPerfil}"/>
					</rich:column>
				    <f:facet name="footer">
	                	<rich:datascroller for="tbUsuarios" maxPages="20"/>   
	               	</f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="1">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
		             	action="#{autorizacionController.agregarConfServUsuario}"
		             	onclick="Richfaces.hideModalPanel('pBuscarUsuario')"
		             	reRender="colTablaUsuarios">
		             </a4j:commandButton>
             	</rich:column>
             </h:panelGrid>			
         </rich:panel>
     </h:form>