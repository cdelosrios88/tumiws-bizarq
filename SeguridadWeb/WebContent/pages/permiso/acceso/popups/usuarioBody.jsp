<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca        			-->
	<!-- Modulo    : Seguridad                 		-->
	<!-- Prototipo : PROTOTIPO selección de usuarios-->
	<!-- Fecha     : 08/05/2012               		-->
    <h:form id="frmEntidad">
    	<rich:panel style="background-color:#DEEBF5">        	
             <h:panelGrid id="pgListaUsuarios">
             	<rich:dataTable id="tbUsuarios" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="600px" 
    				value="#{accesoController.listaUsuarios}" 
    				rows="5">
	                <rich:column style="border: 1px solid #C0C0C0" width="30px">
	                	<h:outputText value="#{rowKey + 1}"></h:outputText>
	                </rich:column>
	                <rich:column style="border: 1px solid #C0C0C0" width="90">
	                	<f:facet name="header">
	              			<h:outputText value="Seleccione"></h:outputText>
	                	</f:facet>
	                    <a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
		             		actionListener="#{accesoController.seleccionarUsuario}"
		             		onclick="Richfaces.hideModalPanel('pBuscarUsuario')"
		             		reRender="#{accesoController.cajaUsuario}"
		             	>
		             		<f:attribute name="item" value="#{item}"/>
		             	</a4j:commandButton>
	             	</rich:column>
	                <rich:column style="border: 1px solid #C0C0C0" width="160">
	                	<f:facet name="header">
	                    	<h:outputText value="Nombre"></h:outputText>
	                 	</f:facet>
	                    <h:outputText value="#{item.usuario.persona.natural.strNombres} #{item.usuario.persona.natural.strApellidoPaterno} #{item.usuario.persona.natural.strApellidoMaterno}"/>
	             	</rich:column>
	                <rich:column style="border: 1px solid #C0C0C0" width="160">
	                	<f:facet name="header">
	                    	<h:outputText value="Perfiles"></h:outputText>
	                  	</f:facet>
	                    <h:outputText value="#{item.strPerfil}"/>
	               	</rich:column>
	                <rich:column style="border: 1px solid #C0C0C0" width="160">
	                	<f:facet name="header">
	                    	<h:outputText value="Sucursales"></h:outputText>
	                  	</f:facet>
	                    <h:outputText value="#{item.strSucursal}"/>
	              	</rich:column>
                    <f:facet name="footer">
	                	<rich:datascroller for="tbUsuarios" maxPages="10"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>			
         </rich:panel>
     </h:form>